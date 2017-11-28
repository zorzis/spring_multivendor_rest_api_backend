package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.payment_processor_paypal;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrderStatusCodesDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrdersDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dao.PlaceOrderDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dao.ShoppingCartDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderStatusCodes;
import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCart;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.IPaymentProcessorStep1SetterService;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.helper_services.OrderEntityCreatorService;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;
import com.multimerchant_haze.rest.v1.modules.payments.service.paypal_api.PaypalRestAPIService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;

/**
 * Created by zorzis on 10/4/2017.
 */
@Service
public class PaymentProcessorStep1SetterServicePaypal implements IPaymentProcessorStep1SetterService, IPaymentPaypalProcessorCheckersService
{
    @Autowired
    private PaypalRestAPIService paypalRestAPIService;

    @Autowired
    private PlaceOrderDAO placeOrderDAO;

    @Autowired
    private OrderEntityCreatorService orderEntityCreatorService;

    @Autowired
    private OrderStatusCodesDAO orderStatusCodesDAO;

    @Autowired
    private OrdersDAO ordersDAO;

    @Autowired
    private ShoppingCartDAO shoppingCartDAO;


    private String paypalApprovalURL;

    private Payment paypalPaymentSetterResponseObject;

    private Payment paymentObjectAfterGetterPaypalAPICall;

    private String paypalPaymentID;



    @Override
    @Transactional("transactionManager")
    public Object placeOrder(String shoppingCartID) throws AppException
    {
        // At first we just check if there is such a shopping cart
        // and also check if shopping cart not already used for an order
        ShoppingCart shoppingCart = this.checkShoppingCartIDFromRequestIsAcceptable(shoppingCartID);
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO(shoppingCart);


        // The order to be persisted but without a Payment Assigned yet
        Order orderToBePlaced = this.orderEntityCreatorService.createOrderEntity(shoppingCartDTO);

        // Set the ammounts of total prices subtotals and taxes
        Float totalOrderPrice = this.calculateTotalOrderPrice(orderToBePlaced.getSubTotalOrderPrice(), orderToBePlaced.getTotalProductsTax());
        orderToBePlaced.setTotalOrderPrice(totalOrderPrice);
        orderToBePlaced.setTotalProcessorTax(this.calculateTotalProcessorTax(totalOrderPrice));

        Payment paypalOriginalPaymentObject = this.paypalRestAPIService.createPaypalCheckoutPaymentObject(orderToBePlaced);

        //** We check if Payment Method claims to be used for payment indeed exists and belongs to producer acceptable payment methods
        PaymentMethod paymentMethodVerified = this.orderEntityCreatorService.checkIfPaymentMethodBelongsToProducer(shoppingCartDTO.getPaymentMethodID(), orderToBePlaced.getOrderHasClientHasProducer().getProducer());


        /**
         * PAYPAL API CALL
         * Create the paypal payment object to be send to Paypal API
         * and return a 'paymentID' which we gonna use later as unique identifier to
         * keep in touch with Paypal Server through getter API calls
         *
         * Verify that payemnt Object sent back as response is in "created" state mode
         *
         */
        try
        {
            this.paypalPaymentSetterResponseObject = this.paypalRestAPIService.setPaypalExpressCheckout(paypalOriginalPaymentObject);
        } catch (PayPalRESTException e)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to Process Paypal Checkout when setting the ExpressCheckout.");
            sb.append(" ");
            sb.append("A PayPalRestException is thrown.");
            sb.append(" ");
            sb.append("Error follows");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(e.toString());
            sb.append("]");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            appException.setAppErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("A PayPal Error Exception is thrown.If Error continues contact Developer.");
            throw appException;
        }
        this.verifyStateIsSetToCreated(this.paypalPaymentSetterResponseObject);


        /**
         * PaymentID (Original Paypal Payment ID)
         */
        this.paypalPaymentID = paypalPaymentSetterResponseObject.getId();


        /**
         * PAYPAL API CALL
         * Verify payment to Paypal is Created and state is set to 'created;
         */
        this.paymentObjectAfterGetterPaypalAPICall = this.paypalRestAPIService.getPaymentFromPaypalAPIByPaypalPaymentID(this.paypalPaymentID);
        this.verifyStateIsSetToCreated(this.paymentObjectAfterGetterPaypalAPICall);

        // ### Take Payment Approval Url
        Iterator<Links> links = this.paypalPaymentSetterResponseObject.getLinks().iterator();
        while (links.hasNext())
        {
            Links link = links.next();
            if (link.getRel().equalsIgnoreCase("approval_url"))
            {
                this.paypalApprovalURL = link.getHref();

            }
        }


        /* PaymentApproval */
        PaymentApproval paymentApproval = new PaymentApproval();
        paymentApproval.setCreatedAt(new Date());
        paymentApproval.setStatus(PaymentStatusEnum.SUCCEEDED);
        paymentApproval.setAmmount(orderToBePlaced.getTotalOrderPrice());
        paymentApproval.setProcessorMessage(this.paypalPaymentID);
        paymentApproval.setPaymentApprovalID(this.orderEntityCreatorService.paymentApprovalIDUniqueGenerator());

        /* Payment */
        PaymentPaypal paypalPayment = new PaymentPaypal();
        paypalPayment.setCreatedAt(new Date());
        paypalPayment.setPaymentID(this.orderEntityCreatorService.paymentIDUniqueGenerator());
        paypalPayment.setOrder(orderToBePlaced);
        paypalPayment.setPaymentMethod(paymentMethodVerified);
        paypalPayment.setOrder(orderToBePlaced);
        paypalPayment.setPaymentApproval(paymentApproval);
        paypalPayment.setPaypalApprovalPaymentID(this.paypalPaymentID);

        /* Order Status */
        // TODO Create a generic Order Status Service depending on
        //** Order Status Code Ref
        OrderStatusCodes orderStatusCodeRef = this.orderStatusCodesDAO.getSingleOrderStatusCodeRefByStatusID(ORDER_STATUS_CODE_TO_BE_ASSIGNED_TO_ORDER_INPROGRESS);
        this.orderEntityCreatorService.checkIfOrderStatusCodeRefExists(orderStatusCodeRef);

        orderToBePlaced.setOrderPayment(paypalPayment);
        orderToBePlaced.setOrderStatusCode(orderStatusCodeRef);

        orderToBePlaced.setShoppingCart(shoppingCart);

        paypalPayment.setOrder(orderToBePlaced);
        paymentApproval.setPayment(paypalPayment);


        this.placeOrderDAO.placeNewOrderByOrderEntity(orderToBePlaced);

        return this.paypalPaymentID;
    }




    private ShoppingCart checkShoppingCartIDFromRequestIsAcceptable(String shoppingCartID)
    {
        //** At first check shopping cart with specific id exists
        ShoppingCart shoppingCart = this.shoppingCartDAO.getShoppingCartByID(shoppingCartID);
        if(shoppingCart == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad Request!ShoppingCartID not found.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Shopping Cart not found!");
            throw appException;
        }

        //** Secondly check if specific shopping cart is already used by an order
        Order orderAssumeNotToBeFound = this.ordersDAO.getOrderByShoppingCartID(shoppingCartID);

        if(orderAssumeNotToBeFound != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad Request!ShoppingCartID is already used by another Order.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("The shoppingCartID of the request seems to be already used by another order.If this is not a normal behavor please contact Dev Team!");
            throw appException;
        }

        return shoppingCart;
    }


}

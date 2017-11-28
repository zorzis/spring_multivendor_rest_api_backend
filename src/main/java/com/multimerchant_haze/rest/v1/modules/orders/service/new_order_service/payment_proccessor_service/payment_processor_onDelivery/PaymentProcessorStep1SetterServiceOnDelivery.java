package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.payment_processor_onDelivery;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrderStatusCodesDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrdersDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dao.PlaceOrderDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dao.ShoppingCartDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderStatusCodes;
import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCart;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.IPaymentProcessorStep1SetterService;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.helper_services.OrderEntityCreatorService;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by zorzis on 10/4/2017.
 */
@Service
public class PaymentProcessorStep1SetterServiceOnDelivery implements IPaymentProcessorStep1SetterService
{

    @Autowired
    PlaceOrderDAO placeOrderDAO;

    @Autowired
    OrderEntityCreatorService orderEntityCreatorService;

    @Autowired
    OrderStatusCodesDAO orderStatusCodesDAO;


    @Autowired
    private OrdersDAO ordersDAO;

    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

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

        //** We check if Payment Method claims to be used for payment indeed exists and belongs to producer acceptable payment methods
        PaymentMethod paymentMethodVerified = this.orderEntityCreatorService.checkIfPaymentMethodBelongsToProducer(shoppingCartDTO.getPaymentMethodID(), orderToBePlaced.getOrderHasClientHasProducer().getProducer());


        /* PaymentApproval */
        PaymentApproval paymentApproval = new PaymentApproval();
        paymentApproval.setCreatedAt(new Date());
        paymentApproval.setStatus(PaymentStatusEnum.SUCCEEDED);
        paymentApproval.setAmmount(orderToBePlaced.getTotalOrderPrice());
        paymentApproval.setProcessorMessage("Payment OnDelivery Approved internally by haze.gr platform.");
        paymentApproval.setPaymentApprovalID(this.orderEntityCreatorService.paymentApprovalIDUniqueGenerator());

        /* Payment */
        PaymentOnDelivery paymentOnDelivery = new PaymentOnDelivery();
        paymentOnDelivery.setCreatedAt(new Date());
        paymentOnDelivery.setPaymentID(this.orderEntityCreatorService.paymentIDUniqueGenerator());
        paymentOnDelivery.setOrder(orderToBePlaced);
        paymentOnDelivery.setPaymentMethod(paymentMethodVerified);
        paymentOnDelivery.setOrder(orderToBePlaced);
        paymentOnDelivery.setPaymentApproval(paymentApproval);
        paymentOnDelivery.setIsDeliveryPaid(false);

        /* Order Status */
        // TODO Create a generic Order Status Service depending on
        //** Order Status Code Ref
        OrderStatusCodes orderStatusCodeRef = this.orderStatusCodesDAO.getSingleOrderStatusCodeRefByStatusID(ORDER_STATUS_CODE_TO_BE_ASSIGNED_TO_ORDER_INPROGRESS);
        this.orderEntityCreatorService.checkIfOrderStatusCodeRefExists(orderStatusCodeRef);

        orderToBePlaced.setOrderPayment(paymentOnDelivery);
        orderToBePlaced.setOrderStatusCode(orderStatusCodeRef);

        orderToBePlaced.setShoppingCart(shoppingCart);

        paymentOnDelivery.setOrder(orderToBePlaced);
        paymentApproval.setPayment(paymentOnDelivery);


        String createdOrderID = this.placeOrderDAO.placeNewOrderByOrderEntity(orderToBePlaced);


        // Do the Deposit Logic Step
        return createdOrderID;
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

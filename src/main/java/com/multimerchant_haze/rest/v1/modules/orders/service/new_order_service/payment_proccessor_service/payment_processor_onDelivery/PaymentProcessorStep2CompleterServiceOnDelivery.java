package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.payment_processor_onDelivery;

import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dao.CompletePaymentOnDeliveryDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrderStatusCodesDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderStatusCodes;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.IPaymentProcessorStep2CompleterService;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.helper_services.OrderEntityCreatorService;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentDeposit;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by zorzis on 10/13/2017.
 */
@Service
public class PaymentProcessorStep2CompleterServiceOnDelivery implements IPaymentProcessorStep2CompleterService, IPaymentOnDeliveryProcessorCheckersService
{

    @Autowired
    private CompletePaymentOnDeliveryDAO completePaymentOnDeliveryDAO;

    @Autowired
    OrderEntityCreatorService orderEntityCreatorService;

    @Autowired
    OrderStatusCodesDAO orderStatusCodesDAO;

    private PaymentOnDelivery paymentOnDelivery;


    @Override
    @Transactional("transactionManager")
    public OrderDTO completeOrderPaymentToProcessorService(Object object) throws AppException
    {
        OrderDTO orderToBeReturned = new OrderDTO();

        // we asume we get a String
        if(object instanceof String)
        {
            String orderID = (String)object;
            /**
             * Do all checks to PaymentOnDelivery Entity From Database
             */
            this.paymentOnDelivery = this.completePaymentOnDeliveryDAO.getOrderByOrderID(orderID);
            this.checkPayment(paymentOnDelivery, orderID);

            /**
             * Create the entities to be updated/inserted after a Successful paypal payment
             */
              /* Order Status */
            // TODO Create a generic Order Status Service depending on
            //** Order Status Code Ref
            OrderStatusCodes orderStatusCodeRef = this.orderStatusCodesDAO.getSingleOrderStatusCodeRefByStatusID(ORDER_STATUS_CODE_TO_BE_ASSIGNED_TO_ORDER_PAID);
            this.orderEntityCreatorService.checkIfOrderStatusCodeRefExists(orderStatusCodeRef);


            Order orderToBeUpdated = this.paymentOnDelivery.getOrder();

            System.out.println("*******************************************************************");
            System.out.println("PaymentOnDelivery from PaymentOnDelivery Entity is: ");

            System.out.println("Order from PaymentOnDelivery Entity is: ");
            System.out.println(this.paymentOnDelivery.getOrder().toString());
            System.out.println("*******************************************************************");

            orderToBeUpdated.setOrderStatusCode(orderStatusCodeRef);
            orderToBeUpdated.setDateOrderPaid(new Date());



            // Construct the PaymentDeposit Entity
            PaymentDeposit paymentDeposit = new PaymentDeposit();
            paymentDeposit.setCreatedAt(new Date());
            paymentDeposit.setPaymentDepositID(this.orderEntityCreatorService.paymentDepositIDUniqueGenerator());
            paymentDeposit.setAmmount(this.paymentOnDelivery.getOrder().getTotalOrderPrice());
            paymentDeposit.setProcessorMessage("Payment OnDelivery Deposit internally by haze.gr platform.");
            paymentDeposit.setPaymentApproval(this.paymentOnDelivery.getPaymentApproval());
            paymentDeposit.setStatus(PaymentStatusEnum.SUCCEEDED);

            // set the new PaymentDeposit entity to PaymentApproval entity
            PaymentApproval paymentApproval = this.paymentOnDelivery.getPaymentApproval();
            paymentApproval.setPaymentDeposit(paymentDeposit);
            paymentApproval.setUpdatedAt(new Date());

            paymentDeposit.setPaymentApproval(paymentApproval);

            // set the updated PaymentApproval entity to PaymentPaypal entity
            this.paymentOnDelivery.setIsDeliveryPaid(true);
            this.paymentOnDelivery.setDeliveryPaidAt(new Date());
            this.paymentOnDelivery.setPaymentApproval(paymentApproval);
            this.paymentOnDelivery.setOrder(orderToBeUpdated);
            this.paymentOnDelivery.setUpdatedAt(new Date());
            orderToBeUpdated.setOrderPayment(this.paymentOnDelivery);


            String updatedOrderID = this.completePaymentOnDeliveryDAO.saveTheDepositInformation(this.paymentOnDelivery);

            orderToBeReturned = new OrderDTO();
            orderToBeReturned.setOrderID(updatedOrderID);
        }
        return orderToBeReturned;
    }


    private void checkPayment(PaymentOnDelivery paymentOnDelivery, String orderID) throws AppException
    {
        // check if there is a payment with the same paypalPaymentID(the one the Paypal Sends as
        // response to our PAYPAL API Set_Payment request
        this.checkIfPaymentExistsToOurSystem(paymentOnDelivery, orderID);

        // Check for existence of payment approval
        this.checkIfPaymentApprovalEntityExists(paymentOnDelivery.getPaymentApproval());

        // Check if succeeded so we can continue
        this.checkPaymentApprovalStatusIsSucceeded(paymentOnDelivery.getPaymentApproval());

        // Check payment is never paid again
        this.checkPaymentIsNotPaidYet(paymentOnDelivery.getPaymentApproval());
    }
}

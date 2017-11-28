package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.payment_processor_paypal;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dao.CompletePaymentPaypalDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrderStatusCodesDAO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.PaypalDataObjectReceivedFromFrontEndDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderStatusCodes;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.IPaymentProcessorStep2CompleterService;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.helper_services.OrderEntityCreatorService;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentDeposit;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;
import com.multimerchant_haze.rest.v1.modules.payments.service.paypal_api.PaypalRestAPIService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/** OOOOOOOOOOOOOOOOK!!!!!! Now my friends this is the point we gonna place some poetry
 *  to the all thing: https://www.youtube.com/watch?v=UDApZhXTpH8 take that biatch
 *  Created by zorzis on 10/6/2017.
 */
@Service
public class PaymentProcessorStep2CompleterServicePaypal implements IPaymentProcessorStep2CompleterService, IPaymentPaypalProcessorCheckersService
{
    @Autowired
    PaypalRestAPIService paypalRestAPIService;


    @Autowired
    CompletePaymentPaypalDAO completePaymentPaypalDAO;

    @Autowired
    private OrderEntityCreatorService orderEntityCreatorService;

    @Autowired
    private OrderStatusCodesDAO orderStatusCodesDAO;

    private PaymentPaypal localPaymentPaypalEntityFromOwrServer;

    private Payment paypalPaymentObjectFromExecutePaymentCheckoutResponse;

    private Payment paypalPaymentObjectFromPaypalAPIBeforeExecutionOfThePayment;
    private Payment paypalPaymentObjectFromPaypalAPIAfterExecutionOfThePayment;
    private String paypalSaleID;


    /**
     *
     * @param object => orderDTO.orderID of newly created Order
     * @return
     * @throws AppException
     */
    @Override
    @Transactional("transactionManager")
    public OrderDTO completeOrderPaymentToProcessorService(Object object) throws AppException
    {
        OrderDTO orderToBeReturned = new OrderDTO();

        // we asume we get a PaypalDataObjectReceivedFromFrontEndDTO from controller
        if(object instanceof PaypalDataObjectReceivedFromFrontEndDTO)
        {
            /**GET ORDER BY CLIENT AND PaypalPaymentID
             *
             * The end-user sends from the Frontend using our REST-API a request to execute the paypal payment
             * The request has 3 parameters as they are RECIEVED from Paypal API success_url
             */

            // At first we create a new Pure PaypalDataObjectReceivedFromFrontEndDTO object
            PaypalDataObjectReceivedFromFrontEndDTO paypalDataObjectReceivedFromFrontEndDTO = new PaypalDataObjectReceivedFromFrontEndDTO(object);


            /**
             * Check if we received the paymentID and PayerID from frontend and are not <null> or isEmpty()
             */
            this.checkIfPaypalDataObjectReceivedFromFrontEndDTOIsAcceptable(paypalDataObjectReceivedFromFrontEndDTO);

            /**
             * Do all checks to PaymentPaypal Entity From Database
             */
            this.localPaymentPaypalEntityFromOwrServer = this.completePaymentPaypalDAO.getOrderPaymentByOriginalPaypalPaymentID(paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentID());
            this.checkPayment(this.localPaymentPaypalEntityFromOwrServer, paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentID());


            /**
             * PAYPAL API CALL
             * Get the Paypal Payment by paymentID from Paypal REST API call
             * so we can inspect on it on parameters like 'state'
             */
            this.paypalPaymentObjectFromPaypalAPIBeforeExecutionOfThePayment = this.paypalRestAPIService.getPaymentFromPaypalAPIByPaypalPaymentID(paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentID());
            // Assume that payment object from Paypal API call state.equals("created")
            this.verifyStateIsSetToCreated(paypalPaymentObjectFromPaypalAPIBeforeExecutionOfThePayment);


            /**
             * PAYPAL API CALL
             * Execute the Payment to Paypal using Paypal API
             */
            try
            {
                this.paypalPaymentObjectFromExecutePaymentCheckoutResponse = this.paypalRestAPIService.executePaypalExpressCheckout(paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentID(), paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentPayerID());
            } catch (PayPalRESTException e)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to Process Paypal Checkout when executing the created Payment for the ExpressCheckout.");
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

            System.out.println("**************************************************************************************************");
            System.out.println("PAYPAL API CALL TO EXECUTE THE PAYMENT CHECKOUT: ");
            System.out.println("PAYPAL API RESPONSE: ");
            System.out.println(this.paypalPaymentObjectFromExecutePaymentCheckoutResponse.toJSON());
            System.out.println("**************************************************************************************************");


            /**
             * PAYPAL API CALL
             * Check that
             */
            this.paypalPaymentObjectFromPaypalAPIAfterExecutionOfThePayment = this.paypalRestAPIService.getPaymentFromPaypalAPIByPaypalPaymentID(paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentID());
            this.paypalSaleID = this.getSaleIDFromPaypalPaymentObjectAPIResponse(this.paypalPaymentObjectFromPaypalAPIAfterExecutionOfThePayment);

            /**
             * Verify Paypal Payment was successful and state is set to 'approved'
             */
            this.verifyOriginalPaypalPaymentIsExecutedSuccessfully(this.paypalPaymentObjectFromPaypalAPIAfterExecutionOfThePayment);

            System.out.println("**************************************************************************************************");
            System.out.println("PAYPAL API CALL TO GET PAYMENT: ");
            System.out.println("PAYPAL API RESPONSE: ");
            System.out.println(this.paypalPaymentObjectFromPaypalAPIAfterExecutionOfThePayment.toJSON());
            System.out.println("**************************************************************************************************");
            System.out.println("SaleID: " + this.paypalSaleID);
            System.out.println("**************************************************************************************************");


            /**
             * Create the entities to be updated/inserted after a Successful paypal payment
             */
              /* Order Status */
            // TODO Create a generic Order Status Service depending on
            //** Order Status Code Ref
            OrderStatusCodes orderStatusCodeRef = this.orderStatusCodesDAO.getSingleOrderStatusCodeRefByStatusID(ORDER_STATUS_CODE_TO_BE_ASSIGNED_TO_ORDER_PAID);
            this.orderEntityCreatorService.checkIfOrderStatusCodeRefExists(orderStatusCodeRef);


            Order orderToBeUpdated = this.localPaymentPaypalEntityFromOwrServer.getOrder();
            orderToBeUpdated.setOrderStatusCode(orderStatusCodeRef);
            orderToBeUpdated.setDateOrderPaid(new Date());



            // Construct the PaymentDeposit Entity
            PaymentDeposit paymentDepositAfterSuccessfulPaypalPayment = new PaymentDeposit();
            paymentDepositAfterSuccessfulPaypalPayment.setCreatedAt(new Date());
            paymentDepositAfterSuccessfulPaypalPayment.setPaymentDepositID(this.orderEntityCreatorService.paymentDepositIDUniqueGenerator());
            paymentDepositAfterSuccessfulPaypalPayment.setAmmount(this.localPaymentPaypalEntityFromOwrServer.getOrder().getTotalOrderPrice());
            paymentDepositAfterSuccessfulPaypalPayment.setProcessorMessage(this.paypalSaleID);
            paymentDepositAfterSuccessfulPaypalPayment.setPaymentApproval(this.localPaymentPaypalEntityFromOwrServer.getPaymentApproval());
            paymentDepositAfterSuccessfulPaypalPayment.setStatus(PaymentStatusEnum.SUCCEEDED);

            // set the new PaymentDeposit entity to PaymentApproval entity
            PaymentApproval paymentApprovalAfterSuccessfulPaypalPayment = this.localPaymentPaypalEntityFromOwrServer.getPaymentApproval();
            paymentApprovalAfterSuccessfulPaypalPayment.setPaymentDeposit(paymentDepositAfterSuccessfulPaypalPayment);
            paymentApprovalAfterSuccessfulPaypalPayment.setUpdatedAt(new Date());

            paymentDepositAfterSuccessfulPaypalPayment.setPaymentApproval(paymentApprovalAfterSuccessfulPaypalPayment);

            // set the updated PaymentApproval entity to PaymentPaypal entity
            this.localPaymentPaypalEntityFromOwrServer.setPaypalTransactionNo(this.paypalSaleID);
            this.localPaymentPaypalEntityFromOwrServer.setPaymentApproval(paymentApprovalAfterSuccessfulPaypalPayment);
            this.localPaymentPaypalEntityFromOwrServer.setOrder(orderToBeUpdated);
            this.localPaymentPaypalEntityFromOwrServer.setUpdatedAt(new Date());
            orderToBeUpdated.setOrderPayment(this.localPaymentPaypalEntityFromOwrServer);




            // Update the PaymentPaypal entity to database
            // We get an OrderID as response from DAO
            // We use the OrderID to be returned to the controller so we can redirect end user browser
            // from paypal page to our frontend web page in a link containing the orderID as url section
            // so the angular frontend can control the newly created order
            String updatedOrderID = this.completePaymentPaypalDAO.saveTheDepositInformation(this.localPaymentPaypalEntityFromOwrServer);

            orderToBeReturned = new OrderDTO();
            orderToBeReturned.setOrderID(updatedOrderID);

        }
        return orderToBeReturned;
    }


    private void checkPayment(PaymentPaypal paymentPaypalToBeChecked, String paypalPaymentID) throws AppException
    {
        // check if there is a payment with the same paypalPaymentID(the one the Paypal Sends as
        // response to our PAYPAL API Set_Payment request
        this.checkIfPaymentExistsToOurSystem(paymentPaypalToBeChecked, paypalPaymentID);

        // Check for existence of payment approval
        this.checkIfPaymentApprovalEntityExists(paymentPaypalToBeChecked.getPaymentApproval());

        // Check if succeeded so we can continue
        this.checkPaymentApprovalStatusIsSucceeded(paymentPaypalToBeChecked.getPaymentApproval());

        // Check payment is never paid again
        this.checkPaymentIsNotPaidYet(paymentPaypalToBeChecked.getPaymentApproval());
    }

}

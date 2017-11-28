package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.payment_processor_paypal;

import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RelatedResources;
import com.paypal.api.payments.Transaction;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dto.PaypalDataObjectReceivedFromFrontEndDTO;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;
import org.springframework.http.HttpStatus;

/**
 * Created by zorzis on 10/11/2017.
 */
public interface IPaymentPaypalProcessorCheckersService
{
    public final static String ORIGINAL_PAYPAL_PAYMENT_STATE_CREATED = "created";
    public final static String ORIGINAL_PAYPAL_PAYMENT_STATE_APPROVED = "approved";


    /**
     * verifies that state of Payment Object from Paypal API Call is
     * set to 'created'
     * @param paymentToBeVerified
     */
    public default void verifyStateIsSetToCreated(Payment paymentToBeVerified) throws AppException
    {
        // If paypal payment state is not equal to "created" then abort
        if(!paymentToBeVerified.getState().equals(ORIGINAL_PAYPAL_PAYMENT_STATE_CREATED))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Paypal Payment State is not set to [created]!");
            sb.append("Payment process can not be continued.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            appException.setAppErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Paypal Payment state not marked as [created].Payment cannot be processed.Please contact developers!");

            throw appException;
        }
    }



    public default void checkIfPaypalDataObjectReceivedFromFrontEndDTOIsAcceptable(PaypalDataObjectReceivedFromFrontEndDTO paypalDataObjectReceivedFromFrontEndDTO) throws AppException
    {
        if(paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentID() == null || paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentID().isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Paypal API Response  [paymentID] cannot be empty!");
            sb.append("Malformed request to our system for Paypal Payment Confirmation.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Malformed request to our system for Paypal Payment Confirmation.The request must contain these variables: " +
                    "[clientEmail] - [paypalPaymentID] - [paypalPaymentPayerID] so we can confirm the payment and proccess the request to Paypal API Do_Payment " +
                    "in order to complete the Payment to Paypal");
            throw appException;
        }
        if(paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentPayerID() == null || paypalDataObjectReceivedFromFrontEndDTO.getPaypalPaymentPayerID().isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Paypal API Response [payerID] cannot be empty!");
            sb.append("Malformed request to our system for Paypal Payment Confirmation.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Malformed request to our system for Paypal Payment Confirmation.The request must contain these variables: " +
                    "[clientEmail] - [paypalPaymentID] - [paypalPaymentPayerID] so we can confirm the payment and proccess the request to Paypal API Do_Payment " +
                    "in order to complete the Payment to Paypal");
            throw appException;
        }
    }


    public default void checkIfPaymentExistsToOurSystem(PaymentPaypal paymentPaypalToBeChecked, String paypalOriginalPaymentID) throws AppException
    {
        if(paymentPaypalToBeChecked == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment with Paypal Not Found!");
            sb.append(" ");
            sb.append("Paypal Payment ID");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(paypalOriginalPaymentID);
            sb.append("]");
            sb.append(" ");
            sb.append("is not registered to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Paypal Payment entity with specific credentials not found at the Database");
            throw appException;
        }
    }


    public default void checkIfPaymentApprovalEntityExists(PaymentApproval paymentApproval)
    {
        if(paymentApproval == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment is not assigned to any Payment Approval!");
            sb.append("Payment process can not be continued.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            appException.setAppErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("No payment approval assigned to Paypal Payment.Please contact developers!");

            throw appException;
        }
    }

    public default void checkPaymentApprovalStatusIsSucceeded(PaymentApproval paymentApproval)
    {
        if(!paymentApproval.getStatus().equals(PaymentStatusEnum.SUCCEEDED))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment Approval Status is not marked as SUCCEEDED!");
            sb.append("Payment process can not be continued.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            appException.setAppErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment approval status not marked as Succeeded.Payment cannot be processed.Please contact developers!");

            throw appException;
        }
    }


    public default void checkPaymentIsNotPaidYet(PaymentApproval paymentApproval)
    {
        if(paymentApproval.getPaymentDeposit() != null && paymentApproval.getPaymentDeposit().getStatus().equals(PaymentStatusEnum.SUCCEEDED))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("It seems your order is already Paid!");
            sb.append("Payment process can not be continued.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            appException.setAppErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment is already paid.Payment cannot be processed.Please contact developers!");

            throw appException;
        }
    }

    public default String getSaleIDFromPaypalPaymentObjectAPIResponse(Payment paypalGetPaymentObjectResponse)
    {
        String saleID = null;

        if(paypalGetPaymentObjectResponse.getTransactions().size() == 1)
        {
            for(Transaction transaction : paypalGetPaymentObjectResponse.getTransactions())
            {
                if(transaction.getRelatedResources().size() == 1)
                {
                    for(RelatedResources relatedResources : transaction.getRelatedResources())
                    {
                        saleID = relatedResources.getSale().getId();
                    }
                }
            }
        }

        return saleID;
    }


    public default void verifyOriginalPaypalPaymentIsExecutedSuccessfully(Payment paymentClaimsToBeSuccessfull)
    {
        if(!paymentClaimsToBeSuccessfull.getState().equals(ORIGINAL_PAYPAL_PAYMENT_STATE_APPROVED))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Paypal Payment State after execution of payment is not set to [approved]!");
            sb.append("Payment process can not be continued.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            appException.setAppErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Paypal Payment state after execution of payment is not marked as [approved].Payment cannot be processed.Please contact developers!");

            throw appException;
        }
    }






}

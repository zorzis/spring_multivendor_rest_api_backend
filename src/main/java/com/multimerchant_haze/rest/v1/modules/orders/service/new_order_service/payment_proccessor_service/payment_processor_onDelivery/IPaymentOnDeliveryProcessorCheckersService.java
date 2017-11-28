package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.payment_processor_onDelivery;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;
import org.springframework.http.HttpStatus;

/**
 * Created by zorzis on 10/13/2017.
 */
public interface IPaymentOnDeliveryProcessorCheckersService
{

    public default void checkIfPaymentExistsToOurSystem(PaymentOnDelivery paymentOnDelivery, String orderID) throws AppException
    {
        if(paymentOnDelivery == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Completing OnDelivery Order process Internal Error Occured!");
            sb.append(" ");
            sb.append("Order with id");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(orderID);
            sb.append("]");
            sb.append(" ");
            sb.append("is not registered to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Paypal Payment entity searched by Order.orderID not found at the Database.If error continues contact developers.");
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
}

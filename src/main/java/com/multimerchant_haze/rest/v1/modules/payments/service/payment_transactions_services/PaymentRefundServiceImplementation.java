package com.multimerchant_haze.rest.v1.modules.payments.service.payment_transactions_services;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dao.payment_transactions_dao.PaymentRefundDAO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_transactions_dto.PaymentRefundDTO;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentRefund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
@Service
public class PaymentRefundServiceImplementation implements PaymentRefundService
{
    @Autowired
    PaymentRefundDAO paymentRefundDAO;


    @Override
    public PaymentRefundDTO getPaymentRefundDTOByPaymentRefundID(String paymentRefundID) throws AppException
    {
        PaymentRefund paymentRefundByPaymentRefundID = this.paymentRefundDAO.getPaymentRefundByID(paymentRefundID);
        PaymentRefundDTO paymentRefundDTO = this.verifyPaymentRefundExists(paymentRefundByPaymentRefundID,
                "PaymentRefundID", paymentRefundID);
        return paymentRefundDTO;
    }

    @Override
    public List<PaymentRefundDTO> getAllPaymentsRefunds() throws AppException
    {
        List<PaymentRefundDTO> paymentRefundDTOS = new ArrayList<>(0);

        List<PaymentRefund> paymentRefundEntities;

        paymentRefundEntities = this.paymentRefundDAO.getPaymentsRefunds();

        for(PaymentRefund paymentRefundEntity : paymentRefundEntities)
        {
            paymentRefundDTOS.add(new PaymentRefundDTO(paymentRefundEntity));
        }

        return paymentRefundDTOS;
    }


    private PaymentRefundDTO verifyPaymentRefundExists(PaymentRefund paymentRefundToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(paymentRefundToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment Refund Not Found!");
            sb.append(" ");
            sb.append(dataKeyToBeChecked);
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(dataValue);
            sb.append("]");
            sb.append(" ");
            sb.append("Not Found to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment Refund entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            return new PaymentRefundDTO(paymentRefundToBeCheckedIfExists);
        }
    }

}

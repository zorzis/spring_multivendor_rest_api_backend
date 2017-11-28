package com.multimerchant_haze.rest.v1.modules.payments.service.payment_transactions_services;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_transactions_dto.PaymentDepositDTO;
import com.multimerchant_haze.rest.v1.modules.payments.dao.payment_transactions_dao.PaymentDepositDAO;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentDeposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
@Service
public class PaymentDepositServiceImplementation implements PaymentDepositService
{

    @Autowired
    PaymentDepositDAO paymentDepositDAO;


    @Override
    public PaymentDepositDTO getPaymentDepositDTOByPaymentDepositID(String paymentDepositID) throws AppException
    {
        PaymentDeposit paymentDepositByPaymentDepositID = this.paymentDepositDAO.getPaymentDepositByID(paymentDepositID);
        PaymentDepositDTO paymentDepositDTO = this.verifyPaymentDepositExists(paymentDepositByPaymentDepositID,
                "PaymentDepositID", paymentDepositID);
        return paymentDepositDTO;
    }

    @Override
    public List<PaymentDepositDTO> getAllPaymentsDeposits() throws AppException
    {
        List<PaymentDepositDTO> paymentDepositDTOS = new ArrayList<>(0);

        List<PaymentDeposit> paymentDepositEntities;

        paymentDepositEntities = this.paymentDepositDAO.getPaymentsDeposits();

        for(PaymentDeposit paymentDepositEntity : paymentDepositEntities)
        {
            paymentDepositDTOS.add(new PaymentDepositDTO(paymentDepositEntity));
        }

        return paymentDepositDTOS;
    }



    private PaymentDepositDTO verifyPaymentDepositExists(PaymentDeposit paymentDepositlToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(paymentDepositlToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment Deposit Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment Deposit entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            return new PaymentDepositDTO(paymentDepositlToBeCheckedIfExists);
        }
    }
}

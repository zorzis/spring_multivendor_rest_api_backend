package com.multimerchant_haze.rest.v1.modules.payments.service.payment_transactions_services;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dao.payment_transactions_dao.PaymentApprovalDAO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_transactions_dto.PaymentApprovalDTO;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
@Service
public class PaymentApprovalServiceImplementation implements PaymentApprovalService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentApprovalServiceImplementation.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    PaymentApprovalDAO paymentApprovalDAO;

    @Override
    public PaymentApprovalDTO getPaymentApprovalDTOByPaymentApprovalID(String paymentApprovalID) throws AppException
    {
        PaymentApproval paymentApprovalByPaymentApprovalID = this.paymentApprovalDAO.getPaymentApprovalByID(paymentApprovalID);
        PaymentApprovalDTO paymentApprovalDTO = this.verifyPaymentApprovalExists(paymentApprovalByPaymentApprovalID,
                "PaymentApprovalID", paymentApprovalID);
        return paymentApprovalDTO;
    }

    @Override
    public List<PaymentApprovalDTO> getAllPaymentsApprovals() throws AppException
    {
        List<PaymentApprovalDTO> paymentApprovalDTOS = new ArrayList<>(0);

        List<PaymentApproval> paymentApprovalEntities;

        paymentApprovalEntities = this.paymentApprovalDAO.getPaymentsApprovals();

        for(PaymentApproval paymentApprovalEntity : paymentApprovalEntities)
        {
            paymentApprovalDTOS.add(new PaymentApprovalDTO(paymentApprovalEntity));
        }

        return paymentApprovalDTOS;
    }


    private PaymentApprovalDTO verifyPaymentApprovalExists(PaymentApproval paymentApprovalToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(paymentApprovalToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment Approval Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment Approval entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            return new PaymentApprovalDTO(paymentApprovalToBeCheckedIfExists);
        }
    }

}

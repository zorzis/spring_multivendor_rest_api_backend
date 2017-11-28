package com.multimerchant_haze.rest.v1.modules.payments.service.payment_types_services;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dao.payment_types_dao.PaymentPaypalDAO;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto.PaymentPaypalDTO;
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
public class PaymentPaypalServiceImplementation implements PaymentPaypalService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentPaypalServiceImplementation.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    PaymentPaypalDAO paymentPaypalDAO;

    @Override
    public PaymentPaypalDTO getPaymentPaypalDTOByPaymentPaypalID(String paymentPaypalID) throws AppException
    {
        PaymentPaypal paymentPaypalByPaymentPaypalID = this.paymentPaypalDAO.getPaymentPaypalByID(paymentPaypalID);
        PaymentPaypalDTO paymentPaypalDTO = this.verifyPaymentPaypalExists(paymentPaypalByPaymentPaypalID,
                "PaymentPaypalID", paymentPaypalID);
        return paymentPaypalDTO;
    }

    @Override
    public List<PaymentPaypalDTO> getAllPaymentsPaypal() throws AppException
    {
        List<PaymentPaypalDTO> paymentPaypalDTOS = new ArrayList<>(0);

        List<PaymentPaypal> paymentPaypalEntities;

        paymentPaypalEntities = this.paymentPaypalDAO.getPaymentsPaypal();

        for(PaymentPaypal paymentPaypalEntity : paymentPaypalEntities)
        {
            paymentPaypalDTOS.add(new PaymentPaypalDTO(paymentPaypalEntity));
        }

        return paymentPaypalDTOS;
    }


    private PaymentPaypalDTO verifyPaymentPaypalExists(PaymentPaypal paymentPaypalToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(paymentPaypalToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment Paypal Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment Paypal entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            return new PaymentPaypalDTO(paymentPaypalToBeCheckedIfExists);
        }
    }

}

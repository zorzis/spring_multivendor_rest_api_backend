package com.multimerchant_haze.rest.v1.modules.payments.service.payment_types_services;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dao.payment_types_dao.PaymentOnDeliveryDAO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto.PaymentOnDeliveryDTO;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;
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
public class PaymentOnDeliveryServiceImplementation implements PaymentOnDeliveryService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentOnDeliveryServiceImplementation.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    PaymentOnDeliveryDAO paymentOnDeliveryDAO;

    @Override
    public PaymentOnDeliveryDTO getPaymentOnDeliveryDTOByPaymentOnDeliveryID(String paymentOnDeliveryID) throws AppException
    {
        PaymentOnDelivery paymentOnDeliveryByPaymentOnDeliveryID = this.paymentOnDeliveryDAO.getPaymentOnDeliveryByID(paymentOnDeliveryID);
        PaymentOnDeliveryDTO paymentOnDeliveryDTO = this.verifyPaymentOnDeliveryExists(paymentOnDeliveryByPaymentOnDeliveryID,
                "PaymentOnDeliveryID", paymentOnDeliveryID);
        return paymentOnDeliveryDTO;
    }

    @Override
    public List<PaymentOnDeliveryDTO> getAllPaymentsOnDelivery() throws AppException
    {
        List<PaymentOnDeliveryDTO> paymentOnDeliveryDTOS = new ArrayList<>(0);

        List<PaymentOnDelivery> paymentOnDeliveryEntities;

        paymentOnDeliveryEntities = this.paymentOnDeliveryDAO.getPaymentsOnDelivery();

        for(PaymentOnDelivery paymentOnDeliveryEntity : paymentOnDeliveryEntities)
        {
            paymentOnDeliveryDTOS.add(new PaymentOnDeliveryDTO(paymentOnDeliveryEntity));
        }

        return paymentOnDeliveryDTOS;
    }

    private PaymentOnDeliveryDTO verifyPaymentOnDeliveryExists(PaymentOnDelivery paymentOnDeliveryToBeCheckedThatExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(paymentOnDeliveryToBeCheckedThatExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment OnDelivery Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment OnDelivery entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            return new PaymentOnDeliveryDTO(paymentOnDeliveryToBeCheckedThatExists);
        }
    }

}
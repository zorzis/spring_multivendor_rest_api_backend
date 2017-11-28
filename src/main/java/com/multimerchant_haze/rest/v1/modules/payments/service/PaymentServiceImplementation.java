package com.multimerchant_haze.rest.v1.modules.payments.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dao.PaymentDAO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto.PaymentDTO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto.PaymentToPaymentDTOService;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerAddressesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorzis on 9/29/2017.
 */
@Service
public class PaymentServiceImplementation implements PaymentService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerAddressesService.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    PaymentDAO paymentDAO;

    @Override
    public PaymentDTO getPaymentByPaymentID(String paymentID) throws AppException
    {

        Payment paymentByPaymentID = this.paymentDAO.getPaymentByID(paymentID);
        PaymentDTO paymentDTO = verifyPaymentExists(paymentByPaymentID,
                "PaymentID", paymentID);
        return paymentDTO;
    }

    @Override
    public List<PaymentDTO> getAllPayments() throws AppException
    {
        List<PaymentDTO> paymentDTOS = new ArrayList<>(0);

        List<Payment> paymentEntities;

        paymentEntities = this.paymentDAO.getPayments();

        for(Payment paymentEntity : paymentEntities)
        {
            PaymentDTO paymentDTO = null;

            paymentDTO = PaymentToPaymentDTOService.convertPaymentEntityToPaymentDTO(paymentEntity);

            paymentDTOS.add(paymentDTO);
        }

        return paymentDTOS;
    }


    private PaymentDTO verifyPaymentExists(Payment paymentToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(paymentToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Payment Not Found!");
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Payment entity with specific credentials not found at the Database.");
            throw appException;
        }
        else
        {
            PaymentDTO paymentDTO = null;
            paymentDTO = PaymentToPaymentDTOService.convertPaymentEntityToPaymentDTO(paymentToBeCheckedIfExists);
            return paymentDTO;
        }
    }



}

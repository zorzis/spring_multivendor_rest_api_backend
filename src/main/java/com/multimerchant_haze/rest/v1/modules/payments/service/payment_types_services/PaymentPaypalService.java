package com.multimerchant_haze.rest.v1.modules.payments.service.payment_types_services;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto.PaymentPaypalDTO;

import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
public interface PaymentPaypalService
{
    public PaymentPaypalDTO getPaymentPaypalDTOByPaymentPaypalID(String paymentPaypalID) throws AppException;

    public List<PaymentPaypalDTO> getAllPaymentsPaypal() throws AppException;
}

package com.multimerchant_haze.rest.v1.modules.payments.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto.PaymentDTO;

import java.util.List;

/**
 * Created by zorzis on 9/29/2017.
 */
public interface PaymentService
{
    public PaymentDTO getPaymentByPaymentID(String paymentID) throws AppException;

    public List<PaymentDTO> getAllPayments() throws AppException;
}

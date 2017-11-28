package com.multimerchant_haze.rest.v1.modules.payments.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dto.PaymentMethodDTO;

import java.util.List;

/**
 * Created by zorzis on 9/8/2017.
 */
public interface PaymentMethodService
{
    public PaymentMethodDTO getPaymentMethodByPaymentMethodName(String paymentMethodName) throws AppException;

    public PaymentMethodDTO getPaymentMethodByPaymentMethodID(String paymentMethodID) throws AppException;

    public List<PaymentMethodDTO> getAllPaymentMethods() throws AppException;

    public String addNewPaymentMethod(PaymentMethodDTO paymentMethodDTO) throws AppException;

    public String deletePaymentMethodByName(PaymentMethodDTO paymentMethodDTO) throws AppException;

    public String deletePaymentMethodByID(PaymentMethodDTO paymentMethodDTO) throws AppException;
}

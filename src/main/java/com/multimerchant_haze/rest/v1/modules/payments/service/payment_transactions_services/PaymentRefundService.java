package com.multimerchant_haze.rest.v1.modules.payments.service.payment_transactions_services;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_transactions_dto.PaymentRefundDTO;

import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
public interface PaymentRefundService
{
    public PaymentRefundDTO getPaymentRefundDTOByPaymentRefundID(String paymentRefundID) throws AppException;

    public List<PaymentRefundDTO> getAllPaymentsRefunds() throws AppException;
}
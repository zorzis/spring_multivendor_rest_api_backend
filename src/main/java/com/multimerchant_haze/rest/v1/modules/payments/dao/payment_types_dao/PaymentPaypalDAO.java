package com.multimerchant_haze.rest.v1.modules.payments.dao.payment_types_dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;

import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
public interface PaymentPaypalDAO
{
    public List<PaymentPaypal> getPaymentsPaypal();

    public PaymentPaypal getPaymentPaypalByID(String paymentPaypalID);
}

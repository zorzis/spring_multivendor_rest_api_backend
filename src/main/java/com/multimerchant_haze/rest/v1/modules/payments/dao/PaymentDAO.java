package com.multimerchant_haze.rest.v1.modules.payments.dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;

import java.util.List;

/**
 * Created by zorzis on 9/29/2017.
 */
public interface PaymentDAO
{
    public List<Payment> getPayments();

    public Payment getPaymentByID(String paymentID);

}

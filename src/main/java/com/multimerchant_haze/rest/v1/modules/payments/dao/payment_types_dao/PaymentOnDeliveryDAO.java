package com.multimerchant_haze.rest.v1.modules.payments.dao.payment_types_dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;

import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
public interface PaymentOnDeliveryDAO
{
    public List<PaymentOnDelivery> getPaymentsOnDelivery();

    public PaymentOnDelivery getPaymentOnDeliveryByID(String paymentOnDeliveryID);
}

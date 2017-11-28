package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;

/**
 * Created by zorzis on 10/13/2017.
 */
public interface CompletePaymentOnDeliveryDAO
{
    public PaymentOnDelivery getOrderByOrderID(String orderID);

    public String saveTheDepositInformation(PaymentOnDelivery paymentOnDelivery);
}

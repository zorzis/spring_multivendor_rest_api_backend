package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;

/**
 * Created by zorzis on 10/6/2017.
 */
public interface CompletePaymentPaypalDAO
{
    public PaymentPaypal getOrderPaymentByOriginalPaypalPaymentID(String paypalPaymentID);

    public String saveTheDepositInformation(PaymentPaypal paymentPaypal);
}

package com.multimerchant_haze.rest.v1.modules.payments.dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;

import java.util.List;

/**
 * Created by zorzis on 9/7/2017.
 */
public interface PaymentMethodDAO
{
    public List<PaymentMethod> getPaymentMethods();

    public PaymentMethod getPaymentMethodByName(String paymentMethodName);

    public PaymentMethod getPaymentMethodByID(String paymentMethodID);

    public String addPaymentMethod(PaymentMethod paymentMethod);

    public String deletePaymentMethod(PaymentMethod paymentMethod);


}

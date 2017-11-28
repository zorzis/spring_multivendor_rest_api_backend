package com.multimerchant_haze.rest.v1.modules.payments.service.paypal_api;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Created by zorzis on 7/24/2017.
 */
public interface PaypalRestAPIService
{

    public Payment setPaypalExpressCheckout(Payment paypalOriginalPaymentObject) throws AppException, PayPalRESTException;

    public Payment executePaypalExpressCheckout(String paymentId, String PayerID) throws AppException, PayPalRESTException;

    public Payment getPaymentFromPaypalAPIByPaypalPaymentID(String originalPaypalPaymentID) throws AppException;

    public Payment createPaypalCheckoutPaymentObject(Order order) throws AppException;

}

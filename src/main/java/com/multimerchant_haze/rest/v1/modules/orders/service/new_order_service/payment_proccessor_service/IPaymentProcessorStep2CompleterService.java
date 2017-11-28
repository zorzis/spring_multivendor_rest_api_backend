package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;

/**
 * Created by zorzis on 10/6/2017.
 */
public interface IPaymentProcessorStep2CompleterService
{
    public final static String ORDER_STATUS_CODE_TO_BE_ASSIGNED_TO_ORDER_PAID = "paid";

    public Object completeOrderPaymentToProcessorService(Object object) throws AppException;
}

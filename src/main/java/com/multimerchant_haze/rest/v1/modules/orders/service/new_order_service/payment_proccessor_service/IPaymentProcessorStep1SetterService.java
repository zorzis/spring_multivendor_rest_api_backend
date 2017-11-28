package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;

/**
 * Created by zorzis on 10/4/2017.
 */
public interface IPaymentProcessorStep1SetterService
{
    public final static String ORDER_STATUS_CODE_TO_BE_ASSIGNED_TO_ORDER_INPROGRESS = "inProgress";

    public Object placeOrder(String shoppingCartID) throws AppException;

    /** Standard PAYPAL Tax Policy Fees from API **/
    public default Float calculateTotalProcessorTax(Float totalOrderPrice)
    {
        Float processorTax;
        processorTax = (float)(((3.4 / 100)*totalOrderPrice) + 0.35);
        return processorTax;
    }

    public default Float calculateTotalOrderPrice(Float subtotalOrderPrice, Float totalProductTax)
    {
        Float totalOrderPrice;
        totalOrderPrice = subtotalOrderPrice + totalProductTax;
        return totalOrderPrice;
    }
}
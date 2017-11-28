package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.OrderStatusCodes;

/**
 * Created by zorzis on 7/18/2017.
 */
public interface OrderStatusCodesDAO
{
    public OrderStatusCodes getSingleOrderStatusCodeRefByStatusID(String statusID);
}

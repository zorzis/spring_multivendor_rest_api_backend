package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.Order;

/**
 * Created by zorzis on 10/5/2017.
 */
public interface PlaceOrderDAO
{
    public String placeNewOrderByOrderEntity(Order order);

}

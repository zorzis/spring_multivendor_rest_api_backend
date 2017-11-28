package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.Order;

import java.util.List;

/**
 * Created by zorzis on 7/11/2017.
 */
public interface ClientOrdersDAO
{
    public List<Order> getClientOrders(String clientEmail);


}

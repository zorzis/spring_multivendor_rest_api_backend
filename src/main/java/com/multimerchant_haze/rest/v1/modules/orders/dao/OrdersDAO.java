package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderHasClientHasProducer;

/**
 * Created by zorzis on 7/10/2017.
 */
public interface OrdersDAO
{
    public Order getOrderByOrderIDOnlyFromOrdersTable(String orderID);

    public OrderHasClientHasProducer getOrderByOrderIDByClientIDByProducerID(String orderID, String clientID, String producerID);

    public Order getSingleOrderFullEntitiesByOrderID(String orderID);

    public Order getOrderByShoppingCartID(String shoppingCartID);
}

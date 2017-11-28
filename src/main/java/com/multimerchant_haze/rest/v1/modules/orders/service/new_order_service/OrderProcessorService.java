package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartDTO;

/**
 * Created by zorzis on 10/3/2017.
 */
public interface OrderProcessorService
{
    public String saveShoppingCartToDatabase(ShoppingCartDTO shoppingCartDTO) throws AppException;

    public void sendOrderConfirmationEmailToClient(OrderDTO orderDTO) throws AppException;
}

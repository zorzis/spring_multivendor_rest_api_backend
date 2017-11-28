package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCart;

/**
 * Created by zorzis on 11/8/2017.
 */
public interface ShoppingCartDAO
{
    public ShoppingCart getShoppingCartByID(String shoppingCartID);

    public String persistShoppingCart(ShoppingCart shoppingCart);
}

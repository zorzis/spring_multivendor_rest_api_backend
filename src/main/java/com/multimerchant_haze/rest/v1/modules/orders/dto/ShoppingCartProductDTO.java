package com.multimerchant_haze.rest.v1.modules.orders.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCartProduct;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;

/**
 * Created by zorzis on 11/7/2017.
 */
public class ShoppingCartProductDTO
{
    @JsonView(JsonACL.ClientsView.class)
    private Long id;

    @JsonView(JsonACL.ClientsView.class)
    private String productID;

    @JsonView(JsonACL.ClientsView.class)
    private Float orderProductQuantity;

    public ShoppingCartProductDTO()
    {

    }

    public ShoppingCartProductDTO(ShoppingCartProduct shoppingCartProduct)
    {
        this.id = shoppingCartProduct.getId();
        this.productID = shoppingCartProduct.getProductID();
        this.orderProductQuantity = shoppingCartProduct.getOrderProductQuantity();
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getProductID()
    {
        return productID;
    }

    public void setProductID(String productID)
    {
        this.productID = productID;
    }

    public Float getOrderProductQuantity()
    {
        return orderProductQuantity;
    }

    public void setOrderProductQuantity(Float orderProductQuantity)
    {
        this.orderProductQuantity = orderProductQuantity;
    }

}

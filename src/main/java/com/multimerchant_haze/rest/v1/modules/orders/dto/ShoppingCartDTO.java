package com.multimerchant_haze.rest.v1.modules.orders.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCart;
import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCartProduct;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 10/16/2017.
 */
public class ShoppingCartDTO
{
    @JsonView(JsonACL.ClientsView.class)
    private String shoppingCartID;

    @JsonView(JsonACL.ClientsView.class)
    private String customerEmail;

    @JsonView(JsonACL.ClientsView.class)
    private String producerID;

    @JsonView(JsonACL.ClientsView.class)
    private Long clientAddressID;

    @JsonView(JsonACL.ClientsView.class)
    private String paymentMethodID;

    @JsonView(JsonACL.ClientsView.class)
    private Set<ShoppingCartProductDTO> shoppingCartProducts = new HashSet<>(0);

    @JsonView(JsonACL.ClientsView.class)
    private OrderDTO order;


    public ShoppingCartDTO()
    {

    }


    public ShoppingCartDTO(ShoppingCart shoppingCart)
    {
        this.shoppingCartID = shoppingCart.getShoppingCartID();
        this.customerEmail = shoppingCart.getCustomerEmail();
        this.producerID = shoppingCart.getProducerID();
        this.clientAddressID = shoppingCart.getClientAddressID();
        this.paymentMethodID = shoppingCart.getPaymentMethodID();
        this.shoppingCartProducts = this.mapShoppingCartProductEntitiesToDTOS(shoppingCart.getShoppingCartProducts());
    }


    private Set<ShoppingCartProductDTO> mapShoppingCartProductEntitiesToDTOS(Set<ShoppingCartProduct> shoppingCartProductEntities)
    {
        Set<ShoppingCartProductDTO> shoppingCartProductDTOSet = new HashSet<>(0);

        if(shoppingCartProductEntities == null || shoppingCartProductEntities.isEmpty())
        {
            return shoppingCartProductDTOSet;
        }
        else
        {
            for(ShoppingCartProduct shoppingCartProduct: shoppingCartProductEntities)
            {
                ShoppingCartProductDTO shoppingCartProductDTO = new ShoppingCartProductDTO(shoppingCartProduct);
                shoppingCartProductDTOSet.add(shoppingCartProductDTO);
            }
            return shoppingCartProductDTOSet;
        }
    }


    public String getShoppingCartID()
    {
        return shoppingCartID;
    }

    public void setShoppingCartID(String shoppingCartID)
    {
        this.shoppingCartID = shoppingCartID;
    }

    public String getCustomerEmail()
    {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail)
    {
        this.customerEmail = customerEmail;
    }

    public String getProducerID()
    {
        return producerID;
    }

    public void setProducerID(String producerID)
    {
        this.producerID = producerID;
    }

    public Long getClientAddressID()
    {
        return clientAddressID;
    }

    public void setClientAddressID(Long clientAddressID)
    {
        this.clientAddressID = clientAddressID;
    }

    public String getPaymentMethodID()
    {
        return paymentMethodID;
    }

    public void setPaymentMethodID(String paymentMethodID)
    {
        this.paymentMethodID = paymentMethodID;
    }

    public Set<ShoppingCartProductDTO> getShoppingCartProducts()
    {
        return shoppingCartProducts;
    }

    public void setShoppingCartProducts(Set<ShoppingCartProductDTO> shoppingCartProducts)
    {
        this.shoppingCartProducts = shoppingCartProducts;
    }

    public OrderDTO getOrder()
    {
        return order;
    }

    public void setOrder(OrderDTO order)
    {
        this.order = order;
    }
}

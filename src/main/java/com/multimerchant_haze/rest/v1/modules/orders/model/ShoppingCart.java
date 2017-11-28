package com.multimerchant_haze.rest.v1.modules.orders.model;

import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartProductDTO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 11/8/2017.
 */
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart implements Serializable
{
    @Id
    @Column(name = "shopping_cart_id", nullable = false)
    private String shoppingCartID;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "producer_id", nullable = false)
    private String producerID;

    @Column(name = "client_address_id", nullable = false)
    private Long clientAddressID;

    @Column(name = "payment_method_id", nullable = false)
    private String paymentMethodID;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingCart")
    private Set<ShoppingCartProduct> shoppingCartProducts = new HashSet<>(0);

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "shoppingCart")
    private Order order;


    public ShoppingCart()
    {

    }

    public ShoppingCart(ShoppingCartDTO shoppingCartDTO)
    {
        this.customerEmail = shoppingCartDTO.getCustomerEmail();
        this.producerID = shoppingCartDTO.getProducerID();
        this.clientAddressID = shoppingCartDTO.getClientAddressID();
        this.paymentMethodID = shoppingCartDTO.getPaymentMethodID();
        this.shoppingCartProducts = this.mapShoppingCartDTOProductsToShoppingCartEntityProducts(shoppingCartDTO.getShoppingCartProducts());
    }

    public Set<ShoppingCartProduct> mapShoppingCartDTOProductsToShoppingCartEntityProducts(Set<ShoppingCartProductDTO> shoppingCartProductDTOS)
    {
        Set<ShoppingCartProduct> shoppingCartProducts = null;

        if(shoppingCartProductDTOS == null || shoppingCartProductDTOS.isEmpty())
        {
            shoppingCartProducts = new HashSet<>(0);
        }
        else
        {
            for(ShoppingCartProductDTO shoppingCartProductDTO: shoppingCartProductDTOS)
            {
                ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct(shoppingCartProductDTO);
                shoppingCartProducts.add(shoppingCartProduct);
            }
        }

        return shoppingCartProducts;
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

    public Set<ShoppingCartProduct> getShoppingCartProducts()
    {
        return shoppingCartProducts;
    }

    public void setShoppingCartProducts(Set<ShoppingCartProduct> shoppingCartProducts)
    {
        this.shoppingCartProducts = shoppingCartProducts;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }
}

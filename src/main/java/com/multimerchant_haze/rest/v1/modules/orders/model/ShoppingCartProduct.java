package com.multimerchant_haze.rest.v1.modules.orders.model;

import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartProductDTO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zorzis on 11/8/2017.
 */
@Entity
@Table(name = "shopping_carts_products")
public class ShoppingCartProduct implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_id")
    private String productID;

    @Column(name = "product_quantity")
    private Float orderProductQuantity;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    public ShoppingCartProduct()
    {

    }

    public ShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO)
    {
        this.productID = shoppingCartProductDTO.getProductID();
        this.orderProductQuantity = shoppingCartProductDTO.getOrderProductQuantity();
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


    public ShoppingCart getShoppingCart()
    {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart)
    {
        this.shoppingCart = shoppingCart;
    }
}

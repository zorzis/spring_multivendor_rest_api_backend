package com.multimerchant_haze.rest.v1.modules.orders.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zorzis on 7/8/2017.
 */
@Entity
@Table(name = "orders_have_products")
public class OrderHasProducts implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_have_products_id")
    private Long orderHasProductsID;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Order order;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "order_product_ID", referencedColumnName = "order_product_id")
    private OrderProduct orderProduct;



    public OrderHasProducts()
    {

    }



    public Long getOrderHasProductsID()
    {
        return orderHasProductsID;
    }

    public void setOrderHasProductsID(Long orderHasProductsID)
    {
        this.orderHasProductsID = orderHasProductsID;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public OrderProduct getOrderProduct()
    {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct)
    {
        this.orderProduct = orderProduct;
    }



    @Override
    public String toString()
    {
        return null;
    }
}

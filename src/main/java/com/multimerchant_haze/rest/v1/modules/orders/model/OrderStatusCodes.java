package com.multimerchant_haze.rest.v1.modules.orders.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 7/8/2017.
 */
@Entity
@Table(name = "orders_status_codes_ref")
public class OrderStatusCodes implements Serializable
{
    @Id
    @Column(name = "order_status_code", nullable = false)
    private String orderStatusCode;

    @Column(name = "order_status_code_description", nullable = false)
    private String orderStatusCodeDescription;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderStatusCode")
    private Set<Order> orderSet = new HashSet<>(0);


    public OrderStatusCodes()
    {

    }

    public String getOrderStatusCode()
    {
        return orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode)
    {
        this.orderStatusCode = orderStatusCode;
    }

    public String getOrderStatusCodeDescription()
    {
        return orderStatusCodeDescription;
    }

    public void setOrderStatusCodeDescription(String orderStatusCodeDescription)
    {
        this.orderStatusCodeDescription = orderStatusCodeDescription;
    }

    public Set<Order> getOrderSet()
    {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet)
    {
        this.orderSet = orderSet;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderStatusCode Entity");
        sb.append(" ");
        sb.append("[");
        sb.append("Order Status Code");
        sb.append(" ");
        sb.append(this.orderStatusCode);
        sb.append(" | ");
        sb.append("Order Status Code Description");
        sb.append(this.orderStatusCodeDescription);
        sb.append("]");
        return sb.toString();
    }
}

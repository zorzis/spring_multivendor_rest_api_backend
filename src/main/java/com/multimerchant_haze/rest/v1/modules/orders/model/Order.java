package com.multimerchant_haze.rest.v1.modules.orders.model;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 7/8/2017.
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable
{
    @Id
    @Column(name = "orderID", nullable = false)
    private String orderID;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderHasProducts> orderHasProducts = new HashSet<>(0);

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private OrderHasClientHasProducer orderHasClientHasProducer;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private OrderClientDetails orderClientDetails;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private OrderProducerDetails orderProducerDetails;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private OrderClientAddressDetails orderClientAddressDetails;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private OrderProducerAddressDetails orderProducerAddressDetails;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "order_status_code", referencedColumnName = "order_status_code")
    private OrderStatusCodes orderStatusCode;

    @Column(name = "date_order_placed", nullable = false)
    private Date dateOrderPlaced;

    @Column(name = "date_order_paid")
    private Date dateOrderPaid;

    @Column(name = "other_order_details")
    private String otherOrderDetails;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "paymentID", referencedColumnName = "paymentID")
    private Payment orderPayment;

    @Column(name = "total_order_price", nullable = false)
    private Float totalOrderPrice;

    @Column(name = "subtotal_order_price", nullable = false)
    private Float subTotalOrderPrice;

    @Column(name = "total_products_tax", nullable = false)
    private Float totalProductsTax;

    @Column(name = "total_processor_tax", nullable = true)
    private Float totalProcessorTax;

    @Column(name = "total_shipping_tax", nullable = true)
    private Float totalShippingTax;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    public Order()
    {

    }


    public Set<OrderProduct> getOrderProductsFromOrderHasProductsEntities()
    {
        Set<OrderProduct> orderProducts = new HashSet<>(0);
        if(this.orderHasProducts == null)
        {
            return orderProducts;
        }
        else
        {
            for (OrderHasProducts orderHasProducts : this.orderHasProducts)
            {
                orderProducts.add(orderHasProducts.getOrderProduct());
            }
            return orderProducts;
        }
    }

    public String getOrderID()
    {
        return orderID;
    }

    public void setOrderID(String orderID)
    {
        this.orderID = orderID;
    }

    public Set<OrderHasProducts> getOrderHasProducts()
    {
        return orderHasProducts;
    }

    public void setOrderHasProducts(Set<OrderHasProducts> orderHasProducts)
    {
        this.orderHasProducts = orderHasProducts;
    }



    public OrderStatusCodes getOrderStatusCode()
    {
        return orderStatusCode;
    }

    public void setOrderStatusCode(OrderStatusCodes orderStatusCode)
    {
        this.orderStatusCode = orderStatusCode;
    }

    public Date getDateOrderPlaced()
    {
        return dateOrderPlaced;
    }

    public void setDateOrderPlaced(Date dateOrderPlaced)
    {
        this.dateOrderPlaced = dateOrderPlaced;
    }

    public Date getDateOrderPaid()
    {
        return dateOrderPaid;
    }

    public void setDateOrderPaid(Date dateOrderPaid)
    {
        this.dateOrderPaid = dateOrderPaid;
    }

    public Float getTotalOrderPrice()
    {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(Float totalOrderPrice)
    {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getOtherOrderDetails()
    {
        return otherOrderDetails;
    }

    public void setOtherOrderDetails(String otherOrderDetails)
    {
        this.otherOrderDetails = otherOrderDetails;
    }

    public Payment getOrderPayment()
    {
        return this.orderPayment;
    }

    public void setOrderPayment(Payment orderPayment)
    {
        this.orderPayment = orderPayment;
    }

    public OrderHasClientHasProducer getOrderHasClientHasProducer()
    {
        return orderHasClientHasProducer;
    }

    public void setOrderHasClientHasProducer(OrderHasClientHasProducer orderHasClientHasProducer)
    {
        this.orderHasClientHasProducer = orderHasClientHasProducer;
    }

    public OrderClientDetails getOrderClientDetails()
    {
        return orderClientDetails;
    }

    public void setOrderClientDetails(OrderClientDetails orderClientDetails)
    {
        this.orderClientDetails = orderClientDetails;
    }

    public OrderProducerDetails getOrderProducerDetails()
    {
        return orderProducerDetails;
    }

    public void setOrderProducerDetails(OrderProducerDetails orderProducerDetails)
    {
        this.orderProducerDetails = orderProducerDetails;
    }


    public OrderClientAddressDetails getOrderClientAddressDetails()
    {
        return orderClientAddressDetails;
    }

    public void setOrderClientAddressDetails(OrderClientAddressDetails orderClientAddressDetails)
    {
        this.orderClientAddressDetails = orderClientAddressDetails;
    }

    public OrderProducerAddressDetails getOrderProducerAddressDetails()
    {
        return orderProducerAddressDetails;
    }

    public void setOrderProducerAddressDetails(OrderProducerAddressDetails orderProducerAddressDetails)
    {
        this.orderProducerAddressDetails = orderProducerAddressDetails;
    }


    public Float getTotalProductsTax()
    {
        return totalProductsTax;
    }

    public void setTotalProductsTax(Float totalProductsTax)
    {
        this.totalProductsTax = totalProductsTax;
    }

    public Float getTotalProcessorTax()
    {
        return totalProcessorTax;
    }

    public void setTotalProcessorTax(Float totalProcessorTax)
    {
        this.totalProcessorTax = totalProcessorTax;
    }

    public Float getTotalShippingTax()
    {
        return totalShippingTax;
    }

    public void setTotalShippingTax(Float totalShippingTax)
    {
        this.totalShippingTax = totalShippingTax;
    }


    public Float getSubTotalOrderPrice()
    {
        return subTotalOrderPrice;
    }

    public void setSubTotalOrderPrice(Float subTotalOrderPrice)
    {
        this.subTotalOrderPrice = subTotalOrderPrice;
    }

    public ShoppingCart getShoppingCart()
    {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart)
    {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderDTO: ");
        sb.append(" ");
        sb.append("[");
        sb.append("OrderID: ");
        sb.append(" ");
        sb.append(this.orderID);
        sb.append(" | ");
        sb.append("Order Status Code: ");
        sb.append(this.orderStatusCode.toString());
        sb.append(" | ");
        sb.append("Date order place: ");
        sb.append(this.dateOrderPlaced);
        sb.append(" | ");
        sb.append("Date order paid: ");
        sb.append(this.dateOrderPaid);
        sb.append(" | ");
        sb.append("Total Order Price: ");
        sb.append(this.totalOrderPrice);
        sb.append(" | ");
        sb.append("SubTotal Order Price: ");
        sb.append(this.subTotalOrderPrice);
        sb.append(" | ");
        sb.append("Total Products Tax: ");
        sb.append(this.totalProductsTax);
        sb.append(" | ");
        sb.append("Total Shipping Tax: ");
        sb.append(this.totalShippingTax);
        sb.append(" | ");
        sb.append("Total Processor Tax: ");
        sb.append(this.totalProcessorTax);
        sb.append(" | ");
        sb.append("Other Order Details: ");
        sb.append(this.otherOrderDetails);
        sb.append(" | ");
        sb.append("Order Payment: ");
        sb.append(this.orderPayment.toString());
        sb.append("]");
        return sb.toString();
    }
}
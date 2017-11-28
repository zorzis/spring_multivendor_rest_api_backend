package com.multimerchant_haze.rest.v1.modules.orders.model;

import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 7/14/2017.
 */
@Entity
@Table(name = "order_producer_details")
public class OrderProducerDetails implements Serializable
{
    @Id
    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Order order;

    @Column(name = "producerID")
    private String producerID;

    @Column(name = "order_producer_first_name")
    private String orderProducerFirstName;

    @Column(name = "order_producer_last_name")
    private String orderProducerLastName;

    @Column(name = "order_producer_email")
    private String orderProducerEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_producer_gender")
    private Gender orderProducerGender;

    @Column(name = "order_producer_birth_date")
    private Date orderProducerBirthDate;



    public OrderProducerDetails()
    {

    }

    public OrderProducerDetails(ProducerDTO producerDTO)
    {
        this.producerID = producerDTO.getProducerID();
        this.orderProducerFirstName = producerDTO.getFirstName();
        this.orderProducerLastName = producerDTO.getLastName();
        this.orderProducerEmail = producerDTO.getEmail();
        this.orderProducerGender = producerDTO.getGender();
        this.orderProducerBirthDate = producerDTO.getBirthDate();
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public String getProducerID()
    {
        return producerID;
    }

    public void setProducerID(String producerID)
    {
        this.producerID = producerID;
    }

    public String getOrderProducerFirstName()
    {
        return orderProducerFirstName;
    }

    public void setOrderProducerFirstName(String orderProducerFirstName)
    {
        this.orderProducerFirstName = orderProducerFirstName;
    }

    public String getOrderProducerLastName()
    {
        return orderProducerLastName;
    }

    public void setOrderProducerLastName(String orderProducerLastName)
    {
        this.orderProducerLastName = orderProducerLastName;
    }

    public String getOrderProducerEmail()
    {
        return orderProducerEmail;
    }

    public void setOrderProducerEmail(String orderProducerEmail)
    {
        this.orderProducerEmail = orderProducerEmail;
    }

    public Gender getOrderProducerGender()
    {
        return orderProducerGender;
    }

    public void setOrderProducerGender(Gender orderProducerGender)
    {
        this.orderProducerGender = orderProducerGender;
    }

    public Date getOrderProducerBirthDate()
    {
        return orderProducerBirthDate;
    }

    public void setOrderProducerBirthDate(Date orderProducerBirthDate)
    {
        this.orderProducerBirthDate = orderProducerBirthDate;
    }


}
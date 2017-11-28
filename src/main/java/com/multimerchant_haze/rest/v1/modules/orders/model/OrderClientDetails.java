package com.multimerchant_haze.rest.v1.modules.orders.model;


import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 7/14/2017.
 */
@Entity
@Table(name = "order_client_details")
public class OrderClientDetails implements Serializable
{
    @Id
    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Order order;

    @Column(name = "clientID")
    private String clientID;

    @Column(name = "order_client_first_name")
    private String orderClientFirstName;

    @Column(name = "order_client_last_name")
    private String orderClientLastName;

    @Column(name = "order_client_email")
    private String orderClientEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_client_gender")
    private Gender orderClientGender;

    @Column(name = "order_client_birth_date")
    private Date orderClientBirthDate;



    public OrderClientDetails()
    {

    }

    public OrderClientDetails(ClientDTO clientDTO)
    {
        this.clientID = clientDTO.getClientID();
        this.orderClientFirstName = clientDTO.getFirstName();
        this.orderClientLastName = clientDTO.getLastName();
        this.orderClientEmail = clientDTO.getEmail();
        this.orderClientGender = clientDTO.getGender();
        this.orderClientBirthDate = clientDTO.getBirthDate();
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public String getClientID()
    {
        return clientID;
    }

    public void setClientID(String clientID)
    {
        this.clientID = clientID;
    }

    public String getOrderClientFirstName()
    {
        return orderClientFirstName;
    }

    public void setOrderClientFirstName(String orderClientFirstName)
    {
        this.orderClientFirstName = orderClientFirstName;
    }

    public String getOrderClientLastName()
    {
        return orderClientLastName;
    }

    public void setOrderClientLastName(String orderClientLastName)
    {
        this.orderClientLastName = orderClientLastName;
    }

    public String getOrderClientEmail()
    {
        return orderClientEmail;
    }

    public void setOrderClientEmail(String orderClientEmail)
    {
        this.orderClientEmail = orderClientEmail;
    }

    public Gender getOrderClientGender()
    {
        return orderClientGender;
    }

    public void setOrderClientGender(Gender orderClientGender)
    {
        this.orderClientGender = orderClientGender;
    }

    public Date getOrderClientBirthDate()
    {
        return orderClientBirthDate;
    }

    public void setOrderClientBirthDate(Date orderClientBirthDate)
    {
        this.orderClientBirthDate = orderClientBirthDate;
    }

}

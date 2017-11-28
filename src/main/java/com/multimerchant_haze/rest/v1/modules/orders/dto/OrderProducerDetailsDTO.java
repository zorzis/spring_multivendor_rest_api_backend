package com.multimerchant_haze.rest.v1.modules.orders.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateDeserializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderProducerDetails;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;

import java.util.Date;

/**
 * Created by zorzis on 7/14/2017.
 */
public class OrderProducerDetailsDTO
{
    @JsonView(JsonACL.ClientsView.class)
    private String producerID;

    @JsonView(JsonACL.ClientsView.class)
    private String orderProducerFirstName;

    @JsonView(JsonACL.ClientsView.class)
    private String orderProducerLastName;

    @JsonView(JsonACL.ClientsView.class)
    private String orderProducerEmail;

    @JsonView(JsonACL.ClientsView.class)
    private Gender orderProducerGender;

    @JsonSerialize(using = CustomBirthDateSerializer.class)
    @JsonDeserialize(using = CustomBirthDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date orderProducerBirthDate;




    public OrderProducerDetailsDTO()
    {

    }


    public OrderProducerDetailsDTO(OrderProducerDetails orderProducerDetails)
    {
        this.producerID = orderProducerDetails.getProducerID();
        this.orderProducerFirstName = orderProducerDetails.getOrderProducerFirstName();
        this.orderProducerLastName = orderProducerDetails.getOrderProducerLastName();
        this.orderProducerEmail = orderProducerDetails.getOrderProducerEmail();
        this.orderProducerGender = orderProducerDetails.getOrderProducerGender();
        this.orderProducerBirthDate = orderProducerDetails.getOrderProducerBirthDate();
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

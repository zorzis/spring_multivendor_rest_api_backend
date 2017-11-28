package com.multimerchant_haze.rest.v1.modules.orders.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateDeserializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderClientDetails;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;

import java.util.Date;

/**
 * Created by zorzis on 7/14/2017.
 */
public class OrderClientDetailsDTO
{
    @JsonView(JsonACL.ClientsView.class)
    private String clientID;

    @JsonView(JsonACL.ClientsView.class)
    private String orderClientFirstName;

    @JsonView(JsonACL.ClientsView.class)
    private String orderClientLastName;

    @JsonView(JsonACL.ClientsView.class)
    private String orderClientEmail;

    @JsonView(JsonACL.ClientsView.class)
    private Gender orderClientGender;

    @JsonSerialize(using = CustomBirthDateSerializer.class)
    @JsonDeserialize(using = CustomBirthDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date orderClientBirthDate;


    public OrderClientDetailsDTO()
    {

    }

    public OrderClientDetailsDTO(OrderClientDetails orderClientDetails)
    {
        this.clientID = orderClientDetails.getClientID();
        this.orderClientFirstName = orderClientDetails.getOrderClientFirstName();
        this.orderClientLastName = orderClientDetails.getOrderClientLastName();
        this.orderClientEmail = orderClientDetails.getOrderClientEmail();
        this.orderClientGender = orderClientDetails.getOrderClientGender();
        this.orderClientBirthDate = orderClientDetails.getOrderClientBirthDate();
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

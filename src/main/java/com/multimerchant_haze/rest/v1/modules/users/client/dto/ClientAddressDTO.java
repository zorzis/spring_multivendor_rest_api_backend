package com.multimerchant_haze.rest.v1.modules.users.client.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAddress;

import java.util.Date;

/**
 * Created by zorzis on 5/8/2017.
 */
public class ClientAddressDTO
{

    @JsonView(JsonACL.ClientsView.class)
    private Long id;

    @JsonView(JsonACL.ClientsView.class)
    private String street;

    @JsonView(JsonACL.ClientsView.class)
    private String streetNumber;

    @JsonView(JsonACL.ClientsView.class)
    private String city;

    @JsonView(JsonACL.ClientsView.class)
    private String postalCode;

    @JsonView(JsonACL.ClientsView.class)
    private String state;

    @JsonView(JsonACL.ClientsView.class)
    private String country;

    @JsonView(JsonACL.ClientsView.class)
    private String latitude;

    @JsonView(JsonACL.ClientsView.class)
    private String longitude;

    @JsonView(JsonACL.ClientsView.class)
    private String floor;


    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date updatedAt;


    public ClientAddressDTO()
    {

    }


    public ClientAddressDTO(ClientAddress clientAddress)
    {
        this.id = clientAddress.getId();
        this.street = clientAddress.getStreet();
        this.streetNumber = clientAddress.getStreetNumber();
        this.city = clientAddress.getCity();
        this.postalCode = clientAddress.getPostalCode();
        this.state = clientAddress.getState();
        this.country = clientAddress.getCountry();
        this.floor = clientAddress.getFloor();
        this.latitude = clientAddress.getLatitude();
        this.longitude = clientAddress.getLongitude();
        this.createdAt = clientAddress.getCreatedAt();
        this.updatedAt = clientAddress.getUpdatedAt();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getStreetNumber()
    {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber)
    {
        this.streetNumber = streetNumber;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getLatitude()
    {
        return this.latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return this.longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String floor)
    {
        this.floor = floor;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Address DTO");
        sb.append("[");
        sb.append("AutoCreated MySQL id: ");
        sb.append(this.id);
        sb.append(" | ");
        sb.append("Street: ");
        sb.append(this.street);
        sb.append(" | ");
        sb.append("Street Number: ");
        sb.append(this.streetNumber);
        sb.append(" | ");
        sb.append("City: ");
        sb.append(this.city);
        sb.append(" | ");
        sb.append(this.postalCode);
        sb.append("Postal Code: ");
        sb.append(" | ");
        sb.append("State: ");
        sb.append(this.state);
        sb.append(" | ");
        sb.append("Country: ");
        sb.append(this.country);
        sb.append(" | ");
        sb.append("Latitude: ");
        sb.append(this.latitude);
        sb.append(" | ");
        sb.append("Longitude: ");
        sb.append(this.longitude);
        sb.append(" | ");
        sb.append("Floor: ");
        sb.append(this.floor);
        sb.append(" | ");
        sb.append("Created at: ");
        sb.append(this.createdAt);
        sb.append(" | ");
        sb.append("Updated at: ");
        sb.append(this.updatedAt);
        sb.append("]");

        return sb.toString();
    }
}

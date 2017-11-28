package com.multimerchant_haze.rest.v1.modules.users.producer.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAddress;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;

import java.util.Date;

/**
 * Created by zorzis on 3/28/2017.
 */
public class ProducerAddressDTO
{
    private Long id;

    @JsonView(JsonACL.PublicView.class)
    private String street;

    @JsonView(JsonACL.PublicView.class)
    private String streetNumber;

    @JsonView(JsonACL.PublicView.class)
    private String city;

    @JsonView(JsonACL.PublicView.class)
    private String postalCode;

    @JsonView(JsonACL.PublicView.class)
    private String state;

    @JsonView(JsonACL.PublicView.class)
    private String country;

    @JsonView(JsonACL.PublicView.class)
    private String latitude;

    @JsonView(JsonACL.PublicView.class)
    private String longitude;

    @JsonView(JsonACL.PublicView.class)
    private String floor;


    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date updatedAt;


    public ProducerAddressDTO()
    {

    }


    public ProducerAddressDTO(ProducerAddress producerAddress)
    {
        this.id = producerAddress.getId();
        this.street = producerAddress.getStreet();
        this.streetNumber = producerAddress.getStreetNumber();
        this.city = producerAddress.getCity();
        this.postalCode = producerAddress.getPostalCode();
        this.state = producerAddress.getState();
        this.country = producerAddress.getCountry();
        this.floor = producerAddress.getFloor();
        this.latitude = producerAddress.getLatitude();
        this.longitude = producerAddress.getLongitude();
        this.createdAt = producerAddress.getCreatedAt();
        this.updatedAt = producerAddress.getUpdatedAt();

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

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
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
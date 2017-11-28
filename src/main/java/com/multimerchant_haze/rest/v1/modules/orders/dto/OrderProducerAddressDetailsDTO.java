package com.multimerchant_haze.rest.v1.modules.orders.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderProducerAddressDetails;

/**
 * Created by zorzis on 7/15/2017.
 */
public class OrderProducerAddressDetailsDTO
{

    @JsonView(JsonACL.ClientsView.class)
    private String producerID;

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



    public OrderProducerAddressDetailsDTO()
    {

    }


    public OrderProducerAddressDetailsDTO(OrderProducerAddressDetails orderProducerAddressDetails)
    {
        this.producerID = orderProducerAddressDetails.getProducerID();
        this.street = orderProducerAddressDetails.getStreet();
        this.streetNumber = orderProducerAddressDetails.getStreetNumber();
        this.city = orderProducerAddressDetails.getCity();
        this.postalCode = orderProducerAddressDetails.getPostalCode();
        this.state = orderProducerAddressDetails.getState();
        this.country = orderProducerAddressDetails.getCountry();
        this.latitude = orderProducerAddressDetails.getLatitude();
        this.longitude = orderProducerAddressDetails.getLongitude();
        this.floor = orderProducerAddressDetails.getFloor();
    }


    public String getProducerID()
    {
        return producerID;
    }

    public void setProducerID(String clientID)
    {
        this.producerID = clientID;
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
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
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


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Producer Address Details DTO");
        sb.append("[");
        sb.append("ProducerID: ");
        sb.append(this.producerID);
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
        sb.append("]");

        return sb.toString();
    }

}
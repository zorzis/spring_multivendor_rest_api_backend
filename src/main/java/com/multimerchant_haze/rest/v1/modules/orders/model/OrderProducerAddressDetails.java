package com.multimerchant_haze.rest.v1.modules.orders.model;

import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAddressDTO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zorzis on 7/15/2017.
 */
@Entity
@Table(name = "order_producer_address_details")
public class OrderProducerAddressDetails implements Serializable
{
    @Id
    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Order order;

    @Column(name = "producerID")
    private String producerID;

    @Column(name = "street")
    private String street;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "floor")
    private String floor;



    public OrderProducerAddressDetails()
    {

    }


    public OrderProducerAddressDetails(ProducerAddressDTO producerAddressDTO)
    {
        this.street = producerAddressDTO.getStreet();
        this.streetNumber = producerAddressDTO.getStreetNumber();
        this.city = producerAddressDTO.getCity();
        this.postalCode = producerAddressDTO.getPostalCode();
        this.state = producerAddressDTO.getState();
        this.country = producerAddressDTO.getCountry();
        this.latitude = producerAddressDTO.getLatitude();
        this.longitude = producerAddressDTO.getLongitude();
        this.floor = producerAddressDTO.getFloor();
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
        sb.append("Order Producer Address Details Entity");
        sb.append("[");
        sb.append("OrderID: ");
        sb.append(this.order.toString());
        sb.append(" | ");
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


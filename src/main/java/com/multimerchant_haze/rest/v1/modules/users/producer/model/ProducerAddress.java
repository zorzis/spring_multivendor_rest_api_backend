package com.multimerchant_haze.rest.v1.modules.users.producer.model;

import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAddressDTO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zorzis on 3/28/2017.
 */

@Entity
@Table(name = "producers_addresses")
public class ProducerAddress
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producerID")
    private ProducerProfile producerProfile = new ProducerProfile();

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

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;



    public ProducerAddress()
    {

    }

    public ProducerAddress(ProducerAddressDTO producerAddressDTO)
    {
        this.id = producerAddressDTO.getId();
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


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public ProducerProfile getProducerProfile()
    {
        return this.producerProfile;
    }

    public void setProducerProfile(ProducerProfile producerProfile)
    {
        this.producerProfile = producerProfile;
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
        sb.append("Address Entity");
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
        sb.append("Postal Code: ");
        sb.append(this.postalCode);
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

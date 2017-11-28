package com.multimerchant_haze.rest.v1.modules.users.client.model;

import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAddressDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 5/8/2017.
 */
@Entity
@Table(name = "clients_addresses")
public class ClientAddress
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientAddress")
    private Set<ClientHasAddress> clientHasAddresses = new HashSet<>(0);


    public ClientAddress()
    {

    }

    public ClientAddress(ClientAddressDTO clientAddressDTO)
    {
        this.id = clientAddressDTO.getId();
        this.street = clientAddressDTO.getStreet();
        this.streetNumber = clientAddressDTO.getStreetNumber();
        this.city = clientAddressDTO.getCity();
        this.postalCode = clientAddressDTO.getPostalCode();
        this.state = clientAddressDTO.getState();
        this.country = clientAddressDTO.getCountry();
        this.latitude = clientAddressDTO.getLatitude();
        this.longitude = clientAddressDTO.getLongitude();
        this.floor = clientAddressDTO.getFloor();
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

    public Set<ClientHasAddress> getClientHasAddresses()
    {
        return clientHasAddresses;
    }

    public void setClientHasAddresses(Set<ClientHasAddress> clientHasAddresses)
    {
        this.clientHasAddresses = clientHasAddresses;
    }

    public void addClientHasAddress(ClientHasAddress clientHasAddress)
    {
        this.clientHasAddresses.add(clientHasAddress);
    }

    public void removeClientHasAddress(ClientHasAddress clientHasAddress)
    {
        this.clientHasAddresses.remove(clientHasAddress);
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

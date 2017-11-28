package com.multimerchant_haze.rest.v1.modules.users.client.model;

import javax.persistence.*;

/**
 * Created by zorzis on 5/8/2017.
 */
@Entity
@Table(name = "clients_have_addresses")
public class ClientHasAddress
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "clientID", referencedColumnName = "clientID")
    private Client client;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "addressID", referencedColumnName = "id")
    private ClientAddress clientAddress;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Client getClient()
    {
        return this.client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public ClientAddress getClientAddress()
    {
        return clientAddress;
    }

    public void setClientAddress(ClientAddress clientAddress)
    {
        this.clientAddress = clientAddress;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ClientHasAddress Entity Row");
        sb.append("[");
        sb.append("UserID: ");
        sb.append(this.client.getClientProfile().getClientID());
        sb.append(" | ");
        sb.append("Address Information: ");
        sb.append(this.clientAddress.toString());
        sb.append("]");
        return sb.toString();
    }
}

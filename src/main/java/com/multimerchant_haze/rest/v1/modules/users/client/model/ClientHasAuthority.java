package com.multimerchant_haze.rest.v1.modules.users.client.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zorzis on 5/8/2017.
 */
@Entity
@Table(name = "clients_have_authorities")
public class ClientHasAuthority implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "clientID", referencedColumnName = "clientID")
    private Client client;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "authorityID", referencedColumnName = "id")
    private ClientAuthority clientAuthority;

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

    public ClientAuthority getClientAuthority()
    {
        return clientAuthority;
    }

    public void setClientAuthority(ClientAuthority clientAuthority)
    {
        this.clientAuthority = clientAuthority;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ClientHasAuthority Entity Row");
        sb.append("[");
        sb.append("ClientID: ");
        sb.append(this.client.getClientProfile().getClientID());
        sb.append(" | ");
        sb.append("Authority Information: ");
        sb.append(this.clientAuthority.toString());
        sb.append("]");
        return sb.toString();
    }

}

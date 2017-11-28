package com.multimerchant_haze.rest.v1.modules.users.client.model;

import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 5/8/2017.
 */
@Entity
@Table(name = "clients_profile")
public class ClientProfile implements Serializable
{

    @Id
    @Column(name = "clientID", nullable = false)
    private String clientID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "clientProfile")
    private Client client;


    public ClientProfile()
    {

    }

    public ClientProfile(String clientID)
    {
        this.clientID = clientID;
    }

    public String getClientID()
    {
        return clientID;
    }

    public void setClientID(String clientID)
    {
        this.clientID = clientID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Client Profile Entity");
        sb.append("[");
        sb.append("Unique ClientID FK: ");
        sb.append(this.clientID);
        sb.append(" | ");
        sb.append("First Name: ");
        sb.append(this.firstName);
        sb.append(" | ");
        sb.append("Last Name: ");
        sb.append(this.lastName);
        sb.append(" | ");
        sb.append("Birth Date: ");
        sb.append(this.birthDate);
        sb.append(" | ");
        sb.append("Gender: ");
        sb.append(this.gender);
        sb.append("]");

        return sb.toString();
    }


}

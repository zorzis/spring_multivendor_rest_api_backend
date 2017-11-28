package com.multimerchant_haze.rest.v1.modules.users.producer.model;

import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 3/27/2017.
 */
@Entity
@Table(name = "producers_profile")
public class ProducerProfile implements Serializable
{

    @Id
    @Column(name = "producerID", nullable = false)
    private String producerID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "producerProfile")
    private Producer producer;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "producerProfile")
    private ProducerAddress producerAddress;


    public ProducerProfile()
    {

    }

    public ProducerProfile(String producerID)
    {
        this.producerID = producerID;
    }

    public String getProducerID()
    {
        return producerID;
    }

    public void setProducerID(String producerID)
    {
        this.producerID = producerID;
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

    public Producer getProducer()
    {
        return producer;
    }

    public void setProducer(Producer producer)
    {
        this.producer = producer;
    }

    public ProducerAddress getProducerAddress()
    {
        return producerAddress;
    }

    public void setProducerAddress(ProducerAddress producerAddress)
    {
        this.producerAddress = producerAddress;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Producer Profile Entity");
        sb.append("[");
        sb.append("Unique ProducerID FK: ");
        sb.append(this.producer.getProducerProfile().getProducerID());
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

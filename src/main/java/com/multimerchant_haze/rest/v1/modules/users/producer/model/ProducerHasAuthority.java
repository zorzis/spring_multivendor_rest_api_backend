package com.multimerchant_haze.rest.v1.modules.users.producer.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zorzis on 3/14/2017.
 */
@Entity
@Table(name = "producers_have_authorities")
public class ProducerHasAuthority implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "producerID", referencedColumnName = "producerID")
    private Producer producer;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "authorityID", referencedColumnName = "id")
    private ProducerAuthority producerAuthority;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Producer getProducer()
    {
        return this.producer;
    }

    public void setProducer(Producer producer)
    {
        this.producer = producer;
    }

    public ProducerAuthority getProducerAuthority()
    {
        return producerAuthority;
    }

    public void setProducerAuthority(ProducerAuthority producerAuthority)
    {
        this.producerAuthority = producerAuthority;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ProducerHasAuthority Entity Row");
        sb.append("[");
        sb.append("ProducerID: ");
        sb.append(this.producer.getProducerProfile().getProducerID());
        sb.append(" | ");
        sb.append("Authority Information: ");
        sb.append(this.producerAuthority.toString());
        sb.append("]");
        return sb.toString();
    }

}

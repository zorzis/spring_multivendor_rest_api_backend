package com.multimerchant_haze.rest.v1.modules.orders.model;

import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zorzis on 7/13/2017.
 */
@Entity
@Table(name = "orders_have_clients_producers")
public class OrderHasClientHasProducer implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Order order;


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "clientID", referencedColumnName = "clientID")
    private Client client;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "producerID", referencedColumnName = "producerID")
    private Producer producer;


    public OrderHasClientHasProducer()
    {

    }



    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public Producer getProducer()
    {
        return producer;
    }

    public void setProducer(Producer producer)
    {
        this.producer = producer;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderHasClientHasProduer Entity Row");
        sb.append("[");
        sb.append("Order: ");
        sb.append(this.order.toString());
        sb.append(" | ");
        sb.append("ClientID: ");
        sb.append(this.client.getClientProfile().getClientID());
        sb.append(" | ");
        sb.append("ProducerID: ");
        sb.append(this.producer.getProducerProfile().getProducerID());
        sb.append("]");
        return sb.toString();
    }
}

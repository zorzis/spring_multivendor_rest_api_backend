package com.multimerchant_haze.rest.v1.modules.users.producer.model;

import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 9/7/2017.
 */
@Entity
@Table(name = "producers_have_payment_methods")
public class ProducerHasPaymentMethod implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "producerID", referencedColumnName = "producerID")
    private Producer producer;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "paymentMethodID", referencedColumnName = "paymentMethodID")
    private PaymentMethod paymentMethod;

    @Column(name = "isDeactivated", nullable = false)
    private boolean isDeactivated;

    @Column(name = "deactivated_at")
    private Date deactivatedAt;

    @Column(name = "isTerminated", nullable = false)
    private boolean isTerminated;

    @Column(name = "terminated_at")
    private Date terminatedAt;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;



    public ProducerHasPaymentMethod()
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

    public Producer getProducer()
    {
        return producer;
    }

    public void setProducer(Producer producer)
    {
        this.producer = producer;
    }

    public PaymentMethod getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public boolean isDeactivated()
    {
        return isDeactivated;
    }

    public void setDeactivated(boolean deactivated)
    {
        isDeactivated = deactivated;
    }

    public Date getDeactivatedAt()
    {
        return deactivatedAt;
    }

    public void setDeactivatedAt(Date deactivatedAt)
    {
        this.deactivatedAt = deactivatedAt;
    }

    public boolean isTerminated()
    {
        return isTerminated;
    }

    public void setTerminated(boolean terminated)
    {
        isTerminated = terminated;
    }

    public Date getTerminatedAt()
    {
        return terminatedAt;
    }

    public void setTerminatedAt(Date terminatedAt)
    {
        this.terminatedAt = terminatedAt;
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


}

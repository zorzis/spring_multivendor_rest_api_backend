package com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model;

import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 9/27/2017.
 *
 * INFO about inheritance found here:
 * https://www.thoughts-on-java.org/complete-guide-inheritance-strategies-jpa-hibernate/
 */
@Entity
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable
{

    @Id
    @Column(name = "paymentID")
    private String paymentID;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "paymentMethodID", referencedColumnName = "paymentMethodID")
    private PaymentMethod paymentMethod;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "orderPayment")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private PaymentApproval paymentApproval;

    public Payment()
    {

    }


    public String getPaymentID()
    {
        return paymentID;
    }

    public void setPaymentID(String paymentID)
    {
        this.paymentID = paymentID;
    }

    public PaymentMethod getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod)
    {
        this.paymentMethod = paymentMethod;
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

    public Order getOrder()
    {
        return this.order;
    }

    public void setOrder(Order orders)
    {
        this.order = order;
    }

    public PaymentApproval getPaymentApproval()
    {
        return paymentApproval;
    }

    public void setPaymentApproval(PaymentApproval paymentApproval)
    {
        this.paymentApproval = paymentApproval;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Payment Entity");
        sb.append("[");
        sb.append("Unique PaymentID PK: ");
        sb.append(this.paymentID);
        sb.append(" | ");
        sb.append("Payment Method Entity: ");
        sb.append(this.paymentMethod.toString());
        sb.append(" | ");
        sb.append("Payment Approval Entity: ");
        sb.append(this.paymentApproval.toString());
        sb.append(" | ");
        sb.append("Created At: ");
        sb.append(this.createdAt);
        sb.append(" | ");
        sb.append("Updated At: ");
        sb.append(this.updatedAt);
        sb.append("]");

        return sb.toString();
    }



}

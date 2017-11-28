package com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zorzis on 9/29/2017.
 */
@Entity
@Table(name = "payments_onDelivery")
public class PaymentOnDelivery extends Payment
{

    @Column(name = "is_delivery_paid", nullable = false)
    private boolean isDeliveryPaid;

    @Column(name = "delivery_paid_at", nullable = true)
    private Date deliveryPaidAt;

    public PaymentOnDelivery()
    {
        super();
    }


    public boolean getIsDeliveryPaid()
    {
        return isDeliveryPaid;
    }

    public void setIsDeliveryPaid(boolean isDeliveryPaid)
    {
        this.isDeliveryPaid = isDeliveryPaid;
    }

    public Date getDeliveryPaidAt()
    {
        return deliveryPaidAt;
    }

    public void setDeliveryPaidAt(Date deliveryPaidAt)
    {
        this.deliveryPaidAt = deliveryPaidAt;
    }



    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentOnDelivery: ");
        sb.append("[");
        sb.append(super.toString());
        sb.append(" | ");
        sb.append("Is Delivery Paid: ");
        sb.append(this.isDeliveryPaid);
        sb.append(" | ");
        sb.append("Delivery Paid At: ");
        sb.append(this.deliveryPaidAt);
        sb.append("]");
        return sb.toString();
    }

}

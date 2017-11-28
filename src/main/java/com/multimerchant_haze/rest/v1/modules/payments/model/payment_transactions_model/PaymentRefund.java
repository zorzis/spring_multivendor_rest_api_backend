package com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model;

import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zorzis on 9/30/2017.
 */
@Entity
@Table(name = "payments_refunds")
public class PaymentRefund
{
    @Id
    @Column(name = "paymentRefundID")
    private String paymentRefundID;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "paymentDepositID", referencedColumnName = "paymentDepositID")
    private PaymentDeposit paymentDeposit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatusEnum status;

    @Column(name = "processor_message")
    private String processorMessage;

    @Column(name = "ammount")
    private Float ammount;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public PaymentRefund()
    {

    }


    public String getPaymentRefundID()
    {
        return paymentRefundID;
    }

    public void setPaymentRefundID(String paymentRefundID)
    {
        this.paymentRefundID = paymentRefundID;
    }

    public PaymentDeposit getPaymentDeposit()
    {
        return paymentDeposit;
    }

    public void setPaymentDeposit(PaymentDeposit paymentDeposit)
    {
        this.paymentDeposit = paymentDeposit;
    }

    public PaymentStatusEnum getStatus()
    {
        return status;
    }

    public void setStatus(PaymentStatusEnum status)
    {
        this.status = status;
    }

    public String getProcessorMessage()
    {
        return processorMessage;
    }

    public void setProcessorMessage(String processorMessage)
    {
        this.processorMessage = processorMessage;
    }

    public Float getAmmount()
    {
        return ammount;
    }

    public void setAmmount(Float ammount)
    {
        this.ammount = ammount;
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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Payment Refund Entity");
        sb.append("[");
        sb.append("Unique PaymentID PK: ");
        sb.append(this.paymentRefundID);
        sb.append(" | ");
        sb.append("Status: ");
        sb.append(this.status);
        sb.append(" | ");
        sb.append("Proccessor Message: ");
        sb.append(this.processorMessage);
        sb.append(" | ");
        sb.append("Ammount: ");
        sb.append(this.processorMessage);
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


package com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model;

import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zorzis on 9/30/2017.
 */
@Entity
@Table(name = "payments_approvals")
public class PaymentApproval
{

    @Id
    @Column(name = "paymentApprovalID")
    private String paymentApprovalID;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "paymentID", referencedColumnName = "paymentID")
    private Payment payment;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "paymentApproval")
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


    public PaymentApproval()
    {

    }


    public String getPaymentApprovalID()
    {
        return paymentApprovalID;
    }

    public void setPaymentApprovalID(String paymentApprovalID)
    {
        this.paymentApprovalID = paymentApprovalID;
    }

    public Payment getPayment()
    {
        return payment;
    }

    public void setPayment(Payment payment)
    {
        this.payment = payment;
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

    public PaymentDeposit getPaymentDeposit()
    {
        return paymentDeposit;
    }

    public void setPaymentDeposit(PaymentDeposit paymentDeposit)
    {
        this.paymentDeposit = paymentDeposit;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Payment Approval Entity");
        sb.append("[");
        sb.append("Unique PaymentID PK: ");
        sb.append(this.paymentApprovalID);
        sb.append(" | ");
        sb.append("Status: ");
        sb.append(this.status);
        sb.append(" | ");
        sb.append("Proccessor Message: ");
        sb.append(this.processorMessage);
        sb.append(" | ");
        sb.append("Ammount: ");
        sb.append(this.ammount);
        sb.append(" | ");
        sb.append("Created At: ");
        sb.append(this.createdAt);
        sb.append(" | ");
        sb.append("Updated At: ");
        sb.append(this.updatedAt);
        sb.append(" | ");
        sb.append("PaymentDeposit: ");
        sb.append(this.paymentDeposit);
        sb.append("]");

        return sb.toString();
    }
}

package com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model;

import javax.persistence.*;

/**
 * Created by zorzis on 9/29/2017.
 */
@Entity
@Table(name = "payments_paypal")
public class PaymentPaypal extends Payment
{

    @Column(name = "paypal_completed_payment_transaction_no", nullable = true)
    private String paypalTransactionNo;

    @Column(name = "paypal_approval_paymentID")
    private String paypalApprovalPaymentID;


    public PaymentPaypal()
    {
        super();
    }


    public String getPaypalTransactionNo()
    {
        return paypalTransactionNo;
    }

    public void setPaypalTransactionNo(String paypalTransactionNo)
    {
        this.paypalTransactionNo = paypalTransactionNo;
    }

    public String getPaypalApprovalPaymentID()
    {
        return paypalApprovalPaymentID;
    }

    public void setPaypalApprovalPaymentID(String paypalApprovalPaymentID)
    {
        this.paypalApprovalPaymentID = paypalApprovalPaymentID;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentPaypal: ");
        sb.append("[");
        sb.append(super.toString());
        sb.append(" | ");
        sb.append("Paypal Transaction No: ");
        sb.append(this.paypalTransactionNo);
        sb.append(" | ");
        sb.append("Payment Approval PaymentID: ");
        sb.append(this.paypalApprovalPaymentID);
        sb.append("]");
        return sb.toString();
    }


}

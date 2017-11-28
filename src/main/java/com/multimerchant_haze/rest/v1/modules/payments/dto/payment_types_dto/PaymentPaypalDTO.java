package com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;

/**
 * Created by zorzis on 9/30/2017.
 */
public class PaymentPaypalDTO extends PaymentDTO
{

    @JsonView(JsonACL.ClientsView.class)
    private String paypalTransactionNo;

    @JsonView(JsonACL.ClientsView.class)
    private String paypalApprovalPaymentID;

    public PaymentPaypalDTO()
    {

    }

    public PaymentPaypalDTO(Payment paymentPaypal)
    {
        super(paymentPaypal);
        this.paypalTransactionNo = ((PaymentPaypal)paymentPaypal).getPaypalTransactionNo();
        this.paypalApprovalPaymentID = ((PaymentPaypal)paymentPaypal).getPaypalApprovalPaymentID();
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
        sb.append("PaymentPaypalDTO: ");
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

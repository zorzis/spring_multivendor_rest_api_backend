package com.multimerchant_haze.rest.v1.modules.payments.dto.payment_transactions_dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentRefund;

import java.util.Date;

/**
 * Created by zorzis on 9/30/2017.
 */
public class PaymentRefundDTO
{



    @JsonView(JsonACL.ClientsView.class)
    private String paymentRefundID;

    @JsonView(JsonACL.ClientsView.class)
    private PaymentStatusEnum status;

    @JsonView(JsonACL.ClientsView.class)
    private String processorMessage;

    @JsonView(JsonACL.ClientsView.class)
    private Float ammount;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date updatedAt;

    public PaymentRefundDTO()
    {

    }

    public PaymentRefundDTO(PaymentRefund paymentRefund)
    {
        this.paymentRefundID = paymentRefund.getPaymentRefundID();
        this.status = paymentRefund.getStatus();
        this.processorMessage = paymentRefund.getProcessorMessage();
        this.ammount = paymentRefund.getAmmount();
        this.createdAt = paymentRefund.getCreatedAt();
        this.updatedAt = paymentRefund.getUpdatedAt();
    }



    public String getPaymentRefundID()
    {
        return paymentRefundID;
    }

    public void setPaymentRefundID(String paymentRefundID)
    {
        this.paymentRefundID = paymentRefundID;
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
        sb.append("Payment Refund DTO");
        sb.append("[");
        sb.append("Unique Payment Refund ID PK: ");
        sb.append(this.paymentRefundID);
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
        sb.append("]");

        return sb.toString();
    }
}

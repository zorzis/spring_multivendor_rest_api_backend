package com.multimerchant_haze.rest.v1.modules.payments.dto.payment_transactions_dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentDeposit;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentRefund;

import java.util.Date;

/**
 * Created by zorzis on 9/30/2017.
 */
public class PaymentDepositDTO
{

    @JsonView(JsonACL.ClientsView.class)
    private String paymentDepositID;

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


    @JsonView(JsonACL.ClientsView.class)
    private PaymentRefundDTO paymentRefund;

    public PaymentDepositDTO()
    {

    }

    public PaymentDepositDTO(PaymentDeposit paymentDeposit)
    {
        this.paymentDepositID = paymentDeposit.getPaymentDepositID();
        this.status = paymentDeposit.getStatus();
        this.processorMessage = paymentDeposit.getProcessorMessage();
        this.ammount = paymentDeposit.getAmmount();
        this.createdAt = paymentDeposit.getCreatedAt();
        this.updatedAt = paymentDeposit.getUpdatedAt();

        this.paymentRefund = mapPaymentRefundEntityToPaymentRefundDTO(paymentDeposit.getPaymentRefund());
    }


    public PaymentRefundDTO mapPaymentRefundEntityToPaymentRefundDTO(PaymentRefund paymentRefund)
    {
        PaymentRefundDTO paymentRefundDTO = null;
        if(paymentRefund == null)
        {
            paymentRefundDTO = null;
        }
        else
        {
            paymentRefundDTO = new PaymentRefundDTO(paymentRefund);
        }
        return paymentRefundDTO;
    }

    public String getPaymentDepositID()
    {
        return paymentDepositID;
    }

    public void setPaymentDepositID(String paymentDepositID)
    {
        this.paymentDepositID = paymentDepositID;
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

    public PaymentRefundDTO getPaymentRefund()
    {
        return paymentRefund;
    }

    public void setPaymentRefund(PaymentRefundDTO paymentRefund)
    {
        this.paymentRefund = paymentRefund;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Payment Approval DTO");
        sb.append("[");
        sb.append("Unique Payment DepositID PK: ");
        sb.append(this.paymentDepositID);
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
        sb.append("Payment Refund DTO: ");
        if(this.paymentRefund != null)
        {
            sb.append(this.paymentRefund.toString());

        }
        else
        {
            sb.append("<null>");

        }
        sb.append("]");

        return sb.toString();
    }
}


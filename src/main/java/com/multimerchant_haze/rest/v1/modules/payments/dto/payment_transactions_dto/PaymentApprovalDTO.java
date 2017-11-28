package com.multimerchant_haze.rest.v1.modules.payments.dto.payment_transactions_dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentStatusEnum;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentDeposit;

import java.util.Date;

/**
 * Created by zorzis on 9/30/2017.
 */
public class PaymentApprovalDTO
{

    @JsonView(JsonACL.ClientsView.class)
    private String paymentApprovalID;

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
    private PaymentDepositDTO paymentDeposit;

    public PaymentApprovalDTO()
    {

    }

    public PaymentApprovalDTO(PaymentApproval paymentApproval)
    {
        this.paymentApprovalID = paymentApproval.getPaymentApprovalID();
        this.status = paymentApproval.getStatus();
        this.processorMessage = paymentApproval.getProcessorMessage();
        this.ammount = paymentApproval.getAmmount();
        this.createdAt = paymentApproval.getCreatedAt();
        this.updatedAt = paymentApproval.getUpdatedAt();
        this.paymentDeposit = mapPaymentDepositEntityToPaymentDepositDTO(paymentApproval.getPaymentDeposit());
    }


    public PaymentDepositDTO mapPaymentDepositEntityToPaymentDepositDTO(PaymentDeposit paymenDeposit)
    {
        PaymentDepositDTO paymentDepositDTO = null;
        if(paymenDeposit == null)
        {
            paymentDepositDTO = null;
        }
        else
        {
            paymentDepositDTO = new PaymentDepositDTO(paymenDeposit);
        }
        return paymentDepositDTO;
    }

    public String getPaymentApprovalID()
    {
        return paymentApprovalID;
    }

    public void setPaymentApprovalID(String paymentApprovalID)
    {
        this.paymentApprovalID = paymentApprovalID;
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

    public PaymentDepositDTO getPaymentDeposit()
    {
        return paymentDeposit;
    }

    public void setPaymentDeposit(PaymentDepositDTO paymentDeposit)
    {
        this.paymentDeposit = paymentDeposit;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Payment Approval DTO");
        sb.append("[");
        sb.append("Unique Payment Approval ID PK: ");
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
        sb.append("Payment Deposit DTO: ");
        if(this.paymentDeposit != null)
        {
            sb.append(this.paymentDeposit.toString());

        }
        else
        {
            sb.append("<null>");

        }
        sb.append("]");

        return sb.toString();
    }
}

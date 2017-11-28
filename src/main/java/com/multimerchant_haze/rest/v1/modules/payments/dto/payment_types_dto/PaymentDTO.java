package com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.payments.dto.PaymentMethodDTO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.payment_transactions_dto.PaymentApprovalDTO;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;

import java.util.Date;

/**
 * Created by zorzis on 9/28/2017.
 */
/*@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentPaypalDTO.class, name = "paymentPaypal"),
        @JsonSubTypes.Type(value = PaymentOnDeliveryDTO.class, name = "paymentOnDelivery")
})*/
public abstract class PaymentDTO
{

    @JsonView(JsonACL.ClientsView.class)
    private String paymentID;

    @JsonView(JsonACL.ClientsView.class)
    private PaymentMethodDTO paymentMethod;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date updatedAt;

    @JsonView(JsonACL.ClientsView.class)
    private PaymentApprovalDTO paymentApproval;

    public PaymentDTO()
    {

    }

    public PaymentDTO(Payment payment)
    {
        this.paymentID = payment.getPaymentID();
        this.paymentMethod = new PaymentMethodDTO(payment.getPaymentMethod());
        this.createdAt = payment.getCreatedAt();
        this.updatedAt = payment.getUpdatedAt();

        this.paymentApproval = mapPaymentApprovalEntityToPaymentApprovalDTO(payment.getPaymentApproval());

    }





    public PaymentApprovalDTO mapPaymentApprovalEntityToPaymentApprovalDTO(PaymentApproval paymentApproval)
    {
        PaymentApprovalDTO paymentApprovalDTO;

        if(paymentApproval == null)
        {
            paymentApprovalDTO = null;
        }
        else
        {
            paymentApprovalDTO = new PaymentApprovalDTO(paymentApproval);
        }

        return paymentApprovalDTO;
    }

    public PaymentOnDeliveryDTO mapPaymentOnDeliveryEntityToPaymentOnDeliveryDTO(PaymentOnDelivery paymentOnDelivery)
    {
         PaymentOnDeliveryDTO paymentOnDeliveryDTO;
         if(paymentOnDelivery == null)
         {
             paymentOnDeliveryDTO = null;
         }
         else
         {
             paymentOnDeliveryDTO = new PaymentOnDeliveryDTO(paymentOnDelivery);
         }
         return paymentOnDeliveryDTO;
    }

    public PaymentPaypalDTO mapPaymentPaypalEntityToPaymentPaypalDTO(PaymentPaypal paymentPaypal)
    {
        PaymentPaypalDTO paymentPaypalDTO;
        if(paymentPaypal == null)
        {
            paymentPaypalDTO = null;
        }
        else
        {
            paymentPaypalDTO = new PaymentPaypalDTO(paymentPaypal);
        }
        return paymentPaypalDTO;
    }

    public String getPaymentID()
    {
        return paymentID;
    }

    public void setPaymentID(String paymentID)
    {
        this.paymentID = paymentID;
    }

    public PaymentMethodDTO getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDTO paymentMethod)
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

    public PaymentApprovalDTO getPaymentApproval()
    {
        return paymentApproval;
    }

    public void setPaymentApproval(PaymentApprovalDTO paymentApproval)
    {
        this.paymentApproval = paymentApproval;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Payment DTO");
        sb.append("[");
        sb.append("Unique PaymentID PK: ");
        sb.append(this.paymentID);
        sb.append(" | ");
        sb.append("Payment Method DTO: ");
        sb.append(this.paymentMethod.toString());
        sb.append(" | ");
        sb.append("Created At: ");
        sb.append(this.createdAt);
        sb.append(" | ");
        sb.append("Updated At: ");
        sb.append(this.updatedAt);
        sb.append(" | ");
        sb.append("Payment Approval DTO: ");
        sb.append(this.paymentApproval.toString());
        sb.append("]");

        return sb.toString();
    }
}

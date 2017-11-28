package com.multimerchant_haze.rest.v1.modules.users.producer.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateDeserializer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerHasPaymentMethod;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.payments.dto.PaymentMethodDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 9/27/2017.
 */
public class ProducerHasPaymentMethodDTO implements Serializable
{
    @JsonView(JsonACL.PublicView.class)
    private PaymentMethodDTO paymentMethod;

    @JsonView(JsonACL.PublicView.class)
    private boolean isDeactivated;

    @JsonSerialize(using = CustomBirthDateSerializer.class)
    @JsonDeserialize(using = CustomBirthDateDeserializer.class)
    @JsonView(JsonACL.ProducersView.class)
    private Date deactivatedAt;

    @JsonView(JsonACL.ProducersView.class)
    private boolean isTerminated;

    @JsonSerialize(using = CustomBirthDateSerializer.class)
    @JsonDeserialize(using = CustomBirthDateDeserializer.class)
    @JsonView(JsonACL.ProducersView.class)
    private Date terminatedAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date updatedAt;

    public ProducerHasPaymentMethodDTO(ProducerHasPaymentMethod producerHasPaymentMethod)
    {
        this.paymentMethod = new PaymentMethodDTO(producerHasPaymentMethod.getPaymentMethod());

        this.isDeactivated = producerHasPaymentMethod.isDeactivated();
        this.deactivatedAt = producerHasPaymentMethod.getDeactivatedAt();
        this.isTerminated = producerHasPaymentMethod.isTerminated();
        this.terminatedAt = producerHasPaymentMethod.getTerminatedAt();
        this.createdAt = producerHasPaymentMethod.getCreatedAt();
        this.updatedAt = producerHasPaymentMethod.getUpdatedAt();
    }



    public PaymentMethodDTO getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDTO paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public boolean isDeactivated()
    {
        return isDeactivated;
    }

    public void setDeactivated(boolean deactivated)
    {
        isDeactivated = deactivated;
    }

    public Date getDeactivatedAt()
    {
        return deactivatedAt;
    }

    public void setDeactivatedAt(Date deactivatedAt)
    {
        this.deactivatedAt = deactivatedAt;
    }

    public boolean isTerminated()
    {
        return isTerminated;
    }

    public void setTerminated(boolean terminated)
    {
        isTerminated = terminated;
    }

    public Date getTerminatedAt()
    {
        return terminatedAt;
    }

    public void setTerminatedAt(Date terminatedAt)
    {
        this.terminatedAt = terminatedAt;
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
        sb.append("ProducerHasPaymentMethod DTO");
        sb.append("[");
        sb.append("PaymentMethodDTO: ");
        sb.append(this.paymentMethod.toString());
        sb.append(" | ");
        sb.append("IsTerminated: ");
        sb.append(this.isTerminated);
        sb.append(" | ");
        sb.append("Terminated At: ");
        sb.append(this.terminatedAt);
        sb.append(" | ");
        sb.append("IsDeactivated: ");
        sb.append(this.isDeactivated);
        sb.append(" | ");
        sb.append("Deactivated At: ");
        sb.append(this.deactivatedAt);
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

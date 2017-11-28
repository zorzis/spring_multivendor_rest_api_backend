package com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;

import java.util.Date;

/**
 * Created by zorzis on 9/30/2017.
 */
public class PaymentOnDeliveryDTO extends PaymentDTO
{

    @JsonView(JsonACL.ClientsView.class)
    private boolean isDeliveryPaid;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date deliveryPaidAt;


    public PaymentOnDeliveryDTO()
    {

    }

    public PaymentOnDeliveryDTO(Payment paymentOnDelivery)
    {
        super(paymentOnDelivery);

        this.isDeliveryPaid = ((PaymentOnDelivery)paymentOnDelivery).getIsDeliveryPaid();
        this.deliveryPaidAt = ((PaymentOnDelivery)paymentOnDelivery).getDeliveryPaidAt();
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
        sb.append("PaymentOnDeliveryDTO: ");
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

package com.multimerchant_haze.rest.v1.modules.payments.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;

/**
 * Created by zorzis on 9/7/2017.
 */
public class PaymentMethodDTO
{
    @JsonView(JsonACL.PublicView.class)
    private String paymentMethodID;

    @JsonView(JsonACL.PublicView.class)
    private String paymentMethodName;

    @JsonView(JsonACL.PublicView.class)
    private String paymentMethodDescription;

    public PaymentMethodDTO()
    {

    }

    public PaymentMethodDTO(PaymentMethod paymentMethod)
    {
        this.paymentMethodID = paymentMethod.getPaymentMethodID();
        this.paymentMethodName = paymentMethod.getPaymentMethodName();
        this.paymentMethodDescription = paymentMethod.getPaymentMethodDescription();
    }





    public String getPaymentMethodID()
    {
        return paymentMethodID;
    }

    public void setPaymentMethodID(String paymentMethodID)
    {
        this.paymentMethodID = paymentMethodID;
    }

    public String getPaymentMethodName()
    {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName)
    {
        this.paymentMethodName = paymentMethodName;
    }

    public String getPaymentMethodDescription()
    {
        return paymentMethodDescription;
    }

    public void setPaymentMethodDescription(String paymentMethodDescription)
    {
        this.paymentMethodDescription = paymentMethodDescription;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentMethod DTO");
        sb.append("[");
        sb.append("Autogenerated MySQL id: ");
        sb.append(this.paymentMethodID);
        sb.append(" | ");
        sb.append("Payment Method Name: ");
        sb.append(this.paymentMethodName);
        sb.append(" | ");
        sb.append("Payment Method Description: ");
        sb.append(this.paymentMethodDescription);
        sb.append("]");
        return sb.toString();
    }
}

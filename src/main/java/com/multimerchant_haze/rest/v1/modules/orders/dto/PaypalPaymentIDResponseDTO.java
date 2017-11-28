package com.multimerchant_haze.rest.v1.modules.orders.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;

/**
 * Created by zorzis on 10/17/2017.
 */
public class PaypalPaymentIDResponseDTO
{

    @JsonView(JsonACL.ClientsView.class)
    private String paymentID;

    public PaypalPaymentIDResponseDTO()
    {

    }


    public String getPaymentID()
    {
        return paymentID;
    }

    public void setPaymentID(String paymentID)
    {
        this.paymentID = paymentID;
    }

}

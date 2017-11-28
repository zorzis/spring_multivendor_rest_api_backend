package com.multimerchant_haze.rest.v1.modules.orders.dto;

/**
 * Created by zorzis on 10/6/2017.
 */
public class PaypalDataObjectReceivedFromFrontEndDTO
{

    private String paypalPaymentID;
    private String paypalPaymentPayerID;

    public PaypalDataObjectReceivedFromFrontEndDTO()
    {

    }

    public PaypalDataObjectReceivedFromFrontEndDTO(String paypalPaymentID, String paypalPaymentPayerID)
    {
        this.paypalPaymentID = paypalPaymentID;
        this.paypalPaymentPayerID = paypalPaymentPayerID;
    }

    public PaypalDataObjectReceivedFromFrontEndDTO(Object paypalDataObjectReceivedFromFrontEndDTO)
    {
        this.paypalPaymentID = ((PaypalDataObjectReceivedFromFrontEndDTO)paypalDataObjectReceivedFromFrontEndDTO).getPaypalPaymentID();
        this.paypalPaymentPayerID = ((PaypalDataObjectReceivedFromFrontEndDTO)paypalDataObjectReceivedFromFrontEndDTO).paypalPaymentPayerID;
    }


    public String getPaypalPaymentID()
    {
        return paypalPaymentID;
    }

    public void setPaypalPaymentID(String paypalPaymentID)
    {
        this.paypalPaymentID = paypalPaymentID;
    }

    public String getPaypalPaymentPayerID()
    {
        return paypalPaymentPayerID;
    }

    public void setPaypalPaymentPayerID(String paypalPaymentPayerID)
    {
        this.paypalPaymentPayerID = paypalPaymentPayerID;
    }

}

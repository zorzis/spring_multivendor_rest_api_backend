package com.multimerchant_haze.rest.v1.app.responseEntities;

/**
 * Created by zorzis on 3/2/2017.
 */
public class ResponseEntitySuccess extends ResponseEntityAbstract
{

    public ResponseEntitySuccess()
    {

    }

    public ResponseEntitySuccess(int httpStatusCode, String message)
    {
        super(message, httpStatusCode);
    }

}

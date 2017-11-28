package com.multimerchant_haze.rest.v1.app.responseEntities;

/**
 * Created by zorzis on 4/3/2017.
 */
public abstract class ResponseEntityAbstract
{
    /** contains the same HTTP Status code returned by the server */
    private int status;

    /** message describing the response*/
    private String message;

    public ResponseEntityAbstract()
    {

    }

    public ResponseEntityAbstract(String message)
    {
        this.message = message;
    }

    public ResponseEntityAbstract(String message, int status)
    {
        this.message = message;
        this.status = status;
    }


    /** GETTERS / SETTERS **/
    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }


}

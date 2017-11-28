package com.multimerchant_haze.rest.v1.app.errorHandling;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zorzis on 4/4/2017.
 */
public class AppException extends RuntimeException
{
    /**
     * contains redundantly the HTTP status of the response sent back to the client in case of error, so that
     * the developer does not have to look into the response headers
     */
    private HttpStatus httpStatus;

    /** application specific error appErrorCode */
    private int appErrorCode;

    /** linkToErrorMessagesDocumentation point to page where the error message is documented */
    private String linkToErrorMessagesDocumentation;

    /** extra information that might useful for developers */
    private List<String> developersMessageExtraInfo;


    public AppException()
    {
        super();
    }

    public AppException(String errorMessage)
    {
        super(errorMessage);
    }



    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus)
    {
        this.httpStatus = httpStatus;
    }

    public int getAppErrorCode()
    {
        return appErrorCode;
    }

    public void setAppErrorCode(int appErrorCode)
    {
        this.appErrorCode = appErrorCode;
    }

    public String getLinkToErrorMessagesDocumentation()
    {
        return linkToErrorMessagesDocumentation;
    }

    public void setLinkToErrorMessagesDocumentation(String linkToErrorMessagesDocumentation)
    {
        this.linkToErrorMessagesDocumentation = linkToErrorMessagesDocumentation;
    }

    public List<String> getDevelopersMessageExtraInfo()
    {
        return developersMessageExtraInfo;
    }

    public void setDevelopersMessageExtraInfoAsListOfReasons(List<String> developersMessageExtraInfo)
    {
        this.developersMessageExtraInfo = developersMessageExtraInfo;
    }

    public void setDevelopersMessageExtraInfoAsSingleReason(String developersMessageExtraInfo)
    {
        this.developersMessageExtraInfo = Arrays.asList(developersMessageExtraInfo);
    }
}

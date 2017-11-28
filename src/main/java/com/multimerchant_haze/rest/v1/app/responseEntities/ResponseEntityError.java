package com.multimerchant_haze.rest.v1.app.responseEntities;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zorzis on 4/3/2017.
 */
public class ResponseEntityError extends ResponseEntityAbstract
{
    /** application specific error code */
    private int code;

    /** extra information that might useful for developers */
    private List<String> developer;

    /** link point to page where the error message is documented */
    private String link;

    public ResponseEntityError()
    {

    }

    public ResponseEntityError(Exception ex)
    {
        this.setMessage(ex.getMessage());
    }

    public ResponseEntityError(AppException ex)
    {
        this.setMessage(ex.getMessage());
        this.setStatus(ex.getHttpStatus().value());
        this.code = ex.getAppErrorCode();
        this.link = ex.getLinkToErrorMessagesDocumentation();
        this.developer = ex.getDevelopersMessageExtraInfo();
    }


    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public List<String> getDeveloper()
    {
        return developer;
    }

    public void setDeveloperInformationList(List<String> developer)
    {
        this.developer = developer;
    }

    public void setDeveloperInformationSignle(String developerInformation)
    {
        this.developer = Arrays.asList(developerInformation);
    }

}

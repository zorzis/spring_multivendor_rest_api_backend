package com.multimerchant_haze.rest.v1.modules.users.client.service.client_registration;

import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by zorzis on 11/22/2017.
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent
{
    private String appUrl;
    private Locale locale;
    private Client client;

    public OnRegistrationCompleteEvent(Client client, Locale locale, String appUrl)
    {
        super(client);

        this.appUrl = appUrl;
        this.locale = locale;
        this.client = client;
    }



    public String getAppUrl()
    {
        return appUrl;
    }

    public void setAppUrl(String appUrl)
    {
        this.appUrl = appUrl;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

}

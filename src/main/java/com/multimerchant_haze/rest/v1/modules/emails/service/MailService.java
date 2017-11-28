package com.multimerchant_haze.rest.v1.modules.emails.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;

/**
 * Created by zorzis on 11/2/2017.
 */
public interface MailService
{
    public void sendEmail(Object object) throws AppException;

}

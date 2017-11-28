package com.multimerchant_haze.rest.v1.modules.emails.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import org.springframework.stereotype.Service;

/**
 * Created by zorzis on 11/3/2017.
 */
@Service("clientRegistrationEmailService")
public class ClientRegistrationMailServiceImpl implements MailService
{
    @Override
    public void sendEmail(Object object) throws AppException
    {

    }
}

package com.multimerchant_haze.rest.v1.modules.users.client.service.client_registration;

import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientVerificationTokenDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_verification_token.ClientVerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by zorzis on 11/23/2017.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationListener.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    private ClientVerificationTokenService clientVerificationTokenService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent)
    {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event)
    {
        Client client = event.getClient();

        // Create and Save the Client Verification Token Entity to the Database
        ClientVerificationTokenDTO clientVerificationTokenDTO = this.clientVerificationTokenService.createClientVerificationToken(client);

        System.out.println("*****************************************************************************");
        System.out.println("Client Verification Token Entity After Persistence");
        System.out.println("ID: " + clientVerificationTokenDTO.getId());
        System.out.println("Token: " + clientVerificationTokenDTO.getToken());
        System.out.println("Exp Date: " + clientVerificationTokenDTO.getExpiryDate());
        System.out.println("Client Email: " + clientVerificationTokenDTO.getClient().getEmail());
        System.out.println("Client ID: " + clientVerificationTokenDTO.getClient().getClientID());

        System.out.println("*****************************************************************************");

        //TODO: create the email and send it to user
    }
}

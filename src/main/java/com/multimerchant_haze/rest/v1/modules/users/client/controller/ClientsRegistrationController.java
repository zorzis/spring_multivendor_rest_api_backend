package com.multimerchant_haze.rest.v1.modules.users.client.controller;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_registration.ClientRegistrationService;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_registration.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by zorzis on 5/8/2017.
 */
@RestController
public class ClientsRegistrationController
{

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = "/client/registration", method = RequestMethod.POST)
    public ResponseEntity<ResponseEntitySuccess> registerClientFromApplicationFormInput(@RequestParam(value = "email") String email,
                                                                                        @RequestParam("password") String password,
                                                                                        WebRequest request)
            throws AppException

    {
        ClientDTO tryingToRegisterUserObject = new ClientDTO(email, password);
        Client registeredClient = clientRegistrationService.registerNewClient(tryingToRegisterUserObject);

        // We call a Spring Application Event to send the registration verification email to customer
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredClient, request.getLocale(), appUrl));



        ResponseEntitySuccess responseEntitySuccess = new ResponseEntitySuccess();
        responseEntitySuccess.setStatus(HttpStatus.OK.value());
        responseEntitySuccess.setMessage("In order to complete your registration please verify your email address." +
                "An email confirmation has been sent to you at " + registeredClient.getEmail());

        return new ResponseEntity<ResponseEntitySuccess>(responseEntitySuccess, HttpStatus.CREATED);
    }
}
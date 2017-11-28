package com.multimerchant_haze.rest.v1.modules.users.producer.controller;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerRegistrationService;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zorzis on 3/2/2017.
 */
@RestController
public class ProducersRegistrationController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducersRegistrationController.class);
    private String className = this.getClass().getSimpleName();

    @Autowired
    private ProducerRegistrationService producerRegistrationService;

    @RequestMapping(value = "/producer/registration", method = RequestMethod.POST)
    public ResponseEntity<?> registerUserFromApplicationFormInput(@RequestParam(value = "email") String email,
                                                                  @RequestParam("password") String password)
            throws AppException

    {
        LOGGER.debug("Producer Trying to Register Credentials: ");
        LOGGER.debug("Email is: " + email);
        LOGGER.debug("Password is: " + password);


        ProducerDTO tryingToRegisterUserObject = new ProducerDTO(email, password);

        // calling the helpers layer to create the Producer to the database
        // if pass all the validation checks and create the unique userID and finally
        // create the user to the database after we have created the hashed password
        // and encrypt that hashed password with AES Encrypt then MAC standard
        String createdUserEmail = null;

        createdUserEmail = producerRegistrationService.registerNewProducer(tryingToRegisterUserObject);

        ResponseEntitySuccess responseEntitySuccess = new ResponseEntitySuccess();
        responseEntitySuccess.setStatus(HttpStatus.OK.value());
        responseEntitySuccess.setMessage("Your Registration Completed Successfully.Welcome at haze.gr community." +
                "An email confirmation has sent to you at " + createdUserEmail);

        return new ResponseEntity<ResponseEntitySuccess>(responseEntitySuccess, HttpStatus.OK);


    }
}
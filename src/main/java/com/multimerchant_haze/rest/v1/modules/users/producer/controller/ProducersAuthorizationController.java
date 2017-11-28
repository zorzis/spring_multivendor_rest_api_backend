package com.multimerchant_haze.rest.v1.modules.users.producer.controller;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.dto.JwtAccessTokenDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerAuthorizationService;
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
public class ProducersAuthorizationController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducersAuthorizationController.class);
    private String className = this.getClass().getSimpleName();

    @Autowired
    private ProducerAuthorizationService producerAuthorizationService;


    @RequestMapping(value = "/producer/auth", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateProducerFromApplicationFormInput(@RequestParam(value = "email") String email,
                                                                          @RequestParam(value = "password") String password)
            throws AppException,Exception

    {
        LOGGER.debug("Producer Trying To Login Credentials: ");
        LOGGER.debug("Email is: " + email);
        LOGGER.debug("Password is: " + password);

        ProducerDTO tryingToLoginProducerObject = new ProducerDTO(email, password);

        // Send the Producer object we just created based on Login Credentials to the Service Layer
        // to check at first if a user based on the Email indeed exists, and if exists
        // to check if the password that is provided when hashed produces the same hash with the one
        // retrieved from database(the pass from database is firstly decrypted and then we take the hashed password
        // that is encrypted to be stored to the database)
        String accessTokenString = producerAuthorizationService.authorizeProducerProducingJWToken(tryingToLoginProducerObject);

        // THE OK RESPONSE
        // Issue the Token for user authentication
        JwtAccessTokenDTO accessTokenEntity = new JwtAccessTokenDTO(accessTokenString);

        return new ResponseEntity<JwtAccessTokenDTO>(accessTokenEntity, HttpStatus.CREATED);

    }
}
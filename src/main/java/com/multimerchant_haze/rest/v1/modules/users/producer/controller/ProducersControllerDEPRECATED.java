package com.multimerchant_haze.rest.v1.modules.users.producer.controller;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zorzis on 3/2/2017.
 */
@RestController
public class ProducersControllerDEPRECATED
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducersControllerDEPRECATED.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    private ProducerService userService;



    @RequestMapping(value = "/producer/delete_user_auhtority", method = RequestMethod.POST)
    public ResponseEntity deleteUserAuthorityByUserEmailAndAuthorityID(@RequestParam(value = "email") String email,
                                                                       @RequestParam(value = "authorityID") String authorityID)
            throws AppException

    {
        ProducerDTO userTryingToDeleteAuthority = new ProducerDTO(email);
        ProducerAuthorityDTO authorityWantedToBeRemoved = new ProducerAuthorityDTO(Long.parseLong(authorityID));
        String authorityIDFromSuccessfulUserAuthorityDeletion = userService.deleteProducerAuthorityByProducerEmailAndAuthorityID(userTryingToDeleteAuthority, authorityWantedToBeRemoved);
        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "ProducerAuthority " + authorityIDFromSuccessfulUserAuthorityDeletion
                        + " removed successfully for Producer " + email), HttpStatus.OK);
    }




    @RequestMapping(value = "/producer/delete_user", method = RequestMethod.POST)
    public ResponseEntity deleteUserByEmail(@RequestParam(value = "email") String email) throws AppException

    {
        String deletedUserObjectByEmail = userService.deleteProducerByProducerEmail(new ProducerDTO(email));

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Producer " + deletedUserObjectByEmail
                        + " removed successfully from Database"), HttpStatus.OK);    }





    @RequestMapping(value = "/producer/add_authority_to_user", method = RequestMethod.POST)
    public ResponseEntity addAuthorityToUserByUserEmailAndAuthorityName(@RequestParam(value = "email") String email,
                                                                        @RequestParam(value = "authorityName") String authorityName)
            throws AppException

    {
        ProducerDTO userTryingToAddAuthority = new ProducerDTO(email);

        ProducerAuthorityDTO authorityWantedToBeAdded = new ProducerAuthorityDTO(authorityName);

        String createdAuthorityForUser = userService.addAuthorityToProducerByProducerEmailAndAuthorityName(userTryingToAddAuthority, authorityWantedToBeAdded);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "ProducerAuthority " + createdAuthorityForUser
                        + " added successfully for Producer " + email), HttpStatus.OK);
    }



}
package com.multimerchant_haze.rest.v1.modules.users.client.controller;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import com.multimerchant_haze.rest.v1.modules.security.custom_security_expressions.get_authentication_principal_interface.AuthenticationFacadeInterface;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by zorzis on 5/11/2017.
 */
@RestController
public class ClientsControllerDEPRECATED
{
    @Autowired
    private ClientService userService;

    @Autowired
    private AuthenticationFacadeInterface authenticationFacadeInterface;

    @RequestMapping(value = "/client/get_user_simple", method = RequestMethod.POST)
    public ResponseEntity getUserByEmail(@RequestParam(value = "email") String email) throws AppException

    {
        ClientDTO getUserObjectByEmail = userService.getClientByEmail(email);
        return new ResponseEntity<ClientDTO> (getUserObjectByEmail, HttpStatus.OK);
    }



    @RequestMapping(value = "/client/get_user_authorities", method = RequestMethod.POST)
    public ResponseEntity getUserByEmailFetchingAuthorities(@RequestParam(value = "email") String email) throws AppException

    {
        ClientDTO getUserObjectByEmail = userService.getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(email);
        return new ResponseEntity<ClientDTO> (getUserObjectByEmail, HttpStatus.OK);
    }


    @RequestMapping(value = "/client/delete_user_authority", method = RequestMethod.POST)
    public ResponseEntity deleteUserAuthorityByUserEmailAndAuthorityID(@RequestParam(value = "email") String email,
                                                                       @RequestParam(value = "authorityID") String authorityID)
            throws AppException

    {
        ClientDTO userTryingToDeleteAuthority = new ClientDTO(email);
        ClientAuthorityDTO authorityWantedToBeRemoved = new ClientAuthorityDTO(Long.parseLong(authorityID));
        String authorityIDFromSuccessfulUserAuthorityDeletion = userService.deleteClientAuthorityByClientEmailAndAuthorityID(userTryingToDeleteAuthority, authorityWantedToBeRemoved);
        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "ClientAuthority " + authorityIDFromSuccessfulUserAuthorityDeletion
                        + " removed successfully for Client " + email), HttpStatus.OK);
    }



    @RequestMapping(value = "/client/delete_user", method = RequestMethod.POST)
    public ResponseEntity deleteUserByEmail(@RequestParam(value = "email") String email) throws AppException

    {
        String deletedUserObjectByEmail = userService.deleteClientByClientEmail(new ClientDTO(email));

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Client " + deletedUserObjectByEmail
                        + " removed successfully from Database"), HttpStatus.OK);    }





    @RequestMapping(value = "/client/add_authority_to_user", method = RequestMethod.POST)
    public ResponseEntity addAuthorityToUserByUserEmailAndAuthorityName(@RequestParam(value = "email") String email,
                                                                        @RequestParam(value = "authorityName") String authorityName)
            throws AppException

    {
        ClientDTO userTryingToAddAuthority = new ClientDTO(email);

        ClientAuthorityDTO authorityWantedToBeAdded = new ClientAuthorityDTO(authorityName);

        String createdAuthorityForUser = userService.addAuthorityToClientByClientEmailAndAuthorityName(userTryingToAddAuthority, authorityWantedToBeAdded);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "ClientAuthority " + createdAuthorityForUser
                        + " added successfully for Client " + email), HttpStatus.OK);
    }



}
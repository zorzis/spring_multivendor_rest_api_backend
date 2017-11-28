package com.multimerchant_haze.rest.v1.modules.users.client.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_profile.ClientProfileService;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zorzis on 5/27/2017.
 */
@RestController
public class ClientsProfileController
{
    @Autowired
    private ClientProfileService clientProfileService;

    @PreAuthorize("(hasAnyAuthority('ROLE_CLIENT') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/get_profile", method = RequestMethod.POST)
    @JsonView(JsonACL.ClientsView.class)
    public ResponseEntity getClientAccountProfileByEmail(@RequestParam(value = "email") String email) throws AppException

    {
        /*
        Authentication authentication = authenticationFacadeInterface.getAuthentication();
        System.out.println("Principal is: " + ((JwtUser)authentication.getPrincipal()).getEmail());
        System.out.println("Principal Authorities are: " + authentication.getAuthorities().toString());
        */

        ClientDTO getClientObjectAccountProfileByEmail = clientProfileService.getClientAccountProfileByEmail(email);
        return new ResponseEntity<ClientDTO> (getClientObjectAccountProfileByEmail, HttpStatus.OK);
    }

    @PreAuthorize("(hasAnyAuthority('ROLE_CLIENT') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/update_profile", method = RequestMethod.POST)
    public ResponseEntity updateClientProfileByClientEmail(@RequestParam(value = "email") String email,
                                                       @RequestParam(value = "firstName") String firstName,
                                                       @RequestParam(value = "lastName") String lastName,
                                                       @RequestParam(value = "birthDate") String birthDate,
                                                       @RequestParam(value = "gender") Gender gender)
            throws AppException

    {
        ClientDTO userTryingToUpdateAccountDetails = new ClientDTO(email);

        // update user profile
        userTryingToUpdateAccountDetails.setFirstName(firstName);
        userTryingToUpdateAccountDetails.setLastName(lastName);
        userTryingToUpdateAccountDetails.setBirthDateFromString(birthDate); // yyyy-MM-dd
        userTryingToUpdateAccountDetails.setGender(gender);

        String userAccountEmailForSuccessfulUpdate = clientProfileService.updateClientProfile(userTryingToUpdateAccountDetails);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Client " + userAccountEmailForSuccessfulUpdate
                        + " udpated successfully Account Details " + email), HttpStatus.OK);
    }

}

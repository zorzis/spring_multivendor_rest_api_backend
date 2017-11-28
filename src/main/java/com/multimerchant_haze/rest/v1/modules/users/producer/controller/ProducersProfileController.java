package com.multimerchant_haze.rest.v1.modules.users.producer.controller;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerProfileService;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zorzis on 6/8/2017.
 */
@RestController
public class ProducersProfileController
{
    @Autowired
    private ProducerProfileService producerProfileService;

    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/get_profile", method = RequestMethod.POST)
    public ResponseEntity getProducerAccountProfileByEmail(@RequestParam(value = "email") String email) throws AppException

    {
        /*
        Authentication authentication = authenticationFacadeInterface.getAuthentication();
        System.out.println("Principal is: " + ((JwtUser)authentication.getPrincipal()).getEmail());
        System.out.println("Principal Authorities are: " + authentication.getAuthorities().toString());
        */

        ProducerDTO getProducerObjectAccountProfileByEmail = producerProfileService.getProducerAccountProfileByEmail(email);
        return new ResponseEntity<ProducerDTO> (getProducerObjectAccountProfileByEmail, HttpStatus.OK);
    }

    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/update_profile", method = RequestMethod.POST)
    public ResponseEntity updateProducerProfileByProducerEmail(@RequestParam(value = "email") String email,
                                                           @RequestParam(value = "firstName") String firstName,
                                                           @RequestParam(value = "lastName") String lastName,
                                                           @RequestParam(value = "birthDate") String birthDate,
                                                           @RequestParam(value = "gender") Gender gender)
            throws AppException

    {
        ProducerDTO userTryingToUpdateAccountDetails = new ProducerDTO(email);

        // update user profile
        userTryingToUpdateAccountDetails.setFirstName(firstName);
        userTryingToUpdateAccountDetails.setLastName(lastName);
        userTryingToUpdateAccountDetails.setBirthDateFromString(birthDate); // yyyy-MM-dd
        userTryingToUpdateAccountDetails.setGender(gender);

        String userAccountEmailForSuccessfulUpdate = producerProfileService.updateProducerProfile(userTryingToUpdateAccountDetails);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Producer " + userAccountEmailForSuccessfulUpdate
                        + " udpated successfully Account Details " + email), HttpStatus.OK);
    }
}

package com.multimerchant_haze.rest.v1.modules.users.producer.controller;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAddressDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerAddressesService;
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
public class ProducersAddressesController
{
    @Autowired
    private ProducerAddressesService producerAddressesService;

    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/get_addresses", method = RequestMethod.POST)
    public ResponseEntity getProducerAddresses(@RequestParam(value = "email") String email)
            throws AppException
    {
        ProducerDTO getProducerObjectFetchingProfileFetchingAddresses = producerAddressesService.getProducerAddress(email);
        return new ResponseEntity<ProducerDTO> (getProducerObjectFetchingProfileFetchingAddresses, HttpStatus.OK);
    }


    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/add_address", method = RequestMethod.POST)
    public ResponseEntity addNewAddressToProducerByEmailAndAddressDetails(@RequestParam(value = "email") String email,
                                                @RequestParam(value = "street") String address,
                                                @RequestParam(value = "streetNumber") String streetNumber,
                                                @RequestParam(value = "city") String city,
                                                @RequestParam(value = "postalCode") String postalCode,
                                                @RequestParam(value = "state") String state,
                                                @RequestParam(value = "country") String country,
                                                @RequestParam(value = "latitude") String latitude,
                                                @RequestParam(value = "longitude") String longitude,
                                                @RequestParam(value = "floor") String floor)
            throws AppException

    {
        ProducerDTO userTryingToAddAddress = new ProducerDTO(email);

        ProducerAddressDTO producerAddressDTO = new ProducerAddressDTO();
        producerAddressDTO.setStreet(address);
        producerAddressDTO.setStreetNumber(streetNumber);
        producerAddressDTO.setCity(city);
        producerAddressDTO.setPostalCode(postalCode);
        producerAddressDTO.setState(state);
        producerAddressDTO.setCountry(country);
        producerAddressDTO.setLatitude(latitude);
        producerAddressDTO.setLongitude(longitude);
        producerAddressDTO.setFloor(floor);


        String createdAddressForUser = producerAddressesService.addAddressToProducerByProducerEmailAndAddressDTO(userTryingToAddAddress, producerAddressDTO);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Address: " + createdAddressForUser + " added successfully for Producer " + email), HttpStatus.OK);
    }



    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/delete_address", method = RequestMethod.POST)
    public ResponseEntity deleteProducerAddressByProducerEmailAndAddressID(@RequestParam(value = "email") String email,
                                                                           @RequestParam(value = "addressID") String addressID)
            throws AppException

    {
        ProducerDTO producerTryingToDeleteAuthority = new ProducerDTO(email);

        ProducerAddressDTO addressWantedToBeRemoved = new ProducerAddressDTO();
        addressWantedToBeRemoved.setId(Long.parseLong(addressID));

        String addressIDFromSuccessfulUserAddressDeletion = producerAddressesService.deleteAddressFromProducerByProducerEmailAndAddress(producerTryingToDeleteAuthority, addressWantedToBeRemoved);
        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "ProducerAddress " + addressIDFromSuccessfulUserAddressDeletion
                        + " removed successfully for Producer " + email), HttpStatus.OK);
    }



    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/update_address", method = RequestMethod.POST)
    public ResponseEntity updateProducerAddressByEmailAndAddressDetails(@RequestParam(value = "email") String email,
                                                  @RequestParam(value = "street") String address,
                                                  @RequestParam(value = "streetNumber") String streetNumber,
                                                  @RequestParam(value = "city") String city,
                                                  @RequestParam(value = "postalCode") String postalCode,
                                                  @RequestParam(value = "state") String state,
                                                  @RequestParam(value = "country") String country,
                                                  @RequestParam(value = "latitude") String latitude,
                                                  @RequestParam(value = "longitude") String longitude,
                                                  @RequestParam(value = "floor") String floor)
            throws AppException

    {
        ProducerDTO userTryingToAddAddress = new ProducerDTO(email);

        ProducerAddressDTO producerAddressDTO = new ProducerAddressDTO();
        producerAddressDTO.setStreet(address);
        producerAddressDTO.setStreetNumber(streetNumber);
        producerAddressDTO.setCity(city);
        producerAddressDTO.setPostalCode(postalCode);
        producerAddressDTO.setState(state);
        producerAddressDTO.setCountry(country);
        producerAddressDTO.setLatitude(latitude);
        producerAddressDTO.setLongitude(longitude);
        producerAddressDTO.setFloor(floor);


        String updatedAddressForProducer = producerAddressesService.updateAddressOfProducerByProducerEmailAndAddressDTO(userTryingToAddAddress, producerAddressDTO);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Address: " + updatedAddressForProducer + " updated successfully for Producer " + email), HttpStatus.OK);
    }


}

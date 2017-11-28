package com.multimerchant_haze.rest.v1.modules.users.client.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAddressDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_addresses.ClientAddressesService;
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
 * Created by zorzis on 5/27/2017.
 */
@RestController
public class ClientsAddressesController
{
    @Autowired
    private ClientAddressesService clientAddressesService;

    @PreAuthorize("(hasAnyAuthority('ROLE_CLIENT') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/get_addresses", method = RequestMethod.POST)
    @JsonView(JsonACL.ClientsView.class)
    public ResponseEntity getClientAddresses(@RequestParam(value = "email") String email)
            throws AppException
    {
        ClientDTO getClientObjectFetchingProfileFetchingAddresses = clientAddressesService.getClientAddresses(email);
        return new ResponseEntity<ClientDTO> (getClientObjectFetchingProfileFetchingAddresses, HttpStatus.OK);
    }


    @PreAuthorize("(hasAnyAuthority('ROLE_CLIENT') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/add_address", method = RequestMethod.POST)
    public ResponseEntity addNewAddressToClient(@RequestParam(value = "email") String email,
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
        ClientDTO userTryingToAddAddress = new ClientDTO(email);

        ClientAddressDTO clientAddressDTO = new ClientAddressDTO();
        clientAddressDTO.setStreet(address);
        clientAddressDTO.setStreetNumber(streetNumber);
        clientAddressDTO.setCity(city);
        clientAddressDTO.setPostalCode(postalCode);
        clientAddressDTO.setState(state);
        clientAddressDTO.setCountry(country);
        clientAddressDTO.setLatitude(latitude);
        clientAddressDTO.setLongitude(longitude);
        clientAddressDTO.setFloor(floor);


        String createdAddressForUser = clientAddressesService.addAddressToClientByClientEmailAndAddressDTO(userTryingToAddAddress, clientAddressDTO);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Address: " + createdAddressForUser + " added successfully for Client " + email), HttpStatus.OK);
    }



    @PreAuthorize("(hasAnyAuthority('ROLE_CLIENT') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/delete_address", method = RequestMethod.POST)
    public ResponseEntity deleteClientAddressByClientEmailAndAddressID(@RequestParam(value = "email") String email,
                                                                       @RequestParam(value = "addressID") String addressID)
            throws AppException

    {
        ClientDTO clientTryingToDeleteAddress = new ClientDTO(email);

        ClientAddressDTO addressWantedToBeRemoved = new ClientAddressDTO();
        addressWantedToBeRemoved.setId(Long.parseLong(addressID));

        String addressIDFromSuccessfulUserAddressDeletion = clientAddressesService.deleteAddressFromClientByClientEmailAndAddress(clientTryingToDeleteAddress, addressWantedToBeRemoved);
        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "ClientAddress " + addressIDFromSuccessfulUserAddressDeletion
                        + " removed successfully for Client " + email), HttpStatus.OK);
    }

}

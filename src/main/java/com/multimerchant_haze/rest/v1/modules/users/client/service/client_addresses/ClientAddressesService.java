package com.multimerchant_haze.rest.v1.modules.users.client.service.client_addresses;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAddressDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;

/**
 * Created by zorzis on 5/27/2017.
 */
public interface ClientAddressesService
{
    public ClientDTO getClientAddresses(String email) throws AppException;

    // add Address to Client
    public String addAddressToClientByClientEmailAndAddressDTO(ClientDTO clientDTO, ClientAddressDTO clientAddressDTO) throws AppException;

    // delete Address From Client
    public String deleteAddressFromClientByClientEmailAndAddress(ClientDTO clientDTO, ClientAddressDTO clientAddressDTO) throws AppException;

}

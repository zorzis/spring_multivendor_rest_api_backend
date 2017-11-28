package com.multimerchant_haze.rest.v1.modules.users.client.service.client_service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAuthorityDTO;

import java.util.List;

/**
 * Created by zorzis on 5/8/2017.
 */
public interface ClientService
{

    public List<ClientDTO> getAllClientsNoFiltering();

    // get a user
    public ClientDTO getClientByEmail(String email) throws AppException;

    public ClientDTO getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(String email) throws AppException;

    // delete a Client
    public String deleteClientByClientEmail(ClientDTO clientDTO) throws AppException;

    // delete user authority
    public String deleteClientAuthorityByClientEmailAndAuthorityID(ClientDTO clientDTO, ClientAuthorityDTO clientAuthorityDTO) throws AppException;

    // add ClientAuthority to Client
    public String addAuthorityToClientByClientEmailAndAuthorityName(ClientDTO clientDTO, ClientAuthorityDTO clientAuthorityDTO) throws AppException;


}



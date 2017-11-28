package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientHasAddress;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientHasAuthority;

import java.util.List;

/**
 * Created by zorzis on 5/8/2017.
 */
public interface ClientDAO
{

    public List<Client> getAllClientsNoFiltering();

    // get a Client
    public Client getClientByEmailOnlyFromClientsTable(String clientEmail);

    // get a Client Fetching Profile
    public Client getClientByClientEmailFetchingProfileFetchVerificationToken(String clientEmail);

    // get Client Addresses fetching also Profile
    public Client getClientByEmailFetchingAddressesFetchingProfile(String clientEmail);

    // get a Client Fetching Profile Fetching UserAuthorities
    public Client getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(String clientEmail);

    // create a new Client
    public String createClient(Client client);

    public Client registerNewClient(Client client);

    // delete a Client
    public String deleteClient(Client client);

    // delete Client ClientAuthority
    public String deleteClientHasAuthority(ClientHasAuthority clientHasAuthority);

    // delete Client ClientAddress
    public String deleteClientHasAddress(ClientHasAddress clientHasAddress);


    // add Client ClientAuthority
    public String addClientHasAuthority(ClientHasAuthority clientHasAuthority);

    // add Client ClientAddress
    public String addClientHasAddress(ClientHasAddress clientHasAddress);


    // update a Client
    public String updateClient(Client client);
}
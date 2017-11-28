package com.multimerchant_haze.rest.v1.modules.users.client.service.client_addresses;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAddressDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAddress;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientHasAddress;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by zorzis on 5/27/2017.
 */
@Service
public class ClientAddressesServiceImpl implements ClientAddressesService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAddressesServiceImpl.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ClientDAO clientDAO;


    @Override
    @Transactional("transactionManager")
    public ClientDTO getClientAddresses(String email) throws AppException
    {
        // the user entity retrieved from database based on email
        Client clientByEmail = clientDAO.getClientByEmailFetchingAddressesFetchingProfile(email);
        // the user data transfer object we gonna create
        ClientDTO clientDTO = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail, "email", email);
        clientDTO.mapAddressesDTOsFromAddressesEntities(clientByEmail.getClientAddressesEntities());
        return clientDTO;

    }


    @Override
    @Transactional("transactionManager")
    public String addAddressToClientByClientEmailAndAddressDTO(ClientDTO clientDTO, ClientAddressDTO clientAddressDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Client clientByEmail = clientDAO.getClientByEmailOnlyFromClientsTable(clientDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        ClientDTO clientDTOafterSuccessfullVerificationForExistence = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail,"Email", clientDTO.getEmail());


        ClientAddress clientAddressToBeAdded = new ClientAddress(clientAddressDTO);
        clientAddressToBeAdded.setCreatedAt(new Date());


        ClientHasAddress clientHasAddressToBeAdded = new ClientHasAddress();
        clientHasAddressToBeAdded.setClient(clientByEmail);
        clientHasAddressToBeAdded.setClientAddress(clientAddressToBeAdded);

        return clientDAO.addClientHasAddress(clientHasAddressToBeAdded);
    }



    @Override
    @Transactional("transactionManager")
    public String deleteAddressFromClientByClientEmailAndAddress(ClientDTO clientDTO, ClientAddressDTO clientAddressDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Client clientByEmail = clientDAO.getClientByEmailOnlyFromClientsTable(clientDTO.getEmail());

        // Check if user already exists else throw AppException and stop process
        ClientDTO clientDTOafterSuccessfullVerificationForExistence = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail,"Email", clientDTO.getEmail());

        ClientAddress clientAddressForDeletion = new ClientAddress(clientAddressDTO);

        ClientHasAddress clientHasAddressToBeDeleted = this.checkIfClientHasSpecificAddressByAddressID(clientByEmail, clientAddressDTO);

        if(clientHasAddressToBeDeleted == null)
        {
            LOGGER.debug("ClientAddress [" + clientAddressDTO.getId() + "] is not part of client [" + clientByEmail.getEmail() + "] addresses");

            StringBuilder sb = new StringBuilder();
            sb.append("Failed to delete Address");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientAddressDTO.getId());
            sb.append("]");
            sb.append(" ");
            sb.append("from Client with Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("because Address does not exist to client addresses.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Address is not found to specific client addresses.");
            throw appException;
        }

        clientHasAddressToBeDeleted.setClientAddress(clientAddressForDeletion);
        return clientDAO.deleteClientHasAddress(clientHasAddressToBeDeleted);
    }



    private ClientHasAddress checkIfClientHasSpecificAddressByAddressID(Client client, ClientAddressDTO clientAddressDTO)
    {
        ClientHasAddress clientHasAddressToBeVerifiedThatExists = null;

        // Verify ClientAuthority does not exist to Client Authorities
        for(ClientHasAddress clientHasAddress : client.getClientHasAddresses())
        {
            if(clientHasAddress.getClientAddress().getId().equals(clientAddressDTO.getId()))
            {
                clientHasAddressToBeVerifiedThatExists = clientHasAddress;
            }
        }

        return clientHasAddressToBeVerifiedThatExists;
    }
}

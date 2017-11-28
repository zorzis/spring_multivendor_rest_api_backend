package com.multimerchant_haze.rest.v1.modules.users.client.service.client_service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientAuthorityDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAuthority;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientHasAuthority;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zorzis on 5/8/2017.
 */
@Service
public class ClientServiceImplementation implements ClientService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImplementation.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    ClientAuthorityDAO clientAuthorityDAO;



    /********************* CREATE related methods implementation ***********************/
    @Override
    @Transactional("transactionManager")
    public String addAuthorityToClientByClientEmailAndAuthorityName(ClientDTO clientDTO, ClientAuthorityDTO clientAuthorityDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Client clientByEmail = clientDAO.getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(clientDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        ClientDTO clientDTOafterSuccessfullVerificationForExistence = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail,"Email", clientDTO.getEmail());

        // Check if authority exists at database
        ClientAuthority clientAuthorityToBeAdded = clientAuthorityDAO.getAuthorityByAuthorityName(clientAuthorityDTO.getRole());
        // TODO: 3/30/2017 In Production don't create new Authority If not already Exists
        // todo we don't want to assign new Authorities If Not Already Exist in Database
        if(clientAuthorityToBeAdded == null)
        {
            LOGGER.debug("ClientAuthority [" + clientAuthorityDTO.getRole() + "]  to be assigned to user " + clientByEmail.getEmail() + " Does Not Exists at Database Authorities Table");

            StringBuilder sb = new StringBuilder();
            sb.append("Failed to assign Authority");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientAuthorityDTO.getRole());
            sb.append("]");
            sb.append(" ");
            sb.append("to Client with Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("because Authority does not exists to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Authority does not exists to our system.System cannot complete authority assignment");
            throw appException;
        }



        // Check if user has already assigned the authority
        ClientHasAuthority clientHasAuthorityToBeAdded = this.checkIfClientHasAuthorityByAuthorityName(clientByEmail, clientAuthorityDTO);
        if(clientHasAuthorityToBeAdded != null)
        {
            LOGGER.debug("ClientAuthority [" + clientAuthorityToBeAdded.getRole() + "] is already assigned to user [" + clientByEmail.getEmail() + "].");

            StringBuilder sb = new StringBuilder();
            sb.append("Failed to assign Authority");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientAuthorityDTO.getRole());
            sb.append("]");
            sb.append(" ");
            sb.append("to Client with Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("because Authority is already assigned to user.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Authority is already assigned to specific user.");
            throw appException;
        }


        clientHasAuthorityToBeAdded = new ClientHasAuthority();
        clientHasAuthorityToBeAdded.setClient(clientByEmail);
        clientHasAuthorityToBeAdded.setClientAuthority(clientAuthorityToBeAdded);


        return clientDAO.addClientHasAuthority(clientHasAuthorityToBeAdded);
    }







    /********************* READ related methods implementation ***********************/

    @Override
    public List<ClientDTO> getAllClientsNoFiltering()
    {
        return null;
    }



    @Override
    public ClientDTO getClientByEmail(String email) throws AppException
    {
        // the user entity retrieved from database based on email
        Client clientByEmail = clientDAO.getClientByEmailOnlyFromClientsTable(email);
        // the user data transfer object we gonna create
        // the check throws AppException
        ClientDTO clientDTO = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail, "email", email);
        return clientDTO;
    }


    @Override
    @Transactional("transactionManager")
    public ClientDTO getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(String email) throws AppException
    {
        // the user entity retrieved from database based on email
        Client clientByEmail = clientDAO.getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(email);
        // the user data transfer object we gonna create
        ClientDTO clientDTO = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail, "email", email);
        LOGGER.debug("Client Entity from " + className + "::getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses " + clientByEmail.toString());
        LOGGER.debug("Client Entity Authorities from " + className + "::getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses " + clientByEmail.printClientAuthorities());
        LOGGER.debug("Client Entity Addresses from " + className + "::getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses " + clientByEmail.printClientAddresses());
        clientDTO.mapAuthoritiesDTOsFromAuthoritiesEntities(clientByEmail.getClientAuthoritiesEntities());
        clientDTO.mapAddressesDTOsFromAddressesEntities(clientByEmail.getClientAddressesEntities());
        LOGGER.debug("Client DTO from " + className + "::getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses " + clientDTO.toString());
        return clientDTO;
    }



    /********************* DELETE related methods implementation ***********************/
    @Override
    @Transactional("transactionManager")
    public String deleteClientByClientEmail(ClientDTO clientDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Client clientByEmail = clientDAO.getClientByEmailOnlyFromClientsTable(clientDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        ClientDTO clientDTOafterSuccessfullVerificationForExistence = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail,"Email", clientDTO.getEmail());
        return clientDAO.deleteClient(clientByEmail);
    }


    @Override
    @Transactional("transactionManager")
    public String deleteClientAuthorityByClientEmailAndAuthorityID(ClientDTO clientDTO, ClientAuthorityDTO clientAuthorityDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Client clientByEmail = clientDAO.getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(clientDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        ClientDTO clientDTOafterSuccessfullVerificationForExistence = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail,"Email", clientDTO.getEmail());

        ClientHasAuthority clientHasAuthorityToBeDeleted = this.checkIfClientHasSpecificAuthorityByAuthorityID(clientByEmail, clientAuthorityDTO);

        if(clientHasAuthorityToBeDeleted == null)
        {
            LOGGER.debug("ClientAuthority [" + clientAuthorityDTO.getAuthorityID() + "] is not assigned to user [" + clientByEmail.getEmail() + "].");

            StringBuilder sb = new StringBuilder();
            sb.append("Failed to delete Authority");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientAuthorityDTO.getAuthorityID());
            sb.append("]");
            sb.append(" ");
            sb.append("from Client with Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("because Authority is not assigned to user.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Authority is not assigned to specific user.");
            throw appException;
        }

        return clientDAO.deleteClientHasAuthority(clientHasAuthorityToBeDeleted);
    }




    /********************* helper private methods implementation ***********************/

    // Check if UserEntity has an Authority by Authority name/role
    private ClientHasAuthority checkIfClientHasAuthorityByAuthorityName(Client client, ClientAuthorityDTO clientAuthorityDTO)
    {
        ClientHasAuthority clientHasAuthorityToBeVerifiedThatExists = null;

        // Verify ClientAuthority does not exist to Client Authorities
        for(ClientHasAuthority clientHasAuthority : client.getClientHasAuthorities())
        {
            if(clientHasAuthority.getClientAuthority().getRole().equals(clientAuthorityDTO.getRole()))
            {
                clientHasAuthorityToBeVerifiedThatExists = clientHasAuthority;
            }
        }

        return clientHasAuthorityToBeVerifiedThatExists;
    }

    private ClientHasAuthority checkIfClientHasSpecificAuthorityByAuthorityID(Client client, ClientAuthorityDTO clientAuthorityDTO)
    {
       ClientHasAuthority clientHasAuthorityToBeVerifiedThatExists = null;

        // Verify ClientAuthority does not exist to Client Authorities
        for(ClientHasAuthority clientHasAuthority : client.getClientHasAuthorities())
        {
            if(clientHasAuthority.getClientAuthority().getAuthorityID().equals(clientAuthorityDTO.getAuthorityID()))
            {
                clientHasAuthorityToBeVerifiedThatExists = clientHasAuthority;
            }
        }

        return clientHasAuthorityToBeVerifiedThatExists;
    }
}

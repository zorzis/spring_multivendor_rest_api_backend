package com.multimerchant_haze.rest.v1.modules.users.client.service.client_registration;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientAuthorityDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientProfile;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.helpers.RegisterAccountHelper;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAuthority;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_verification_token.ClientVerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 5/27/2017.
 */
@Service
public class ClientRegistrationServiceImpl implements ClientRegistrationService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRegistrationServiceImpl.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    ClientAuthorityDAO clientAuthorityDAO;

    @Autowired
    ClientVerificationTokenService clientVerificationTokenService;

    @Override
    @Transactional("transactionManager")
    public Client registerNewClient(ClientDTO clientDTO) throws AppException
    {
        // validate email, username, password provided and are not NULL
        this.validateNotNullInputForClientRegistration(clientDTO);

        //verify existence of controller in the db (email must be unique)
        Client clientByEmail = clientDAO.getClientByClientEmailFetchingProfileFetchVerificationToken(clientDTO.getEmail());

        if( clientByEmail != null)
        {
            LOGGER.debug("Registration Failed!!! Email " +  clientByEmail.getEmail() + " is already registered.");

            StringBuilder sb = new StringBuilder();
            sb.append("Registration Failed!!!");
            sb.append("Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(clientByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("is already registered to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Client already exists on our system.Registration could not be completed!");
            throw appException;
        }

        // Set the Default Registration Authorities for the new Client
        clientDTO.addAuthorityByName("ROLE_CLIENT");

        // check the given user authorities if exist in database
        // throws AppException if Authority does not exist in database UserAuthorities Table
        Set<ClientAuthority> clientAuthoritiesEntities = verifyAuthoritiesExistenceInDatabase(clientDTO.getAuthorities());

        RegisterAccountHelper registerAccountHelper = new RegisterAccountHelper();

        // generate user unique userID using generator from helper class
        String userID = registerAccountHelper.generateUserUniqueID("CLIENT");

        // hash the user input clear text password so that no password recovery can be done neither from
        // application side
        String hashedUserPassword = registerAccountHelper.hashUserInputPassword(clientDTO.getPassword());

        // encrypt the hashed password so that we keep it safe at database and only with our secret key
        // to be decrypted. We also use the Encrypt then Mac standard
        String encryptedHashedUserPassword = registerAccountHelper.encryptHashedPassword(hashedUserPassword);

        // clientDTO.setClientID(userID);
        clientDTO.setPassword(encryptedHashedUserPassword);
        clientDTO.setCreatedAt(new Date());
        clientDTO.setEnabled(false);

        // initialie the Client Entity to be persisted in Database
        Client clientToBeRegistered = new Client(clientDTO);

        // initialize the Authorities Entities assigned to Client Entity
        // using the intermediate ClientHasAuthority Class to add the Authorities
        clientToBeRegistered.addMultipleClientAuthorities(clientAuthoritiesEntities);

        // initialize the ClientProfile Entity
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setClientID(userID);
        clientProfile.setClient(clientToBeRegistered);

        // attach the coresponding entities to client
        clientToBeRegistered.setClientProfile(clientProfile);

        LOGGER.debug("Client Profile to be saved is: " + clientProfile.toString());

        Client registeredClient = this.clientDAO.registerNewClient(clientToBeRegistered);

        return registeredClient;
    }






    /********************* helper private methods implementation ***********************/

    // Check if user Register credentials are not NULL
    private void validateNotNullInputForClientRegistration(ClientDTO clientDTO) throws AppException
    {
        if(clientDTO.getEmail() == null || clientDTO.getEmail().isEmpty())
        {
            LOGGER.debug("Email is missing.Provided data not sufficient for insertion" +
                    "Please verify that the email is properly generated/set");

            StringBuilder sb = new StringBuilder();
            sb.append("Email is missing!!!");
            sb.append("Provided data not sufficient for authentication");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Please verify that the email is properly generated/set");
            throw appException;
        }
        if(clientDTO.getPassword() == null || clientDTO.getPassword().isEmpty())
        {
            LOGGER.debug("Password is missing.Provided data not sufficient for insertion" +
                    "Please verify that the password is properly generated/set");

            StringBuilder sb = new StringBuilder();
            sb.append("Password is missing!!!");
            sb.append("Provided data not sufficient for authentication");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Please verify that the email is properly generated/set");
            throw appException;
        }
    }


    private Set<ClientAuthority> verifyAuthoritiesExistenceInDatabase(Set<ClientAuthorityDTO> clientAuthoritiesDTO) throws AppException
    {
        Set<ClientAuthority> clientAuthoritiesEntities = new HashSet<>(0);

        ClientAuthority clientAuthorityEntityByAuthorityDTOAuthorityName;

        for(ClientAuthorityDTO clientAuthorityDTO :clientAuthoritiesDTO)
        {
            String authorityName = clientAuthorityDTO.getRole();

            // check if authority by unique name exists at UserAuthorities Table
            clientAuthorityEntityByAuthorityDTOAuthorityName = clientAuthorityDAO.getAuthorityByAuthorityName(authorityName);

            if(clientAuthorityEntityByAuthorityDTOAuthorityName == null)
            {
                LOGGER.debug("Registration Failed!!! ClientAuthority " + authorityName + " don't exist");
                StringBuilder sb = new StringBuilder();
                sb.append("Registration Failed!!!");
                sb.append("Failed to assign Authority");
                sb.append(":");
                sb.append(" ");
                sb.append("[");
                sb.append(authorityName);
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
            else
            {
                LOGGER.debug("ClientAuthority Entity details from " + className + "::registerNewClient is: " + clientAuthorityEntityByAuthorityDTOAuthorityName.toString());
                clientAuthoritiesEntities.add(clientAuthorityEntityByAuthorityDTOAuthorityName);
            }
        }

        return clientAuthoritiesEntities;
    }

}

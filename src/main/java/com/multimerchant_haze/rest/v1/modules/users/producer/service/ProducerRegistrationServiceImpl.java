package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerAuthorityDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAuthority;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerProfile;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.helpers.RegisterAccountHelper;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
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
 * Created by zorzis on 6/7/2017.
 */
@Service
public class ProducerRegistrationServiceImpl implements ProducerRegistrationService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerRegistrationServiceImpl.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ProducerDAO producerDAO;

    @Autowired
    ProducerAuthorityDAO producerAuthorityDAO;


    @Override
    @Transactional("transactionManager")
    public String registerNewProducer(ProducerDTO producerDTO) throws AppException
    {
        // validate email, username, password provided and are not NULL
        validateNotNullInputForProducerRegistration(producerDTO);

        //verify existence of controller in the db (email must be unique)
        Producer userByEmail = producerDAO.getProducerByEmailOnlyFromProducersTable(producerDTO.getEmail());
        if( userByEmail != null)
        {
            LOGGER.debug("Registration Failed!!! Email " +  userByEmail.getEmail() + " is already registered.");

            StringBuilder sb = new StringBuilder();
            sb.append("Registration Failed!!!");
            sb.append("Email");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(userByEmail.getEmail());
            sb.append("]");
            sb.append(" ");
            sb.append("is already registered to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Producer already exists on our System.Registration could not be completed!");
            throw appException;
        }

        // Set the Default Registration Authorities for the new Producer
        producerDTO.addAuthorityByName("ROLE_PRODUCER");

        // check the given user authorities if exist in database
        // throws AppException if Authority does not exist in database UserAuthorities Table
        Set<ProducerAuthority> userAuthoritiesEntities = verifyAuthoritiesExistenceInDatabase(producerDTO.getAuthorities());

        RegisterAccountHelper registerAccountHelper = new RegisterAccountHelper();

        // generate user unique userID using generator from helper class
        String userID = registerAccountHelper.generateUserUniqueID("producer");

        // hash the user input clear text password so that no password recovery can be done neither from
        // application side
        String hashedUserPassword = registerAccountHelper.hashUserInputPassword(producerDTO.getPassword());

        // encrypt the hashed password so that we keep it safe at database and only with our secret key
        // to be decrypted. We also use the Encrypt then Mac standard
        String encryptedHashedUserPassword = registerAccountHelper.encryptHashedPassword(hashedUserPassword);

        //producerDTO.setProducerID(userID);
        producerDTO.setPassword(encryptedHashedUserPassword);
        producerDTO.setCreatedAt(new Date());
        producerDTO.setEnabled(false);

        // initialie the Producer Entity to be persisted in Database
        Producer producerEntityToBeCreatedAtDatabase = new Producer(producerDTO);

        // initialize the Authorities Entities assigned to Producer Entity
        // using the intermediate ProducerHasAuthority Class to add the Authorities
        producerEntityToBeCreatedAtDatabase.addMultipleProducerAuthorities(userAuthoritiesEntities);

        // initialize the ProducerProfile Entity
        ProducerProfile producerProfile = new ProducerProfile();
        producerProfile.setProducerID(userID);
        producerEntityToBeCreatedAtDatabase.setProducerProfile(producerProfile);

        return producerDAO.registerNewProducer(producerEntityToBeCreatedAtDatabase);
    }


    // Check if user Register credentials are not NULL
    private void validateNotNullInputForProducerRegistration(ProducerDTO producerDTO) throws AppException
    {
        if(producerDTO.getEmail() == null || producerDTO.getEmail().isEmpty())
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
        if(producerDTO.getPassword() == null || producerDTO.getPassword().isEmpty())
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
            appException.setDevelopersMessageExtraInfoAsSingleReason("Please verify that the password is properly generated/set");
            throw appException;
        }
    }


    private Set<ProducerAuthority> verifyAuthoritiesExistenceInDatabase(Set<ProducerAuthorityDTO> producerAuthoritiesDTO) throws AppException
    {
        Set<ProducerAuthority> producerAuthoritiesEntities = new HashSet<>(0);

        ProducerAuthority producerAuthorityEntityByAuthorityDTOAuthorityName;

        for(ProducerAuthorityDTO producerAuthorityDTO :producerAuthoritiesDTO)
        {
            String authorityName = producerAuthorityDTO.getRole();

            // check if authority by unique name exists at UserAuthorities Table
            producerAuthorityEntityByAuthorityDTOAuthorityName = producerAuthorityDAO.getAuthorityByAuthorityName(authorityName);

            if(producerAuthorityEntityByAuthorityDTOAuthorityName == null)
            {
                LOGGER.debug("Registration Failed!!! ProducerAuthority " + authorityName + " don't exist");
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
                LOGGER.debug("ProducerAuthority Entity details from " + className + "::registerNewProducer is: " + producerAuthorityEntityByAuthorityDTOAuthorityName.toString());
                producerAuthoritiesEntities.add(producerAuthorityEntityByAuthorityDTOAuthorityName);
            }
        }

        return producerAuthoritiesEntities;
    }

}

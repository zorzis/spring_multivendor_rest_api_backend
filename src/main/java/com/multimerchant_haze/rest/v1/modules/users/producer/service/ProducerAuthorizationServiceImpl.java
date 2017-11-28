package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.helpers.LoginAccountHelper;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.utils.JwtTokenUtil;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Created by zorzis on 6/7/2017.
 */
@Service
public class ProducerAuthorizationServiceImpl implements ProducerAuthorizationService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerAuthorizationServiceImpl.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ProducerDAO producerDAO;

    @Override
    public String authorizeProducerProducingJWToken(ProducerDTO producerDTO) throws AppException, Exception
    {
        String accessToken = null;

        // validate email, password provided and are not NULL
        validateNotNullInputForProducerAuthentication(producerDTO);

        //verify existence of controller in the db (email must be unique)
        Producer producerByEmail = producerDAO.getProducerByEmailFetchingProfileFetchingAuthoritiesFetchingAddress(producerDTO.getEmail());

        ProducerDTO verifiedProducerDTOThatExistsOnDBbyEmail = UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail, "Email", producerDTO.getEmail());

        LoginAccountHelper loginAccountHelper = new LoginAccountHelper();

        // Decrypt the userByEmail Password from database so take the pure Hashed Producer Password
        // to be checked against user provided password
        String hashedPasswordOfTryingToLoginProducer = loginAccountHelper.decryptUserStoredPassword(producerByEmail.getPassword());

        // Check if password hashes match means credentials provided are OK
        loginAccountHelper.checkIfPasswordHashesMatch(producerDTO.getPassword(), hashedPasswordOfTryingToLoginProducer);


        ProducerDTO verifiedProducer = new ProducerDTO(producerByEmail);
        verifiedProducer.mapAuthoritiesDTOsFromAuthoritiesEntities(producerByEmail.getProducerAuthoritiesEntities());

        LOGGER.debug("-----------------------------------------------------------------------------------");
        LOGGER.debug("Veridied Producer Credentials");
        LOGGER.debug(verifiedProducer.toString());
        LOGGER.debug("-----------------------------------------------------------------------------------");

        // Produce the AccessToken for Authenticated Producer
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        accessToken = jwtTokenUtil.generateTokenForProducer(verifiedProducer);

        return accessToken;
    }


    // Check if user Register credentials are not NULL
    private void validateNotNullInputForProducerAuthentication(ProducerDTO producerDTO) throws AppException
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
}

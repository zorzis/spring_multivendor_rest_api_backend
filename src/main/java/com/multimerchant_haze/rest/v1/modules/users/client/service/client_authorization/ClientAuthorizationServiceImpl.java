package com.multimerchant_haze.rest.v1.modules.users.client.service.client_authorization;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.helpers.LoginAccountHelper;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.utils.JwtTokenUtil;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Created by zorzis on 5/27/2017.
 */
@Service
public class ClientAuthorizationServiceImpl implements ClientAuthorizationService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAuthorizationServiceImpl.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ClientDAO clientDAO;

    @Override
    public String authorizeClientProducingJWToken(ClientDTO clientDTO) throws AppException, Exception
    {
        String accessToken = null;

        // validate email, password provided and are not NULL
        validateNotNullInputForClientAuthentication(clientDTO);

        //verify existence of controller in the db (email must be unique)
        Client clientByEmail = clientDAO.getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(clientDTO.getEmail());

        ClientDTO verifiedClientDTOThatExistsOnDBbyEmail = UserServiceHelper.createClientDTOIfClientEntityExists(clientByEmail, "Email", clientDTO.getEmail());

        LoginAccountHelper loginAccountHelper = new LoginAccountHelper();

        // Decrypt the userByEmail Password from database so take the pure Hashed Client Password
        // to be checked against user provided password
        String hashedPasswordOfTryingToLoginClient = loginAccountHelper.decryptUserStoredPassword(clientByEmail.getPassword());

        // Check if password hashes match means credentials provided are OK
        loginAccountHelper.checkIfPasswordHashesMatch(clientDTO.getPassword(), hashedPasswordOfTryingToLoginClient);


        // Check if Client is Activated(Throw Registration Email Confirmation Link)
        this.checkIfUserObjectIsActivatedUsingEmailRegistrationLink(clientByEmail);

        ClientDTO verifiedClient = new ClientDTO(clientByEmail);
        verifiedClient.mapAuthoritiesDTOsFromAuthoritiesEntities(clientByEmail.getClientAuthoritiesEntities());

        LOGGER.debug("-----------------------------------------------------------------------------------");
        LOGGER.debug("Veridied Client Credentials");
        LOGGER.debug(verifiedClient.toString());
        LOGGER.debug("-----------------------------------------------------------------------------------");

        // Produce the AccessToken for Authenticated Client
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        accessToken = jwtTokenUtil.generateTokenForClient(verifiedClient);

        return accessToken;
    }



    // Check if user Register credentials are not NULL
    private void validateNotNullInputForClientAuthentication(ClientDTO clientDTO) throws AppException
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


    private void checkIfUserObjectIsActivatedUsingEmailRegistrationLink(Client client) throws AppException
    {
        if(!client.getIsEnabled())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Account is not activated.!");
            sb.append(" ");
            sb.append("Please Activate your account using the activation link sent to: [");
            sb.append(client.getEmail());
            sb.append("].");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.FORBIDDEN);
            appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Account is not activated.");
            throw appException;
        }
    }

}

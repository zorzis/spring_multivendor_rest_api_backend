package com.multimerchant_haze.rest.v1.modules.users.client.service.client_verification_token;

import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientVerificationToken;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientVerificationTokenDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientVerificationTokenDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zorzis on 11/19/2017.
 */
@Service
public class ClientVerificationTokenServiceImplementation implements ClientVerificationTokenService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientVerificationTokenServiceImplementation.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    private ClientVerificationTokenDAO clientVerificationTokenDAO;

    @Autowired
    private ClientDAO clientDAO;


    private static final int EXPIRATION = 60 * 24;


    @Override
    @Transactional("transactionManager")
    public ClientVerificationTokenDTO createClientVerificationToken(Client client)
    {
        // Create the token UUID string and produce a new while there is already a client verification in the Database
        // with the same token
        String token = this.generateVerificationToken();
        ClientVerificationToken clientVerificationTokenFromDatabase = this.clientVerificationTokenDAO.getClientVerificationToken(token);
        while(clientVerificationTokenFromDatabase != null)
        {
            token = this.generateVerificationToken();
            clientVerificationTokenFromDatabase = this.clientVerificationTokenDAO.getClientVerificationToken(token);
        }

        // init the client entity from the clientDto
/*
        Client client = this.clientDAO.getClientByClientEmailFetchingProfileFetchVerificationToken(clientDTO.getEmail());
*/

        // init the clientVerificationToken Entity
        ClientVerificationToken clientVerificationToken = new ClientVerificationToken();
        clientVerificationToken.setToken(token);
        clientVerificationToken.setExpiryDate(this.calculateExpiryDate(EXPIRATION));

        client.setClientVerificationToken(clientVerificationToken);
        clientVerificationToken.setClient(client);


        // persist the ClientVerificationToken Entity to the Database
        Client clientAfterVerificationTokenPersistence = this.clientVerificationTokenDAO.persistClientVerificationToken(client);

        return new ClientVerificationTokenDTO(clientAfterVerificationTokenPersistence.getClientVerificationToken());
    }



    // Creates a unique UserID with a prefix
    private String generateVerificationToken()
    {
        String userUniqueID = null;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        StringBuilder sb = new StringBuilder();
        sb.append("VERIFICATION-TOKEN-");
        sb.append(uuid);
        userUniqueID = sb.toString();
        return userUniqueID;
    }

    // Calculates/Sets the expiration date of the token
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }


    private void checkIfTokenIsExpired(Date expirationDate) throws AppException
    {
        Calendar cal = Calendar.getInstance();
        if ((expirationDate.getTime() - cal.getTime().getTime()) <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("User Account Verification Token Expired!!!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("User Account Verification Token Expired!");
            throw appException;
        }
    }
}

package com.multimerchant_haze.rest.v1.modules.users.userAbstract.helpers;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.cryptoFactory.SymmetricCryptoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Created by zorzis on 3/2/2017.
 */
public class LoginAccountHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAccountHelper.class);
    private String className =  this.getClass().getSimpleName();

    private static final SymmetricCryptoProvider mySymmetricCryptoProvider = new SymmetricCryptoProvider();


    public String decryptUserStoredPassword(String userEncryptedStoredHashedPassword) throws Exception
    {
        LOGGER.debug("Inside " + className + "::decryptUserStoredPassword");
        LOGGER.debug("--Start Decryption of Producer encrypted password from database--");

        // We decrypt the encryptedStoredPassword retrieved from database
        String decryptedWithAESuserHashedPassword = null;

        decryptedWithAESuserHashedPassword = mySymmetricCryptoProvider.decrypt(userEncryptedStoredHashedPassword,
                    mySymmetricCryptoProvider.getSecretKeyPasswordForAuthentication());

        LOGGER.debug("Decrypted user password from database is: " + decryptedWithAESuserHashedPassword);

        LOGGER.debug("--End Decryption of Producer encrypted password from database--");

        return decryptedWithAESuserHashedPassword;
    }



    public void checkIfPasswordHashesMatch(String userInputPassword,
                                           String decryptedWithAESuserHashedPassword) throws AppException
    {
        LOGGER.debug("--Start Hashes Authentication--");
        LOGGER.debug("--info: the one previously retrieved from database and successfully decrypted AND one we gonna create from Producer provided password from input--");

        LOGGER.debug("Producer input password is: " + userInputPassword);
        LOGGER.debug("Producer stored password hash(previously decrypted) is: " + decryptedWithAESuserHashedPassword);

        boolean areHashesEqual = false;

        // Try to authenticate Hashes
        // In case of Internal Error of hash functionality catch the Exception, print it at logs
        // and threw an AppException

        try
        {
            areHashesEqual = mySymmetricCryptoProvider.authenticateHashes(userInputPassword,
                    decryptedWithAESuserHashedPassword);
        } catch (Exception e)
        {
            LOGGER.debug("Threw an Unchecked(Technical) Exception, Handled from " + className +" , full stack trace follows: ", e);

            StringBuilder sb = new StringBuilder();
            sb.append("Internal Server Error caused by conflicts Hashing the given password.If problem remains contact Administration.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            appException.setAppErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Problem caused using the Hash Generator trying to authenticate hashes from user given password " +
                    "                            \"and the one retrieved from Database upon Authentication");
            throw appException;

        }

        if(areHashesEqual == false)
        {
            LOGGER.debug( "Password is Incorrect! Provided Password is not creating the same hash with one from database");

            StringBuilder sb = new StringBuilder();
            sb.append("Incorect Password!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.FORBIDDEN);
            appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Provided Password is not creating the same hash with one from database");
            throw appException;
        }

        LOGGER.debug("Hashes are equals = " + areHashesEqual);
    }

}


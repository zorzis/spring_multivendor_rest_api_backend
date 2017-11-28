package com.multimerchant_haze.rest.v1.modules.users.userAbstract.helpers;

import com.multimerchant_haze.rest.v1.app.utils.cryptoFactory.SymmetricCryptoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by zorzis on 3/2/2017.
 */

public class RegisterAccountHelper
{
    private static final Logger logger = LoggerFactory.getLogger(LoginAccountHelper.class);
    private String className =  this.getClass().getSimpleName();



    private static SymmetricCryptoProvider mySymmetricCryptoProvider = new SymmetricCryptoProvider();


    // Creates a unique UserID with a prefix
    public final String generateUserUniqueID(String prefix)
    {
        String userUniqueID = null;

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append("-");
        sb.append(uuid);

        userUniqueID = sb.toString();

        return userUniqueID;
    }


    // AES hashing of user clear input text password with Symmetric Key
    // because we don't want in any case to be cabable as Application
    // to retrieve the user Password in ClearText in any way
    public String hashUserInputPassword(String inputCleanPassword)
    {
        // generate hashed user password
        String hashedPassword = null;

        try
        {
            hashedPassword = mySymmetricCryptoProvider.generateHash(inputCleanPassword);
        }catch (Exception e)
        {
            logger.debug("Threw an Unchecked(Technical) Exception, Handled from " + className +" , full stack trace follows: ", e);

        }
        return hashedPassword;
    }

    // We encrypt the hashed clear input text password using SymmetricCryptoProvider Enctypt then MAC standard
    public String encryptHashedPassword(String hashedPassword)
    {

        String myEncryptedHashedUserPassword = null;

        // We encrypt then mac the hashed password of the user
        try
        {
            // for encryption we give the hashed string password and also the Symmetric Key
            myEncryptedHashedUserPassword = mySymmetricCryptoProvider.encrypt(hashedPassword,
                    mySymmetricCryptoProvider.getSecretKeyPasswordForAuthentication());
        } catch (Exception e)
        {
            logger.debug("Threw an Unchecked(Technical) Exception, Handled from " + className +" , full stack trace follows: ", e);


        }

        return myEncryptedHashedUserPassword;
    }
}

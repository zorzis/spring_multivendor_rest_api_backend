package com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.utils;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.jwx.HeaderParameterNames;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class validates a given token by using the secret configured in the application
 *
 * Created by zorzis on 2/20/2017.
 */
@Component
public class JwtTokenUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private String className =  this.getClass().getSimpleName();

    private static final float TOKEN_DURATION_MINUTES = 30;

    // The shared secret or shared symmetric key represented as a octet sequence JSON Web Key (JWK)
    private JsonWebKey jwKey = null;

    public JwtTokenUtil()
    {
        this.jwKey = this.getJwKey();
    }


    /**
     **********************************************************************************************************
     ****************************************** PARSING TOKEN METHODS *****************************************
     **********************************************************************************************************
     */

    public String getUserEmailFromToken(String token)
    {
        String userEmail = null;
        JwtClaims jwtClaims = null;
        try
        {
            jwtClaims = this.getClaimsFromTokenContext(token);
            userEmail = jwtClaims.getSubject();
        } catch (MalformedClaimException e)
        {
            LOGGER.debug("Threw a MalformedClaimException in " + className + "::getUserEmailFromToken, full stack trace follows: ", e);
            userEmail = null;
        } catch (InvalidJwtException e)
        {
            LOGGER.debug("Threw an InvalidJwtException in " + className + "::getUserEmailFromToken, full stack trace follows: ", e);
            userEmail = null;
        }
        LOGGER.debug("Log produced by: " + className + "::getUserEmailFromToken" + " --> " + "User Email Extracted from Token: " + userEmail);
        return userEmail;
    }


    public String getAudienceFromToken(String token)
    {
        String audience = null;
        JwtClaims jwtClaims = null;

        try
        {
            jwtClaims = getClaimsFromTokenContext(token);

            // assume only one Audience present
            if (jwtClaims.getAudience().isEmpty())
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Authentication Error!");
                sb.append(" ");
                sb.append("No token Audience found!");
                sb.append(" ");
                sb.append("Provided data not sufficient for authentication");
                String errorMessage = sb.toString();

                AppException appException = new AppException(errorMessage);
                appException.setHttpStatus(HttpStatus.FORBIDDEN);
                appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
                appException.setDevelopersMessageExtraInfoAsSingleReason("Please contact developers");
                throw appException;

            }
            else
            {
                audience = jwtClaims.getAudience().get(0);
            }
        } catch (MalformedClaimException e)
        {
            LOGGER.debug("Threw a MalformedClaimException in " + className + "::getAudienceFromToken, full stack trace follows: ", e);
            audience = null;
        } catch (InvalidJwtException e)
        {
            LOGGER.debug("Threw an InvalidJwtException in " + className + "::getAudienceFromToken, full stack trace follows: ", e);
            audience = null;
        }
        LOGGER.debug("Log produced by: " + className + "::getAudienceFromToken" + " --> " + "Audience Extracted from Token: " + audience);
        return audience;
    }

    public String getUserIDFromToken(String token)
    {
        String userID = null;
        JwtClaims jwtClaims = null;
        try
        {
            jwtClaims = getClaimsFromTokenContext(token);
            userID = (String)jwtClaims.getClaimValue("userID");
        } catch (InvalidJwtException e)
        {
            LOGGER.debug("Threw an InvalidJwtException in " + className + "::getUserIDFromToken, full stack trace follows: ", e);
            userID = null;
        }
        LOGGER.debug("Log produced by: " + className + "::getUserIDFromToken" + " --> " + "Producer Unique ID Extracted from Token: " + userID);
        return userID;
    }

    public List<String> getUserRolesFromToken(String token)
    {
        List<String> allUserRoles = null;
        JwtClaims jwtClaims = null;
        try
        {
            jwtClaims = getClaimsFromTokenContext(token);
            allUserRoles = jwtClaims.getStringListClaimValue("roles");
        } catch (MalformedClaimException e)
        {
            LOGGER.debug("Threw a MalformedClaimException in " + className + "::getUserRolesFromToken, full stack trace follows: ", e);
            allUserRoles = null;
        } catch (InvalidJwtException e)
        {
            LOGGER.debug("Threw an InvalidJwtException in " + className + "::getUserRolesFromToken, full stack trace follows: ", e);
            allUserRoles = null;
        }
        return allUserRoles;
    }

    public boolean isTokenExpired(String token)
    {
        JwtClaims jwtClaims = null;
        try
        {
            jwtClaims = this.getClaimsFromTokenContext(token);
            int secondsOfAllowedClockSkew = 30;
            if ((NumericDate.now().getValue() - secondsOfAllowedClockSkew) >= jwtClaims.getExpirationTime().getValue())
            {
                // send a clear error message to the API consumer that token is expired and renew/refresh is needed
                LOGGER.debug("JWT is Expired!!!!!");
                LOGGER.debug("Token expiration date is: [" + jwtClaims.getExpirationTime().getValue() + "] Now date is: [" + (NumericDate.now().getValue()) + "]");
                return true;
            }
        }catch (MalformedClaimException e)
        {
            LOGGER.debug("JWT is Malformed!!!!!");
            LOGGER.debug("Threw a MalformedClaimException in " + className + "::isTokenExpired, full stack trace follows: ", e);

            StringBuilder sb = new StringBuilder();
            sb.append("Authentication Error!");
            sb.append(" ");
            sb.append("Token seems to be Malformed!");
            sb.append(" ");
            sb.append("Provided data not sufficient for authentication");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.FORBIDDEN);
            appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Please contact developer!");
            throw appException;
        } catch (InvalidJwtException e)
        {
            LOGGER.debug("JWT is Invalid!");
            LOGGER.debug("Threw an InvalidJwtException in " + className + "::isTokenExpired, full stack trace follows: ", e);

            StringBuilder sb = new StringBuilder();
            sb.append("Authentication Error!");
            sb.append(" ");
            sb.append("Token seems to be Invalid!");
            sb.append(" ");
            sb.append("Provided data not sufficient for authentication");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.FORBIDDEN);
            appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Please contact developer!");
            throw appException;
        }
        return false;
    }


    /**
     **********************************************************************************************************
     *************************************** VALIDATING TOKEN METHODS *****************************************
     **********************************************************************************************************
     */
    public boolean validateTokenForProducer(String token)
    {
        LOGGER.debug("Starting JWT Validation for PRODUCER...");
        JwtConsumer consumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setRequireIssuedAt() // ensure that issued at is set
                .setAllowedClockSkewInSeconds(30) // allow for a 30 second difference to account for clock skew
                .setRequireSubject() 	// the JWT must have a subject claim
                .setExpectedIssuer("haze.gr") // whom the JWT needs to have been issued by
                .setExpectedAudience("PRODUCER")  // to whom the JWT is intended for
                .setRequireJwtId() // we require the jti to validation so Token with No JWT ID to not be accepted
                .setVerificationKey(jwKey.getKey()) // the symmetric key to verify signature
                .setJwsAlgorithmConstraints(AlgorithmConstraints.DISALLOW_NONE) // we set that 'none' algorithm is not accepted
                .build(); // create the JwtConsumer instance

        return this.finalValidationStep(token, consumer);
    }

    public boolean validateTokenForClient(String token)
    {
        LOGGER.debug("Starting JWT Validation for CLIENT...");
        JwtConsumer consumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setRequireIssuedAt() // ensure that issued at is set
                .setAllowedClockSkewInSeconds(30) // allow for a 30 second difference to account for clock skew
                .setRequireSubject() 	// the JWT must have a subject claim
                .setExpectedIssuer("haze.gr") // whom the JWT needs to have been issued by
                .setExpectedAudience("CLIENT")  // to whom the JWT is intended for
                .setRequireJwtId() // we require the jti to validation so Token with No JWT ID to not be accepted
                .setVerificationKey(jwKey.getKey()) // the symmetric key to verify signature
                .setJwsAlgorithmConstraints(AlgorithmConstraints.DISALLOW_NONE) // we set that 'none' algorithm is not accepted
                .build(); // create the JwtConsumer instance

        return this.finalValidationStep(token, consumer);
    }

    private boolean finalValidationStep(String token, JwtConsumer consumer)
    {
        JwtContext jwtContext = null;
        try
        {
            jwtContext = getJwtContextFromToken(token);
            //  Validate the JWT and process it to the Claims
            consumer.processContext(jwtContext);
        } catch (InvalidJwtException e)
        {
            LOGGER.debug("JWT Validation failed!");
            LOGGER.debug("Threw an InvalidJwtException in " + className + "::finalValidationStep, full stack trace follows: ", e);

            StringBuilder sb = new StringBuilder();
            sb.append("Authentication Error!");
            sb.append(" ");
            sb.append("Invalid Token!");
            sb.append(" ");
            sb.append("Provided data not sufficient for authentication");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.FORBIDDEN);
            appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Please contact developers providing the following error message: " + e.getMessage());
            throw appException;
        }
        LOGGER.debug("JWT Validation succeeded!");
        LOGGER.debug("Log produced by: " + className + "::finalValidationStep" + " --> " + "JWT Validated Claims of Token are: " + jwtContext.getJwtClaims());
        return true;
    }



    /*public JwtUser getJwtUserFromToken(String token)
    {
        JwtUser jwtUser = null;
        String email = this.getUserEmailFromToken(token);
        String userID = this.getUserIDFromToken(token);
        jwtUser = new JwtUser(null,email,userID);
        LOGGER.debug("Log produced by: " + className + "::getJwtUserFromToken" + " --> " + "Jwt Producer Details: " + jwtUser.toString());
        return jwtUser;
    }*/

    private JwtContext getJwtContextFromToken(String token) throws InvalidJwtException
    {
        // We parse the Token once withut validating signature and also All other validators
        // just to check the expiration time
        JwtConsumer consumer = new JwtConsumerBuilder()
                .setSkipAllValidators()
                .setDisableRequireSignature()
                .setSkipSignatureVerification()
                .build();
        JwtContext jwtContext;
        jwtContext = consumer.process(token);
        return jwtContext;
    }

    private JwtClaims getClaimsFromTokenContext(String token) throws InvalidJwtException
    {
        JwtClaims jwtClaims;
        JwtContext jwtContext = getJwtContextFromToken(token);
        jwtClaims = jwtContext.getJwtClaims();
        return jwtClaims;
    }



    /**
     **********************************************************************************************************
     *************************************** GENERATING TOKEN METHODS *****************************************
     **********************************************************************************************************
     */

    public String generateTokenForProducer(ProducerDTO producerDTO)
    {
        LOGGER.debug("Inside " + className + "::generateTokenForProducer()");
        LOGGER.debug("--Start Creating Token--");
        String token = null;
        String jwtClaimsToJson = this.createJsonWebTokenClaimsForProducer(producerDTO);
        String signedJWT = this.signJWT(jwtClaimsToJson);
        //String encryptedJWT = this.encryptJWT(signedJWT);
        //logger.debug("Encrypted JWT from " + className + " is: " + encryptedJWT);
        token = signedJWT;
        LOGGER.debug("Created Token for producer: " + producerDTO.getEmail() + " with producerID: " + producerDTO.getProducerID() + " is: " + token);
        LOGGER.debug("--End Creating Token--");
        return token;
    }


    public String generateTokenForClient(ClientDTO clientDTO)
    {
        LOGGER.debug("Inside " + className + "::generateTokenForClient()");
        LOGGER.debug("--Start Creating Token--");
        String token = null;
        String jwtClaimsToJson = this.createJsonWebTokenClaimsForClient(clientDTO);
        String signedJWT = this.signJWT(jwtClaimsToJson);
        //String encryptedJWT = this.encryptJWT(signedJWT);
        //logger.debug("Encrypted JWT from " + className + " is: " + encryptedJWT);
        token = signedJWT;
        LOGGER.debug("Created Token for client: " + clientDTO.getEmail() + " with clientID: " + clientDTO.getClientID() + " is: " + token);
        LOGGER.debug("--End Creating Token--");
        return token;
    }


    private String createJsonWebTokenClaimsForProducer(ProducerDTO producerDTO)
    {
        LOGGER.debug("Generating JWT Claims...");
        JwtClaims claims = null;
        // Create the Claims, which will be the content of the JWT
        claims = new JwtClaims();
        claims.setIssuer("haze.gr"); // who creates the token and signs it
        //claims.setAudience(mUser.getEmail()); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(TOKEN_DURATION_MINUTES); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(producerDTO.getEmail()); // the subject/principal is whom the token is about
        claims.setAudience("PRODUCER");
        claims.setClaim("producerID",producerDTO.getProducerID()); // additional claims/attributes about the subject can be added
        claims.setClaim("is_account_enabled", producerDTO.getEnabled());
        claims.setClaim("first_name", producerDTO.getFirstName());
        claims.setClaim("last_name", producerDTO.getLastName());
        //List<String> groups = Arrays.asList("group-1", "other-group", "group-3");
        //claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array
        LOGGER.debug("JWT claims: " + claims.toJson());
        return claims.toJson();
    }

    private String createJsonWebTokenClaimsForClient(ClientDTO clientDTO)
    {
        LOGGER.debug("Generating JWT Claims...");
        JwtClaims claims = null;
        // Create the Claims, which will be the content of the JWT
        claims = new JwtClaims();
        claims.setIssuer("haze.gr"); // who creates the token and signs it
        //claims.setAudience(mUser.getEmail()); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(TOKEN_DURATION_MINUTES); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(clientDTO.getEmail()); // the subject/principal is whom the token is about
        claims.setAudience("CLIENT");
        claims.setClaim("clientID",clientDTO.getClientID()); // additional claims/attributes about the subject can be added
        claims.setClaim("is_account_enabled", clientDTO.getEnabled());
        claims.setClaim("first_name", clientDTO.getFirstName());
        claims.setClaim("last_name", clientDTO.getLastName());

        //List<String> groups = Arrays.asList("group-1", "other-group", "group-3");
        //claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array
        LOGGER.debug("JWT claims: " + claims.toJson());
        return claims.toJson();
    }

    //SIGNING
    private String signJWT(String claims)
    {
        LOGGER.debug("Signing JWT...");
        String jwt = null;
        try
        {
            JsonWebSignature jws = new JsonWebSignature();
            // The payload of the JWS is JSON content of the JWT Claims
            jws.setKey(jwKey.getKey()); // set the key to sign the JWT s
            jws.setAlgorithmConstraints(AlgorithmConstraints.DISALLOW_NONE); // set that 'none' algorithm is not accepted
            // The header part
            jws.setKeyIdHeaderValue(jwKey.getKeyId());
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
            jws.setHeader(HeaderParameterNames.TYPE, "JWT");
            jws.setPayload(claims);
            jwt = jws.getCompactSerialization();
            LOGGER.debug("Signed JWT: " + jwt);
        }catch (JoseException e) // We catch the Jose Exception, print it at logs, and produce an AppException
        {
            LOGGER.debug("Threw an Unchecked(Technical) JoseException, Handled from " + className +" , full stack trace follows: ", e);
        }
        return jwt;
    }

    //ENCRYPTING
    private String encryptJWT(String jwt)
    {
        LOGGER.debug("Encrypting JWS...");
        String encryptedJWT = null;
        try
        {
            JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.DIRECT);
            jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
            jwe.setKey(jwKey.getKey());
            jwe.setKeyIdHeaderValue(jwKey.getKeyId());
            jwe.setContentTypeHeaderValue("JWT");
            jwe.setPayload(jwt);
            encryptedJWT = jwe.getCompactSerialization();
            LOGGER.debug("Encrypted JWT inside JwtSymmetricKeyFactory::encryptJWT, is: " + encryptedJWT);
        }catch (JoseException e) // We catch the Jose Exception, print it at logs, and produce an AppException
        {
            LOGGER.debug("Threw an Unchecked(Technical) JoseException, Handled from " + className +" , full stack trace follows: ", e);
        }
        return encryptedJWT;
    }









    /**
     **********************************************************************************************************
     *************************************** PRIVATE Json Web Key METHODS *************************************
     **********************************************************************************************************
     */
    private JsonWebKey getJwKey()
    {
        // we produced that OctetSequenceJsonWebKey in json forat using the
        // private static OctetSequenceJsonWebKey generateJwk(int keyLengthInBits)
        // where keyLengthBits are 256

        //final String jwkJson = "{\"kty\":\"oct\",\"kid\":\"haze.gr_JWT_secretKeyID_v1\",\"k\":\"G8ltDEFsLabcjRQeht_Ir8aZ9xI0U7zl5X06ICqBzyk\"}";
        final String kty = "oct";
        final String kid = "haze.gr_JWT_secretKeyID_v1";
        final String k = "G8ltDEFsLabcjRQeht_Ir8aZ9xI0U7zl5X06ICqBzyk";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(JsonWebKey.KEY_TYPE_PARAMETER, kty);
        params.put(JsonWebKey.KEY_ID_PARAMETER, kid);
        params.put("k", k);

        try
        {
            jwKey = JsonWebKey.Factory.newJwk(params);
        } catch (JoseException e)
        {
            LOGGER.debug("Threw a JoseException in " + className + "::SymmetricKey Initializor, full stack trace follows: ", e);
        }
        return jwKey;
    }

    // Method to generate new AES key(256 bits)
    private OctetSequenceJsonWebKey generateJwk(int keyLengthInBits)
    {
        LOGGER.debug("----------------------- START : JWT - NEW AES SECRET KEY GENERATOR ----------------------");
        LOGGER.debug("----Json Web Token AES key generator for base64 encoded key----");
        byte[] bytes = ByteUtil.randomBytes(ByteUtil.byteLength(keyLengthInBits));
        OctetSequenceJsonWebKey octetSequenceJsonWebKey = new OctetSequenceJsonWebKey(new AesKey(bytes));
        octetSequenceJsonWebKey.setKeyId("haze.gr_JWT_secretKeyID_v1");
        LOGGER.debug("NEW AES KEY in Json Format is: " + octetSequenceJsonWebKey.toJson());
        LOGGER.debug("----------------------- END : JWT - NEW AES SECRET KEY GENERATOR ----------------------");
        return octetSequenceJsonWebKey;
    }


}

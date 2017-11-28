package com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.config;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zorzis on 5/24/2017.
 */
@Component
public class JwtAuthenticationFilterHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilterHelper.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_HEADER_BEARER = "Bearer ";

    public boolean isAuthorizationHeaderNull(HttpServletRequest request)
    {
        String authorizationHeader = request.getHeader(TOKEN_HEADER);

        if(authorizationHeader == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String parseTokenFromAuthorizationHeader(HttpServletRequest request)
    {
        String authorizationHeader = request.getHeader(TOKEN_HEADER);

        if(!authorizationHeader.startsWith(TOKEN_HEADER_BEARER))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Authentication Error!");
            sb.append(" ");
            sb.append("Authorization Header does not start with [Bearer ]!");
            sb.append(" ");
            sb.append("Provided data not sufficient for authentication");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.FORBIDDEN);
            appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Please contact developers!");
            throw appException;
        }

        String authToken = authorizationHeader.substring(7);

        LOGGER.debug("Token sent with request is: " + authToken);

        return authToken;
    }

    public boolean isTokenExpired(String token)
    {
        // checking expiration date of token just to not proceed to any further validations if token is already expired
        if(jwtTokenUtil.isTokenExpired(token))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Authentication Error!");
            sb.append(" ");
            sb.append("Token seems to be expired!");
            sb.append(" ");
            sb.append("Provided data not sufficient for authentication");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.FORBIDDEN);
            appException.setAppErrorCode(HttpStatus.FORBIDDEN.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Please claim new token!");
            throw appException;
        }
        return false;
    }

}

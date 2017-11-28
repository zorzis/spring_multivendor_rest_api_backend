package com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multimerchant_haze.rest.v1.app.constants.errorLinks;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntityError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * /**
 * Since our API only 'speaks' REST we give a HTTP 401 if user cannot be authenticated. There is no
 * login page top redirect to.
 *
 * Help found here for exception Custom Handling: https://www.captechconsulting.com/blogs/versioned-validated-and-secured-rest-services-with-spring-40-part-6
 *
 * Created by zorzis on 2/19/2017.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    private String className =  this.getClass().getSimpleName();

    /** SENDING ERROR RESPONSE IN JSON FORMAT IF UNAUTHORIZED REQUEST IS MADE**/
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException ex)
            throws IOException, ServletException
    {
        //LOGGER.debug("Threw a Spring(Business) Exception, Handled from " + className +" , full stack trace follows: ", ex);

        // This is invoked when user tries to access a secured REST controller without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        //httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");

        httpServletResponse.setStatus(SC_UNAUTHORIZED);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String errorDevMessage = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();

        // construct the ResponseEntityError
        ResponseEntityError errorResponseEntity = new ResponseEntityError();
        errorResponseEntity.setMessage("Unauthorized access is forbidden");
        errorResponseEntity.setStatus(SC_UNAUTHORIZED);
        errorResponseEntity.setCode(SC_UNAUTHORIZED);
        errorResponseEntity.setLink(errorLinks.API_ERRORS_EXPLANATION_URL);
        errorResponseEntity.setDeveloperInformationSignle("No Token Provided or Token is not valid!");

        // This mapper (or, data binder, or codec) provides functionality for converting
        // between Java objects (instances of JDK provided core classes, beans), and matching JSON constructs.
        // It will use instances of JsonParser and JsonGenerator for implementing actual reading/writing of JSON.
        byte[] responseAsJSON = new ObjectMapper()
                .writeValueAsBytes(errorResponseEntity);

        httpServletResponse.getOutputStream().write(responseAsJSON);

    }
}
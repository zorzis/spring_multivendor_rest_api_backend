package com.multimerchant_haze.rest.v1.app.errorHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multimerchant_haze.rest.v1.app.constants.errorLinks;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntityError;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

/**
 * Created by zorzis on 4/9/2017.
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{


    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException ex) throws IOException, ServletException
    {


        String errorDevMessage = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();

        // construct the ResponseEntityError
        ResponseEntityError errorResponseEntity = new ResponseEntityError();
        errorResponseEntity.setMessage("You are not privileged to request this resource.");
        errorResponseEntity.setStatus(SC_FORBIDDEN);
        errorResponseEntity.setCode(SC_FORBIDDEN);
        errorResponseEntity.setLink(errorLinks.API_ERRORS_EXPLANATION_URL);
        errorResponseEntity.setDeveloperInformationSignle("Authenticated user, trying to access the Resource has not sufficient privileges!");

        // This mapper (or, data binder, or codec) provides functionality for converting
        // between Java objects (instances of JDK provided core classes, beans), and matching JSON constructs.
        // It will use instances of JsonParser and JsonGenerator for implementing actual reading/writing of JSON.
        byte[] responseAsJSON = new ObjectMapper()
                .writeValueAsBytes(errorResponseEntity);

        httpServletResponse.setStatus(SC_FORBIDDEN);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.getOutputStream().write(responseAsJSON);
    }
}

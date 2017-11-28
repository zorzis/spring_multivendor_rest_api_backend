package com.multimerchant_haze.rest.v1.app.errorHandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntityError;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.multimerchant_haze.rest.v1.app.constants.errorLinks.API_ERRORS_EXPLANATION_URL;

/**
 * Created by zorzis on 5/12/2017.
 */
public class FiltersExceptionHandler extends OncePerRequestFilter
{

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException
    {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {

            // custom error response class used across my project
            ResponseEntityError errorResponse = new ResponseEntityError((AppException)ex);
            errorResponse.setLink(API_ERRORS_EXPLANATION_URL);

            response.setStatus(((AppException)ex).getHttpStatus().value());
            response.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException
    {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}

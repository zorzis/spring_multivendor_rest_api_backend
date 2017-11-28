package com.multimerchant_haze.rest.v1.app.errorHandling;

import com.multimerchant_haze.rest.v1.app.constants.errorLinks;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntityError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by zorzis on 4/6/2017.
 */
@ControllerAdvice
public class AppExceptionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AppExceptionHandler.class);
    private String className =  this.getClass().getSimpleName();


    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseEntityError> handleAppException(AppException ex)
    {
        LOGGER.debug("Threw a Spring(Business) Exception, Handled from " + className +" , full stack trace follows: ", ex);

        ResponseEntityError errorResponseEntity = new ResponseEntityError(ex);
        errorResponseEntity.setLink(errorLinks.API_ERRORS_EXPLANATION_URL);
        return new ResponseEntity<ResponseEntityError>(errorResponseEntity, ex.getHttpStatus());
    }

}

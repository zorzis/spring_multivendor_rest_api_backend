package com.multimerchant_haze.rest.v1.app.errorHandling;

import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntityError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.multimerchant_haze.rest.v1.app.constants.errorLinks.API_ERRORS_EXPLANATION_URL;

/**
 *  HELP FOUND HERE: http://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 *
 * Created by zorzis on 4/3/2017.
 */
@ControllerAdvice
public class CustomSpringDefaultExceptionsHandler extends ResponseEntityExceptionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSpringDefaultExceptionsHandler.class);
    private String className =  this.getClass().getName();

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request)
    {
        LOGGER.debug("Threw a Spring(Business) Exception, Handled from " + className +" , full stack trace follows: ", ex);

        String unsupported = "Unsupported method type: " + ex.getMethod();

        StringBuilder sb = new StringBuilder();
        sb.append("Supported method types: ");
        for(HttpMethod method:ex.getSupportedHttpMethods())
        {
            sb.append(" ");
            sb.append(method.name());
        }
        String supported = sb.toString();

        ResponseEntityError errorResponseEntity = new ResponseEntityError();
        errorResponseEntity.setMessage(unsupported);
        errorResponseEntity.setStatus(status.value());
        errorResponseEntity.setCode(status.value());
        errorResponseEntity.setLink(API_ERRORS_EXPLANATION_URL);
        errorResponseEntity.setDeveloperInformationSignle(supported);
        return new ResponseEntity<Object>(errorResponseEntity, new HttpHeaders(), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                                   HttpHeaders headers,
                                                                                   HttpStatus status,
                                                                                   WebRequest request)
    {
        LOGGER.debug("Threw a Spring(Business) Exception, Handled from " + className +" , full stack trace follows: ", ex);

        // set the error message
        StringBuilder sbMessage = new StringBuilder();
        sbMessage.append("Required");
        sbMessage.append(" ");
        sbMessage.append("parameter");
        sbMessage.append(" ");
        sbMessage.append("[");
        sbMessage.append(ex.getParameterName());
        sbMessage.append("]");
        sbMessage.append(" ");
        sbMessage.append("is missing");
        String errorMessage = sbMessage.toString();

        // set the developer message
        StringBuilder sbDeveloper = new StringBuilder();
        sbDeveloper.append(errorMessage);
        sbDeveloper.append(".");
        sbDeveloper.append("Requested type of missing parameter is");
        sbDeveloper.append(" ");
        sbDeveloper.append("[");
        sbDeveloper.append(ex.getParameterType());
        sbDeveloper.append("]");
        String developerMessage = sbDeveloper.toString();

        // construct the ResponseEntityError
        ResponseEntityError errorResponseEntity = new ResponseEntityError();
        errorResponseEntity.setMessage(errorMessage);
        errorResponseEntity.setStatus(status.value());
        errorResponseEntity.setCode(status.value());
        errorResponseEntity.setLink(API_ERRORS_EXPLANATION_URL);
        errorResponseEntity.setDeveloperInformationSignle(developerMessage);

        return new ResponseEntity<Object>(errorResponseEntity, new HttpHeaders(), status);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                   final HttpHeaders headers,
                                                                   final HttpStatus status,
                                                                   final WebRequest request)
    {
        LOGGER.debug("Threw a Spring(Business) Exception, Handled from " + className +" , full stack trace follows: ", ex);

        final StringBuilder sb = new StringBuilder();
        sb.append("EndPoint Not Found for");
        sb.append(" ");
        sb.append(ex.getHttpMethod());
        sb.append(" ");
        sb.append(ex.getRequestURL());

        final String errorMessage = sb.toString();

        // construct the ResponseEntityError
        ResponseEntityError errorResponseEntity = new ResponseEntityError();
        errorResponseEntity.setMessage(errorMessage);
        errorResponseEntity.setStatus(status.value());
        errorResponseEntity.setCode(status.value());
        errorResponseEntity.setLink(API_ERRORS_EXPLANATION_URL);
        errorResponseEntity.setDeveloperInformationSignle(errorMessage);

        return new ResponseEntity<Object>(errorResponseEntity, new HttpHeaders(), status);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex,
                                                                            final WebRequest request)
    {
        LOGGER.debug("Threw a Spring(Business) Exception, Handled from " + className + " , full stack trace follows: ", ex);
        StringBuilder sb = new StringBuilder();
        sb.append("Access is Denied!");
        sb.append(" ");
        sb.append("It seems you are not Privileged to access the resource!");
        final String errorMessage = sb.toString();

        // construct the ResponseEntityError
        ResponseEntityError errorResponseEntity = new ResponseEntityError();
        errorResponseEntity.setMessage(errorMessage);
        errorResponseEntity.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponseEntity.setCode(HttpStatus.FORBIDDEN.value());
        errorResponseEntity.setLink(API_ERRORS_EXPLANATION_URL);
        errorResponseEntity.setDeveloperInformationSignle(errorMessage);

        return new ResponseEntity<Object>(errorResponseEntity, new HttpHeaders(), HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex,
                                                                               final WebRequest request)
    {
        LOGGER.debug("Threw a Spring(Business) Exception, Handled from " + className +" , full stack trace follows: ", ex);

        final StringBuilder sb = new StringBuilder();
        sb.append("Failed to proceed.Parameter: ");
        sb.append("[");
        sb.append(ex.getName());
        sb.append("]");
        sb.append(" ");
        sb.append("should be of type");
        sb.append(" ");
        sb.append(ex.getRequiredType().getName());

        final String errorMessage = sb.toString();

        // construct the ResponseEntityError
        ResponseEntityError errorResponseEntity = new ResponseEntityError();
        errorResponseEntity.setMessage(errorMessage);
        errorResponseEntity.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseEntity.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponseEntity.setLink(API_ERRORS_EXPLANATION_URL);
        errorResponseEntity.setDeveloperInformationSignle(errorMessage);

        return new ResponseEntity<Object>(errorResponseEntity, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request)
    {
        LOGGER.debug("Threw an Unchecked Exception, Handled from " + className +" , full stack trace follows: ", ex);

        // construct the ResponseEntityError
        ResponseEntityError errorResponseEntity = new ResponseEntityError();
        errorResponseEntity.setMessage("Internal Server Error: " + ex.getLocalizedMessage());
        errorResponseEntity.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponseEntity.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponseEntity.setLink(API_ERRORS_EXPLANATION_URL);
        errorResponseEntity.setDeveloperInformationSignle("If error continues to occur, Contact Developer");

        return new ResponseEntity<Object>(errorResponseEntity, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
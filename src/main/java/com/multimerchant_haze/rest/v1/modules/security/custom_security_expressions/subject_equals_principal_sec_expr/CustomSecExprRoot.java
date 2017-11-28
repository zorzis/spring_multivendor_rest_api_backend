package com.multimerchant_haze.rest.v1.modules.security.custom_security_expressions.subject_equals_principal_sec_expr;

import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.model.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * Created by zorzis on 5/22/2017.
 */
public class CustomSecExprRoot
        extends SecurityExpressionRoot implements MethodSecurityExpressionOperations
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSecExprRoot.class);
    private String className =  this.getClass().getSimpleName();


    public CustomSecExprRoot(Authentication authentication)
    {
        super(authentication);
    }

    public boolean isOwner(String subjectRequestedTheResourceEmail) {
        String principalEmail = ((JwtUser) this.getPrincipal()).getEmail();

        LOGGER.debug("Checking equality for Owner of Resource and Authorized Principal");
        LOGGER.debug("Owner is: " + subjectRequestedTheResourceEmail);
        LOGGER.debug("Principal is: " +  principalEmail);
        return principalEmail.equals(subjectRequestedTheResourceEmail);
    }


    @Override
    public void setFilterObject(Object o)
    {

    }

    @Override
    public Object getFilterObject()
    {
        return null;
    }

    @Override
    public void setReturnObject(Object o)
    {

    }

    @Override
    public Object getReturnObject()
    {
        return null;
    }

    @Override
    public Object getThis()
    {
        return null;
    }
}
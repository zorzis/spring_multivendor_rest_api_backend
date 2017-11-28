package com.multimerchant_haze.rest.v1.modules.security.custom_security_expressions.subject_equals_principal_sec_expr;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * Created by zorzis on 5/22/2017.
 */
public class CustomPermissionEvaluator implements PermissionEvaluator
{
    @Override
    public boolean hasPermission(Authentication auth,
                                 Object targetDomainObject,
                                 Object permission)
    {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String))
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasPermission(Authentication auth,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission)
    {
        if ((auth == null) || (targetType == null) || !(permission instanceof String))
        {
            return false;
        }
        return true;
    }
}

package com.multimerchant_haze.rest.v1.modules.security.custom_security_expressions.get_authentication_principal_interface;

import org.springframework.security.core.Authentication;


/**
 * Interface for getting the Authentication and the authenticated Principal
 *
 * Created by zorzis on 5/22/2017.
 */
public interface AuthenticationFacadeInterface
{
    Authentication getAuthentication();
}



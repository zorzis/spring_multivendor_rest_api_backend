package com.multimerchant_haze.rest.v1.modules.security.custom_security_expressions.get_authentication_principal_interface;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Implementing our custom Interface we manage to take the authentication and the principal
 * In our code the principal is a JwtUser object that implements the Spring Security UserDetails
 *
 * Created by zorzis on 5/22/2017.
 */
@Component
public class AuthenticationFacade implements AuthenticationFacadeInterface
{

    @Override
    public Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

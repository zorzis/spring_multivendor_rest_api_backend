package com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Created by zorzis on 3/5/2017.
 */
public class JwtUser implements UserDetails
{

    private final Long id;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
    private final String userID;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final Date lastPasswordResetDate;

    public JwtUser(Long id,
                   String firstname,
                   String lastname,
                   String email,
                   String userID,
                   String password,
                   Collection<? extends GrantedAuthority> authorities,
                   boolean enabled,
                   Date lastPasswordResetDate)
    {

        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userID = userID;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }



    public Long getId() {
        return id;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getEmail()
    {
        return email;
    }

    @Override
    public String getUsername()
    {
        return null;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "userID: " + userID + " | " + "email: " + email;
    }
}
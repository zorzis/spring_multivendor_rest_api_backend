package com.multimerchant_haze.rest.v1.modules.users.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAuthority;

import java.io.Serializable;

/**
 * Created by zorzis on 5/8/2017.
 */
public class ClientAuthorityDTO implements Serializable
{
    @JsonIgnore
    private Long authorityID;

    private String role;


    public ClientAuthorityDTO()
    {

    }

    public ClientAuthorityDTO(Long authorityID)
    {
        this.authorityID = authorityID;
    }

    public ClientAuthorityDTO(String role)
    {
        this.role = role;
    }

    public ClientAuthorityDTO(ClientAuthority clientAuthority)
    {
        this.authorityID = clientAuthority.getAuthorityID();
        this.role = clientAuthority.getRole();
    }

    public Long getAuthorityID()
    {
        return authorityID;
    }

    public void setAuthorityID(Long authorityID)
    {
        this.authorityID = authorityID;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ClientAuthority DTO");
        sb.append("[");
        sb.append("AutoCreated MySQL id: ");
        sb.append(this.authorityID);
        sb.append(" | ");
        sb.append("Authority Role Name: ");
        sb.append(this.role);
        sb.append("]");
        return sb.toString();
    }
}


package com.multimerchant_haze.rest.v1.modules.users.producer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAuthority;

import java.io.Serializable;

/**
 * Created by zorzis on 3/11/2017.
 */
public class ProducerAuthorityDTO implements Serializable
{
    @JsonIgnore
    private Long authorityID;

    private String role;


    public ProducerAuthorityDTO()
    {

    }

    public ProducerAuthorityDTO(Long authorityID)
    {
        this.authorityID = authorityID;
    }

    public ProducerAuthorityDTO(String role)
    {
        this.role = role;
    }

    public ProducerAuthorityDTO(ProducerAuthority producerAuthority)
    {
        this.authorityID = producerAuthority.getAuthorityID();
        this.role = producerAuthority.getRole();
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
        sb.append("ProducerAuthority DTO");
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

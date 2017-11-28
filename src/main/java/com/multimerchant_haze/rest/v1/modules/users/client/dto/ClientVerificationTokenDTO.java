package com.multimerchant_haze.rest.v1.modules.users.client.dto;

import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientVerificationToken;

import java.util.Date;

/**
 * Created by zorzis on 11/19/2017.
 */
public class ClientVerificationTokenDTO
{
    private Long id;

    private String token;

    private ClientDTO client;

    private Date expiryDate;


    public ClientVerificationTokenDTO()
    {

    }

    public ClientVerificationTokenDTO(ClientVerificationToken clientVerificationToken)
    {
        this.id = clientVerificationToken.getId();
        this.token = clientVerificationToken.getToken();
        this.client = this.mapClientDTOFromClientEntity(clientVerificationToken.getClient());
        this.expiryDate = clientVerificationToken.getExpiryDate();
    }





    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public ClientDTO getClient()
    {
        return client;
    }

    public void setClient(ClientDTO client)
    {
        this.client = client;
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public ClientDTO mapClientDTOFromClientEntity(Client client)
    {
        if(client != null)
        {
            return new ClientDTO(client);
        }else {
            return new ClientDTO();
        }
    }
}

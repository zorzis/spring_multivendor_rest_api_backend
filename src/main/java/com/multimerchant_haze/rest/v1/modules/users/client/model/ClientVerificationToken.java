package com.multimerchant_haze.rest.v1.modules.users.client.model;

import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientVerificationTokenDTO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zorzis on 11/13/2017.
 */
@Entity
@Table(name = "clients_verification_tokens")
public class ClientVerificationToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "client_account_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @Column(name = "expiration_date")
    private Date expiryDate;


    public  ClientVerificationToken()
    {

    }

    public ClientVerificationToken(ClientVerificationTokenDTO clientVerificationTokenDTO)
    {
        this.id = clientVerificationTokenDTO.getId();
        this.token = clientVerificationTokenDTO.getToken();
        this.expiryDate = clientVerificationTokenDTO.getExpiryDate();
        this.client = this.mapClientEntityFromClientDTO(clientVerificationTokenDTO.getClient());
    }

    public Client mapClientEntityFromClientDTO(ClientDTO clientDTO)
    {
        if(clientDTO != null)
        {
            return new Client(clientDTO);
        }else {
            return new Client();
        }
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

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
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

}

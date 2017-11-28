package com.multimerchant_haze.rest.v1.modules.users.client.model;

import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAuthorityDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 5/8/2017.
 */
@Entity
@Table(name = "users_authorities")
public class ClientAuthority implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long authorityID;

    @Column(name = "authority_name", nullable = false)
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientAuthority")
    private Set<ClientHasAuthority> clientHasAuthorities = new HashSet<>(0);



    public ClientAuthority()
    {

    }

    public ClientAuthority(String role)
    {
        this.role = role;
    }

    public ClientAuthority(ClientAuthorityDTO clientAuthorityDTO)
    {
        this.authorityID = clientAuthorityDTO.getAuthorityID();
        this.role = clientAuthorityDTO.getRole();
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

    public Set<ClientHasAuthority> getClientHasAuthorities()
    {
        return this.clientHasAuthorities;
    }

    public void setClientHasAuthorities(Set<ClientHasAuthority> clientHasAuthorities)
    {
        this.clientHasAuthorities = clientHasAuthorities;
    }

    public void addClientHasAuthority(ClientHasAuthority clientHasAuthority)
    {
        this.clientHasAuthorities.add(clientHasAuthority);
    }

    public void removeClientHasAuthority(ClientHasAuthority clientHasAuthority)
    {
        this.clientHasAuthorities.remove(clientHasAuthority);
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ClientAuthority Entity");
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
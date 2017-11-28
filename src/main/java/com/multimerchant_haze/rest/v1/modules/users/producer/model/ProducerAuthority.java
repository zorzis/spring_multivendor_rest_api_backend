package com.multimerchant_haze.rest.v1.modules.users.producer.model;

import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAuthorityDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 3/9/2017.
 */
@Entity
@Table(name = "users_authorities")
public class ProducerAuthority implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long authorityID;

    @Column(name = "authority_name", nullable = false)
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producerAuthority")
    private Set<ProducerHasAuthority> producerHasAuthorities = new HashSet<>(0);



    public ProducerAuthority()
    {

    }

    public ProducerAuthority(String role)
    {
        this.role = role;
    }

    public ProducerAuthority(ProducerAuthorityDTO producerAuthorityDTO)
    {
        this.authorityID = producerAuthorityDTO.getAuthorityID();
        this.role = producerAuthorityDTO.getRole();
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

    public Set<ProducerHasAuthority> getProducerHasAuthorities()
    {
        return this.producerHasAuthorities;
    }

    public void setProducerHasAuthorities(Set<ProducerHasAuthority> producerHasAuthorities)
    {
        this.producerHasAuthorities = producerHasAuthorities;
    }

    public void addProducerHasAuthority(ProducerHasAuthority producerHasAuthority)
    {
        this.producerHasAuthorities.add(producerHasAuthority);
    }

    public void removeProducerHasAuthority(ProducerHasAuthority producerHasAuthority)
    {
        this.producerHasAuthorities.remove(producerHasAuthority);
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ProducerAuthority Entity");
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

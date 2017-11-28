package com.multimerchant_haze.rest.v1.modules.users.client.model;

import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderHasClientHasProducer;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 5/8/2017.
 */

@Entity
@Table(name = "clients_accounts")
public class Client implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientID", referencedColumnName = "clientID")
    private ClientProfile clientProfile = new ClientProfile();

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "isEnabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<ClientHasAuthority> clientHasAuthorities = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<ClientHasAddress> clientHasAddresses = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<OrderHasClientHasProducer> orderHasClientHasProducerSet = new HashSet<>(0);

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "client")
    private ClientVerificationToken clientVerificationToken;



    // Default Public Constructor
    public Client()
    {
        this.isEnabled = false;
    }


    public Client(ClientDTO clientDTO)
    {
        this.id = clientDTO.getId();
        this.email = clientDTO.getEmail();
        this.clientProfile.setClientID(clientDTO.getClientID());
        this.password = clientDTO.getPassword();
        this.createdAt = clientDTO.getCreatedAt();
        this.updatedAt = clientDTO.getUpdatedAt();
        this.isEnabled = clientDTO.getEnabled();
        this.lastPasswordResetDate = clientDTO.getLastPasswordResetDate();

        //set client profile information data
        this.clientProfile.setFirstName(clientDTO.getFirstName());
        this.clientProfile.setLastName(clientDTO.getLastName());
        this.clientProfile.setBirthDate(clientDTO.getBirthDate());
        this.clientProfile.setGender(clientDTO.getGender());
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsEnabled()
    {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled)
    {
        isEnabled = enabled;
    }

    public Date getLastPasswordResetDate()
    {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date last_password_reset_date)
    {
        this.lastPasswordResetDate = last_password_reset_date;
    }

    public ClientProfile getClientProfile()
    {
        return this.clientProfile;
    }

    public void setClientProfile(ClientProfile clientProfile)
    {
        this.clientProfile = clientProfile;
    }

    public Set<ClientHasAuthority> getClientHasAuthorities()
    {
        return clientHasAuthorities;
    }

    public void setClientHasAuthorities(Set<ClientHasAuthority> clientHasAuthorities)
    {
        this.clientHasAuthorities = clientHasAuthorities;
    }

    public Set<ClientHasAddress> getClientHasAddresses()
    {
        return clientHasAddresses;
    }

    public void setClientHasAddresses(Set<ClientHasAddress> ClientHasAddresses)
    {
        this.clientHasAddresses = clientHasAddresses;
    }

    public Set<OrderHasClientHasProducer> getOrderHasClientHasProducerSet()
    {
        return orderHasClientHasProducerSet;
    }

    public void setOrderHasClientHasProducerSet(Set<OrderHasClientHasProducer> orderHasClientHasProducerSet)
    {
        this.orderHasClientHasProducerSet = orderHasClientHasProducerSet;
    }





    /************************************************************************************************************
     ***************************************** Orders Related Methods ***************************************
     ***********************************************************************************************************/
    public Set<Order> getClientOrdersEntities()
    {
        Set<Order> clientOrders = new HashSet<>(0);
        for (OrderHasClientHasProducer orderHasClientHasProducerEntity : orderHasClientHasProducerSet)
        {
            Order newOrder = orderHasClientHasProducerEntity.getOrder();
            clientOrders.add(newOrder);
        }
        return clientOrders;
    }


    // using this way we ensure that authorities gonna be saved to database among with the Client Entity
    public void addSingleClientOrder(Order clientOrder)
    {
        OrderHasClientHasProducer orderHasClientHasProducer = new OrderHasClientHasProducer();
        orderHasClientHasProducer.setOrder(clientOrder);
        orderHasClientHasProducer.setClient(this);
        this.orderHasClientHasProducerSet.add(orderHasClientHasProducer);
    }



    /************************************************************************************************************
     ***************************************** Athorities Related Methods ***************************************
     ***********************************************************************************************************/
    public void addClientHasAuthority(ClientHasAuthority clientHasAuthority)
    {
        this.clientHasAuthorities.add(clientHasAuthority);
    }

    public void removeClientHasAuthority(ClientHasAuthority clientHasAuthority)
    {
        this.clientHasAuthorities.remove(clientHasAuthority);
    }


    public Set<ClientAuthority> getClientAuthoritiesEntities()
    {
        Set<ClientAuthority> clientAuthorities = new HashSet<>(0);
        for (ClientHasAuthority clientHasAuthority : clientHasAuthorities)
        {
            clientAuthorities.add(clientHasAuthority.getClientAuthority());
        }
        return clientAuthorities;
    }


    // using this way we ensure that authorities gonna be saved to database among with the Client Entity
    public void addSingleClientAuthority(ClientAuthority clientAuthority)
    {
        ClientHasAuthority clientHasAuthority = new ClientHasAuthority();
        clientHasAuthority.setClientAuthority(clientAuthority);
        clientHasAuthority.setClient(this);
        this.clientHasAuthorities.add(clientHasAuthority);
    }

    // just passing a Set of Authorities
    public void addMultipleClientAuthorities(Set<ClientAuthority> clientAuthorities)
    {
        for (ClientAuthority clientAuthorityEntity : clientAuthorities)
        {
            this.addSingleClientAuthority(clientAuthorityEntity);
        }
    }

    // removing a Client Authority from ClientHasAuthority HashMap<>
    public void removeSingleClientAuthority(ClientAuthority clientAuthority)
    {
        ClientHasAuthority clientHasAuthority = new ClientHasAuthority();
        clientHasAuthority.setClientAuthority(clientAuthority);
        clientHasAuthority.setClient(this);
        this.clientHasAuthorities.remove(clientHasAuthority);
    }

    // just removing a Set of Authorities
    public void removeMultipleClientAuthorities(Set<ClientAuthority> clientAuthorities)
    {
        for (ClientAuthority clientAuthorityEntity : clientAuthorities)
        {
            this.removeSingleClientAuthority(clientAuthorityEntity);
        }
    }


    /************************************************************************************************************
     ***************************************** Addresses Related Methods ****************************************
     ***********************************************************************************************************/
    public void addClientHasAddress(ClientHasAddress clientHasAddress)
    {
        this.clientHasAddresses.add(clientHasAddress);
    }

    public void removeClientHasAddress(ClientHasAddress clientHasAddress)
    {
        this.clientHasAddresses.remove(clientHasAddress);
    }

    public Set<ClientAddress> getClientAddressesEntities()
    {
        Set<ClientAddress> clientAddresses = new HashSet<>(0);
        for (ClientHasAddress clientHasAddress : clientHasAddresses)
        {
            clientAddresses.add(clientHasAddress.getClientAddress());
        }
        return clientAddresses;
    }


    // using this way we ensure that authorities gonna be saved to database among with the Client Entity
    public void addSingleClientAddress(ClientAddress clientAddress)
    {
        ClientHasAddress clientHasAddress = new ClientHasAddress();
        clientHasAddress.setClientAddress(clientAddress);
        clientHasAddress.setClient(this);
        this.clientHasAddresses.add(clientHasAddress);
    }

    // just adding a Set of Addresses
    public void addMultipleClientAddresses(Set<ClientAddress> clientAddresses)
    {
        for (ClientAddress clientAddressEntity : clientAddresses)
        {
            this.addSingleClientAddress(clientAddressEntity);
        }
    }

    // removing a user Has Address from a user.UserHasAddresses HashMap
    public void removeSingleClientAddress(ClientAddress clientAddress)
    {
        ClientHasAddress clientHasAddress = new ClientHasAddress();
        clientHasAddress.setClientAddress(clientAddress);
        clientHasAddress.setClient(this);
        this.clientHasAddresses.remove(clientHasAddress);
    }

    // just removing a Set of Addresses
    public void removeMultipleUserAddresses(Set<ClientAddress> clientAddresses)
    {
        for (ClientAddress clientAddressEntity : clientAddresses)
        {
            this.removeSingleClientAddress(clientAddressEntity);
        }
    }


    public ClientVerificationToken getClientVerificationToken()
    {
        return clientVerificationToken;
    }

    public void setClientVerificationToken(ClientVerificationToken clientVerificationToken)
    {
        this.clientVerificationToken = clientVerificationToken;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Client Entity");
        sb.append("[");
        sb.append("AutoCreated MySQL id: ");
        sb.append(this.id);
        sb.append(" | ");
        sb.append("Email: ");
        sb.append(this.email);
        sb.append(" | ");
        sb.append("Unique ClientID: ");
        sb.append(this.clientProfile.getClientID());
        sb.append(" | ");
        sb.append("First Name: ");
        sb.append(this.clientProfile.getFirstName());
        sb.append(" | ");
        sb.append("Last Name: ");
        sb.append(this.clientProfile.getLastName());
        sb.append(" | ");
        sb.append("Birth Date: ");
        sb.append(this.clientProfile.getBirthDate());
        sb.append(" | ");
        sb.append("Gender: ");
        sb.append(this.clientProfile.getGender());
        sb.append(" | ");
        sb.append("Is Enabled Client Account: ");
        sb.append(this.isEnabled);
        sb.append(" | ");
        sb.append("Created at: ");
        sb.append(this.createdAt);
        sb.append(" | ");
        sb.append("Updated at: ");
        sb.append(this.updatedAt);
        sb.append(" | ");
        sb.append("Password(Encrypted then MACed): ");
        sb.append(this.password);
        sb.append("]");

        return sb.toString();
    }

    public String printClientAuthorities()
    {
        StringBuilder sb = new StringBuilder();
        if (clientHasAuthorities != null && !clientHasAuthorities.isEmpty())
        {
            sb.append("Authorities{");
            for (ClientHasAuthority clientHasAuthority : clientHasAuthorities)
            {
                String authority_name = clientHasAuthority.getClientAuthority().getRole();
                sb.append(authority_name);
                if (clientHasAuthorities.iterator().hasNext())
                {
                    sb.append(" , ");
                }
            }
            sb.append("}");
        }
        return sb.toString();
    }

    public String printClientAddresses()
    {
        StringBuilder sb = new StringBuilder();
        if (clientHasAddresses != null && !clientHasAddresses.isEmpty())
        {
            sb.append("Registered Addresses{");
            for (ClientHasAddress clientHasAddress : clientHasAddresses)
            {
                String addressDetails = clientHasAddress.getClientAddress().toString();
                sb.append(addressDetails);
                if (clientHasAddresses.iterator().hasNext())
                {
                    sb.append(" , ");
                }
            }
            sb.append("}");
        }
        return sb.toString();
    }
}
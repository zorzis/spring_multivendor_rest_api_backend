package com.multimerchant_haze.rest.v1.modules.users.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateDeserializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderHasClientHasProducer;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAddress;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAuthority;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 5/8/2017.
 */
public class ClientDTO implements Serializable
{
    @JsonIgnore
    private Long id;

    @JsonView(JsonACL.ClientsView.class)
    private String clientID;

    @JsonIgnore
    private String password;

    @JsonView(JsonACL.ClientsView.class)
    private String email;

    @JsonView(JsonACL.ClientsView.class)
    private String firstName;

    @JsonView(JsonACL.ClientsView.class)
    private String lastName;

    @JsonSerialize(using = CustomBirthDateSerializer.class)
    @JsonDeserialize(using = CustomBirthDateDeserializer.class)
    @JsonView(JsonACL.ClientsView.class)
    private Date birthDate;

    @JsonView(JsonACL.ClientsView.class)
    private Gender gender;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date updatedAt;

    @JsonView(JsonACL.AdminsView.class)
    private Boolean isEnabled;

    @JsonView(JsonACL.AdminsView.class)
    private Date lastPasswordResetDate;

    @JsonView(JsonACL.AdminsView.class)
    private Set<ClientAuthorityDTO> authorities = new HashSet<>(0);

    @JsonView(JsonACL.ClientsView.class)
    private Set<ClientAddressDTO> addresses = new HashSet<>(0);

    @JsonView(JsonACL.OrderListBelongingToClient.class)
    private Set<OrderDTO> orders = new HashSet<>(0);




    /************************************************************************************************************
     *********************************************** Constructors ***********************************************
     ***********************************************************************************************************/
    // Default Public Constructor
    public ClientDTO()
    {

    }

    public ClientDTO(String email)
    {
        this.email = email;
    }


    public ClientDTO(String email, String password)
    {
        this.email = email;
        this.password = password;
    }


    public ClientDTO(Client client)
    {
        this.id = client.getId();
        this.clientID = client.getClientProfile().getClientID();
        this.email = client.getEmail();
        this.isEnabled = client.getIsEnabled();
        this.lastPasswordResetDate = client.getLastPasswordResetDate();
        this.createdAt = client.getCreatedAt();
        this.updatedAt = client.getUpdatedAt();
        this.password = client.getPassword();
        this.firstName = client.getClientProfile().getFirstName();
        this.lastName = client.getClientProfile().getLastName();
        this.birthDate = client.getClientProfile().getBirthDate();
        this.gender = client.getClientProfile().getGender();
    }




    /************************************************************************************************************
     ******************************************** Getters/Setters ***********************************************
     ***********************************************************************************************************/
    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getClientID()
    {
        return clientID;
    }

    public void setClientID(String clientID)
    {
        this.clientID = clientID;
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

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public void setBirthDateFromString(String birthDate) throws AppException
    {
        final Logger LOGGER = LoggerFactory.getLogger(ClientDTO.class);
        final String className =  this.getClass().getName();

        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            date = formatter.parse(birthDate);
        } catch (ParseException ex)
        {
            LOGGER.debug("Threw an Unchecked Exception, Handled from " + className +" , full stack trace follows: ", ex);
            AppException appException = new AppException("Failed to proceed.BirthDate must be in [yyyy-MM-dd] format");
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            throw appException;
        }
        this.birthDate = date;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
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

    public Boolean getEnabled()
    {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled)
    {
        isEnabled = enabled;
    }

    public Date getLastPasswordResetDate()
    {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate)
    {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Set<ClientAuthorityDTO> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(Set<ClientAuthorityDTO> authorities)
    {
        this.authorities = authorities;
    }

    public Set<ClientAddressDTO> getAddresses()
    {
        return addresses;
    }

    public void setAddresses(Set<ClientAddressDTO> addresses)
    {
        this.addresses = addresses;
    }


    public Set<OrderDTO> getOrders()
    {
        return orders;
    }

    public void setOrders(Set<OrderDTO> orders)
    {
        this.orders = orders;
    }





    /************************************************************************************************************
     ***************************************** Athorities Related Methods ***************************************
     ***********************************************************************************************************/
    public Set<ClientAuthorityDTO> mapAuthoritiesDTOsFromAuthoritiesEntities(Set<ClientAuthority> clientAuthorities)
    {
        for(ClientAuthority clientAuthority :clientAuthorities)
        {
            authorities.add(new ClientAuthorityDTO(clientAuthority));
        }
        return authorities;
    }

    public void addAuthority(ClientAuthorityDTO clientAuthorityDTO)
    {
        this.authorities.add(clientAuthorityDTO);
    }

    public void addAuthorityByName(String authorityName)
    {
        this.authorities.add(new ClientAuthorityDTO(authorityName));
    }

    public void removeAuthority(ClientAuthorityDTO clientAuthorityDTO)
    {
        this.authorities.remove(clientAuthorityDTO);
    }









    /************************************************************************************************************
     ***************************************** Addresses Related Methods ****************************************
     ***********************************************************************************************************/
    public Set<ClientAddressDTO> mapAddressesDTOsFromAddressesEntities(Set<ClientAddress> clientAddresses)
    {
        for(ClientAddress clientAddress :clientAddresses)
        {
            addresses.add(new ClientAddressDTO(clientAddress));
        }
        return addresses;
    }

    public void addAddress(ClientAddressDTO clientAddressDTO)
    {
        this.addresses.add(clientAddressDTO);
    }

    public void removeAddress(ClientAddressDTO clientAddressDTO)
    {
        this.addresses.remove(clientAddressDTO);
    }



    /************************************************************************************************************
     ***************************************** Orders Related Methods ****************************************
     ***********************************************************************************************************/
    public Set<OrderDTO> mapOrdersDTOsFromOrdersEntities(Set<OrderHasClientHasProducer> orderHasClientHasProducerSet)
    {
        for(OrderHasClientHasProducer orderHasClientHasProducer : orderHasClientHasProducerSet)
        {
            OrderDTO orderDTO = new OrderDTO(orderHasClientHasProducer.getOrder());

            // set the OrderDTO ProducerDTO entity
            ProducerDTO producerDTO = orderDTO.mapProducerEntityToProducerDTO(orderHasClientHasProducer.getProducer());
            orderDTO.setProducer(producerDTO);

            // set the OrderDTO ClientDTO entity
            ClientDTO clientDTO = orderDTO.mapClientEntityToClientDTO(orderHasClientHasProducer.getClient());
            orderDTO.setClient(clientDTO);

            this.orders.add(orderDTO);
        }
        return this.orders;
    }




    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Client DTO");
        sb.append("[");
        sb.append("Email: ");
        sb.append(this.email);
        sb.append(" | ");
        sb.append("Unique clientID: ");
        sb.append(this.clientID);
        sb.append(" | ");
        sb.append("First Name: ");
        sb.append(this.firstName);
        sb.append(" | ");
        sb.append("Last Name: ");
        sb.append(this.lastName);
        sb.append(" | ");
        sb.append("Birth Date: ");
        sb.append(this.birthDate);
        sb.append(" | ");
        sb.append("Created at: ");
        sb.append(this.createdAt);
        sb.append(" | ");
        sb.append("Updated at: ");
        sb.append(this.updatedAt);
        sb.append(" | ");
        sb.append("Is Enabled Client Account: ");
        sb.append(this.isEnabled);
        sb.append(" | ");
        sb.append("Last Password Reset Date: ");
        sb.append(this.lastPasswordResetDate);
        sb.append(" | ");
        sb.append("Password(Encrypted then MACed): ");
        sb.append(this.password);
        sb.append(" | ");
        sb.append(this.printUserAuthorities());
        sb.append(" | ");
        sb.append(this.printClientAddresses());
        return sb.toString();
    }

    public String printUserAuthorities()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Authorities{");
        for(ClientAuthorityDTO clientAuthorityDTO :authorities)
        {
            String authority_name = clientAuthorityDTO.getRole();
            sb.append(authority_name);
            if(authorities.iterator().hasNext())
            {
                sb.append(" , ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public String printClientAddresses()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Registered Addresses{");
        for(ClientAddressDTO clientAddressDTOAddress : addresses)
        {
            String addressDetails = clientAddressDTOAddress.toString();
            sb.append(addressDetails);
            if(addresses.iterator().hasNext())
            {
                sb.append(" , ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}

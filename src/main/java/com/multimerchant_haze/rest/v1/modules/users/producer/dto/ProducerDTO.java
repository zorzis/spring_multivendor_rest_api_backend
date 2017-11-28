package com.multimerchant_haze.rest.v1.modules.users.producer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateDeserializer;
import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerHasPaymentMethod;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomBirthDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderHasClientHasProducer;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;
import com.multimerchant_haze.rest.v1.modules.products.model.Product;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAddress;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAuthority;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.model.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * JSON-serializable DTO containing user data
 *
 * Created by zorzis on 3/2/2017.
 */
@JsonRootName(value = "producer")
@JsonTypeName(value = "producer")
public class ProducerDTO implements Serializable
{
    private Long id;

    @JsonView(JsonACL.PublicView.class)
    private String producerID;

    @JsonIgnore
    private String password;

    @JsonView(JsonACL.ClientsView.class)
    private String email;

    @JsonView(JsonACL.PublicView.class)
    private String firstName;

    @JsonView(JsonACL.PublicView.class)
    private String lastName;

    @JsonSerialize(using = CustomBirthDateSerializer.class)
    @JsonDeserialize(using = CustomBirthDateDeserializer.class)
    @JsonView(JsonACL.PublicView.class)
    private Date birthDate;

    @JsonView(JsonACL.PublicView.class)
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
    private Set<ProducerAuthorityDTO> authorities = new HashSet<>(0);

    @JsonView(JsonACL.PublicView.class)
    private Set<ProducerHasPaymentMethodDTO> producerPaymentMethods = new HashSet<>(0);

    @JsonView(JsonACL.PublicView.class)
    private ProducerAddressDTO producerAddress = new ProducerAddressDTO();

    @JsonView(JsonACL.SearchByProducersPublicView.class)
    private Set<ProductDTO> products = new HashSet<>(0);

    @JsonView(JsonACL.OrderListBelongingToProducer.class)
    private Set<OrderDTO> orders = new HashSet<>(0);


    /************************************************************************************************************
     *********************************************** Constructors ***********************************************
     ***********************************************************************************************************/
    // Default Public Constructor
    public ProducerDTO()
    {

    }

    public ProducerDTO(String email)
    {
        this.email = email;
    }


    public ProducerDTO(String email, String password)
    {
        this.email = email;
        this.password = password;
    }


    public ProducerDTO(Producer producer)
    {
        this.id = producer.getId();
        this.producerID = producer.getProducerProfile().getProducerID();
        this.email = producer.getEmail();
        this.isEnabled = producer.getIsEnabled();
        this.lastPasswordResetDate = producer.getLastPasswordResetDate();
        this.createdAt = producer.getCreatedAt();
        this.updatedAt = producer.getUpdatedAt();
        this.password = producer.getPassword();
        this.firstName = producer.getProducerProfile().getFirstName();
        this.lastName = producer.getProducerProfile().getLastName();
        this.birthDate = producer.getProducerProfile().getBirthDate();
        this.gender = producer.getProducerProfile().getGender();

        this.producerAddress = this.mapAddressEntityToProducerAddressDTO(producer.getProducerProfile().getProducerAddress());
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

    public String getProducerID()
    {
        return producerID;
    }

    public void setProducerID(String producerID)
    {
        this.producerID = producerID;
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
        final Logger LOGGER = LoggerFactory.getLogger(ProducerDTO.class);
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

    public Set<ProducerAuthorityDTO> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(Set<ProducerAuthorityDTO> authorities)
    {
        this.authorities = authorities;
    }

    public ProducerAddressDTO getProducerAddress()
    {
        return producerAddress;
    }

    public void setProducerAddress(ProducerAddressDTO producerAddress)
    {
        this.producerAddress = producerAddress;
    }

    public Set<ProducerHasPaymentMethodDTO> getProducerPaymentMethods()
    {
        return producerPaymentMethods;
    }

    public void setProducerPaymentMethods(Set<ProducerHasPaymentMethodDTO> paymentMethods)
    {
        this.producerPaymentMethods = paymentMethods;
    }


    public Set<ProductDTO> getProducts()
    {
        return products;
    }

    public void setProducts(Set<ProductDTO> products)
    {
        this.products = products;
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
    public Set<ProducerAuthorityDTO> mapAuthoritiesDTOsFromAuthoritiesEntities(Set<ProducerAuthority> producerAuthoritiesEntities)
    {
        for(ProducerAuthority producerAuthorityEntity :producerAuthoritiesEntities)
        {
            authorities.add(new ProducerAuthorityDTO(producerAuthorityEntity));
        }
        return authorities;
    }

    public void addAuthority(ProducerAuthorityDTO producerAuthorityDTO)
    {
        this.authorities.add(producerAuthorityDTO);
    }

    public void addAuthorityByName(String authorityName)
    {
        this.authorities.add(new ProducerAuthorityDTO(authorityName));
    }

    public void removeAuthority(ProducerAuthorityDTO producerAuthorityDTO)
    {
        this.authorities.remove(producerAuthorityDTO);
    }



    // check if there is an address else create an null object to avoid the NullPointerException
    private ProducerAddressDTO mapAddressEntityToProducerAddressDTO(ProducerAddress producerAddress)
    {
        if (producerAddress == null)
        {
            return new ProducerAddressDTO();
        }
        else
        {
            return new ProducerAddressDTO(producerAddress);
        }
    }


    public Set<OrderDTO> mapOrdersDTOsFromOrderEntities(Set<Order> producerOrdersEntities)
    {
        for(Order orderEntity : producerOrdersEntities)
        {
            this.orders.add(new OrderDTO(orderEntity));
        }
        return this.orders;
    }

    public Set<ProductDTO> mapProductsDTOsFromProductsEntities(Set<Product> producerProductsEntities)
    {
        for(Product productEntity :producerProductsEntities)
        {
            this.products.add(new ProductDTO(productEntity));
        }
        return this.products;
    }


    public Set<ProducerHasPaymentMethodDTO> mapProducerPaymentMethodsDTOsFromProducerPaymentMethodsEntities(Set<ProducerHasPaymentMethod> paymentMethodsEntitiesSet)
    {
        if(paymentMethodsEntitiesSet != null)
        {
            for(ProducerHasPaymentMethod producerPaymentMethodEntity : paymentMethodsEntitiesSet)
            {
                this.producerPaymentMethods.add(new ProducerHasPaymentMethodDTO(producerPaymentMethodEntity));
            }
        }
        return this.producerPaymentMethods;
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
        sb.append("Producer DTO");
        sb.append("[");
        sb.append("Email: ");
        sb.append(this.email);
        sb.append(" | ");
        sb.append("Unique producerID: ");
        sb.append(this.producerID);
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
        sb.append("Is Enabled Producer Account: ");
        sb.append(this.isEnabled);
        sb.append(" | ");
        sb.append("Last Password Reset Date: ");
        sb.append(this.lastPasswordResetDate);
        sb.append(" | ");
        sb.append("Password(Encrypted then MACed): ");
        sb.append(this.password);
        sb.append(" | ");
        sb.append(this.printProducerAuthorities());
        sb.append(" | ");
        sb.append(this.producerAddress.toString());
        return sb.toString();
    }

    public String printProducerAuthorities()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Authorities{");
        for(ProducerAuthorityDTO producerAuthorityDTO :authorities)
        {
            String authority_name = producerAuthorityDTO.getRole();
            sb.append(authority_name);
            if(authorities.iterator().hasNext())
            {
                sb.append(" , ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

}
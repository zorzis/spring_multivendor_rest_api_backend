package com.multimerchant_haze.rest.v1.modules.users.producer.model;

import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderHasClientHasProducer;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.products.model.Product;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductStock;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 *
 * The Producer JPA entity.
 *
 * Created by zorzis on 3/2/2017.
 */
@Entity
@Table(name = "producers_accounts")
public class Producer implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producerID")
    private ProducerProfile producerProfile = new ProducerProfile();

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producer")
    private Set<ProducerHasAuthority> producerHasAuthorities = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producer")
    private Set<ProductStock> producerHasProducts = new HashSet<>(0);


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producer")
    private Set<ProducerHasPaymentMethod> producerHasPaymentMethodSet = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producer")
    private Set<OrderHasClientHasProducer> orderHasClientHasProducerSet = new HashSet<>(0);

    // Default Public Constructor
    public Producer()
    {

    }


    public Producer(ProducerDTO producerDTO)
    {
        this.id = producerDTO.getId();
        this.email = producerDTO.getEmail();
        this.producerProfile.setProducerID(producerDTO.getProducerID());
        this.password = producerDTO.getPassword();
        this.createdAt = producerDTO.getCreatedAt();
        this.updatedAt = producerDTO.getUpdatedAt();
        this.isEnabled = producerDTO.getEnabled();
        this.lastPasswordResetDate = producerDTO.getLastPasswordResetDate();

        //set producer profile information data
        this.producerProfile.setFirstName(producerDTO.getFirstName());
        this.producerProfile.setLastName(producerDTO.getLastName());
        this.producerProfile.setBirthDate(producerDTO.getBirthDate());
        this.producerProfile.setGender(producerDTO.getGender());
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

    public ProducerProfile getProducerProfile()
    {
        return this.producerProfile;
    }

    public void setProducerProfile(ProducerProfile producerProfile)
    {
        this.producerProfile = producerProfile;
    }

    public Set<ProducerHasAuthority> getProducerHasAuthorities()
    {
        return producerHasAuthorities;
    }

    public void setProducerHasAuthorities(Set<ProducerHasAuthority> producerHasAuthorities)
    {
        this.producerHasAuthorities = producerHasAuthorities;
    }

    public Boolean getEnabled()
    {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled)
    {
        isEnabled = enabled;
    }

    public Set<ProductStock> getProducerHasProducts()
    {
        return producerHasProducts;
    }

    public void setProducerHasProducts(Set<ProductStock> producerHasProducts)
    {
        this.producerHasProducts = producerHasProducts;
    }


    public Set<ProducerHasPaymentMethod> getProducerHasPaymentMethodSet()
    {
        return producerHasPaymentMethodSet;
    }

    public void setProducerHasPaymentMethodSet(Set<ProducerHasPaymentMethod> producerHasPaymentMethodSet)
    {
        this.producerHasPaymentMethodSet = producerHasPaymentMethodSet;
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
    public Set<Order> getProducerOrdersEntities()
    {
        Set<Order> clientOrders = new HashSet<>(0);
        for (OrderHasClientHasProducer orderHasClientHasProducerEntity : orderHasClientHasProducerSet)
        {
            clientOrders.add(orderHasClientHasProducerEntity.getOrder());
        }
        return clientOrders;
    }


    // using this way we ensure that orders gonna be saved to database among with the Client Entity
    public void addSingleProducerOrder(Order producerOrder)
    {
        OrderHasClientHasProducer orderHasClientHasProducer = new OrderHasClientHasProducer();
        orderHasClientHasProducer.setOrder(producerOrder);
        orderHasClientHasProducer.setProducer(this);
        this.orderHasClientHasProducerSet.add(orderHasClientHasProducer);
    }



    /************************************************************************************************************
     ***************************************** PaymentMethods Related Methods ***************************************
     ***********************************************************************************************************/
    public Set<PaymentMethod> getProducerPaymentMethods()
    {
        Set<PaymentMethod> paymentMethods = new HashSet<>(0);

        for(ProducerHasPaymentMethod producerHasPaymentMethod : this.producerHasPaymentMethodSet)
        {

            paymentMethods.add(producerHasPaymentMethod.getPaymentMethod());
        }

        return paymentMethods;
    }


    /************************************************************************************************************
     ***************************************** Authorities Related Methods ***************************************
     ***********************************************************************************************************/
    public void addProducerHasAuthority(ProducerHasAuthority producerHasAuthority)
    {
        this.producerHasAuthorities.add(producerHasAuthority);
    }

    public void removeProducerHasAuthority(ProducerHasAuthority producerHasAuthority)
    {
        this.producerHasAuthorities.remove(producerHasAuthority);
    }


    public Set<ProducerAuthority> getProducerAuthoritiesEntities()
    {
        Set<ProducerAuthority> producerAuthorities = new HashSet<>(0);
        for (ProducerHasAuthority producerHasAuthority : producerHasAuthorities)
        {
            producerAuthorities.add(producerHasAuthority.getProducerAuthority());
        }
        return producerAuthorities;
    }


    public Set<Product> getProducerProductsEntities()
    {
        Set<Product> productEntities = new HashSet<>(0);
        for (ProductStock productStock : this.producerHasProducts)
        {
            productEntities.add(productStock.getProduct());
        }
        return productEntities;
    }


    // using this way we ensure that authorities gonna be saved to database among with the Producer Entity
    public void addSingleProducerAuthority(ProducerAuthority producerAuthority)
    {
        ProducerHasAuthority producerHasAuthority = new ProducerHasAuthority();
        producerHasAuthority.setProducerAuthority(producerAuthority);
        producerHasAuthority.setProducer(this);
        this.producerHasAuthorities.add(producerHasAuthority);
    }

    // just passing a Set of Authorities
    public void addMultipleProducerAuthorities(Set<ProducerAuthority> producerAuthorities)
    {
        for (ProducerAuthority producerAuthorityEntity : producerAuthorities)
        {
            this.addSingleProducerAuthority(producerAuthorityEntity);
        }
    }

    // removing a ProducerAuthority from ProducerHasAuthority HashMap<>
    public void removeSingleProducerAuthority(ProducerAuthority producerAuthority)
    {
        ProducerHasAuthority producerHasAuthority = new ProducerHasAuthority();
        producerHasAuthority.setProducerAuthority(producerAuthority);
        producerHasAuthority.setProducer(this);
        this.producerHasAuthorities.remove(producerHasAuthority);
    }

    // just removing a Set of Authorities
    public void removeMultipleProducerAuthorities(Set<ProducerAuthority> producerAuthorities)
    {
        for (ProducerAuthority producerAuthorityEntity : producerAuthorities)
        {
            this.removeSingleProducerAuthority(producerAuthorityEntity);
        }
    }



    



    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Producer Entity");
        sb.append("[");
        sb.append("AutoCreated MySQL id: ");
        sb.append(this.id);
        sb.append(" | ");
        sb.append("Email: ");
        sb.append(this.email);
        sb.append(" | ");
        sb.append("Unique producerID: ");
        sb.append(this.producerProfile.getProducerID());
        sb.append(" | ");
        sb.append("First Name: ");
        sb.append(this.producerProfile.getFirstName());
        sb.append(" | ");
        sb.append("Last Name: ");
        sb.append(this.producerProfile.getLastName());
        sb.append(" | ");
        sb.append("Birth Date: ");
        sb.append(this.producerProfile.getBirthDate());
        sb.append(" | ");
        sb.append("Gender: ");
        sb.append(this.producerProfile.getGender());
        sb.append(" | ");
        sb.append("Is Enabled Producer Account: ");
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

    public String printProducerAuthorities()
    {
        StringBuilder sb = new StringBuilder();
        if(producerHasAuthorities != null && !producerHasAuthorities.isEmpty())
        {
            sb.append("Authorities{");
            for(ProducerHasAuthority producerHasAuthority : producerHasAuthorities)
            {
                String authority_name = producerHasAuthority.getProducerAuthority().getRole();
                sb.append(authority_name);
                if(producerHasAuthorities.iterator().hasNext())
                {
                    sb.append(" , ");
                }
            }
            sb.append("}");
        }
        return sb.toString();
    }

}
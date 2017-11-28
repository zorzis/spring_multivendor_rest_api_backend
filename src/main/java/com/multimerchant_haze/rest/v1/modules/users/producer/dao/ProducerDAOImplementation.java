package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAddress;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerHasAuthority;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by zorzis on 3/2/2017.
 */
@Repository("producerDAO")
public class ProducerDAOImplementation implements ProducerDAO
{

    @PersistenceContext
    private EntityManager entityManager;


    /********************* READ related USER methods implementation ***********************/
    @Override
    public List<Producer> getAllProducersNoFiltering()
    {
        String sqlString = null;
        sqlString = "SELECT producer FROM Producer producer";
        TypedQuery<Producer> query = entityManager.createQuery(sqlString, Producer.class);
        return query.getResultList();
    }




    @Override
    public Producer getProducerByEmailOnlyFromProducersTable(String producerEmail)
    {
        try
        {
            String qlString = "SELECT producer FROM Producer producer WHERE producer.email = ?1";
            Query query = entityManager.createQuery(qlString, Producer.class);
            query.setParameter(1, producerEmail);
            Producer producer = (Producer)query.getSingleResult();
            return producer;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }


    @Override
    public Producer getProducerByProducerIDOnlyFromProducersTable(String producerID)
    {
        try
        {
            String qlString = "SELECT producer FROM Producer producer WHERE producer.producerID = ?1";
            Query query = entityManager.createQuery(qlString, Producer.class);
            query.setParameter(1, producerID);
            Producer producer = (Producer)query.getSingleResult();
            return producer;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public Producer getProducerByProducerEmailFetchingProfile(String producerEmail)
    {
        try
        {
            String qlString = "SELECT producer FROM Producer producer " +
                    "LEFT JOIN FETCH producer.producerProfile producerProfile " +
                    "WHERE producer.email= ?1";
            Query query = entityManager.createQuery(qlString, Producer.class);
            query.setParameter(1, producerEmail);
            Producer producer = (Producer)query.getSingleResult();
            return producer;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }


    @Override
    public Producer getProducerByEmailFetchingAddressFetchingProfile(String producerEmail)
    {
        try
        {
            String qlString = "SELECT producer FROM Producer producer " +
                    "LEFT JOIN FETCH producer.producerProfile producerProfile " +
                    "LEFT JOIN FETCH producerProfile.producerAddress producerAddress " +
                    "WHERE producer.email= ?1";
            Query query = entityManager.createQuery(qlString, Producer.class);
            query.setParameter(1, producerEmail);
            Producer producer = (Producer)query.getSingleResult();
            return producer;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }


    @Override
    public Producer getProducerByEmailFetchingProfileFetchingAuthoritiesFetchingAddress(String producerEmail)
    {
        try
        {
            String qlString = "SELECT producer FROM Producer producer " +
                    "LEFT JOIN FETCH producer.producerProfile producerProfile " +
                    "LEFT JOIN FETCH producer.producerHasAuthorities producerHasAuthorities " +
                    "LEFT JOIN FETCH producerHasAuthorities.producerAuthority producerAuthority " +
                    "LEFT JOIN FETCH producerProfile.producerAddress producerAddress " +
                    "WHERE producer.email= ?1";
            Query query = entityManager.createQuery(qlString, Producer.class);
            query.setParameter(1, producerEmail);
            Producer producer = (Producer)query.getSingleResult();
            return producer;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }





    @Override
    public Producer getProducerByProducerIDFetchingAddressFetchingProducts(String producerID)
    {
        try
        {
            String qlString = "SELECT producer FROM Producer producer " +
                    "LEFT JOIN FETCH producer.producerProfile producerProfile " +
                    "LEFT JOIN FETCH producerProfile.producerAddress producerAddress " +
                    "LEFT JOIN FETCH producer.producerHasProducts producerHasProducts " +
                    "LEFT JOIN FETCH producerHasProducts.product product " +
                    "WHERE producerProfile.producerID= ?1";
            Query query = entityManager.createQuery(qlString, Producer.class);
            query.setParameter(1, producerID);
            Producer producer = (Producer)query.getSingleResult();
            return producer;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }






    /********************* CREATE related USER methods implementation ***********************/

    @Override
    public String registerNewProducer(Producer producer)
    {
        // using the intermediate parent class ProducerHasAuthority to persist both user and user authorities
        Set<ProducerHasAuthority> producerHasAuthorities = producer.getProducerHasAuthorities();
        for(ProducerHasAuthority producerHasAuthority : producerHasAuthorities)
        {
            entityManager.persist(producerHasAuthority);
        }
        return producer.getEmail();
    }

    // add user authority using the intermediate table based on intermediate many2many ProducerHasAuthority class
    @Override
    public String addProducerHasAuthority(ProducerHasAuthority producerHasAuthority)
    {
        Producer producerToBeMerged = entityManager.find(Producer.class, producerHasAuthority.getProducer().getId());
        producerHasAuthority.setProducer(producerToBeMerged);

        entityManager.persist(producerHasAuthority);
        return producerHasAuthority.getProducerAuthority().getRole();
    }


    @Override
    public String addProducerAddress(ProducerAddress producerAddress)
    {
        entityManager.persist(producerAddress);
        return producerAddress.toString();
    }


    @Override
    public String updateProducerAddress(ProducerAddress producerAddress)
    {
        entityManager.merge(producerAddress);
        return producerAddress.toString();
    }


    /********************* DELETE related USER methods implementation ***********************/
    @Override
    public String deleteProducer(Producer producer)
    {
        Producer producerToBeDeleted = entityManager.find(Producer.class, producer.getId());
        entityManager.remove(producerToBeDeleted);
        return producer.getEmail();
    }

    @Override
    public String deleteProducerHasAuthority(ProducerHasAuthority producerHasAuthority)
    {
        ProducerHasAuthority producerHasAuthorityRowToBeRemoved = entityManager.find(ProducerHasAuthority.class, producerHasAuthority.getId());
        entityManager.remove(producerHasAuthorityRowToBeRemoved);
        return producerHasAuthority.getProducerAuthority().getRole();
    }


    @Override
    public String deleteProducerAddress(ProducerAddress producerAddress)
    {
        return null;
    }





    /********************* UPDATE related USER methods implementation ***********************/
    @Override
    public String updateProducer(Producer producer)
    {
        //TODO think about partial update and full update
        entityManager.merge(producer);

        return producer.getEmail();
    }
}

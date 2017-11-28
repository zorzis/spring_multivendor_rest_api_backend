package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAddress;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by zorzis on 6/12/2017.
 */
@Repository("producerAddressDAO")
public class ProducerAddressDAOImplementation implements ProducerAddressDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProducerAddress getProducerAddressByProducerEmail(String producerEmail)
    {
        try
        {
            String qlString = "SELECT producerAdress FROM ProducerAddress producerAdress " +
                    "WHERE producerAdress.producerProfile.producerID = ?1";
            Query query = entityManager.createQuery(qlString, ProducerAddress.class);
            query.setParameter(1, producerEmail);
            ProducerAddress producerAddress = (ProducerAddress)query.getSingleResult();
            return producerAddress;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}

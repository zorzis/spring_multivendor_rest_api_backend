package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerHasPaymentMethod;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by zorzis on 9/7/2017.
 */
@Repository("producerPaymentMethodDAO")

public class ProducerPaymentMethodDAOImplementation implements ProducerPaymentMethodDAO
{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Producer getProducerByProducerIDFetchingAddressFetchingProductsFetchingPaymentMethods(String producerEmail)
    {
        try
        {
            String qlString = "SELECT producer FROM Producer producer " +
                    "LEFT JOIN FETCH producer.producerProfile producerProfile " +
                    "LEFT JOIN FETCH producerProfile.producerAddress producerAddress " +
                    "LEFT JOIN FETCH producer.producerHasPaymentMethodSet producerHasPaymentMethodSet " +
                    "LEFT JOIN FETCH producerHasPaymentMethodSet.paymentMethod paymentMethod " +
                    "LEFT JOIN FETCH producer.producerHasProducts producerHasProducts " +
                    "LEFT JOIN FETCH producerHasProducts.product product " +
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
    public String addProducerHasPaymentMethod(ProducerHasPaymentMethod producerHasPaymentMethod)
    {
        Producer producerToBeMerged = entityManager.find(Producer.class, producerHasPaymentMethod.getProducer().getId());
        producerHasPaymentMethod.setProducer(producerToBeMerged);

        entityManager.persist(producerHasPaymentMethod);
        return producerHasPaymentMethod.getPaymentMethod().getPaymentMethodName();
    }

    @Override
    public String deleteProducerHasPaymentMethod(ProducerHasPaymentMethod producerHasPaymentMethod)
    {
        Producer producerToBeMerged = entityManager.find(Producer.class, producerHasPaymentMethod.getProducer().getId());
        producerHasPaymentMethod.setProducer(producerToBeMerged);

        entityManager.remove(producerHasPaymentMethod);
        return producerHasPaymentMethod.getPaymentMethod().getPaymentMethodName();
    }

}

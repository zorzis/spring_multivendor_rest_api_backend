package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zorzis on 6/13/2017.
 */
@Repository("clientSearchProducersDAO")
public class ClientSearchProducersDAOImplementation implements ClientSearchProducersDAO
{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Producer> getAllActiveSellersProducers()
    {
        try
        {
            String qlString = "SELECT  DISTINCT(producer) FROM Producer producer " +
                    "LEFT JOIN FETCH producer.producerProfile producerProfile " +
                    "LEFT JOIN FETCH producerProfile.producerAddress producerAddress " +
                    "LEFT JOIN FETCH producer.producerHasPaymentMethodSet producerHasPaymentMethodSet " +
                    "LEFT JOIN FETCH producerHasPaymentMethodSet.paymentMethod paymentMethod " +
                    "LEFT JOIN FETCH producer.producerHasProducts producerHasProducts " +
                    "LEFT JOIN FETCH producerHasProducts.product product " +
                    "WHERE producer.isEnabled= ?1";
            Query query = entityManager.createQuery(qlString, Producer.class);
            query.setParameter(1, true);
            List<Producer> producersList = (List<Producer>)query.getResultList();

            return producersList;

        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public Producer getActiveSellerProducer(String producerID)
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
                    "WHERE producerProfile.producerID= ?1 AND producer.isEnabled= ?2";
            Query query = entityManager.createQuery(qlString, Producer.class);
            query.setParameter(1, producerID);
            query.setParameter(2, true);
            Producer producer = (Producer)query.getSingleResult();

            return producer;

        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}

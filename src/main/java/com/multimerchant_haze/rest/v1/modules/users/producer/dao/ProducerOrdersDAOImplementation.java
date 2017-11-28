package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by zorzis on 7/12/2017.
 */
@Repository("producerOrdersDAO")
public class ProducerOrdersDAOImplementation implements ProducerOrdersDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Producer getProducerFetchingOrders(String producerEmail)
    {
        try
        {
            String qlString = "SELECT producer FROM Producer producer " +
                    "LEFT JOIN FETCH producer.producerProfile producerProfile " +
                    "LEFT JOIN FETCH producer.producerHasAuthorities producerHasAuthorities " +
                    "LEFT JOIN FETCH producerHasAuthorities.producerAuthority producerAuthority " +
                    "LEFT JOIN FETCH producerProfile.producerAddress producerAddress " +
                    "LEFT JOIN FETCH producer.orderHasClientHasProducerSet orderHasClientHasProducerSet " +
                    "LEFT JOIN FETCH orderHasClientHasProducerSet.order producerOrder " +
                    "LEFT JOIN FETCH orderHasClientHasProducerSet.client client " +
                    "LEFT JOIN FETCH producerOrder.orderStatusCode orderStatusCode " +
                    "LEFT JOIN FETCH producerOrder.orderPayment orderPayment " +
                    "LEFT JOIN FETCH orderPayment.paymentMethod orderPaymentMethod " +
                    "LEFT JOIN FETCH producerOrder.orderHasProducts orderHasProducts " +
                    "LEFT JOIN FETCH orderHasProducts.orderProduct orderProduct " +
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
}

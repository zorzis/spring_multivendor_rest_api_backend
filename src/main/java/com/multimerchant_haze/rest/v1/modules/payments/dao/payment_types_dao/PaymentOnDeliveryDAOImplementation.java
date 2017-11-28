package com.multimerchant_haze.rest.v1.modules.payments.dao.payment_types_dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
@Repository("paymentOnDeliveryDAO")
public class PaymentOnDeliveryDAOImplementation implements PaymentOnDeliveryDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentOnDelivery> getPaymentsOnDelivery()
    {
        try
        {
            String qlString = "SELECT paymentOnDelivery FROM PaymentOnDelivery paymentOnDelivery";
            Query query = entityManager.createQuery(qlString, PaymentOnDelivery.class);
            List<PaymentOnDelivery> paymentsOnDeliveryList = ( List<PaymentOnDelivery>) query.getResultList();
            return paymentsOnDeliveryList;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public PaymentOnDelivery getPaymentOnDeliveryByID(String paymentOnDeliveryID)
    {
        try
        {
            String qlString = "SELECT paymentOnDelivery FROM PaymentOnDelivery paymentOnDelivery " +
                    "WHERE paymentOnDelivery.paymentOnDeliveryID = ?1";
            Query query = entityManager.createQuery(qlString, PaymentOnDelivery.class);
            query.setParameter(1, paymentOnDeliveryID);
            PaymentOnDelivery paymentOnDelivery = (PaymentOnDelivery) query.getSingleResult();
            return paymentOnDelivery;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}

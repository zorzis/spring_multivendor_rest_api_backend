package com.multimerchant_haze.rest.v1.modules.payments.dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zorzis on 9/29/2017.
 */
@Repository("paymentDAO")
public class PaymentDAOImplementation implements PaymentDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Payment> getPayments()
    {
        try
        {
            String qlString = "SELECT payment FROM Payment payment";
            Query query = entityManager.createQuery(qlString, Payment.class);
            List<Payment> paymentsList = ( List<Payment>) query.getResultList();
            return paymentsList;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public Payment getPaymentByID(String paymentID)
    {
        try
        {
            String qlString = "SELECT payment FROM Payment payment " +
                    "WHERE payment.paymentID = ?1";
            Query query = entityManager.createQuery(qlString, Payment.class);
            query.setParameter(1, paymentID);
            Payment payment = (Payment) query.getSingleResult();
            return payment;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}

package com.multimerchant_haze.rest.v1.modules.payments.dao.payment_types_dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
@Repository("paymentPaypalDAO")
public class PaymentPaypalDAOImplementation implements PaymentPaypalDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentPaypal> getPaymentsPaypal()
    {
        try
        {
            String qlString = "SELECT paymentPaypal FROM PaymentPaypal paymentPaypal";
            Query query = entityManager.createQuery(qlString, PaymentPaypal.class);
            List<PaymentPaypal> paymentsPaypalList = ( List<PaymentPaypal>) query.getResultList();
            return paymentsPaypalList;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public PaymentPaypal getPaymentPaypalByID(String paymentPaypalID)
    {
        try
        {
            String qlString = "SELECT paymentPaypal FROM PaymentPaypal paymentPaypal " +
                    "WHERE paymentPaypal.paymentPaypalID = ?1";
            Query query = entityManager.createQuery(qlString, PaymentPaypal.class);
            query.setParameter(1, paymentPaypalID);
            PaymentPaypal paymentPaypal = (PaymentPaypal) query.getSingleResult();
            return paymentPaypal;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}

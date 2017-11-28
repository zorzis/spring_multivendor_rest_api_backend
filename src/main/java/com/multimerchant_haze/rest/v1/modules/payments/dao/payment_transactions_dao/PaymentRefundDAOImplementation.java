package com.multimerchant_haze.rest.v1.modules.payments.dao.payment_transactions_dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentRefund;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
@Repository("paymentRefundDAO")
public class PaymentRefundDAOImplementation implements PaymentRefundDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentRefund> getPaymentsRefunds()
    {
        try
        {
            String qlString = "SELECT paymentRefund FROM PaymentRefund paymentRefund";
            Query query = entityManager.createQuery(qlString, PaymentRefund.class);
            List<PaymentRefund> paymentsRefundList = ( List<PaymentRefund>) query.getResultList();
            return paymentsRefundList;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public PaymentRefund getPaymentRefundByID(String paymentRefundID)
    {
        try
        {
            String qlString = "SELECT paymentRefund FROM PaymentRefund paymentRefund " +
                    "WHERE paymentRefund.paymentRefundID = ?1";
            Query query = entityManager.createQuery(qlString, PaymentRefund.class);
            query.setParameter(1, paymentRefundID);
            PaymentRefund paymentRefund = (PaymentRefund) query.getSingleResult();
            return paymentRefund;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}

package com.multimerchant_haze.rest.v1.modules.payments.dao.payment_transactions_dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentDeposit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
@Repository("paymentDepositDAO")
public class PaymentDepositDAOImplementation implements PaymentDepositDAO
{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<PaymentDeposit> getPaymentsDeposits()
    {
        try
        {
            String qlString = "SELECT paymentDeposit FROM PaymentDeposit paymentDeposit";
            Query query = entityManager.createQuery(qlString, PaymentDeposit.class);
            List<PaymentDeposit> paymentsDepositList = ( List<PaymentDeposit>) query.getResultList();
            return paymentsDepositList;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public PaymentDeposit getPaymentDepositByID(String paymentDepositID)
    {
        try
        {
            String qlString = "SELECT paymentDeposit FROM PaymentDeposit paymentDeposit " +
                    "WHERE paymentDeposit.paymentDepositID = ?1";
            Query query = entityManager.createQuery(qlString, PaymentDeposit.class);
            query.setParameter(1, paymentDepositID);
            PaymentDeposit paymentDeposit = (PaymentDeposit) query.getSingleResult();
            return paymentDeposit;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}

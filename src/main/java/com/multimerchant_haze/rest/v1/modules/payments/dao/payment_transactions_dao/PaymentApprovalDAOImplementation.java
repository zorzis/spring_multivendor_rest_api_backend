package com.multimerchant_haze.rest.v1.modules.payments.dao.payment_transactions_dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
@Repository("paymentApprovalDAO")
public class PaymentApprovalDAOImplementation implements PaymentApprovalDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentApproval> getPaymentsApprovals()
    {
        try
        {
            String qlString = "SELECT paymentApproval FROM PaymentApproval paymentApproval";
            Query query = entityManager.createQuery(qlString, PaymentApproval.class);
            List<PaymentApproval> paymentsApprovalList = ( List<PaymentApproval>) query.getResultList();
            return paymentsApprovalList;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public PaymentApproval getPaymentApprovalByID(String paymentApprovalID)
    {
        try
        {
            String qlString = "SELECT paymentApproval FROM PaymentApproval paymentApproval " +
                    "WHERE paymentApproval.paymentApprovalID = ?1";
            Query query = entityManager.createQuery(qlString, PaymentApproval.class);
            query.setParameter(1, paymentApprovalID);
            PaymentApproval paymentApproval = (PaymentApproval) query.getSingleResult();
            return paymentApproval;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}

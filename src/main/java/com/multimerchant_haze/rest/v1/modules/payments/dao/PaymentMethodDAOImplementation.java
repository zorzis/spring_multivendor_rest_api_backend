package com.multimerchant_haze.rest.v1.modules.payments.dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zorzis on 9/7/2017.
 */
@Repository("paymentMethodDAO")
public class PaymentMethodDAOImplementation implements PaymentMethodDAO
{
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public PaymentMethod getPaymentMethodByName(String paymentMethodName)
    {
        try
        {
            String qlString = "SELECT paymentMethod FROM PaymentMethod paymentMethod " +
                    "WHERE paymentMethod.paymentMethodName = ?1";
            Query query = entityManager.createQuery(qlString, PaymentMethod.class);
            query.setParameter(1, paymentMethodName);
            PaymentMethod paymentMethod = (PaymentMethod) query.getSingleResult();
            return paymentMethod;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }


    @Override
    public PaymentMethod getPaymentMethodByID(String paymentMethodID)
    {
        try
        {
            String qlString = "SELECT paymentMethod FROM PaymentMethod paymentMethod " +
                    "WHERE paymentMethod.paymentMethodID = ?1";
            Query query = entityManager.createQuery(qlString, PaymentMethod.class);
            query.setParameter(1, paymentMethodID);
            PaymentMethod paymentMethod = (PaymentMethod) query.getSingleResult();
            return paymentMethod;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }


    @Override
    public List<PaymentMethod> getPaymentMethods()
    {
        try
        {
            String qlString = "SELECT paymentMethod FROM PaymentMethod paymentMethod";
            Query query = entityManager.createQuery(qlString, PaymentMethod.class);
            List<PaymentMethod> paymentMethodList = ( List<PaymentMethod>) query.getResultList();
            return paymentMethodList;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public String addPaymentMethod(PaymentMethod paymentMethod)
    {
        this.entityManager.persist(paymentMethod);
        return paymentMethod.getPaymentMethodName();
    }


    @Override
    public String deletePaymentMethod(PaymentMethod paymentMethod)
    {
        PaymentMethod paymentMethodToBeDeleted = entityManager.find(PaymentMethod.class, paymentMethod.getPaymentMethodID());

        this.entityManager.remove(paymentMethodToBeDeleted);
        return paymentMethod.getPaymentMethodName();
    }
}

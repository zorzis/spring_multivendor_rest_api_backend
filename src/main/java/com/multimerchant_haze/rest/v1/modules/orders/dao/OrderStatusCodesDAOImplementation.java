package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.OrderStatusCodes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by zorzis on 7/18/2017.
 */
@Repository("orderStatusCodesDAO")
public class OrderStatusCodesDAOImplementation implements OrderStatusCodesDAO
{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public OrderStatusCodes getSingleOrderStatusCodeRefByStatusID(String statusID)
    {
        try
        {
            String qlString = "SELECT orderStatusCodeRef " +
                    "FROM OrderStatusCodes orderStatusCodeRef " +
                    "WHERE orderStatusCodeRef.orderStatusCode = ?1";
            Query query = entityManager.createQuery(qlString, OrderStatusCodes.class);
            query.setParameter(1, statusID);
            OrderStatusCodes orderStatusCodeRef = (OrderStatusCodes) query.getSingleResult();
            return orderStatusCodeRef;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}

package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderHasProducts;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

/**
 * Created by zorzis on 10/5/2017.
 */
@Repository("placeOrderDAO")
public class PlaceOrderDAOImplementation implements PlaceOrderDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Deprecated
    @Override
    public String placeNewOrderByOrderEntity(Order order)
    {
        entityManager.persist(order.getOrderPayment());
        entityManager.persist(order.getOrderPayment().getPaymentApproval());
        entityManager.persist(order.getOrderHasClientHasProducer());
        entityManager.persist(order.getOrderClientDetails());
        entityManager.persist(order.getOrderProducerDetails());
        entityManager.persist(order.getOrderClientAddressDetails());
        entityManager.persist(order.getOrderProducerAddressDetails());
        Set<OrderHasProducts> orderHasProductsHashSet = order.getOrderHasProducts();
        for(OrderHasProducts orderHasProducts : orderHasProductsHashSet)
        {
            entityManager.persist(orderHasProducts);
        }
        return order.getOrderID();
    }

}

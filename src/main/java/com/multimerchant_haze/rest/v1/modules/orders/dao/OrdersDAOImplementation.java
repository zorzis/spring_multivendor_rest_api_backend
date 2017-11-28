package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.*;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentDeposit;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentRefund;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientProfile;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerProfile;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;

/**
 * Created by zorzis on 7/10/2017.
 */
@Repository("ordersDAO")
public class OrdersDAOImplementation implements OrdersDAO
{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public com.multimerchant_haze.rest.v1.modules.orders.model.Order getOrderByOrderIDOnlyFromOrdersTable(String orderID)
    {
        try
        {
            String qlString = "SELECT order FROM Order order " +
                    "WHERE order.orderID = ?1";
            Query query = entityManager.createQuery(qlString, com.multimerchant_haze.rest.v1.modules.orders.model.Order.class);
            query.setParameter(1, orderID);
            com.multimerchant_haze.rest.v1.modules.orders.model.Order order = (com.multimerchant_haze.rest.v1.modules.orders.model.Order)query.getSingleResult();
            return order;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Deprecated
    @Override
    public OrderHasClientHasProducer getOrderByOrderIDByClientIDByProducerID(String orderID, String clientID, String producerID)
    {
        try
        {
            String qlString = "SELECT orderHasClientHasProducer FROM OrderHasClientHasProducer orderHasClientHasProducer " +
                    "LEFT JOIN FETCH orderHasClientHasProducer.order singleOrder " +
                    "LEFT JOIN FETCH orderHasClientHasProducer.client client " +
                    "LEFT JOIN FETCH client.clientProfile clientProfile " +
                    "LEFT JOIN FETCH orderHasClientHasProducer.producer producer " +
                    "LEFT JOIN FETCH producer.producerProfile producerProfile " +
                    "WHERE singleOrder.orderID = ?1 AND clientProfile.clientID = ?2 AND producerProfile.producerID = ?3";
            Query query = entityManager.createQuery(qlString, OrderHasClientHasProducer.class);
            query.setParameter(1, orderID);
            query.setParameter(2, clientID);
            query.setParameter(3, producerID);
            OrderHasClientHasProducer orderHasClientHasProducer = (OrderHasClientHasProducer)query.getSingleResult();
            return orderHasClientHasProducer;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    public com.multimerchant_haze.rest.v1.modules.orders.model.Order getSingleOrderFullEntitiesByOrderID(String orderID)
    {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(com.multimerchant_haze.rest.v1.modules.orders.model.Order.class);

        Root<com.multimerchant_haze.rest.v1.modules.orders.model.Order> orderRoot = criteriaQuery.from(com.multimerchant_haze.rest.v1.modules.orders.model.Order.class);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderHasClientHasProducer> orderHasClientHasProducer = orderRoot.join("orderHasClientHasProducer", JoinType.LEFT);
        Join<OrderHasClientHasProducer, Client> client = orderHasClientHasProducer.join("client", JoinType.LEFT);
        Join<OrderHasClientHasProducer, Producer> producer = orderHasClientHasProducer.join("producer", JoinType.LEFT);
        Join<Producer, ProducerProfile> producerProfile = producer.join("producerProfile", JoinType.LEFT);
        Join<Client, ClientProfile> clientProfile =  client.join("clientProfile", JoinType.LEFT);
        SetJoin<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderHasProducts> orderHasProducts = (SetJoin)orderRoot.fetch("orderHasProducts", JoinType.LEFT);
        Join<OrderHasProducts, OrderProduct> orderProduct = orderHasProducts.join("orderProduct", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderClientDetails> orderClientDetails = orderRoot.join("orderClientDetails", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderProducerDetails> orderProducerDetails = orderRoot.join("orderProducerDetails", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderClientAddressDetails> orderClientAddressDetails = orderRoot.join("orderClientAddressDetails", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderProducerAddressDetails> orderProducerAddressDetails = orderRoot.join("orderProducerAddressDetails", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderStatusCodes> orderStatusCode = orderRoot.join("orderStatusCode", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, Payment> orderPayment = orderRoot.join("orderPayment", JoinType.LEFT);
        Join<Payment, PaymentMethod> paymentMethod = orderPayment.join("paymentMethod", JoinType.LEFT);
        Join<Payment, PaymentApproval> paymentApproval = orderPayment.join("paymentApproval", JoinType.LEFT);
        Join<PaymentApproval, PaymentDeposit> paymentDeposit = paymentApproval.join("paymentDeposit", JoinType.LEFT);
        Join<PaymentDeposit, PaymentRefund> paymentRefund = paymentDeposit.join("paymentRefund", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, ShoppingCart> shoppingCart = orderRoot.join("shoppingCart", JoinType.LEFT);
/*
        SetJoin<ShoppingCart, ShoppingCartProduct> shoppingCartProduct = (SetJoin)shoppingCart.fetch("shoppingCartProducts", JoinType.LEFT);
*/

        Predicate predicateOrderIDForSearch = criteriaBuilder.equal(orderRoot.get("orderID"), orderID);

        TypedQuery<com.multimerchant_haze.rest.v1.modules.orders.model.Order> typedQuery = entityManager.createQuery(
                criteriaQuery
                        .select(orderRoot)
                        .where(predicateOrderIDForSearch)
                        .distinct(true)
        );

        com.multimerchant_haze.rest.v1.modules.orders.model.Order orderFromDatabase;
        try
        {
            orderFromDatabase = typedQuery.getSingleResult();

        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
        return orderFromDatabase;
    }



    public com.multimerchant_haze.rest.v1.modules.orders.model.Order getOrderByShoppingCartID(String shoppingCartID)
    {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(com.multimerchant_haze.rest.v1.modules.orders.model.Order.class);

        Root<com.multimerchant_haze.rest.v1.modules.orders.model.Order> orderRoot = criteriaQuery.from(com.multimerchant_haze.rest.v1.modules.orders.model.Order.class);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderHasClientHasProducer> orderHasClientHasProducer = orderRoot.join("orderHasClientHasProducer", JoinType.LEFT);
        Join<OrderHasClientHasProducer, Client> client = orderHasClientHasProducer.join("client", JoinType.LEFT);
        Join<OrderHasClientHasProducer, Producer> producer = orderHasClientHasProducer.join("producer", JoinType.LEFT);
        Join<Producer, ProducerProfile> producerProfile = producer.join("producerProfile", JoinType.LEFT);
        Join<Client, ClientProfile> clientProfile =  client.join("clientProfile", JoinType.LEFT);
        SetJoin<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderHasProducts> orderHasProducts = (SetJoin)orderRoot.fetch("orderHasProducts", JoinType.LEFT);
        Join<OrderHasProducts, OrderProduct> orderProduct = orderHasProducts.join("orderProduct", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderClientDetails> orderClientDetails = orderRoot.join("orderClientDetails", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderProducerDetails> orderProducerDetails = orderRoot.join("orderProducerDetails", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderClientAddressDetails> orderClientAddressDetails = orderRoot.join("orderClientAddressDetails", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderProducerAddressDetails> orderProducerAddressDetails = orderRoot.join("orderProducerAddressDetails", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, OrderStatusCodes> orderStatusCode = orderRoot.join("orderStatusCode", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, Payment> orderPayment = orderRoot.join("orderPayment", JoinType.LEFT);
        Join<Payment, PaymentMethod> paymentMethod = orderPayment.join("paymentMethod", JoinType.LEFT);
        Join<Payment, PaymentApproval> paymentApproval = orderPayment.join("paymentApproval", JoinType.LEFT);
        Join<PaymentApproval, PaymentDeposit> paymentDeposit = paymentApproval.join("paymentDeposit", JoinType.LEFT);
        Join<PaymentDeposit, PaymentRefund> paymentRefund = paymentDeposit.join("paymentRefund", JoinType.LEFT);
        Join<com.multimerchant_haze.rest.v1.modules.orders.model.Order, ShoppingCart> shoppingCart = orderRoot.join("shoppingCart", JoinType.LEFT);

        Predicate predicateOrderIDForSearch = criteriaBuilder.equal(orderRoot.join("shoppingCart").get("shoppingCartID"), shoppingCartID);

        TypedQuery<com.multimerchant_haze.rest.v1.modules.orders.model.Order> typedQuery = entityManager.createQuery(
                criteriaQuery
                        .select(orderRoot)
                        .where(predicateOrderIDForSearch)
                        .distinct(true)
        );


        com.multimerchant_haze.rest.v1.modules.orders.model.Order orderFromDatabase;
        try
        {
            orderFromDatabase = typedQuery.getSingleResult();

        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
        return orderFromDatabase;
    }
}

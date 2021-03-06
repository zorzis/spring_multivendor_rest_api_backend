package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.*;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientProfile;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerProfile;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentDeposit;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentRefund;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by zorzis on 7/11/2017.
 */
@Repository("clientOrdersDAO")
public class ClientOrdersDAOImplementation implements ClientOrdersDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    public List<com.multimerchant_haze.rest.v1.modules.orders.model.Order> getClientOrders(String clientEmail)
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

        Predicate predicateOrderIDForSearch = criteriaBuilder.equal(orderRoot.join("orderHasClientHasProducer").join("client").get("email"), clientEmail);

        TypedQuery<com.multimerchant_haze.rest.v1.modules.orders.model.Order> typedQuery = entityManager.createQuery(
                criteriaQuery
                        .select(orderRoot)
                        .where(predicateOrderIDForSearch)
                        .orderBy(criteriaBuilder.desc(orderRoot.get("dateOrderPlaced")))
                        .distinct(true)
        );

        List<com.multimerchant_haze.rest.v1.modules.orders.model.Order> orders;
        try
        {
            orders = typedQuery.getResultList();

        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
        return orders;
    }



}

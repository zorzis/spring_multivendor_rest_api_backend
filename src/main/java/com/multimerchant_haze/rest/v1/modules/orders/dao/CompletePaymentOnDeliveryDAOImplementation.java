package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by zorzis on 10/13/2017.
 */
@Repository("onDeliveryCompletePaymentDAO")
public class CompletePaymentOnDeliveryDAOImplementation implements CompletePaymentOnDeliveryDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaymentOnDelivery getOrderByOrderID(String orderID)
    {
        try
        {
            String qlString = "SELECT paymentOnDelivery FROM PaymentOnDelivery paymentOnDelivery " +
                    "LEFT JOIN FETCH paymentOnDelivery.paymentMethod paymentMethod " +
                    "LEFT JOIN FETCH paymentOnDelivery.order paypalOrder " +
                    "LEFT JOIN FETCH paymentOnDelivery.paymentApproval paymentApproval " +
                    "LEFT JOIN FETCH paymentApproval.paymentDeposit paymentDeposit " +
                    "LEFT JOIN FETCH paymentDeposit.paymentRefund paymentRefund " +
                    "LEFT JOIN FETCH paymentOnDelivery.order orderEntity " +
                    "LEFT JOIN FETCH orderEntity.orderHasClientHasProducer orderHasClientHasProducer " +
                    "LEFT JOIN FETCH orderHasClientHasProducer.client client " +
                    "LEFT JOIN FETCH client.clientProfile clientProfile " +
                    "LEFT JOIN FETCH orderHasClientHasProducer.producer producer " +
                    "LEFT JOIN FETCH producer.producerProfile producerProfile " +
                    "LEFT JOIN FETCH orderEntity.orderClientDetails orderClientDetails " +
                    "LEFT JOIN FETCH orderEntity.orderClientAddressDetails orderClientAddressDetails " +
                    "LEFT JOIN FETCH orderEntity.orderProducerDetails orderProducerDetails " +
                    "LEFT JOIN FETCH orderEntity.orderProducerAddressDetails orderProducerAddressDetails " +
                    "LEFT JOIN FETCH orderEntity.orderStatusCode orderStatusCode " +
                    "LEFT JOIN FETCH orderEntity.orderHasProducts orderHasProducts " +
                    "LEFT JOIN FETCH orderHasProducts.orderProduct orderProduct " +
                    "WHERE orderEntity.orderID = ?1";
            Query query = entityManager.createQuery(qlString, PaymentOnDelivery.class);
            query.setParameter(1, orderID);
            PaymentOnDelivery paymentOnDelivery = (PaymentOnDelivery)query.getSingleResult();
            return paymentOnDelivery;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public String saveTheDepositInformation(PaymentOnDelivery paymentOnDelivery)
    {
        entityManager.persist(paymentOnDelivery.getPaymentApproval().getPaymentDeposit());
        entityManager.merge(paymentOnDelivery);
        return paymentOnDelivery.getOrder().getOrderID();
    }
}

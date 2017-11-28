package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by zorzis on 10/6/2017.
 */
@Repository("paypalCompletePaymentDAO")
public class CompletePaymentPaypalDAOImplementation implements CompletePaymentPaypalDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaymentPaypal getOrderPaymentByOriginalPaypalPaymentID(String paypalPaymentID)
    {
        try
        {
            String qlString = "SELECT paymentPaypal FROM PaymentPaypal paymentPaypal " +
                    "LEFT JOIN FETCH paymentPaypal.paymentMethod paymentMethod " +
                    "LEFT JOIN FETCH paymentPaypal.order paypalOrder " +
                    "LEFT JOIN FETCH paymentPaypal.paymentApproval paymentApproval " +
                    "LEFT JOIN FETCH paymentApproval.paymentDeposit paymentDeposit " +
                    "LEFT JOIN FETCH paymentDeposit.paymentRefund paymentRefund " +
                    "LEFT JOIN FETCH paymentPaypal.order orderEntity " +
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
                    "WHERE paymentPaypal.paypalApprovalPaymentID = ?1";
            Query query = entityManager.createQuery(qlString, PaymentPaypal.class);
            query.setParameter(1, paypalPaymentID);
            PaymentPaypal paymentPaypal = (PaymentPaypal)query.getSingleResult();
            return paymentPaypal;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public String saveTheDepositInformation(PaymentPaypal paymentPaypal)
    {
        entityManager.persist(paymentPaypal.getPaymentApproval().getPaymentDeposit());
        entityManager.merge(paymentPaypal);
        return paymentPaypal.getOrder().getOrderID();
    }


}

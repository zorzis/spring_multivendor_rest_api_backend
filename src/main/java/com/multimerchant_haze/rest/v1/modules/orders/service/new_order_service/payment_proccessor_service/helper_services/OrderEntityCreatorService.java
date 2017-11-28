package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.helper_services;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.OrderStatusCodes;

/**
 * Created by zorzis on 10/5/2017.
 */
public interface OrderEntityCreatorService
{
    public Order createOrderEntity(ShoppingCartDTO shoppingCartDTO) throws AppException;

    public PaymentMethod checkIfPaymentMethodBelongsToProducer(String paymentMethodID, Producer producerOwnTheOrder) throws AppException;

    public String paymentIDUniqueGenerator() throws AppException;

    public String paymentApprovalIDUniqueGenerator() throws AppException;

    public String paymentDepositIDUniqueGenerator() throws AppException;

    public void checkIfOrderStatusCodeRefExists(OrderStatusCodes orderStatusCodeRef);



}

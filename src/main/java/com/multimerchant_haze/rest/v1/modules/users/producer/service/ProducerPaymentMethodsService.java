package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.payments.dto.PaymentMethodDTO;

import java.util.List;

/**
 * Created by zorzis on 9/9/2017.
 */
public interface ProducerPaymentMethodsService
{
    /**
     * Get Original Payment Methods from database. For our design we decided to implement this
     * from ***user/admin*** module as Model Object and DTO Object
     * @return
     */
    public List<PaymentMethodDTO> getAllStandardPaymentMethods() throws AppException;


    public ProducerDTO getProducerWithPaymentMethodsByProducerEmail(ProducerDTO producerDTO) throws AppException;

    public String addPaymentMethodToProducerPaymentMethodsByProducerEMAIL(ProducerDTO producerDTO, PaymentMethodDTO paymentMethodDTO) throws AppException;



}

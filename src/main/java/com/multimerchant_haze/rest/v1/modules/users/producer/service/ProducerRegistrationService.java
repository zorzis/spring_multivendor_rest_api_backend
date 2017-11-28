package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;

/**
 * Created by zorzis on 6/7/2017.
 */
public interface ProducerRegistrationService
{
    public String registerNewProducer(ProducerDTO producerDTO) throws AppException;
}

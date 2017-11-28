package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;

/**
 * Created by zorzis on 7/12/2017.
 */
public interface ProducerOrdersService
{
    public ProducerDTO getAllOrdersBelongingToProducerBasedOnProducerEmail(ProducerDTO producerDTO) throws AppException;

}

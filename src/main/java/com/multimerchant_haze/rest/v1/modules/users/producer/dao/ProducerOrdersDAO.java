package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;

/**
 * Created by zorzis on 7/12/2017.
 */
public interface ProducerOrdersDAO
{
    public Producer getProducerFetchingOrders(String producerEmail);

}

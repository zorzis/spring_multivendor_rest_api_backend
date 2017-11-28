package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAddress;

/**
 * Created by zorzis on 6/12/2017.
 */
public interface ProducerAddressDAO
{
    // get an ProducerAddress
    public ProducerAddress getProducerAddressByProducerEmail(String producerEmail);
}

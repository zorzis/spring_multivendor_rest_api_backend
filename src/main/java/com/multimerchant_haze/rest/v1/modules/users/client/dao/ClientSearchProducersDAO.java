package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;

import java.util.List;

/**
 * Created by zorzis on 6/13/2017.
 */
public interface ClientSearchProducersDAO
{
    public List<Producer> getAllActiveSellersProducers();

    public Producer getActiveSellerProducer(String producerID);
}

package com.multimerchant_haze.rest.v1.modules.users.client.service.client_search_producers;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;

import java.util.List;


/**
 * Created by zorzis on 6/13/2017.
 */
public interface ClientSearchProducersService
{
    public List<ProducerDTO> getActiveSellersProducers() throws AppException;

    public ProducerDTO getActiveProducer(String producerID) throws AppException;
}

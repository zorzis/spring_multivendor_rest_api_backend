package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerOrdersDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zorzis on 7/12/2017.
 */
@Service
public class ProducerOrdersServiceImplementation implements ProducerOrdersService
{
    @Autowired
    ProducerOrdersDAO producerOrdersDAO;

    public ProducerDTO getAllOrdersBelongingToProducerBasedOnProducerEmail(ProducerDTO producerDTO) throws AppException
    {
        Producer producer = this.producerOrdersDAO.getProducerFetchingOrders(producerDTO.getEmail());

        ProducerDTO producerDTOWithOrders = UserServiceHelper.createProducerDTOIfProducerEntityExists(producer, "Email", producerDTO.getEmail());

        producerDTOWithOrders.mapOrdersDTOsFromOrdersEntities(producer.getOrderHasClientHasProducerSet());

        return producerDTOWithOrders;
    }
}

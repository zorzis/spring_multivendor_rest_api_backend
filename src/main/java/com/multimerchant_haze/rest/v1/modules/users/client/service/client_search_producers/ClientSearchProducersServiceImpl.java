package com.multimerchant_haze.rest.v1.modules.users.client.service.client_search_producers;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientSearchProducersDAO;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorzis on 6/13/2017.
 */
@Service
public class ClientSearchProducersServiceImpl implements ClientSearchProducersService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSearchProducersServiceImpl.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ClientSearchProducersDAO clientSearchProducersDAO;

    @Override
    public List<ProducerDTO> getActiveSellersProducers() throws AppException
    {
        List<Producer> activeProducersList = this.clientSearchProducersDAO.getAllActiveSellersProducers();

        List<ProducerDTO> activeProducersDTOList = new ArrayList(0);

        for(int i = 0; i<activeProducersList.size(); i++)
        {
            Producer producer = activeProducersList.get(i);
            ProducerDTO activeProducerDTO = new ProducerDTO(producer);

            // asign products entities to producerDTO
            activeProducerDTO.mapProductsDTOsFromProductsEntities(producer.getProducerProductsEntities());

            //assign paymentMethods entities to producerDTO
            activeProducerDTO.mapProducerPaymentMethodsDTOsFromProducerPaymentMethodsEntities(producer.getProducerHasPaymentMethodSet());


            activeProducersDTOList.add(activeProducerDTO);
        }

        return activeProducersDTOList;
    }

    @Override
    public ProducerDTO getActiveProducer(String producerID) throws AppException
    {
        Producer activeProducer = this.clientSearchProducersDAO.getActiveSellerProducer(producerID);
        // check if producer exists
        ProducerDTO activeProducerDTO = UserServiceHelper.createProducerDTOIfProducerEntityExists(activeProducer, "ProducerID", producerID);
        // asign products entities to producerDTO
        activeProducerDTO.mapProductsDTOsFromProductsEntities(activeProducer.getProducerProductsEntities());

        //assign paymentMethods entities to producerDTO
        activeProducerDTO.mapProducerPaymentMethodsDTOsFromProducerPaymentMethodsEntities(activeProducer.getProducerHasPaymentMethodSet());
        return activeProducerDTO;
    }
}

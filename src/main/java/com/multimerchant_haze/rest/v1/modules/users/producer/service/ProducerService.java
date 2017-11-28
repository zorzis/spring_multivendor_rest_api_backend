package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;

/**
 * Created by zorzis on 3/2/2017.
 */
public interface ProducerService
{

    // delete a Producer
    public String deleteProducerByProducerEmail(ProducerDTO producerDTO) throws AppException;

    // delete user authority
    public String deleteProducerAuthorityByProducerEmailAndAuthorityID(ProducerDTO producerDTO, ProducerAuthorityDTO producerAuthorityDTO) throws AppException;

    // add ProducerAuthority to Producer
    public String addAuthorityToProducerByProducerEmailAndAuthorityName(ProducerDTO producerDTO, ProducerAuthorityDTO producerAuthorityDTO) throws AppException;


    /*
     * ******************** Helper methods **********************
     * */
    public ProducerDTO verifyProducerExists(Producer producerToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue ) throws AppException;

}

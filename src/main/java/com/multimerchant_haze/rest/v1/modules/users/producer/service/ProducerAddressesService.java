package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAddressDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;

/**
 * Created by zorzis on 6/8/2017.
 */
public interface ProducerAddressesService
{
    public ProducerDTO getProducerAddress(String email) throws AppException;

    // add Address to Producer
    public String addAddressToProducerByProducerEmailAndAddressDTO(ProducerDTO producerDTO, ProducerAddressDTO producerAddressDTO) throws AppException;

    // update Address of Producer
    public String updateAddressOfProducerByProducerEmailAndAddressDTO(ProducerDTO producerDTO, ProducerAddressDTO producerAddressDTO) throws AppException;


    // delete Address From Producer
    public String deleteAddressFromProducerByProducerEmailAndAddress(ProducerDTO clientDTO, ProducerAddressDTO producerAddressDTO) throws AppException;


}

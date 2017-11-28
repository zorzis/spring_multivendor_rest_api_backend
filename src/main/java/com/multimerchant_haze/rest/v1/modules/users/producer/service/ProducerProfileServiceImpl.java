package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by zorzis on 6/7/2017.
 */
@Service
public class ProducerProfileServiceImpl implements ProducerProfileService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerProfileServiceImpl.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ProducerDAO producerDAO;

    @Override
    public ProducerDTO getProducerAccountProfileByEmail(String email) throws AppException
    {
        Producer producerByEmail = producerDAO.getProducerByProducerEmailFetchingProfile(email);
        ProducerDTO producerDTO = UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail, "Email", email);
        return producerDTO;
    }

    @Override
    @Transactional("transactionManager")
    public String updateProducerProfile(ProducerDTO producerDTO) throws AppException
    {
        //verify existence of controller in the db (email must be unique)
        Producer producerByEmail = producerDAO.getProducerByProducerEmailFetchingProfile(producerDTO.getEmail());

        // Check if user already exists else throw AppException and stop process
        ProducerDTO producerDTOafterSuccessfullVerificationForExistence = UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail,"Email", producerDTO.getEmail());


        // set the properties to be changed
        producerByEmail.getProducerProfile().setFirstName(producerDTO.getFirstName());
        producerByEmail.getProducerProfile().setLastName(producerDTO.getLastName());
        producerByEmail.getProducerProfile().setBirthDate(producerDTO.getBirthDate());
        producerByEmail.getProducerProfile().setGender(producerDTO.getGender());
        //producerByEmail.setIsEnabled(producerDTO.getEnabled());
        producerByEmail.setUpdatedAt(new Date());

        return producerDAO.updateProducer(producerByEmail);
    }
}

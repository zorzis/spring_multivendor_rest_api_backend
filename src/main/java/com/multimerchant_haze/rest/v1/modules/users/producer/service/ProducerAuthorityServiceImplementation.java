package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerAuthorityDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zorzis on 3/12/2017.
 */
@Service
public class ProducerAuthorityServiceImplementation implements ProducerAuthorityService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerAuthorityServiceImplementation.class);
    private String className =  this.getClass().getSimpleName();

    @Autowired
    ProducerAuthorityDAO authorityDAO;


    @Override
    @Transactional("transactionManager")
    public ProducerAuthorityDTO getAuthorityByAuthorityName(String authorityName) throws AppException
    {
        ProducerAuthority userAuthorityByAuthorityName = authorityDAO.getAuthorityByAuthorityName(authorityName);
        ProducerAuthorityDTO producerAuthorityDTO = verifyAuthorityExists(userAuthorityByAuthorityName,
                                                            "AuthorityRole", authorityName);
        return producerAuthorityDTO;
    }



    @Override
    public ProducerAuthorityDTO verifyAuthorityExists(ProducerAuthority authorityToBeCheckedIfExists, String dataKeyToBeChecked, String dataValue) throws AppException
    {
        if(authorityToBeCheckedIfExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Authority Not Found!");
            sb.append(" ");
            sb.append(dataKeyToBeChecked);
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(dataValue);
            sb.append("]");
            sb.append(" ");
            sb.append("is not registered to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Authority entity with specific credentials not found at the Database");
            throw appException;
        }
        else
        {
            // create a user model object using Producer(UserEntity userEntity) constructor
            return new ProducerAuthorityDTO(authorityToBeCheckedIfExists);
        }
    }
}

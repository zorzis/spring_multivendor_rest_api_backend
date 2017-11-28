package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAuthority;

/**
 * Created by zorzis on 3/12/2017.
 */
public interface ProducerAuthorityService
{

    // get an ProducerAuthority
    public ProducerAuthorityDTO getAuthorityByAuthorityName(String authorityName) throws AppException;

    /*
    * ******************** Helper methods **********************
    * */
    public ProducerAuthorityDTO verifyAuthorityExists(ProducerAuthority authorityToBeCheckedIfExists,
                                                      String dataKeyToBeChecked,
                                                      String dataValue ) throws AppException;

}

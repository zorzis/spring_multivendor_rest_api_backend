package com.multimerchant_haze.rest.v1.modules.users.client.service.client_authorities;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientAuthorityDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAuthority;

/**
 * Created by zorzis on 5/8/2017.
 */
public interface ClientAuthorityService
{
    // get an ClientAuthority
    public ClientAuthorityDTO getAuthorityByAuthorityName(String authorityName) throws AppException;

    /*
    * ******************** Helper methods **********************
    * */
    public ClientAuthorityDTO verifyAuthorityExists(ClientAuthority authorityToBeCheckedIfExists,
                                                      String dataKeyToBeChecked,
                                                      String dataValue ) throws AppException;
}

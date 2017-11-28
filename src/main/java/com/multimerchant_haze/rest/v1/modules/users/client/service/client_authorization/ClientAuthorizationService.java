package com.multimerchant_haze.rest.v1.modules.users.client.service.client_authorization;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;

/**
 * Created by zorzis on 5/27/2017.
 */
public interface ClientAuthorizationService
{

    public String authorizeClientProducingJWToken(ClientDTO clientDTO) throws AppException, Exception;
}

package com.multimerchant_haze.rest.v1.modules.users.client.service.client_registration;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;

/**
 * Created by zorzis on 5/27/2017.
 */
public interface ClientRegistrationService
{
    public Client registerNewClient(ClientDTO clientDTO) throws AppException;

}

package com.multimerchant_haze.rest.v1.modules.users.client.service.client_verification_token;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientVerificationTokenDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;

/**
 * Created by zorzis on 11/19/2017.
 */
public interface ClientVerificationTokenService
{
    public ClientVerificationTokenDTO createClientVerificationToken(Client client) throws AppException;
}

package com.multimerchant_haze.rest.v1.modules.users.client.service.client_profile;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;

/**
 * Created by zorzis on 5/27/2017.
 */
public interface ClientProfileService
{
    public ClientDTO getClientAccountProfileByEmail(String email) throws AppException;

    public String updateClientProfile(ClientDTO clientDTO) throws AppException;
}

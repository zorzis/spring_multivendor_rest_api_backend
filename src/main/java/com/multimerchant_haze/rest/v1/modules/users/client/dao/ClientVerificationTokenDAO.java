package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientVerificationToken;

/**
 * Created by zorzis on 11/13/2017.
 */
public interface ClientVerificationTokenDAO
{
    public ClientVerificationToken getClientVerificationToken(String token);

    public Client getClientByVerificationToken(String token);

    public Client persistClientVerificationToken(Client client);
}

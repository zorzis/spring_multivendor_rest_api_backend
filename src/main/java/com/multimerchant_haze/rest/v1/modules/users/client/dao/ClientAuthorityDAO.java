package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAuthority;

/**
 * Created by zorzis on 5/8/2017.
 */
public interface ClientAuthorityDAO
{
    // get a ClientAuthority
    public ClientAuthority getAuthorityByAuthorityName(String authorityName);
}

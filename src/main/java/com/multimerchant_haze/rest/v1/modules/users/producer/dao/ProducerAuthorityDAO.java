package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAuthority;

/**
 * Created by zorzis on 3/12/2017.
 */
public interface ProducerAuthorityDAO
{
    // get an ProducerAuthority
    public ProducerAuthority getAuthorityByAuthorityName(String authorityName);

}

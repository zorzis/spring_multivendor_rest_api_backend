package com.multimerchant_haze.rest.v1.modules.users.client.dao;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.SearchFilterCriteriaDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;

import java.util.List;

/**
 * Created by zorzis on 10/29/2017.
 */
public interface ClientSearchDAO
{
    public List<Producer> searchProducersBasedOnFilterCriteria(SearchFilterCriteriaDTO params);

}

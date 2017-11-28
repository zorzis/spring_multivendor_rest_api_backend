package com.multimerchant_haze.rest.v1.modules.users.client.service.client_search;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.SearchFilterCriteriaDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;

import java.util.List;

/**
 * Created by zorzis on 10/28/2017.
 */
public interface ClientSearchService
{
    public List<ProducerDTO> searchProducers(SearchFilterCriteriaDTO searchFilterCriteria) throws AppException;
}

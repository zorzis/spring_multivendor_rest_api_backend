package com.multimerchant_haze.rest.v1.modules.users.client.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.SearchFilterCriteriaDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_search.ClientSearchService;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zorzis on 10/28/2017.
 */
@RestController
public class ClientsSearchCriteriaController
{
    @Autowired
    private ClientSearchService clientSearchService;

    @RequestMapping(value = "/client/search_producers", method = RequestMethod.POST)
    @JsonView(JsonACL.SearchByProducersPublicView.class)
    public ResponseEntity <List<ProducerDTO>> searchProducers(@RequestBody SearchFilterCriteriaDTO searchFilterCriteria) throws AppException

    {
        List<ProducerDTO> getEnabledProducers = this.clientSearchService.searchProducers(searchFilterCriteria);
        return new ResponseEntity<List<ProducerDTO>> (getEnabledProducers , HttpStatus.OK);
    }

}

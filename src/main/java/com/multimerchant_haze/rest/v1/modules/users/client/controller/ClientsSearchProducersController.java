package com.multimerchant_haze.rest.v1.modules.users.client.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_search_producers.ClientSearchProducersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zorzis on 6/13/2017.
 */
@RestController
public class ClientsSearchProducersController
{
    @Autowired
    private ClientSearchProducersService clientSearchProducersService;

    @RequestMapping(value = "/client/get_producers", method = RequestMethod.GET)
    @JsonView(JsonACL.SearchByProducersPublicView.class)
    public ResponseEntity getAllActiveProducers() throws AppException

    {
        List<ProducerDTO> getEnabledProducers = clientSearchProducersService.getActiveSellersProducers();
        return new ResponseEntity<List<ProducerDTO>> (getEnabledProducers , HttpStatus.OK);
    }

    @RequestMapping(value = "/client/get_producer", method = RequestMethod.POST)
    @JsonView(JsonACL.SearchByProducersPublicView.class)
    public ResponseEntity getProducer(@RequestParam(value = "producerID") String producerID) throws AppException

    {
        System.out.println("producerID from RequestParam is: " + producerID);
        ProducerDTO getEnabledProducer = clientSearchProducersService.getActiveProducer(producerID);
        return new ResponseEntity<ProducerDTO> (getEnabledProducer , HttpStatus.OK);
    }
}

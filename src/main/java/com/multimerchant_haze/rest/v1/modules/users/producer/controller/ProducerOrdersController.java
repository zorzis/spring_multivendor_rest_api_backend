package com.multimerchant_haze.rest.v1.modules.users.producer.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zorzis on 7/12/2017.
 */
@RestController
public class ProducerOrdersController
{
    @Autowired
    private ProducerOrdersService producerOrdersService;

    //@PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/get_orders", method = RequestMethod.POST)
    @JsonView(JsonACL.OrderListBelongingToProducer.class)
    public ResponseEntity getClientOrders(@RequestParam(value = "email") String email)
            throws AppException
    {
        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setEmail(email);

        ProducerDTO getProducerOrdersList = this.producerOrdersService.getAllOrdersBelongingToProducerBasedOnProducerEmail(producerDTO);
        return new ResponseEntity<ProducerDTO> (getProducerOrdersList, HttpStatus.OK);
    }

}

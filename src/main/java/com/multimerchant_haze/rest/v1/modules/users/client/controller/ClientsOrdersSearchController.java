package com.multimerchant_haze.rest.v1.modules.users.client.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.service.client_orders.ClientOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zorzis on 7/11/2017.
 */
@RestController
public class ClientsOrdersSearchController
{
    @Autowired
    private ClientOrdersService clientOrdersService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientsOrdersSearchController.class);
    private String className =  this.getClass().getSimpleName();


    @PreAuthorize("(hasAnyAuthority('ROLE_CLIENT') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/get_orders", method = RequestMethod.POST)
    @JsonView(JsonACL.OrderListBelongingToClient.class)
    public ResponseEntity<List<OrderDTO>> getClientOrders(@RequestParam(value = "email") String email)
            throws AppException
    {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmail(email);

        List<OrderDTO> orderDTOList = this.clientOrdersService.getAllOrdersBelongingToClientBasedOnClientEmail(clientDTO);
        return new ResponseEntity<List<OrderDTO>> (orderDTOList, HttpStatus.OK);
    }


    //@PreAuthorize("(hasAnyAuthority('ROLE_CLIENT') and isOwner(#clientEmail)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/get_single_order", method = RequestMethod.POST)
    @JsonView(JsonACL.OrderListBelongingToClient.class)
    public ResponseEntity<OrderDTO> getSingleClientOrder(@RequestParam(value = "email") String clientEmail,
                                               @RequestParam(value = "orderID") String orderID)
            throws AppException
    {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmail(clientEmail);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderID(orderID);


        OrderDTO getSingleOrder = this.clientOrdersService.getSingleOrderBasedOnOrderIDClientEmail(orderDTO, clientDTO);
        return new ResponseEntity<OrderDTO> (getSingleOrder, HttpStatus.OK);
    }



}

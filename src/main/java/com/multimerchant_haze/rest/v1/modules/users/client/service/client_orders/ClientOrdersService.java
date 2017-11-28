package com.multimerchant_haze.rest.v1.modules.users.client.service.client_orders;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;

import java.util.List;

/**
 * Created by zorzis on 7/11/2017.
 */
public interface ClientOrdersService
{

    public List<OrderDTO> getAllOrdersBelongingToClientBasedOnClientEmail(ClientDTO clientDTO) throws AppException;

    public OrderDTO getSingleOrderBasedOnOrderIDClientEmail(OrderDTO orderDTO, ClientDTO clientDTO) throws AppException;

}

package com.multimerchant_haze.rest.v1.modules.users.client.service.client_orders;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.orders.model.Order;
import com.multimerchant_haze.rest.v1.modules.security.json_web_token_security.utils.JwtTokenUtil;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientOrdersDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.ClientDTO;
import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import com.multimerchant_haze.rest.v1.modules.orders.dao.OrdersDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * Created by zorzis on 7/11/2017.
 */
@Service
public class ClientOrdersServiceImpl implements ClientOrdersService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private String className =  this.getClass().getSimpleName();


    @Autowired
    private OrdersDAO ordersDAO;

    @Autowired
    private ClientOrdersDAO clientOrdersDAO;

    @Autowired
    private ClientDAO clientDAO;



    @Override
    public List<OrderDTO> getAllOrdersBelongingToClientBasedOnClientEmail(ClientDTO clientDTO) throws AppException
    {
        Client clientClaimsOwnOrders = this.clientDAO.getClientByClientEmailFetchingProfileFetchVerificationToken(clientDTO.getEmail());
        UserServiceHelper.checkIfClientEntityExistsElseThrowException(clientClaimsOwnOrders, "Email", clientDTO.getEmail());

        List<Order> orderList = this.clientOrdersDAO.getClientOrders(clientDTO.getEmail());

        List<OrderDTO> orderDTOList = new ArrayList<>(0);
        for(Order order: orderList)
        {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }




    @Override
    public OrderDTO getSingleOrderBasedOnOrderIDClientEmail(OrderDTO orderDTO, ClientDTO clientDTO) throws AppException
    {
        // First check if client and producer by email indeed exists
        Client clientMadeTheOrder = this.clientDAO.getClientByEmailOnlyFromClientsTable(clientDTO.getEmail());
        UserServiceHelper.createClientDTOIfClientEntityExists(clientMadeTheOrder, "Email", clientDTO.getEmail());

        // check orderID is not null or empty
        if(orderDTO.getOrderID() == null || orderDTO.getOrderID().isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad Request!");
            sb.append(" ");
            sb.append("Your request does not contain any orderID for searching!");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Your request does not contain an OrderID for search");
            throw appException;
        }

        // Get the order from database based on orderID
        Order orderFromDatabase = this.ordersDAO.getSingleOrderFullEntitiesByOrderID(orderDTO.getOrderID());
        // check if found else appexception
        if(orderFromDatabase == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Order Not Found!");
            sb.append(" ");
            sb.append("Order with ID");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(orderDTO.getOrderID());
            sb.append("]");
            sb.append(" ");
            sb.append("not found to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Order entity not found at the Database");
            throw appException;
        }

        // check if order belongs to client ask for it
        if(!Objects.equals(clientDTO.getEmail(), orderFromDatabase.getOrderHasClientHasProducer().getClient().getEmail()))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Not privileged to access");
            sb.append(" ");
            sb.append("Order with ID");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(orderDTO.getOrderID());
            sb.append("]");
            sb.append(".");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Order Requested does not belong to specific client.");
            throw appException;
        }


        OrderDTO orderDTOToBeReturned = new OrderDTO(orderFromDatabase);
        return orderDTOToBeReturned;
    }


}

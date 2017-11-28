package com.multimerchant_haze.rest.v1.modules.orders.service.orders_search_service;

import com.multimerchant_haze.rest.v1.modules.orders.dao.OrdersDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientDAO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zorzis on 7/10/2017.
 */
@Service
public class OrdersSearchServiceImpl implements OrdersSearchService
{
    @Autowired
    OrdersDAO ordersDAO;

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    ProducerDAO producerDAO;




}

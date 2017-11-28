package com.multimerchant_haze.rest.v1.modules.users.client.controller;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.OrderProcessorService;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.IPaymentProcessorStep2CompleterService;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartDTO;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.IPaymentProcessorStep1SetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by zorzis on 10/12/2017.
 */
@RestController
public class ClientsNewOrderOnDeliveryController
{
    @Autowired
    private OrderProcessorService orderProcessorService;

    @Qualifier("paymentProcessorStep1SetterServiceOnDelivery")
    @Autowired
    IPaymentProcessorStep1SetterService paypalIPaymentProcessorStep1SetterService;

    @Qualifier("paymentProcessorStep2CompleterServiceOnDelivery")
    @Autowired
    private IPaymentProcessorStep2CompleterService paymentProcessorStep2CompleterService;


    //@PreAuthorize("(hasAnyAuthority('ROLE_CLIENT') and isOwner(#shoppingCartDTO.customerEmail)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/order-checkout/ondelivery", method = RequestMethod.POST)
    public ResponseEntity<OrderDTO> placeNewOrderForOnDeliveryPayment(@RequestBody ShoppingCartDTO shoppingCartDTO)
            throws AppException, IOException
    {
        String shoppingCartID = this.orderProcessorService.saveShoppingCartToDatabase(shoppingCartDTO);

        String createdOrderID = (String)this.paypalIPaymentProcessorStep1SetterService.placeOrder(shoppingCartID);

        // At second Step of Payment Process we just follow the logic and we create
        // a PaymentDeposit Entity and we persist it among with the updated Order Status and other info
        OrderDTO createdOrderDTO = (OrderDTO)this.paymentProcessorStep2CompleterService.completeOrderPaymentToProcessorService(createdOrderID);


        // Send the order confirmation email to client
        this.orderProcessorService.sendOrderConfirmationEmailToClient(createdOrderDTO);

        return new ResponseEntity<OrderDTO> (createdOrderDTO, HttpStatus.OK);

    }
}
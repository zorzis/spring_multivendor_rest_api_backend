package com.multimerchant_haze.rest.v1.modules.users.client.controller;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.PaypalDataObjectReceivedFromFrontEndDTO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.PaypalPaymentIDResponseDTO;
import com.multimerchant_haze.rest.v1.modules.orders.dto.ShoppingCartDTO;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.OrderProcessorService;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.IPaymentProcessorStep1SetterService;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.IPaymentProcessorStep2CompleterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zorzis on 10/12/2017.
 */
@RestController
public class ClientsNewOrderPaypalController
{
    @Qualifier("paymentProcessorStep1SetterServicePaypal")
    @Autowired
    IPaymentProcessorStep1SetterService paypalIPaymentProcessorStep1SetterService;

    @Qualifier("paymentProcessorStep2CompleterServicePaypal")
    @Autowired
    private IPaymentProcessorStep2CompleterService paymentProcessorStep2CompleterService;

    @Autowired
    OrderProcessorService orderProcessorService;


    
    @PreAuthorize("(hasAnyAuthority('ROLE_CLIENT') and isOwner(#shoppingCartDTO.customerEmail)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/order-checkout/paypal", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO)
            throws AppException
    {


        Object objectToBeReturned = this.orderProcessorService.saveShoppingCartToDatabase(shoppingCartDTO);

        String shoppingCartID = (String)objectToBeReturned;
        ShoppingCartDTO shoppingCartDTOToBeReturned = new ShoppingCartDTO();
        shoppingCartDTOToBeReturned.setShoppingCartID(shoppingCartID);

        return new ResponseEntity<ShoppingCartDTO> (shoppingCartDTOToBeReturned, HttpStatus.CREATED);

    }

    /**
     * Only Paypal API calls the specific route, so that can create a Payment,
     * and get back a PaymentID
     *
     * @param shoppingCartID
     * @return
     * @throws AppException
     */
    @RequestMapping(value = "/client/payment-checkout/paypal/create/{shoppingCartID}", method = RequestMethod.POST)
    public ResponseEntity<PaypalPaymentIDResponseDTO> createPaypalPayment(@PathVariable("shoppingCartID") String shoppingCartID)
            throws AppException
    {

        // we need to return a json format like below from Paypal Dev Express Checkout Server-Side REST

        /*
            {
                "paymentID": "PAY-0J356327TH335450NK56Y2PQ"
            }
        */

        System.out.println("shoppingCartID from API URL REQUEST is : " + shoppingCartID);

        String paymentID = (String)this.paypalIPaymentProcessorStep1SetterService.placeOrder(shoppingCartID);

        PaypalPaymentIDResponseDTO paypalPaymentIDResponseDTO = new PaypalPaymentIDResponseDTO();
        paypalPaymentIDResponseDTO.setPaymentID(paymentID);

        return new ResponseEntity<PaypalPaymentIDResponseDTO>(paypalPaymentIDResponseDTO, HttpStatus.OK);
    }



    /**
     * This API route is activated for PAYPAL API calls only
     * TODO We have to implement a restrict rule like accepts calls only from PAYPAL API URL eg: https://sandbox.paypal.com
     *
     * @param paymentId
     * @param payerID
     * @return
     * @throws AppException
     */
    @RequestMapping(value = "/client/payment-checkout/paypal/execute", method = RequestMethod.POST)
    public ResponseEntity<OrderDTO> executePaypalCheckout(@RequestParam(value = "paymentID") String paymentId,
                                                          @RequestParam(value = "payerID") String payerID)
            throws AppException

    {

        PaypalDataObjectReceivedFromFrontEndDTO paypalDataObjectReceivedFromFrontEndDTO = new PaypalDataObjectReceivedFromFrontEndDTO(paymentId, payerID);

        Object object = this.paymentProcessorStep2CompleterService.completeOrderPaymentToProcessorService(paypalDataObjectReceivedFromFrontEndDTO);

        OrderDTO orderDTO = (OrderDTO)object;

        // Send the order confirmation email to client
        this.orderProcessorService.sendOrderConfirmationEmailToClient(orderDTO);

        return new ResponseEntity<OrderDTO> (orderDTO, HttpStatus.OK);

    }

}

package com.multimerchant_haze.rest.v1.modules.users.producer.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.payments.dto.PaymentMethodDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerPaymentMethodsService;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zorzis on 9/9/2017.
 */
@RestController
public class ProducerPaymentMethodsController
{
    @Autowired
    ProducerPaymentMethodsService paymentMethodsService;


    /**
     * Get Original Payment Methods from database. For our design we decided to implement this
     * from ***user/admin*** module as Model Object and DTO Object
     * @return
     */
    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/get_original_payment_methods", method = RequestMethod.POST)
    @JsonView(JsonACL.ProducersView.class)
    public ResponseEntity getPaymentMethods(@RequestParam(value = "email") String email)
            throws AppException
    {
        List<PaymentMethodDTO> paymentMethodDTOList;

        paymentMethodDTOList = this.paymentMethodsService.getAllStandardPaymentMethods();

        return new ResponseEntity<List<PaymentMethodDTO>> (paymentMethodDTOList , HttpStatus.OK);
    }



    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/get_payment_methods", method = RequestMethod.POST)
    @JsonView(JsonACL.ProducersView.class)
    public ResponseEntity getProducerFetchingPaymentMethods(@RequestParam(value = "email") String email)
            throws AppException
    {
        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setEmail(email);

        ProducerDTO getProducerFetchingPaymentMethods= this.paymentMethodsService.getProducerWithPaymentMethodsByProducerEmail(producerDTO);
        return new ResponseEntity<ProducerDTO> (getProducerFetchingPaymentMethods, HttpStatus.OK);
    }


    //@PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/add_payment_method", method = RequestMethod.POST)
    public ResponseEntity addPaymentMethod(@RequestParam(value = "email") String email,
                                           @RequestParam(value = "paymentMethodID") String paymentMethodID)
            throws AppException
    {
        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setEmail(email);

        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setPaymentMethodID(paymentMethodID);



        String assignedNewPaymentMethodName = this.paymentMethodsService.addPaymentMethodToProducerPaymentMethodsByProducerEMAIL(producerDTO, paymentMethodDTO);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Payment Method: [" + assignedNewPaymentMethodName + "] added successfully for Producer " + email), HttpStatus.OK);

    }
}

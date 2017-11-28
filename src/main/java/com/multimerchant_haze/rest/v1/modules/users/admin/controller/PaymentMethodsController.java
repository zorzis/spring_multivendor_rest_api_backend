package com.multimerchant_haze.rest.v1.modules.users.admin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.payments.dto.PaymentMethodDTO;
import com.multimerchant_haze.rest.v1.modules.payments.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zorzis on 9/8/2017.
 */
@RestController
public class PaymentMethodsController
{
    @Autowired
    private PaymentMethodService paymentMethodService;

    //@PreAuthorize("(hasAnyAuthority('ROLE_ADMIN') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/get_payment_methods", method = RequestMethod.POST)
    @JsonView(JsonACL.AdminsView.class)
    public ResponseEntity getPaymentMethods(@RequestParam(value = "email") String email)
            throws AppException
    {
        List<PaymentMethodDTO> paymentMethodDTOList;

        paymentMethodDTOList = this.paymentMethodService.getAllPaymentMethods();

        return new ResponseEntity<List<PaymentMethodDTO>> (paymentMethodDTOList , HttpStatus.OK);
    }

    //@PreAuthorize("(hasAnyAuthority('ROLE_ADMIN') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/create_new_payment_method", method = RequestMethod.POST)
    @JsonView(JsonACL.AdminsView.class)
    public ResponseEntity createNewPaymentMethod(@RequestParam(value = "email") String email,
                                                 @RequestParam(value = "paymentMethodID") String paymentMethodID,
                                                 @RequestParam(value = "paymentMethodName") String paymentMethodName,
                                                 @RequestParam(value = "paymentMethodDescription") String paymentMethodDescription)
            throws AppException
    {
       PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
       paymentMethodDTO.setPaymentMethodID(paymentMethodID);
       paymentMethodDTO.setPaymentMethodName(paymentMethodName);
       paymentMethodDTO.setPaymentMethodDescription(paymentMethodDescription);

       String createdPaymentMethodName = this.paymentMethodService.addNewPaymentMethod(paymentMethodDTO);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Payment Method: [" + createdPaymentMethodName + "] created successfully"), HttpStatus.OK);
    }

    //@PreAuthorize("(hasAnyAuthority('ROLE_ADMIN') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/client/delete_payment_method", method = RequestMethod.POST)
    @JsonView(JsonACL.AdminsView.class)
    public ResponseEntity deletePaymentMethod(@RequestParam(value = "email") String email,
                                                 @RequestParam(value = "paymentMethodID") String paymentMethodID,
                                                 @RequestParam(value = "paymentMethodName") String paymentMethodName)
            throws AppException
    {
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setPaymentMethodID(paymentMethodID);
        paymentMethodDTO.setPaymentMethodName(paymentMethodName);

        String deletedPaymentMethodName = this.paymentMethodService.deletePaymentMethodByID(paymentMethodDTO);

        return new ResponseEntity<String> ("Payment method: [" + deletedPaymentMethodName + "] deleted successfully!", HttpStatus.OK);
    }
}

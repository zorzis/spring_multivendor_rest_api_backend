package com.multimerchant_haze.rest.v1.modules.payments.dto.payment_types_dto;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentPaypal;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.Payment;
import com.multimerchant_haze.rest.v1.modules.payments.model.payment_types_model.PaymentOnDelivery;

/**
 * Created by zorzis on 10/2/2017.
 */
public class PaymentToPaymentDTOService
{
    public static PaymentDTO convertPaymentEntityToPaymentDTO(Payment payment)
    {
        PaymentDTO paymentDTO = null;

        if(payment == null)
        {
            // TODO Update a Notification System or create at least a Log
            paymentDTO = null;
        }
        else
        {
            if(payment instanceof PaymentOnDelivery)
            {
                paymentDTO = new PaymentOnDeliveryDTO((PaymentOnDelivery)payment);
            }
            else if(payment instanceof PaymentPaypal)
            {
                paymentDTO = new PaymentPaypalDTO((PaymentPaypal)payment);
            }
        }

        return paymentDTO;
    }
}

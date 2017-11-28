package com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.payment_processor_visa;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.service.new_order_service.payment_proccessor_service.IPaymentProcessorStep2CompleterService;
import org.springframework.stereotype.Service;

/**
 * Created by zorzis on 10/6/2017.
 */
@Service
public class PaymentProcessorStep2CompleterServiceVisa implements IPaymentProcessorStep2CompleterService
{
    @Override
    public Object completeOrderPaymentToProcessorService(Object object) throws AppException
    {
        return null;
    }
}

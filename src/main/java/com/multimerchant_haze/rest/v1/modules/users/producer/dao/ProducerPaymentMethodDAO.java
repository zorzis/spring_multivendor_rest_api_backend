package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerHasPaymentMethod;

/**
 * Created by zorzis on 9/7/2017.
 */
public interface ProducerPaymentMethodDAO
{

    public Producer getProducerByProducerIDFetchingAddressFetchingProductsFetchingPaymentMethods(String producerEmail);

    public String addProducerHasPaymentMethod(ProducerHasPaymentMethod producerHasPaymentMethod);

    public String deleteProducerHasPaymentMethod(ProducerHasPaymentMethod producerHasPaymentMethod);

}

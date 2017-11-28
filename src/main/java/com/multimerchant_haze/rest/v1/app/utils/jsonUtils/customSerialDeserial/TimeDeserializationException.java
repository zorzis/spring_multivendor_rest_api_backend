package com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial;


import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * Custom exception thrown when it was not possible to deserialize a time field,
 * @see CustomTimeDeserializer
 *
 * Created by zorzis on 3/2/2017.
 */
public class TimeDeserializationException extends JsonProcessingException
{

    protected TimeDeserializationException(Throwable rootCause)
    {
        super(rootCause);
    }

}

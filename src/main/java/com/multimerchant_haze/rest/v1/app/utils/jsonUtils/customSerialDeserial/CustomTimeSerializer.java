package com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 *
 * custom JSON deserializer for java.sql.Time type
 *
 * Created by zorzis on 3/2/2017.
 */
public class CustomTimeSerializer extends JsonSerializer<Time>
{

    @Override
    public void serialize(Time value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException
    {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        jgen.writeString(formatter.format(value));
    }

}

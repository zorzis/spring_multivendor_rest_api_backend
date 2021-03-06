package com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zorzis on 3/2/2017.
 */
public class CustomDateSerializer extends JsonSerializer<Date>
{

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MMMM-dd HH:mm:ss");


    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException
    {
        if (date == null)
        {
            jsonGenerator.writeNull();
        }
        else
        {
            jsonGenerator.writeString(FORMATTER.format(date.getTime()));
        }
    }
}
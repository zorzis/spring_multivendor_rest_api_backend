package com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by zorzis on 3/2/2017.
 */
public class CustomDateDeserializer extends JsonDeserializer<Date>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomDateDeserializer.class);


    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException, JsonProcessingException
    {
            String dateAsString = jsonparser.getText();
            try
            {
                LOGGER.info("converting date parameter from value " + jsonparser.getText());

                Date date = CustomDateSerializer.FORMATTER.parse(dateAsString);

                return date;
            } catch (ParseException e)
            {
                throw new DateDeserializationException(e);
            }
    }
}

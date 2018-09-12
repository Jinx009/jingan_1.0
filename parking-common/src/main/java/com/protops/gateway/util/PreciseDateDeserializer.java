package com.protops.gateway.util;

/**
 * Created by damen on 2015/12/24.
 */

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

public class PreciseDateDeserializer extends JsonDeserializer<Date> {
//
//    @Override
//    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String formattedDate = formatter.format(value);
//        jgen.writeString(formattedDate);
//    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return DateUtil.parseDate(jsonParser.getText(), DateUtil.DATE_FMT_DISPLAY);
    }
}

package com.protops.gateway.util;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

public class JsonConverter {

    private static final Logger logger = LoggerFactory.getLogger(JsonConverter.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.getDeserializationConfig().setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat(DATE_FORMAT));

        mapper.configure(
                DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static JsonConverter instance = new JsonConverter();

    private JsonConverter() {

    }

    public static JsonConverter getInstance() {
        return instance;
    }

    /**
     * 对象转换成JSON字符串
     *
     * @param o
     * @return
     */
    public String toString(Object o) {
        StringWriter stringWriter = new StringWriter();
        try {
            mapper.writeValue(stringWriter, o);
        } catch (JsonGenerationException e) {
            logger.error("object2Json error", e);
        } catch (JsonMappingException e) {
            logger.error("object2Json error", e);
        } catch (IOException e) {
            logger.error("object2Json error", e);
        }
        return stringWriter.toString();
    }

    /**
     * JSON字符串转换成对象
     *
     * @param <T>
     * @param clazz
     * @return
     */
    public <T> T toObject(String str, Class<T> clazz) {
        T obj = null;
        try {
            obj = mapper.readValue(str, clazz);
        } catch (JsonParseException e) {
            logger.error("json2Object error", e);
        } catch (JsonMappingException e) {
            logger.error("json2Object error", e);
        } catch (IOException e) {
            logger.error("json2Object error", e);
        }
        return obj;
    }

    public Object toObject(String str, TypeReference type) {
        Object obj = null;
        try {
            obj = mapper.readValue(str, type);
        } catch (JsonParseException e) {
            logger.error("json2Object error", e);
        } catch (JsonMappingException e) {
            logger.error("json2Object error", e);
        } catch (IOException e) {
            logger.error("json2Object error", e);
        }
        return obj;
    }

    public <T> T toObjectthrowException(String str, Class<T> clazz) throws IOException {
        return mapper.readValue(str, clazz);
    }
}
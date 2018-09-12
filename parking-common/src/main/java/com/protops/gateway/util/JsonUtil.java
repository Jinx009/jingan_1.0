package com.protops.gateway.util;

import com.protops.gateway.constants.Constants;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 使用Jaskson的新API：Streaming API<br>
 * http://wiki.fasterxml.com/JacksonInFiveMinutes<br>
 * http://wiki.fasterxml.com/JacksonStreamingApi
 */
public class JsonUtil {

	private static final Logger logger = Logger.getLogger(JsonUtil.class);

	private static final ObjectMapper mapper = new ObjectMapper();
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String EMPTY_JSON = "{}";
	public static final byte[] EMPTY_JSON_BYTES = new byte[] { '{', '}' };

	static {
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
		mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat(DATE_FORMAT));
	}

	public static <T> T fromJson(String json, Class<T> t) {
		if (json == null) {
			return null;
		}
		try {
			return mapper.readValue(json, t);
		} catch (Exception e) {
			logger.info("Cannot parse json to Object. Json: <" + json + ">, Object class: <" + t.getName() + ">.", e);
		}
		return null;
	}

	public static <T> T fromJson(byte[] json, Class<T> t) {
		if (json == null) {
			return null;
		}
		try {
			return mapper.readValue(json, t);
		} catch (Exception e) {
			logger.info("Cannot parse json to Object. Json: <" + json + ">, Object class: <" + t.getName() + ">.", e);
		}
		return null;
	}

	public static <T> T fromJsonWithException(String json, Class<T> t) {
		try {
			return mapper.readValue(json, t);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromMap(Map<?, ?> map, Class<T> t) {

		if (map == null) {
			return null;
		}
		try {
			return mapper.readValue(toJson(map), t);
		} catch (Exception e) {
			logger.info("Cannot parse map to Object. Map: <" + map + ">, Object class: <" + t.getName() + ">.", e);
		}
		return null;
	}

	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.warn(e);
		}
		return EMPTY_JSON;
	}

	public static byte[] toJsonBytes(Object obj) {
		try {
			return mapper.writeValueAsBytes(obj);
		} catch (Exception e) {
			logger.warn(e);
		}
		return EMPTY_JSON_BYTES;
	}

	
    @SuppressWarnings("unchecked")
    public static <T> T fromJsonWithCustomObject(String json, TypeReference<T> typeReference) {
		if (json == null) {
			return null;
		}
		try {
			return (T) mapper.readValue(json, typeReference);
		} catch (Exception e) {
			logger.info("Cannot parse json string to Object. Json: <" + json + ">,", e);
		}
		return null;
	}

	public static <T> T fromJsonWithCustomObject(byte[] json, TypeReference<T> typeReference) {
		if (json == null) {
			return null;
		}
		try {
			return (T) mapper.readValue(json, typeReference);
		} catch (Exception e) {
			logger.info("Cannot parse json string to Object. Json: <" + json + ">,", e);
		}
		return null;
	}
}

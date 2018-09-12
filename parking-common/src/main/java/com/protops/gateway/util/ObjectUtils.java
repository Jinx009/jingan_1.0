package com.protops.gateway.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;

public final class ObjectUtils extends org.apache.commons.lang.ObjectUtils {

	/**
	 * check if the src object is in the given parameters, using equals compare.
	 * 
	 */
	public static boolean in(Object src, Object... objects) {
		if (objects == null || objects.length == 0) {
			return false;
		}
		for (Object obj : objects) {
			if (equals(src, obj)) {
				return true;
			}
		}
		return false;
	}

	public static String serialize(Serializable object) {
		byte[] bytes = SerializationUtils.serialize(object);
		return Base64.encodeBase64String(bytes);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialize(String str) {
		return (str == null) ? null : (T) SerializationUtils.deserialize(Base64.decodeBase64(str));
	}
}

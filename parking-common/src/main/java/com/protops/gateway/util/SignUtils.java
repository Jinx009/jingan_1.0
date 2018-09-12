package com.protops.gateway.util;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SignUtils {

	private static final String DEFAULT_ENCODING = "utf-8";

	private static final String DEFAULT_SIGN_KEY = "TheAvengers";

	public static String sign(String valueToSign){
		return md5(valueToSign);
	}


	public static boolean verify(String valueToSign, String dest) {

		String src = sign(valueToSign);
		
		if (StringUtils.equals(src, dest)) {

			return true;

		}

		return false;

	}

	public static String md5(String str) {

		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(DEFAULT_ENCODING));
		} catch (NoSuchAlgorithmException e) {

			return str;
		} catch (UnsupportedEncodingException e) {
			return str;
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
}

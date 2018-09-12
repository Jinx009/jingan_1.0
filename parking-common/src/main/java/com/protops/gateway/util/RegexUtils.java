package com.protops.gateway.util;

import com.protops.gateway.constants.RegexConstants;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexUtils {

	public static final String REGEX_CHINESE_CHARS = "\u4e00-\u9fa5（）：";
	public static final String REGEX_CELLPHONE = "1[34578]\\d{9}";
	public static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	public static final String REGEX_CARD_NUMBER = "\\d{13,19}";
	public static final int REGEX_HK_PHONENUMBER_LENGTH = 8;
	public static final String REGEX_FOREIGN_PHONE_NUMBER = "\\d{8,15}";
	private static final Pattern PATTERN_URL = getCaseInsensitivePattern("^http(s)?:\\/\\/(([\\w-]+(\\.[\\w-]+)+)|localhost)(:\\d+)?(\\/[\\w-?%=+&\\.:!]*)*(#\\w+)?$");
	private static final Pattern PATTERN_PARTIAL_URL = getCaseInsensitivePattern("^(\\/[\\w-?%=+&\\.:!]*)*(#\\w+)?$");
	private static final Pattern PATTERN_CELLPHONE = getCaseInsensitivePattern("^" + REGEX_CELLPHONE + "$");
	private static final Pattern PATTERN_FOREIGN_PHONE = getCaseInsensitivePattern("^" + REGEX_FOREIGN_PHONE_NUMBER + "$");
	private static final Pattern PATTERN_EMAIL = getCaseInsensitivePattern("^" + REGEX_EMAIL + "$");
	private static final Pattern PATTERN_CARD_NUMBER = getCaseInsensitivePattern("^" + REGEX_CARD_NUMBER + "$");
	private static final Pattern PATTERN_CERT_NUMBER = getCaseInsensitivePattern("^[^!$%\\^&*?<>]{5,18}$");

	public static Pattern getCaseSensitivePattern(String regex) {
		return Pattern.compile(regex);
	}

	public static Pattern getCaseInsensitivePattern(String regex) {
		return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	}

	public static boolean isValidUrl(String url) {
		return url == null ? false : PATTERN_URL.matcher(url).matches();
	}

	public static boolean isValidPartialUrl(String partialUrl) {
		return partialUrl == null ? false : PATTERN_PARTIAL_URL.matcher(partialUrl).matches();
	}

	public static boolean isValidPhoneNumber(String phoneNumber) {
		return phoneNumber == null ? false : PATTERN_CELLPHONE.matcher(phoneNumber).matches();
	}

	public static boolean isValidForeignPhoneNumber(String phoneNumber) {
		return phoneNumber == null ? false : PATTERN_FOREIGN_PHONE.matcher(phoneNumber).matches();
	}
	
	public static boolean isValidEmail(String email) {
		return email == null ? false : PATTERN_EMAIL.matcher(email).matches();
	}

	public static boolean isValidCardNumber(String cardNumber) {
		return cardNumber == null ? false : PATTERN_CARD_NUMBER.matcher(cardNumber).matches();
	}
	
	public static boolean isValid(String cardNumber, String regex) {
		Pattern pattern = getCaseInsensitivePattern(regex);
		return cardNumber == null ? false : pattern.matcher(cardNumber).matches();
	}
	
	/** 是否是除身份证外的其他证件 */
	public static boolean isValidCertNumber(String certNumber) {
		return certNumber == null ? false : PATTERN_CERT_NUMBER.matcher(certNumber).matches();
	}

	public static boolean verifyRealName(String value) {

		try {

			if (value.getBytes("GBK").length > 30) {
				return false;
			}

		} catch (UnsupportedEncodingException e) {
			// ignored
		}

		return verify(value, RegexConstants.REAL_NAME_PATTERN);
	}

	public static boolean verify(String value, Pattern pattern) {

		if (org.springframework.util.StringUtils.isEmpty(value)) {
			return false;
		}

		Matcher cp = pattern.matcher(value);

		return cp.matches();
	}
}

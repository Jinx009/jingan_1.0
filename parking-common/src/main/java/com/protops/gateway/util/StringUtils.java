package com.protops.gateway.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utility.
 * 
 * @author Hubert Yang
 * 
 */
public final class StringUtils extends org.apache.commons.lang.StringUtils {

	static final String DEFAULT_ENCODING = "UTF-8";
	private static final String UPOP_SERVER_ENCODING = "GBK";
	private static final Logger log = LoggerFactory.getLogger(StringUtils.class);
	private static final Pattern md5Pattern = RegexUtils.getCaseInsensitivePattern("^[a-f0-9]{32}$");
	private static final Pattern base64Pattern = RegexUtils
			.getCaseInsensitivePattern("^(?:[a-z0-9+/]{4})*(?:[a-z0-9+/]{2}==|[a-z0-9+/]{3}=)?$");
	private static final DecimalFormat DEFAULT_DECIMAL_FORMATTER = new DecimalFormat("#.##");

	public static final char DEFAULT_JOINER = '&';
	public static final String AREA_CODE_CHINA = "86";

	private StringUtils() {
	}

	public static String encryptPassword(String password, String loginName) {
		return md5(md5(password) + loginName);
	}

	public static String encryptPasswordWithSeed(String password, String loginName, String seed) {
		return md5(encryptPassword(password, loginName) + seed);
	}

	public static String encryptLoginPasswordWithSeed(String securePassword, String seed) {
		return md5(securePassword + seed);
	}

	/**
	 * get the md5 hash of a string
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {

		return md5(str, DEFAULT_ENCODING);
	}
	
	public static byte[] toMessageDigestByte(String str, String encoding, String algorithm){
		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(algorithm.toUpperCase());
			messageDigest.reset();
			messageDigest.update(str.getBytes(encoding));
		} catch (NoSuchAlgorithmException e) {
			if (log.isErrorEnabled()) {
				log.error("sha1 algorithm not supported", e);
			}
			return null;
		} catch (UnsupportedEncodingException e) {
			if (log.isErrorEnabled()) {
				log.error(formatMessage("Encoding \"{0}\" not supported for SHA-1 algorithm.", DEFAULT_ENCODING), e);
			}
			return null;
		}

		byte[] byteArray = messageDigest.digest();

		return byteArray;
	}

    public static byte[] toMessageDigestByte(byte [] bytes, String algorithm){
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance(algorithm.toUpperCase());
            messageDigest.reset();
            messageDigest.update(bytes);
        } catch (NoSuchAlgorithmException e) {
            if (log.isErrorEnabled()) {
                log.error("sha1 algorithm not supported", e);
            }
            return null;
        }

        byte[] byteArray = messageDigest.digest();

        return byteArray;
    }
	
	public static String sha1(String str, String encoding){
		
		byte[] byteArray = toMessageDigestByte(str, encoding, "SHA-1");

		return toHexString(byteArray);
	}
	
	public static String toHexString(byte[] byteArray){
		StringBuffer sha1StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				sha1StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				sha1StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return sha1StrBuff.toString();
	}

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static String signMD5(String signData, String secretKey, String encoding) {
        String key = StringUtils.defaultIfEmpty(secretKey, "");
        String s = signData + DEFAULT_JOINER + StringUtils.md5(key, encoding);

        String sig =  StringUtils.md5(s, encoding);

        if (log.isDebugEnabled()) {
            log.debug("md5 sign data: " + s);
            log.debug("sign value: " + sig);
        }

        return sig;
    }

	public static String md5(String str, String encoding) {

		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(encoding));
		} catch (NoSuchAlgorithmException e) {
			if (log.isErrorEnabled()) {
				log.error("MD5 algorithm not supported", e);
			}
			return str;
		} catch (UnsupportedEncodingException e) {
			if (log.isErrorEnabled()) {
				log.error(formatMessage("Encoding \"{0}\" not supported for MD5 algorithm.", DEFAULT_ENCODING), e);
			}
			return str;
		}

		byte[] byteArray = messageDigest.digest();

		return toHexString(byteArray);
	}

	/**
	 * detect if the given string represents a md5 hash
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMd5String(String str) {
		return isNotBlank(str) && md5Pattern.matcher(str).matches();
	}

	public static boolean isBase64String(String str) {
		// some base 64 implementations may have \r\n as line separator.
		return isNotBlank(str) && base64Pattern.matcher(str.replace("\n", "").replace("\r", "")).matches();
	}

	/**
	 * Get an escaped string for regular expression to use
	 * 
	 * @param input
	 *            the input string
	 * @return escaped pattern
	 */
	public static String escapeForRegex(String input) {
		if (isBlank(input)) {
			return input;
		}
		input = input.replaceAll("\\\\", "\\\\\\\\");
		input = input.replaceAll("\\(", "\\\\(");
		input = input.replaceAll("\\)", "\\\\)");
		input = input.replaceAll("\\[", "\\\\[");
		input = input.replaceAll("\\]", "\\\\]");
		input = input.replaceAll("\\{", "\\\\{");
		input = input.replaceAll("\\}", "\\\\}");
		input = input.replaceAll("\\|", "\\\\|");
		input = input.replaceAll("\\^", "\\\\^");
		input = input.replaceAll("\\$", "\\\\\\$");
		input = input.replaceAll("\\.", "\\\\.");
		input = input.replaceAll("\\?", "\\\\?");
		input = input.replaceAll("\\+", "\\\\+");
		input = input.replaceAll("\\*", "\\\\*");
		return input;
	}

	public static String formatDouble(double d) {
		return DEFAULT_DECIMAL_FORMATTER.format(d);
	}

	/**
	 * list all the enum values as a comma joined string, with value quoted.
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> String listEnumAsQuotedString(Class<T> clazz) {
		T[] enums = clazz.getEnumConstants();
		List<String> quotedValues = new ArrayList<String>();
		for (T e : enums) {
			quotedValues.add("\"" + e.toString() + "\"");
		}
		return join(quotedValues, ",");
	}

	public static String join(int[] array, String separator) {
		if (array == null || array.length == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer(array[0]);
		for (int i = 1; i < array.length; i++) {
			buffer.append(separator);
			buffer.append(array[i]);
		}
		return buffer.toString();
	}

	public static String formatMessage(String msg, Object... args) {
		if (isBlank(msg) || ArrayUtils.getLength(args) == 0) {
			return msg;
		} else {
			return MessageFormat.format(msg, args);
		}
	}

	/**
	 * Take the fixed length of chars from the given string, right pad if not enough length, or full pad if null.
	 * 
	 * @param str
	 * @param length
	 * @param padChar
	 * @return
	 */
	public static String toFixLenStr(String str, int length, char padChar) {
		if (length < 0) {
			throw new RuntimeException("Length can not be less than zero.");
		}
		char[] result = new char[length];
		if (str == null) {
			for (int i = 0; i < length; ++i) {
				result[i] = padChar;
			}
		} else {
			char[] tmp = str.toCharArray();
			int copyLen = Math.min(length, tmp.length);
			System.arraycopy(tmp, 0, result, 0, copyLen);
			if (copyLen < length) {
				for (int i = copyLen; i < length; ++i) {
					result[i] = padChar;
				}
			}
		}
		return new String(result);
	}

	public static String substr(String str, int startIndex) {
		if (isEmpty(str)) {
			return str;
		} else if (startIndex >= 0) {
			return substring(str, startIndex);
		} else {
			int len = -startIndex;
			if (str.length() <= len) {
				return str;
			} else {
				return substring(str, str.length() - len);
			}
		}
	}

	/**
	 * <p>
	 * Get the substring using the specified charset.
	 * </p>
	 * <p>
	 * Length is calculated by bytes. If the result substring does not stand for a valid string in the given charset, it will be
	 * cut off (drop the leading and/or trailing bytes if necessary).
	 * </p>
	 * <p>
	 * i.e.:
	 * <ul>
	 * <li>substrByBytes("ab中文c", 0, 3, "UTF-8") = "ab"</li>
	 * <li>substrByBytes("ab中文c", 0, 4, "GBK") = "ab中"</li>
	 * <li>substrByBytes("ab中文c", 1, 5, "UTF-8") = "ab中"</li>
	 * <li>substrByBytes("ab中文c", 1, 5, "GBK") = "ab中文"</li>
	 * <li>substrByBytes("ab中文c", 100, 1, "UTF-8") = ""</li>
	 * <li>substrByBytes("中文abc", 1, 4, "UTF-8") = ""</li>
	 * <li>substrByBytes("中文abc", 1, 6, "UTF-8") = "中"</li>
	 * </ul>
	 * </p>
	 * 
	 * @param str
	 *            the original string
	 * @param startIndex
	 *            start index (zero-based)
	 * @param len
	 *            length of bytes to retrieve
	 * @param charset
	 *            the charset
	 * @return
	 */
	public static String substrByBytes(String str, int startIndex, int len, String charset) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();

		try {
			int str_len = str.length();
			startIndex = startIndex < 0 ? 0 : startIndex;
			if (startIndex > 0) {
				StringBuffer temp = new StringBuffer();
				for (int j = 0; j < str_len; j++) {
					temp.append(str.charAt(j));
					if (temp.toString().getBytes(charset).length >= startIndex) {
						startIndex = j + 1;
						len = len - (temp.toString().getBytes(charset).length - startIndex);
						break;
					}
				}
			}

			for (int i = startIndex; i < str_len; i++) {
				char tmp_c = str.charAt(i);
				sb.append(tmp_c);
				if (sb.toString().getBytes(charset).length > len) {
					sb.deleteCharAt(i - startIndex);
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			log.warn("UnsupportedEncodingException", e);
		}
		return "";
	}

	public static String maskCardNumber(String cardNumber) {
		return addMask(cardNumber, 4, 4, 4);
	}

	public static String maskPhoneNumber(String phoneNumber) {
		return addMask(phoneNumber, 3, 3, 5);
	}

	public static String maskPhoneNumberWithAreaCode(String phoneNumber, String areaCode) {
		if (isEmpty(areaCode)) {
			return addMask(phoneNumber, 3, 3, 5);
		} else {
			return areaCode + "-" + addMask(phoneNumber, 0, 2, 9);
		}
	}

	public static String addMask(String str, int lenBeforeMask, int lenAfterMask, int maxMaskLength) {
		if (isEmpty(str)) {
			return str;
		}
		int len = str.length();
		if (len <= lenBeforeMask + lenAfterMask) {
			return str;
		}
		int x = Math.min(maxMaskLength, len - lenBeforeMask - lenAfterMask);
		return substring(str, 0, lenBeforeMask) + repeat("*", x) + substring(str, len - lenAfterMask);
	}

	public static int getLength4GBKCharset(String str) {
		int len = 0;
		if (StringUtils.isNotEmpty(str)) {
			try {
				len = str.getBytes(UPOP_SERVER_ENCODING).length;
			} catch (UnsupportedEncodingException e) {
				if (log.isErrorEnabled()) {
					log.error(formatMessage("Encoding \"{0}\" not supported.", UPOP_SERVER_ENCODING), e);
				}
				len = str.length();
			}
		}
		return len;
	}

	public static String joinMapValue(Map<String, String> map, char connector, boolean appendKey4EmptyValue) {
		StringBuffer b = new StringBuffer();
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (StringUtils.isEmpty(entry.getValue())) {
					if (appendKey4EmptyValue) {
						b.append(entry.getKey());
						b.append('=');
						b.append(connector);
					}
				} else {
					b.append(entry.getKey());
					b.append('=');
					b.append(entry.getValue());
					b.append(connector);
				}
			}
			if (b.length() > 0) {
				b.deleteCharAt(b.length() - 1);
			}
		}
		return b.toString();
	}

    public static String joinMapOjbectValue(Map<String, Object> map, char connector, boolean appendKey4EmptyValue) {
        StringBuffer b = new StringBuffer();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry == null) {
                    if (appendKey4EmptyValue) {
                        b.append(entry.getKey().toString());
                        b.append('=');
                        b.append(connector);
                    }
                } else {
                    b.append(entry.getKey());
                    b.append('=');
                    b.append(entry.getValue());
                    b.append(connector);
                }
            }
            if (b.length() > 0) {
                b.deleteCharAt(b.length() - 1);
            }
        }
        return b.toString();
    }

	public static String joinMapURLEncoderValue(Map<String, String> map, char connector, String encoding) {
		return joinMapURLEncoderValue(map, connector, true, encoding);
	}

	public static String joinMapURLEncoderValue(Map<String, String> map, char connector, boolean appendKey4EmptyValue,
			String encoding) {
		StringBuilder b = new StringBuilder();
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				try {
					if (entry.getValue() == null) {
						if (appendKey4EmptyValue) {
							b.append(URLEncoder.encode(entry.getKey(), encoding));
							b.append('=');
							b.append(connector);
						}
					} else {
						b.append(URLEncoder.encode(entry.getKey(), encoding));
						b.append('=');
						b.append(URLEncoder.encode(entry.getValue(), encoding));
						b.append(connector);
					}
				} catch (UnsupportedEncodingException e) {
					log.warn("UnsupportedEncodingException", e);
				}
			}
			b.deleteCharAt(b.length() - 1);
		}
		return b.toString();
	}

	public static String joinMapValue(Map<String, String> map, char connector) {
		return joinMapValue(map, connector, true);
	}

	public static String joinMapValue(Map<String, String> map) {
		return joinMapValue(map, DEFAULT_JOINER);
	}

    /**
     * Splits map string.
     * E.g., mapString is "a=1;b=2", delimeter is ";"
     * Result is: Map: {key=a, value=1, key=b, value=2} 
     * 
     * @param mapString
     * @return
     */
    public static Map<String, String> splitMapString(String mapString, String delimeter, String encoding) {
        String[] nameValuePairs = mapString.split(Pattern.quote(delimeter));
        Map<String, String> orderInfo = new TreeMap<String, String>();
        for (String nameValuePair : nameValuePairs) {
            String[] elements = nameValuePair.split("=");
            if (elements.length < 2) {
                // no value, put empty
                orderInfo.put(elements[0], "");
            } else {
                try {
                    orderInfo.put(elements[0], URLDecoder.decode(elements[1], encoding));
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage(), e);
                    orderInfo.put(elements[0], elements[1]);
                }
            }
        }
        return orderInfo;
    }
	
    public static Map<String, String> splitQueryString(String queryString) {
        return splitMapString(queryString, "&", DEFAULT_ENCODING);
    }
    public static Map<String, String> splitQueryString(String queryString, String encoding) {
    	return splitMapString(queryString, "&", encoding);
    }
    
	public static String deleteLastOccurrence(String origString, String str4Delete) {
		if (isEmpty(origString)) {
			return "";
		}
		if (isEmpty(str4Delete)) {
			return origString;
		}
		StringBuilder result = new StringBuilder(origString);
		int lastIndex = origString.lastIndexOf(str4Delete);
		if (lastIndex != -1) {
			result.delete(lastIndex, lastIndex + str4Delete.length());
		}
		return result.toString();
	}

	public static String blur(String source) {
		return blur(source, DEFAULT_ENCODING);
	}

	public static String deblur(String bluredString) {
		return deblur(bluredString, DEFAULT_ENCODING);
	}

	public static String blur(String source, String encoding) {
	    if (isBlank(source)) {
	        return source;
	    } else {
	        String reversed = new StringBuilder(source).reverse().toString();
	        String base64String = "";
	        try {
	            base64String = Base64.encodeBase64String(reversed.getBytes(encoding));
	        } catch (UnsupportedEncodingException e) {
	            log.error(formatMessage("Cannot blur string.  source={0}", source), e);
	        }
	        int indexOfEqualSign = base64String.indexOf("=");
	        indexOfEqualSign = (indexOfEqualSign == -1) ? base64String.length() : indexOfEqualSign;
	        return new StringBuilder(base64String.substring(0, indexOfEqualSign)).reverse()
	                .append(base64String.substring(indexOfEqualSign)).toString();
	    }
	}

	public static String deblur(String bluredString, String encoding) {
	    if (isBlank(bluredString)) {
            return bluredString;
        } else {
            int indexOfEqualSign = bluredString.indexOf("=");
            indexOfEqualSign = (indexOfEqualSign == -1) ? bluredString.length() : indexOfEqualSign;
            String base64String = new StringBuilder(bluredString.substring(0, indexOfEqualSign)).reverse()
                    .append(bluredString.substring(indexOfEqualSign)).toString();

            String reversed = "";
            try {
                reversed = new String(Base64.decodeBase64(base64String), encoding);
            } catch (UnsupportedEncodingException e) {
                log.error(formatMessage("Cannot deblur string.  base64String={0}", base64String), e);
            }

            String source = new StringBuilder(reversed).reverse().toString();
            return source;
        }
	}
	
	/**
	 * A compensation of Integer.toBinaryString(). 
	 * E.g, 
	 * toBinaryString(1, 3) = 001
	 * toBinaryString(2, 4) = 0010
	 * toBinaryString(8, 2) = 100
	 * 
	 * @param value
	 * @param bits
	 * @return
	 */
	public static String toBinaryString(int value, int bits) {
	    String binaryString = Integer.toBinaryString(value);
	    return StringUtils.leftPad(binaryString, bits, "0");
	}
	
	public static String subString(byte[] itemBytes, int begin, int size, String charset) throws UnsupportedEncodingException{
		byte[] array = new byte[size];
		System.arraycopy(itemBytes, begin, array, 0, size);
		String str = new String(array, charset);
		return StringUtils.trim(str);
	}
	
	/**
	 * to transform C return fix number , like 000001201
	 * 
	 * <li>trimFixedNumber(000001201) = 1201 </li>
	 * <li>trimFixedNumber(09) = 9</li>
	 * @param number
	 */
	public static String trimFixedNumber(String number){
		
		String regex = "0+([1-9][0-9]*)";
		String regex4AllZero = "^0+$"; //ex: 000000
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(number);
		if(matcher.matches()){
			return matcher.group(1);
		}else if(number.matches(regex4AllZero)){
			return "0";
		}else{
			return number;
		}
	}
	
	public static String concat(Object... pieces) {
		StringBuilder sb = new StringBuilder();
		for (Object piece : pieces) {
			sb.append(piece);
		}
		return sb.toString();
	}
	
	
	public static void main(String args[]){
		/**
			String str ="charset=UTF-8&cupReserved={cardNumber=6227563200000001&exchangeDate=&exchangeRate=&merAbbr=&merId=&orderAmount=&orderCurrency=&orderNumber=&qid=&respCode=00&respMsg=Success!&respTime=&settleAmount=&settleCurrency=&settleDate=&traceNumber=&traceTime=&transType=&version=1.0.0";
			String str2 = str+"&"+StringUtils.md5("88888888");
			String sig = StringUtils.md5(str2);
			
			System.out.println(str2);
			System.out.println(sig);
		**/
		System.out.println(substr("0123456789", -4));
//		String number = "01002331";
//		System.out.println(trimFixedNumber(number));
		
	}

	/**
	 * 填充字符
	 *
	 * @param value
	 * @param len
	 * @param fillValue
	 * @return
	 */
	public static String beforFillValue(String value, int len, char fillValue) {
		String str = (value == null) ? "" : value.trim();
		StringBuffer result = new StringBuffer();
		int paramLen = str.length();
		if (paramLen < len) {
			for (int i = 0; i < len - paramLen; i++) {
				result.append(fillValue);
			}
		}
		result.append(str);
		return result.toString();
	}

	public static List<Integer> toIntegerList(String stringArrayWithComma) {
		List<Integer> list = new ArrayList<Integer>();
		String[] rulesIdArray = stringArrayWithComma.split(",");
		for (String id : rulesIdArray) {
			list.add(Integer.parseInt(id));
		}
		return list;
	}
}

package com.protops.gateway.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class UpeditorUtils {

	private static String DES = "DES";
	private static String DESECBNOPADDING = "DES/ECB/PKCS5Padding";

	public static byte[] des(String algm, byte[] key, byte[] src, int mode)
			throws Exception {
		Cipher c = Cipher.getInstance(algm);
		SecretKeySpec sks = new SecretKeySpec(key, 0, key.length, DES);
		c.init(mode, sks);
		return c.doFinal(src);
	}

	public static String decryptCipher(String cipher, String key)
			throws Exception {

		byte[] src = Base64.decodeBase64(cipher.getBytes("UTF-8"));
		byte[] output = des(DESECBNOPADDING, key.getBytes("UTF-8"), src,
				Cipher.DECRYPT_MODE);
		return new String(output);
	}

    public static String encryptCipher(String src, String key)throws Exception {
        byte[] srcBytes = src.getBytes("UTF-8");
        byte[] output = des(DESECBNOPADDING, key.getBytes("UTF-8"), srcBytes, Cipher.ENCRYPT_MODE);
        return Base64.encodeBase64String(output);
    }

	/**
	 * SecureKey 生成规则: <code>secureKey</code>(01234567), <code>cardNumber</code>
	 * (612345123456789)
	 * <p>
	 * STEP1: <code>cardNumber</code>从末尾第二位截取<code>secureKey</code>
	 * 长度的位数(12345678)
	 * </p>
	 * <p>
	 * STEP2: 将STEP1的<code>secureKey</code>(01234567)和截取的<code>cardNumber</code>
	 * (12345678)分别奇偶位排序-> <code>secureKey</code> (13570246),
	 * <code>cardNumber</code>(13572468)
	 * </p>
	 * <p>
	 * STEP3: 将STEP2的<code>secureKey</code>(13570246),<code>cardNumber</code>
	 * (13572468)按位串联 -> <code>newKey</code> (1133557702244668)
	 * </p>
	 * <p>
	 * STEP4: 将STEP3的<code>newKey</code>截取8位:11335577
	 * </p>
	 * 
	 * @param secureKey
	 * @param cardNumber
	 * @return newSecureKey
	 */
	public static String composeUpeNewSecureKey(String secureKey,
			String cardNumber) {
		if (StringUtils.isEmpty(secureKey) || StringUtils.isEmpty(cardNumber)) {
			throw new RuntimeException("Exception while encryptUpeSecureKey");
		}
		int secureKeyLength = secureKey.getBytes().length;
		int cardNumberLength = cardNumber.getBytes().length;
		int beginIndex = cardNumberLength - secureKeyLength - 1;
		String cutCardNumber = null;
		if (0 < beginIndex) {
			cutCardNumber = cardNumber.substring(beginIndex,
					cardNumberLength - 1);
		} else {
			StringBuffer sf = new StringBuffer();
			int le = secureKeyLength - cardNumberLength + 1;
			for (int i = 0; i < le; i++) {
				sf.append("0");
			}
			sf.append(cardNumber.substring(0, cardNumberLength - 1));
			cutCardNumber = sf.toString();
		}
		String resultStr = jointKeyAndCardNo(oddEvenSort(secureKey),
				oddEvenSort(cutCardNumber));
		if (!StringUtils.isEmpty(resultStr)
				&& StringUtils.length(resultStr) > 8) {
			return resultStr.substring(0, 8);
		} else {
			throw new RuntimeException("Exception while encryptUpeSecureKey");
		}
	}

	/**
	 * SecureKey 生成规则: <code>secureKey</code>(01234567), <code>cardNumber</code>
	 * (612345123456789)
	 * <p>
	 * STEP1: <code>cardNumber</code>从末尾第二位截取<code>secureKey</code>
	 * 长度的位数(12345678)
	 * </p>
	 * <p>
	 * STEP2: 将STEP1的<code>secureKey</code>(01234567)和截取的<code>cardNumber</code>
	 * (12345678)分别奇偶位排序-><code>secureKey</code> (13570246),
	 * <code>cardNumber</code>(13572468)
	 * </p>
	 * <p>
	 * STEP3: 将STEP2的<code>secureKey</code>(13570246),<code>cardNumber</code>
	 * (13572468)按位串联 -><code>newKey</code> (1133557702244668)
	 * </p>
	 * <p>
	 * STEP4: 将STEP3的<code>newKey</code>截取8位:11335577
	 * </p>
	 * 
	 * @param secureKey
	 * @param cardNumber
	 * @return newSecureKey
	 */
	public static String composeUpeSecureKey(String secureKey, String cardNumber) {
		if (StringUtils.isEmpty(secureKey) || StringUtils.isEmpty(cardNumber)) {
			throw new RuntimeException("Exception while encryptUpeSecureKey");
		}
		int secureKeyLength = secureKey.getBytes().length;
		int cardNumberLength = cardNumber.getBytes().length;
		int beginIndex = cardNumberLength - secureKeyLength - 1;
		String cutCardNumber = cardNumber.substring(beginIndex,
				cardNumberLength - 1);
		String resultStr = jointKeyAndCardNo(oddEvenSort(secureKey),
				oddEvenSort(cutCardNumber));
		if (StringUtils.isNotEmpty(resultStr)
				&& StringUtils.length(resultStr) > 8) {
			return resultStr.substring(0, 8);
		} else {
			throw new RuntimeException("Exception while encryptUpeSecureKey");
		}
	}

	public static String oddEvenSort(String src) {
		char[] chSrc = src.toCharArray();
		StringBuffer odd = new StringBuffer();
		StringBuilder even = new StringBuilder();
		int len = chSrc.length;
		int index = 0;
		for (; index < len; index++) {
			if (index % 2 == 0) {
				odd.append(chSrc[index]);
			} else {
				even.append(chSrc[index]);
			}
		}
		odd.append(even);
		return odd.toString();
	}

	public static String jointKeyAndCardNo(String sortedSecureKey,
			String sortedCardNo) {
		StringBuffer jointSb = new StringBuffer();
		int sortedSkLen = sortedSecureKey.length();
		int sortedCnLen = sortedCardNo.length();

		if (sortedSkLen != sortedCnLen) {
			return null;
		}

		for (int i = 0; i < sortedSkLen; i++) {
			jointSb.append(sortedSecureKey.charAt(i));
			jointSb.append(sortedCardNo.charAt(i));
		}
		return jointSb.toString();
	}
}

package com.protops.gateway.util;


import com.protops.gateway.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Description: 字符串工具类 String Utilities.
 * <p/>
 * (C) Copyright of China UnionPay Co., Ltd. 2010.
 */
public class StringUtility {

    private static Logger logger = Logger.getLogger(StringUtility.class);
    /**
     * 匹配中文
     */
    private static final String zh_regEx = "^[\u4e00-\u9fa5]+$";
    /**
     * 匹配中文
     */
    private static final String cn_regEx = "^[\u4e00-\u9fa5]+$";
    /**
     * 匹配URL
     */
    private static final String url_regEx = "^(http://){0,1}.+\\..+\\..+$";
    /**
     * 匹配手机号码
     */
    private static final String mobile_regEx = "^13\\d{9}$";
    /**
     * 匹配邮箱
     */
    private static final String email_regEx = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    /**
     * 匹配数字、字母
     */
    private static final String num_word_regEx = "^[A-Za-z0-9]+$";
    /**
     * 匹配数字
     */
    private static final String num_regEx = "^[0-9]+$";
    /**
     * 匹配数字、减号
     */
    private static final String num_minus_regEx = "^[0-9\\-]+$";
    /**
     * 匹配字母
     */
    private static final String word_regEx = "^[A-Za-z]+$";

    private static Pattern cn_pattern;
    private static Pattern url_pattern;
    private static Pattern mobile_pattern;
    private static Pattern email_pattern;
    private static Pattern num_word_pattern;
    private static Pattern num_pattern;
    private static Pattern num_minus_pattern;
    private static Pattern word_pattern;

    /**
     * 静态代码框，初始化
     */
    static {
        cn_pattern = Pattern.compile(cn_regEx);
        url_pattern = Pattern.compile(url_regEx);
        mobile_pattern = Pattern.compile(mobile_regEx);
        email_pattern = Pattern.compile(email_regEx);
        num_word_pattern = Pattern.compile(num_word_regEx);
        num_pattern = Pattern.compile(num_regEx);
        num_minus_pattern = Pattern.compile(num_minus_regEx);
        word_pattern = Pattern.compile(word_regEx);
    }
//	/**
//	 * 测试方法
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		//System.out.println(checkNumOrWord("ssssdfsdf 11231 ssdf"));
//		System.out.println("a b c".replaceAll("\\s+", ""));
//		System.out.println(checkNumOrWord("ssssdfsdf 11231 ssdf"));
//		
//		String amount = "22.34   ";
//		System.out.println("\"" + amount + "\"" + (isAmount(amount) ? "是金额" : "不是金额"));
//		amount = "22.3";
//		System.out.println("\"" + amount + "\"" + (isAmount(amount) ? "是金额" : "不是金额"));
//		amount = "22";
//		System.out.println("\"" + amount + "\"" + (isAmount(amount) ? "是金额" : "不是金额"));
//		amount = "11.222";
//		System.out.println("\"" + amount + "\"" + (isAmount(amount) ? "是金额" : "不是金额"));
//		amount = "0.0";
//		System.out.println("\"" + amount + "\"" + (isAmount(amount) ? "是金额" : "不是金额"));
//		amount = "0.00";
//		System.out.println("\"" + amount + "\"" + (isAmount(amount) ? "是金额" : "不是金额"));
//	}

    /**
     * 检查是否是中文
     *
     * @param s
     * @return
     */
    public static boolean checkCn(String s) {
        return cn_pattern.matcher(s).matches();
    }

    /**
     * 检查URL格式
     *
     * @param s
     * @return
     */
    public static boolean checkUrl(String s) {
        return url_pattern.matcher(s).matches();
    }

    /**
     * 检查手机号码格式
     *
     * @param s
     * @return
     */
    public static boolean checkMobile(String s) {
        return mobile_pattern.matcher(s).matches();
    }

    public static boolean checkEmail2(String s) {
        return email_pattern.matcher(s).matches();
    }

    /**
     * 检查是否是数字后者字母
     *
     * @param s
     * @return
     */
    public static boolean checkNumOrWord(String s) {
        return num_word_pattern.matcher(s.replaceAll("\\s+", "")).matches();
    }

    /**
     * 检查是否是数字
     *
     * @param s
     * @return
     */
    public static boolean checkNum(String s) {
        return num_pattern.matcher(s).matches();
    }

    /**
     * 检查是否是字母
     *
     * @param s
     * @return
     */
    public static boolean checkWord(String s) {
        return word_pattern.matcher(s).matches();
    }

    public static boolean checkNumOrMinus(String s) {
        return num_minus_pattern.matcher(s).matches();
    }

    /**
     * 判断object是否为空
     *
     * @param object Object对象
     * @return 布尔值
     */
    public static boolean isNull(Object object) {
        if (object instanceof String) {
            return StringUtility.isEmpty(object.toString());
        }
        return object == null;
    }

    /**
     * Checks if string is null or empty.
     *
     * @param value The string to be checked
     * @return True if string is null or empty, otherwise false.
     */
    public static boolean isEmpty(final String value) {
        return value == null || value.trim().length() == 0;
    }

//	/**
//	 * Converts <code>null</code> to empty string, otherwise returns it directly.
//	 *
//	 * @param string
//	 *            The nullable string
//	 * @return empty string if passed in string is null, or original string without any change
//	 */
//	public static String null2String(Object obj) {
//		return obj == null ? "" : obj.toString();
//	}
//
//	public static String null2String(String str) {
//		return str == null ? "" : str;
//	}

    /**
     * 填充字符
     *
     * @param value
     * @param len
     * @param fillValue
     * @return
     */
    public static String fillValue(String value, int len, char fillValue) {
        String str = (value == null) ? "" : value.trim();
        StringBuffer result = new StringBuffer();
        result.append(str);
        int paramLen = str.length();
        if (paramLen < len) {
            for (int i = 0; i < len - paramLen; i++) {
                result.append(fillValue);
            }
        }
        return result.toString();
    }

//	/**
//	 * 在value后变插入count次appendValue
//	 *
//	 * @param value
//	 * @param count
//	 *            插入的次数
//	 * @param appendValue
//	 * @return
//	 */
//	public static String appendValue(String value, int count, String appendValue) {
//		if (count < 1) {
//			return value;
//		}
//		StringBuffer result = new StringBuffer();
//		result.append(value);
//		for (int i = 0; i < count; i++) {
//			result.append(appendValue);
//		}
//		return result.toString();
//	}

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

    /**
     * 格式化金额
     *
     * @param amount 金额
     * @return
     */
    public static String convertAmount(String amount) {
        String str = String.valueOf(Double.parseDouble(amount));
        int pos = str.indexOf(".");
        int len = str.length();
        if (len - pos < 3) {
            return str.substring(0, pos + 2) + "0";
        } else {
            return str.substring(0, pos + 3);
        }
    }

    /**
     * currency fomate
     *
     * @param currency
     * @return
     */
    public static String formatCurrency(String currency) {
        if ((null == currency) || "".equals(currency) || "null".equals(currency)) {
            return "";
        }

        NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        try {
            return usFormat.format(Double.parseDouble(currency));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 根据separator拆分text
     *
     * @param text      需拆分的字符串 String
     * @param separator 拆分表达式 String
     * @return String[]
     */
    public static String[] split(String text, String separator) {
        return split(text, separator, -1);
    }

    /**
     * Splits the provided text into a list, based on a given separator. The separator is not
     * included in the returned String array. The maximum number of splits to perfom can be
     * controlled. A null separator will cause parsing to be on whitespace.
     * <p/>
     * <p/>
     * This is useful for quickly splitting a string directly into an array of tokens, instead of an
     * enumeration of tokens (as <code>StringTokenizer</code> does).
     *
     * @param str       The string to parse.
     * @param separator Characters used as the delimiters. If <code>null</code>, splits on whitespace.
     * @param max       The maximum number of elements to include in the list. A zero or negative value
     *                  implies no limit.
     * @return an array of parsed Strings
     */
    public static String[] split(String str, String separator, int max) {
        StringTokenizer tok = null;
        if (separator == null) {
            // Null separator means we're using StringTokenizer's default
            // delimiter, which comprises all whitespace characters.
            tok = new StringTokenizer(str);
        } else {
            tok = new StringTokenizer(str, separator);
        }

        int listSize = tok.countTokens();
        if (max > 0 && listSize > max) {
            listSize = max;
        }

        String[] list = new String[listSize];
        int i = 0;
        int lastTokenBegin = 0;
        int lastTokenEnd = 0;
        while (tok.hasMoreTokens()) {
            if (max > 0 && i == listSize - 1) {
                String endToken = tok.nextToken();
                lastTokenBegin = str.indexOf(endToken, lastTokenEnd);
                list[i] = str.substring(lastTokenBegin);
                break;
            }
            list[i] = tok.nextToken();
            lastTokenBegin = str.indexOf(list[i], lastTokenEnd);
            lastTokenEnd = lastTokenBegin + list[i].length();
            i++;
        }
        return list;
    }

    /**
     * Replace all occurances of a string within another string.
     *
     * @param text text to search and replace in
     * @param repl String to search for
     * @param with String to replace with
     * @return the text with any replacements processed
     * @see #replace(String text, String repl, String with, int max)
     */
    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    /**
     * Replace a string with another string inside a larger string, for the first <code>max</code>
     * values of the search string. A <code>null</code> reference is passed to this method is a
     * no-op.
     *
     * @param text text to search and replace in
     * @param repl String to search for
     * @param with String to replace with
     * @param max  maximum number of values to replace, or <code>-1</code> if no maximum
     * @return the text with any replacements processed
     * @throws NullPointerException if repl is null
     */
    private static String replace(String text, String repl, String with, int max) {
        if (text == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(text.length());
        int start = 0;
        int end = text.indexOf(repl, start);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
            end = text.indexOf(repl, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static String first2Upper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * @param Object [] input: Object[]: String[] result={"TYHR0001","TYHR0002"} delim: "," output:
     *               "'TYHR0001','TYHR0002'"
     * @param Object []
     * @return String
     */

    public static String arrayToDelimitedString(Object[] arr, String delim) {

        if (arr == null || arr.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append('\'');
            sb.append(arr[i]);
            sb.append('\'');
        }
        return sb.toString();
    }

    /**
     * e.g: String[] result={"TYHR0001","TYHR0002"}; split=","; return: str="TYHR0001,TYHR0002";
     *
     * @param Object []
     * @param split
     * @return String
     */
    public static String arrayToStr(Object[] arr, char split) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(split);
            }
            sb.append(arr[i]);

        }
        return sb.toString();
    }

    /**
     * 将数组的每个元素后加入split，然后组成字符串返回
     *
     * @param arr   字符串数组
     * @param split 插入字符
     * @return
     */
    public static String arrayToStr(Object[] arr, String split) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(split);
            }
            sb.append(arr[i]);

        }
        return sb.toString();
    }

    /**
     * 获取本地时间
     *
     * @param style yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getcurrdate(String style) {
        Date currDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(style); // "yyyy-MM-dd
        // HH:mm:ss"
        String strdate = sdf.format(currDate);
        return strdate;
    }

    /**
     * 格式化日期 去掉时分秒 增加分隔符
     *
     * @param @param  date
     * @param @return
     * @return String    返回类型
     * @throws
     */
    public static String dateSub(String date) {
        String formatDate = date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);
        return formatDate;
    }

    /**
     * 格式化日期 去掉时分秒 增加分隔符
     * 小于8位的日期，直接返回原字符串
     *
     * @param date      日期字符串
     * @param splitChar 分隔符
     * @return
     */
    public static String dateSub2(String date, String splitChar) {
        if (date.length() < 8) {
            return date;
        }
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }

    /**
     * 返回16进制MD5字符串。
     *
     * @param str 需要计算MD5的字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(String raw, String charSet) {

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(raw.getBytes(charSet));

            byte[] bytes = messageDigest.digest();

            return byte2hex(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * 返回16进制MD5字符串。
     *
     * @param str 需要计算MD5的字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(byte[] raw) {

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(raw);

            byte[] bytes = messageDigest.digest();

            return byte2hex(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * 返回16进制MD5字符串。
     *
     * @param str 需要计算MD5的字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha1(byte[] raw) {

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            messageDigest.update(raw);
            byte[] bytes = messageDigest.digest();
            return byte2hex(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 得到字符串中某个字符出现的次数
     *
     * @param str
     * @param c
     * @return
     */
    public static int getCharCount(String str, char c) {

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                count++;
        }
        return count;
    }

    /**
     * 判断输入字符串是否都为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (isNull(str))
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断输入字符串是否都为字母
     *
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        if (isNull(str))
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断输入字符串是否为数字和字母的组合
     *
     * @param str
     * @return
     */
    public static boolean isLetterOrNumeric(String str) {
        if (isNull(str))
            return false;
        if (isContainsChinese(str))
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        Matcher matcher = Pattern.compile(zh_regEx).matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    /**
     * 字符串转字节数组 *
     */
    public static byte[] Str2Hex(String str) {
        char[] ch = str.toCharArray();
        byte[] b = new byte[ch.length / 2];
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == 0)
                break;
            if (ch[i] >= '0' && ch[i] <= '9') {
                ch[i] = (char) (ch[i] - '0');
            } else if (ch[i] >= 'A' && ch[i] <= 'F') {
                ch[i] = (char) (ch[i] - 'A' + 10);
            }
        }
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (((ch[2 * i] << 4) & 0xf0) + (ch[2 * i + 1] & 0x0f));
        }
        return b;
    }

    /**
     * @param b
     * @return
     */
    public static String Hex2Str(byte[] b) {
        StringBuffer d = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
            char lo = Character.forDigit(b[i] & 0x0F, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }
        return d.toString();
    }

    /**
     * 字节数组转字符串*
     */
    public static String Hex2Str(byte[] b, int len) {
        String str = "";
        char[] ch = new char[len * 2];

        for (int i = 0; i < len; i++) {
            if ((((b[i] >> 4) & 0x0f) < 0x0a) && (((b[i] >> 4) & 0x0f) >= 0x0))
                ch[i * 2] = (char) (((b[i] >> 4) & 0x0f) + '0');
            else
                ch[i * 2] = (char) (((b[i] >> 4) & 0x0f) + 'A' - 10);

            if ((((b[i]) & 0x0f) < 0x0a) && (((b[i]) & 0x0f) >= 0x0))
                ch[i * 2 + 1] = (char) (((b[i]) & 0x0f) + '0');
            else
                ch[i * 2 + 1] = (char) (((b[i]) & 0x0f) + 'A' - 10);

        }
        str = new String(ch);
        return str;
    }

    /**
     * @param
     * @return
     */
    public static String byte2hex(byte[] bytes) // 二行制转字符串
    {
        StringBuilder hexStr = new StringBuilder();
        for (byte b : bytes) {
            if (Integer.toHexString(0xFF & b).length() == 1)
                hexStr.append("0").append(
                        Integer.toHexString(0xFF & b));
            else
                hexStr.append(Integer.toHexString(0xFF & b));
        }
        return hexStr.toString();
    }

    public static boolean Str2Hex(byte in[], byte out[], int len) {
        byte asciiCode[] = {10, 11, 12, 13, 14, 15};
        if (len > in.length)
            return false;
        if (len % 2 != 0)
            return false;
        byte temp[] = new byte[len];
        for (int i = 0; i < len; i++)
            if (in[i] >= 48 && in[i] <= 57)
                temp[i] = (byte) (in[i] - 48);
            else if (in[i] >= 65 && in[i] <= 70)
                temp[i] = asciiCode[in[i] - 65];
            else if (in[i] >= 97 && in[i] <= 102)
                temp[i] = asciiCode[in[i] - 97];
            else
                return false;

        for (int i = 0; i < len / 2; i++)
            out[i] = (byte) (temp[2 * i] * 16 + temp[2 * i + 1]);

        return true;
    }

    /**
     * @param in
     * @param out
     * @param len
     * @return
     */
    public static boolean Hex2Str(byte in[], byte out[], int len) {
        byte asciiCode[] = {65, 66, 67, 68, 69, 70};
        if (len > in.length)
            return false;
        byte temp[] = new byte[2 * len];
        for (int i = 0; i < len; i++) {
            temp[2 * i] = (byte) ((in[i] & 0xf0) / 16);
            temp[2 * i + 1] = (byte) (in[i] & 0xf);
        }

        for (int i = 0; i < 2 * len; i++)
            if (temp[i] <= 9 && temp[i] >= 0)
                out[i] = (byte) (temp[i] + 48);
            else
                out[i] = asciiCode[temp[i] - 10];

        return true;
    }

    public static boolean isEmptyOrWildcard(final String value) {
        return value == null || value.trim().length() == 0 || "null".endsWith(value) || "*".equals(value);
    }

    public static boolean isEmptyOrMinus(final String value) {
        return value == null || value.trim().length() == 0 || "null".endsWith(value) || "-".equals(value);
    }

    /**
     * 以UTF-8编码计算字符串的长度，
     *
     * @param str
     * @return
     */
    public static int getStrLength(String str) {
        if (str == null || str.equals("")) {
            return 0;
        } else {
            try {
                return str.getBytes(Constants.Default_SysEncoding).length;
            } catch (UnsupportedEncodingException e) {
                return 0;
            }
        }
    }

    /**
     * 检查邮箱格式是否正确
     *
     * @param email
     * @return true：正确；false：不正确
     */
    public static boolean checkEmail(String email) {
        return email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");
    }


    /**
     * 检查电话号码格式是否正确
     *
     * @param phone
     * @return
     */
    public static boolean checkTelPhone(String phone) {
        String prefix = "0\\d{2,3}-\\d{7,8}";
        Pattern p = Pattern.compile(prefix);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 判断字符串是否是金额
     *
     * @param dailyLimit
     * @return
     */
    public static boolean isAmount(String dailyLimit) {
        String reg = "^[0-9]*(\\.[0-9]{1,2})?$";
        return dailyLimit.matches(reg);
    }

    public static String trimRight(String str) {
        return str.replaceAll("\\s+$", "");
    }

    public static String trim(String str) {
        return null == str ? "" : str.trim();
    }

    public static String concat(Object... pieces) {
        StringBuilder sb = new StringBuilder();
        for (Object piece : pieces) {
            sb.append(piece);
        }
        return sb.toString();
    }

    /**
     * 获取随机码
     *
     * @param size
     * @return
     */
    public static String generateCode(int size) {
        char[] chars = "1234567890".toCharArray();
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }

    /**
     * 截取掉超长的字符
     *
     * @param val
     * @param len
     * @return
     */
    public static String interc(String val, String encoding, int len) {
        // 如果为空，则填写默认值
        if (isEmpty(val)) {
            return StringUtils.EMPTY;
        }
        // 去除两边的空格
        val = val.trim();
        // 计算字节数
        try {
            byte[] array;
            boolean isDefault = isEmpty(encoding);
            if (isDefault) {
                array = val.getBytes();
            } else {
                array = val.getBytes(encoding);
            }
            if (array.length > len) {
                return isDefault ? new String(array, 0, len) : new String(array, 0, len, encoding);
            } else {
                return val;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return val;
        }
    }
}

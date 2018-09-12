package com.protops.gateway.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhouzhihao on 2014/11/25.
 */
public class VerifyUtils {

    public static final Pattern REGEX_BASE_USERNAME = Pattern.compile("^\\w{5,20}$");

    public static final Pattern REGEX_MOBILE = Pattern.compile("1[34578]\\d{9}");

    public static final Pattern REGEX_EMAIL = Pattern.compile("(?=.{0,64})\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    public static final Pattern REGEX_IDCARD = Pattern.compile("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");

    public static final Pattern REGEX_HANZI = Pattern.compile("[\u4E00-\u9FA5]");

    public static final Pattern REGEX_ZIMU_SHUZI = Pattern.compile("[A-HJ-NP-Za-hj-np-z0-9]{6}");

    public static boolean verify(String value, Pattern pattern) {

        if (org.springframework.util.StringUtils.isEmpty(value)) {
            return false;
        }

        Matcher cp = pattern.matcher(value);

        return cp.matches();
    }

    public static boolean isValidPlateNumber(String value) {

        // 允许空
        if(StringUtils.isEmpty(value)){
            return true;
        }

        if (!StringUtils.isEmpty(value) && value.length() != 7) {
            return false;
        }

        String firstHanzi = value.substring(0, 1);

        Matcher matcher = REGEX_HANZI.matcher(firstHanzi);

        if (!matcher.matches()) {
            return false;
        }

        String left = value.substring(1);

        Matcher matcherZimuShuzi = REGEX_ZIMU_SHUZI.matcher(left);

        if (!matcherZimuShuzi.matches()) {
            return false;
        }

        return true;

    }

    public static boolean isValidPlateNumberAbbr(String value) {

        Matcher matcherZimuShuzi = REGEX_ZIMU_SHUZI.matcher(value);

        if (!matcherZimuShuzi.matches()) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        String value = "鄂O54168";

        System.out.println(isValidPlateNumber(value));


    }


}


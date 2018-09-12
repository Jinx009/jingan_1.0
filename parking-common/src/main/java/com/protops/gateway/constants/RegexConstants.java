package com.protops.gateway.constants;

import java.util.regex.Pattern;

/**
 * 页面前端正则校验常量。
 * 
 * @author Linhui
 * 
 */
public class RegexConstants {
    // 校验码
    public static final Pattern CAPTCHA_CODE_PATTERN = Pattern.compile("^[\\da-zA-Z]{4}$");

    // 手机短信校验码
    public static final Pattern SMS_CODE_PATTERN = Pattern.compile("^\\d{6}$");

    // 用户名
    public static final Pattern LOGIN_NAME_PATTERN = Pattern.compile("^\\w{6,20}$");
    
    //普通用户名
    public static final Pattern PLAIN_USER_NAME_PATTERN = Pattern.compile("(?=[\\d]{0,20}[_A-Za-z])[_A-Za-z0-9]{6,20}");

    // 真实姓名，2-16个中文或者2-32个英文，需要在代码里自行判定长度
    public static final Pattern REAL_NAME_PATTERN = Pattern.compile("[^!$%\\^&*?<>]{2,30}");

    // 通讯地址
    public static final Pattern ADDRESS_PATTERN = Pattern.compile("[^!$%\\^&*?<>]*");

    // 电子邮箱
    public static final Pattern EMAIL_PATTERN = Pattern
                    .compile("(?=.{0,64})\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    // 手机号码
    public static final Pattern CELLPHONE_PATTERN = Pattern.compile("^1[34578]\\d{9}$");
    
    // 国外手机号码
    public static final Pattern FOREIGN_CELLPHONE_PATTERN = Pattern.compile("^\\d{4,15}$");

    // 页面欢迎问候语
    public static final Pattern SECURITY_INFO_PATTERN = Pattern.compile("[^!$%\\^&*?<>]{2,16}");

    // 安全问题
    public static final Pattern SECURITY_QUESTION_PATTERN = Pattern.compile("[^!$%\\^&*?<>]{2,16}");

    // 安全问题答案
    public static final Pattern SECURITY_ANSWER_PATTERN = Pattern.compile("[^!$%\\^&*?<>]{2,16}");

    // 邮编
    public static final Pattern POST_CODE_PATTERN = Pattern.compile("\\d{6}");

    // 身份证
    public static final Pattern ID_CARD_PATTERN = Pattern.compile("^(\\d{15}|\\d{17}[x\\d])$");

    // 其他证件
    public static final Pattern OTHER_CERT_PATTERN = Pattern.compile("^[^!$%\\^&*?<>]{5,18}$");

    // 银行卡号
    public static final Pattern CARD_NO_PATTERN = Pattern.compile("^\\d{13,19}$");

    // cvn2
    public static final Pattern CVN2_PATTERN = Pattern.compile("^\\d{3}$");

    // 有效期年
    public static final Pattern EXPIRE_YEAR_PATTERN = Pattern.compile("^\\d{2}$");

    // 有效期月
    public static final Pattern EXPIRE_MONTH_PATTERN = Pattern.compile("^0[1-9]|1[0-2]$");
}

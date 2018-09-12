package com.protops.gateway.util;

import com.protops.gateway.constants.enums.LoginType;
import org.apache.commons.lang.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhouzhihao on 2015/4/7.
 */
public class BaseMemberUtil {

    private static final String INIT_PWD = "666666";

    private static final String DEACTIVETIME = "19861016000000";

    public static boolean checkLoginTypePermission(int result, LoginType logintype) {
        return (result & logintype.getResult()) != 0;
    }

    public static Integer getAllLoginType() {
        return LoginType.all();
    }

    public static String genInitPwd() {
        return SignUtils.md5(INIT_PWD);
    }

    public static String genProtopsMememberNumber(Integer maxCnt) {
        return org.apache.commons.lang.StringUtils.leftPad(String.valueOf(maxCnt), 8, '0');
    }

    public static Date getDeactiveTime() {
        return DateUtil.parseDate(DEACTIVETIME, DateUtil.DATE_FMT_YMDHMS);
    }
}

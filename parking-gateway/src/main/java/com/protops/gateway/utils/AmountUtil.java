package com.protops.gateway.utils;

import com.protops.gateway.util.StringUtils;

import java.math.BigDecimal;

/**
 * Created by damen on 2016/1/4.
 */
public class AmountUtil {

    public static Integer toInteger(String amount) {
        if (StringUtils.isEmpty(amount)) {
            return 0;
        }
        return new BigDecimal(amount).multiply(new BigDecimal("100")).intValue();
    }

    public static String toString(Integer amount) {
        if (amount == null) {
            return "0";
        }
        return new BigDecimal(amount + "").divide(new BigDecimal("100")).toString();
    }

}


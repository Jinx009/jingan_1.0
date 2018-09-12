package com.protops.gateway.constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: wangyuqiu
 * Date: 13-3-8
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */


public enum LocaleEnum implements Serializable {

        US(Locale.US, "English"),

        CHINA(Locale.CHINA, "简体中文"),

        TAIWAN(Locale.TAIWAN, "繁體中文");


        private Locale locale;
        private String desc;

        private LocaleEnum(Locale locale, String desc) {
            this.locale = locale;
            this.desc = desc;
        }

        public Locale getLocale() {
            return locale;
        }

        public String getDesc() {
            return desc;
        }




        public static LocaleEnum parseByLocale(Locale locale){
            for (LocaleEnum result : values()) {
                //如果是该locale则判断是否相等
                if (result.getLocale().toString().equals(locale.toString())) {
                    return result;
                }
            }

            //如果都不匹配 则判断国家是否匹配 返回该国家的语言
            for (LocaleEnum result : values()) {
                //如果是该locale则判断是否相等
                if (result.getLocale().getLanguage().equals(locale.getLanguage())) {
                    return result;
                }
            }

            return LocaleEnum.CHINA;

        }




        public static ArrayList<LocaleEnum> getNotEnums(LocaleEnum localeEnum){
            ArrayList<LocaleEnum> enumArrayList = new ArrayList<LocaleEnum>();
            for (LocaleEnum result : values()) {
                if (!result.getLocale().toString().equals(localeEnum.getLocale().toString())) {
                    enumArrayList.add(result);
                }
            }

            return enumArrayList;

        }

        public static LocaleEnum parseByDesc(String desc) {
            for (LocaleEnum result : values()) {
                if (result.getDesc().equals(desc)) {
                    return result;
                }
            }
            return null;
        }

        public static LocaleEnum getFinalizedLocaleEnum(LocaleEnum localeEnum) {
            if (localeEnum == null) {
                return null;
            }
            switch (localeEnum) {
                case CHINA:{
                    return LocaleEnum.CHINA;
                }
                case TAIWAN: {
                    return LocaleEnum.TAIWAN;
                }
                case US: {
                    return LocaleEnum.US;
                }
                default: {
                    return null;
                }
            }
        }

        public final LocaleEnum getFinalized() {
            switch (this) {
                case CHINA:{
                    return LocaleEnum.CHINA;
                }
                case TAIWAN: {
                    return LocaleEnum.TAIWAN;
                }
                case US: {
                    return LocaleEnum.US;
                }
                default: {
                    return null;
                }
            }
        }

    }




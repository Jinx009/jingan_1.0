package com.protops.gateway.constants;

/**
 * Created by zhouzhihao on 2014/10/23.
 */
public class ResponseCodes {

    public class Sys {

        public static final String PARAM_ILLEGAL = "0000001";

        public static final String SYS_ERR = "0000002";

        public static final String AUTH_FAILED = "0000003";

        public static final String AUTH_TIME_EXPIRED = "0000004";

        public static final String AUTH_COUNT_EXCEED = "0000005";
    }

    public class Weixin {

        public static final String SESSION_MISSING = "0000200";

        public static final String USER_DUPLICATED = "0000201";

        public static final String NO_SUCH_SERVICE = "0000209";

        public static final String SMS_SEND_ERROR = "0000211";

        public static final String TOO_MANY_SMS_SENT = "0000212";

        public static final String SMS_CODE_ERR = "0000213";

        public static final String SMS_CODE_INVALID = "0000214";

        public static final String SMS_TOO_FREQUENCY = "0000215";

        public static final String LOT_NO_ILLEGAL = "0000216";

        public static final String REPUSH_ORDER_REQUIRED = "0000230";

        public static final String PUSH_ORDER_TO_WEIXIN_FAILED = "0000231";

        public static final String NO_MORE_CASH = "0000232";

        public static final String LOT_UNAVAILABLE = "0000233";
    }
}

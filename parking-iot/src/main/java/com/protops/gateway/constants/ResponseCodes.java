package com.protops.gateway.constants;

/**
 * Created by zhouzhihao on 2014/10/23.
 */
public class ResponseCodes {

    public class Sys {

        public static final String PARAM_ILLEGAL = "0000001";

        public static final String SYS_ERR = "0000002";

        public static final String AUTH_FAILED = "0000102";

    }

    public class RouterErr {

        public static final String ROUTER_INFO_NOT_FOUND = "000100";


    }

    public class SensorErr {


        public static final String REQUEST_ILLEGAL = "000200";
    }

    public class Weixin {

        public static final String SESSION_MISSING = "0000200";

        public static final String BINDING_PLATE_NUMBER_ERROR = "0000201";

        public static final String YOU_ARE_NOT_ONE_OF_US = "0000202";

        public static final String PLATE_NUMBER_NOT_BOUND = "0000203";

        public static final String THIS_VIHICLE_HAS_OUT = "0000204";

        public static final String NEED_CHOOSE_DIKOU_WAY = "0000205";

        public static final String NO_MORE_DIKOU = "0000206";

        public static final String NO_MORE_COUPONS = "0000207";

        public static final String NO_MORE_POINTS = "0000208";

        public static final String NO_SUCH_SERVICE = "0000209";

        public static final String PLATE_NUMBER_ALREADY_BOUND = "0000210";

        public static final String SMS_SEND_ERROR = "0000211";

        public static final String TOO_MANY_SMS_SENT = "0000212";

        public static final String SMS_CODE_ERR = "0000213";

        public static final String SMS_CODE_INVALID = "0000214";

        public static final String SMS_TOO_FREQUENCY = "0000215";

        public static final String COUPON_ALREADY_BOUND = "0000216";

        public static final String COUPON_ID_INVALID = "0000217";

        public static final String COUPON_RUN_OUT = "0000218";

        public static final String MEMBER_COUPON_USED = "0000219";

        public static final String MEMBER_COUPON_INVALID = "0000220";

        public static final String COUPON_CODE_INCONSISTENCY = "0000221";

        public static final String MEMBER_ALREADY_BOUND = "0000222";

        public static final String MEMBER_NUMBER_INEXIST = "0000223";

        public static final String MEMBER_NUMBER_9_START = "0000224";

        public static final String MEMBER_COUPON_NOT_FOUND = "0000225";

        public static final String MEMBER_EXPIRED = "0000226";

        public static final String MEMBER_REAL_NAME_MOBILE_EMPTY = "0000227";

        public static final String MEMBER_MOBILE_ALREADY_BOUND = "0000228";

        public static final String COUPON_SHAKABLE_NOT_BINGO = "0000229";

        public static final String REPUSH_ORDER_REQUIRED = "0000230";

        public static final String PUSH_ORDER_TO_WEIXIN_FAILED = "0000231";

        public static final String NO_MORE_CASH = "0000232";

        public static final String STILL_FREE = "0000233";

        public static final String EXCEED_24_HOURS = "0000234";

        public static final String AMOUNT_ZERO = "0000235";

        public static final String QBDK_SEND_TO_PARKING_LOT_ERR = "0000236";

        public static final String PLATE_NUMBER_INVALID = "0000237";
    }

}

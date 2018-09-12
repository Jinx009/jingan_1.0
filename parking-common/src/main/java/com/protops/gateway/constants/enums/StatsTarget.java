package com.protops.gateway.constants.enums;

/**
 * Created by damen on 2014/11/26.
 */
public enum StatsTarget {

    bind, myMemberCard, plateNumberBinding, register, points_qbdk, coupon_qbdk, eparkCouponUse, pointsUse,pointsUse_show, parkInfo, pointsQuery,
    coupon_list, my_coupon_list, bingo_coupon, sms, order;


    public static String[] queryList(){

        return new String[]{eparkCouponUse.name(), pointsUse_show.name(), parkInfo.name(), pointsQuery.name()};

    }

    public static String[] payList(){

        return new String[]{order.name(), points_qbdk.name(), coupon_qbdk.name()};

    }

    public static String[] allList(){

        return new String[]{points_qbdk.name(), pointsUse_show.name(), bind.name(), parkInfo.name()};

    }

    public static String[] pointsUseList(){

        return new String[]{points_qbdk.name()};

    }

    public static String[] pointsQueryList(){

        return new String[]{pointsUse_show.name()};

    }


}

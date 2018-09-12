package com.protops.gateway.constants.enums;

/**
 * Created by zhouzhihao on 2014/10/22.
 */
public enum PayTypeEnum {

    free(0), points(1), coupon(2), cash(3);

    private Integer payType;

    PayTypeEnum(Integer payType) {
        this.payType = payType;
    }


    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public static PayTypeEnum parse(Integer payType) {

        for (PayTypeEnum payTypeEnum : values()) {
            if (payTypeEnum.getPayType() == payType) {
                return payTypeEnum;
            }
        }
        return null;
    }
}

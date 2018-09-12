package com.protops.gateway.constants.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by 琳 on 2015/4/14.
 */
public enum VihicleInOutStatusEnum {

    STATUS_IN(0),
    STATUS_QUERY_COUPON(1),
    STATUS_QUERY_POINTS(2),
    STATUS_PAIED(3),
    STATUS_OUT(4);

    private Integer status;

    VihicleInOutStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String display() {
        return getPlaceHolder(this.name());
    }

    public static List<VihicleInOutStatusEnum> getAll() {

        List<VihicleInOutStatusEnum> statusList = new ArrayList<VihicleInOutStatusEnum>();

        for (VihicleInOutStatusEnum opEnum : values()) {
            statusList.add(opEnum);
        }

        return statusList;
    }

    private String getPlaceHolder(String key) {
        return ResourceBundle.getBundle("i18n/placeholder").getString(key);
    }
}

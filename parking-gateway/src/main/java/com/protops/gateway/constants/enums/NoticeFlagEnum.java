package com.protops.gateway.constants.enums;

/**
 * Created by fanlin on 16/6/21.
 */
public enum NoticeFlagEnum {

    NOTHING(0), NOTICE_TO_EXPIRE(1), NOTICE_BE_EXPIRING(2), NOTICE_EXPIRED(3);

    private Integer id;

    NoticeFlagEnum(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

package com.protops.gateway.constants.enums;

/**
 * Created by damen on 2016/6/12.
 */
public enum MemberStatus {

    OK(0);

    private Integer id;

    MemberStatus(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

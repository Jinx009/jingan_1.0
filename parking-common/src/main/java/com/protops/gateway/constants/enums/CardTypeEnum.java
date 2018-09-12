package com.protops.gateway.constants.enums;

/**
 * Created by zhouzhihao on 2015/4/13.
 */
public enum CardTypeEnum {

    emember(1), realNameMember(2);

    private Integer id;

    CardTypeEnum(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

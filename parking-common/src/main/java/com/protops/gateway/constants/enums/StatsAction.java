package com.protops.gateway.constants.enums;

/**
 * Created by zhouzhihao on 2014/11/26.
 */
public enum StatsAction {

    view(0), event(1), text(2), richText(3);

    private Integer code;

    private StatsAction(Integer code) {
        this.code = code;
    }

    public static StatsAction parse(Integer code) {
        for (StatsAction statsAction : values()) {
            if (statsAction.getCode() == code) {
                return statsAction;
            }
        }
        return null;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

package com.protops.gateway.constants.enums;

/**
 * Created by zhouzhihao on 2015/7/28.
 */
public enum QueryResult {

    /**
     * 成功
     */
    Succeed(0),

    /**
     * 处理中
     */
    InProcess(1),

    /**
     * 失败
     */
    Failed(2);


    private Integer code;

    QueryResult(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static QueryResult parse(Integer code) {
        for (QueryResult result : values()) {
            if (result.getCode().equals(code)) {
                return result;
            }
        }
        return null;
    }
}

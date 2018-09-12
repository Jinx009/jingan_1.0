package com.protops.gateway.constants.enums;

/**
 * Created by zhouzhihao on 2015/4/7.
 */
public enum LoginType {

    MOBILE(1), WEIXIN(2);

    // 偏移后结果
    private int result;

    /**
     * 输入为偏移量
     *
     * @param offset
     */
    LoginType(int offset) {
        this.result = 1 << offset;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static void main(String[] args){

    }

    public static Integer all() {

        Integer totalResult = 0;

        for (LoginType loginType : LoginType.values()) {

            totalResult += loginType.getResult();

        }

        return totalResult;

    }
}

package com.protops.gateway.constants.enums;

/**
 * Created by zhouzhihao on 2016/10/9.
 */
public enum Version {

    v1_0("1_0"), v1_1("1_1");

    private String version;

    Version(String version) {
        this.version = version;
    }

    public static Version parse(String code) {
        for (Version result : values()) {
            if (result.getVersion().equals(code)) {
                return result;
            }
        }
        return null;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

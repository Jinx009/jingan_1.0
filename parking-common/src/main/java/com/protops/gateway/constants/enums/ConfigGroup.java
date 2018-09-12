package com.protops.gateway.constants.enums;

/**
 * Created by zhouzhihao on 2015/8/18.
 */
public enum  ConfigGroup {

    DEFAULT(0), PAYMENT(1);

    private int groupId;

    ConfigGroup(int groupId) {
        this.groupId = groupId;
    }

    public static ConfigGroup parse(Integer groupId) {

        for (ConfigGroup configGroup : values()) {
            if (configGroup.getGroupId() == groupId) {
                return configGroup;
            }
        }
        return null;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


}

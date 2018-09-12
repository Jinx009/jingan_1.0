package com.protops.gateway.constants.enums.iot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damen on 2016/3/30.
 */
public enum  JobStatus {

    init(0, "新建"), received(1, "已接收"), ok(2, "已完成"), failed(3, "已失败"), timeout(4, "已超时");

    private Integer status;
    private String name;

    JobStatus(Integer status, String name){
        this.status = status;
        this.name = name;
    }

    public static JobStatus parse(Integer payType) {

        for (JobStatus payTypeEnum : values()) {
            if (payTypeEnum.getStatus() == payType) {
                return payTypeEnum;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<JobStatus> getUndoingStatus() {
        ArrayList<JobStatus> ret = new ArrayList<JobStatus>();
        ret.add(received);
        ret.add(init);
        return ret;
    }
}


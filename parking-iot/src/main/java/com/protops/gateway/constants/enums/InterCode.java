package com.protops.gateway.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 接口方法映射枚举
 */
@Getter
@AllArgsConstructor
public enum InterCode {

    INTERSECTION_SENSOR_REGISTER("intersectionSensor_register_2_0","intersectionSensorService","register","路口地磁注册_2.0版本"),
    INTERSECTION_SENSOR_OPERATION("intersectionSensor_operation_2_0","intersectionSensorService","operation","路口地磁业务数据_2.0版本"),
    INTERSECTION_SENSOR_DEVICE("intersectionSensor_device_2_0","intersectionSensorService","device","路口地磁心跳_2.0版本"),

    ROUTER_REGISTER("router_register_2_0","newRouterService","register","接收机注册新_2.0版本"),
    ROUTER_DEVICE("router_device_2_0","newRouterService","device","接收机心跳新_2.0版本"),

    JOB_STATUS("job_status_2_0","newJobService","status","任务执行状态_2.0版本"),

    BLUE_STATUS("bluetooth_status_1_0","bluetoothService","status","1.0版本"),
    BIKE_STATUS("bike_status_1_0","bluetoothService","bike","1.0版本")

    ;



    private String code;//编码
    private String serverBean;//bean名称
    private String func; //方法名
    private String desc;//描述

    /**
     *  根据code查询
     * @param code 接口编码
     * @return 接口编码对象
     */
    public static InterCode getByCode(String code) {
        InterCode[] es = InterCode.values();
        for (InterCode e : es) {
            if (e.getCode().equals(code))
                return e;
        }
        return null;
    }
}

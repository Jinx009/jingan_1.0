package com.protops.gateway.controller.telcom;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TelcomPushDataModel {

    private String deviceId;
    private String gatewayId;
    private String notifyType;
    private List<PushModel> services;
    private PushModel service;


}
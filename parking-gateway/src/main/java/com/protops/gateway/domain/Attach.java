package com.protops.gateway.domain;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.util.Base64;
import com.protops.gateway.util.JsonUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhouzhihao on 2015/7/28.
 */
public class Attach {

    private Integer parkingTimeSpan;
    private Integer memberId;

    public static Attach toAttach(String attachStr){

        String attachJson = null;
        try {
            attachJson = new String(Base64.decode(attachStr), Constants.Default_SysEncoding);
        } catch (UnsupportedEncodingException e) {
        }

        return JsonUtil.fromJson(attachJson, OrderAttach.class);

    }

    public String toBase64() {
        String json = JsonUtil.toJson(this);

        try {
            return Base64.encode(json.getBytes(Constants.Default_SysEncoding));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getParkingTimeSpan() {
        return parkingTimeSpan;
    }

    public void setParkingTimeSpan(Integer parkingTimeSpan) {
        this.parkingTimeSpan = parkingTimeSpan;
    }
}

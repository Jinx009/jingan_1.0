package com.protops.gateway.vo.lbs;


import com.protops.gateway.exception.WeixinException;

/**
 * Created by damen on 2014/11/3.
 */
public class ReportLocationRequest {

    private String latitude;

    private String longitude;

    private String accuracy;

    private String district;

    public void validate() throws WeixinException {

    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}

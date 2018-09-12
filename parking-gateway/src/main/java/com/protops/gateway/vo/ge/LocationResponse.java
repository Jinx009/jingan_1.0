package com.protops.gateway.vo.ge;

import com.protops.gateway.domain.AreaVo;

import java.util.List;

public class LocationResponse {

    private LocationVo location;
    private List<AreaVo> areas;


    public LocationVo getLocation() {
        return location;
    }

    public void setLocation(LocationVo location) {
        this.location = location;
    }

    public List<AreaVo> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaVo> areas) {
        this.areas = areas;
    }

    public static LocationResponse parse(LocationVo locationVo, List<AreaVo> areaCntList) {

        LocationResponse locationResponse = new LocationResponse();

        locationResponse.setLocation(locationVo);

        locationResponse.setAreas(areaCntList);

        return locationResponse;

    }
}
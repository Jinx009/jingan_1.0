package com.protops.gateway.vo.lbs;

import com.protops.gateway.domain.AreaVo;
import com.protops.gateway.domain.iot.Location;

import java.util.List;

/**
 * Created by damen on 2016/6/12.
 */
public class LBSVo {

    private Location location;

    private List<AreaVo> areaList;

    public LBSVo(Location location, List<AreaVo> areaList) {
        this.areaList = areaList;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<AreaVo> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaVo> areaList) {
        this.areaList = areaList;
    }
}

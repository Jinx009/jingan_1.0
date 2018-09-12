package com.protops.gateway.vo.lbs;

import com.protops.gateway.domain.AreaVo;

import java.util.List;

/**
 * Created by damen on 2014/11/3.
 */
public class ReportLocationResponse {

    private List<AreaVo> areaVoList;

    public ReportLocationResponse(List<AreaVo> areaVoList) {
        this.areaVoList = areaVoList;
    }

    public List<AreaVo> getAreaVoList() {
        return areaVoList;
    }

    public void setAreaVoList(List<AreaVo> areaVoList) {
        this.areaVoList = areaVoList;
    }
}

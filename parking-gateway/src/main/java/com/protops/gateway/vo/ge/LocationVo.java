package com.protops.gateway.vo.ge;

/**
 * Created by damen on 2016/4/15.
 */
public class LocationVo {

    private Integer id;
    private String name;
    private Integer availableCnt = 0;
    private Integer totalCnt = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailableCnt() {
        return availableCnt;
    }

    public void setAvailableCnt(Integer availableCnt) {
        this.availableCnt = availableCnt;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }
}

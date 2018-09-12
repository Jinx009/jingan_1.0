package com.protops.gateway.vo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/5/29.
 */
public class AreaDetail {

    private Integer id;
    private Integer available;
    private String addr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @JsonIgnore
    public boolean available() {
        if(available == 0){
            return true;
        }
        return false;
    }
}

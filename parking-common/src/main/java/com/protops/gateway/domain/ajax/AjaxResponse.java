package com.protops.gateway.domain.ajax;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by damen on 2014/11/3.
 */
@Getter
@Setter
@JsonPropertyOrder({"respCode", "msg", "params"})
public class AjaxResponse<T> {

    @JsonProperty("respCode")
    private String respCode = "00";
    @JsonProperty("msg")
    private String msg = "";

    @JsonProperty("params")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private T domain;

    public AjaxResponse(String respCode,String msg,T t){
        this.domain = t;
        this.respCode = respCode;
        this.msg = msg;
    }
    public AjaxResponse(){

    }

}
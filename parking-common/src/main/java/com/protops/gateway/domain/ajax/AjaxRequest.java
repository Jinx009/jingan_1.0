package com.protops.gateway.domain.ajax;


import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by damen on 2014/11/3.
 */
public class AjaxRequest<T> {

    @JsonProperty("params")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private T domain;

    public T getDomain() {
        return domain;
    }

    public void setDomain(T domain) {
        this.domain = domain;
    }

}
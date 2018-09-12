package com.protops.gateway.domain.base;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by damen on 2014/10/21.
 */
@JsonPropertyOrder({"m", "s", "d"})
public class IOTRequest<T extends IOTDomain> extends BaseMessage {

    @JsonProperty("d")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private T domain;

    public T getDomain() {
        return domain;
    }

    public void setDomain(T domain) {
        this.domain = domain;
    }

    public boolean routerCheckIgnore(){
        return getDomain().routerCheckIgnore();
    }

}
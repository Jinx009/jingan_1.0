package com.protops.gateway.domain;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.exception.IOTException;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/3/23.
 */
public class SignInResponse extends IOTDomain {

    @JsonProperty("es")
    private String encryptedSecret;

    @Override
    public void validateDomain() throws IOTException {

    }

    @Override
    public String parseSignStr(IOTContext iotContext) {

        StringBuilder sb = new StringBuilder();

        genKeyValuePair(sb, "es", getEncryptedSecret() == null ? "" : getEncryptedSecret(), iotContext);

        return sb.toString();
    }

    public void setEncryptedSecret(String encryptedSecret) {
        this.encryptedSecret = encryptedSecret;
    }

    public String getEncryptedSecret() {
        return encryptedSecret;
    }
}

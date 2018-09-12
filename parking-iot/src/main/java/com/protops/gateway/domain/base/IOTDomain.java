package com.protops.gateway.domain.base;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.enums.Version;
import com.protops.gateway.exception.IOTException;
import org.apache.commons.lang.StringUtils;

/**
 * Created by damen on 2016/3/29.
 */
public abstract class IOTDomain {

    public boolean routerCheckIgnore() {
        return false;
    }

    public abstract void validateDomain() throws IOTException;

    public abstract String parseSignStr(IOTContext iotContext);

    public StringBuilder genKeyValuePair(StringBuilder sb, String key, String value, IOTContext iotContext) {

        if(iotContext.getVersion() != Version.v1_0){

            // 如果是空，还要判断结尾是不是含有&
            if(StringUtils.isBlank(value)){

                if(sb.toString().endsWith("&")){
                    sb.deleteCharAt(sb.length() - 1);
                }

                return sb;
            }

        }


        return sb.append(key).append("=").append(value);
    }

    public StringBuilder genKeyValuePairWithAnd(StringBuilder sb, String key, String value, IOTContext iotContext) {

        if(iotContext.getVersion() != Version.v1_0){

            if(StringUtils.isBlank(value)){
                return sb;
            }

        }

        return genKeyValuePair(sb, key, value, iotContext).append("&");

    }

}

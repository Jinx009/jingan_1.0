package com.protops.gateway.vo.sms;

import com.protops.gateway.constants.PlaceHolder;
import com.protops.gateway.constants.RegexConstants;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.util.RegexUtils;

/**
 * Created by zhouzhihao on 2015/4/20.
 */
public class SendSmsRequest {

    private String mobile;
    private String secureKey;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSecureKey() {
        return secureKey;
    }

    public void setSecureKey(String secureKey) {
        this.secureKey = secureKey;
    }

    public void validate() throws WeixinException {

        boolean ok = RegexUtils.verify(getMobile(), RegexConstants.CELLPHONE_PATTERN);

        if (!ok) {
            throw new WeixinException(ResponseCodes.Sys.PARAM_ILLEGAL, PlaceHolder.MOBILE);
        }

    }

}

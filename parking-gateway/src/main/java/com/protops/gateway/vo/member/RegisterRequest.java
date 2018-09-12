package com.protops.gateway.vo.member;


import com.protops.gateway.constants.PlaceHolder;
import com.protops.gateway.constants.RegexConstants;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.util.RegexUtils;

/**
 * Created by damen on 2014/11/3.
 */
public class RegisterRequest {

    private String mobile;

    private String smsCode;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void validate() throws WeixinException {

        boolean ok = RegexUtils.verify(getMobile(), RegexConstants.CELLPHONE_PATTERN);

        if (!ok) {
            throw new WeixinException(ResponseCodes.Sys.PARAM_ILLEGAL, PlaceHolder.MOBILE);
        }

    }
}

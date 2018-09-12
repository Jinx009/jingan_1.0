package com.protops.gateway.vo.order;

import com.protops.gateway.util.XMLParser;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhouzhihao on 2015/7/24.
 */
@XStreamAlias("xml")
public class NotifyResponse {

    private String return_code;
    private String return_msg;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public static String OkMsg() {

        NotifyResponse notifyResponse = new NotifyResponse();
        notifyResponse.setReturn_code("SUCCESS");
        notifyResponse.setReturn_msg("OK");

        return XMLParser.toXMLWithCData(notifyResponse);

    }

    public static String failedMsg() {

        NotifyResponse notifyResponse = new NotifyResponse();
        notifyResponse.setReturn_code("FAIL");

        return XMLParser.toXMLWithCData(notifyResponse);

    }
}

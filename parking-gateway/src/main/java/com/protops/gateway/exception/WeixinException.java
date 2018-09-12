package com.protops.gateway.exception;

/**
 * Created by damen on 2014/11/3.
 */
public class WeixinException extends ProtopsException {


    private static final long serialVersionUID = -5696871042180912169L;

    private String responseCode;
    private String responseMessage;
    private Object[] placeHolders;

    public WeixinException(String responseCode, Object... placeHolders) {
        this.responseCode = responseCode;
        this.placeHolders = placeHolders;
    }

    public WeixinException() {
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object[] getMessageKeys() {
        return placeHolders;
    }

    public void setMessageKeys(Object[] placeHolders) {
        this.placeHolders = placeHolders;
    }
}

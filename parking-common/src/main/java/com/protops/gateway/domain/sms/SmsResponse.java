package com.protops.gateway.domain.sms;

/**
 * Created by damen on 2015/5/19.
 * {
 "code": 0,
 "msg": "OK",
 "result": {
 "count": 1,   //成功发送的短信个数
 "fee": 1,     //扣费条数，70个字一条，超出70个字时按每67字一条计
 "sid": 1097   //短信id；群发时以该id+手机号尾号后8位作为短信id
 }
 }
 */
public class SmsResponse {

    private Integer code;
    private String msg;
    private Result result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}

package com.protops.gateway.domain.sms;

/**
 * Created by damen on 2015/5/19.
 *  "count": 1,   //成功发送的短信个数
 "fee": 1,     //扣费条数，70个字一条，超出70个字时按每67字一条计
 "sid": 1097   //短信id；群发时以该id+手机号尾号后8位作为短信id
 */
public class Result {

    private Integer count;
    private Integer fee;
    private long sid;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}

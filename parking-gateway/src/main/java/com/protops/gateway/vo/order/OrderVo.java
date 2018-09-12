package com.protops.gateway.vo.order;

import com.protops.gateway.constants.enums.OrderStatus;
import com.protops.gateway.constants.enums.OrderType;
import com.protops.gateway.utils.AmountUtil;

import java.util.Date;

/**
 * Created by damen on 2016/6/15.
 */
public class OrderVo {

    private Integer id;

    private String day;

    private String date;

    private String payTime;

    private Integer amount;

    private Integer orderType;

    private Integer status;

    public String getOrderStatusDisplay(){
        return OrderStatus.parse(getStatus()).getMsg();
    }

    public String getOrderTypeDisplay(){
        return OrderType.parse(getOrderType()).getMsg();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getAmountDisplay(){
        return AmountUtil.toString(getAmount());
    }
}

package com.protops.gateway.vo.order;

/**
 * Created by damen on 2016/6/14.
 */
public class PushOrderResponse {

    private String orderNumber;
    private H5Prepay h5Prepay;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public H5Prepay getH5Prepay() {
        return h5Prepay;
    }

    public void setH5Prepay(H5Prepay h5Prepay) {
        this.h5Prepay = h5Prepay;
    }
}

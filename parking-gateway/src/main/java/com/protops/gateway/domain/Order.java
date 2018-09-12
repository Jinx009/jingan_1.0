package com.protops.gateway.domain;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.util.Base64;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.StringUtils;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by damen on 2015/7/20.
 * <p/>
 * 不允许一笔订单支付多次
 */
@Entity
@Table(name = "tbl_order")
public class Order {

    public static final Integer ORDER_STATUS_UNPAID = 1;
    public static final Integer ORDER_STATUS_PAID = 2;
    public static final Integer ORDER_STATUS_REFUNDING = 3;
    public static final Integer ORDER_STATUS_REFUNDED = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "pay_time")
    private Date payTime;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "order_source")
    private Integer orderType;

    @Column(name = "prepay_id")
    private String prepayId;

    @Column(name = "trans_id")
    private String transId;

    @Column(name = "expire_time")
    private Date expireTime;

    @Column(name = "reserved")
    private String reserved;

    @Transient
    private OrderAttach attach;

    @Column(name = "rec_st")
    private Integer recSt = 1;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Integer getRecSt() {
        return recSt;
    }

    public void setRecSt(Integer recSt) {
        this.recSt = recSt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public OrderAttach getAttach() {

        if (attach != null) {
            return attach;
        }

        String attachStr = getReserved();

        if (StringUtils.isEmpty(attachStr)) {
            return new OrderAttach();
        }

        String attachJson = null;
        try {
            attachJson = new String(Base64.decode(attachStr), Constants.Default_SysEncoding);
        } catch (UnsupportedEncodingException e) {
            // ignore
        }

        this.attach = JsonUtil.fromJson(attachJson, OrderAttach.class);

        return attach;
    }

    public void setAttach(OrderAttach attach) {
        this.attach = attach;
    }

    public String getPayTimeDisplay(){
        return DateUtil.getDate(getPayTime(), DateUtil.DATE_FMT_DISPLAY2);
    }

    public String getExpiredDateDisplay(){
        Date date = DateUtil.getAddedTime(getPayTime(), Calendar.MINUTE, getAttach().getParkingTimeSpan());
        return DateUtil.getDate(date, DateUtil.DATE_FMT_DISPLAY2);
    }

    public Integer getAmountDisplay(){
        return getTotalAmount() / 100;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
package com.protops.gateway.domain.base;


import java.io.Serializable;
import java.util.List;

/**
 * Created by jinx on 3/7/17.
 * 新设备数据传入iot实体类
 */
public class IotRequstDomain<T,E> implements Serializable{

    private String baseId;//通讯设备mac号
    private String sign;//签名
    private Integer signType;//签名规则
    private Integer sonType;//子集设备类型
    private Integer fatherType;//通讯设备类型,1代表router,2代表待定
    private T father;//通讯设备
    private List<E> son;//子集


    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    public Integer getFatherType() {
        return fatherType;
    }

    public void setFatherType(Integer fatherType) {
        this.fatherType = fatherType;
    }

    public Integer getSonType() {
        return sonType;
    }

    public void setSonType(Integer sonType) {
        this.sonType = sonType;
    }

    public T getFather() {
        return father;
    }

    public void setFather(T father) {
        this.father = father;
    }

    public List<E> getSon() {
        return son;
    }

    public void setSon(List<E> son) {
        this.son = son;
    }

    public String toString(){
        return "fatherType="+fatherType+";sonType="+sonType+";sonSize:"+son.size()+";baseId:"+baseId;
    }
}

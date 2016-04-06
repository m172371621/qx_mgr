package com.brilliantreform.sc.order.po;

import java.util.Date;

public class DeliveryAddress {
    private Integer address_id;
    private Integer user_id;
    private String lnglatXY;
    private String address;
    private Date createTime;

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getLnglatXY() {
        return lnglatXY;
    }

    public void setLnglatXY(String lnglatXY) {
        this.lnglatXY = lnglatXY;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

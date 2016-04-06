package com.brilliantreform.sc.activity.po;

import java.util.Date;

public class UserCoupon {
    private Integer coupon_id;
    private Integer user_id;
    private Double off_price;
    private Double use_price;
    private Integer status;
    private Integer type;
    private String randomCode;
    private Date expiretime;
    private Date createtime;

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Double getOff_price() {
        return off_price;
    }

    public void setOff_price(Double off_price) {
        this.off_price = off_price;
    }

    public Double getUse_price() {
        return use_price;
    }

    public void setUse_price(Double use_price) {
        this.use_price = use_price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public Date getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}

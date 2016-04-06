package com.brilliantreform.sc.activity.po;

import java.util.Date;

public class CouponInfo {
    private Integer coupon_id;
    private Double off_price;
    private Double use_price;
    private Integer cid;
    private Date expire_date;
    private Date createtime;
    private Integer removetag;

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
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

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(Date expire_date) {
        this.expire_date = expire_date;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getRemovetag() {
        return removetag;
    }

    public void setRemovetag(Integer removetag) {
        this.removetag = removetag;
    }
}

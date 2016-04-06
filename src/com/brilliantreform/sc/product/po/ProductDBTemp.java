package com.brilliantreform.sc.product.po;

import java.util.Date;

/**
 * 商品调拨单临时表 product_db_temp
 * Created by shangwq on 2016/1/13.
 */
public class ProductDBTemp {
    private Integer objid;
    private Integer community_id;
    private Integer user_id;
    private Integer product_id;
    private Integer is_total;
    private Double amount;
    private Date create_time;

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getIs_total() {
        return is_total;
    }

    public void setIs_total(Integer is_total) {
        this.is_total = is_total;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

}

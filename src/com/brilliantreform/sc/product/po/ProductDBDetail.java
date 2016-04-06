package com.brilliantreform.sc.product.po;

/**
 * 商品调拨商品清单
 * Created by shangwq on 2016/1/15.
 */
public class ProductDBDetail {
    private Integer objid;
    private Integer header_id;
    private String batch_serial;
    private Integer product_id;
    private Integer is_total;
    private Double unit_price;
    private Double change_count;
    private Double pre_stock_count;
    private String remark;
    private Integer removetag;

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getHeader_id() {
        return header_id;
    }

    public void setHeader_id(Integer header_id) {
        this.header_id = header_id;
    }

    public String getBatch_serial() {
        return batch_serial;
    }

    public void setBatch_serial(String batch_serial) {
        this.batch_serial = batch_serial;
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

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Double getChange_count() {
        return change_count;
    }

    public void setChange_count(Double change_count) {
        this.change_count = change_count;
    }

    public Double getPre_stock_count() {
        return pre_stock_count;
    }

    public void setPre_stock_count(Double pre_stock_count) {
        this.pre_stock_count = pre_stock_count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRemovetag() {
        return removetag;
    }

    public void setRemovetag(Integer removetag) {
        this.removetag = removetag;
    }
}

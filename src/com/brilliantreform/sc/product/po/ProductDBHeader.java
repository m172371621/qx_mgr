package com.brilliantreform.sc.product.po;

import java.util.Date;

/**
 * 商品调拨单
 * Created by shangwq on 2016/1/15.
 */
public class ProductDBHeader {
    private Integer objid;
    private Integer from_cid;
    private Integer to_cid;
    private String header_no;
    private Integer user_id;
    private Date create_time;
    private Integer is_total;
    private Integer status;
    private Integer operator;
    private Date operate_time;
    private String remark;
    private Integer removetag;

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getFrom_cid() {
        return from_cid;
    }

    public void setFrom_cid(Integer from_cid) {
        this.from_cid = from_cid;
    }

    public Integer getTo_cid() {
        return to_cid;
    }

    public void setTo_cid(Integer to_cid) {
        this.to_cid = to_cid;
    }

    public String getHeader_no() {
        return header_no;
    }

    public void setHeader_no(String header_no) {
        this.header_no = header_no;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getIs_total() {
        return is_total;
    }

    public void setIs_total(Integer is_total) {
        this.is_total = is_total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Date getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(Date operate_time) {
        this.operate_time = operate_time;
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

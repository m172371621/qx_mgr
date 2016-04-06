package com.brilliantreform.sc.order.po;

import java.util.Date;

public class OrderRefundBase {
    private Integer objid;
    private String order_serial;
    private String refund_serial;
    private Integer userid;
    private Integer workerid;
    private Integer community_id;
    private Integer return_status;
    private Double refund_money;
    private Integer refund_type;
    private Integer refund_status;
    private String refund_reason;
    private Date return_time;
    private Date refund_time;
    private Date reject_time;
    private String reject_reason;
    private String remark;
    private Date create_time;
    private Integer removetag;

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public String getOrder_serial() {
        return order_serial;
    }

    public void setOrder_serial(String order_serial) {
        this.order_serial = order_serial;
    }

    public String getRefund_serial() {
        return refund_serial;
    }

    public void setRefund_serial(String refund_serial) {
        this.refund_serial = refund_serial;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getWorkerid() {
        return workerid;
    }

    public void setWorkerid(Integer workerid) {
        this.workerid = workerid;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }

    public Integer getReturn_status() {
        return return_status;
    }

    public void setReturn_status(Integer return_status) {
        this.return_status = return_status;
    }

    public Double getRefund_money() {
        return refund_money;
    }

    public void setRefund_money(Double refund_money) {
        this.refund_money = refund_money;
    }

    public Integer getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(Integer refund_type) {
        this.refund_type = refund_type;
    }

    public Integer getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(Integer refund_status) {
        this.refund_status = refund_status;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public Date getReturn_time() {
        return return_time;
    }

    public void setReturn_time(Date return_time) {
        this.return_time = return_time;
    }

    public Date getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(Date refund_time) {
        this.refund_time = refund_time;
    }

    public Date getReject_time() {
        return reject_time;
    }

    public void setReject_time(Date reject_time) {
        this.reject_time = reject_time;
    }

    public String getReject_reason() {
        return reject_reason;
    }

    public void setReject_reason(String reject_reason) {
        this.reject_reason = reject_reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getRemovetag() {
        return removetag;
    }

    public void setRemovetag(Integer removetag) {
        this.removetag = removetag;
    }
}

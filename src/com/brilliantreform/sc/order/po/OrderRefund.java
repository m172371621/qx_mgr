package com.brilliantreform.sc.order.po;

import java.util.Date;

public class OrderRefund {
    private Integer objid;
    private Integer order_id;
    private String order_serial;
    private String refund_serial;
    private Integer userid;
    /**
     * 退货状态：0.待处理(初始) 1.退货完成 -1.退货驳回
     */
    private Integer status;
    /**
     * 退款状态：0.待处理  1.处理中  2.处理完成  -1.失败   -2.无需退款
     * */
    private Integer refund_status;
    /**
     * 退货时间（退货成功时为退货完成时间，驳回时候则为驳回时间）
     */
    private Date refund_time;
    private String refund_reason;
    private Integer workerid;
    private String reject_reason;
    private String remark;
    /**
     * 退款方式 1. 现金  2. 区享卡  3. 支付宝   4. 微信
     */
    private Integer refund_type;
    private Integer product_id;
    private Double refund_amount;
    private Double product_price;
    private Double refund_price;
    private Integer community_id;
    private Date create_time;

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(Date refund_time) {
        this.refund_time = refund_time;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public Integer getWorkerid() {
        return workerid;
    }

    public void setWorkerid(Integer workerid) {
        this.workerid = workerid;
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

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    public Double getRefund_price() {
        return refund_price;
    }

    public void setRefund_price(Double refund_price) {
        this.refund_price = refund_price;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public void setRefund_status(Integer refund_status) {
        this.refund_status = refund_status;
    }

    public Integer getRefund_status() {
        return refund_status;
    }

    public Integer getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(Integer refund_type) {
        this.refund_type = refund_type;
    }

    public Double getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(Double refund_amount) {
        this.refund_amount = refund_amount;
    }
}

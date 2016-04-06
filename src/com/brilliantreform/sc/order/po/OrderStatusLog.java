package com.brilliantreform.sc.order.po;

import java.util.Date;

/**
 * 订单状态变化日志
 * Created by shangwq on 2015/9/16.
 */
public class OrderStatusLog {
    private Integer objid;
    private String order_serial;
    private Integer old_status;
    private Integer new_status;
    private Integer user_id;
    private Date create_time;

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

    public Integer getOld_status() {
        return old_status;
    }

    public void setOld_status(Integer old_status) {
        this.old_status = old_status;
    }

    public Integer getNew_status() {
        return new_status;
    }

    public void setNew_status(Integer new_status) {
        this.new_status = new_status;
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
}

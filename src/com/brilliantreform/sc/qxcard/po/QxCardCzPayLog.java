package com.brilliantreform.sc.qxcard.po;

import java.util.Date;

public class QxCardCzPayLog {
    private Integer objid;
    private Integer czlog_id;
    private String cz_serial;
    private String retdata;
    private Date pay_time;
    private Double pay_price;

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getCzlog_id() {
        return czlog_id;
    }

    public void setCzlog_id(Integer czlog_id) {
        this.czlog_id = czlog_id;
    }

    public String getCz_serial() {
        return cz_serial;
    }

    public void setCz_serial(String cz_serial) {
        this.cz_serial = cz_serial;
    }

    public String getRetdata() {
        return retdata;
    }

    public void setRetdata(String retdata) {
        this.retdata = retdata;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public Double getPay_price() {
        return pay_price;
    }

    public void setPay_price(Double pay_price) {
        this.pay_price = pay_price;
    }
}

package com.brilliantreform.sc.qxcard.po;

import java.io.Serializable;
import java.util.Date;

public class QxCardCzLog implements Serializable {

    private Integer objid;
    private Integer user_id;
    private String phone;
    private Integer card_value;
    private Integer amount;
    private Integer cz_price;
    private Double pay_price;
    private Integer channel;
    private Integer pay_type;
    private Integer pay_status;
    private Integer status;
    private String card_no;
    private String cz_serial;
    private Integer community_id;
    private Date create_time;
    private Date pay_time;
    private Date cz_time;
    private Integer workerid;
    private Integer removetag;

    public QxCardCzLog() {}

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCard_value() {
        return card_value;
    }

    public void setCard_value(Integer card_value) {
        this.card_value = card_value;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCz_price() {
        return cz_price;
    }

    public void setCz_price(Integer cz_price) {
        this.cz_price = cz_price;
    }

    public Double getPay_price() {
        return pay_price;
    }

    public void setPay_price(Double pay_price) {
        this.pay_price = pay_price;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public Integer getPay_status() {
        return pay_status;
    }

    public void setPay_status(Integer pay_status) {
        this.pay_status = pay_status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCz_serial() {
        return cz_serial;
    }

    public void setCz_serial(String cz_serial) {
        this.cz_serial = cz_serial;
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

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public Date getCz_time() {
        return cz_time;
    }

    public void setCz_time(Date cz_time) {
        this.cz_time = cz_time;
    }

    public Integer getWorkerid() {
        return workerid;
    }

    public void setWorkerid(Integer workerid) {
        this.workerid = workerid;
    }

    public Integer getRemovetag() {
        return removetag;
    }

    public void setRemovetag(Integer removetag) {
        this.removetag = removetag;
    }
}

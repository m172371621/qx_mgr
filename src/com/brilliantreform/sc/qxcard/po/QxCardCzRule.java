package com.brilliantreform.sc.qxcard.po;

import java.io.Serializable;

/**
 * 区享卡充值优惠规则
 * */
public class QxCardCzRule implements Serializable {
    private Integer objid;
    private Integer card_value;
    private Double rest_value;
    private Integer community_id;

    public QxCardCzRule() {}

    public QxCardCzRule(Integer card_value, Double rest_value, Integer community_id) {
        this.card_value = card_value;
        this.rest_value = rest_value;
        this.community_id = community_id;
    }

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getCard_value() {
        return card_value;
    }

    public void setCard_value(Integer card_value) {
        this.card_value = card_value;
    }

    public Double getRest_value() {
        return rest_value;
    }

    public void setRest_value(Double rest_value) {
        this.rest_value = rest_value;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }
}

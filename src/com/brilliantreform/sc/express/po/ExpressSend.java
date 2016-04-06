package com.brilliantreform.sc.express.po;

import java.util.Date;

/**
 * 寄件信息
 * */
public class ExpressSend {

	private Integer objid;
    private String express_no;
    private Integer express_com;
    private String name;
    private String phone;
    private Double express_price;
    private Integer community_id;
    private String remark;
    private Date send_time;
    private Integer removetag;

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public String getExpress_no() {
        return express_no;
    }

    public Integer getExpress_com() {
        return express_com;
    }

    public void setExpress_com(Integer express_com) {
        this.express_com = express_com;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getExpress_price() {
        return express_price;
    }

    public void setExpress_price(Double express_price) {
        this.express_price = express_price;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public Integer getRemovetag() {
        return removetag;
    }

    public void setRemovetag(Integer removetag) {
        this.removetag = removetag;
    }
}

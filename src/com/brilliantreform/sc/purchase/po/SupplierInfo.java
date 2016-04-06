package com.brilliantreform.sc.purchase.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 供货商基本信息
 */
public class SupplierInfo implements Serializable {
    private Integer supplier_info_id;
    private String supplier_info_name;
    private String supplier_info_addr;
    private Integer supply_province;
    private Integer supply_city;
    private Integer supply_area;
    private String contact_name;
    private String contact_phone;
    private Integer removetag;
    private Date createTime;

    public Integer getSupplier_info_id() {
        return supplier_info_id;
    }

    public void setSupplier_info_id(Integer supplier_info_id) {
        this.supplier_info_id = supplier_info_id;
    }

    public String getSupplier_info_name() {
        return supplier_info_name;
    }

    public void setSupplier_info_name(String supplier_info_name) {
        this.supplier_info_name = supplier_info_name;
    }

    public String getSupplier_info_addr() {
        return supplier_info_addr;
    }

    public void setSupplier_info_addr(String supplier_info_addr) {
        this.supplier_info_addr = supplier_info_addr;
    }

    public Integer getSupply_province() {
        return supply_province;
    }

    public void setSupply_province(Integer supply_province) {
        this.supply_province = supply_province;
    }

    public Integer getSupply_city() {
        return supply_city;
    }

    public void setSupply_city(Integer supply_city) {
        this.supply_city = supply_city;
    }

    public Integer getSupply_area() {
        return supply_area;
    }

    public void setSupply_area(Integer supply_area) {
        this.supply_area = supply_area;
    }

    public Integer getRemovetag() {
        return removetag;
    }

    public void setRemovetag(Integer removetag) {
        this.removetag = removetag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }
}

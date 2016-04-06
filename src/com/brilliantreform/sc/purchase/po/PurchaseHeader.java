package com.brilliantreform.sc.purchase.po;

import java.util.Date;

public class PurchaseHeader {
	
	private Integer purchase_header_id;//
	private String purchase_header_no;//
	private Integer createby;//申请人
	private Double purchase_price;//总额
	private Double purchase_real_price;//实际总额
	private Integer createbycid;//申请人所在小区
	private String remark;//备注说明
	private Integer status;//状态 1=审核中 2=确认中 3=已确认
	private Date createTime;//更新时间
	
	
	public Double getPurchase_real_price() {
		return purchase_real_price;
	}
	public void setPurchase_real_price(Double purchaseRealPrice) {
		purchase_real_price = purchaseRealPrice;
	}
	public Integer getPurchase_header_id() {
		return purchase_header_id;
	}
	public void setPurchase_header_id(Integer purchaseHeaderId) {
		purchase_header_id = purchaseHeaderId;
	}
	public String getPurchase_header_no() {
		return purchase_header_no;
	}
	public void setPurchase_header_no(String purchaseHeaderNo) {
		purchase_header_no = purchaseHeaderNo;
	}
	public Integer getCreateby() {
		return createby;
	}
	public void setCreateby(Integer createby) {
		this.createby = createby;
	}
	public Double getPurchase_price() {
		return purchase_price;
	}
	public void setPurchase_price(Double purchasePrice) {
		purchase_price = purchasePrice;
	}
	public Integer getCreatebycid() {
		return createbycid;
	}
	public void setCreatebycid(Integer createbycid) {
		this.createbycid = createbycid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}

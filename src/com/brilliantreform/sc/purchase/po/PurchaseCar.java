package com.brilliantreform.sc.purchase.po;

import java.util.Date;

public class PurchaseCar {

	private Integer purchase_cart_id;//
	private Integer createby;//申请人
	private Integer product_id;//商品ID
	private String product_name;//商品名称
	private Double product_amount;//数量
	private Double product_price;//单价
	private String remark;//备注说明
	private Date createTime;//更新时间
	private Integer createbycid;//申请人所在小区
	
	
	public Integer getCreatebycid() {
		return createbycid;
	}
	public void setCreatebycid(Integer createbycid) {
		this.createbycid = createbycid;
	}
	public Integer getPurchase_cart_id() {
		return purchase_cart_id;
	}
	public void setPurchase_cart_id(Integer purchaseCartId) {
		purchase_cart_id = purchaseCartId;
	}
	public Integer getCreateby() {
		return createby;
	}
	public void setCreateby(Integer createby) {
		this.createby = createby;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String productName) {
		product_name = productName;
	}
	public Double getProduct_amount() {
		return product_amount;
	}
	public void setProduct_amount(Double productAmount) {
		product_amount = productAmount;
	}
	public Double getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Double productPrice) {
		product_price = productPrice;
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
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer productId) {
		product_id = productId;
	}
	
	
}

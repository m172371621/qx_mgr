package com.brilliantreform.sc.purchase.po;

public class PurchaseDetail {
	
	private Integer purchase_detail_id;//
	private Integer purchase_header_id;//汇总ID
	private Integer product_id;//商品ID
	private String product_name;//商品名称
	private Double product_amount;//数量
	private Double product_real_amount;	//实际数量
	private Double product_price;//预计进价
	private Double product_real_price;//实际进价
	private Integer status;	//状态
	private String remark;//备注说明
	private Integer supplier_id;	//供货商id
	
	
	public Double getProduct_real_price() {
		return product_real_price;
	}
	public void setProduct_real_price(Double productRealPrice) {
		product_real_price = productRealPrice;
	}
	public Integer getPurchase_detail_id() {
		return purchase_detail_id;
	}
	public void setPurchase_detail_id(Integer purchaseDetailId) {
		purchase_detail_id = purchaseDetailId;
	}
	public Integer getPurchase_header_id() {
		return purchase_header_id;
	}
	public void setPurchase_header_id(Integer purchaseHeaderId) {
		purchase_header_id = purchaseHeaderId;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer productId) {
		product_id = productId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}

	public Double getProduct_real_amount() {
		return product_real_amount;
	}

	public void setProduct_real_amount(Double product_real_amount) {
		this.product_real_amount = product_real_amount;
	}
}

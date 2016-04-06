package com.brilliantreform.sc.saleplugins.po;

public class PwsearchBean {
	private Integer user_id;
	private Integer produc_tid;
	private String product_name;
	private String product_prict;
	private String unit;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer userId) {
		user_id = userId;
	}

	public Integer getProduc_tid() {
		return produc_tid;
	}

	public void setProduc_tid(Integer producTid) {
		produc_tid = producTid;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String productName) {
		product_name = productName;
	}

	public String getProduct_prict() {
		return product_prict;
	}

	public void setProduct_prict(String productPrict) {
		product_prict = productPrict;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		StringBuilder builder  = new  StringBuilder();
		builder.append("PwsearchBean [produc_tid=");
		builder.append(produc_tid);
		builder.append(", product_name=");
		builder.append(product_name);
		builder.append(", product_prict=");
		builder.append(product_prict);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", user_id=");
		builder.append(user_id + "]");
		return builder.toString();
	}
}

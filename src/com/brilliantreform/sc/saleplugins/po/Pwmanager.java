package com.brilliantreform.sc.saleplugins.po;

import java.util.Date;

/**
 * 称重商品管理
 * 
 * @author Lm
 * 
 */
public class Pwmanager {
	private Integer user_id;
	private Integer produc_tid;
	private String username;
	private String phone;
	private String product_name;
	private String product_prict;
	private Integer amount;
	private String createTime;
	private int real_stock_sum;

	public int getReal_stock_sum() {
		return real_stock_sum;
	}

	public void setReal_stock_sum(int realStockSum) {
		real_stock_sum = realStockSum;
	}

	private Integer tags;
	private String price;

	public Integer getTags() {
		return tags;
	}

	public void setTags(Integer tags) {
		this.tags = tags;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

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
		StringBuilder builder = new StringBuilder();
		builder.append("PwsearchBean [produc_tid=");
		builder.append(produc_tid);
		builder.append(", product_name=");
		builder.append(product_name);
		builder.append(", product_prict=");
		builder.append(product_prict);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append(", tags=");
		builder.append(tags);
		builder.append(", price");
		builder.append(price);
		builder.append(", real_stock_sum");
		builder.append(real_stock_sum + "}");
		return builder.toString();
	}
}

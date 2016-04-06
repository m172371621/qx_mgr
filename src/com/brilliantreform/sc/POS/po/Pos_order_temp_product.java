package com.brilliantreform.sc.POS.po;

import java.util.Date;

public class Pos_order_temp_product {
	private int order_temp_product_id; // 'POS暂存明细ID',
	private int order_temp_base_id; // 'POS暂存ID',
	private int product_id; // '商品ID',
	private String product_name; // '商品名称',
	private double product_amount; // '商品数量',
	private double product_price; // '商品价格',
	private Date create_time; // '创建时间',
	private int state; // '状态 1=正常',

	public int getOrder_temp_product_id() {
		return order_temp_product_id;
	}

	public void setOrder_temp_product_id(int orderTempProductId) {
		order_temp_product_id = orderTempProductId;
	}

	public int getOrder_temp_base_id() {
		return order_temp_base_id;
	}

	public void setOrder_temp_base_id(int orderTempBaseId) {
		order_temp_base_id = orderTempBaseId;
	}


	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int productId) {
		product_id = productId;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String productName) {
		product_name = productName;
	}

	public double getProduct_amount() {
		return product_amount;
	}

	public void setProduct_amount(double productAmount) {
		product_amount = productAmount;
	}

	public double getProduct_price() {
		return product_price;
	}

	public void setProduct_price(double productPrice) {
		product_price = productPrice;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Pos_order_temp_product [create_time=" + create_time + ", order_temp_base_id=" + order_temp_base_id + ", order_temp_product_id="
				+ order_temp_product_id + ", product_amount=" + product_amount + ", product_id=" + product_id + ", product_name=" + product_name
				+ ", product_price=" + product_price + ", state=" + state + "]";
	}
}

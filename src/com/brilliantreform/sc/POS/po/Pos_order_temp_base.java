package com.brilliantreform.sc.POS.po;

import java.util.Date;

public class Pos_order_temp_base {
	private int order_temp_base_id;// 'POS暂存ID'
	private int community_id;// '小区ID'
	private double order_price;// '订单总价'
	private String create_by;// '创建人'
	private Date create_time;// '创建时间'
	private String url;// '支付URL'
	private int state;// '状态 1=正常'

	public int getOrder_temp_base_id() {
		return order_temp_base_id;
	}

	public void setOrder_temp_base_id(int orderTempBaseId) {
		order_temp_base_id = orderTempBaseId;
	}

	public int getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(int communityId) {
		community_id = communityId;
	}

	public double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(double orderPrice) {
		order_price = orderPrice;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String createBy) {
		create_by = createBy;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Pos_order_temp_base [community_id=" + community_id + ", create_by=" + create_by + ", create_time=" + create_time + ", order_price="
				+ order_price + ", order_temp_base_id=" + order_temp_base_id + ", state=" + state + ", url=" + url + "]";
	}

}

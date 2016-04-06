package com.brilliantreform.sc.qxcard.po;

import java.sql.Timestamp;

public class QxCardPayLog {
	
	private String order_serial; // varchar(31) NOT NULL,
	private String qxcard_no; // varchar(31) NOT NULL,
	private Float pay_price; // float NOT NULL DEFAULT '0',
	private Integer user_id; // int(11) NOT NULL,
	private Integer status;
	private Timestamp createtime; // timestamp NULL DEFAULT NULL,

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrder_serial() {
		return order_serial;
	}

	public void setOrder_serial(String orderSerial) {
		order_serial = orderSerial;
	}

	public String getQxcard_no() {
		return qxcard_no;
	}

	public void setQxcard_no(String qxcardNo) {
		qxcard_no = qxcardNo;
	}

	public Float getPay_price() {
		return pay_price;
	}

	public void setPay_price(Float payPrice) {
		pay_price = payPrice;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer userId) {
		user_id = userId;
	}


	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QxCardPayLog [createtime=");
		builder.append(createtime);
		builder.append(", order_serial=");
		builder.append(order_serial);
		builder.append(", pay_price=");
		builder.append(pay_price);
		builder.append(", qxcard_no=");
		builder.append(qxcard_no);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append("]");
		return builder.toString();
	}

}

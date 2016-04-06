package com.brilliantreform.sc.qxcard.po;

import java.sql.Timestamp;

public class QxCardLog {
	private Integer oplog_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String qxcard_no; // varchar(31) NOT NULL,
	private String order_serial; // varchar(31) DEFAULT NULL,
	private Double qxcard_balance;
	private Double op_price; // int(11) NOT NULL DEFAULT '0',
	private Integer op_type; // smallint(11) NOT NULL DEFAULT '1001' COMMENT
	// '1001 开卡 1002 激活 1003 冻结 1004 解冻 2001 消费 2002
	// 退款 3001 过期 3002 删除 3003 作废',
	private String op_dec; // varchar(127) DEFAULT NULL,
	private Integer op_result; // tinyint(4) DEFAULT '0',
	private String op_result_dec; // varchar(127) DEFAULT NULL,
	private Integer user_type; // tinyint(4) DEFAULT '1' COMMENT '1 管理员 2 用户 3
	// 系统自动',
	private Integer user_id; 
	private String user_ip; // String(63) DEFAULT NULL,
	private Timestamp createtime; // timestamp NULL DEFAULT NULL,
	private String phone;
	
	

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getOplog_id() {
		return oplog_id;
	}

	public void setOplog_id(Integer oplogId) {
		oplog_id = oplogId;
	}

	public String getQxcard_no() {
		return qxcard_no;
	}

	public void setQxcard_no(String qxcardNo) {
		qxcard_no = qxcardNo;
	}

	public String getOrder_serial() {
		return order_serial;
	}

	public void setOrder_serial(String orderSerial) {
		order_serial = orderSerial;
	}

	public Double getQxcard_balance() {
		return qxcard_balance;
	}

	public void setQxcard_balance(Double qxcardBalance) {
		qxcard_balance = qxcardBalance;
	}

	public Double getOp_price() {
		return op_price;
	}

	public void setOp_price(Double opPrice) {
		op_price = opPrice;
	}

	public Integer getOp_type() {
		return op_type;
	}

	public void setOp_type(Integer opType) {
		op_type = opType;
	}

	public String getOp_dec() {
		return op_dec;
	}

	public void setOp_dec(String opDec) {
		op_dec = opDec;
	}

	public Integer getOp_result() {
		return op_result;
	}

	public void setOp_result(Integer opResult) {
		op_result = opResult;
	}

	public String getOp_result_dec() {
		return op_result_dec;
	}

	public void setOp_result_dec(String opResultDec) {
		op_result_dec = opResultDec;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer userType) {
		user_type = userType;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer userId) {
		user_id = userId;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String userIp) {
		user_ip = userIp;
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
		builder.append("QxCardLog [createtime=");
		builder.append(createtime);
		builder.append(", op_dec=");
		builder.append(op_dec);
		builder.append(", op_price=");
		builder.append(op_price);
		builder.append(", op_result=");
		builder.append(op_result);
		builder.append(", op_result_dec=");
		builder.append(op_result_dec);
		builder.append(", op_type=");
		builder.append(op_type);
		builder.append(", oplog_id=");
		builder.append(oplog_id);
		builder.append(", order_serial=");
		builder.append(order_serial);
		builder.append(", qxcard_balance=");
		builder.append(qxcard_balance);
		builder.append(", qxcard_no=");
		builder.append(qxcard_no);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append(", user_ip=");
		builder.append(user_ip);
		builder.append(", user_type=");
		builder.append(user_type);
		builder.append("]");
		return builder.toString();
	}

}

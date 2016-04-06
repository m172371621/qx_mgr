package com.brilliantreform.sc.qxcard.po;

import java.sql.Timestamp;

public class QxcardReglog {
	
	private Integer id; // int(11) NOT NULL AUTO_INCREMENT,
	private String qxcard_no; // varchar(30) NOT NULL,
	private Integer cid; // int(11) NOT NULL,
	private Integer op_type; // tinyint(4) NOT NULL DEFAULT '1' COMMENT '1 开卡 2
								// 冻结',
	private String custm_name; // varchar(30) DEFAULT NULL,
	private String custm_phone; // varchar(30) DEFAULT NULL,
	private String custm_dec; // varchar(120) DEFAULT NULL,
	private String seller_name; // varchar(30) DEFAULT NULL,
	private String seller_ip; // varchar(30) DEFAULT NULL,
	private String seller_dec; // varchar(120) DEFAULT NULL,
	private Timestamp createtime; // timestamp NULL DEFAULT NULL,

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQxcard_no() {
		return qxcard_no;
	}

	public void setQxcard_no(String qxcardNo) {
		qxcard_no = qxcardNo;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getOp_type() {
		return op_type;
	}

	public void setOp_type(Integer opType) {
		op_type = opType;
	}

	public String getCustm_name() {
		return custm_name;
	}

	public void setCustm_name(String custmName) {
		custm_name = custmName;
	}

	public String getCustm_phone() {
		return custm_phone;
	}

	public void setCustm_phone(String custmPhone) {
		custm_phone = custmPhone;
	}

	public String getCustm_dec() {
		return custm_dec;
	}

	public void setCustm_dec(String custmDec) {
		custm_dec = custmDec;
	}

	public String getSeller_name() {
		return seller_name;
	}

	public void setSeller_name(String sellerName) {
		seller_name = sellerName;
	}

	public String getSeller_ip() {
		return seller_ip;
	}

	public void setSeller_ip(String sellerIp) {
		seller_ip = sellerIp;
	}

	public String getSeller_dec() {
		return seller_dec;
	}

	public void setSeller_dec(String sellerDec) {
		seller_dec = sellerDec;
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
		builder.append("QxcardReglog [cid=");
		builder.append(cid);
		builder.append(", createtime=");
		builder.append(createtime);
		builder.append(", custm_dec=");
		builder.append(custm_dec);
		builder.append(", custm_name=");
		builder.append(custm_name);
		builder.append(", custm_phone=");
		builder.append(custm_phone);
		builder.append(", id=");
		builder.append(id);
		builder.append(", op_type=");
		builder.append(op_type);
		builder.append(", qxcard_no=");
		builder.append(qxcard_no);
		builder.append(", seller_dec=");
		builder.append(seller_dec);
		builder.append(", seller_ip=");
		builder.append(seller_ip);
		builder.append(", seller_name=");
		builder.append(seller_name);
		builder.append("]");
		return builder.toString();
	}

}

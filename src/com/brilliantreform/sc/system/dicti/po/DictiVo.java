package com.brilliantreform.sc.system.dicti.po;

import java.sql.Timestamp;

public class DictiVo {

	private Integer id; // int(11) NOT NULL AUTO_INCREMENT,
	private Integer type_id; // int(11) NOT NULL,
	private String name; // varchar(63) DEFAULT NULL,
	private Integer value; // varchar(63) DEFAULT NULL,
	private String dec; // varchar(127) DEFAULT NULL,
	private Integer i_order; // int(11) DEFAULT NULL,
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DictiVo [createTime=");
		builder.append(createTime);
		builder.append(", name=");
		builder.append(name);
		builder.append(", dec=");
		builder.append(dec);
		builder.append(", i_order=");
		builder.append(i_order);
		builder.append(", id=");
		builder.append(id);
		builder.append(", type_id=");
		builder.append(type_id);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType_id() {
		return type_id;
	}

	public void setType_id(Integer typeId) {
		type_id = typeId;
	}

	public Integer getValue() {
		return value;
	}

	public String getDec() {
		return dec;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public Integer getI_order() {
		return i_order;
	}

	public void setI_order(Integer iOrder) {
		i_order = iOrder;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
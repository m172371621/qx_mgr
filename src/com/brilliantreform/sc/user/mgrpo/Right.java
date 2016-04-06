package com.brilliantreform.sc.user.mgrpo;

import java.sql.Timestamp;

public class Right {
	private Integer right_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String right_name; // varchar(31) DEFAULT NULL,
	private String right_dec; // varchar(127) DEFAULT NULL,
	private Integer type; // int(11) DEFAULT NULL,
	private String uri; // varchar(31) NOT NULL,
	private String method; // varchar(31) DEFAULT NULL,
	private String option; // varchar(31) DEFAULT NULL,
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,

	public Integer getRight_id() {
		return right_id;
	}

	public void setRight_id(Integer rightId) {
		right_id = rightId;
	}

	public String getRight_name() {
		return right_name;
	}

	public void setRight_name(String rightName) {
		right_name = rightName;
	}

	public String getRight_dec() {
		return right_dec;
	}

	public void setRight_dec(String rightDec) {
		right_dec = rightDec;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Right [createTime=");
		builder.append(createTime);
		builder.append(", method=");
		builder.append(method);
		builder.append(", option=");
		builder.append(option);
		builder.append(", right_dec=");
		builder.append(right_dec);
		builder.append(", right_id=");
		builder.append(right_id);
		builder.append(", right_name=");
		builder.append(right_name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", uri=");
		builder.append(uri);
		builder.append("]");
		return builder.toString();
	}
}

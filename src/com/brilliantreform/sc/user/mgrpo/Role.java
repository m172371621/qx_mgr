package com.brilliantreform.sc.user.mgrpo;

import java.sql.Timestamp;

public class Role {
	private Integer role_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String role_name; // varchar(31) DEFAULT NULL,
	private String role_dec; // varchar(127) DEFAULT NULL,
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer roleId) {
		role_id = roleId;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String roleName) {
		role_name = roleName;
	}

	public String getRole_dec() {
		return role_dec;
	}

	public void setRole_dec(String roleDec) {
		role_dec = roleDec;
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
		builder.append("Role [createTime=");
		builder.append(createTime);
		builder.append(", role_dec=");
		builder.append(role_dec);
		builder.append(", role_id=");
		builder.append(role_id);
		builder.append(", role_name=");
		builder.append(role_name);
		builder.append("]");
		return builder.toString();
	}

}

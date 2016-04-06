package com.brilliantreform.sc.active.po;

import java.sql.Timestamp;

public class SignPrize {
	 private Integer sign_id;  //  int(11) NOT NULL,
	 private Integer prize_id;  //  int(11) DEFAULT NULL,
	 private Integer prize_name;  //  int(11) DEFAULT NULL,
	 private String prize_url;  //  varchar(255) DEFAULT NULL,
	 private Integer user_id;  //  int(11) DEFAULT NULL,
	 private Integer status;  //  int(11) DEFAULT NULL,
	 private Timestamp receivetime;  //  timestamp NULL DEFAULT NULL,
	 private Timestamp createtime;  //  timestamp NULL DEFAULT NULL,
	public Integer getSign_id() {
		return sign_id;
	}
	public void setSign_id(Integer signId) {
		sign_id = signId;
	}
	public Integer getPrize_id() {
		return prize_id;
	}
	public void setPrize_id(Integer prizeId) {
		prize_id = prizeId;
	}
	public Integer getPrize_name() {
		return prize_name;
	}
	public void setPrize_name(Integer prizeName) {
		prize_name = prizeName;
	}
	public String getPrize_url() {
		return prize_url;
	}
	public void setPrize_url(String prizeUrl) {
		prize_url = prizeUrl;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer userId) {
		user_id = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getReceivetime() {
		return receivetime;
	}
	public void setReceivetime(Timestamp receivetime) {
		this.receivetime = receivetime;
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
		builder.append("SignPrize [createtime=");
		builder.append(createtime);
		builder.append(", prize_id=");
		builder.append(prize_id);
		builder.append(", prize_name=");
		builder.append(prize_name);
		builder.append(", prize_url=");
		builder.append(prize_url);
		builder.append(", receivetime=");
		builder.append(receivetime);
		builder.append(", sign_id=");
		builder.append(sign_id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append("]");
		return builder.toString();
	}
	 
	 
}

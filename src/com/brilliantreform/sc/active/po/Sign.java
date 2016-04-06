package com.brilliantreform.sc.active.po;

import java.sql.Timestamp;

public class Sign {
	
	 private Integer sign_id;  //     int(11) NOT NULL AUTO_INCREMENT,
	 private Integer user_id;  //     int(11) DEFAULT NULL,
	 private Timestamp sign_date;  //     timestamp NULL DEFAULT NULL,
	 private Integer sign_times;  //     int(11) DEFAULT NULL,
	 private Integer max_sign_times;  //     int(11) DEFAULT NULL,
	 
	public Integer getSign_id() {
		return sign_id;
	}
	public void setSign_id(Integer signId) {
		sign_id = signId;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer userId) {
		user_id = userId;
	}
	public Timestamp getSign_date() {
		return sign_date;
	}
	public void setSign_date(Timestamp signDate) {
		sign_date = signDate;
	}
	public Integer getSign_times() {
		return sign_times;
	}
	public void setSign_times(Integer signTimes) {
		sign_times = signTimes;
	}
	public Integer getMax_sign_times() {
		return max_sign_times;
	}
	public void setMax_sign_times(Integer maxSignTimes) {
		max_sign_times = maxSignTimes;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sign [max_sign_times=");
		builder.append(max_sign_times);
		builder.append(", sign_date=");
		builder.append(sign_date);
		builder.append(", sign_id=");
		builder.append(sign_id);
		builder.append(", sign_times=");
		builder.append(sign_times);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append("]");
		return builder.toString();
	}
	
	
	 
}

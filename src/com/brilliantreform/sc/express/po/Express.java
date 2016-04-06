package com.brilliantreform.sc.express.po;

import java.util.Date;

public class Express {

	private Integer express_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String express_com; // varchar(15) DEFAULT NULL,
	private String user_phone; // varchar(31) DEFAULT NULL,
	private String express_no; // varchar(63) DEFAULT NULL,
	private String express_info; // varchar(63) DEFAULT NULL, 快递单号
	private Integer user_type; // int(11) DEFAULT '1' COMMENT '1 非app用户 2
								// 是app用户',
	private Integer community_id; // int(11) NOT NULL,
	private Integer status; // int(11) DEFAULT '1' COMMENT '1 未签收 2 已签收',
	private Integer sendflag; // int(11) DEFAULT '1' COMMENT '1 未通知 2 已通知',
	private Date sign_time; // timestamp NULL DEFAULT NULL,
	private Date arrival_time; // timestamp NULL DEFAULT NULL,

	
	public String getExpress_info() {
		return express_info;
	}

	public void setExpress_info(String expressInfo) {
		express_info = expressInfo;
	}

	public Integer getSendflag() {
		return sendflag;
	}

	public void setSendflag(Integer sendflag) {
		this.sendflag = sendflag;
	}

	public Integer getExpress_id() {
		return express_id;
	}

	public void setExpress_id(Integer expressId) {
		express_id = expressId;
	}

	public String getExpress_com() {
		return express_com;
	}

	public void setExpress_com(String expressCom) {
		express_com = expressCom;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String userPhone) {
		user_phone = userPhone;
	}

	public String getExpress_no() {
		return express_no;
	}

	public void setExpress_no(String expressNo) {
		express_no = expressNo;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer userType) {
		user_type = userType;
	}

	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getSign_time() {
		return sign_time;
	}

	public void setSign_time(Date signTime) {
		sign_time = signTime;
	}

	public Date getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(Date arrivalTime) {
		arrival_time = arrivalTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Express [arrival_time=");
		builder.append(arrival_time);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", express_com=");
		builder.append(express_com);
		builder.append(", express_id=");
		builder.append(express_id);
		builder.append(", express_no=");
		builder.append(express_no);
		builder.append(", sign_time=");
		builder.append(sign_time);
		builder.append(", status=");
		builder.append(status);
		builder.append(", user_phone=");
		builder.append(user_phone);
		builder.append(", user_type=");
		builder.append(user_type);
		builder.append("]");
		return builder.toString();
	}

}

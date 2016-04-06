package com.brilliantreform.sc.circle.po;

import java.sql.Timestamp;

public class CircleFriend {
	private Integer me_id; // int(11) NOT NULL,
	private Integer user_id; // int(11) NOT NULL,
	private Integer status; // int(11) DEFAULT '1',
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,
	
	private String user_name;
	private String user_pic;
	private String user_sex; //F M
	private String user_sign;
	private String user_community;
	private Integer relation_type; // int(11) DEFAULT '1',
	
	public Integer getRelation_type() {
		return relation_type;
	}
	public void setRelation_type(Integer relationType) {
		relation_type = relationType;
	}
	public Integer getMe_id() {
		return me_id;
	}
	public void setMe_id(Integer meId) {
		me_id = meId;
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getUser_pic() {
		return user_pic;
	}
	public void setUser_pic(String userPic) {
		user_pic = userPic;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String userSex) {
		user_sex = userSex;
	}
	public String getUser_sign() {
		return user_sign;
	}
	public void setUser_sign(String userSign) {
		user_sign = userSign;
	}
	public String getUser_community() {
		return user_community;
	}
	public void setUser_community(String userCommunity) {
		user_community = userCommunity;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CircleFried [createTime=");
		builder.append(createTime);
		builder.append(", me_id=");
		builder.append(me_id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", user_community=");
		builder.append(user_community);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append(", user_name=");
		builder.append(user_name);
		builder.append(", user_pic=");
		builder.append(user_pic);
		builder.append(", user_sex=");
		builder.append(user_sex);
		builder.append(", user_sign=");
		builder.append(user_sign);
		builder.append("]");
		return builder.toString();
	}

	
}

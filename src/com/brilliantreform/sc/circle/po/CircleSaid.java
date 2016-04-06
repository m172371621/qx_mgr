package com.brilliantreform.sc.circle.po;

import java.sql.Timestamp;
import java.util.Arrays;

public class CircleSaid {
	private Integer said_id; // int(11) NOT NULL AUTO_INCREMENT,
	private Integer user_id; // int(11) NOT NULL,
	private String said_content; // varchar(255) DEFAULT NULL,
	private Integer status; // int(11) DEFAULT NULL,
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,
	

	private String user_name;
	private String user_pic;
	private String said_date;
	private String said_pics;
	private String[] said_thumbnail;
	private String user_community;
	
	
	public Integer getSaid_id() {
		return said_id;
	}
	public void setSaid_id(Integer saidId) {
		said_id = saidId;
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
	public String getSaid_date() {
		return said_date;
	}
	public void setSaid_date(String saidDate) {
		said_date = saidDate;
	}
	public String getSaid_content() {
		return said_content;
	}
	public void setSaid_content(String saidContent) {
		said_content = saidContent;
	}

	public String getSaid_pics() {
		return said_pics;
	}
	public void setSaid_pics(String saidPics) {
		said_pics = saidPics;
	}
	public String[] getSaid_thumbnail() {
		return said_thumbnail;
	}
	public void setSaid_thumbnail(String[] saidThumbnail) {
		said_thumbnail = saidThumbnail;
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
		builder.append("CircleSaid [createTime=");
		builder.append(createTime);
		builder.append(", said_content=");
		builder.append(said_content);
		builder.append(", said_date=");
		builder.append(said_date);
		builder.append(", said_id=");
		builder.append(said_id);
		builder.append(", said_pics=");
		builder.append(said_pics);
		builder.append(", said_thumbnail=");
		builder.append(Arrays.toString(said_thumbnail));
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
		builder.append("]");
		return builder.toString();
	}
}

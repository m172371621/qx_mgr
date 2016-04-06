package com.brilliantreform.sc.task.po;

import java.sql.Timestamp;
import java.util.List;

public class TaskProperty {
	private Integer task_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String task_title; // varchar(128) NOT NULL,
	private String task_dec; // varchar(512) DEFAULT NULL,
	private String address; // varchar(256) DEFAULT NULL,
	private Integer task_status; // tinyint(4) NOT NULL DEFAULT '1' COMMENT
	// '1为未接收 2为已接收未完成 3为已接收已完成',
	private Integer pics; // tinyint(4) NOT NULL DEFAULT '0' COMMENT '0为无图片
	// 1为有图片',
	private String phone; // varchar(16) NOT NULL,
	private Integer send_user; // int(11) NOT NULL,
	private String send_user_name;
	private Timestamp updateTime; // timestamp NOT NULL DEFAULT
	// CURRENT_TIMESTAMP ON UPDATE
	// CURRENT_TIMESTAMP,
	private Timestamp publish_time; // timestamp NOT NULL DEFAULT '1980-01-01
	// 00:00:00',
	private Timestamp complete_time; // timestamp NOT NULL DEFAULT '1980-01-01
	// 00:00:00',
	private List<String> photo;
	private List<String> audio;

	private String send_avatar;
	private Integer community_id;
	private String community;

	
	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getSend_avatar() {
		return send_avatar;
	}

	public void setSend_avatar(String sendAvatar) {
		send_avatar = sendAvatar;
	}

	public String getSend_user_name() {
		return send_user_name;
	}

	public void setSend_user_name(String sendUserName) {
		send_user_name = sendUserName;
	}

	public List<String> getPhoto() {
		return photo;
	}

	public void setPhoto(List<String> photo) {
		this.photo = photo;
	}

	public List<String> getAudio() {
		return audio;
	}

	public void setAudio(List<String> audio) {
		this.audio = audio;
	}

	public Integer getTask_id() {
		return task_id;
	}

	public void setTask_id(Integer taskId) {
		task_id = taskId;
	}

	public String getTask_title() {
		return task_title;
	}

	public void setTask_title(String taskTitle) {
		task_title = taskTitle;
	}

	public String getTask_dec() {
		return task_dec;
	}

	public void setTask_dec(String taskDec) {
		task_dec = taskDec;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTask_status() {
		return task_status;
	}

	public void setTask_status(Integer taskStatus) {
		task_status = taskStatus;
	}

	public Integer getPics() {
		return pics;
	}

	public void setPics(Integer pics) {
		this.pics = pics;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSend_user() {
		return send_user;
	}

	public void setSend_user(Integer sendUser) {
		send_user = sendUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(Timestamp publishTime) {
		publish_time = publishTime;
	}

	public Timestamp getComplete_time() {
		return complete_time;
	}

	public void setComplete_time(Timestamp completeTime) {
		complete_time = completeTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskProperty [address=");
		builder.append(address);
		builder.append(", audio=");
		builder.append(audio);
		builder.append(", community=");
		builder.append(community);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", complete_time=");
		builder.append(complete_time);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", photo=");
		builder.append(photo);
		builder.append(", pics=");
		builder.append(pics);
		builder.append(", publish_time=");
		builder.append(publish_time);
		builder.append(", send_avatar=");
		builder.append(send_avatar);
		builder.append(", send_user=");
		builder.append(send_user);
		builder.append(", send_user_name=");
		builder.append(send_user_name);
		builder.append(", task_dec=");
		builder.append(task_dec);
		builder.append(", task_id=");
		builder.append(task_id);
		builder.append(", task_status=");
		builder.append(task_status);
		builder.append(", task_title=");
		builder.append(task_title);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append("]");
		return builder.toString();
	}

}

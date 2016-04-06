package com.brilliantreform.sc.task.po;

import java.sql.Timestamp;
import java.util.List;

public class Task {
	private Integer task_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String task_title; // varchar(128) NOT NULL,
	private String task_dec; // varchar(512) DEFAULT NULL,
	private Integer send_user_id; // int(11) NOT NULL,
	private Integer task_type; // int(11) NOT NULL, 1 普通任务 2 咨询服务类任务
	private Integer consultation_id; // int(11) DEFAULT NULL COMMENT '服务ID
	// 如果有代表是服务类任务',

	private Integer tip; // int(11) DEFAULT NULL,小费
	private String receive_addr; // varchar(256) DEFAULT NULL,
	private String target_user_type; // 1为其他小区用户 2为专业服务员，此处可复选，用|隔开',
	private Integer task_status; // 任务状态：1 未被接收 2接收未完成 3已完成待评价 4发送者已评价 5任务过期 6接收者已评 7双方已评,
	private Integer receive_user_id; // int(11) DEFAULT NULL,
	private String phone; // varchar(16) NOT NULL,
	private Timestamp publish_time;
	private Timestamp expired_time;
	private Timestamp complete_time;
	
	private Timestamp updateTime;

	private List<String> thumbnail;
	private List<String> photo;
	private List<String> audio;
	
	private String send_user_name;
	
	private String receive_user_name;
	
	private String send_avatar;
	
	private String community;
	
	

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

	public String getReceive_user_name() {
		return receive_user_name;
	}

	public void setReceive_user_name(String receiveUserName) {
		receive_user_name = receiveUserName;
	}

	public Timestamp getComplete_time() {
		return complete_time;
	}

	public void setComplete_time(Timestamp completeTime) {
		complete_time = completeTime;
	}

	public Integer getTask_type() {
		return task_type;
	}

	public void setTask_type(Integer taskType) {
		task_type = taskType;
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

	public Integer getSend_user_id() {
		return send_user_id;
	}

	public void setSend_user_id(Integer sendUserId) {
		send_user_id = sendUserId;
	}

	public Integer getConsultation_id() {
		return consultation_id;
	}

	public void setConsultation_id(Integer consultationId) {
		consultation_id = consultationId;
	}

	public Integer getTip() {
		return tip;
	}

	public void setTip(Integer tip) {
		this.tip = tip;
	}

	public String getReceive_addr() {
		return receive_addr;
	}

	public void setReceive_addr(String receiveAddr) {
		receive_addr = receiveAddr;
	}

	public String getTarget_user_type() {
		return target_user_type;
	}

	public void setTarget_user_type(String targetUserType) {
		target_user_type = targetUserType;
	}

	public Integer getTask_status() {
		return task_status;
	}

	public void setTask_status(Integer taskStatus) {
		task_status = taskStatus;
	}

	public Integer getReceive_user_id() {
		return receive_user_id;
	}

	public void setReceive_user_id(Integer receiveUserId) {
		receive_user_id = receiveUserId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(Timestamp publishTime) {
		publish_time = publishTime;
	}

	public Timestamp getExpired_time() {
		return expired_time;
	}

	public void setExpired_time(Timestamp expiredTime) {
		expired_time = expiredTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public List<String> getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(List<String> thumbnail) {
		this.thumbnail = thumbnail;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [audio=");
		builder.append(audio);
		builder.append(", complete_time=");
		builder.append(complete_time);
		builder.append(", consultation_id=");
		builder.append(consultation_id);
		builder.append(", expired_time=");
		builder.append(expired_time);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", photo=");
		builder.append(photo);
		builder.append(", publish_time=");
		builder.append(publish_time);
		builder.append(", receive_addr=");
		builder.append(receive_addr);
		builder.append(", receive_user_id=");
		builder.append(receive_user_id);
		builder.append(", receive_user_name=");
		builder.append(receive_user_name);
		builder.append(", send_user_id=");
		builder.append(send_user_id);
		builder.append(", send_user_name=");
		builder.append(send_user_name);
		builder.append(", target_user_type=");
		builder.append(target_user_type);
		builder.append(", task_dec=");
		builder.append(task_dec);
		builder.append(", task_id=");
		builder.append(task_id);
		builder.append(", task_status=");
		builder.append(task_status);
		builder.append(", task_title=");
		builder.append(task_title);
		builder.append(", task_type=");
		builder.append(task_type);
		builder.append(", thumbnail=");
		builder.append(thumbnail);
		builder.append(", tip=");
		builder.append(tip);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append("]");
		return builder.toString();
	}

}

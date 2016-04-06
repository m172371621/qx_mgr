package com.brilliantreform.sc.task.po;

import java.sql.Timestamp;

public class TaskMedia {

	private Integer media_id; // int(11) NOT NULL AUTO_INCREMENT,
	private Integer task_type; // 1 跑腿任務 2 物業任務
	private Integer task_id; // int(11) NOT NULL,
	private String media_url; // varchar(256) NOT NULL,
	private Integer type; // tinyint(4) NOT NULL DEFAULT '1' COMMENT '1
	// thumbnail 2 photo 3 audio',
	private Integer media_order; // int(11) NOT NULL DEFAULT '0',
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,

	public Integer getTask_type() {
		return task_type;
	}

	public void setTask_type(Integer taskType) {
		task_type = taskType;
	}

	public Integer getMedia_id() {
		return media_id;
	}

	public void setMedia_id(Integer mediaId) {
		media_id = mediaId;
	}

	public Integer getTask_id() {
		return task_id;
	}

	public void setTask_id(Integer taskId) {
		task_id = taskId;
	}

	public String getMedia_url() {
		return media_url;
	}

	public void setMedia_url(String mediaUrl) {
		media_url = mediaUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMedia_order() {
		return media_order;
	}

	public void setMedia_order(Integer mediaOrder) {
		media_order = mediaOrder;
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
		builder.append("TaskMedia [createTime=");
		builder.append(createTime);
		builder.append(", media_id=");
		builder.append(media_id);
		builder.append(", media_order=");
		builder.append(media_order);
		builder.append(", media_url=");
		builder.append(media_url);
		builder.append(", task_id=");
		builder.append(task_id);
		builder.append(", task_type=");
		builder.append(task_type);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}

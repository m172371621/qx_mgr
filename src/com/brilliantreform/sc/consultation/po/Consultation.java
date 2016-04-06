package com.brilliantreform.sc.consultation.po;

import java.sql.Timestamp;

public class Consultation {
	private Integer consultation_id;
	private Integer service_id;
	private Integer consultation_type;
	private String name;
	private String picture;
	private String thumbnail;
	private String description;
	private Integer comment_level;
	private Integer comment_count;
	private Integer status;
	private Timestamp updateTime;
	private Timestamp createTime;



	public Integer getConsultation_id() {
		return consultation_id;
	}

	public void setConsultation_id(Integer consultationId) {
		consultation_id = consultationId;
	}

	public Integer getService_id() {
		return service_id;
	}

	public void setService_id(Integer serviceId) {
		service_id = serviceId;
	}

	public Integer getConsultation_type() {
		return consultation_type;
	}

	public void setConsultation_type(Integer consultationType) {
		consultation_type = consultationType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getComment_level() {
		return comment_level;
	}

	public void setComment_level(Integer commentLevel) {
		comment_level = commentLevel;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer commentCount) {
		comment_count = commentCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Consultation [comment_count=");
		builder.append(comment_count);
		builder.append(", comment_level=");
		builder.append(comment_level);
		builder.append(", consultation_id=");
		builder.append(consultation_id);
		builder.append(", consultation_type=");
		builder.append(consultation_type);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", description=");
		builder.append(description);
		builder.append(", name=");
		builder.append(name);
		builder.append(", picture=");
		builder.append(picture);
		builder.append(", service_id=");
		builder.append(service_id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", thumbnail=");
		builder.append(thumbnail);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append("]");
		return builder.toString();
	}



}

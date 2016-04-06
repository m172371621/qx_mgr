package com.brilliantreform.sc.circle.po;

import java.sql.Timestamp;

public class CircleMedia {
	 private Integer media_id; // int(11) NOT NULL AUTO_INCREMENT,
	 private Integer said_id; // int(11) NOT NULL,
	 private String media_url; // varchar(255) NOT NULL,
	 private Integer order; // int(11) DEFAULT '0',
	 private String media_type; // varchar(16) DEFAULT NULL,
	 private Timestamp createTime; // timestamp NULL DEFAULT NULL,
	 
	public Integer getMedia_id() {
		return media_id;
	}
	public void setMedia_id(Integer mediaId) {
		media_id = mediaId;
	}
	public Integer getSaid_id() {
		return said_id;
	}
	public void setSaid_id(Integer saidId) {
		said_id = saidId;
	}
	public String getMedia_url() {
		return media_url;
	}
	public void setMedia_url(String mediaUrl) {
		media_url = mediaUrl;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String mediaType) {
		media_type = mediaType;
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
		builder.append("CircleMedia [createTime=");
		builder.append(createTime);
		builder.append(", media_id=");
		builder.append(media_id);
		builder.append(", media_type=");
		builder.append(media_type);
		builder.append(", media_url=");
		builder.append(media_url);
		builder.append(", order=");
		builder.append(order);
		builder.append(", said_id=");
		builder.append(said_id);
		builder.append("]");
		return builder.toString();
	}
	 
	 
}

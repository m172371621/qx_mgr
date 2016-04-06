package com.brilliantreform.sc.circle.po;

import java.sql.Timestamp;

public class CircleReply {
	private Integer reply_id; // int(11) NOT NULL AUTO_INCREMENT,
	private Integer said_id; // int(11) NOT NULL,
	private Integer comment_user_id; // int(11) NOT NULL,
	private Integer reply_user_id;
	private String comment_content; // int(11) DEFAULT NULL,
	private Integer comment_type; // int(11) NOT NULL DEFAULT '1' COMMENT '1 点赞
									// 2 评论 3 回复评论',
	private Integer status; // int(11) DEFAULT NULL,
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,

	private String comment_user;
	private String reply_user;
	private String comment_date;
	
	
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String commentContent) {
		comment_content = commentContent;
	}
	public Integer getReply_user_id() {
		return reply_user_id;
	}
	public void setReply_user_id(Integer replyUserId) {
		reply_user_id = replyUserId;
	}
	public Integer getReply_id() {
		return reply_id;
	}
	public void setReply_id(Integer replyId) {
		reply_id = replyId;
	}
	public Integer getSaid_id() {
		return said_id;
	}
	public void setSaid_id(Integer saidId) {
		said_id = saidId;
	}
	public Integer getComment_user_id() {
		return comment_user_id;
	}
	public void setComment_user_id(Integer commentUserId) {
		comment_user_id = commentUserId;
	}
	public Integer getComment_type() {
		return comment_type;
	}
	public void setComment_type(Integer commentType) {
		comment_type = commentType;
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
	public String getComment_user() {
		return comment_user;
	}
	public void setComment_user(String commentUser) {
		comment_user = commentUser;
	}
	public String getReply_user() {
		return reply_user;
	}
	public void setReply_user(String replyUser) {
		reply_user = replyUser;
	}
	public String getComment_date() {
		return comment_date;
	}
	public void setComment_date(String commentDate) {
		comment_date = commentDate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CircleReply [comment_content=");
		builder.append(comment_content);
		builder.append(", comment_date=");
		builder.append(comment_date);
		builder.append(", comment_type=");
		builder.append(comment_type);
		builder.append(", comment_user=");
		builder.append(comment_user);
		builder.append(", comment_user_id=");
		builder.append(comment_user_id);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", reply_id=");
		builder.append(reply_id);
		builder.append(", reply_user=");
		builder.append(reply_user);
		builder.append(", reply_user_id=");
		builder.append(reply_user_id);
		builder.append(", said_id=");
		builder.append(said_id);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}

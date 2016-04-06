package com.brilliantreform.sc.comment.po;

import java.sql.Timestamp;

public class CommentServcie {

	private Integer comment_id;
	private Integer comment_user;
	private String comment_username;
	private Integer comment_type; // 1 产品评论 2 咨询服务评论
	private Integer product_id;
	private Integer consultation_id;
	private Integer comment_level;
	private String comment_dec;
	private Timestamp createTime;

	public String getComment_username() {
		return comment_username;
	}

	public void setComment_username(String commentUsername) {
		comment_username = commentUsername;
	}

	public Integer getComment_user() {
		return comment_user;
	}

	public void setComment_user(Integer commentUser) {
		comment_user = commentUser;
	}

	public Integer getComment_id() {
		return comment_id;
	}

	public void setComment_id(Integer commentId) {
		comment_id = commentId;
	}

	public Integer getComment_type() {
		return comment_type;
	}

	public void setComment_type(Integer commentType) {
		comment_type = commentType;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer productId) {
		product_id = productId;
	}

	public Integer getConsultation_id() {
		return consultation_id;
	}

	public void setConsultation_id(Integer consultationId) {
		consultation_id = consultationId;
	}

	public Integer getComment_level() {
		return comment_level;
	}

	public void setComment_level(Integer commentLevel) {
		comment_level = commentLevel;
	}

	public String getComment_dec() {
		return comment_dec;
	}

	public void setComment_dec(String commentDec) {
		comment_dec = commentDec;
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
		builder.append("CommentServcie [comment_dec=");
		builder.append(comment_dec);
		builder.append(", comment_id=");
		builder.append(comment_id);
		builder.append(", comment_level=");
		builder.append(comment_level);
		builder.append(", comment_type=");
		builder.append(comment_type);
		builder.append(", comment_user=");
		builder.append(comment_user);
		builder.append(", comment_username=");
		builder.append(comment_username);
		builder.append(", consultation_id=");
		builder.append(consultation_id);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", product_id=");
		builder.append(product_id);
		builder.append("]");
		return builder.toString();
	}

}

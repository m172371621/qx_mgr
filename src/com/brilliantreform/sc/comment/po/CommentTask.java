package com.brilliantreform.sc.comment.po;

import java.sql.Timestamp;

public class CommentTask {
	private Integer comment_id;
	private Integer task_id;
	private String comment_dec;
	private String audio;
	private String picture;
	private Integer comment_user;
	private Integer comment_level;
	private Integer tig_comment;// 奖励是否如任务描述：1按时给予 2.延时给予 3.没给
	private Integer communication_comment;// 任务交流描述：1 非常愉快2.一般 3.很差劲
	private Integer comment_type; // 1 任务发布者评论 2.任务接收者评论
	private Timestamp createTime;

	public Integer getComment_id() {
		return comment_id;
	}

	public void setComment_id(Integer commentId) {
		comment_id = commentId;
	}

	public Integer getTask_id() {
		return task_id;
	}

	public void setTask_id(Integer taskId) {
		task_id = taskId;
	}

	public String getComment_dec() {
		return comment_dec;
	}

	public void setComment_dec(String commentDec) {
		comment_dec = commentDec;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getComment_user() {
		return comment_user;
	}

	public void setComment_user(Integer commentUser) {
		comment_user = commentUser;
	}

	public Integer getComment_level() {
		return comment_level;
	}

	public void setComment_level(Integer commentLevel) {
		comment_level = commentLevel;
	}

	public Integer getTig_comment() {
		return tig_comment;
	}

	public void setTig_comment(Integer tigComment) {
		tig_comment = tigComment;
	}

	public Integer getCommunication_comment() {
		return communication_comment;
	}

	public void setCommunication_comment(Integer communicationComment) {
		communication_comment = communicationComment;
	}

	public Integer getComment_type() {
		return comment_type;
	}

	public void setComment_type(Integer commentType) {
		comment_type = commentType;
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
		builder.append("CommentTask [audio=");
		builder.append(audio);
		builder.append(", comment_dec=");
		builder.append(comment_dec);
		builder.append(", comment_id=");
		builder.append(comment_id);
		builder.append(", comment_level=");
		builder.append(comment_level);
		builder.append(", comment_type=");
		builder.append(comment_type);
		builder.append(", comment_user=");
		builder.append(comment_user);
		builder.append(", communication_comment=");
		builder.append(communication_comment);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", picture=");
		builder.append(picture);
		builder.append(", task_id=");
		builder.append(task_id);
		builder.append(", tig_comment=");
		builder.append(tig_comment);
		builder.append("]");
		return builder.toString();
	}

}

package com.brilliantreform.sc.task.po;


public class TaskQueryBean {

	private String title;
	private String send_user;
	private String recv_user;
	private String phone;
	private Integer task_status;
	private String start_time;
	private String end_time;
	private Integer task_cid;
	private Integer begin;
	private Integer size;
	

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSend_user() {
		return send_user;
	}

	public void setSend_user(String sendUser) {
		send_user = sendUser;
	}

	public String getRecv_user() {
		return recv_user;
	}

	public void setRecv_user(String recvUser) {
		recv_user = recvUser;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getTask_status() {
		return task_status;
	}

	public void setTask_status(Integer taskStatus) {
		task_status = taskStatus;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String startTime) {
		start_time = startTime;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String endTime) {
		end_time = endTime;
	}

	public Integer getTask_cid() {
		return task_cid;
	}

	public void setTask_cid(Integer taskCid) {
		task_cid = taskCid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskQueryBean [begin=");
		builder.append(begin);
		builder.append(", end_time=");
		builder.append(end_time);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", recv_user=");
		builder.append(recv_user);
		builder.append(", send_user=");
		builder.append(send_user);
		builder.append(", size=");
		builder.append(size);
		builder.append(", start_time=");
		builder.append(start_time);
		builder.append(", task_cid=");
		builder.append(task_cid);
		builder.append(", task_status=");
		builder.append(task_status);
		builder.append(", title=");
		builder.append(title);
		builder.append("]");
		return builder.toString();
	}
}

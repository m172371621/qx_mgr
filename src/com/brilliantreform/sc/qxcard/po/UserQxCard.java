package com.brilliantreform.sc.qxcard.po;

import java.sql.Timestamp;

public class UserQxCard {
	private Integer user_id; // int(11) NOT NULL,
	private String qxcard_no; // varchar(15) NOT NULL,
	private Double qxcard_balance; // varchar(31) NOT NULL,
	private Integer qxcard_value; // int(11) NOT NULL
	private Integer qxcard_status; // int(11) NOT NULL DEFAULT '3' COMMENT
									// '区享卡状态： 1 未制卡 2 未激活 3 正常 4 已使用（余额为0） 5
									// 已过期 9 不可用',
	private Integer disabled;// 1正常  2 已冻结 
	private Timestamp expire_time; // timestamp NULL DEFAULT NULL,
	private Timestamp updatetime; // timestamp NULL DEFAULT CURRENT_TIMESTAMP ON
									// UPDATE CURRENT_TIMESTAMP,
	private Timestamp createtime; // timestamp NULL DEFAULT NULL,

	
	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer userId) {
		user_id = userId;
	}

	public String getQxcard_no() {
		return qxcard_no;
	}

	public void setQxcard_no(String qxcardNo) {
		qxcard_no = qxcardNo;
	}

	public Double getQxcard_balance() {
		return qxcard_balance;
	}

	public void setQxcard_balance(Double qxcardBalance) {
		qxcard_balance = qxcardBalance;
	}

	public Integer getQxcard_value() {
		return qxcard_value;
	}

	public void setQxcard_value(Integer qxcardValue) {
		qxcard_value = qxcardValue;
	}

	public Integer getQxcard_status() {
		return qxcard_status;
	}

	public void setQxcard_status(Integer qxcardStatus) {
		qxcard_status = qxcardStatus;
	}

	public Timestamp getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(Timestamp expireTime) {
		expire_time = expireTime;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserQxCard [createtime=");
		builder.append(createtime);
		builder.append(", expire_time=");
		builder.append(expire_time);
		builder.append(", qxcard_balance=");
		builder.append(qxcard_balance);
		builder.append(", qxcard_no=");
		builder.append(qxcard_no);
		builder.append(", qxcard_status=");
		builder.append(qxcard_status);
		builder.append(", qxcard_value=");
		builder.append(qxcard_value);
		builder.append(", updatetime=");
		builder.append(updatetime);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append(", disabled=");
		builder.append(disabled);
		builder.append("]");
		return builder.toString();
	}

}

package com.brilliantreform.sc.qxcard.po;

import java.sql.Timestamp;

public class QxCard {
	private String qxcard_no; // varchar(15) NOT NULL,
	private String qxcard_cdkey; // varchar(31) NOT NULL,
	private Integer status; // int(11) NOT NULL DEFAULT '1' COMMENT '1 制卡 2 开卡 3
							// 过期 4 作废',
	private Integer disabled;// 1正常  2 已冻结 
	private Integer value; // int(11) NOT NULL DEFAULT '100',
	private Timestamp expire_time; // timestamp NULL DEFAULT NULL,
	private Timestamp createtime; // timestamp NULL DEFAULT NULL,

	 
	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public String getQxcard_no() {
		return qxcard_no;
	}

	public void setQxcard_no(String qxcardNo) {
		qxcard_no = qxcardNo;
	}

	public String getQxcard_cdkey() {
		return qxcard_cdkey;
	}

	public void setQxcard_cdkey(String qxcardCdkey) {
		qxcard_cdkey = qxcardCdkey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Timestamp getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(Timestamp expireTime) {
		expire_time = expireTime;
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
		builder.append("QxCard [createtime=");
		builder.append(createtime);
		builder.append(", expire_time=");
		builder.append(expire_time);
		builder.append(", qxcard_cdkey=");
		builder.append(qxcard_cdkey);
		builder.append(", qxcard_no=");
		builder.append(qxcard_no);
		builder.append(", status=");
		builder.append(status);
		builder.append(", value=");
		builder.append(value);
		builder.append(", disabled=");
		builder.append(disabled);
		builder.append("]");
		return builder.toString();
	}

}

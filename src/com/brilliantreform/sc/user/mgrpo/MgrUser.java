package com.brilliantreform.sc.user.mgrpo;

import java.sql.Timestamp;
import java.util.Date;

public class MgrUser {
	private Integer user_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String loginname; // varchar(32) NOT NULL,
	private String password; // varchar(64) NOT NULL,
	private Integer type; // DEFAULT '1' COMMENT '1为普通用户2为专业用户',
	private String personName; //姓名

	private String phone; // varchar(64) DEFAULT NULL,
	private String email; // varchar(64) DEFAULT NULL,
	private String addr; // varchar(64) DEFAULT NULL,
	private String cname; // varchar(64) DEFAULT NULL,
	private Integer cid;
	private Integer authenticate; // 0 未认证 1已认证2 注销',
	private Date updateTime; // timestamp NOT NULL DEFAULT
	private Date createTime; // timestamp NOT NULL DEFAULT '1980-01-01

    private Integer sub_cid;
	
	private Integer activation; // 新增      0冻结   1解冻

	private Integer community_id; //小区编号
	private String openid; //微信的openid

	private String distri_worker_name;
	private String distri_worker_phone;
	private Integer distri_worker_status;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer community_id) {
		this.community_id = community_id;
	}

	public Integer getActivation() {
		return activation;
	}

	public void setActivation(Integer activation) {
		this.activation = activation;
	}

	public String getPersonName() {
		return personName;
	}
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer userId) {
		user_id = userId;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(Integer authenticate) {
		this.authenticate = authenticate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

    public Integer getSub_cid() {
        return sub_cid;
    }

    public void setSub_cid(Integer sub_cid) {
        this.sub_cid = sub_cid;
    }

	public Integer getDistri_worker_status() {
		return distri_worker_status;
	}

	public String getDistri_worker_name() {
		return distri_worker_name;
	}

	public String getDistri_worker_phone() {
		return distri_worker_phone;
	}

	public void setDistri_worker_name(String distri_worker_name) {
		this.distri_worker_name = distri_worker_name;
	}

	public void setDistri_worker_phone(String distri_worker_phone) {
		this.distri_worker_phone = distri_worker_phone;
	}

	public void setDistri_worker_status(Integer distri_worker_status) {
		this.distri_worker_status = distri_worker_status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MgrUser [addr=");
		builder.append(addr);
		builder.append(", authenticate=");
		builder.append(authenticate);
		builder.append(", cname=");
		builder.append(cname);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", email=");
		builder.append(email);
		builder.append(", loginname=");
		builder.append(loginname);
		builder.append(", password=");
		builder.append(password);
		builder.append(", personName=");
		builder.append(personName);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", type=");
		builder.append(type);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append("]");
		return builder.toString();
	}

}

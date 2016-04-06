package com.brilliantreform.sc.user.mgrpo;

public class UserSearchBean {
	private Integer user_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String loginname; // varchar(32) NOT NULL,
	private String phone; 
	private Integer cid; 
	private Integer my_user_id; // DEFAULT '1' COMMENT '1为普通用户2为专业用户',
	private Integer authenticate;	//0 未认证 1已认证 2 注销

	private Integer begin; // varchar(64) DEFAULT NULL,
	private Integer size; // varchar(64) DEFAULT NULL,

	public Integer getAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(Integer authenticate) {
		this.authenticate = authenticate;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
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
	public Integer getMy_user_id() {
		return my_user_id;
	}
	public void setMy_user_id(Integer myUserId) {
		my_user_id = myUserId;
	}
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserSearchBean [begin=");
		builder.append(begin);
		builder.append(", loginname=");
		builder.append(loginname);
		builder.append(", my_user_id=");
		builder.append(my_user_id);
		builder.append(", size=");
		builder.append(size);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append("]");
		return builder.toString();
	}


}

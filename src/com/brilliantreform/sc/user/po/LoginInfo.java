package com.brilliantreform.sc.user.po;

public class LoginInfo {

	private Integer userId;
	private String loginName;
	private String username;
	private String password;
	private String phone;
	private Integer type; // 1 为普通用户 2 为专业用户
	private String nick;
	private String email;
	private Integer authenticate; // 0 未认证 1 已认证 2 注销

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginInfo [authenticate=");
		builder.append(authenticate);
		builder.append(", email=");
		builder.append(email);
		builder.append(", loginName=");
		builder.append(loginName);
		builder.append(", username=");
		builder.append(username);
		builder.append(", nick=");
		builder.append(nick);
		builder.append(", password=");
		builder.append(password);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", type=");
		builder.append(type);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}
}

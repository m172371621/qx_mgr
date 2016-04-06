package com.brilliantreform.sc.user.po;

public class UserInfo {
	
	private Integer userId;
	private String loginName;
	private String password;
	private String phone;
	private Integer type; // 1 为普通用户 2 为专业用户
	private String nick;
	private String email;
	private Integer authenticate; // 0 未认证 1 已认证 2 注销
	  
	private String username;
	private String avatar;
	private String room;
	private String floor;
	private Integer communityId;
	private String communityName;
	private String interest;
	private String profession;
	private String age;
	private String sex;
	
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo [age=");
		builder.append(age);
		builder.append(", authenticate=");
		builder.append(authenticate);
		builder.append(", avatar=");
		builder.append(avatar);
		builder.append(", communityId=");
		builder.append(communityId);
		builder.append(", communityName=");
		builder.append(communityName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", floor=");
		builder.append(floor);
		builder.append(", interest=");
		builder.append(interest);
		builder.append(", loginName=");
		builder.append(loginName);
		builder.append(", nick=");
		builder.append(nick);
		builder.append(", password=");
		builder.append(password);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", profession=");
		builder.append(profession);
		builder.append(", room=");
		builder.append(room);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", type=");
		builder.append(type);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", username=");
		builder.append(username);
		builder.append("]");
		return builder.toString();
	}
	
}

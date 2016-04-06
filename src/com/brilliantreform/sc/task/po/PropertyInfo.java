package com.brilliantreform.sc.task.po;
/**
 * 物业公告信息
 * 
 * @author Lm
 * 
 */
public class PropertyInfo {
	private Integer property_information_id; // 编号
	private String title; // 标题
	private String content; // 内容
	private String phone; // 手机号码
	private String landline; // 电话号码
	private String address; // 地址
	private String createTime; // 时间
	private String remarks;   //备注
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private Integer community_id; // 小区编号

	public Integer getProperty_information_id() {
		return property_information_id;
	}

	public void setProperty_information_id(Integer propertyInformationId) {
		property_information_id = propertyInformationId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyInfo [address=");
		builder.append(address);
		builder.append(", content=");
		builder.append(content);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", landline=");
		builder.append(landline);
		builder.append(", ommunity_id=");
		builder.append(community_id);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", property_information_id=");
		builder.append(property_information_id);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append(", title=");
		builder.append(title + "]");
		return builder.toString();
	}
}

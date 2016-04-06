package com.brilliantreform.sc.user.mgrpo;

import java.sql.Timestamp;

public class Relation {
	private Integer relation_id; // int(11) NOT NULL AUTO_INCREMENT,
	private Integer user_id; // int(11) NOT NULL,
	private Integer role_id; // int(11) NOT NULL,
	private Integer right_id; // int(11) NOT NULL,
	private Integer community_id; // int(11) DEFAULT NULL,
	private Integer service_id; // int(11) DEFAULT NULL
	private Integer type; // int(11) DEFAULT NULL,
	private String option; // varchar(31) DEFAULT NULL,
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,

	public Integer getRelation_id() {
		return relation_id;
	}

	public void setRelation_id(Integer relationId) {
		relation_id = relationId;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer userId) {
		user_id = userId;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer roleId) {
		role_id = roleId;
	}

	public Integer getRight_id() {
		return right_id;
	}

	public void setRight_id(Integer rightId) {
		right_id = rightId;
	}

	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	public Integer getService_id() {
		return service_id;
	}

	public void setService_id(Integer serviceId) {
		service_id = serviceId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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
		builder.append("Relation [community_id=");
		builder.append(community_id);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", option=");
		builder.append(option);
		builder.append(", relation_id=");
		builder.append(relation_id);
		builder.append(", right_id=");
		builder.append(right_id);
		builder.append(", role_id=");
		builder.append(role_id);
		builder.append(", service_id=");
		builder.append(service_id);
		builder.append(", type=");
		builder.append(type);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append("]");
		return builder.toString();
	}

}

package com.brilliantreform.sc.statistics.po;

public class Statistics {

	private Integer community_id;
	private String community_name;
	private String community_addr;
	private String community_Dec;
	private Integer property_id;

	private String community_property;
	private String property_Dec;

	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	public String getCommunity_addr() {
		return community_addr;
	}

	public void setCommunity_addr(String communityAddr) {
		community_addr = communityAddr;
	}

	public String getCommunity_Dec() {
		return community_Dec;
	}

	public void setCommunity_Dec(String communityDec) {
		community_Dec = communityDec;
	}

	public Integer getProperty_id() {
		return property_id;
	}

	public void setProperty_id(Integer propertyId) {
		property_id = propertyId;
	}

	public String getCommunity_property() {
		return community_property;
	}

	public void setCommunity_property(String communityProperty) {
		community_property = communityProperty;
	}

	public String getProperty_Dec() {
		return property_Dec;
	}

	public void setProperty_Dec(String propertyDec) {
		property_Dec = propertyDec;
	}

	public String getCommunity_name() {
		return community_name;
	}

	public void setCommunity_name(String communityName) {
		community_name = communityName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Community [community_Dec=");
		builder.append(community_Dec);
		builder.append(", community_addr=");
		builder.append(community_addr);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", community_name=");
		builder.append(community_name);
		builder.append(", community_property=");
		builder.append(community_property);
		builder.append(", property_Dec=");
		builder.append(property_Dec);
		builder.append(", property_id=");
		builder.append(property_id);
		builder.append("]");
		return builder.toString();
	}

}

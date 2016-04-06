package com.brilliantreform.sc.community.po;

public class Community {

	private Integer community_id;
	private String community_name;
	private String community_addr;
	private String community_Dec;
	private Integer property_id;

	private String community_property;
	private String property_Dec;

	private String qx_tel;
	private String property_tel;
	private String property_msg_url;
	private String qx_msg_url;
	
	private Integer org_info_pid;//上层组织ID
	private Integer org_info_type;//经营组织类型 1=总部 2=个体户 3=公司 4=门店
	private String org_info_level;//经营组织级别
	private String org_info_area;//经营区域
	private String org_info_person;//负责人
	private String org_info_phone;//负责人号码
	private String org_info_location;//地点

    private String City;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Integer getOrg_info_pid() {
		return org_info_pid;
	}

	public void setOrg_info_pid(Integer orgInfoPid) {
		org_info_pid = orgInfoPid;
	}


	public Integer getOrg_info_type() {
		return org_info_type;
	}

	public void setOrg_info_type(Integer orgInfoType) {
		org_info_type = orgInfoType;
	}

	public String getOrg_info_level() {
		return org_info_level;
	}

	public void setOrg_info_level(String orgInfoLevel) {
		org_info_level = orgInfoLevel;
	}

	public String getOrg_info_area() {
		return org_info_area;
	}

	public void setOrg_info_area(String orgInfoArea) {
		org_info_area = orgInfoArea;
	}

	public String getOrg_info_person() {
		return org_info_person;
	}

	public void setOrg_info_person(String orgInfoPerson) {
		org_info_person = orgInfoPerson;
	}

	public String getOrg_info_phone() {
		return org_info_phone;
	}

	public void setOrg_info_phone(String orgInfoPhone) {
		org_info_phone = orgInfoPhone;
	}

	public String getOrg_info_location() {
		return org_info_location;
	}

	public void setOrg_info_location(String orgInfoLocation) {
		org_info_location = orgInfoLocation;
	}

	public String getQx_tel() {
		return qx_tel;
	}

	public void setQx_tel(String qxTel) {
		qx_tel = qxTel;
	}

	public String getProperty_tel() {
		return property_tel;
	}

	public void setProperty_tel(String propertyTel) {
		property_tel = propertyTel;
	}

	public String getProperty_msg_url() {
		return property_msg_url;
	}

	public void setProperty_msg_url(String propertyMsgUrl) {
		property_msg_url = propertyMsgUrl;
	}

	public String getQx_msg_url() {
		return qx_msg_url;
	}

	public void setQx_msg_url(String qxMsgUrl) {
		qx_msg_url = qxMsgUrl;
	}

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
		builder.append(", property_msg_url=");
		builder.append(property_msg_url);
		builder.append(", property_tel=");
		builder.append(property_tel);
		builder.append(", qx_msg_url=");
		builder.append(qx_msg_url);
		builder.append(", qx_tel=");
		builder.append(qx_tel);
		builder.append("]");
		return builder.toString();
	}

}

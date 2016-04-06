package com.brilliantreform.sc.community.po;

public class Community_register {

	private Integer community_register_id;//
	private String community_name;//申请小区名称
	private String community_addr;//小区地址
	private String community_person_phone;//申请人手机号
	private String community_person_name;//申请人姓名
	private String community_person_addr;//申请人居住地址
	private Integer community_register_status;//状态 1=未审核 2=通过 3=不通过
	private String create_time;//创建时间
	public Integer getCommunity_register_id() {
		return community_register_id;
	}
	public void setCommunity_register_id(Integer communityRegisterId) {
		community_register_id = communityRegisterId;
	}
	public String getCommunity_name() {
		return community_name;
	}
	public void setCommunity_name(String communityName) {
		community_name = communityName;
	}
	public String getCommunity_addr() {
		return community_addr;
	}
	public void setCommunity_addr(String communityAddr) {
		community_addr = communityAddr;
	}
	public String getCommunity_person_phone() {
		return community_person_phone;
	}
	public void setCommunity_person_phone(String communityPersonPhone) {
		community_person_phone = communityPersonPhone;
	}
	public String getCommunity_person_name() {
		return community_person_name;
	}
	public void setCommunity_person_name(String communityPersonName) {
		community_person_name = communityPersonName;
	}
	public String getCommunity_person_addr() {
		return community_person_addr;
	}
	public void setCommunity_person_addr(String communityPersonAddr) {
		community_person_addr = communityPersonAddr;
	}
	public Integer getCommunity_register_status() {
		return community_register_status;
	}
	public void setCommunity_register_status(Integer communityRegisterStatus) {
		community_register_status = communityRegisterStatus;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String createTime) {
		create_time = createTime;
	}
	@Override
	public String toString() {
		return "Community_register [community_addr=" + community_addr
				+ ", community_name=" + community_name
				+ ", community_person_addr=" + community_person_addr
				+ ", community_person_name=" + community_person_name
				+ ", community_person_phone=" + community_person_phone
				+ ", community_register_id=" + community_register_id
				+ ", community_register_status=" + community_register_status
				+ ", create_time=" + create_time + "]";
	}
	
	
}

package com.brilliantreform.sc.service.po;

import java.sql.Timestamp;

public class ServiceVo {
	private Integer service_id; // int(11) NOT NULL AUTO_INCREMENT,
	private Integer community_id; // 
	private String service_name; // varchar(32) DEFAULT NULL,
	private String service_dec; // varchar(256) DEFAULT NULL,
	private String service_img; // varchar(256) DEFAULT NULL,
	private String service_title_img; // varchar(256) DEFAULT NULL,
	private String service_url;
	private Integer service_order; // int(11) NOT NULL DEFAULT '1',
	private Integer service_type; // int(11) NOT NULL DEFAULT '1' COMMENT '1 商品 2 咨询服务 3 广告 4咨询服务有二级菜单
	private Integer consultation_id;
	private Integer parent_id; // int(11) NOT NULL DEFAULT '0',
	private Integer status; // int(11) DEFAULT NULL,
	private Timestamp updateTime; // timestamp NOT NULL DEFAULT									
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,

	private String parent_service_name;
	private String community_name;

	private Integer i_service_id;

	
	public String getService_title_img() {
		return service_title_img;
	}

	public void setService_title_img(String service_title_img) {
		this.service_title_img = service_title_img;
	}

	public String getParent_service_name() {
		return parent_service_name;
	}

	public void setParent_service_name(String parentServiceName) {
		parent_service_name = parentServiceName;
	}

	public String getCommunity_name() {
		return community_name;
	}

	public void setCommunity_name(String communityName) {
		community_name = communityName;
	}

	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	public String getService_url() {
		return service_url;
	}

	public void setService_url(String serviceUrl) {
		service_url = serviceUrl;
	}

	public Integer getConsultation_id() {
		return consultation_id;
	}

	public void setConsultation_id(Integer consultationId) {
		consultation_id = consultationId;
	}

	public Integer getService_id() {
		return service_id;
	}

	public void setService_id(Integer serviceId) {
		service_id = serviceId;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String serviceName) {
		service_name = serviceName;
	}

	public String getService_dec() {
		return service_dec;
	}

	public void setService_dec(String serviceDec) {
		service_dec = serviceDec;
	}

	public String getService_img() {
		return service_img;
	}

	public void setService_img(String serviceImg) {
		service_img = serviceImg;
	}

	public Integer getService_order() {
		return service_order;
	}

	public void setService_order(Integer serviceOrder) {
		service_order = serviceOrder;
	}

	public Integer getService_type() {
		return service_type;
	}

	public void setService_type(Integer serviceType) {
		service_type = serviceType;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parentId) {
		parent_id = parentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getI_service_id() {
		return i_service_id;
	}

	public void setI_service_id(Integer i_service_id) {
		this.i_service_id = i_service_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceVo [service_id=");
		builder.append(service_id);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", service_name=");
		builder.append(service_name);
		builder.append(", service_dec=");
		builder.append(service_dec);
		builder.append(", service_img=");
		builder.append(service_img);
		builder.append(", service_title_img=");
		builder.append(service_title_img);
		builder.append(", service_url=");
		builder.append(service_url);
		builder.append(", service_order=");
		builder.append(service_order);
		builder.append(", service_type=");
		builder.append(service_type);
		builder.append(", consultation_id=");
		builder.append(consultation_id);
		builder.append(", parent_id=");
		builder.append(parent_id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", parent_service_name=");
		builder.append(parent_service_name);
		builder.append(", community_name=");
		builder.append(community_name);
		builder.append("]");
		return builder.toString();
	}

	

}

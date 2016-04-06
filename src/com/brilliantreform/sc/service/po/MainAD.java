package com.brilliantreform.sc.service.po;

import java.sql.Timestamp;

public class MainAD {

	private Integer ad_id; // int(11) NOT NULL AUTO_INCREMENT,
	private Integer community_id;
	private String ad_name; // varchar(32) DEFAULT NULL,
	private String ad_dec; // varchar(256) DEFAULT NULL,
	private String ad_img; // varchar(256) DEFAULT NULL,
	private Integer ad_order; // int(11) NOT NULL DEFAULT '1',
	private String ad_url; // varchar(256) NOT NULL,

	private Integer service_id; // int(11) NOT NULL DEFAULT '1',
	//11.商品广告  12.订单广告
	private Integer service_type; // int(11) NOT NULL DEFAULT '1' COMMENT '1 商品
	// 2 咨询服务 3 广告 4 咨询服务有二级菜单',
	private Integer product_id; // int(11) DEFAULT NULL,
	private Integer consultation_id; // int(11) DEFAULT NULL,
	private String wap_url; // varchar(256) DEFAULT NULL,

	private Integer status; // int(11) DEFAULT NULL,
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,

	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer community_id) {
		this.community_id = community_id;
	}

	public Integer getService_id() {
		return service_id;
	}

	public void setService_id(Integer serviceId) {
		service_id = serviceId;
	}

	public Integer getService_type() {
		return service_type;
	}

	public void setService_type(Integer serviceType) {
		service_type = serviceType;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer productId) {
		product_id = productId;
	}

	public Integer getConsultation_id() {
		return consultation_id;
	}

	public void setConsultation_id(Integer consultationId) {
		consultation_id = consultationId;
	}

	public String getWap_url() {
		return wap_url;
	}

	public void setWap_url(String wapUrl) {
		wap_url = wapUrl;
	}

	public Integer getAd_id() {
		return ad_id;
	}

	public void setAd_id(Integer adId) {
		ad_id = adId;
	}

	public String getAd_name() {
		return ad_name;
	}

	public void setAd_name(String adName) {
		ad_name = adName;
	}

	public String getAd_dec() {
		return ad_dec;
	}

	public void setAd_dec(String adDec) {
		ad_dec = adDec;
	}

	public String getAd_img() {
		return ad_img;
	}

	public void setAd_img(String adImg) {
		ad_img = adImg;
	}

	public Integer getAd_order() {
		return ad_order;
	}

	public void setAd_order(Integer adOrder) {
		ad_order = adOrder;
	}

	public String getAd_url() {
		return ad_url;
	}

	public void setAd_url(String adUrl) {
		ad_url = adUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		builder.append("MainAD [ad_dec=");
		builder.append(ad_dec);
		builder.append(", ad_id=");
		builder.append(ad_id);
		builder.append(", ad_img=");
		builder.append(ad_img);
		builder.append(", ad_name=");
		builder.append(ad_name);
		builder.append(", ad_order=");
		builder.append(ad_order);
		builder.append(", ad_url=");
		builder.append(ad_url);
		builder.append(", consultation_id=");
		builder.append(consultation_id);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", product_id=");
		builder.append(product_id);
		builder.append(", service_id=");
		builder.append(service_id);
		builder.append(", service_type=");
		builder.append(service_type);
		builder.append(", status=");
		builder.append(status);
		builder.append(", wap_url=");
		builder.append(wap_url);
		builder.append("]");
		return builder.toString();
	}

}

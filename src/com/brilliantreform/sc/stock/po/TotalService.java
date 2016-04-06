package com.brilliantreform.sc.stock.po;

import java.util.Date;

/**
 * 总库分类
 */
public class TotalService {
	private Integer service_id;
	private String service_name;
	private String service_dec;
	private String service_img;
	private Integer service_order;
	private Integer service_type;
	private String service_url;
	private Integer parent_id;
	private Integer status;
	private Date updateTime;
	private Date createTime;

	public Integer getService_id() {
		return service_id;
	}

	public void setService_id(Integer service_id) {
		this.service_id = service_id;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getService_dec() {
		return service_dec;
	}

	public void setService_dec(String service_dec) {
		this.service_dec = service_dec;
	}

	public String getService_img() {
		return service_img;
	}

	public void setService_img(String service_img) {
		this.service_img = service_img;
	}

	public String getService_url() {
		return service_url;
	}

	public void setService_url(String service_url) {
		this.service_url = service_url;
	}

	public Integer getService_order() {
		return service_order;
	}

	public void setService_order(Integer service_order) {
		this.service_order = service_order;
	}

	public Integer getService_type() {
		return service_type;
	}

	public void setService_type(Integer service_type) {
		this.service_type = service_type;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

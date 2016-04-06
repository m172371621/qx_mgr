package com.brilliantreform.sc.stock.po;

import java.util.Date;

/**
 * 总库
 * 
 * @author Lm
 * 
 */
public class TotalProduct {
	private Integer product_id; // 商品编号
	private Integer service_id; //分类码
	private String name; // 商品名
	private String picture; // 图片
	private String thumbnail; // 缩略图
	private String description_url; // 描述链接
	private String description_pic; // 描述图片
	private String description; // 描述
	private Double price; // 价格
	private Double market_price; // App专享价
	private String unit; // 商品类型(盒,袋,个等)
	private Integer tags; // 商品标签 二进制数表示：预售 特价 支持区享卡 满减 APP专享 称重
	private String wholesale_price; // 满减
	private Double delivery_price;// 交货价格
	private Integer status; // 销售状态
	private String barcode; // 条码
	private Date updateTime; // 更新日期
	private Date createTime; // 创建日期
	private Double inPrice; // 指导进价
	private String product_code; // 商品编码
	private String place; // 商品产地
	private String spec; // 规格型号
	private String unitProportion; // 单位比率

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public Integer getService_id() {
		return service_id;
	}

	public void setService_id(Integer service_id) {
		this.service_id = service_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDescription_url() {
		return description_url;
	}

	public void setDescription_url(String description_url) {
		this.description_url = description_url;
	}

	public String getDescription_pic() {
		return description_pic;
	}

	public void setDescription_pic(String description_pic) {
		this.description_pic = description_pic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(Double market_price) {
		this.market_price = market_price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getTags() {
		return tags;
	}

	public void setTags(Integer tags) {
		this.tags = tags;
	}

	public String getWholesale_price() {
		return wholesale_price;
	}

	public void setWholesale_price(String wholesale_price) {
		this.wholesale_price = wholesale_price;
	}

	public Double getDelivery_price() {
		return delivery_price;
	}

	public void setDelivery_price(Double delivery_price) {
		this.delivery_price = delivery_price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public Double getInPrice() {
		return inPrice;
	}

	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnitProportion() {
		return unitProportion;
	}

	public void setUnitProportion(String unitProportion) {
		this.unitProportion = unitProportion;
	}

}

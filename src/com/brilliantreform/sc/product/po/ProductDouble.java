package com.brilliantreform.sc.product.po;

import java.sql.Timestamp;

public class ProductDouble {
	private Integer product_id;
	private Integer community_id;
	private Integer service_id;
	private Integer product_type;
	private String name;
	private String picture;
	private String thumbnail;
	private String dec_picture;
	private String description;
	private String description_pic;
	private String description_url;
	private String price;// 多个区间价格以|分割',
	private String market_price;
	private String unit;
	private Integer period;// 价格区间的时间间隔 单位天',
	private Double amount;
	private Integer delivery_type;// 是否送货上门 1不上门 2提供上门服务',
	private Float delivery_price;
	private Float comment_level;
	private Integer status;
	private Integer rule_id;
	private Timestamp updateTime;
	private Timestamp createTime;

	private String communityName;
	private String service_name;

	private String barcode;// 条码
	private Double order_current_sum;

	private Integer real_amount;

	private Integer tags;

	private String wholesale_price; // 满减

	public String getWholesale_price() {
		return wholesale_price;
	}

	public void setWholesale_price(String wholesalePrice) {
		wholesale_price = wholesalePrice;
	}

	public Integer getTags() {
		return tags;
	}

	public void setTags(Integer tags) {
		this.tags = tags;
	}


	public Integer getReal_amount() {
		return real_amount;
	}

	public void setReal_amount(Integer realAmount) {
		real_amount = realAmount;
	}


	/**
	 * @return the order_current_sum
	 */
	public Double getOrder_current_sum() {
		return order_current_sum;
	}

	/**
	 * @param orderCurrentSum the order_current_sum to set
	 */
	public void setOrder_current_sum(Double orderCurrentSum) {
		order_current_sum = orderCurrentSum;
	}

	public Integer getRule_id() {
		return rule_id;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode
	 *            the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setRule_id(Integer ruleId) {
		rule_id = ruleId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String serviceName) {
		service_name = serviceName;
	}

	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	public String getDescription_url() {
		return description_url;
	}

	public void setDescription_url(String descriptionUrl) {
		description_url = descriptionUrl;
	}

	public String getDescription_pic() {
		return description_pic;
	}

	public void setDescription_pic(String descriptionPic) {
		description_pic = descriptionPic;
	}

	public String getDec_picture() {
		return dec_picture;
	}

	public void setDec_picture(String decPicture) {
		dec_picture = decPicture;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer productId) {
		product_id = productId;
	}

	public Integer getService_id() {
		return service_id;
	}

	public void setService_id(Integer serviceId) {
		service_id = serviceId;
	}

	public Integer getProduct_type() {
		return product_type;
	}

	public void setProduct_type(Integer productType) {
		product_type = productType;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMarket_price() {
		return market_price;
	}

	public void setMarket_price(String marketPrice) {
		market_price = marketPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}


	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(Integer deliveryType) {
		delivery_type = deliveryType;
	}

	public Float getDelivery_price() {
		return delivery_price;
	}

	public void setDelivery_price(Float deliveryPrice) {
		delivery_price = deliveryPrice;
	}

	public Float getComment_level() {
		return comment_level;
	}

	public void setComment_level(Float commentLevel) {
		comment_level = commentLevel;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [amount=");
		builder.append(amount);
		builder.append(", comment_level=");
		builder.append(comment_level);
		builder.append(", communityName=");
		builder.append(communityName);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", dec_picture=");
		builder.append(dec_picture);
		builder.append(", delivery_price=");
		builder.append(delivery_price);
		builder.append(", delivery_type=");
		builder.append(delivery_type);
		builder.append(", description=");
		builder.append(description);
		builder.append(", description_pic=");
		builder.append(description_pic);
		builder.append(", description_url=");
		builder.append(description_url);
		builder.append(", market_price=");
		builder.append(market_price);
		builder.append(", name=");
		builder.append(name);
		builder.append(", period=");
		builder.append(period);
		builder.append(", picture=");
		builder.append(picture);
		builder.append(", real_amount=");
		builder.append(real_amount);
		builder.append(", price=");
		builder.append(price);
		builder.append(", product_id=");
		builder.append(product_id);
		builder.append(", rule_id=");
		builder.append(rule_id);
		builder.append(", product_type=");
		builder.append(product_type);
		builder.append(", service_id=");
		builder.append(service_id);
		builder.append(", service_name=");
		builder.append(service_name);
		builder.append(", status=");
		builder.append(status);
		builder.append(", thumbnail=");
		builder.append(thumbnail);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", tags=");
		builder.append(tags);
		builder.append(", wholesale_price=");
		builder.append(wholesale_price);
		builder.append(", barcode=");
		builder.append(barcode);
		builder.append("]");
		return builder.toString();
	}

}

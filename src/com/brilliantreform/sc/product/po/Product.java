package com.brilliantreform.sc.product.po;

import java.util.Date;

public class Product {
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
	private Double price;// 多个区间价格以|分割',
	private Double market_price;
	private String unit;
	private Integer period;// 价格区间的时间间隔 单位天',
	private Integer amount;
	private Integer delivery_type;// 是否送货上门 1不上门 2提供上门服务',
	private Double delivery_price;
	private Double comment_level;
	private Integer status;
	private Integer rule_id;
	private Date updateTime;
	private Date createTime;

	private String communityName;
	private String service_name;

	private String barcode;// 条码
	private Integer order_current_sum;

	private Integer real_amount;

	private Integer tags;

	private String wholesale_price; // 满减

	private Integer i_product_id;
	private String dec_tag;
	private Integer sold;

    private String source_product_id;

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
	public Integer getOrder_current_sum() {
		return order_current_sum;
	}

	/**
	 * @param orderCurrentSum
	 *            the order_current_sum to set
	 */
	public void setOrder_current_sum(Integer orderCurrentSum) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(Double marketPrice) {
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
	public Integer getAmount() {
		if (real_amount != null)
			return real_amount;
		return amount;
	}


    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(Integer deliveryType) {
		delivery_type = deliveryType;
	}

	public Double getDelivery_price() {
		return delivery_price;
	}

	public void setDelivery_price(Double deliveryPrice) {
		delivery_price = deliveryPrice;
	}

	public Double getComment_level() {
		return comment_level;
	}

	public void setComment_level(Double commentLevel) {
		comment_level = commentLevel;
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

	public Integer getI_product_id() {
		return i_product_id;
	}

	public void setI_product_id(Integer i_product_id) {
		this.i_product_id = i_product_id;
	}

	public Integer getSold() {
		return sold;
	}

	public String getDec_tag() {
		return dec_tag;
	}

	public void setDec_tag(String dec_tag) {
		this.dec_tag = dec_tag;
	}

	public void setSold(Integer sold) {
		this.sold = sold;
	}

    public String getSource_product_id() {
        return source_product_id;
    }

    public void setSource_product_id(String source_product_id) {
        this.source_product_id = source_product_id;
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

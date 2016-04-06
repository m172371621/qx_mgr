package com.brilliantreform.sc.product.po;

import java.sql.Timestamp;

public class ProductCommunity {
	private Integer product_id; // int(11) NOT NULL,
	private Integer community_id; // int(11) NOT NULL,
	private Integer amount; // int(11) DEFAULT NULL,
	private Integer ruleId; // int(11) DEFAULT NULL,
	private Timestamp createTime; // timestamp NULL DEFAULT NULL,

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer productId) {
		product_id = productId;
	}

	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
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
		builder.append("ProductCommunity [amount=");
		builder.append(amount);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", product_id=");
		builder.append(product_id);
		builder.append(", ruleId=");
		builder.append(ruleId);
		builder.append("]");
		return builder.toString();
	}
}

package com.brilliantreform.sc.product.po;

import java.sql.Timestamp;
import java.util.Date;

public class ProductRule {
	private Integer rule_id; // int(11) NOT NULL AUTO_INCREMENT,
	private Integer community_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String communityName; // int(11) NOT NULL AUTO_INCREMENT,
	private String rule_name; // varchar(31) DEFAULT NULL,
	private String rule_dec; // varchar(127) DEFAULT NULL,
	private Integer user_limit; // int(11) DEFAULT NULL,
	private Integer user_limit_gap; // int(11) DEFAULT NULL,
	private Integer amount_limit; // int(11) DEFAULT NULL,
	private Integer pamount; // int(11) DEFAULT NULL,
	private Date rule_begin_time; // timestamp NULL DEFAULT NULL,
	private Integer price_off; // int(11) DEFAULT NULL,
	private Date rule_end_time; // timestamp NULL DEFAULT NULL,
	private Integer rule_type; // int(11) DEFAULT NULL,
	private Timestamp createtime; // timestamp NULL DEFAULT NULL,

	
	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Integer getPamount() {
		return pamount;
	}

	public void setPamount(Integer pamount) {
		this.pamount = pamount;
	}

	public Integer getRule_id() {
		return rule_id;
	}

	public void setRule_id(Integer ruleId) {
		rule_id = ruleId;
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String ruleName) {
		rule_name = ruleName;
	}

	public String getRule_dec() {
		return rule_dec;
	}

	public void setRule_dec(String ruleDec) {
		rule_dec = ruleDec;
	}

	public Integer getUser_limit() {
		return user_limit;
	}

	public void setUser_limit(Integer userLimit) {
		user_limit = userLimit;
	}

	public Integer getUser_limit_gap() {
		return user_limit_gap;
	}

	public void setUser_limit_gap(Integer userLimitGap) {
		user_limit_gap = userLimitGap;
	}

	public Integer getAmount_limit() {
		return amount_limit;
	}

	public void setAmount_limit(Integer amountLimit) {
		amount_limit = amountLimit;
	}

	public Integer getPrice_off() {
		return price_off;
	}

	public void setPrice_off(Integer priceOff) {
		price_off = priceOff;
	}

	public Integer getRule_type() {
		return rule_type;
	}

	public void setRule_type(Integer ruleType) {
		rule_type = ruleType;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

    public Date getRule_begin_time() {
        return rule_begin_time;
    }

    public void setRule_begin_time(Date rule_begin_time) {
        this.rule_begin_time = rule_begin_time;
    }

    public Date getRule_end_time() {
        return rule_end_time;
    }

    public void setRule_end_time(Date rule_end_time) {
        this.rule_end_time = rule_end_time;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductRule [amount_limit=");
		builder.append(amount_limit);
		builder.append(", communityName=");
		builder.append(communityName);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", createtime=");
		builder.append(createtime);
		builder.append(", pamount=");
		builder.append(pamount);
		builder.append(", price_off=");
		builder.append(price_off);
		builder.append(", rule_begin_time=");
		builder.append(rule_begin_time);
		builder.append(", rule_dec=");
		builder.append(rule_dec);
		builder.append(", rule_end_time=");
		builder.append(rule_end_time);
		builder.append(", rule_id=");
		builder.append(rule_id);
		builder.append(", rule_name=");
		builder.append(rule_name);
		builder.append(", rule_type=");
		builder.append(rule_type);
		builder.append(", user_limit=");
		builder.append(user_limit);
		builder.append(", user_limit_gap=");
		builder.append(user_limit_gap);
		builder.append("]");
		return builder.toString();
	}

}

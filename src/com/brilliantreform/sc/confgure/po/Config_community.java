package com.brilliantreform.sc.confgure.po;


import java.util.Date;

/**
 * 配置项管理
 * @author Lm
 *
 */
public class Config_community {
	private String config_id;
	private String config_value;
	private String config_dec;
	private Integer community_id;
	private String config_ext1;
	private String config_ext2;
	private String config_ext3;
	private Date createTime;
	public String getConfig_id() {
		return config_id;
	}
	public void setConfig_id(String configId) {
		config_id = configId;
	}
	public String getConfig_value() {
		return config_value;
	}
	public void setConfig_value(String configValue) {
		config_value = configValue;
	}
	public String getConfig_dec() {
		return config_dec;
	}
	public void setConfig_dec(String configDec) {
		config_dec = configDec;
	}
	public Integer getCommunity_id() {
		return community_id;
	}
	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}
	public String getConfig_ext1() {
		return config_ext1;
	}
	public void setConfig_ext1(String configExt1) {
		config_ext1 = configExt1;
	}
	public String getConfig_ext2() {
		return config_ext2;
	}
	public void setConfig_ext2(String configExt2) {
		config_ext2 = configExt2;
	}
	public String getConfig_ext3() {
		return config_ext3;
	}
	public void setConfig_ext3(String configExt3) {
		config_ext3 = configExt3;
	}
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Config_community [community_id=");
		builder.append(community_id);
		builder.append(", config_dec=");
		builder.append(config_dec);
		builder.append(", config_ext1=");
		builder.append(config_ext1);
		builder.append(", config_ext2=");
		builder.append(config_ext2);
		builder.append(", config_ext3=");
		builder.append(config_ext3);
		builder.append(", config_id=");
		builder.append(config_id);
		builder.append(", config_value=");
		builder.append(config_value);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

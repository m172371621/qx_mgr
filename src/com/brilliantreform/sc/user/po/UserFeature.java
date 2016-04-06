package com.brilliantreform.sc.user.po;

public class UserFeature {

	private Integer userid;
	private Integer featureId;
	private Integer featureType;
	private String featureName;
	private String featureDesc;
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}
	public Integer getFeatureType() {
		return featureType;
	}
	public void setFeatureType(Integer featureType) {
		this.featureType = featureType;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public String getFeatureDesc() {
		return featureDesc;
	}
	public void setFeatureDesc(String featureDesc) {
		this.featureDesc = featureDesc;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserFeature [featureDesc=");
		builder.append(featureDesc);
		builder.append(", featureId=");
		builder.append(featureId);
		builder.append(", featureName=");
		builder.append(featureName);
		builder.append(", featureType=");
		builder.append(featureType);
		builder.append(", userid=");
		builder.append(userid);
		builder.append("]");
		return builder.toString();
	}

}

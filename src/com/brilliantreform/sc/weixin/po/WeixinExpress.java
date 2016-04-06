package com.brilliantreform.sc.weixin.po;


public class WeixinExpress{
	private Integer express_id; // int(11) NOT NULL AUTO_INCREMENT,
	private String express_com; // varchar(15) DEFAULT NULL, 快递公司
	private String user_phone; // varchar(31) DEFAULT NULL, 用户手机号
	private String express_no; // varchar(63) DEFAULT NULL, 快递单号
	private Integer user_type; // int(11) DEFAULT '1' COMMENT '1 非app用户 2
								// 是app用户',
	private Integer community_id; // int(11) NOT NULL,
	private Integer status; // int(11) DEFAULT '1' COMMENT '1 未签收 2 已签收',
	private Integer sendflag; // int(11) DEFAULT '1' COMMENT '1 未通知 2 已通知',
	private String sign_time; // timestamp NULL DEFAULT NULL, 签收时间
	private String arrival_time; // timestamp NULL DEFAULT NULL, 到达时间
    private Integer wuser_id;//微信用户id
    private String openid;//用户微信唯一标示
	/**
	 * @return the wuser_id
	 */
	public Integer getWuser_id() {
		return wuser_id;
	}
	/**
	 * @param wuserId the wuser_id to set
	 */
	public void setWuser_id(Integer wuserId) {
		wuser_id = wuserId;
	}
	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * @return the express_id
	 */
	public Integer getExpress_id() {
		return express_id;
	}
	/**
	 * @param expressId the express_id to set
	 */
	public void setExpress_id(Integer expressId) {
		express_id = expressId;
	}
	/**
	 * @return the express_com
	 */
	public String getExpress_com() {
		return express_com;
	}
	/**
	 * @param expressCom the express_com to set
	 */
	public void setExpress_com(String expressCom) {
		express_com = expressCom;
	}
	/**
	 * @return the user_phone
	 */
	public String getUser_phone() {
		return user_phone;
	}
	/**
	 * @param userPhone the user_phone to set
	 */
	public void setUser_phone(String userPhone) {
		user_phone = userPhone;
	}
	/**
	 * @return the express_no
	 */
	public String getExpress_no() {
		return express_no;
	}
	/**
	 * @param expressNo the express_no to set
	 */
	public void setExpress_no(String expressNo) {
		express_no = expressNo;
	}
	/**
	 * @return the user_type
	 */
	public Integer getUser_type() {
		return user_type;
	}
	/**
	 * @param userType the user_type to set
	 */
	public void setUser_type(Integer userType) {
		user_type = userType;
	}
	/**
	 * @return the community_id
	 */
	public Integer getCommunity_id() {
		return community_id;
	}
	/**
	 * @param communityId the community_id to set
	 */
	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the sendflag
	 */
	public Integer getSendflag() {
		return sendflag;
	}
	/**
	 * @param sendflag the sendflag to set
	 */
	public void setSendflag(Integer sendflag) {
		this.sendflag = sendflag;
	}
	/**
	 * @return the sign_time
	 */
	public String getSign_time() {
		return sign_time;
	}
	/**
	 * @param signTime the sign_time to set
	 */
	public void setSign_time(String signTime) {
		sign_time = signTime;
	}
	/**
	 * @return the arrival_time
	 */
	public String getArrival_time() {
		return arrival_time;
	}
	/**
	 * @param arrivalTime the arrival_time to set
	 */
	public void setArrival_time(String arrivalTime) {
		arrival_time = arrivalTime;
	}
    
}

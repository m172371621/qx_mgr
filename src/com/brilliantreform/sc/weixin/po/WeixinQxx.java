package com.brilliantreform.sc.weixin.po;

public class WeixinQxx {

	private String openid;//openid
	private String nickname;//微信名称
	private String phone;//手机
	private String name;//姓名
	private String addr;//地址
	private String my_recommend_code;//我的推荐码
	private String other_recommend_code;//他人推荐码
	private Integer recommend_amount;//已推荐数量
	private String state;//状态 1=未确认 2=已确认
	private Integer buy_count;//购买数量
	private String create_time;
	private String img_url;//
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getBuy_count() {
		return buy_count;
	}
	public void setBuy_count(Integer buyCount) {
		buy_count = buyCount;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getMy_recommend_code() {
		return my_recommend_code;
	}
	public void setMy_recommend_code(String myRecommendCode) {
		my_recommend_code = myRecommendCode;
	}
	public String getOther_recommend_code() {
		return other_recommend_code;
	}
	public void setOther_recommend_code(String otherRecommendCode) {
		other_recommend_code = otherRecommendCode;
	}
	public Integer getRecommend_amount() {
		return recommend_amount;
	}
	public void setRecommend_amount(Integer recommendAmount) {
		recommend_amount = recommendAmount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String createTime) {
		create_time = createTime;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String imgUrl) {
		img_url = imgUrl;
	}
	@Override
	public String toString() {
		return "WeixinQxx [addr=" + addr + ", buy_count=" + buy_count
				+ ", create_time=" + create_time + ", img_url=" + img_url
				+ ", my_recommend_code=" + my_recommend_code + ", name=" + name
				+ ", nickname=" + nickname + ", openid=" + openid
				+ ", other_recommend_code=" + other_recommend_code + ", phone="
				+ phone + ", recommend_amount=" + recommend_amount + ", state="
				+ state + "]";
	}
	
	
}

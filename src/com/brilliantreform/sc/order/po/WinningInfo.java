package com.brilliantreform.sc.order.po;

import java.util.Date;

/**
 * 中奖信息
 * 
 * @author Lm
 * 
 */
public class WinningInfo {
	private String order_id; //订单ID
	private String card_name; // 几等奖
	private Integer userid;

	private String phone; // 手机号码
	private Date createTime; // 时间
	private Integer community_id;
	private int begin;
	private int size;
	private Integer awardStatus; //领奖状态   0-未知 ,1-为领取,2-已领取
	private String awardTime;  //领奖时间    
	private String operatorName; //操作人员

    private String community_name;

	
	public Integer getUserid() {
		return userid;
	}
	
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	 

	/**
	 * @return the order_id
	 */
	public String getOrder_id() {
		return order_id;
	}

	/**
	 * @param orderId the order_id to set
	 */
	public void setOrder_id(String orderId) {
		order_id = orderId;
	}

	public Integer getAwardStatus() {
		return awardStatus;
	}

	public void setAwardStatus(Integer awardStatus) {
		this.awardStatus = awardStatus;
	}

	public String getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(String awardTime) {
		this.awardTime = awardTime;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}


	public Integer getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String cardName) {
		card_name = cardName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    @Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("WinningInfo [card_name=");
		builder.append(card_name);
		builder.append(", order_id=");
		builder.append(order_id);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", awardStatus=");
		builder.append(awardStatus);
		builder.append(", awardTime=");
		builder.append(awardTime);
		builder.append(", operatorName=");
		builder.append(operatorName);
		builder.append(", begin=");
		builder.append(begin);
		builder.append(", size=");
		builder.append(size+"]");
		
		return builder.toString();
	}

	
	
}

package com.brilliantreform.sc.incomming.po;

import java.util.Date;

public class IncommingHeaderBean {
     private Integer stockchange_header_id;//汇总id
     private String stockchange_header_no;//汇总单号
     private Integer community_id;//小区ID
     private String community_name;//小区名称
     private Integer stock_type;//类型（进货，调拨入，退货，损耗，调拨出）
     private String create_by;//创建人
     private Date create_time;//创建时间
     private Integer state;//状态 1=未确认 2=已确认
     
     
	/**
	 * @return the community_name
	 */
	public String getCommunity_name() {
		return community_name;
	}
	/**
	 * @param communityName the community_name to set
	 */
	public void setCommunity_name(String communityName) {
		community_name = communityName;
	}
	/**
	 * @return the stockchange_header_id
	 */
	public Integer getStockchange_header_id() {
		return stockchange_header_id;
	}
	/**
	 * @param stockchangeHeaderId the stockchange_header_id to set
	 */
	public void setStockchange_header_id(Integer stockchangeHeaderId) {
		stockchange_header_id = stockchangeHeaderId;
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
	 * @return the stock_type
	 */
	public Integer getStock_type() {
		return stock_type;
	}
	/**
	 * @param stockType the stock_type to set
	 */
	public void setStock_type(Integer stockType) {
		stock_type = stockType;
	}
	/**
	 * @return the create_by
	 */
	public String getCreate_by() {
		return create_by;
	}
	/**
	 * @param createBy the create_by to set
	 */
	public void setCreate_by(String createBy) {
		create_by = createBy;
	}
	/**
	 * @return the create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}
	/**
	 * @param createTime the create_time to set
	 */
	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * @return the stockchange_header_no
	 */
	public String getStockchange_header_no() {
		return stockchange_header_no;
	}
	/**
	 * @param stockchangeHeaderNo the stockchange_header_no to set
	 */
	public void setStockchange_header_no(String stockchangeHeaderNo) {
		stockchange_header_no = stockchangeHeaderNo;
	}

     
	
}

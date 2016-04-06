package com.brilliantreform.sc.incomming.po;

public class StockChangeLogBean {

	private int log_id;//记录id
	private int product_id;//商品ID
	private int community_id;//小区ID
	private String batch_serial;//批次号（进货，调拨入，退货，损耗，调拨出）
	private int order_id;//订单号（销售）
	private int log_type;//类型（1进货，2调拨入，3退货，4损耗，5调拨出，6售出，7取消订单）
	private double order_current_sum;//操作数量
	private double stock_sum;//当前库存
	private String ip;//IP
	private String create_by;//创建人ID
	private int create_type;//创建人类型（用户，管理后台）
	private int create_time;//当前库存
	
	
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the log_id
	 */
	public int getLog_id() {
		return log_id;
	}
	/**
	 * @param logId the log_id to set
	 */
	public void setLog_id(int logId) {
		log_id = logId;
	}
	/**
	 * @return the product_id
	 */
	public int getProduct_id() {
		return product_id;
	}
	/**
	 * @param productId the product_id to set
	 */
	public void setProduct_id(int productId) {
		product_id = productId;
	}
	/**
	 * @return the community_id
	 */
	public int getCommunity_id() {
		return community_id;
	}
	/**
	 * @param communityId the community_id to set
	 */
	public void setCommunity_id(int communityId) {
		community_id = communityId;
	}
	/**
	 * @return the batch_serial
	 */
	public String getBatch_serial() {
		return batch_serial;
	}
	/**
	 * @param batchSerial the batch_serial to set
	 */
	public void setBatch_serial(String batchSerial) {
		batch_serial = batchSerial;
	}
	/**
	 * @return the order_id
	 */
	public int getOrder_id() {
		return order_id;
	}
	/**
	 * @param orderId the order_id to set
	 */
	public void setOrder_id(int orderId) {
		order_id = orderId;
	}
	/**
	 * @return the log_type
	 */
	public int getLog_type() {
		return log_type;
	}
	/**
	 * @param logType the log_type to set
	 */
	public void setLog_type(int logType) {
		log_type = logType;
	}
	/**
	 * @return the order_current_sum
	 */
	public double getOrder_current_sum() {
		return order_current_sum;
	}
	/**
	 * @param orderCurrentSum the order_current_sum to set
	 */
	public void setOrder_current_sum(double orderCurrentSum) {
		order_current_sum = orderCurrentSum;
	}
	/**
	 * @return the stock_sum
	 */
	public double getStock_sum() {
		return stock_sum;
	}
	/**
	 * @param stockSum the stock_sum to set
	 */
	public void setStock_sum(double stockSum) {
		stock_sum = stockSum;
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
	 * @return the create_type
	 */
	public int getCreate_type() {
		return create_type;
	}
	/**
	 * @param createType the create_type to set
	 */
	public void setCreate_type(int createType) {
		create_type = createType;
	}
	/**
	 * @return the create_time
	 */
	public int getCreate_time() {
		return create_time;
	}
	/**
	 * @param createTime the create_time to set
	 */
	public void setCreate_time(int createTime) {
		create_time = createTime;
	}
	
	
}

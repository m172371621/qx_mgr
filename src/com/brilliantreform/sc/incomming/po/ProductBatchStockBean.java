package com.brilliantreform.sc.incomming.po;

import java.util.Date;

public class ProductBatchStockBean {

	private int batch_id;//
	private String batch_serial;//批次号
	private int community_id;//小区
	private int product_id;//商品ID
	private double incommint_price;//进货单价
	private double stock_sum;//总数量
	private double order_current_sum;//当前数量 （备注：退货，损耗，调拨出 数量扣最新一批的，售卖订单扣最老一批的）
	private Date create_time;//创建时间
	
	
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
	 * @return the batch_id
	 */
	public int getBatch_id() {
		return batch_id;
	}
	/**
	 * @param batchId the batch_id to set
	 */
	public void setBatch_id(int batchId) {
		batch_id = batchId;
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
	 * @return the incommint_price
	 */
	public double getIncommint_price() {
		return incommint_price;
	}
	/**
	 * @param incommintPrice the incommint_price to set
	 */
	public void setIncommint_price(double incommintPrice) {
		incommint_price = incommintPrice;
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
	
	
}

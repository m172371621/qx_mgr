package com.brilliantreform.sc.incomming.po;

import java.util.Date;

public class ProductRealStockBean {

	private int product_id;//商品ID
	private int community_id;//小区ID
	private double real_stock_sum;//库存总量
	private double pre_incomming_price;//当前进货价格
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
	 * @return the real_stock_sum
	 */
	public double getReal_stock_sum() {
		return real_stock_sum;
	}
	/**
	 * @param realStockSum the real_stock_sum to set
	 */
	public void setReal_stock_sum(double realStockSum) {
		real_stock_sum = realStockSum;
	}
	/**
	 * @return the pre_incomming_price
	 */
	public double getPre_incomming_price() {
		return pre_incomming_price;
	}
	/**
	 * @param preIncommingPrice the pre_incomming_price to set
	 */
	public void setPre_incomming_price(double preIncommingPrice) {
		pre_incomming_price = preIncommingPrice;
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

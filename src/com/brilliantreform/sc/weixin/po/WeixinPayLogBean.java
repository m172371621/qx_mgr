package com.brilliantreform.sc.weixin.po;

public class WeixinPayLogBean {
	private Integer log_id;//日志id
	private String out_trade_no;//订单号
	private String bind_time;//支付时间
	private String order_price;//订单总额
	private String phone;//用户手机号
	private String pay_price;//支付金额
	private String retdata;//返回数据
	private String qxcard_pay_price;//区享卡支付总额
	private Double total_fee;
	/**
	 * @return the log_id
	 */
	public Integer getLog_id() {
		return log_id;
	}
	/**
	 * @param logId the log_id to set
	 */
	public void setLog_id(Integer logId) {
		log_id = logId;
	}
	/**
	 * @return the out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}
	/**
	 * @param outTradeNo the out_trade_no to set
	 */
	public void setOut_trade_no(String outTradeNo) {
		out_trade_no = outTradeNo;
	}
	/**
	 * @return the bind_time
	 */
	public String getBind_time() {
		return bind_time;
	}
	/**
	 * @param bindTime the bind_time to set
	 */
	public void setBind_time(String bindTime) {
		bind_time = bindTime;
	}
	/**
	 * @return the order_price
	 */
	public String getOrder_price() {
		return order_price;
	}
	/**
	 * @param orderPrice the order_price to set
	 */
	public void setOrder_price(String orderPrice) {
		order_price = orderPrice;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the pay_price
	 */
	public String getPay_price() {
		return pay_price;
	}
	/**
	 * @param payPrice the pay_price to set
	 */
	public void setPay_price(String payPrice) {
		pay_price = payPrice;
	}
	/**
	 * @return the retdata
	 */
	public String getRetdata() {
		return retdata;
	}
	/**
	 * @param retdata the retdata to set
	 */
	public void setRetdata(String retdata) {
		this.retdata = retdata;
	}
	/**
	 * @return the qxcard_pay_price
	 */
	public String getQxcard_pay_price() {
		return qxcard_pay_price;
	}
	/**
	 * @param qxcardPayPrice the qxcard_pay_price to set
	 */
	public void setQxcard_pay_price(String qxcardPayPrice) {
		qxcard_pay_price = qxcardPayPrice;
	}

	public Double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
}

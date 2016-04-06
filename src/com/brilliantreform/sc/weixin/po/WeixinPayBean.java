package com.brilliantreform.sc.weixin.po;

public class WeixinPayBean {

	private int log_id;
	private String out_trade_no;
	private String retdata;
	private String bind_time;
	private Integer total_fee;
	
	
	/**
	 * @return the total_fee
	 */
	public Integer getTotal_fee() {
		return total_fee;
	}
	/**
	 * @param totalFee the total_fee to set
	 */
	public void setTotal_fee(Integer totalFee) {
		total_fee = totalFee;
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
	
	
}

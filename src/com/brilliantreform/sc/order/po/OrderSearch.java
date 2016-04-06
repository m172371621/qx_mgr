package com.brilliantreform.sc.order.po;

public class OrderSearch {
	
	
	private String order_serial;
	private String user_name;
	private String user_phone;
	private String product_name;
	private Integer order_status;
	private Integer pay_type;
	private Integer cid;
	private Integer sid;
	private String start_time;
	private String end_time;
	private String pickup_start_time;
	private String pickup_end_time;
	private int begin;
	private int size;
	private Integer distri_staus;//配送状态

	public Integer getDistri_staus() {
		return distri_staus;
	}
	public void setDistri_staus(Integer distriStaus) {
		distri_staus = distriStaus;
	}
	public String getPickup_start_time() {
		return pickup_start_time;
	}
	public void setPickup_start_time(String pickupStartTime) {
		pickup_start_time = pickupStartTime;
	}
	public String getPickup_end_time() {
		return pickup_end_time;
	}
	public void setPickup_end_time(String pickupEndTime) {
		pickup_end_time = pickupEndTime;
	}
	public Integer getPay_type() {
		return pay_type;
	}
	public void setPay_type(Integer payType) {
		pay_type = payType;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getOrder_serial() {
		return order_serial;
	}
	public void setOrder_serial(String orderSerial) {
		order_serial = orderSerial;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String userPhone) {
		user_phone = userPhone;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String productName) {
		product_name = productName;
	}
	public Integer getOrder_status() {
		return order_status;
	}
	public void setOrder_status(Integer orderStatus) {
		order_status = orderStatus;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String startTime) {
		start_time = startTime;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String endTime) {
		end_time = endTime;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderSearch [begin=");
		builder.append(begin);
		builder.append(", end_time=");
		builder.append(end_time);
		builder.append(", order_serial=");
		builder.append(order_serial);
		builder.append(", order_status=");
		builder.append(order_status);
		builder.append(", product_name=");
		builder.append(product_name);
		builder.append(", size=");
		builder.append(size);
		builder.append(", cid=");
		builder.append(cid);
		builder.append(", sid=");
		builder.append(sid);
		builder.append(", start_time=");
		builder.append(start_time);
		builder.append(", user_name=");
		builder.append(user_name);
		builder.append(", user_phone=");
		builder.append(user_phone);
		builder.append(", distri_staus=");
		builder.append(distri_staus);
		builder.append("]");
		return builder.toString();
	}
	
	
}

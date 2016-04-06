package com.brilliantreform.sc.task.po;

/**
 * 预约订单表
 * @author Lm
 *
 */
public class Service_order {
	private Integer order_id;       
	private Integer service_id;                       
	private Integer user_id;                         
	private Integer community_id;                 
	private String service_name;                
	private String service_img;                 
	private String order_dec;                 
	private String order_price;               
	private Integer order_status;  //'1 预约 2 接受 3 完成'               
	private String order_time;               
	private String accept_time;              
	private String finish_time;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	private String username;
	private String phone;
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer orderId) {
		order_id = orderId;
	}
	public Integer getService_id() {
		return service_id;
	}
	public void setService_id(Integer serviceId) {
		service_id = serviceId;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer userId) {
		user_id = userId;
	}
	public Integer getCommunity_id() {
		return community_id;
	}
	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String serviceName) {
		service_name = serviceName;
	}
	public String getService_img() {
		return service_img;
	}
	public void setService_img(String serviceImg) {
		service_img = serviceImg;
	}
	public String getOrder_dec() {
		return order_dec;
	}
	public void setOrder_dec(String orderDec) {
		order_dec = orderDec;
	}
	public String getOrder_price() {
		return order_price;
	}
	public void setOrder_price(String orderPrice) {
		order_price = orderPrice;
	}
	public Integer getOrder_status() {
		return order_status;
	}
	public void setOrder_status(Integer orderStatus) {
		order_status = orderStatus;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String orderTime) {
		order_time = orderTime;
	}
	public String getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(String acceptTime) {
		accept_time = acceptTime;
	}
	public String getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(String finishTime) {
		finish_time = finishTime;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Service_order [accept_time=");
		builder.append(accept_time);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", finish_time=");
		builder.append(finish_time);
		builder.append(", order_dec=");
		builder.append(order_dec);
		builder.append(", order_id=");
		builder.append(order_id);
		builder.append(", order_price=");
		builder.append(order_price);
		builder.append(", order_status=");
		builder.append(order_status);
		builder.append(", order_time=");
		builder.append(order_time);
		builder.append(", service_id=");
		builder.append(service_id);
		builder.append(", service_img=");
		builder.append(service_img);
		builder.append(", service_name=");
		builder.append(service_name);
		builder.append(", user_id=");
		builder.append(", username=");
		builder.append(username);
		builder.append(", phone=");
		builder.append(phone);
		builder.append("]");
		return builder.toString();
	} 
	
	
}

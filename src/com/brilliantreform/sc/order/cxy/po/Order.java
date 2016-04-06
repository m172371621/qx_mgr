package com.brilliantreform.sc.order.cxy.po;

import java.sql.Timestamp;

public class Order {
	private Integer order_id;
	private String order_serial;
	private Integer order_type;// 1 商品类 2服务类3模糊订单
	private Integer user_id;
	private Integer product_id;
	private Integer product_amount;
	private Float product_price;
	private Float order_price;
	private String delivery_addr;
	private Integer delivery_type;// 1 自取 2送货上门',
	private Float delivery_price;
	private Integer product_period;
	private Integer pay_type;// 1 货到付款 2 在线支付',
	private Integer order_status;// 1 订单已提交 2 商品到货 3 已提货', 11 待定价 12已定价
	private Integer comment_status;// 1 未评价 2 已评价',
	private Timestamp order_time;
	private Timestamp delivery_time;
	private Timestamp pay_time;
	private Timestamp pickup_time;

	private String product_pic;
	private String product_name;
	private String c_name;
	
	private String user_name;
	private String user_phone;
	
	
	public String getC_name() {
		return c_name;
	}

	public void setC_name(String cName) {
		c_name = cName;
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



	public Integer getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Integer orderType) {
		order_type = orderType;
	}

	public Timestamp getPickup_time() {
		return pickup_time;
	}

	public void setPickup_time(Timestamp pickupTime) {
		pickup_time = pickupTime;
	}

	public String getProduct_pic() {
		return product_pic;
	}

	public void setProduct_pic(String productPic) {
		product_pic = productPic;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String productName) {
		product_name = productName;
	}

	public String getDelivery_addr() {
		return delivery_addr;
	}

	public void setDelivery_addr(String deliveryAddr) {
		delivery_addr = deliveryAddr;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer orderId) {
		order_id = orderId;
	}

	public String getOrder_serial() {
		return order_serial;
	}

	public void setOrder_serial(String orderSerial) {
		order_serial = orderSerial;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer userId) {
		user_id = userId;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer productId) {
		product_id = productId;
	}

	public Integer getProduct_amount() {
		return product_amount;
	}

	public void setProduct_amount(Integer productAmount) {
		product_amount = productAmount;
	}

	public Float getProduct_price() {
		return product_price;
	}

	public void setProduct_price(Float productPrice) {
		product_price = productPrice;
	}

	public Float getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Float orderPrice) {
		order_price = orderPrice;
	}

	public Integer getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(Integer deliveryType) {
		delivery_type = deliveryType;
	}

	public Float getDelivery_price() {
		return delivery_price;
	}

	public void setDelivery_price(Float deliveryPrice) {
		delivery_price = deliveryPrice;
	}

	public Integer getProduct_period() {
		return product_period;
	}

	public void setProduct_period(Integer productPeriod) {
		product_period = productPeriod;
	}

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer payType) {
		pay_type = payType;
	}

	public Integer getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Integer orderStatus) {
		order_status = orderStatus;
	}

	public Integer getComment_status() {
		return comment_status;
	}

	public void setComment_status(Integer commentStatus) {
		comment_status = commentStatus;
	}

	public Timestamp getOrder_time() {
		return order_time;
	}

	public void setOrder_time(Timestamp orderTime) {
		order_time = orderTime;
	}

	public Timestamp getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(Timestamp deliveryTime) {
		delivery_time = deliveryTime;
	}

	public Timestamp getPay_time() {
		return pay_time;
	}

	public void setPay_time(Timestamp payTime) {
		pay_time = payTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [comment_status=");
		builder.append(comment_status);
		builder.append(", delivery_addr=");
		builder.append(delivery_addr);
		builder.append(", delivery_price=");
		builder.append(delivery_price);
		builder.append(", delivery_time=");
		builder.append(delivery_time);
		builder.append(", delivery_type=");
		builder.append(delivery_type);
		builder.append(", order_id=");
		builder.append(order_id);
		builder.append(", order_price=");
		builder.append(order_price);
		builder.append(", order_serial=");
		builder.append(order_serial);
		builder.append(", order_status=");
		builder.append(order_status);
		builder.append(", order_time=");
		builder.append(order_time);
		builder.append(", order_type=");
		builder.append(order_type);
		builder.append(", pay_time=");
		builder.append(pay_time);
		builder.append(", pay_type=");
		builder.append(pay_type);
		builder.append(", pickup_time=");
		builder.append(pickup_time);
		builder.append(", product_amount=");
		builder.append(product_amount);
		builder.append(", product_id=");
		builder.append(product_id);
		builder.append(", product_name=");
		builder.append(product_name);
		builder.append(", product_period=");
		builder.append(product_period);
		builder.append(", product_pic=");
		builder.append(product_pic);
		builder.append(", product_price=");
		builder.append(product_price);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append("]");
		return builder.toString();
	}

}

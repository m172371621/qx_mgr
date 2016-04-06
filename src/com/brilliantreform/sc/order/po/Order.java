package com.brilliantreform.sc.order.po;

import java.sql.Timestamp;
import java.util.Date;

public class Order {
	private Integer order_id;
	private String order_serial;
	private Integer order_type;// 1 商品类 2服务类3模糊订单
	private Integer user_id;
	private Integer product_id;
	private Double product_amount;
	private Double product_price;
	private Double order_price;
	private String delivery_addr;
	private String order_phone;
	private Integer delivery_type;// 1 自取 2送货上门',
	private Double delivery_price;
	private Integer product_period;
	private Integer pay_type;// 1 货到付款 2 在线支付',
	private Integer order_status;// 1 订单已提交 2 商品到货 3 已提货', 11 待定价 12已定价, 21 未付款 22 已付款 23 取消
	private Integer comment_status;// 1 未评价 2 已评价',
	private Date order_time;
	private Date delivery_time;
	private Date pay_time;
	private Date pickup_time;

	private String channel;
	
	private String product_pic;
	private String product_name;
	private String c_name;
	
	private String user_name;
	private String user_phone;
	
	/** 3.0 新增*/
	private String str_delivery_time;
	private Integer user_age;
	private Integer user_sex;
	private String ip ;
	private String salesman;
	private Integer pay_type_ext;// 1 货到付款 2 在线支付',
	private Double payoff_price;
	private Double cost_price;
	
	private Integer order_base_status;

    private Integer coupon_id;

	public Integer getOrder_base_status() {
		return order_base_status;
	}

	public void setOrder_base_status(Integer order_base_status) {
		this.order_base_status = order_base_status;
	}

	public String getStr_delivery_time() {
		return str_delivery_time;
	}

	public void setStr_delivery_time(String strDeliveryTime) {
		str_delivery_time = strDeliveryTime;
	}

	public Integer getUser_age() {
		return user_age;
	}

	public void setUser_age(Integer userAge) {
		user_age = userAge;
	}

	public Integer getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(Integer userSex) {
		user_sex = userSex;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public Integer getPay_type_ext() {
		return pay_type_ext;
	}

	public void setPay_type_ext(Integer payTypeExt) {
		pay_type_ext = payTypeExt;
	}

	public Double getPayoff_price() {
		return payoff_price;
	}

	public void setPayoff_price(Double payoffPrice) {
		payoff_price = payoffPrice;
	}

	public Double getCost_price() {
		return cost_price;
	}

	public void setCost_price(Double costPrice) {
		cost_price = costPrice;
	}

	private Integer community_id;
	//配送状态
	private int distri_staus;//'配送状态 0=未完成 1=完成'
	
	public int getDistri_staus() {
		return distri_staus;
	}

	public void setDistri_staus(int distriStaus) {
		distri_staus = distriStaus;
	}

	public String getOrder_phone() {
		return order_phone;
	}

	public void setOrder_phone(String orderPhone) {
		order_phone = orderPhone;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public Date getPickup_time() {
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

	public Double getProduct_amount() {
		return product_amount;
	}

	public void setProduct_amount(Double productAmount) {
		product_amount = productAmount;
	}

	public Double getProduct_price() {
		return product_price;
	}

	public void setProduct_price(Double productPrice) {
		product_price = productPrice;
	}

	public Double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Double orderPrice) {
		order_price = orderPrice;
	}

	public Integer getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(Integer deliveryType) {
		delivery_type = deliveryType;
	}

	public Double getDelivery_price() {
		return delivery_price;
	}

	public void setDelivery_price(Double deliveryPrice) {
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

	public Date getOrder_time() {
		return order_time;
	}

	public void setOrder_time(Timestamp orderTime) {
		order_time = orderTime;
	}

	public Date getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(Date deliveryTime) {
		delivery_time = deliveryTime;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Timestamp payTime) {
		pay_time = payTime;
	}

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [c_name=");
		builder.append(c_name);
		builder.append(", channel=");
		builder.append(channel);
		builder.append(", comment_status=");
		builder.append(comment_status);
		builder.append(", community_id=");
		builder.append(community_id);
		builder.append(", cost_price=");
		builder.append(cost_price);
		builder.append(", delivery_addr=");
		builder.append(delivery_addr);
		builder.append(", delivery_price=");
		builder.append(delivery_price);
		builder.append(", delivery_time=");
		builder.append(delivery_time);
		builder.append(", delivery_type=");
		builder.append(delivery_type);
		builder.append(", distri_staus=");
		builder.append(distri_staus);
		builder.append(", ip=");
		builder.append(ip);
		builder.append(", order_id=");
		builder.append(order_id);
		builder.append(", order_phone=");
		builder.append(order_phone);
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
		builder.append(", pay_type_ext=");
		builder.append(pay_type_ext);
		builder.append(", payoff_price=");
		builder.append(payoff_price);
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
		builder.append(", salesman=");
		builder.append(salesman);
		builder.append(", str_delivery_time=");
		builder.append(str_delivery_time);
		builder.append(", user_age=");
		builder.append(user_age);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append(", user_name=");
		builder.append(user_name);
		builder.append(", user_phone=");
		builder.append(user_phone);
		builder.append(", user_sex=");
		builder.append(user_sex);
		builder.append("]");
		return builder.toString();
	}

}

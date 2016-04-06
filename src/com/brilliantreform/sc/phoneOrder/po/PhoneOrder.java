package com.brilliantreform.sc.phoneOrder.po;

import java.sql.Timestamp;
import java.util.List;


public class PhoneOrder {
    private Integer order_id;
    private String order_serial;
    private Integer order_type;// 1 商品类 2服务类3模糊订单
    private Integer user_id;
    private Integer community_id;
    private Integer product_id;
    private Integer product_amount;
    private Float product_price;
    private Float order_price;
    private String order_phone;
    private String delivery_addr;
    private Integer delivery_type;// 1 自取 2送货上门',
    private Float delivery_price;
    private Integer product_period;
    private Integer pay_type;// 1 货到付款 2 在线支付',
    private Integer order_status;// 1 订单已提交 2 商品到货 3 已提货, 11 待定价 12已定价  21 待支付 22 已付款  23 已取消
    private Integer comment_status;// 1 未评价 2 已评价',
    private String order_time;
    private String delivery_time;
    private Timestamp pay_time;
    private String pickup_time;

    private String product_pic;
    private String product_name;

    private String channel;//1=ios 2=android 3=weixin

    private List<PhoneOrder> subList;//子商品列表

    private String delivery_phone;//配送手机

    private String nick; //昵称
    private String phone; // 用户手机
    private String create_time;

    private String finish_time;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrder_phone() {
        return order_phone;
    }

    public void setOrder_phone(String orderPhone) {
        order_phone = orderPhone;
    }

    public Integer getOrder_type() {
        return order_type;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer communityId) {
        community_id = communityId;
    }

    public void setOrder_type(Integer orderType) {
        order_type = orderType;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickupTime) {
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

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String orderTime) {
        order_time = orderTime;
    }


    /**
     * @return the delivery_time
     */
    public String getDelivery_time() {
        return delivery_time;
    }

    /**
     * @param deliveryTime the delivery_time to set
     */
    public void setDelivery_time(String deliveryTime) {
        delivery_time = deliveryTime;
    }

    public Timestamp getPay_time() {
        return pay_time;
    }

    public void setPay_time(Timestamp payTime) {
        pay_time = payTime;
    }


    /**
     * @return the subList
     */
    public List<PhoneOrder> getSubList() {
        return subList;
    }

    /**
     * @param subList the subList to set
     */
    public void setSubList(List<PhoneOrder> subList) {
        this.subList = subList;
    }

    /**
     * @return the delivery_phone
     */
    public String getDelivery_phone() {
        return delivery_phone;
    }

    /**
     * @param deliveryPhone the delivery_phone to set
     */
    public void setDelivery_phone(String deliveryPhone) {
        delivery_phone = deliveryPhone;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order [comment_status=");
        builder.append(comment_status);
        builder.append(", community_id=");
        builder.append(community_id);
        builder.append(", order_phone=");
        builder.append(order_phone);
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

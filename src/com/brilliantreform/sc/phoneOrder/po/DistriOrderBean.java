package com.brilliantreform.sc.phoneOrder.po;

import java.util.List;


public class DistriOrderBean {

	private Integer distri_id;//
	private String distri_num;// 配送单号：小区编号（4位）+操作员编号（4位）+时间戳
	private Integer distri_worker_id;// 配送员
	private Integer distri_head_status;// 配送单状态
	private Integer distri_community_id;//小区编号
	private String create_time;// 生成时间
	private String finish_time;// 完成时间
	private Integer distri_detail_id;//
	private String nickName;// 昵称
	private String phone;// 手机号
	private String addr;// 地址
	private String order_price;// 订单总额
	private String pay_staus;// 支付状态
	private Integer distri_staus;// 配送状态 0=待配送 1=配送中 2=完成 3=暂时删除 4=永久删除 5=用户拒收
	private String remark;// 备注
	private Integer distri_product_id;//
	private String product_id;// 商品id
	private String product_name;// 商品名称
	private String product_price;// 商品总额
	private String product_amount;// 商品数量
	private String distri_worker_name;// 配送员姓名
	private String distri_worker_phone;// 配送员手机
	private String distri_worker_addr;// 配送员地址
	private String distri_worker_status;// 配送员状态
	private Integer order_id;//单据id
	private Integer productCount;//商品数量
	private String distri_worker_login_name;//登录名
	private String distri_worker_login_pwd;//登录密码
	private String order_serial;
	private String delivery_time;//配送时间
	private List<DistriOrderBean> subList;//子商品列表
	private String delivery_addr;
	private String delivery_phone;//用户手机
	private Integer user_id; //用户Id

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the delivery_addr
	 */
	public String getDelivery_addr() {
		return delivery_addr;
	}

	/**
	 * @param deliveryAddr the delivery_addr to set
	 */
	public void setDelivery_addr(String deliveryAddr) {
		delivery_addr = deliveryAddr;
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

	/**
	 * @return the subList
	 */
	public List<DistriOrderBean> getSubList() {
		return subList;
	}

	/**
	 * @param subList the subList to set
	 */
	public void setSubList(List<DistriOrderBean> subList) {
		this.subList = subList;
	}

	/**
	 * @return the order_serial
	 */
	public String getOrder_serial() {
		return order_serial;
	}

	/**
	 * @param orderSerial the order_serial to set
	 */
	public void setOrder_serial(String orderSerial) {
		order_serial = orderSerial;
	}

	/**
	 * @return the distri_worker_login_name
	 */
	public String getDistri_worker_login_name() {
		return distri_worker_login_name;
	}

	/**
	 * @param distriWorkerLoginName the distri_worker_login_name to set
	 */
	public void setDistri_worker_login_name(String distriWorkerLoginName) {
		distri_worker_login_name = distriWorkerLoginName;
	}

	/**
	 * @return the distri_worker_login_pwd
	 */
	public String getDistri_worker_login_pwd() {
		return distri_worker_login_pwd;
	}

	/**
	 * @param distriWorkerLoginPwd the distri_worker_login_pwd to set
	 */
	public void setDistri_worker_login_pwd(String distriWorkerLoginPwd) {
		distri_worker_login_pwd = distriWorkerLoginPwd;
	}

	/**
	 * @return the productCount
	 */
	public Integer getProductCount() {
		return productCount;
	}

	/**
	 * @param productCount the productCount to set
	 */
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	/**
	 * @return the order_id
	 */
	public Integer getOrder_id() {
		return order_id;
	}

	/**
	 * @param orderId the order_id to set
	 */
	public void setOrder_id(Integer orderId) {
		order_id = orderId;
	}

	/**
	 * @return the distri_id
	 */
	public Integer getDistri_id() {
		return distri_id;
	}

	/**
	 * @param distriId the distri_id to set
	 */
	public void setDistri_id(Integer distriId) {
		distri_id = distriId;
	}

	/**
	 * @return the distri_head_status
	 */
	public Integer getDistri_head_status() {
		return distri_head_status;
	}

	/**
	 * @param distriHeadStatus the distri_head_status to set
	 */
	public void setDistri_head_status(Integer distriHeadStatus) {
		distri_head_status = distriHeadStatus;
	}


	/**
	 * @return the distri_num
	 */
	public String getDistri_num() {
		return distri_num;
	}

	/**
	 * @param distriNum
	 *            the distri_num to set
	 */
	public void setDistri_num(String distriNum) {
		distri_num = distriNum;
	}


	/**
	 * @return the distri_worker_id
	 */
	public Integer getDistri_worker_id() {
		return distri_worker_id;
	}

	/**
	 * @param distriWorkerId the distri_worker_id to set
	 */
	public void setDistri_worker_id(Integer distriWorkerId) {
		distri_worker_id = distriWorkerId;
	}

	/**
	 * @return the create_time
	 */
	public String getCreate_time() {
		return create_time;
	}

	/**
	 * @param createTime
	 *            the create_time to set
	 */
	public void setCreate_time(String createTime) {
		create_time = createTime;
	}

	/**
	 * @return the finish_time
	 */
	public String getFinish_time() {
		return finish_time;
	}

	/**
	 * @param finishTime
	 *            the finish_time to set
	 */
	public void setFinish_time(String finishTime) {
		finish_time = finishTime;
	}


	/**
	 * @return the distri_detail_id
	 */
	public Integer getDistri_detail_id() {
		return distri_detail_id;
	}

	/**
	 * @param distriDetailId the distri_detail_id to set
	 */
	public void setDistri_detail_id(Integer distriDetailId) {
		distri_detail_id = distriDetailId;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr
	 *            the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return the order_price
	 */
	public String getOrder_price() {
		return order_price;
	}

	/**
	 * @param orderPrice
	 *            the order_price to set
	 */
	public void setOrder_price(String orderPrice) {
		order_price = orderPrice;
	}

	/**
	 * @return the pay_staus
	 */
	public String getPay_staus() {
		return pay_staus;
	}

	/**
	 * @param payStaus
	 *            the pay_staus to set
	 */
	public void setPay_staus(String payStaus) {
		pay_staus = payStaus;
	}


	/**
	 * @return the distri_staus
	 */
	public Integer getDistri_staus() {
		return distri_staus;
	}

	/**
	 * @param distriStaus the distri_staus to set
	 */
	public void setDistri_staus(Integer distriStaus) {
		distri_staus = distriStaus;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}


	/**
	 * @return the distri_product_id
	 */
	public Integer getDistri_product_id() {
		return distri_product_id;
	}

	/**
	 * @param distriProductId the distri_product_id to set
	 */
	public void setDistri_product_id(Integer distriProductId) {
		distri_product_id = distriProductId;
	}

	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}

	/**
	 * @param productId
	 *            the product_id to set
	 */
	public void setProduct_id(String productId) {
		product_id = productId;
	}

	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}

	/**
	 * @param productName
	 *            the product_name to set
	 */
	public void setProduct_name(String productName) {
		product_name = productName;
	}

	/**
	 * @return the product_price
	 */
	public String getProduct_price() {
		return product_price;
	}

	/**
	 * @param productPrice
	 *            the product_price to set
	 */
	public void setProduct_price(String productPrice) {
		product_price = productPrice;
	}

	/**
	 * @return the distri_worker_name
	 */
	public String getDistri_worker_name() {
		return distri_worker_name;
	}

	/**
	 * @param distriWorkerName
	 *            the distri_worker_name to set
	 */
	public void setDistri_worker_name(String distriWorkerName) {
		distri_worker_name = distriWorkerName;
	}

	/**
	 * @return the distri_worker_phone
	 */
	public String getDistri_worker_phone() {
		return distri_worker_phone;
	}

	/**
	 * @param distriWorkerPhone
	 *            the distri_worker_phone to set
	 */
	public void setDistri_worker_phone(String distriWorkerPhone) {
		distri_worker_phone = distriWorkerPhone;
	}

	/**
	 * @return the distri_worker_addr
	 */
	public String getDistri_worker_addr() {
		return distri_worker_addr;
	}

	/**
	 * @param distriWorkerAddr
	 *            the distri_worker_addr to set
	 */
	public void setDistri_worker_addr(String distriWorkerAddr) {
		distri_worker_addr = distriWorkerAddr;
	}

	/**
	 * @return the distri_worker_status
	 */
	public String getDistri_worker_status() {
		return distri_worker_status;
	}

	/**
	 * @param distriWorkerStatus
	 *            the distri_worker_status to set
	 */
	public void setDistri_worker_status(String distriWorkerStatus) {
		distri_worker_status = distriWorkerStatus;
	}


	/**
	 * @return the distri_community_id
	 */
	public Integer getDistri_community_id() {
		return distri_community_id;
	}

	/**
	 * @param distriCommunityId the distri_community_id to set
	 */
	public void setDistri_community_id(Integer distriCommunityId) {
		distri_community_id = distriCommunityId;
	}

	/**
	 * @return the product_amount
	 */
	public String getProduct_amount() {
		return product_amount;
	}

	/**
	 * @param productAmount the product_amount to set
	 */
	public void setProduct_amount(String productAmount) {
		product_amount = productAmount;
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

}

package com.brilliantreform.sc.order.po;

/**
 * 录入，干洗 金额，备注 快递 金额，备注 皮具 金额，备注 摇摇 车金额，备注 小区的编号
 * 
 * @author Lm
 * 
 */
public class AddInfo {
	private Integer service_sumery_id; // 汇总id
	private Float cleaning_price; // 干洗金额
	private String cleaning_remarks; // 干洗备注
	private Float order_price; // 快递金额
	private String order_remarks; // 快递备注
	private Float leather_price; // 皮具金额
	private String leather_remarks; // 皮具备注
	private Float rocking_car_price; // 摇摇车金额
	private String rocking_car_remarks; // 摇摇车备注
	private Integer Community_id; // 小区id

	public Integer getCommunity_id() {
		return Community_id;
	}

	public void setCommunity_id(Integer communityId) {
		Community_id = communityId;
	}

	public Integer getService_sumery_id() {
		return service_sumery_id;
	}

	public void setService_sumery_id(Integer serviceSumeryId) {
		service_sumery_id = serviceSumeryId;
	}

	public Float getCleaning_price() {
		return cleaning_price;
	}

	public void setCleaning_price(Float cleaningPrice) {
		cleaning_price = cleaningPrice;
	}

	public String getCleaning_remarks() {
		return cleaning_remarks;
	}

	public void setCleaning_remarks(String cleaningRemarks) {
		cleaning_remarks = cleaningRemarks;
	}

	public Float getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Float orderPrice) {
		order_price = orderPrice;
	}

	public String getOrder_remarks() {
		return order_remarks;
	}

	public void setOrder_remarks(String orderRemarks) {
		order_remarks = orderRemarks;
	}

	public Float getLeather_price() {
		return leather_price;
	}

	public void setLeather_price(Float leatherPrice) {
		leather_price = leatherPrice;
	}

	public String getLeather_remarks() {
		return leather_remarks;
	}

	public void setLeather_remarks(String leatherRemarks) {
		leather_remarks = leatherRemarks;
	}

	public Float getRocking_car_price() {
		return rocking_car_price;
	}

	public void setRocking_car_price(Float rockingCarPrice) {
		rocking_car_price = rockingCarPrice;
	}

	public String getRocking_car_remarks() {
		return rocking_car_remarks;
	}

	public void setRocking_car_remarks(String rockingCarRemarks) {
		rocking_car_remarks = rockingCarRemarks;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddInfo [service_sumery_id=");
		builder.append(service_sumery_id);
		builder.append(", cleaning_price=");
		builder.append(cleaning_price);
		builder.append(", cleaning_remarks=");
		builder.append(cleaning_remarks);
		builder.append(", order_price=");
		builder.append(order_price);
		builder.append(", leather_price=");
		builder.append(leather_price);
		builder.append(", leather_remarks=");
		builder.append(leather_remarks);
		builder.append(", rocking_car_price=");
		builder.append(rocking_car_price);
		builder.append(", rocking_car_price=");
		builder.append(rocking_car_price);
		builder.append(", rocking_car_remarks=");
		builder.append(rocking_car_remarks);
		builder.append(", community_id=");
		builder.append(Community_id);
		
		return builder.toString();
	}
}
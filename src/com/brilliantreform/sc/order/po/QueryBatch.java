package com.brilliantreform.sc.order.po;

import java.util.Date;

/**
 * 批次查询 信息 实体类
 * 
 * @author Lm
 * 
 */
public class QueryBatch {
	private String batch_serial; // 批次
	private Integer stock_sum; // 总数量
	private String name; // 商品名称
	private String service_name; // 分类名称
	private Float incommint_price; // 进货价格
	private Integer community_id;
	private Integer product_id;
	private Date create_time; //创建时间
	private String create_by; //创建人
	private int begin;
	private int size;
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public Integer getCommunity_id() {
	
		return community_id;
	}

	public void setCommunity_id(Integer communityId) {
		community_id = communityId;
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

	public String getBatch_serial() {
		return batch_serial;
	}

	public void setBatch_serial(String batchSerial) {
		batch_serial = batchSerial;
	}

	public Integer getStock_sum() {
		return stock_sum;
	}

	public void setStock_sum(Integer stockSum) {
		stock_sum = stockSum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String serviceName) {
		service_name = serviceName;
	}

	public Float getIncommint_price() {
		return incommint_price;
	}

	public void setIncommint_price(Float incommintPrice) {
		incommint_price = incommintPrice;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryBatch [batch_serial=");
		builder.append(batch_serial);
		builder.append(", incommint_price=");
		builder.append(incommint_price);
		builder.append(", name=");
		builder.append(name);
		builder.append(", service_name=");
		builder.append(service_name);
		builder.append(", stock_sum=");
		builder.append(stock_sum);
		builder.append(", begin=");
		builder.append(begin);
		builder.append(", size=");
		builder.append(size);
		return builder.toString();
	}

}

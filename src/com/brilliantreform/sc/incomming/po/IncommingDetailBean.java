package com.brilliantreform.sc.incomming.po;

import java.util.Date;

public class IncommingDetailBean {

	private Integer stockchange_detail_id;//明细id
	private Integer stockchange_header_id;//进货汇总表ID
	private String batch_serial;//批次号
	private Integer product_id;//商品ID
	private String product_name;//商品名称
	private String barcode;//商品条码
	private Double market_price;//市场价
	private Double price;//售价
	private Integer stock_type;//类型（进货，调拨入，退货，损耗，调拨出）
	private Double change_count;//变更数量
	private Double unit_price;//成本单价
	private Double pre_stock_count;//原库存数量
	private String create_by;//创建人
	private Date create_time;//创建时间
	private Integer start;
	private Integer size;
	private Integer community_id;//小区id
	private String ip;//ip
    private String remark;
    private Date deadline;
    private Integer supplier_id;
    private Integer puhuo_flag;


    public Integer getStockchange_detail_id() {
        return stockchange_detail_id;
    }

    public void setStockchange_detail_id(Integer stockchange_detail_id) {
        this.stockchange_detail_id = stockchange_detail_id;
    }

    public Integer getStockchange_header_id() {
        return stockchange_header_id;
    }

    public void setStockchange_header_id(Integer stockchange_header_id) {
        this.stockchange_header_id = stockchange_header_id;
    }

    public String getBatch_serial() {
        return batch_serial;
    }

    public void setBatch_serial(String batch_serial) {
        this.batch_serial = batch_serial;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(Double market_price) {
        this.market_price = market_price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock_type() {
        return stock_type;
    }

    public void setStock_type(Integer stock_type) {
        this.stock_type = stock_type;
    }

    public Double getChange_count() {
        return change_count;
    }

    public void setChange_count(Double change_count) {
        this.change_count = change_count;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Double getPre_stock_count() {
        return pre_stock_count;
    }

    public void setPre_stock_count(Double pre_stock_count) {
        this.pre_stock_count = pre_stock_count;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    public Integer getPuhuo_flag() {
        return puhuo_flag;
    }

    public void setPuhuo_flag(Integer puhuo_flag) {
        this.puhuo_flag = puhuo_flag;
    }
}

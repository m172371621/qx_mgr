package com.brilliantreform.sc.product.po;

public class ProductSearchBean {
	private String name;
	private String barcode;
	private Integer status;
	private Integer sid;
	private Integer cid;
	private Integer product_type;
	private Integer amount_from;
	private Integer amount_to;
	private int begin;
	private int size;

	
	public Integer getAmount_from() {
		return amount_from;
	}

	public void setAmount_from(Integer amountFrom) {
		amount_from = amountFrom;
	}

	public Integer getAmount_to() {
		return amount_to;
	}

	public void setAmount_to(Integer amountTo) {
		amount_to = amountTo;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getProduct_type() {
		return product_type;
	}

	public void setProduct_type(Integer productType) {
		product_type = productType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		builder.append("ProductSearchBean [begin=");
		builder.append(begin);
		builder.append(", cid=");
		builder.append(cid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", sid=");
		builder.append(sid);
		builder.append(", size=");
		builder.append(size);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}

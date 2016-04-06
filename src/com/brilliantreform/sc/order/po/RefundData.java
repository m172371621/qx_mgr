package com.brilliantreform.sc.order.po;

public class RefundData {
    private Integer order_id;
    private Integer product_id;
    private Double product_price;
    private Double refund_amount;
    private Double refund_money;

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    public Double getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(Double refund_amount) {
        this.refund_amount = refund_amount;
    }

    public Double getRefund_money() {
        return refund_money;
    }

    public void setRefund_money(Double refund_money) {
        this.refund_money = refund_money;
    }
}

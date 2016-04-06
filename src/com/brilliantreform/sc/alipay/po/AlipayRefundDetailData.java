package com.brilliantreform.sc.alipay.po;

/**
 * 支付宝退款单笔数据集
 * Created by shangwq on 2015/8/19.
 */
public class AlipayRefundDetailData {

    /**
     * 原付款支付宝交易号
     * */
    private String tradeNo;

    /**
     * 退款总金额
     * */
    private Double refundMoney;

    /**
     * 退款原因
     * */
    private String refundReason;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }
}

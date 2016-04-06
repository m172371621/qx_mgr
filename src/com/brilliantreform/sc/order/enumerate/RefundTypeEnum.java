package com.brilliantreform.sc.order.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 退款方式
 */
public class RefundTypeEnum extends ValuedEnum {
    protected RefundTypeEnum(String name, int value) {
        super(name, value);
    }

    public static final RefundTypeEnum CASH = new RefundTypeEnum("现金", 1);
    public static final RefundTypeEnum QXCARD = new RefundTypeEnum("区享卡", 2);
    public static final RefundTypeEnum ALIPAY = new RefundTypeEnum("支付宝", 3);
    public static final RefundTypeEnum WXPAY = new RefundTypeEnum("微信", 4);

}

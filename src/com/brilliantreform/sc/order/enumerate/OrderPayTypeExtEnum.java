package com.brilliantreform.sc.order.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 订单付款方式
 */
public class OrderPayTypeExtEnum extends ValuedEnum {
    protected OrderPayTypeExtEnum(String name, int value) {
        super(name, value);
    }

    public static final OrderPayTypeExtEnum CASH = new OrderPayTypeExtEnum("现金", 11);
    public static final OrderPayTypeExtEnum CARD = new OrderPayTypeExtEnum("刷卡", 12);
    public static final OrderPayTypeExtEnum BOTH = new OrderPayTypeExtEnum("混合", 13);
    public static final OrderPayTypeExtEnum QXCARD = new OrderPayTypeExtEnum("区享卡", 21);
    public static final OrderPayTypeExtEnum ALIPAY = new OrderPayTypeExtEnum("支付宝", 22);
    public static final OrderPayTypeExtEnum WXPAY = new OrderPayTypeExtEnum("微信", 23);

}

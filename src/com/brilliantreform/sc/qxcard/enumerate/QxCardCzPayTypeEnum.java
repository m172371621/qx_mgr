package com.brilliantreform.sc.qxcard.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 区享卡充值支付方式
 */
public class QxCardCzPayTypeEnum extends ValuedEnum {
    protected QxCardCzPayTypeEnum(String name, int value) {
        super(name, value);
    }

    public static final QxCardCzPayTypeEnum CASH = new QxCardCzPayTypeEnum("现金", 1);
    public static final QxCardCzPayTypeEnum ALIPAY = new QxCardCzPayTypeEnum("支付宝", 2);
    public static final QxCardCzPayTypeEnum WEIXIN = new QxCardCzPayTypeEnum("微信", 3);

}

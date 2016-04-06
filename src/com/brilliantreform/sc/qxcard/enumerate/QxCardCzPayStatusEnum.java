package com.brilliantreform.sc.qxcard.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 区享卡充值支付状态
 */
public class QxCardCzPayStatusEnum extends ValuedEnum {
    protected QxCardCzPayStatusEnum(String name, int value) {
        super(name, value);
    }

    public static final QxCardCzPayStatusEnum NO = new QxCardCzPayStatusEnum("未支付", 0);
    public static final QxCardCzPayStatusEnum YES = new QxCardCzPayStatusEnum("已支付", 1);

}

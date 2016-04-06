package com.brilliantreform.sc.qxcard.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 区享卡充值状态
 */
public class QxCardCzStatusEnum extends ValuedEnum {
    protected QxCardCzStatusEnum(String name, int value) {
        super(name, value);
    }

    public static final QxCardCzStatusEnum ING = new QxCardCzStatusEnum("未完成", 0);
    public static final QxCardCzStatusEnum FINISH = new QxCardCzStatusEnum("完成", 1);
    public static final QxCardCzStatusEnum CANCEL = new QxCardCzStatusEnum("取消", -1);

}

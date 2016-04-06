package com.brilliantreform.sc.order.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 配送状态
 */
public class DeliveryStatusEnum extends ValuedEnum {
    protected DeliveryStatusEnum(String name, int value) {
        super(name, value);
    }

    public static final DeliveryStatusEnum WAIT = new DeliveryStatusEnum("待取货", 0);
    public static final DeliveryStatusEnum ING = new DeliveryStatusEnum("配送中", 2);
    public static final DeliveryStatusEnum FINISH = new DeliveryStatusEnum("完成", 1);

}

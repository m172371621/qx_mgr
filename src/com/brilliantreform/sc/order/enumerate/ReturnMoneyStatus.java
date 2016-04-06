package com.brilliantreform.sc.order.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 订单退款状态
 * Created by shangwq on 2015/8/28.
 */
public class ReturnMoneyStatus extends ValuedEnum {
    protected ReturnMoneyStatus(String name, int value) {
        super(name, value);
    }

    public static final ReturnMoneyStatus WAIT = new ReturnMoneyStatus("待处理", 0);
    public static final ReturnMoneyStatus ING = new ReturnMoneyStatus("处理中", 1);
    public static final ReturnMoneyStatus FINISH = new ReturnMoneyStatus("完成", 2);
    public static final ReturnMoneyStatus FAIL = new ReturnMoneyStatus("失败", -1);
    public static final ReturnMoneyStatus DONOT = new ReturnMoneyStatus("无需退款", -2);

}

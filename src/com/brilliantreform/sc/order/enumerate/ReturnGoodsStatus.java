package com.brilliantreform.sc.order.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 订单退货状态
 */
public class ReturnGoodsStatus extends ValuedEnum {
    protected ReturnGoodsStatus(String name, int value) {
        super(name, value);
    }

    public static final ReturnGoodsStatus WAIT = new ReturnGoodsStatus("待处理", 0);
    public static final ReturnGoodsStatus FINISH = new ReturnGoodsStatus("完成", 1);
    public static final ReturnGoodsStatus FAIL = new ReturnGoodsStatus("驳回", -1);

}

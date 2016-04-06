package com.brilliantreform.sc.purchase.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 进货商品状态
 */
public class PurchaseStatus extends ValuedEnum {

    protected PurchaseStatus(String name, int value) {
        super(name, value);
    }

    public static final PurchaseStatus INIT = new PurchaseStatus("未发货", 1);
    public static final PurchaseStatus SENDED = new PurchaseStatus("已发货", 2);
    public static final PurchaseStatus CONFIRMED = new PurchaseStatus("已确认", 3);
}

package com.brilliantreform.sc.product.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

public class StockTypeEnum extends ValuedEnum {

    protected StockTypeEnum(String name, int value) {
        super(name, value);
    }

    public static final StockTypeEnum RK = new StockTypeEnum("入库", 1);
    public static final StockTypeEnum DR = new StockTypeEnum("调拨入", 2);
    public static final StockTypeEnum TH = new StockTypeEnum("退货", 3);
    public static final StockTypeEnum SH = new StockTypeEnum("损耗", 4);
    public static final StockTypeEnum DC = new StockTypeEnum("调拨出", 5);
}

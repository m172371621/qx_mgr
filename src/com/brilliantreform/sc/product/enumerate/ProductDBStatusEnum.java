package com.brilliantreform.sc.product.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

public class ProductDBStatusEnum extends ValuedEnum {

    protected ProductDBStatusEnum(String name, int value) {
        super(name, value);
    }

    public static final ProductDBStatusEnum INIT = new ProductDBStatusEnum("待审批", 0);
    public static final ProductDBStatusEnum YES = new ProductDBStatusEnum("已通过", 1);
    public static final ProductDBStatusEnum NO = new ProductDBStatusEnum("驳回", -1);
}

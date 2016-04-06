package com.brilliantreform.sc.activity.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

public class UserCouponStatusEnum extends ValuedEnum {
    protected UserCouponStatusEnum(String name, int value) {
        super(name, value);
    }

    public static final UserCouponStatusEnum UNUSE = new UserCouponStatusEnum("未使用", 1);
    public static final UserCouponStatusEnum EXPIRE = new UserCouponStatusEnum("过期", 2);
    public static final UserCouponStatusEnum USED = new UserCouponStatusEnum("已使用", 3);
}

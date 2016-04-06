package com.brilliantreform.sc.sys.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 消息队列明细状态
 * */
public class SendDetailStatusEnum extends ValuedEnum {

    protected SendDetailStatusEnum(String name, int value) {
        super(name, value);
    }

    public static final SendDetailStatusEnum FAIL = new SendDetailStatusEnum("发送失败", -1);
    public static final SendDetailStatusEnum INIT = new SendDetailStatusEnum("待发送", 0);
    public static final SendDetailStatusEnum SENDING = new SendDetailStatusEnum("发送中", 1);
    public static final SendDetailStatusEnum SUCCESS = new SendDetailStatusEnum("发送成功", 2);
}

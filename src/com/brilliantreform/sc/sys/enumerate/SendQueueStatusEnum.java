package com.brilliantreform.sc.sys.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 消息发送队列状态
 * */
public class SendQueueStatusEnum extends ValuedEnum {

    protected SendQueueStatusEnum(String name, int value) {
        super(name, value);
    }

    public static final SendQueueStatusEnum FAIL = new SendQueueStatusEnum("发送失败", -1);
    public static final SendQueueStatusEnum INIT = new SendQueueStatusEnum("待发送", 0);
    public static final SendQueueStatusEnum SENDING = new SendQueueStatusEnum("发送中", 1);
    public static final SendQueueStatusEnum PART = new SendQueueStatusEnum("部分完成", 2);
    public static final SendQueueStatusEnum COMPLETE = new SendQueueStatusEnum("全部完成", 4);

}

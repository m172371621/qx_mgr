package com.brilliantreform.sc.sys.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 消息提醒方式
 * */
public class MsgNotifyTypeEnum extends ValuedEnum {

    protected MsgNotifyTypeEnum(String name, int value) {
        super(name, value);
    }

    public static final MsgNotifyTypeEnum EMAIL = new MsgNotifyTypeEnum("邮件", 1);
    public static final MsgNotifyTypeEnum SMS = new MsgNotifyTypeEnum("短信", 2);
    public static final MsgNotifyTypeEnum SYSMSG = new MsgNotifyTypeEnum("系统消息", 3);
    public static final MsgNotifyTypeEnum PUSH = new MsgNotifyTypeEnum("手机推送", 4);
    public static final MsgNotifyTypeEnum WEIXIN = new MsgNotifyTypeEnum("微信", 5);
}

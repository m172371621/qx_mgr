package com.brilliantreform.sc.qxcard.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 区享卡充值渠道
 */
public class QxCardCzChannelEnum extends ValuedEnum {
    protected QxCardCzChannelEnum(String name, int value) {
        super(name, value);
    }

    public static final QxCardCzChannelEnum ANDROID = new QxCardCzChannelEnum("Android", 1);
    public static final QxCardCzChannelEnum IOS = new QxCardCzChannelEnum("IOS", 2);
    public static final QxCardCzChannelEnum WEIXIN = new QxCardCzChannelEnum("微信", 3);
    public static final QxCardCzChannelEnum POS = new QxCardCzChannelEnum("POS", 4);
    public static final QxCardCzChannelEnum CHINANET = new QxCardCzChannelEnum("ChinaNet", 5);

}

package com.brilliantreform.sc.sys.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 定时任务状态
 * */
public class JobStatusEnum extends ValuedEnum {

    protected JobStatusEnum(String name, int value) {
        super(name, value);
    }

    public static final JobStatusEnum ENABLE = new JobStatusEnum("开启", 1);
    public static final JobStatusEnum DISABLE = new JobStatusEnum("关闭", 0);
}

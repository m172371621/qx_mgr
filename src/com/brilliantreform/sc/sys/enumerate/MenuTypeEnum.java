package com.brilliantreform.sc.sys.enumerate;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 菜单类型
 * */
public class MenuTypeEnum extends ValuedEnum {

    protected MenuTypeEnum(String name, int value) {
        super(name, value);
    }

    public static final MenuTypeEnum MENU = new MenuTypeEnum("菜单", 1);
    public static final MenuTypeEnum OPERATE = new MenuTypeEnum("功能操作", 2);
}

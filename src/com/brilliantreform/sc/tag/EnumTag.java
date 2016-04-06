package com.brilliantreform.sc.tag;

import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 根据枚举值获取枚举名称Tag
 * Created by shangwq on 2015/10/16.
 */
public class EnumTag extends BodyTagSupport {

    /**
     * 枚举类
     * */
    private String clazz;

    /**
     * 返回的枚举名称
     * */
    private String name;

    /**
     * 枚举值
     * */
    private Integer value;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public int doEndTag() throws JspException {
        ValuedEnum valuedEnum = null;
        try {
            Class c = Class.forName(clazz);
            valuedEnum = EnumUtils.getEnum(c, value);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(valuedEnum != null) {
            pageContext.setAttribute(name, valuedEnum.getName());
        }
        return TagSupport.EVAL_BODY_INCLUDE;
    }
}

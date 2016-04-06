package com.brilliantreform.sc.tag;

import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * 根据枚举类获取所有的枚举Tag
 * Created by shangwq on 2015/10/16.
 */
public class EnumListTag extends BodyTagSupport {

    /**
     * 枚举类
     * */
    private String clazz;

    private String enumList;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getEnumList() {
        return enumList;
    }

    public void setEnumList(String enumList) {
        this.enumList = enumList;
    }

    public int doEndTag() throws JspException {
        List<ValuedEnum> list = null;
        try {
            Class c = Class.forName(clazz);
            list = EnumUtils.getEnumList(c);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(list != null && list.size() > 0) {
            pageContext.setAttribute(enumList, list);
        }
        return TagSupport.EVAL_BODY_INCLUDE;
    }
}

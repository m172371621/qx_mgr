package com.brilliantreform.sc.tag;

import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.DictUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Map;

public class DictTag extends BodyTagSupport {

    private String module;

    private String type;

    private String value;

    private String var;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public int doEndTag() throws JspException {
        if(StringUtils.isNotBlank(module) && StringUtils.isNotBlank(type)) {
            if(StringUtils.isNotBlank(value)) {
                String dictName = DictUtil.getDictName(module, type, CommonUtil.safeToInteger(value, null));
                pageContext.setAttribute(var, dictName);
            } else {
                Map map = DictUtil.findDictByModuleAndType(module, type);
                pageContext.setAttribute(var, map);
            }
        }
        return TagSupport.EVAL_BODY_INCLUDE;
    }
}

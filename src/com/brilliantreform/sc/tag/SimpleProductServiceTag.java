package com.brilliantreform.sc.tag;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * 简单商品类别Tag，不包含上层
 */
public class SimpleProductServiceTag extends BodyTagSupport {

    private String id = "";

    private String communitySelect = "";

    private String name = "";

    private String css = "form-control";

    private String header = "";

    private String headerValue = "";

    private String event = "";

    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    public String getCommunitySelect() {
        return communitySelect;
    }

    public void setCommunitySelect(String communitySelect) {
        this.communitySelect = communitySelect;
    }

    public int doStartTag() throws JspException {
        try {
            pageContext.getRequest().setAttribute("simplePsTag_id", id);
            pageContext.getRequest().setAttribute("simplePsTag_communitySelect", communitySelect);
            pageContext.getRequest().setAttribute("simplePsTag_name", name);
            pageContext.getRequest().setAttribute("simplePsTag_event", event);
            pageContext.getRequest().setAttribute("simplePsTag_header", header);
            pageContext.getRequest().setAttribute("simplePsTag_headerValue", headerValue);
            pageContext.getRequest().setAttribute("simplePsTag_value", value);
            pageContext.getRequest().setAttribute("simplePsTag_css", css);

            pageContext.include("/new/jsp/tag/simpleProductServiceTag.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TagSupport.EVAL_BODY_INCLUDE;
    }
}

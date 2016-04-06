package com.brilliantreform.sc.sys.po;

/**
 * 消息模板
 * Created by shangwq on 2016/1/5.
 */
public class SysMsgTemplate {
    private Integer objid;
    private String template_code;
    private String template_name;
    private String template_title;
    private Integer template_type;
    private String notify_type;
    private String template_desc;
    private String email_content;
    private String sms_content;
    private String sysmsg_content;
    private String push_content;
    private Integer removetag;

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public String getTemplate_code() {
        return template_code;
    }

    public void setTemplate_code(String template_code) {
        this.template_code = template_code;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getTemplate_title() {
        return template_title;
    }

    public void setTemplate_title(String template_title) {
        this.template_title = template_title;
    }

    public Integer getTemplate_type() {
        return template_type;
    }

    public void setTemplate_type(Integer template_type) {
        this.template_type = template_type;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getTemplate_desc() {
        return template_desc;
    }

    public void setTemplate_desc(String template_desc) {
        this.template_desc = template_desc;
    }

    public String getEmail_content() {
        return email_content;
    }

    public void setEmail_content(String email_content) {
        this.email_content = email_content;
    }

    public String getSms_content() {
        return sms_content;
    }

    public void setSms_content(String sms_content) {
        this.sms_content = sms_content;
    }

    public String getSysmsg_content() {
        return sysmsg_content;
    }

    public void setSysmsg_content(String sysmsg_content) {
        this.sysmsg_content = sysmsg_content;
    }

    public String getPush_content() {
        return push_content;
    }

    public void setPush_content(String push_content) {
        this.push_content = push_content;
    }

    public Integer getRemovetag() {
        return removetag;
    }

    public void setRemovetag(Integer removetag) {
        this.removetag = removetag;
    }
}

package com.brilliantreform.sc.phoneOrder.po;

import java.util.Map;

/**
 * Created by Lm on 2015/12/30.
 * 微信模板消息类
 */
public class WxTemplate {
    private String template_id; //模版id
    private String touser;  //发送的openid
    private String url; //url
    private String topcolor; //顶部字体颜色
    private Map<String,TemplateData> data; //模版数据

    public String getTemplate_id() {
        return template_id;
    }
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTopcolor() {
        return topcolor;
    }
    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }
    public Map<String,TemplateData> getData() {
        return data;
    }
    public void setData(Map<String,TemplateData> data) {
        this.data = data;
    }
}

package com.brilliantreform.sc.sys.po;

import java.util.List;
import java.util.Map;

/**
 * 信息发送主体
 * Created by shangwq on 2016/1/9.
 */
public class MessageContext {

    private String templateCode;    //模板代码
    private List<MessageReceiver> receivers;    //接收者集合
    private Map params;     //额外参数，用于填充模板内容
    private Integer sender_id;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public List<MessageReceiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<MessageReceiver> receivers) {
        this.receivers = receivers;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }
}

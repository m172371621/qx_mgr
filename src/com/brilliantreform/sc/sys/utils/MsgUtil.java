package com.brilliantreform.sc.sys.utils;

import com.brilliantreform.sc.sys.po.MessageContext;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;

/**
 * 消息发送工具
 * Created by shangwq on 2016/1/5.
 */
public class MsgUtil {

    /**
     * 发送短信
     * */
    public void sendSms() {

    }

    /**
     * 发送邮件
     * */
    public void sendEmail() throws Exception{
        HtmlEmail email = new HtmlEmail();
        email.setHostName("");
        email.setAuthentication("", "");
        email.setCharset("GBK");

        email.addTo("");
        email.setFrom("");

        email.setSubject("");
        email.setHtmlMsg("");
        email.send();
    }

    /**
     * 发送系统消息
     * */
    public void sendSysMsg() {

    }

    /**
     * 发送手机推送信息
     * */
    public void sendPush() {

    }

    /**
     * 发送微信推送信息
     * */
    public void sendWeixin() {

    }
}

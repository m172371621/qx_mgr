package com.brilliantreform.sc.lm.weixin.req;

/**
 * Created by Lm on 2015/12/28.
 * 类名:  TestMessage <br/>
 * 描述:  请求消息之文本消息  <br/>
 * 开发人员:    lm <br/>
 * 创建时间:    2015/12/28 <br/>
 * 发布版本:    v1.0 <br/>
 */
public class TestMessage extends BaseMessage {
    //文本消息内容
    private String Content;

    public void setContent(String content) {
        Content = content;
    }

    public String getContent() {
        return Content;
    }
}
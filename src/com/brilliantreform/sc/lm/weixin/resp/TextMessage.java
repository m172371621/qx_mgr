package com.brilliantreform.sc.lm.weixin.resp;

/**
 * Created by Lm on 2015/12/28.
 * 类名:  TextMessage <br/>
 * 描述:  回复文本消息 </br>
 * 开发人员:    lm <br/>
 * 创建时间:    2015/12/28 <br/>
 * 发布版本:    v1.0 <br/>
 */
public class TextMessage extends BaseMessage {
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
package com.brilliantreform.sc.lm.weixin.event;

/**
 * Created by Lm on 2015/12/28.
 * 类名:  BaseEvent<br/>
 * 描述:  自定义菜单事件  <br/>
 * 开发人员:    lm <br/>
 * 创建时间:    2015/12/28 <br/>
 * 发布版本:    v1.0 <br/>
 */
public class MenuEvent extends BaseEvent {
    // 事件KEY值，与自定义菜单接口中KEY值对应
    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}

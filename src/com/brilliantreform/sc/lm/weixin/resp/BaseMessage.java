package com.brilliantreform.sc.lm.weixin.resp;

/**
 * Created by Lm on 2015/12/28.
 * ����:  BaseMessage <br/>
 * ����:  ��Ϣ���ࣨ�����ʺ� -> ��ͨ�û��� </br>
 * ������Ա:    lm <br/>
 * ����ʱ��:    2015/12/28 <br/>
 * �����汾:    v1.0 <br/>
 */
public class BaseMessage {
    // ���շ��ʺţ��յ���OpenID��
    private String ToUserName;
    // ������΢�ź�
    private String FromUserName;
    // ��Ϣ����ʱ�� �����ͣ�
    private long CreateTime;
    // ��Ϣ����
    private String MsgType;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}

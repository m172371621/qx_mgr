package com.brilliantreform.sc.lm.weixin.req;

/**
 * Created by Lm on 2015/12/28.
 * ����:  TestMessage <br/>
 * ����:  ������Ϣ֮ͼƬ��Ϣ  <br/>
 * ������Ա:    lm <br/>
 * ����ʱ��:    2015/12/28 <br/>
 * �����汾:    v1.0 <br/>
 */
public class ImageMessage extends BaseMessage {
    //ͼƬ����
    private String PicUrl;
    //ͼƬ��Ϣý��id
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
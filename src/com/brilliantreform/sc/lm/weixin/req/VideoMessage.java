package com.brilliantreform.sc.lm.weixin.req;

/**
 * Created by Lm on 2015/12/28.
 * ����:  VideoMessage <br/>
 * ����:  ������Ϣ֮��Ƶ��Ϣ  <br/>
 * ������Ա:    lm <br/>
 * ����ʱ��:    2015/12/28 <br/>
 * �����汾:    v1.0 <br/>
 */
public class VideoMessage  extends BaseMessage{

    // ý��ID
    private String MediaId;
    // ������ʽ
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }
    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
    public String getThumbMediaId() {
        return ThumbMediaId;
    }
    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }



}
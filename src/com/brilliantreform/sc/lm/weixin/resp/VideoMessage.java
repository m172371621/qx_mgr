package com.brilliantreform.sc.lm.weixin.resp;

/**
 * ����: VideoMessage </br>
 * ����: ��Ƶ��Ϣ </br>
 * ������Ա�� souvc </br>
 * ����ʱ��:    2015/12/28 <br/>
 * �����汾��V1.0  </br>
 */
public class VideoMessage extends BaseMessage {
    // ��Ƶ
    private Video Video;

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        Video = video;
    }
}
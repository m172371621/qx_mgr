package com.brilliantreform.sc.lm.weixin.resp;

/**
 * Created by Lm on 2015/12/28.
 * 类名: Video </br>
 * 描述: 视频model </br>
 * 开发人员： souvc </br>
 * 创建时间:    2015/12/28 <br/>
 * 发布版本：V1.0  </br>
 */
public class Video {
    // 媒体文件id
    private String MediaId;
    // 缩略图的媒体id
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
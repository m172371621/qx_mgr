package com.brilliantreform.sc.lm.weixin.resp;

/**
 * Created by Lm on 2015/12/28.
 * 类名: Music </br>
 * 描述: 音乐model </br>
 * 开发人员： lm </br>
 * 创建时间:    2015/12/28 <br/>
 * 发布版本：V1.0  </br>
 */
public class Music {
    // 音乐标题
    private String Title;
    // 音乐描述
    private String Description;
    // 音乐链接
    private String MusicUrl;
    // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
    private String HQMusicUrl;
    // 缩略图的媒体id，通过上传多媒体文件得到的id
    private String ThumbMediaId;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        MusicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String musicUrl) {
        HQMusicUrl = musicUrl;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
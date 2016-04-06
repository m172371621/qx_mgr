package com.brilliantreform.sc.lm.weixin.resp;

/**
 * Created by Lm on 2015/12/28.
 * 类名: MusicMessage </br>
 * 描述: 音乐消息 </br>
 * 开发人员： souvc </br>
 * 创建时间:    2015/12/28 <br/>
 * 发布版本：V1.0  </br>
 */
public class MusicMessage extends BaseMessage {
    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}

package com.brilliantreform.sc.lm.weixin.resp;

/**
 * Created by Lm on 2015/12/28.
 * ����: MusicMessage </br>
 * ����: ������Ϣ </br>
 * ������Ա�� souvc </br>
 * ����ʱ��:    2015/12/28 <br/>
 * �����汾��V1.0  </br>
 */
public class MusicMessage extends BaseMessage {
    // ����
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}

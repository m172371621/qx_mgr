package com.brilliantreform.sc.lm.weixin.resp;

/**
 * Created by Lm on 2015/12/28.
 * 类名: VoiceMessage </br>
 * 描述: 语音消息</br>
 * 开发人员： souvc </br>
 * 创建时间:    2015/12/28 <br/>
 * 发布版本：V1.0  </br>
 */
public class VoiceMessage extends BaseMessage {
    // 语音
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }
}
package com.brilliantreform.sc.lm.weixin.resp;

/**
 * Created by Lm on 2015/12/28.
 * ����: VoiceMessage </br>
 * ����: ������Ϣ</br>
 * ������Ա�� souvc </br>
 * ����ʱ��:    2015/12/28 <br/>
 * �����汾��V1.0  </br>
 */
public class VoiceMessage extends BaseMessage {
    // ����
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }
}
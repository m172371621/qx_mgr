package com.brilliantreform.sc.lm.weixin.resp;

/**
 * Created by Lm on 2015/12/28.
 * ����: ImageMessage </br>
 * ����: ͼƬ��Ϣ</br>
 * ������Ա�� souvc </br>
 * ����ʱ��:    2015/12/28 <br/>
 * �����汾��V1.0  </br>
 */
public class ImageMessage extends BaseMessage {

    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
package com.brilliantreform.sc.lm.weixin.event;

/**
 * Created by Lm on 2015/12/28.
 * ����:  BaseEvent<br/>
 * ����:  �ϱ�����λ���¼�  <br/>
 * ������Ա:    lm <br/>
 * ����ʱ��:    2015/12/28 <br/>
 * �����汾:    v1.0 <br/>
 */
public class LocationEvent extends BaseEvent {
    // ����λ��γ��
    private String Latitude;
    // ����λ�þ���
    private String Longitude;
    // ����λ�þ���
    private String Precision;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPrecision() {
        return Precision;
    }

    public void setPrecision(String precision) {
        Precision = precision;
    }
}

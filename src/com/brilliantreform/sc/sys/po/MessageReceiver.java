package com.brilliantreform.sc.sys.po;

/**
 * 信息接收人
 * Created by shangwq on 2016/1/9.
 */
public class MessageReceiver {

    private String email;
    private String phone;
    private String user_id;

    public MessageReceiver() {}

    public MessageReceiver(String email, String phone, String user_id) {
        this.email = email;
        this.phone = phone;
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

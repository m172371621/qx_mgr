package com.brilliantreform.sc.sys.po;

import java.util.Date;

/**
 * 系统消息收件箱
 * Created by shangwq on 2016/1/5.
 */
public class SysInbox {

    private Integer objid;
    private Integer user_id;
    private String title;
    private String content;
    private Integer status;
    private Integer sender_id;
    private Date create_time;
    private Date read_time;
    private Integer removetag;

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getRead_time() {
        return read_time;
    }

    public void setRead_time(Date read_time) {
        this.read_time = read_time;
    }

    public Integer getRemovetag() {
        return removetag;
    }

    public void setRemovetag(Integer removetag) {
        this.removetag = removetag;
    }
}

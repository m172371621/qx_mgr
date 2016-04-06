package com.brilliantreform.sc.activity.po;

import java.util.Date;

/**
 * 抽奖活动记录
 * Created by shangwq on 2016/1/21.
 */
public class PrizeLog {
    private Integer log_id;
    private Integer user_id;
    private Integer prize_id;
    private Integer status;
    private String prize_level;
    private String prize_name;
    private String prize_img;
    private String dec;
    private String channel;
    private String operatorName;
    private Date awardTime;
    private Date createtime;

    public Integer getLog_id() {
        return log_id;
    }

    public void setLog_id(Integer log_id) {
        this.log_id = log_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getPrize_id() {
        return prize_id;
    }

    public void setPrize_id(Integer prize_id) {
        this.prize_id = prize_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPrize_level() {
        return prize_level;
    }

    public void setPrize_level(String prize_level) {
        this.prize_level = prize_level;
    }

    public String getPrize_name() {
        return prize_name;
    }

    public void setPrize_name(String prize_name) {
        this.prize_name = prize_name;
    }

    public String getPrize_img() {
        return prize_img;
    }

    public void setPrize_img(String prize_img) {
        this.prize_img = prize_img;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}

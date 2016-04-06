package com.brilliantreform.sc.activity.po;

import java.util.Date;

/**
 * 抽奖活动奖项设置信息
 * Created by shangwq on 2016/1/21.
 */
public class PrizeInfo {
    private Integer prize_id;
    private Integer probability;
    private Integer count;
    private Integer cid;
    private String prize_level;
    private String prize_name;
    private String prize_img;
    private String prize_dec;
    private Date updatetime;

    public Integer getPrize_id() {
        return prize_id;
    }

    public void setPrize_id(Integer prize_id) {
        this.prize_id = prize_id;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
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

    public String getPrize_dec() {
        return prize_dec;
    }

    public void setPrize_dec(String prize_dec) {
        this.prize_dec = prize_dec;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}

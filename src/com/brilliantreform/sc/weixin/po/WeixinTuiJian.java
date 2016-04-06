package com.brilliantreform.sc.weixin.po;

import java.util.Date;

/**
 * Created by Lm on 2015/12/14 0014.
 */
public class WeixinTuiJian {
    String openid;
    String phone; //手机
    String name;//姓名
    String addr;//地址
    String my_recommend_code; //我的推荐码
    String other_recommend_code;// 他人的推荐码
    Integer recommend_amount; //已推荐数量
    Integer state; //状态 1=未确认 2=已确认
    Integer buy_count; //购买数量
    String img_url; // 头像
    String nickname; //别名
    Date create_time;// 创建时间

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMy_recommend_code() {
        return my_recommend_code;
    }

    public void setMy_recommend_code(String my_recommend_code) {
        this.my_recommend_code = my_recommend_code;
    }

    public String getOther_recommend_code() {
        return other_recommend_code;
    }

    public void setOther_recommend_code(String other_recommend_code) {
        this.other_recommend_code = other_recommend_code;
    }

    public Integer getRecommend_amount() {
        return recommend_amount;
    }

    public void setRecommend_amount(Integer recommend_amount) {
        this.recommend_amount = recommend_amount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(Integer buy_count) {
        this.buy_count = buy_count;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Weixinqxx{" +
                "openid='" + openid + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", my_recommend_code='" + my_recommend_code + '\'' +
                ", other_recommend_code='" + other_recommend_code + '\'' +
                ", recommend_amount=" + recommend_amount +
                ", state=" + state +
                ", buy_count=" + buy_count +
                ", img_url='" + img_url + '\'' +
                ", nickname='" + nickname + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}

package com.brilliantreform.sc.store.po;

public class Store {
    private String country;// 经营组织
    private String storename;// 门店名称
    private String province;// 省
    private String city;// 市
    private String area;// 区
    private String address;// 门店位置
    private String coordinate;// 定位坐标
    private String phone;// 服务电话
    private Integer isdoor;// 是否送货上门
    private String delivery_range;// 送货半径 * （公里）
    private Double delivery_price;// 送货费用
    private Double door_howmuch;// 上门条件 * （购物满多少元）
    private String updoortime;// 上门时间
    private String worktime;// 营业时间
    private Integer isfirst_manjan;// 是否首单减免
    private Double manjian_price;// 首单减免额度
    private String personincharge;// 服负责人
    private String personincharge_phone;// 服负责人电话
    private Integer cache_id; //暂存标识(0暂存,1审核中,2审核通过,3审核驳回)
    private Integer cid; //小区Id
    private Integer obj_id; //自增Id
    private Integer user_id;//用户Id
    private String question; //原因

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getObj_id() {
        return obj_id;
    }

    public void setObj_id(Integer obj_id) {
        this.obj_id = obj_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCache_id() {
        return cache_id;
    }

    public void setCache_id(Integer cache_id) {
        this.cache_id = cache_id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsdoor() {
        return isdoor;
    }

    public void setIsdoor(Integer isdoor) {
        this.isdoor = isdoor;
    }

    public String getDelivery_range() {
        return delivery_range;
    }

    public void setDelivery_range(String delivery_range) {
        this.delivery_range = delivery_range;
    }

    public Double getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(Double delivery_price) {
        this.delivery_price = delivery_price;
    }

    public Double getDoor_howmuch() {
        return door_howmuch;
    }

    public void setDoor_howmuch(Double door_howmuch) {
        this.door_howmuch = door_howmuch;
    }

    public String getUpdoortime() {
        return updoortime;
    }

    public void setUpdoortime(String updoortime) {
        this.updoortime = updoortime;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public Integer getIsfirst_manjan() {
        return isfirst_manjan;
    }

    public void setIsfirst_manjan(Integer isfirst_manjan) {
        this.isfirst_manjan = isfirst_manjan;
    }

    public Double getManjian_price() {
        return manjian_price;
    }

    public void setManjian_price(Double manjian_price) {
        this.manjian_price = manjian_price;
    }

    public String getPersonincharge() {
        return personincharge;
    }

    public void setPersonincharge(String personincharge) {
        this.personincharge = personincharge;
    }

    public String getPersonincharge_phone() {
        return personincharge_phone;
    }

    public void setPersonincharge_phone(String personincharge_phone) {
        this.personincharge_phone = personincharge_phone;
    }

}

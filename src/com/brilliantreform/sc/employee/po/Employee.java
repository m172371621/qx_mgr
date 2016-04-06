package com.brilliantreform.sc.employee.po;

/**
 * Created by Lm on 2015/11/17 0017.
 */
public class Employee {
    private String community_id;
    private String employeeName;
    private String phone;
    private String loginName;
    private String password;
    private String job;
    private Integer age;
    private String sex;
    private String touXinag;
    private String stauts;
    private Integer authenticate;
    private Integer user_id;
    private Integer community_i;
    private Integer jingli;
    private Integer dianzhang;
    private Integer dianyuan;
    private Integer kuaidi;

    public Integer getDianzhang() {
        return dianzhang;
    }

    public void setDianzhang(Integer dianzhang) {
        this.dianzhang = dianzhang;
    }

    public Integer getDianyuan() {
        return dianyuan;
    }

    public void setDianyuan(Integer dianyuan) {
        this.dianyuan = dianyuan;
    }

    public Integer getKuaidi() {
        return kuaidi;
    }

    public void setKuaidi(Integer kuaidi) {
        this.kuaidi = kuaidi;
    }

    public Integer getZongjinli() {
        return jingli;
    }

    public void setZongjinli(Integer zongjinli) {
        this.jingli = zongjinli;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTouXinag() {
        return touXinag;
    }

    public void setTouXinag(String touXinag) {
        this.touXinag = touXinag;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }

    public Integer getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(Integer authenticate) {
        this.authenticate = authenticate;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCommunity_i() {
        return community_i;
    }

    public void setCommunity_i(Integer community_i) {
        this.community_i = community_i;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public Integer getJingli() {
        return jingli;
    }

    public void setJingli(Integer jingli) {
        this.jingli = jingli;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "user_id=" + user_id +
                ", community_id='" + community_id + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", phone='" + phone + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", job='" + job + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", touXinag='" + touXinag + '\'' +
                ", stauts='" + stauts + '\'' +
                ", authenticate=" + authenticate +
                ", community_i=" + community_i +
                ", jingli=" + jingli +
                ", dianzhang=" + dianzhang +
                ", dianyuan=" + dianyuan +
                ", kuaidi=" + kuaidi +
                '}';
    }
}

package com.brilliantreform.sc.sys.po;

import java.io.Serializable;

public class SysMenu implements Serializable {
    private Integer objid;
    private String name;
    private String logo;
    private Integer parentid;
    private Integer type;   //1.菜单 2.功能操作
    private Integer sort;
    private String url;
    private String remark;
    private Integer removetag;

    public SysMenu() {}

    public SysMenu(Integer objid, String name, String logo, Integer parentid, Integer type, Integer sort, String url, String remark, Integer removetag) {
        this.objid = objid;
        this.name = name;
        this.logo = logo;
        this.parentid = parentid;
        this.type = type;
        this.sort = sort;
        this.url = url;
        this.remark = remark;
        this.removetag = removetag;
    }

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRemovetag() {
        return removetag;
    }

    public void setRemovetag(Integer removetag) {
        this.removetag = removetag;
    }

}

package com.brilliantreform.sc.sys.po;

import java.util.ArrayList;
import java.util.List;

public class MenuTree {
    private Integer id;
    private String name;
    private String logo;
    private String url;
    private List<MenuTree> child = new ArrayList<MenuTree>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<MenuTree> getChild() {
        return child;
    }

    public void setChild(List<MenuTree> child) {
        this.child = child;
    }

    public void addChild(MenuTree childTree) {
        if (childTree != null) {
            this.child.add(childTree);
        }
    }
}

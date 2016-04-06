package com.brilliantreform.sc.sys.service;

import com.brilliantreform.sc.sys.dao.SysMenuDao;
import com.brilliantreform.sc.sys.enumerate.MenuTypeEnum;
import com.brilliantreform.sc.sys.po.MenuTree;
import com.brilliantreform.sc.sys.po.SysMenu;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuService {
    private static final Logger LOGGER = Logger.getLogger(SysMenuService.class);

    @Autowired
    private SysMenuDao sysMenuDao;

    public List<SysMenu> findAllMenu(Integer type) {
        return sysMenuDao.findAllMenu(type);
    }

    public List<SysMenu> findUserMenu(Integer user_id, Integer type) {
        if(user_id == null) {
            return findAllMenu(type);
        } else {
            return sysMenuDao.findUserMenu(user_id, type);
        }
    }

    /**
     * 获取权限内的菜单
     * */
    public List<MenuTree> loadSysMenu(Integer user_id) {
        List<MenuTree> list = new ArrayList<MenuTree>();

        List<SysMenu> menuList = new ArrayList<SysMenu>();
        if(user_id == null) {
            menuList = findAllMenu(MenuTypeEnum.MENU.getValue());
        } else {
            menuList = sysMenuDao.findUserMenu(user_id, MenuTypeEnum.MENU.getValue());
        }

        for(SysMenu menu : menuList) {
            if(menu.getParentid().intValue() == 0) {
                MenuTree menuTree = new MenuTree();
                menuTree.setId(menu.getObjid());
                menuTree.setName(menu.getName());
                menuTree.setLogo(menu.getLogo());
                menuTree.setUrl(menu.getUrl());
                list.add(addTreeNode(menuTree, menuList));
            }
        }

        return list;
    }

    private MenuTree addTreeNode(MenuTree parent, List<SysMenu> menuList){
        List<SysMenu> childList = loadSysMenuByPid(menuList, parent.getId());
        for(SysMenu child : childList) {
            MenuTree childTree = new MenuTree();
            childTree.setName(child.getName());
            childTree.setId(child.getObjid());
            childTree.setLogo(child.getLogo());
            childTree.setUrl(child.getUrl());
            parent.addChild(childTree);
            this.addTreeNode(childTree, menuList);
        }
        return parent;
    }

    private List<SysMenu> loadSysMenuByPid(List<SysMenu> list, Integer pid) {
        List<SysMenu> _list = new ArrayList<SysMenu>();
        for(SysMenu menu : list) {
            if(pid != null && menu.getParentid() != null) {
                if (pid == menu.getParentid().intValue()) {
                    _list.add(menu);
                }
            }
        }
        return _list;
    }

}

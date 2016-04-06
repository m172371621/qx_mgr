package com.brilliantreform.sc.sys.dao;

import com.brilliantreform.sc.sys.po.SysMenu;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysMenuDao {

    private static Logger LOGGER = Logger.getLogger(SysMenuDao.class);

    private static final String NAMESPACE = "sysmenu.";

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public List<SysMenu> findAllMenu(Integer type) {
        Map param = new HashMap();
        param.put("type", type);
        return sqlMapClient.queryForList(NAMESPACE + "findAllMenu", type);
    }

    /**
     * 根据用户id获取该用户有权限的菜单
     * @param type 1.菜单  2.功能操作  null则查询所有
     * */
    public List<SysMenu> findUserMenu(int user_id, Integer type) {
        Map map = new HashMap();
        map.put("user_id", user_id);
        map.put("type", type);
        return sqlMapClient.queryForList(NAMESPACE + "findUserMenu", map);
    }
}

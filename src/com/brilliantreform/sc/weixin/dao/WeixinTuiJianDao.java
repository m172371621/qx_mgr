package com.brilliantreform.sc.weixin.dao;

import com.brilliantreform.sc.weixin.po.WeixinTuiJian;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2015/12/14 0014.
 */

@Repository
public class WeixinTuiJianDao {
    private static Logger LOGGER = Logger.getLogger(WeixinTuiJianDao.class);

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    private static final String NAMESPACE = "wxqtj.";

    public List<WeixinTuiJian> selWeixinqxxList(Map param) {
        return (List<WeixinTuiJian>) sqlMapClient.queryForList(NAMESPACE + "selWeixinqxxTuiJianList", param);
    }

    public int selWeixinqxxListcount(Map map) {
        return (Integer) sqlMapClient.queryForObject(NAMESPACE + "selWeixinqxxTuiJianListcount", map);
    }

    public void updateWeixinqxxTuiJian(Map map) {
        sqlMapClient.update(NAMESPACE + "updateWeixinqxxTuiJian", map);
    }

    public WeixinTuiJian selWeixinqxx(Map map) {
        return (WeixinTuiJian) sqlMapClient.queryForObject(NAMESPACE + "selWeixinqxxTuiJian", map);
    }
}

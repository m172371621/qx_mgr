package com.brilliantreform.sc.sys.dao;

import com.brilliantreform.sc.sys.po.SysJob;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SysJobDao {

    private static Logger LOGGER = Logger.getLogger(SysDao.class);

    private static final String NAMESPACE = "sysjob.";

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public List<SysJob> findAllJob() {
        return sqlMapClient.queryForList(NAMESPACE + "findAllJob");
    }

    public List<Map> searchSysJob(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchSysJob", param);
    }

    public int searchSysJobCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchSysJobCount", param), 0);
    }
}

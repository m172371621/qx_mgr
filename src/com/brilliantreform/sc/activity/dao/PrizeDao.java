package com.brilliantreform.sc.activity.dao;

import com.brilliantreform.sc.activity.po.PrizeInfo;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PrizeDao {

    private static Logger LOGGER = Logger.getLogger(PrizeDao.class);

    private static final String NAMESPACE = "prize.";

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public List<Map> searchPrizeLog(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchPrizeLog", param);
    }

    public int searchPrizeLogCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchPrizeLogCount", param), 0);
    }

    public void acceptPrize(int log_id, String operatorName) {
        Map param = new HashMap();
        param.put("log_id", log_id);
        param.put("operatorName", operatorName);
        sqlMapClient.update(NAMESPACE + "acceptPrize", param);
    }

    public List<Map> searchPrizeInfo(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchPrizeInfo", param);
    }

    public int searchPrizeInfoCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchPrizeInfoCount", param), 0);
    }

    public PrizeInfo getPrizeInfo(int community_id, String prize_level, Integer prize_id) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("prize_level", prize_level);
        param.put("prize_id", prize_id);
        return (PrizeInfo) sqlMapClient.queryForObject(NAMESPACE + "getPrizeInfo", param);
    }

    public PrizeInfo getPrizeInfoById(int prize_id) {
        return (PrizeInfo) sqlMapClient.queryForObject(NAMESPACE + "getPrizeInfoById", prize_id);
    }

    public void insertPrizeInfo(PrizeInfo prizeInfo) {
        sqlMapClient.insert(NAMESPACE + "insertPrizeInfo", prizeInfo);
    }

    public void updatePrizeInfo(PrizeInfo prizeInfo) {
        sqlMapClient.update(NAMESPACE + "updatePrizeInfo", prizeInfo);
    }

    public void deletePrizeInfo(int prize_id) {
        sqlMapClient.delete(NAMESPACE + "deletePrizeInfo", prize_id);
    }
}

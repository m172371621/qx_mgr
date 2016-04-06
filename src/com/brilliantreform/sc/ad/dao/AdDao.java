package com.brilliantreform.sc.ad.dao;

import com.brilliantreform.sc.order.po.OrderRefund;
import com.brilliantreform.sc.service.po.MainAD;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdDao {
    private static Logger LOGGER = Logger.getLogger(AdDao.class);

    private static final String NAMESPACE = "ad.";

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public List<Map> searchAd(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchAd", param);
    }

    public int searchAdCount(Map param) {
        return (Integer)sqlMapClient.queryForObject(NAMESPACE + "searchAdCount", param);
    }

    public int insertAd(MainAD ad) {
        return (Integer) sqlMapClient.insert(NAMESPACE + "insertAd", ad);
    }

    public void updateAd(MainAD ad) {
        sqlMapClient.update(NAMESPACE + "updateAd", ad);
    }

    public MainAD getAdById(int ad_id) {
        return (MainAD) sqlMapClient.queryForObject(NAMESPACE + "getAdById", ad_id);
    }

    public void deleteAd(int ad_id) {
        sqlMapClient.delete(NAMESPACE + "deleteAd", ad_id);
    }

    public List<OrderRefund> findOrderRefund() {
        return sqlMapClient.queryForList(NAMESPACE + "findOrderRefund");
    }

}

package com.brilliantreform.sc.card.dao;

import com.brilliantreform.sc.activity.po.CardNumConfig;
import com.brilliantreform.sc.activity.po.PrizeUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CardDao {
    private static Logger logger = Logger.getLogger(CardDao.class);

    private static final String NAMESPACE = "card.";

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public List<Map> findCommunityCardInfo(int community_id) {
        return sqlMapClient.queryForList(NAMESPACE + "findCommunityCardInfo", community_id);
    }

    public void updateAmount(int community_id, int level_id, int amount) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("level_id", level_id);
        param.put("amount", amount);
        sqlMapClient.update(NAMESPACE + "updateAmount", param);
    }

    public void updateCardLevel(int level_id, int probability) {
        Map param = new HashMap();
        param.put("level_id", level_id);
        param.put("probability", probability);
        sqlMapClient.update(NAMESPACE + "updateCardLevel", param);
    }

    public void updateAward(int community_id, int level_id, String award) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("level_id", level_id);
        param.put("award", award);
        sqlMapClient.update(NAMESPACE + "updateAward", param);
    }
    
    public List<Map> findCardNum(int community_id) {
    	return sqlMapClient.queryForList(NAMESPACE.concat("findCardNum"), community_id);
    }
    
    public int delCardNum(int objid) {
    	return sqlMapClient.delete(NAMESPACE.concat("delCardNum"), objid);
    }
    
    public void updateCardNum(Map map) {
    	sqlMapClient.update(NAMESPACE.concat("updateCardNum"), map);
    }
    
    public void insertCardNum(Map map) {
    	sqlMapClient.update(NAMESPACE.concat("insertCardNum"), map);
    }

    public List<Map> findCommunityCardInfoV4(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "findCommunityCardInfoV4", param);
    }

    public List<Map> searchCardNumConfig(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchCardNumConfig", param);
    }

    public void insertCardNumConfig(CardNumConfig cardNumConfig) {
        sqlMapClient.insert(NAMESPACE + "insertCardNumConfig", cardNumConfig);
    }

    public void updateCardNumConfig(CardNumConfig cardNumConfig) {
        sqlMapClient.update(NAMESPACE + "updateCardNumConfig", cardNumConfig);
    }

    public void saveCardNumConfig(CardNumConfig cardNumConfig) {
        if(cardNumConfig != null) {
            if(cardNumConfig.getObjid() == null) {
                insertCardNumConfig(cardNumConfig);
            } else {
                updateCardNumConfig(cardNumConfig);
            }
        }
    }

    public CardNumConfig getCardNumConfigById(int objid) {
        return (CardNumConfig)sqlMapClient.queryForObject(NAMESPACE + "getCardNumConfigById", objid);
    }

    public CardNumConfig getCardNumByPrice(int community_id, double order_price) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("order_price", order_price);
        return (CardNumConfig) sqlMapClient.queryForObject(NAMESPACE + "getCardNumByPrice", param);
    }

    /**
     * 增加或减少抽奖次数
     * */
    public void addUserPrizeCount(int user_id, int count) {
        Map param = new HashMap();
        param.put("user_id", user_id);
        param.put("count", count);
        sqlMapClient.update(NAMESPACE + "addUserPrizeCount", param);
    }

    public void insertPrizeUser(PrizeUser prizeUser) {
        sqlMapClient.insert(NAMESPACE + "insertPrizeUser", prizeUser);
    }

    public PrizeUser getPrizeUserByUserId(int user_id) {
        return (PrizeUser) sqlMapClient.queryForObject(NAMESPACE + "getPrizeUserByUserId", user_id);
    }
}

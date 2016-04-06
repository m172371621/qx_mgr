package com.brilliantreform.sc.card.service;

import com.brilliantreform.sc.activity.po.CardNumConfig;
import com.brilliantreform.sc.card.dao.CardDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CardService {

    private static Logger logger = Logger.getLogger(CardService.class);

    @Autowired
    private CardDao cardDao;

    public List<Map> findCommunityCardInfo(int community_id) {
        return cardDao.findCommunityCardInfo(community_id);
    }

    public void updateAmount(int community_id, int level_id, int amount) {
        cardDao.updateAmount(community_id, level_id, amount);
    }

    public void updateCardLevel(int level_id, int probability) {
        cardDao.updateCardLevel(level_id, probability);
    }

    public void updateAward(int community_id, int level_id, String award) {
        cardDao.updateAward(community_id, level_id, award);
    }
    
    public List<Map> findCardNum(int community_id) {
    	return cardDao.findCardNum(community_id);
    }
    
    public int delCardNum(int objid) {
    	return cardDao.delCardNum(objid);
    }
    
    public void updateCardNum(Map map) {
    	cardDao.updateCardNum(map);
    }
    
    public void insertCardNum(Map map) {
    	cardDao.insertCardNum(map);
    }

    public List<Map> findCommunityCardInfoV4(Map param) {
        return cardDao.findCommunityCardInfoV4(param);
    }

    public List<Map> searchCardNumConfig(Map param) {
        return cardDao.searchCardNumConfig(param);
    }

    public void saveCardNumConfig(CardNumConfig cardNumConfig) {
        cardDao.saveCardNumConfig(cardNumConfig);
    }

    public CardNumConfig getCardNumConfigById(int objid) {
        return cardDao.getCardNumConfigById(objid);
    }
}

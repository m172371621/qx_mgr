package com.brilliantreform.sc.activity.service;

import com.brilliantreform.sc.activity.dao.PrizeDao;
import com.brilliantreform.sc.activity.po.PrizeInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PrizeService {
    private static Logger LOGGER = Logger.getLogger(PrizeService.class);

    @Autowired
    private PrizeDao prizeDao;

    public List<Map> searchPrizeLog(Map param) {
        return prizeDao.searchPrizeLog(param);
    }

    public int searchPrizeLogCount(Map param) {
        return prizeDao.searchPrizeLogCount(param);
    }

    /**
     * 抽奖活动-领奖
     * */
    public void acceptPrize(int log_id, String operatorName) {
        prizeDao.acceptPrize(log_id, operatorName);
    }

    public List<Map> searchPrizeInfo(Map param) {
        return prizeDao.searchPrizeInfo(param);
    }

    public int searchPrizeInfoCount(Map param) {
        return prizeDao.searchPrizeInfoCount(param);
    }

    /**
     * 获取某门店的某一奖项信息
     * */
    public PrizeInfo getPrizeInfo(int community_id, String prize_level, Integer prize_id) {
        return prizeDao.getPrizeInfo(community_id, prize_level, prize_id);
    }

    public PrizeInfo getPrizeInfoById(int prize_id) {
        return prizeDao.getPrizeInfoById(prize_id);
    }

    public void savePrizeInfo(PrizeInfo prizeInfo) {
        if(prizeInfo != null) {
            if(prizeInfo.getPrize_id() == null) {
                prizeDao.insertPrizeInfo(prizeInfo);
            } else {
                prizeDao.updatePrizeInfo(prizeInfo);
            }
        }
    }

    public void deletePrizeInfo(int prize_id) {
        prizeDao.deletePrizeInfo(prize_id);
    }
}

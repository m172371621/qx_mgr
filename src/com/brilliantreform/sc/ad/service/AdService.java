package com.brilliantreform.sc.ad.service;

import com.brilliantreform.sc.ad.dao.AdDao;
import com.brilliantreform.sc.card.dao.CardDao;
import com.brilliantreform.sc.service.po.MainAD;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdService {

    private static Logger LOGGER = Logger.getLogger(AdService.class);

    @Autowired
    private AdDao adDao;

    public List<Map> searchAd(Map param) {
        return adDao.searchAd(param);
    }

    public int searchAdCount(Map param) {
        return adDao.searchAdCount(param);
    }

    public Integer saveAd(MainAD ad) {
        if(ad != null) {
            if(ad.getAd_id() == null) {
                return adDao.insertAd(ad);
            } else {
                adDao.updateAd(ad);
            }
        }
        return null;
    }

    public MainAD getAdById(int ad_id) {
        return adDao.getAdById(ad_id);
    }

    public void deleteAd(int ad_id) {
        adDao.deleteAd(ad_id);
    }
}

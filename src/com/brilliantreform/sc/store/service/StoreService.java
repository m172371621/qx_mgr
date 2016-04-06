package com.brilliantreform.sc.store.service;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.stock.service.StockService;
import com.brilliantreform.sc.store.dao.StoreDao;
import com.brilliantreform.sc.store.po.Store;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("StoreService")
public class StoreService {

    private static Logger logger = Logger.getLogger(StockService.class);

    @Autowired
    private StoreDao storeDao;

    /**
     * 添加Store
     *
     * @param map
     * @return
     */
    public Integer addStore(Store store) {
        return storeDao.addStore(store);
    }

    /**
     * 添加Community_base
     * @param Community
     * @return
     */
    public int addCommunity(Community community) {
        return  storeDao.addCommunity(community);
    }

    /**
     * 查找Store
     *
     * @param map
     * @return
     */
    public Store selStore(Map map) {
        return storeDao.selStore(map);
    }

    /**
     * 更新Store
     *
     * @param map
     */
    public void updateStore(Store store) {
        storeDao.updateStore(store);
    }

    public List<Store> storeList(Map map) {
        return storeDao.storeList(map);
    }

    public int storeListCount(Map map) {
        return storeDao.storeListCount(map);
    }

    public Store seachStore (int obj_id) {

    return storeDao.seachStore(obj_id);
    }

    public void updateStoreQuestion (String questionVaue,Integer obj_id) {
        Map map = new HashMap();
        map.put("question", questionVaue);
        map.put("obj_id", obj_id);
        map.put("cache_id", 3);
        storeDao.updateStoreQuestion(map);
    }

    public void updateCache_id(Map map) {
        storeDao.updateCache_id(map);
    }
    public Community queryChildrenId(int cid) {
        return storeDao.queryChildrenId(cid);
    }
}

package com.brilliantreform.sc.store.dao;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.stock.dao.StockDao;
import com.brilliantreform.sc.store.po.Store;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("StoreDao")
public class StoreDao {
	private static Logger logger = Logger.getLogger(StockDao.class);
	private static final String NAMESPACE = "store.";
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Integer addStore(Store store) {
		return (Integer) sqlMapClient.insert(NAMESPACE.concat("addStore"), store);
	}

	public Store selStore(Map map) {
		return (Store)sqlMapClient.queryForObject(NAMESPACE.concat("selStore"), map);
	}
	
	public void updateStore(Store Store) {
		sqlMapClient.update(NAMESPACE.concat("updateStore"), Store);
	}

	public List<Store> storeList(Map map) {
		return (List<Store>) sqlMapClient.queryForList(NAMESPACE.concat("storeList"), map);
	}
	public int storeListCount(Map map) {
		return (Integer)sqlMapClient.queryForObject(NAMESPACE.concat("storeListCount"), map);
	}

	public Store seachStore(int obj_id) {
		return (Store)sqlMapClient.queryForObject(NAMESPACE.concat("seachStore"),obj_id);
	}
	public void updateStoreQuestion(Map map) {
		sqlMapClient.update(NAMESPACE.concat("updateStoreQuestion"),map);
	}

	public Integer addCommunity(Community community) {
		return (Integer)sqlMapClient.insert(NAMESPACE.concat("addCommunity"),community);
	}
	public Community queryChildrenId(int cid) {
		return (Community)sqlMapClient.queryForObject(NAMESPACE.concat("queryChildrenId"), cid);
	}

	public void updateCache_id (Map map) {
		sqlMapClient.update(NAMESPACE.concat("updateCache_id"),map);
	}
}

package com.brilliantreform.sc.scancodeshpping.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.scancodeshpping.po.Pos_order_temp_base;
import com.brilliantreform.sc.scancodeshpping.po.Pos_order_temp_product;

@Repository("postSanDao")
public class PostSanDao {
	 private static Logger LOGGER = Logger.getLogger(PostSanDao.class);
	private static final String NAMESPANCE = "posSan.";
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Pos_order_temp_base seachPostScanBeaseList(int cid) {
		return (Pos_order_temp_base) sqlMapClient.queryForObject(NAMESPANCE.concat("seachPostScanBeaseList"), cid);
	}
	
	public List<Pos_order_temp_product> seachPostScanproductList(Map map) {
		return  sqlMapClient.queryForList(NAMESPANCE.concat("seachPostScanproductList"), map);
	}
}
	
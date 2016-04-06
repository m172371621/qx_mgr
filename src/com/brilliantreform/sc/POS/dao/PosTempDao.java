package com.brilliantreform.sc.POS.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.POS.po.Pos_order_temp_base;
import com.brilliantreform.sc.POS.po.Pos_order_temp_product;

@Repository("posTempDao")
public class PosTempDao {
	private static Logger logger = Logger.getLogger(PosTempDao.class);

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Integer insertTempDataBase(Pos_order_temp_base pos){
		logger.info("insertTempDataBase");
		return (Integer) sqlMapClient.insert("posTemp.insertTempDataBase", pos);
	}
	
	public Integer insertTempDataProduct(List<Pos_order_temp_product> list){
		logger.info("insertTempDataProduct");
		return (Integer) sqlMapClient.insert("posTemp.insertTempDataProduct", list);
	}

}

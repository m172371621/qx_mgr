package com.brilliantreform.sc.statistics.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository("statisticsDao")
public class StatisticsDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	
	@SuppressWarnings("unchecked")
	public List<Map> countRegist(Map map){
		return (List<Map>)sqlMapClient.queryForList("statistics.countRegist",map);
	}
}

package com.brilliantreform.sc.statistics.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.community.dao.CommunityDao;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.statistics.dao.StatisticsDao;

@Service("statisticsService")
public class StatisticsService {

	
	@Autowired
	private StatisticsDao statisticsDao;
	
	@SuppressWarnings("unchecked")
	public List<Map> countRegist(String start_time,String end_time,Integer cid){
		Map map = new HashMap();
		map.put("cid", cid);
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		return statisticsDao.countRegist(map);
	}

}

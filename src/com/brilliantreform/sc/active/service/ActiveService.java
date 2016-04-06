package com.brilliantreform.sc.active.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.active.dao.ActiveDao;
import com.brilliantreform.sc.active.po.Sign;
import com.brilliantreform.sc.active.po.SignPrize;

@Service("activeService")
public class ActiveService {

	private static Logger logger = Logger.getLogger(ActiveService.class);

	@Autowired
	private ActiveDao activeDao;
	
	
	public Integer insertSign(Sign sign)
	{
		return activeDao.insertSign(sign);
	}
	
	public void insertSignPrize(SignPrize signPrize)
	{
		activeDao.insertSignPrize(signPrize);
	}
	
	
	public void updateSignPrize(SignPrize signPrize)
	{
		activeDao.updateSignPrize(signPrize);
	}
	
	public Sign getSign(Integer user_id)
	{
		return activeDao.getSign(user_id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getSignPrize(String name,String phone,Integer stat,Integer cid,Integer begin,Integer size)
	{
		Map map = new HashMap();
		map.put("name", name);
		map.put("phone", phone);
		map.put("stat", stat);
		map.put("cid", cid);
		map.put("begin", begin);
		map.put("size", size);

		return activeDao.getSignPrize(map);
	}
	
	@SuppressWarnings("unchecked")
	public Integer countSignPrize(String name,String phone,Integer stat,Integer cid)
	{
		Map map = new HashMap();
		map.put("name", name);
		map.put("phone", phone);
		map.put("stat", stat);
		map.put("cid", cid);
		return activeDao.countSignPrize(map);
	}
	
	@SuppressWarnings("unchecked")
	public Integer countSign(int user_id,String date)
	{
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("date", date);
		return activeDao.countSign(map);
	}
}

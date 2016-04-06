package com.brilliantreform.sc.active.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.active.po.Sign;
import com.brilliantreform.sc.active.po.SignPrize;

@Repository("activeDao")
public class ActiveDao {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Integer insertSign(Sign sign)
	{
		return (Integer)sqlMapClient.insert("active.insertSign", sign);
	}
	
	public void insertSignPrize(SignPrize signPrize)
	{
		sqlMapClient.insert("active.insertSignPrize", signPrize);
	}
	
	
	public void updateSignPrize(SignPrize signPrize)
	{
		sqlMapClient.update("active.updateSignPrize", signPrize);
	}
	
	public Sign getSign(Integer user_id)
	{
		return (Sign)sqlMapClient.queryForObject("active.getSign", user_id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getSignPrize(Map map)
	{
		return (List<Map>)sqlMapClient.queryForList("active.getSignPrize", map);
	}
	
	@SuppressWarnings("unchecked")
	public Integer countSignPrize(Map map)
	{
		return (Integer)sqlMapClient.queryForObject("active.countSignPrize", map);
	}
	
	@SuppressWarnings("unchecked")
	public Integer countSign(Map map)
	{
		return (Integer)sqlMapClient.queryForObject("active.countSign", map);
	}
	
}

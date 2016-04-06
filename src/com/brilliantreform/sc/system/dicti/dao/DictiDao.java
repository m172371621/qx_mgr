package com.brilliantreform.sc.system.dicti.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.active.po.Sign;
import com.brilliantreform.sc.active.po.SignPrize;
import com.brilliantreform.sc.system.dicti.po.DictiVo;

@Repository("dictiDao")
public class DictiDao {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	
	@SuppressWarnings("unchecked")
	public List<DictiVo> getDictiType()
	{
		return (List<DictiVo>)sqlMapClient.queryForList("dicti.getDictiType");
	}
	
	@SuppressWarnings("unchecked")
	public List<DictiVo> getDictiValue(int id)
	{
		return (List<DictiVo>)sqlMapClient.queryForList("dicti.getDictiValue", id);
	}
	
	
}

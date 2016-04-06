package com.brilliantreform.sc.community.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.community.po.Community_register;

@Repository("communityRegisterDao")
public class CommunityRegisterDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Integer register(Community_register register){
		return (Integer) sqlMapClient.insert("communityRegister.register", register);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Community_register> queryRegisterList(Map<String,Object> map){
		return (List<Community_register>) sqlMapClient.queryForList("communityRegister.queryRegisterList", map);
	}
	
	public Integer queryRegisterCount(Map<String,Object> map){
		return (Integer) sqlMapClient.queryForObject("communityRegister.queryRegisterCount", map);
	}
	
	public Integer auditRegister(Community_register com){
		return (Integer) sqlMapClient.update("communityRegister.auditRegister", com);
	}
	/**
	 * 查询明细
	 * @param map
	 * @return
	 */
	public Community_register queryRegisterDetail(Map<String,Object> map){
		return (Community_register) sqlMapClient.queryForObject("communityRegister.queryRegisterDetail", map);
	}
	
}

package com.brilliantreform.sc.confgure.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.confgure.po.Config_community;

@Repository("config_communityDao")
public class Config_communityDao {
	private static final String NAMESPANCE = "config_community.";
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@SuppressWarnings("unchecked")
	public List<Config_community> getConfig_communityList(Map param)	{
		return (List<Config_community>)sqlMapClient.queryForList(NAMESPANCE.concat("getConfig_communityList"), param);
		
	}
	
	public int getConfig_communityCount(int cid){
		return (Integer)sqlMapClient.queryForObject(NAMESPANCE.concat("getConfig_communityCount"), cid);
	}
	
	public int config_CommunityDel(Map map){
		return (Integer)sqlMapClient.delete(NAMESPANCE.concat("config_CommunityDel"), map);
	}
	
	public Config_community selConfig_community(Map map){
		return (Config_community) sqlMapClient.queryForObject(NAMESPANCE.concat("selConfig_community"), map);
	}
	
	public void config_CommunityUpdata(Config_community community){
		sqlMapClient.update(NAMESPANCE.concat("config_CommunityUpdata"), community);
	}
	
	public void config_CommunityInsert(Config_community community){
		sqlMapClient.insert(NAMESPANCE.concat("config_CommunityInsert"),community);
	}
}

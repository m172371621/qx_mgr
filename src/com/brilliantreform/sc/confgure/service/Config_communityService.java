package com.brilliantreform.sc.confgure.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.confgure.dao.Config_communityDao;
import com.brilliantreform.sc.confgure.po.Config_community;


@Service("commconfig_communityService")
public class Config_communityService {
	
	@Autowired
	private Config_communityDao configCommunityDao;
	
	public List<Config_community> getConfig_communityList(Map param){
		return configCommunityDao.getConfig_communityList(param);
	}
	
	public int getConfig_communityCount(int cid){
		return configCommunityDao.getConfig_communityCount(cid);
	}
	
	public int config_CommunityDel(Map map){
		return configCommunityDao.config_CommunityDel(map);
	}
	
	public Config_community selConfig_community(Map map){
		return  configCommunityDao.selConfig_community(map);
	}
	
	public void config_CommunityUpdata(Config_community community){
		configCommunityDao.config_CommunityUpdata(community);
	}
	
	public void config_CommunityInsert(Config_community community){
		configCommunityDao.config_CommunityInsert(community);
	}
}	

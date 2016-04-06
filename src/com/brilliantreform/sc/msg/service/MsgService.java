package com.brilliantreform.sc.msg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.community.dao.CommunityDao;
import com.brilliantreform.sc.community.po.Community;

@Service("msgService")
public class MsgService {

	
	@Autowired
	private CommunityDao communityDao;
	
	public Community getCommunity(int id){
		return communityDao.getCommunityInfo(id);
	}
	
	public List<Community> getCommunityList(){
		return communityDao.getCommunityList();
	}
}

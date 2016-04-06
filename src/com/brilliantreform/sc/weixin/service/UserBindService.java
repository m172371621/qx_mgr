package com.brilliantreform.sc.weixin.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.user.po.UserInfo;
import com.brilliantreform.sc.weixin.dao.UserBindDao;

@Service
public class UserBindService {

	@Autowired
	private UserBindDao userbindDao;

	public Integer isExistsUser(Map<String, Object> map) {
		return userbindDao.isExistsUser(map);
	}
	
	public Integer isBindCommunity(Map<String,Object> map){
		return userbindDao.isBindCommunity(map);
	}
	public UserInfo getUserInfo(Map<String,Object> map)
	{
		return userbindDao.getUserInfo(map);
	}

	public void bindPhone(Map<String, Object> map) {
		userbindDao.bindPhone(map);
	}

	public void insertuserinfo(Map<String, Object> map) {
		userbindDao.insertuserinfo(map);
	}

	public void bindCommunity(Map<String, Object> map) {
		userbindDao.bindCommunity(map);
	}
}

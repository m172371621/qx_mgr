package com.brilliantreform.sc.weixin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brilliantreform.sc.weixin.dao.DeliverDao;
import com.brilliantreform.sc.weixin.po.WeixinExpress;
import com.brilliantreform.sc.weixin.po.WeixinUser;

@Service
public class DeliverService {

	@Autowired
	private DeliverDao deliverDao;
	
	public boolean isBinded(String openid){
		if(deliverDao.isBinded(openid)>0)
			return true;
		return false;
	}
	
	public List<WeixinExpress> getExpressList(Map<String,Object> map){
		return deliverDao.getExpressList(map);
	}
	public List<WeixinExpress> getSignExpressList(Map<String,Object> map){
		return deliverDao.getSignExpressList(map);
	}
	
	@Transactional
	public String bindPhone(WeixinUser user){
		deliverDao.addWeixinUser(user);
		deliverDao.addWeixinPhone(user);
		return "OK";
	}
	public List<Map<String,Object>> getPhoneListById(Map<String,Object> map){
		return deliverDao.getPhoneListById(map);
	}
	
	public String delPhone(Map<String,Object> map){
		deliverDao.delPhone(map);
		return "OK";
	}
}

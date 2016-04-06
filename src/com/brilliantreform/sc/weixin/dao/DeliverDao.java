package com.brilliantreform.sc.weixin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.weixin.po.WeixinExpress;
import com.brilliantreform.sc.weixin.po.WeixinUser;

@Repository
public class DeliverDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Integer isBinded(String openid){
		return (Integer)sqlMapClient.queryForObject("deliver.isBinded", openid);
	}
	
	public Integer isExistsUser(WeixinUser user){
		return (Integer)sqlMapClient.queryForObject("deliver.isExistsUser", user);
	}
	
	@SuppressWarnings("unchecked")
	public List<WeixinExpress> getExpressList(Map<String,Object> map){
		return (List<WeixinExpress>)sqlMapClient.queryForList("deliver.getExpressList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<WeixinExpress> getSignExpressList(Map<String,Object> map){
		return (List<WeixinExpress>)sqlMapClient.queryForList("deliver.getSignExpressList", map);
	}
	
	public void addWeixinUser(WeixinUser user){
		if(isExistsUser(user)<1){
			sqlMapClient.insert("deliver.addWeixinUser", user);
		}
	}
	
	public void addWeixinPhone(WeixinUser user){
		sqlMapClient.insert("deliver.addWeixinPhone", user);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPhoneListById(Map<String,Object> map){
		return sqlMapClient.queryForList("deliver.getPhoneListById", map);
	}
	
    public void delPhone(Map<String,Object> map){
    	sqlMapClient.delete("deliver.delPhone", map);
	}
}

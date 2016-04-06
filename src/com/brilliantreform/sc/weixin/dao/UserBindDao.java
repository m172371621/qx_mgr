package com.brilliantreform.sc.weixin.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.user.po.UserInfo;

@Repository
public class UserBindDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	public Integer isExistsUser(Map<String, Object> map) {
		return (Integer) sqlMapClient.queryForObject(
				"weixinuserbind.isExistsUser", map);
	}

	public Integer isBindCommunity(Map<String, Object> map) {
		return (Integer) sqlMapClient.queryForObject(
				"weixinuserbind.isBindCommunity", map);
	}

	public void bindPhone(Map<String, Object> map) {
		sqlMapClient.insert("weixinuserbind.bindPhone", map);
	}

	public void insertuserinfo(Map<String, Object> map) {
		sqlMapClient.insert("weixinuserbind.insertuserinfo", map);
	}

	public void bindCommunity(Map<String, Object> map) {
		sqlMapClient.insert("weixinuserbind.bindCommunity", map);
	}

	public UserInfo getUserInfo(Map<String, Object> map) {
		return (UserInfo) sqlMapClient.queryForObject(
				"weixinuserbind.getUserInfo", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpenid(Map<String, Object> map) {
		return (Map<String, Object>) sqlMapClient.queryForObject(
				"weixinuserbind.getOpenid", map);
	}
}

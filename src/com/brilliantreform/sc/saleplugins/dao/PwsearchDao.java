package com.brilliantreform.sc.saleplugins.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.saleplugins.po.Pwmanager;
import com.brilliantreform.sc.saleplugins.po.PwsearchBean;

@Repository("pwsearchDao")
public class PwsearchDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	/**
	 * 搜索商品 (根据商品名 模糊查)
	 * 
	 * @param proname2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PwsearchBean> getProductsList(String proname2) {
		return (List<PwsearchBean>) sqlMapClient.queryForList("pwsearchBean.getProductsList", proname2);
	}

	/**
	 * 更新用户购物车状态
	 * 
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	public void user_cartUp(Map user_cartMap) {
		sqlMapClient.update("pwsearchBean.user_cartUp", user_cartMap);
	}
	
	/**
	 * 称重商品管理 列表
	 * @return List<Pwmanager>
	 */
	@SuppressWarnings("unchecked")
	public List<Pwmanager> pwmanagerList(int cid){
		return (List<Pwmanager>)sqlMapClient.queryForList("pwsearchBean.pwmanagerList",cid);
	}

	/**
	 * 称重商品 推送
	 * @return List<Pwmanager>
	 */
	@SuppressWarnings("unchecked")
	public List<Pwmanager> pwmanagerTs(){
		return (List<Pwmanager>)sqlMapClient.queryForList("pwsearchBean.pwmanagerTs");
	}

	/**
	 * 称重商品管理 列表
	 * @return List<Pwmanager>
	 */
	@SuppressWarnings("unchecked")
	public int pwmanagerListcount(int cid){
		return (Integer)sqlMapClient.queryForObject("pwsearchBean.pwmanagerListcount",cid);
	}

	/**
	 * 更新 称重商品 状态
	 * @param user_cartMap
	 */
	public void pwmanagerUp(Map user_cartMap){
		sqlMapClient.update("pwsearchBean.pwmanagerUp", user_cartMap);
	}
	
	/**
	 * 取消 称重商品 状态
	 * @param user_cartMap
	 */
	public void pwmanagerCancel(Map user_cartMap){
		sqlMapClient.update("pwsearchBean.pwmanagerCancel", user_cartMap);
	}
}

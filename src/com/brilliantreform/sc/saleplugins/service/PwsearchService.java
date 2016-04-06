package com.brilliantreform.sc.saleplugins.service;

import com.brilliantreform.sc.saleplugins.dao.PwsearchDao;
import com.brilliantreform.sc.saleplugins.po.Pwmanager;
import com.brilliantreform.sc.saleplugins.po.PwsearchBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("pwsearchService")
public class PwsearchService {

	@Autowired
	private PwsearchDao pwsearchDao;

	/**
	 * 搜索商品 (根据商品名 模糊查)
	 * 
	 * @param proname2
	 * @return
	 */
	public List<PwsearchBean> getProductsList(String proname2) {
		return (List<PwsearchBean>) pwsearchDao.getProductsList(proname2);
	}

	/**
	 * 更新用户购物车状态
	 * 
	 * @param user_cartMap
	 */
	@SuppressWarnings("unchecked")
	public void user_cartUp(Map user_cartMap) {
		pwsearchDao.user_cartUp(user_cartMap);
	}
	
	/**
	 * 称重商品管理 列表
	 */
	public List<Pwmanager> pwmanagerList(int cid){
		return (List<Pwmanager>)pwsearchDao.pwmanagerList(cid);
	}

	/**
	 * 称重商品管理 列表
	 */
	public List<Pwmanager> pwmanagerTS(){
		return (List<Pwmanager>)pwsearchDao.pwmanagerTs();
	}

	/**
	 * 称重商品管理 列表数量
	 */
	public int pwmanagerListcount(int cid){
		return pwsearchDao.pwmanagerListcount(cid);
	}
	
	/**
	 * 更新 称重商品 状态
	 * @param user_cartMap
	 */
	public void pwmanagerUp(Map user_cartMap){
		pwsearchDao.pwmanagerUp(user_cartMap);
	}
	
	/**
	 * 取消 称重商品 状态
	 * @param user_cartMap
	 */
	public void pwmanagerCancel(Map user_cartMap){
		pwsearchDao.pwmanagerCancel(user_cartMap);
	}
}

package com.brilliantreform.sc.weixin.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.weixin.po.WeixinPayBean;

@Repository
public class WeixinPayDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	public void insertLog(WeixinPayBean weixinpayBean) {
		sqlMapClient.insert("weixinpay.insertLog", weixinpayBean);
	}
}

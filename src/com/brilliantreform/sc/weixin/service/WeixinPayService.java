package com.brilliantreform.sc.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.weixin.dao.WeixinPayDao;
import com.brilliantreform.sc.weixin.po.WeixinPayBean;

@Service
public class WeixinPayService {

	@Autowired
	private WeixinPayDao wexinpayDao;

	public void insertLog(WeixinPayBean weixinpayBean) {
		wexinpayDao.insertLog(weixinpayBean);
	}
}

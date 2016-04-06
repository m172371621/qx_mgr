package com.brilliantreform.sc.weixin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.weixin.dao.PayLogDao;
import com.brilliantreform.sc.weixin.po.WeixinPayLogBean;

@Service("payLogService")
public class PayLogService {

	@Autowired
	private PayLogDao payLogDao;

	public List<WeixinPayLogBean> getWeixinPayLogList(Map<String, Object> map) {
		return payLogDao.getWeixinPayLogList(map);
	}

	public Integer getWeixinPayLogCount(Map<String, Object> map) {
		return payLogDao.getWeixinPayLogCount(map);
	}
	
	public List<WeixinPayLogBean> getAliPayLogList(Map<String, Object> map) {
		return payLogDao.getAliPayLogList(map);
	}

	public Integer getAliPayLogCount(Map<String, Object> map) {
		return payLogDao.getAliPayLogCount(map);
	}

	public Map getWxpaylogByNo(String order_serial) {
		return payLogDao.getWxpaylogByNo(order_serial);
	}

	public Integer saveWXpayLog(String out_trade_no, String retdata, Date bind_time, Integer total_fee) {
		return payLogDao.saveWXpayLog(out_trade_no, retdata, bind_time, total_fee);
	}

    public List<Map> searchWxPayLog(Map param) {
        return payLogDao.searchWxPayLog(param);
    }

    public int searchWxPayLogCount(Map param) {
        return payLogDao.searchWxPayLogCount(param);
    }
}

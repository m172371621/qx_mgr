package com.brilliantreform.sc.alipay.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.alipay.dao.AliPayLogDao;
import com.brilliantreform.sc.alipay.po.AliPayLogBean;
import com.brilliantreform.sc.weixin.po.WeixinPayLogBean;

@Service("alipayLogService")
public class AliPayLogService {

	@Autowired
	private AliPayLogDao aliPayLogDao;

	
	public List<AliPayLogBean> getAliPayLogList(Map<String, Object> map) {
		return aliPayLogDao.getAliPayLogList(map);
	}

	public Integer getAliPayLogCount(Map<String, Object> map) {
		return aliPayLogDao.getAliPayLogCount(map);
	}

	public AliPayLogBean getAliPayLogByOrderSerial(String orderSerial) {
		return aliPayLogDao.getAliPayLogByOrderSerial(orderSerial);
	}

	public Integer saveAlipayLog(String out_trade_no, String retdata, Date bind_time, Double total_fee) {
		return aliPayLogDao.saveAlipayLog(out_trade_no, retdata, bind_time, total_fee);
	}

    public List<Map> searchAlipayLog(Map param) {
        return aliPayLogDao.searchAlipayLog(param);
    }

    public int searchAlipayLogCount(Map param) {
        return aliPayLogDao.searchAlipayLogCount(param);
    }
}

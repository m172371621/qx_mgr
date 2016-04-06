package com.brilliantreform.sc.weixin.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.weixin.po.WeixinPayLogBean;

@Repository("payLogDao")
public class PayLogDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<WeixinPayLogBean> getWeixinPayLogList(Map<String, Object> map) {
		return (List<WeixinPayLogBean>) sqlMapClient.queryForList(
				"paylog.getWeixinPayLogList", map);
	}

	public Integer getWeixinPayLogCount(Map<String, Object> map) {
		return (Integer) sqlMapClient.queryForObject(
				"paylog.getWeixinPayLogCount", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<WeixinPayLogBean> getAliPayLogList(Map<String, Object> map) {
		return (List<WeixinPayLogBean>) sqlMapClient.queryForList(
				"paylog.getAliPayLogList", map);
	}

	public Integer getAliPayLogCount(Map<String, Object> map) {
		return (Integer) sqlMapClient.queryForObject(
				"paylog.getAliPayLogCount", map);
	}

	public Map getWxpaylogByNo(String order_serial) {
		return (Map)sqlMapClient.queryForObject("paylog.getWxpaylogByNo", order_serial);
	}

	public Integer saveWXpayLog(String out_trade_no, String retdata, Date bind_time, Integer total_fee) {
		Map param = new HashMap();
		param.put("out_trade_no", out_trade_no);
		param.put("retdata", retdata);
		param.put("bind_time", bind_time);
		param.put("total_fee", total_fee);
		return (Integer)sqlMapClient.insert("paylog.saveWXpayLog", param);
	}

    public List<Map> searchWxPayLog(Map param) {
        return sqlMapClient.queryForList("paylog.searchWxPayLog", param);
    }

    public int searchWxPayLogCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("paylog.searchWxPayLogCount", param), 0);
    }
}

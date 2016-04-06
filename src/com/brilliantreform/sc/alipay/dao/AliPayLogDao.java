package com.brilliantreform.sc.alipay.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.order.po.OrderRefund;
import com.brilliantreform.sc.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.alipay.po.AliPayLogBean;
import com.brilliantreform.sc.weixin.po.WeixinPayLogBean;

@Repository("alipayLogDao")
public class AliPayLogDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	
	@SuppressWarnings("unchecked")
	public List<AliPayLogBean> getAliPayLogList(Map<String, Object> map) {
		return (List<AliPayLogBean>) sqlMapClient.queryForList(
				"alipaylog.getAliPayLogList", map);
	}

	public Integer getAliPayLogCount(Map<String, Object> map) {
		return (Integer) sqlMapClient.queryForObject(
				"alipaylog.getAliPayLogCount", map);
	}

	public AliPayLogBean getAliPayLogByOrderSerial(String orderSerial) {
		return (AliPayLogBean) sqlMapClient.queryForObject("alipaylog.getAliPayLogByOrderSerial", orderSerial);
	}

	public Integer saveAlipayLog(String out_trade_no, String retdata, Date bind_time, Double total_fee) {
		Map param = new HashMap();
		param.put("out_trade_no", out_trade_no);
		param.put("retdata", retdata);
		param.put("bind_time", bind_time);
		param.put("total_fee", total_fee);
		return (Integer)sqlMapClient.insert("alipaylog.saveAlipayLog", param);
	}

    public List<Map> searchAlipayLog(Map param) {
        return sqlMapClient.queryForList("alipaylog.searchAlipayLog", param);
    }

    public int searchAlipayLogCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("alipaylog.searchAlipayLogCount", param), 0);
    }
	
}

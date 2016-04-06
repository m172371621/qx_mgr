package com.brilliantreform.sc.order.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.order.po.MsgInfo;

@Repository("MsgInfoDao")
public class MsgInfoDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	// 添加待发送消息
	public Integer addMsgInfo(MsgInfo msginfo) {
		return (Integer) sqlMapClient.insert("msg_info.addMsgInfo", msginfo);
	}

}

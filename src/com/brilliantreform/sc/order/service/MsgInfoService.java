package com.brilliantreform.sc.order.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.order.dao.MsgInfoDao;
import com.brilliantreform.sc.order.po.MsgInfo;

@Service("MsgInfoService")
public class MsgInfoService {

	private static Logger logger = Logger.getLogger(MsgInfoService.class);
	@Autowired
	private MsgInfoDao msgInfoDao;

	// 添加待发送消息
	public Integer addMsgInfo(MsgInfo msginfo) {
		return msgInfoDao.addMsgInfo(msginfo);
	}

	
}

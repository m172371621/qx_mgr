package com.brilliantreform.sc.order.service;

import com.brilliantreform.sc.order.dao.OrderStatusLogDao;
import com.brilliantreform.sc.order.po.OrderStatusLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusLogService {

	private static Logger LOGGER = Logger.getLogger(OrderStatusLogService.class);

	@Autowired
	private OrderStatusLogDao orderStatusLogDao;

	public Integer saveOrderStatusLog(OrderStatusLog orderStatusLog) {
		return orderStatusLogDao.saveOrderStatusLog(orderStatusLog);
	}
}

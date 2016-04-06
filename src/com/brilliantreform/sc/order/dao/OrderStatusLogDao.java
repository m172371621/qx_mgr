package com.brilliantreform.sc.order.dao;

import com.brilliantreform.sc.order.po.OrderStatusLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderStatusLogDao {
	private static Logger LOGGER = Logger.getLogger(OrderStatusLogDao.class);
	private static final String NAMESPACE = "orderStatusLog.";

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Integer saveOrderStatusLog(OrderStatusLog orderStatusLog) {
		if(orderStatusLog != null) {
			if(orderStatusLog.getObjid() == null) {
				return (Integer)sqlMapClient.insert(NAMESPACE + "insertOrderStatusLog", orderStatusLog);
			} else {
				sqlMapClient.update(NAMESPACE + "updateOrderStatusLog", orderStatusLog);
			}
		}
		return null;
	}

}

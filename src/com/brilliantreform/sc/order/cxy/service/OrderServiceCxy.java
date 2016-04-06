package com.brilliantreform.sc.order.cxy.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.order.cxy.dao.OrderDaoCxy;
import com.brilliantreform.sc.order.cxy.po.CountOrder;

@Service("orderServicecxy")
public class OrderServiceCxy {
	private static Logger logger = Logger.getLogger(OrderServiceCxy.class);

	@Autowired
	private OrderDaoCxy  orderDao;
	

	public List<CountOrder>  countOrder(Map<String,String> map){
		return orderDao.countOrder(map);
	}
	
}

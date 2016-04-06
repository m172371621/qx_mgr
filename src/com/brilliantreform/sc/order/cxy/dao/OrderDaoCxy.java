package com.brilliantreform.sc.order.cxy.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.order.cxy.po.CountOrder;
import com.brilliantreform.sc.order.cxy.po.Order;
import com.brilliantreform.sc.order.cxy.po.OrderSearch;

@Repository("orderDaocxy")
public class OrderDaoCxy {
	private static Logger logger = Logger.getLogger(OrderDaoCxy.class);

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	
	
	public List<CountOrder>  countOrder(Map<String,String> map){
		return sqlMapClient.queryForList("ordercxy.sumOrder",map);
	}
	
}

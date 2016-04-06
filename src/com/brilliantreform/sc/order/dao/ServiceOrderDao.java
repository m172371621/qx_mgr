package com.brilliantreform.sc.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.order.po.Order;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.service.po.ServiceVo;

@Repository("serviceOrderDao")
public class ServiceOrderDao {
	private static Logger logger = Logger.getLogger(ServiceOrderDao.class);

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(Map<String,Object> map){
		return (List<Product>)sqlMapClient.queryForList("serviceOrder.getProductList", map);
	}
	
	public void createOrder(Order order) {
		sqlMapClient.insert("serviceOrder.insertOrder", order);
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceVo> getService(Map<String,Object> map){
		return (List<ServiceVo>)sqlMapClient.queryForList("serviceOrder.getService", map);
	}

}

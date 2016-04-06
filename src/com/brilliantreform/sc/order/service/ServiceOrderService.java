package com.brilliantreform.sc.order.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.order.dao.ServiceOrderDao;
import com.brilliantreform.sc.order.po.Order;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.service.po.ServiceVo;

@Service("serviceOrderService")
public class ServiceOrderService {

	@Autowired
	private ServiceOrderDao serviceOrderDao;

	public List<Product> getProductList(Map<String, Object> map) {
		return serviceOrderDao.getProductList(map);

	}

	public void createOrder(Order order) {
		serviceOrderDao.createOrder(order);
	}
	
	public List<ServiceVo> getService(Map<String, Object> map){
		return serviceOrderDao.getService(map);
	}
}

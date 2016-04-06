package com.brilliantreform.sc.POS.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brilliantreform.sc.POS.dao.PosTempDao;
import com.brilliantreform.sc.POS.po.Pos_order_temp_base;
import com.brilliantreform.sc.POS.po.Pos_order_temp_product;
import com.brilliantreform.sc.order.service.OrderService;

@Service("posTempService")
public class PosTempService {
	
	@Autowired
	private PosTempDao posTempDao;
	
	@Autowired
	private OrderService orderService;

	@Transactional
	public Map<String,Object> insertTempData(Map<String,Object> paramMap,List<Map<String,Object>> list){
		int result_code = 0;
		String result_dec = "成功";
		Double order_price = 0.0d;
		String url="";
		List<Pos_order_temp_product> product_list = new ArrayList<Pos_order_temp_product>();
		for (Map<String, Object> map : list) {
			// 处理暂存汇总明细信息
			Integer product_id = Integer.parseInt(map.get("product_id")
					.toString());
			Double product_amount = Double.parseDouble(map
					.get("amount").toString());
			double tmp_order_price=0.0;
			Map<String, Object> tempMap = orderService.checkProductOrder(
					product_id, product_amount, tmp_order_price);
			if (!"0".equals(tempMap.get("result_code").toString())) {
				return tempMap;
			}
			Pos_order_temp_product product = new Pos_order_temp_product();
			product.setProduct_id(product_id);
			product.setProduct_amount(product_amount);
			product.setProduct_price((Double)tempMap.get("product_price"));
			order_price+=(Double)tempMap.get("order_price");
			product_list.add(product);
			url+=product_id+","+product_amount+"|";

		}
		// 处理暂存汇总信息
		Pos_order_temp_base pos = new Pos_order_temp_base();
		pos.setOrder_price(order_price);
		pos.setUrl(url);
		pos.setCreate_by(paramMap.get("user_id").toString());
		pos.setCommunity_id((Integer) paramMap.get("cid"));

		Integer baseid = posTempDao.insertTempDataBase(pos);
		for (Pos_order_temp_product posOrderTempProduct : product_list) {
			posOrderTempProduct.setOrder_temp_base_id(baseid);
		}
		posTempDao.insertTempDataProduct(product_list);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result_code", result_code);
		result.put("result_dec", result_dec);
		result.put("order_price", order_price);
		return result;
	}
}

package com.brilliantreform.sc.scancodeshpping.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.scancodeshpping.dao.PostSanDao;
import com.brilliantreform.sc.scancodeshpping.po.Pos_order_temp_base;
import com.brilliantreform.sc.scancodeshpping.po.Pos_order_temp_product;

@Service("postSanService")
public class PostSanService {
	
	@Autowired
	private PostSanDao postSanDao;
	
	public Pos_order_temp_base seachPostScanBeaseList(int cid) {
		return postSanDao.seachPostScanBeaseList(cid);
	}
	
	public List<Pos_order_temp_product> seachPostScanproductList(Map map) {
		return postSanDao.seachPostScanproductList(map);
	}
}

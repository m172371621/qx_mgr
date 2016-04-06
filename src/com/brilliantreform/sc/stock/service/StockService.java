package com.brilliantreform.sc.stock.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.stock.dao.StockDao;
import com.brilliantreform.sc.stock.po.TotalProduct;

@Service("stockService")
public class StockService {
	private static Logger logger = Logger.getLogger(StockService.class);

	@Autowired
	private StockDao stockDao;

	public List<TotalProduct> getStockList() {
		return stockDao.getStockList();
	}

	public List<Map> shopingInfo(int id) {
		return stockDao.shopingInfo(id);
	}

	// 第一个树
	public List<Map> inventory_classification() {
		return stockDao.inventory_classification();

	}

	// 第一个树子菜单
	public List<Map> inventory_classification_id(int id) {
		return stockDao.inventory_classification_id(id);

	}

	// 第二个树
	public List<Map> getService_base(int cid) {
		return stockDao.getService_base(cid);
	}

	// 第二个树子菜单
	public Integer getService_base_id(Map map) {
		return stockDao.getService_base_id(map);
	}

	// 删除分类
	public Integer delService_base(Map map) {
		return stockDao.delService_base(map);
	}

	// 删除全部商品
	public Integer delService_product_all(Map map) {
		return stockDao.delService_product_all(map);
	}

	// 删除商品
	public Integer delService_product(Map map) {
		return stockDao.delService_product(map);
	}

	// 添加小区分类
	public Integer addService_base(Map map) {
		return stockDao.addService_base(map);
	}
	// 查找总库商品service_id
	public Integer selService_id(Map map) {
		return stockDao.selService_id(map);
	}

	// 检查checkService_base小区商品分类是否村在 返回 ：1 存在 ，0 不存
	public Integer checkService_base(Map map) {
		return stockDao.checkService_base(map);
	}

	// 检查checkService_base小区商品分类是是否村在 返回 ：1 存在 ，0 不存
	public Integer checkService_product(Map map) {
		return stockDao.checkService_product(map);
	}

	//
	public String getService_product_name(int temp_id) {
		return stockDao.getService_product_name(temp_id);
	}

	// 添加 insertService_product(map_id)
	public Integer insertService_product(Map map_id) {
		return stockDao.insertService_product(map_id);
	}

	// 勾选分类却没有勾选商品 就把勾选的service_product_sum下的商品查出
	public List<Map> getService_product_sum(int id) {
		return stockDao.getService_product_sum(id);
	}

	// 更新商品的商品分类
	public Integer update(Map map) {
		return stockDao.update(map);
	}

	// 根据service_id 查出 i_service_id
	public Integer getI_service_id(int service_id) {
		return stockDao.getI_service_id(service_id);
	}

	public List<Map> getService_Product_iservice_id(Map map) {
		return stockDao.getService_Product_iservice_id(map);
	}
	
	/**
	 * 插入小区分类 (根据总库分类ID数据插入到小区分类)
	 */
	public Integer insertService_base(Map map) {
		return stockDao.insertService_base(map);
	}
	
	// 更新商品的商品分类i_service_id
	public Integer updateIServiceId(Map map) {
		return stockDao.updateIServiceId(map);
	}
	// 更新商品的商品i_product_id
	public Integer updateIProductId(Map map) {
		return stockDao.updateIProductId(map);
	}
}

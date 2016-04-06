package com.brilliantreform.sc.stock.dao;

import com.brilliantreform.sc.stock.po.TotalProduct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("stockDao")
public class StockDao {
	private static Logger logger = Logger.getLogger(StockDao.class);
	private static final String NAMESPACE = "stock.";

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<TotalProduct> getStockList() {
		return (List<TotalProduct>) sqlMapClient.queryForList(NAMESPACE.concat("selstocks"));
	}

	@SuppressWarnings("unchecked")
	public List<Map> inventory_classification() {
		return sqlMapClient.queryForList(NAMESPACE.concat("inventory_classification"));
	}

	@SuppressWarnings("unchecked")
	public List<Map> inventory_classification_id(int id) {
		return sqlMapClient.queryForList(NAMESPACE.concat("inventory_classification_id"), id);

	}

	@SuppressWarnings("unchecked")
	public List<Map> shopingInfo(int id) {
		return sqlMapClient.queryForList(NAMESPACE.concat("shopingInfo"), id);
	}

	// 第二个树
	@SuppressWarnings("unchecked")
	public List<Map> getService_base(int cid) {
		return sqlMapClient.queryForList(NAMESPACE.concat("service_base"), cid);
	}

	// 第二个树子菜单
	@SuppressWarnings("unchecked")
	public Integer getService_base_id(Map map) {
		return (Integer) sqlMapClient.queryForObject(NAMESPACE.concat("getService_base_id"), map);
	}

	// 删除分类
	public Integer delService_base(Map map) {
		return (Integer) sqlMapClient.delete(NAMESPACE.concat("delService_base"), map);
	}

	// 删除商品
	public Integer delService_product_all(Map map) {
		return (Integer) sqlMapClient.delete(NAMESPACE.concat("delService_product_all"), map);
	}

	// 删除商品
	public Integer delService_product(Map map) {
		return (Integer) sqlMapClient.delete(NAMESPACE.concat("delService_product"), map);
	}

	// 添加分类
	public Integer addService_base(Map map) {
		return (Integer) sqlMapClient.insert(NAMESPACE.concat("addService_base"), map);
	}
	
	// 添加分类
	public Integer selService_id(Map map) {
		return (Integer) sqlMapClient.queryForObject(NAMESPACE.concat("selService_id"), map);
	}

	// 检查小区商品分类有没有
	public Integer checkService_base(Map map) {
		return (Integer) sqlMapClient.queryForObject(NAMESPACE.concat("checkService_base"), map);
	}

	// 检查小区商品信息有没有
	public Integer checkService_product(Map map) {
		return (Integer) sqlMapClient.queryForObject(NAMESPACE.concat("checkService_product"), map);
	}

	//
	public String getService_product_name(int temp) {
		return (String) sqlMapClient.queryForObject(NAMESPACE.concat("getService_product_name"), temp);
	}

	// 添加小区商品信息
	public Integer insertService_product(Map map_id) {
		return (Integer) sqlMapClient.insert(NAMESPACE.concat("insertService_product"), map_id);
	}

	// 勾选分类却没有勾选商品 就把勾选的service_product_sum下的商品查出
	public List<Map> getService_product_sum(int id) {
		return (List<Map>) sqlMapClient.queryForList(NAMESPACE.concat("getService_product_sum"), id);
	}

	// 更新商品的商品分类
	public Integer update(Map map) {
		return sqlMapClient.update(NAMESPACE.concat("updata"), map);
	}
	
	// 更新商品的商品分类i_service_id
	public Integer updateIServiceId(Map map) {
		return sqlMapClient.update(NAMESPACE.concat("updateIServiceId"), map);
	}

	// 根据service_id 查出 i_service_id
	public Integer getI_service_id(int service) {
		return (Integer) sqlMapClient.queryForObject(NAMESPACE.concat("getI_service_id"), service);
	}

	public List<Map> getService_Product_iservice_id(Map map) {
		return sqlMapClient.queryForList(NAMESPACE.concat("getService_Product_iservice_id"), map);
	}
	
	/**
	 * 插入小区分类 (根据总库分类ID数据插入到小区分类)
	 */
	public Integer insertService_base(Map map) {
		return (Integer) sqlMapClient.insert(NAMESPACE.concat("insertService_base"), map);
	}
	
	// 更新商品的商品i_product_id
	public Integer updateIProductId(Map map) {
		return sqlMapClient.update(NAMESPACE.concat("updateIProductId"), map);
	}
}
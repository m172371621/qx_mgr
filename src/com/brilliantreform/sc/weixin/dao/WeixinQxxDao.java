package com.brilliantreform.sc.weixin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.order.po.Order;
import com.brilliantreform.sc.product.po.ProductDouble;
import com.brilliantreform.sc.weixin.po.WeixinQxx;

@Repository
public class WeixinQxxDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	// 查询是否存在该用户对应的信息 参数openid，返回手机、我的推荐码、他人推荐码等信息
	public WeixinQxx getPersonalInfo(Map<String, Object> map) {
		return (WeixinQxx) sqlMapClient.queryForObject(
				"weixinqxx.getPersonalInfo", map);
	}

	// 填写相关个人信息生成个人信息记录，返回我的个人推荐码，参数openid，姓名、地址等，返回个人推荐码
	public void insertPersonalInfo(WeixinQxx qxx) {
		sqlMapClient.insert("weixinqxx.insertPersonalInfo", qxx);
	}
	
	//更新已经购买数量，参数openid，修改buy_count
	public void updatePersonalInfo(WeixinQxx qxx) {
		sqlMapClient.insert("weixinqxx.updatePersonalInfo", qxx);
	}

	// 查询指定小区的促销商品信息 返回商品信息
	public ProductDouble getProductInfo(Map<String, Object> map) {
		return (ProductDouble) sqlMapClient.queryForObject(
				"weixinqxx.getProductInfo", map);
	}

	// 查询用户是否已经购买过此商品
	public Integer getBuyCount(Map<String, Object> paramMap) {
		return (Integer) sqlMapClient.queryForObject("weixinqxx.getBuyCount",
				paramMap);
	}
	
	//插入order_base
	public void insertOrderBase(Order order) {
		sqlMapClient.insert("weixinqxx.insertOrderBase", order);
	}
	//插入order_product
	public void insertOrderProduct(Order order) {
		sqlMapClient.insert("weixinqxx.insertOrderproduct", order);
	}
	
	public List<WeixinQxx> getRecommendList(Map<String, Object> paramMap){
		return sqlMapClient.queryForList("weixinqxx.getRecommendList", paramMap);
	}
}

package com.brilliantreform.sc.order.dao;

import com.brilliantreform.sc.order.po.*;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("orderDao")
public class OrderDao {
	private static Logger logger = Logger.getLogger(OrderDao.class);
	private static final String NAMESPACE = "order.";
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	

	public Integer createOrder(Order order) {
		return (Integer)sqlMapClient.insert("order.insertOrder", order);
	}

	@SuppressWarnings("unchecked")
	public List<Order> getOrderList(Map map) {
		return (List<Order>) sqlMapClient.queryForList("order.getOrderList",
				map);
	}

	public Order getOrderById(Integer order_id) {
		return (Order) sqlMapClient.queryForObject("order.getOrderById",
				order_id);
	}

	public Order getOrderBySerial(String order_serial) {
		return (Order) sqlMapClient.queryForObject("order.getOrderBySerial",
				order_serial);
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getOrderBySerialList(String order_serial) {
		return (List<Order>)sqlMapClient.queryForList("order.getOrderBySerialList",
				order_serial);
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getOrderBySerialList2(Order order) {
		return (List<Order>)sqlMapClient.queryForList("order.getOrderBySerialList2",
				order);
	}
	
	public void updateOrder(Order order) {
		sqlMapClient.update("order.updateOrder", order);
	}

	@SuppressWarnings("unchecked")
	public List<Order> searchOrderList(OrderSearch searchBean) {
		return (List<Order>) sqlMapClient.queryForList("order.searchOrderList",
				searchBean);
	}

	public int countOrderList(OrderSearch searchBean) {
		return (Integer) sqlMapClient.queryForObject("order.countOrderList",
				searchBean);
	}

	public int countOrderByProduct(Integer productId) {
		return (Integer) sqlMapClient.queryForObject(
				"order.countOrderByProduct", productId);
	}

	@SuppressWarnings("unchecked")
	public int countProduct(Map map) {
		return (Integer) sqlMapClient.queryForObject("order.countProduct", map);
	}

	@SuppressWarnings("unchecked")
	public List<Map> searchProduct(Map map) {
		return (List<Map>) sqlMapClient
				.queryForList("order.searchProduct", map);
	}

	public void updateOrderByProduct(Integer productId) {
		sqlMapClient.update("order.updateOrderByProduct", productId);
	}

	public void deleteOrder(Integer id) {
		sqlMapClient.insert("order.insertDeleteOrder", id);
		sqlMapClient.delete("order.deleteOrder", id);
	}

	public void updateOrderCard(Integer id) {
		sqlMapClient.update("order.updateUserCard", id);
	}

	public int countNewOrder(int cid) {
		return (Integer) sqlMapClient.queryForObject("order.countNewOrder",cid);
	}
	
	
	public List<CountOrder>  countOrder(Map<String,String> map){
		return sqlMapClient.queryForList("order.sumOrder",map);
	}
	
	@SuppressWarnings("unchecked")
	public Map p_orderCreate(Map paramMap)
	{
		
		sqlMapClient.queryForObject("order.p_orderCreate", paramMap);  
	 
		return paramMap;  
	}
	
	public List<Order> getOrderReturnList(String order_serial){
		return sqlMapClient.queryForList("order.orderReturnList", order_serial);
		
	}
	
	 @SuppressWarnings("unchecked")
		public List<Map> select_Stock_change_log(String param){
			return (List<Map> )sqlMapClient.queryForList("order.select_Stock_change_log",param);
	}
	 
	  @SuppressWarnings("unchecked")
		public void update_product_real_stock(Map paramBean){
			sqlMapClient.update("order.update_product_real_stock",paramBean);
	}
	  
	  @SuppressWarnings("unchecked")
	  public void update_product_batch_stock(Map paramBean){
		sqlMapClient.update("order.update_product_batch_stock", paramBean);
	  }
	  
	  @SuppressWarnings("unchecked")
		public void insert_stock_change_log(Map paramBean){
			sqlMapClient.insert("order.insert_stock_change_log", paramBean);
		}
	  
	  public void updateOrderBySerial(Order order){
			sqlMapClient.update("order.updateOrderBySerial", order);
		}
	  
	  /**
	   * 插入录入
	   * @param addInfo
	   * @return
	   */
	  public int addOrder(AddInfo addInfo){
		  return (Integer)sqlMapClient.insert("order.addOrder", addInfo);
		  
	  }
	  
	  /**
	   * 批次查询
	   * @param service_name
	   * @return
	   */
	  public List<QueryBatch> queryBatchList(QueryBatch batch){
		  return sqlMapClient.queryForList("order.queryBatchList", batch);
	  }
	  /**
	   * 查询 批次数量
	   * @param batch
	   * @return
	   */
	  public int queryBatchCount(QueryBatch batch){
		  return (Integer)sqlMapClient.queryForObject("order.queryBatchCount", batch);
		  
	  }
	  
	  /**
	   * 中奖记录查询
	   * @param map
	   * @return
	   */
	  public List<WinningInfo> getWinning(Map map){
		return (List<WinningInfo>) sqlMapClient.queryForList("order.getWinning", map);
	  }
	  
	  /**
	   * 中奖记录 数量 （进行分页）
	   * @param map
	   * @return
	   */
	  public int getWinningCount(Map map){
			return (Integer)sqlMapClient.queryForObject("order.getWinningCount", map);
		  }
	  
	  /**
		 * 更新领奖状态
		 */
		public void awardUpdate(Map orderID) {
			sqlMapClient.update("order.awardUpdate", orderID);
		}

	public double getOrderAmount(String order_serial) {
		return CommonUtil.safeToDouble(sqlMapClient.queryForObject("order.getOrderAmount", order_serial), 0d);
	}

	public void updateOrderStatusById(int order_id, int order_status) {
		Map param = new HashMap();
		param.put("order_id", order_id);
		param.put("order_status", order_status);
		sqlMapClient.update("order.updateOrderStatusById", param);
	}

	public void updateOrderBaseStatus(String order_serial, int order_status) {
		Map param = new HashMap();
		param.put("order_serial", order_serial);
		param.put("order_status", order_status);
		sqlMapClient.update("order.updateOrderBaseStatus", param);
	}

	public List<Map> searchOrderBase(Map param) {
		return sqlMapClient.queryForList(NAMESPACE + "searchOrderBase", param);
	}

	public int searchOrderBaseCount(Map param) {
		return (Integer)sqlMapClient.queryForObject(NAMESPACE + "searchOrderBaseCount", param);
	}

	public List<Order> findOrderProduct(String order_serial) {
		return sqlMapClient.queryForList(NAMESPACE + "findOrderProduct", order_serial);
	}

	public List<Map> findOrderProductTemp(String order_serial) {
		return sqlMapClient.queryForList(NAMESPACE + "findOrderProductTemp", order_serial);
	}

	public Map getOrderBaseById(String order_serial) {
		return (Map)sqlMapClient.queryForObject(NAMESPACE + "getOrderBaseById", order_serial);
	}

	public List<String> findOrderSerial(Map param) {
		return sqlMapClient.queryForList("order.findOrderSerial", param);
	}

	public void updateOrderBaseStatus(int order_status, String order_serial, String[] order_serial_array) {
		if(StringUtils.isNotBlank(order_serial) || (order_serial_array != null && order_serial_array.length > 0)) {
			Map param = new HashMap();
			param.put("order_status", order_status);
			param.put("order_serial", order_serial);
			param.put("order_serial_array", order_serial_array);
			sqlMapClient.update("order.updateOrderBaseStatus2", param);
		}
	}

	/**
	 * 获取一段时间内未支付订单
	 * @param time 单位：分钟
	 * */
	public List<Map> findUnPayOrder(int time) {
		return sqlMapClient.queryForList("order.findUnPayOrder", time);
	}

	public void pickUpOrder(String order_serial) {
		sqlMapClient.update("order.pickUpOrder", order_serial);
	}

	public void updatePreSaleOrder(String order_serial) {
		sqlMapClient.update("order.updatePreSaleOrder", order_serial);
	}

	public void updatePreSaleOrderProduct(String order_serial) {
		sqlMapClient.update("order.updatePreSaleOrderProduct", order_serial);
	}

	public void pickUpOrderProduct(String order_serial) {
		sqlMapClient.update("order.pickUpOrderProduct", order_serial);
	}

	public Map getOrderBySerial3(String order_serial) {
		return (Map)sqlMapClient.queryForObject("order.getOrderBySerial3", order_serial);
	}

	public Map getOrderProductMapById(int order_id) {
		return (Map) sqlMapClient.queryForObject(NAMESPACE + "getOrderProductMapById", order_id);
	}

	public void updateUserCardIdBySerial(String order_serial, int card_id) {
		Map param = new HashMap();
		param.put("order_serial", order_serial);
		param.put("card_id", card_id);
		sqlMapClient.update("order.updateUserCardIdBySerial", param);
	}
	
	@SuppressWarnings("unchecked")
	public Map p_torderCreate(Map paramMap)
	{
		
		sqlMapClient.queryForObject("order.p_torderCreate", paramMap);  
	 
		return paramMap;  
	}

	/**
	 * 根据订单号 查 user_id
	 * @param order_serial
	 * @return
	 */
	public int selOrderProductUserid(String order_serial) {
		return (Integer)sqlMapClient.queryForObject("order.selOrderProductUserid",order_serial);
	}
	
	public Integer getNewOrderCount(Map<String, Object> param){
		return (Integer) sqlMapClient.queryForObject("order.getNewOrderCount", param);
	}

    public List<Map> searchProductBatch(Map param) {
        return sqlMapClient.queryForList("order.searchProductBatch", param);
    }

    public int searchProductBatchCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("order.searchProductBatchCount", param), 0);
    }

    public void finishDelivery(String order_serial) {
        sqlMapClient.update("order.finishDelivery", order_serial);
    }

	public List<Map> findDeliveryer(Map map) {
		return sqlMapClient.queryForList("order.findDeliveryer", map);
	}

	public int getDeliveryCount(Map map) {
		return CommonUtil.safeToInteger(sqlMapClient.queryForObject("order.getDeliveryCount", map), 0);
	}

	public DeliveryAddress getDeliveryAddress(int user_id) {
		return (DeliveryAddress) sqlMapClient.queryForObject(NAMESPACE + "getDeliveryAddress", user_id);
	}

	public void updateDeliveryStatus(String order_serial, int delivery_status) {
		Map map = new HashMap();
		map.put("order_serial", order_serial);
		map.put("delivery_status", delivery_status);
		sqlMapClient.update(NAMESPACE + "updateDeliveryStatus", map);
	}
}

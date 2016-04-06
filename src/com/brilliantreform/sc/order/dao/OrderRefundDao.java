package com.brilliantreform.sc.order.dao;

import com.brilliantreform.sc.order.po.*;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import javax.print.attribute.standard.MediaSize;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRefundDao {
	private static Logger logger = Logger.getLogger(OrderRefundDao.class);
	private static final String NAMESPACE = "orderRefund.";

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	

	public Integer saveOrderRefund(OrderRefund orderRefund) {
		if(orderRefund != null) {
			if(orderRefund.getObjid() == null) {
				return (Integer)sqlMapClient.insert("orderRefund.saveOrderRefund", orderRefund);
			} else {
				sqlMapClient.update(NAMESPACE + "updateOrderRefund", orderRefund);
			}
		}
		return null;
	}

	public List<OrderRefund> findOrderRefundByOrderId(Integer order_id) {
		return sqlMapClient.queryForList(NAMESPACE + "findOrderRefundByOrderId", order_id);
	}

	public double getRefundAmount(int order_id, int[] status, Integer objid) {
		Map param = new HashMap();
		param.put("order_id", order_id);
		param.put("status", status);
		if(objid != null) {
			param.put("objid", objid);
		}
		return CommonUtil.safeToDouble(sqlMapClient.queryForObject(NAMESPACE + "getRefundAmount", param), 0d);
	}

	public List<Map> searchRefund(Map param) {
		return sqlMapClient.queryForList(NAMESPACE + "searchRefund", param);
	}

	public int searchRefundCount(Map param) {
		return (Integer)sqlMapClient.queryForObject(NAMESPACE + "searchRefundCount", param);
	}

	public OrderRefund getOrderRefundById(Integer objid) {
		return (OrderRefund)sqlMapClient.queryForObject(NAMESPACE + "getOrderRefundById", objid);
	}

	public boolean isOrderAward(int order_id) {
		int count = CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "checkOrderAward", order_id), 0);
		return count > 0 ? true : false;
	}

	/**
	 * 查询某商品的出售记录
	 * */
	public Map getSellChangLogByProduct(String order_serial, int product_id) {
		Map param = new HashMap();
		param.put("order_serial", order_serial);
		param.put("product_id", product_id);
		List<Map> list = sqlMapClient.queryForList(NAMESPACE + "findSellChangLogByProduct", param);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	public List<OrderRefund> findRefundByRefundSerial(String refund_serial, Integer refund_status) {
		Map param = new HashMap();
		param.put("refund_serial", refund_serial);
		param.put("refund_status", refund_status);
		return sqlMapClient.queryForList(NAMESPACE + "findRefundByRefundSerial", param);
	}

	/**
	 * 获取订单的退货总数(退货完成状态)
	 * */
	public double getReGoodsAmount(String order_serial) {
		return CommonUtil.safeToDouble(sqlMapClient.queryForObject(NAMESPACE + "getReGoodsAmount", order_serial), 0d);
	}

	/**
	 * 获取订单的退货总数（退货中+退货完成）
	 * */
	public double getAllReGoodsAmount(String order_serial) {
		return CommonUtil.safeToDouble(sqlMapClient.queryForObject(NAMESPACE + "getAllReGoodsAmount", order_serial), 0d);
	}

	public void insertOrderRefundBase(OrderRefundBase orderRefundBase) {
		sqlMapClient.insert(NAMESPACE + "insertOrderRefundBase", orderRefundBase);
	}

	public void updateOrderRefundBase(OrderRefundBase orderRefundBase) {
		sqlMapClient.update(NAMESPACE + "updateOrderRefundBase", orderRefundBase);
	}

	public void insertOrderRefundProduct(OrderRefundProduct orderRefundProduct) {
		sqlMapClient.insert(NAMESPACE + "insertOrderRefundProduct", orderRefundProduct);
	}

	public void updateOrderRefundProduct(OrderRefundProduct orderRefundProduct) {
		sqlMapClient.update(NAMESPACE + "updateOrderRefundProduct", orderRefundProduct);
	}

	/**
	 * 获取某个小订单还能退货的商品数量：该小订单总数-待处理或已完成的数量
	 * */
	public double getCanRefundProductAmount(int order_id, int[] return_status) {
		Map map = new HashMap();
		map.put("order_id", order_id);
		map.put("return_status", return_status);
		return CommonUtil.safeToDouble(sqlMapClient.queryForObject(NAMESPACE + "getCanRefundProductAmount", map), 0d);
	}

	/**
	 * 获取某个大订单所有退货的商品数量
	 * */
	public double sumRefundAmountBySerial(String order_serial, int[] return_status) {
		Map map = new HashMap();
		map.put("order_serial", order_serial);
		map.put("return_status", return_status);
		return CommonUtil.safeToDouble(sqlMapClient.queryForObject(NAMESPACE + "sumRefundAmountBySerial", map), 0d);
	}

	/**
	 * 获取某个大订单的可退款金额
	 * */
	public double getCanRefundMoney(String order_serial) {
		return CommonUtil.safeToDouble(sqlMapClient.queryForObject(NAMESPACE + "getCanRefundMoney", order_serial), 0d);
	}

	public List<Map> searchOrderRefundBase(Map param) {
		return sqlMapClient.queryForList(NAMESPACE + "searchOrderRefundBase", param);
	}

	public int searchOrderRefundBaseCount(Map param) {
		return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchOrderRefundBaseCount", param), 0);
	}

	public List<Map> searchOrderRefundProduct(Map param) {
		return sqlMapClient.queryForList(NAMESPACE + "searchOrderRefundProduct", param);
	}

	public OrderRefundBase getOrderRefundBaseById(int objid) {
		return (OrderRefundBase) sqlMapClient.queryForObject(NAMESPACE + "getOrderRefundBaseById", objid);
	}

	public OrderRefundBase getOrderRefundBaseByRefundSerial(String refund_serial, Integer refund_status) {
		Map map = new HashMap();
		map.put("refund_serial", refund_serial);
		map.put("refund_status", refund_status);
		return (OrderRefundBase) sqlMapClient.queryForObject(NAMESPACE + "getOrderRefundBaseByRefundSerial", map);
	}

}

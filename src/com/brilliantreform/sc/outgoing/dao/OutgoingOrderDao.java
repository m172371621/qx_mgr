package com.brilliantreform.sc.outgoing.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.incomming.po.IncommingDetailBean;
import com.brilliantreform.sc.incomming.po.IncommingHeaderBean;
import com.brilliantreform.sc.incomming.po.ProductBatchStockBean;
import com.brilliantreform.sc.incomming.po.ProductRealStockBean;
import com.brilliantreform.sc.incomming.po.StockChangeLogBean;
import com.brilliantreform.sc.product.po.ProductDouble;

@Repository("outgoingOrderDao")
public class OutgoingOrderDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<IncommingHeaderBean> getIncommingHeader(
			Map<String,Object> map) {
		return (List<IncommingHeaderBean>) sqlMapClient.queryForList(
				"outgoing_order.getIncommingHeader", map);
	}
	
	public Integer getIncommingHeaderCount(
			Map<String,Object> map) {
		return (Integer) sqlMapClient.queryForObject(
				"outgoing_order.getIncommingHeaderCount", map);
	}

	@SuppressWarnings("unchecked")
	public List<IncommingDetailBean> getIncommintDetail(
			IncommingDetailBean detailBean) {
		return (List<IncommingDetailBean>) sqlMapClient.queryForList(
				"outgoing_order.getIncommingDetail", detailBean);
	}
	
	public Integer getIncommintDetailCount(
			IncommingDetailBean detailBean) {
		return (Integer) sqlMapClient.queryForObject(
				"outgoing_order.getIncommingDetailCount", detailBean);
	}

	public Integer insertIncommingHeader(IncommingHeaderBean headerBean) {
		return (Integer) sqlMapClient.insert(
				"outgoing_order.insertIncommingHeader", headerBean);
	}
	
	public void updateHeaderInfo(IncommingHeaderBean headerBean) {
		sqlMapClient.insert(
				"outgoing_order.updateHeaderInfo", headerBean);
	}

	public void insertIncommingDetail(IncommingDetailBean detailBean) {
		sqlMapClient.insert("outgoing_order.insertIncommingDetail", detailBean);
	}

	@SuppressWarnings("unchecked")
	public List<ProductDouble> queryGoods(Map<String, Object> map) {
		return (List<ProductDouble>) sqlMapClient.queryForList(
				"outgoing_order.queryGoods", map);
	}

	public void delIncommintHeader(IncommingHeaderBean headerBean) {
		sqlMapClient.delete("outgoing_order.delIncommintHeader", headerBean);
	}

	public void delIncommintDetail(IncommingDetailBean detailBean) {
		sqlMapClient.delete("outgoing_order.delIncommintDetail", detailBean);
	}
	public String getMaxNo(Map<String,Object> map) {
		return (String) sqlMapClient.queryForObject("outgoing_order.getMaxNo", map);
	}
	
	public ProductRealStockBean getProductStockById(ProductRealStockBean realStockBean) {
		return (ProductRealStockBean) sqlMapClient.queryForObject("outgoing_order.getProductStockById", realStockBean);
	}
	
	public void insertProductStock(ProductRealStockBean realStockBean) {
		sqlMapClient.insert("outgoing_order.insertProductStock", realStockBean);
	}
	
	public void updateProductStockById(ProductRealStockBean realStockBean) {
		sqlMapClient.update("outgoing_order.updateProductStockById", realStockBean);
	}
	
	
	public ProductBatchStockBean getProductBatchStockById(ProductBatchStockBean batchBean) {
		return (ProductBatchStockBean) sqlMapClient.queryForObject("outgoing_order.getProductBatchStockById", batchBean);
	}
	
	public void insertProductBatchStock(ProductBatchStockBean batchStockBean) {
		sqlMapClient.insert("outgoing_order.insertProductBatchStock", batchStockBean);
	}
	
	public void updateProductBatchStockById(ProductBatchStockBean batchStockBean) {
		sqlMapClient.update("outgoing_order.updateProductBatchStockById", batchStockBean);
	}
	
	public void insertChangeLog(StockChangeLogBean changeLogBean) {
		sqlMapClient.insert("outgoing_order.insertChangeLog", changeLogBean);
	}

}

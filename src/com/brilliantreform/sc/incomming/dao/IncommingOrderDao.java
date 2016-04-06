package com.brilliantreform.sc.incomming.dao;

import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.incomming.po.IncommingDetailBean;
import com.brilliantreform.sc.incomming.po.IncommingHeaderBean;
import com.brilliantreform.sc.incomming.po.ProductBatchStockBean;
import com.brilliantreform.sc.incomming.po.ProductRealStockBean;
import com.brilliantreform.sc.incomming.po.StockChangeLogBean;
import com.brilliantreform.sc.product.po.Product;

@Repository("incommingOrderDao")
public class IncommingOrderDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<IncommingHeaderBean> getIncommingHeader(
			Map<String,Object> map) {
		return (List<IncommingHeaderBean>) sqlMapClient.queryForList(
				"incoming_order.getIncommingHeader", map);
	}
	
	public Integer getIncommingHeaderCount(
			Map<String,Object> map) {
		return (Integer) sqlMapClient.queryForObject(
				"incoming_order.getIncommingHeaderCount", map);
	}

	@SuppressWarnings("unchecked")
	public List<IncommingDetailBean> getIncommintDetail(
			IncommingDetailBean detailBean) {
		return (List<IncommingDetailBean>) sqlMapClient.queryForList(
				"incoming_order.getIncommingDetail", detailBean);
	}
	
	public Integer getIncommintDetailCount(
			IncommingDetailBean detailBean) {
		return (Integer) sqlMapClient.queryForObject(
				"incoming_order.getIncommingDetailCount", detailBean);
	}

	public Integer insertIncommingHeader(IncommingHeaderBean headerBean) {
		return (Integer) sqlMapClient.insert(
				"incoming_order.insertIncommingHeader", headerBean);
	}
	
	public void updateHeaderInfo(IncommingHeaderBean headerBean) {
		sqlMapClient.insert(
				"incoming_order.updateHeaderInfo", headerBean);
	}

	public void insertIncommingDetail(IncommingDetailBean detailBean) {
		sqlMapClient.insert("incoming_order.insertIncommingDetail", detailBean);
	}

	@SuppressWarnings("unchecked")
	public List<Product> queryGoods(Map<String, Object> map) {
		return (List<Product>) sqlMapClient.queryForList(
				"incoming_order.queryGoods", map);
	}
	@SuppressWarnings("unchecked")
	public List<Product> queryGoods_zj(Map<String, Object> map) {
		return (List<Product>) sqlMapClient.queryForList(
				"incoming_order.queryGoods_zj", map);
	}
	public void delIncommintHeader(IncommingHeaderBean headerBean) {
		sqlMapClient.delete("incoming_order.delIncommintHeader", headerBean);
	}

	public void delIncommintDetail(IncommingDetailBean detailBean) {
		sqlMapClient.delete("incoming_order.delIncommintDetail", detailBean);
	}
	public String getMaxNo(Map<String,Object> map) {
		return (String) sqlMapClient.queryForObject("incoming_order.getMaxNo", map);
	}
	
	public ProductRealStockBean getProductStockById(ProductRealStockBean realStockBean) {
		return (ProductRealStockBean) sqlMapClient.queryForObject("incoming_order.getProductStockById", realStockBean);
	}
	
	public void insertProductStock(ProductRealStockBean realStockBean) {
		sqlMapClient.insert("incoming_order.insertProductStock", realStockBean);
	}
	
	public void updateProductStockById(ProductRealStockBean realStockBean) {
		sqlMapClient.update("incoming_order.updateProductStockById", realStockBean);
	}
	
	
	public ProductBatchStockBean getProductBatchStockById(ProductBatchStockBean batchBean) {
		return (ProductBatchStockBean) sqlMapClient.queryForObject("incoming_order.getProductBatchStockById", batchBean);
	}
	
	public void insertProductBatchStock(ProductBatchStockBean batchStockBean) {
		sqlMapClient.insert("incoming_order.insertProductBatchStock", batchStockBean);
	}
	
	public void updateProductBatchStockById(ProductBatchStockBean batchStockBean) {
		sqlMapClient.update("incoming_order.updateProductBatchStockById", batchStockBean);
	}
	
	public void insertChangeLog(StockChangeLogBean changeLogBean) {
		sqlMapClient.insert("incoming_order.insertChangeLog", changeLogBean);
	}

    public List<Map> searchIncommingHeader(Map param) {
        return sqlMapClient.queryForList("incoming_order.searchIncommingHeader", param);
    }

    public int searchIncommingHeaderCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("incoming_order.searchIncommingHeaderCount", param), 0);
    }

    public List<Map> searchInCommingDetail(Map param) {
        return sqlMapClient.queryForList("incoming_order.searchInCommingDetail", param);
    }

    public int searchInCommingDetailCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("incoming_order.searchInCommingDetailCount", param), 0);
    }
}

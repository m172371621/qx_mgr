package com.brilliantreform.sc.purchase.dao;

import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.purchase.po.PurchaseCar;
import com.brilliantreform.sc.purchase.po.PurchaseDetail;
import com.brilliantreform.sc.purchase.po.PurchaseHeader;
import com.brilliantreform.sc.purchase.po.SupplierInfo;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.stock.po.TotalProduct;
import com.brilliantreform.sc.stock.po.TotalService;
import com.brilliantreform.sc.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PurchaseDao {

	private static final String NAMESPACE = "purchase.";

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	public List<Map> findCartInfo(int user_id) {
		return sqlMapClient.queryForList("purchase.findCartInfo", user_id);
	}

	// 删除购物车中商品
	public void delCartInfo(int cart_id) {
		sqlMapClient.delete("purchase.delCartInfo", cart_id);
	}

	// 查询待审核的进货汇总单
	@SuppressWarnings("unchecked")
	public List<Map> getPurchaseHeader(Map<String, Object> map) {
		return sqlMapClient.queryForList("purchase.getPurchaseHeader", map);
	}
	
	//查询待审核的进货汇总单总数
	public Integer getPurchaseHeaderCount(Map<String, Object> map) {
		return (Integer) sqlMapClient.queryForObject("purchase.getPurchaseHeaderCount",
				map);
	}

	public List<Map> findPurchaseDetailByHeaderId(int header_id) {
		return sqlMapClient.queryForList(NAMESPACE + "findPurchaseDetailByHeaderId", header_id);
	}

	public Map getPurchaseHeaderById(int header_id) {
		return (Map) sqlMapClient.queryForObject(NAMESPACE + "getPurchaseHeaderById", header_id);
	}

	public PurchaseHeader getHeaderById(int header_id) {
		return (PurchaseHeader) sqlMapClient.queryForObject(NAMESPACE + "getHeaderById", header_id);
	}

	// 审核进货单
	public Integer auditPurchase(Map<String, Object> map) {
		return sqlMapClient.update("purchase.auditPurchase", map);
	}

	public List<TotalProduct> searchProduct(Map param) {
		return sqlMapClient.queryForList(NAMESPACE + "searchProduct", param);
	}

	public int searchProductCount(Map param) {
		return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchProductCount", param), 0);
	}

	public TotalService getServiceById(Integer service_id) {
		return (TotalService)sqlMapClient.queryForObject(NAMESPACE + "getServiceById", service_id);
	}

	public TotalProduct getProductById(int product_id) {
		return (TotalProduct) sqlMapClient.queryForObject(NAMESPACE + "getProductById", product_id);
	}

	public PurchaseCar getPurchaseCartByProduct(int user_id, int product_id) {
		Map param = new HashMap();
		param.put("user_id", user_id);
		param.put("product_id", product_id);
		return (PurchaseCar) sqlMapClient.queryForObject(NAMESPACE + "getPurchaseCartByProduct", param);
	}

	public void insertPurchaseCar(PurchaseCar purchaseCar) {
		sqlMapClient.insert(NAMESPACE + "insertPurchaseCar", purchaseCar);
	}

	public void updatePurchaseCar(PurchaseCar purchaseCar) {
		sqlMapClient.update(NAMESPACE + "updatePurchaseCar", purchaseCar);
	}

	public PurchaseCar savePurchaseCar(PurchaseCar purchaseCar) {
		if(purchaseCar != null) {
			if(purchaseCar.getPurchase_cart_id() != null) {
				updatePurchaseCar(purchaseCar);
			} else {
				insertPurchaseCar(purchaseCar);
			}
		}
		return purchaseCar;
	}

	public int getCartCount(int user_id) {
		return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "getCartCount", user_id), 0);
	}

	public void insertPurchaseDetail(PurchaseDetail detail) {
		sqlMapClient.insert(NAMESPACE + "insertPurchaseDetail", detail);
	}

	public void updatePurchaseDetail(PurchaseDetail detail) {
		sqlMapClient.update(NAMESPACE + "updatePurchaseDetail", detail);
	}

	public PurchaseDetail savePurchaseDetail(PurchaseDetail detail) {
		if(detail != null) {
			if(detail.getPurchase_detail_id() != null) {
				updatePurchaseDetail(detail);
			} else {
				insertPurchaseDetail(detail);
			}
		}
		return detail;
	}

	public void insertPurchaseHeader(PurchaseHeader header) {
		sqlMapClient.insert(NAMESPACE + "insertPurchaseHeader", header);
	}

	public void updatePurchaseHeader(PurchaseHeader header) {
		sqlMapClient.update(NAMESPACE + "updatePurchaseHeader", header);
	}

	public PurchaseHeader savePurchaseHeader(PurchaseHeader header) {
		if(header != null) {
			if(header.getPurchase_header_id() != null) {
				updatePurchaseHeader(header);
			} else {
				insertPurchaseHeader(header);
			}
		}
		return header;
	}

	public void removePurchaseCart(int user_id) {
		sqlMapClient.delete(NAMESPACE + "removePurchaseCart", user_id);
	}

	/**
	 * 根据商品获取供应商
	 * */
	public List<SupplierInfo> findSupplierByProductId(int product_id) {
		return (List<SupplierInfo>) sqlMapClient.queryForList(NAMESPACE + "findSupplierByProductId", product_id);
	}

	public List<TotalProduct> findProductByKeyword(String keyword, Integer header_id) {
		Map param = new HashMap();
		param.put("keyword", keyword);
		param.put("header_id", header_id);
		return (List<TotalProduct>) sqlMapClient.queryForList(NAMESPACE + "findProductByKeyword", param);
	}

	public Product getChildProByTotalProId(int community_id, int total_product_id) {
		Map param = new HashMap();
		param.put("community_id", community_id);
		param.put("total_product_id", total_product_id);
		return (Product) sqlMapClient.queryForObject(NAMESPACE + "getChildProByTotalProId", param);
	}

	public ServiceVo getChildServiceByTotalServiceId(int community_id, int total_service_id) {
		Map param = new HashMap();
		param.put("community_id", community_id);
		param.put("total_service_id", total_service_id);
		return (ServiceVo) sqlMapClient.queryForObject(NAMESPACE + "getChildServiceByTotalServiceId", param);
	}

	public Map getPurchaseDetailById(int purchase_detail_id) {
		return (Map) sqlMapClient.queryForObject(NAMESPACE + "getPurchaseDetailById", purchase_detail_id);
	}

	public PurchaseDetail getPurchaseDetail(int purchase_detail_id) {
		return (PurchaseDetail) sqlMapClient.queryForObject(NAMESPACE + "getPurchaseDetail", purchase_detail_id);
	}

	public void updatePurchaseDetail(int purchase_detail_id, int status) {
		Map param = new HashMap();
		param.put("purchase_detail_id", purchase_detail_id);
		param.put("status", status);
		sqlMapClient.update(NAMESPACE + "updatePurchaseDetailStatus", param);
	}

	public List<TotalService> findService(int parent_id) {
		return (List<TotalService>) sqlMapClient.queryForList(NAMESPACE + "findService", parent_id);
	}

	public Map getSumProperties(int header_id) {
		return (Map) sqlMapClient.queryForObject(NAMESPACE + "getSumProperties", header_id);
	}

	public void removePurchaseDetail(int purchase_detail_id) {
		sqlMapClient.delete(NAMESPACE + "removePurchaseDetail", purchase_detail_id);
	}
}

package com.brilliantreform.sc.product.dao;

import com.brilliantreform.sc.incomming.po.ProductBatchStockBean;
import com.brilliantreform.sc.incomming.po.ProductRealStockBean;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.po.ProductDBDetail;
import com.brilliantreform.sc.product.po.ProductDBHeader;
import com.brilliantreform.sc.product.po.ProductDBTemp;
import com.brilliantreform.sc.stock.po.TotalProduct;
import com.brilliantreform.sc.stock.po.TotalService;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WarehouseDao {
	private static Logger LOGGER = Logger.getLogger(WarehouseDao.class);

    private static final String NAMESPACE = "warehouse.";
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    public List<Map> getTotalServiceTree() {
        return sqlMapClient.queryForList(NAMESPACE + "getTotalServiceTree");
    }

    public List<Map> getTotalProductTree(int community_id) {
        return sqlMapClient.queryForList(NAMESPACE + "getTotalProductTree", community_id);
    }

    public List<Map> getServiceTree(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "getServiceTree", param);
    }

    public List<Map> getProductTree(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "getProductTree", param);
    }

    public Map getTotalProductById(int community_id, int product_id) {
        Map param = new HashMap();
        param.put("product_id", product_id);
        param.put("community_id", community_id);
        return (Map)sqlMapClient.queryForObject(NAMESPACE + "getTotalProductById", param);
    }

    public Map getProductById(int product_id) {
        return (Map) sqlMapClient.queryForObject(NAMESPACE + "getProductById", product_id);
    }

    public void insertProductDBTemp(ProductDBTemp productDBTemp) {
        sqlMapClient.insert(NAMESPACE + "insertProductDBTemp", productDBTemp);
    }

    public void updateProductDBTemp(ProductDBTemp productDBTemp) {
        sqlMapClient.update(NAMESPACE + "updateProductDBTemp", productDBTemp);
    }

    public void deleteUserProductDBTemp(int user_id) {
        sqlMapClient.delete(NAMESPACE + "deleteUserProductDBTemp", user_id);
    }

    public ProductDBTemp getProductDBTempInfo(int community_id, int user_id, int product_id) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("user_id", user_id);
        param.put("product_id", product_id);
        return (ProductDBTemp)sqlMapClient.queryForObject(NAMESPACE + "getProductDBTempInfo", param);
    }

    public List<Map> searchTotalProductByKeyword(int community_id, String keyword) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("keyword", keyword);
        return sqlMapClient.queryForList(NAMESPACE + "searchTotalProductByKeyword", param);
    }

    public ProductRealStockBean getProductRealStock(int community_id, int product_id) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("product_id", product_id);
        return (ProductRealStockBean) sqlMapClient.queryForObject(NAMESPACE + "getProductRealStock", param);
    }

    public void updateProductRealStock(ProductRealStockBean productRealStock) {
        sqlMapClient.update(NAMESPACE + "updateProductRealStock", productRealStock);
    }

    public void insertProductRealStock(ProductRealStockBean productRealStock) {
        sqlMapClient.insert(NAMESPACE + "insertProductRealStock", productRealStock);
    }

    public List<ProductDBTemp> findUserProductDBTemp(int user_id) {
        return sqlMapClient.queryForList(NAMESPACE + "findUserProductDBTemp", user_id);
    }

    public TotalService getTotalServiceById(int service_id) {
        return (TotalService) sqlMapClient.queryForObject(NAMESPACE + "getTotalServiceById", service_id);
    }

    /**
     * 获取某商品库存大于0的所有批次
     * */
    public List<ProductBatchStockBean> findProductBatchStock(int community_id, int product_id) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("product_id", product_id);
        return sqlMapClient.queryForList(NAMESPACE + "findProductBatchStock", param);
    }

    public void insertProductDBHeader(ProductDBHeader header) {
        sqlMapClient.insert(NAMESPACE + "insertProductDBHeader", header);
    }

    public void updateProductDBHeader(ProductDBHeader header) {
        sqlMapClient.update(NAMESPACE + "updateProductDBHeader", header);
    }

    public void insertProductDBDetail(ProductDBDetail detail) {
        sqlMapClient.insert(NAMESPACE + "insertProductDBDetail", detail);
    }

    public void updateProductDBDetail(ProductDBDetail detail) {
        sqlMapClient.update(NAMESPACE + "updateProductDBDetail", detail);
    }

    public List<Integer> findDBTempCommunity(int user_id) {
        return sqlMapClient.queryForList(NAMESPACE + "findDBTempCommunity", user_id);
    }

    public void deleteProductDBTemp(int objid) {
        sqlMapClient.delete(NAMESPACE + "deleteProductDBTemp", objid);
    }

    public double getProductCountInDBTemp(int community_id, int product_id) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("product_id", product_id);
        return CommonUtil.safeToDouble(sqlMapClient.queryForObject(NAMESPACE + "getProductCountInDBTemp", param), 0d);
    }

    public List<Map> searchDBHeader(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchDBHeader", param);
    }

    public int searchDBHeaderCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchDBHeaderCount"), 0);
    }

    public List<Map> searchDBDetail(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchDBDetail", param);
    }

    public int searchDBDetailCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchDBDetailCount"), 0);
    }

    public List<Map> searchTotalDBDetail(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchTotalDBDetail", param);
    }

    public int searchTotalDBDetailCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchTotalDBDetailCount"), 0);
    }

    public ProductDBHeader getDBHeaderById(int objid) {
        return (ProductDBHeader) sqlMapClient.queryForObject(NAMESPACE + "getDBHeaderById", objid);
    }
}

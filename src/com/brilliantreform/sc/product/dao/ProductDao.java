package com.brilliantreform.sc.product.dao;

import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.po.ProductRule;
import com.brilliantreform.sc.product.po.ProductSearchBean;

@Repository("productDao")
public class ProductDao {
	private static Logger logger = Logger.getLogger(ProductDao.class);

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(ProductSearchBean productSearchBean){
		
		return (List<Product>)sqlMapClient.queryForList("product.getProductList",productSearchBean);
	}
	
	public Integer countProductList(ProductSearchBean productSearchBean){
		
		return (Integer)sqlMapClient.queryForObject("product.countProductList",productSearchBean);
	}

    public List<Product> searchProduct(Map param) {

        return (List<Product>)sqlMapClient.queryForList("product.searchProduct",param);
    }

    public int searchProductCount(Map param){
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("product.searchProductCount", param), 0);
    }
	
	public Product getProduct(Integer pid){
		return (Product)sqlMapClient.queryForObject("product.getProduct",pid);
	}
	
	public void updateProduct(Product product){
		sqlMapClient.update("product.updateProduct",product);
	}
	
	public Integer insertProduct(Product product){
		return (Integer)sqlMapClient.insert("product.insertProduct",product);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductRule> getRuleList(Integer cid){
		
		return (List<ProductRule>)sqlMapClient.queryForList("product.getRuleList",cid);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductRule> getRuleListS(ProductSearchBean productSearchBean){
		
		return (List<ProductRule>)sqlMapClient.queryForList("product.getRuleListS",productSearchBean);
	}
	
	public Integer countRuleListS(ProductSearchBean productSearchBean){
		
		return (Integer)sqlMapClient.queryForObject("product.countRuleListS",productSearchBean);
	}
	
	public ProductRule getRule(Integer pid){
		return (ProductRule)sqlMapClient.queryForObject("product.getRule",pid);
	}
	
	public void updateRule(ProductRule rule){
		sqlMapClient.update("product.updateRule",rule);
	}
	
	public Integer insertRule(ProductRule rule){
		return (Integer)sqlMapClient.insert("product.insertRule", rule);
	}
	
	public void backupProduct(Integer pid){
		sqlMapClient.insert("product.backupProduct",pid);
	}
	
	public void delProduct(Integer pid){
		sqlMapClient.delete("product.delProduct",pid);
	}
	
	public void updateProducts(Map map){
		sqlMapClient.update("product.updateProducts",map);
	}
	
	public Product getProductByBarcode(Map map){
		return (Product)sqlMapClient.queryForObject("product.getProductByBarcode",map);
	}
	
	public void updateAmount(Map map){
		sqlMapClient.update("product.updateAmount",map);
	}
	/**
	 * 更新营销类型 
	 * @param tags
	 */
	public void service_product_tagsUp(Map map){
		sqlMapClient.update("product.service_product_tagsUp", map);
	}

    public void insertProductV4(Product product) {
        sqlMapClient.insert("product.insertProductV4", product);
    }

    public void updateProductV4(Product product) {
        sqlMapClient.update("product.updateProductV4", product);
    }

    public void saveProduct(Product product) {
        if(product != null) {
            if(product.getProduct_id() != null) {
                updateProductV4(product);
            } else {
                insertProductV4(product);
            }
        }
    }

    public List<Map> searchRule(Map param) {
        return sqlMapClient.queryForList("product.searchRule", param);
    }

    public int searchRuleCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("product.searchRuleCount", param), 0);
    }

    public void saveRule(ProductRule rule) {
        if(rule != null) {
            if(rule.getRule_id() == null) {
                insertRule(rule);
            } else {
                updateRule(rule);
            }
        }
    }
}

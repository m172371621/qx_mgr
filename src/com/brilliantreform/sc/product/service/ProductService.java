package com.brilliantreform.sc.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.product.dao.ProductDao;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.po.ProductRule;
import com.brilliantreform.sc.product.po.ProductSearchBean;

@Service("productService")
public class ProductService {
	private static Logger logger = Logger.getLogger(ProductService.class);

	@Autowired
	private ProductDao  productDao;
	

	public List<Product> getProductList(ProductSearchBean productSearchBean){
		logger.info("into ProductService getProductList:"+productSearchBean);
		List<Product> list = null;
		list = productDao.getProductList(productSearchBean);
		logger.info("ProductService getProductList:"+list);
		return list;
	}
	

	public Integer countProductList(ProductSearchBean productSearchBean){
		logger.info("into ProductService countProductList:"+productSearchBean);
			return productDao.countProductList(productSearchBean);
	}
	
	public Product getProduct(Integer pid){
		logger.info("into ProductService getProduct:"+pid);
		Product p =  productDao.getProduct(pid);
		logger.info("end getProduct:"+p);
		return p;
	}
	
	public void updateProduct(Product product){
		logger.info("into ProductService updateProduct:"+product);
		productDao.updateProduct(product);
		logger.info("end updateProduct");
		
	}
	
	public List<ProductRule> getRuleList(Integer cid){
		logger.info("into ProductService getRuleList:"+cid);
		return productDao.getRuleList(cid);
	}
	
	public Integer insertProduct(Product product){
		logger.info("into ProductService insertProduct:"+product);
		int id = productDao.insertProduct(product);
		logger.info("end insertProduct:"+id);
		return id;
	}	
	

	public List<ProductRule> getRuleListS(ProductSearchBean productSearchBean){
		logger.info("into ProductService getRuleListS:"+productSearchBean);
		return productDao.getRuleListS(productSearchBean);
	}
	
	public Integer countRuleListS(ProductSearchBean productSearchBean){
		logger.info("into ProductService countRuleListS:"+productSearchBean);
		return productDao.countRuleListS(productSearchBean);
	}
	
	public ProductRule getRule(Integer pid){
		logger.info("into ProductService getRule:"+pid);
		return productDao.getRule(pid);
	}
	
	public void updateRule(ProductRule rule){
		logger.info("into ProductService updateRule:"+rule);
		productDao.updateRule(rule);
	}
	
	public Integer insertRule(ProductRule rule){
		logger.info("into ProductService insertRule:"+rule);
		return productDao.insertRule(rule);
	}
	
	public void delProduct(Integer pid){
		//productDao.backupProduct(pid);
		productDao.delProduct(pid);
	}
		
	
	@SuppressWarnings("unchecked")
	public void updateProducts(Integer pid,Integer sid_from,Integer sid_to,Integer status){
		
		if(sid_from == null && pid == null)
			return;
			
		Map map = new HashMap();
		map.put("status",status );
		map.put("sid_to", sid_to);
		map.put("pid", pid);
		map.put("sid_from", sid_from);
				
	
		productDao.updateProducts(map);
	}
	
	@SuppressWarnings("unchecked")
	public Product getProductByBarcode(String barcode,Integer cid){
		Map map = new HashMap();
		map.put("barcode",barcode );
		map.put("cid", cid);
		return productDao.getProductByBarcode(map);
	}
	/**
	 * 更新营销类型 tags
	 * @param tags
	 */
	public void service_product_tagsUp(Map map){
		productDao.service_product_tagsUp(map);
	}

    public List<Product> searchProduct(Map param) {
        return productDao.searchProduct(param);
    }

    public int searchProductCount(Map param){
        return productDao.searchProductCount(param);
    }

    public void saveProduct(Product product) {
        productDao.saveProduct(product);
    }

    public List<Map> searchRule(Map param) {
        return productDao.searchRule(param);
    }

    public int searchRuleCount(Map param) {
        return productDao.searchRuleCount(param);
    }

    public void saveRule(ProductRule rule) {
        productDao.saveRule(rule);
    }
}

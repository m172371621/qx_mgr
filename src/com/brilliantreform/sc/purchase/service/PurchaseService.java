package com.brilliantreform.sc.purchase.service;

import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.purchase.dao.PurchaseDao;
import com.brilliantreform.sc.purchase.enumerate.PurchaseStatus;
import com.brilliantreform.sc.purchase.po.PurchaseCar;
import com.brilliantreform.sc.purchase.po.PurchaseDetail;
import com.brilliantreform.sc.purchase.po.PurchaseHeader;
import com.brilliantreform.sc.purchase.po.SupplierInfo;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.stock.dao.StockDao;
import com.brilliantreform.sc.stock.po.TotalProduct;
import com.brilliantreform.sc.stock.po.TotalService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.MathUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PurchaseService {
	
	@Autowired
	private PurchaseDao purchaseDao;
	
	@Autowired
	private StockDao stockDao;

	public List<Map> findcartInfo(int user_id) {
		return purchaseDao.findCartInfo(user_id);
	}

	// 删除购物车中商品
	public void delCartInfo(int cart_id) {
		purchaseDao.delCartInfo(cart_id);
	}

	public List<Map> findPurchaseDetailByHeaderId(int header_id) {
		return purchaseDao.findPurchaseDetailByHeaderId(header_id);
	}

	public Map getPurchaseHeaderById(int header_id) {
		return purchaseDao.getPurchaseHeaderById(header_id);
	}
	
	//查询待审核的进货汇总单总数
	public Integer getPurchaseHeaderCount(Map<String, Object> map) {
		return purchaseDao.getPurchaseHeaderCount(map);
	}
	// 查询进货汇总单
	public List<Map> getPurchaseHeader(Map<String, Object> map) {
		return purchaseDao.getPurchaseHeader(map);
	}


	// 审核进货单
	public void auditPurchase(Map<String, Object> map) {
		purchaseDao.auditPurchase(map);
	}

	public List<TotalService> findService(int parent_id) {
		return purchaseDao.findService(parent_id);
	}

	public List<TotalProduct> searchProduct(Map param) {
		return purchaseDao.searchProduct(param);
	}

	public int searchProductCount(Map param) {
		return purchaseDao.searchProductCount(param);
	}

	public TotalService getServiceById(Integer service_id) {
		return purchaseDao.getServiceById(service_id);
	}

	public TotalProduct getProductById(int product_id) {
		return purchaseDao.getProductById(product_id);
	}

	/**
	 * 增加商品到购物车，如果购物车中存在，则增加相应的数量
	 * @param product_id 商品id
	 * @param amount 商品数量
	 * @param MgrUser 当前用户
	 * */
	public PurchaseCar addCartInfo(int product_id, double amount, MgrUser user) {
		//获取该商品信息
		TotalProduct product = purchaseDao.getProductById(product_id);
		if(product != null) {
			//购物车中是否存在该商品
			PurchaseCar purchaseCar = purchaseDao.getPurchaseCartByProduct(user.getUser_id(), product_id);
			if(purchaseCar != null) {
				//购物车中存在该商品，数量加1
				purchaseCar.setProduct_amount(purchaseCar.getProduct_amount() + amount);
			} else {
				//不存在，创建购物车
				purchaseCar = new PurchaseCar();
				purchaseCar.setCreateby(user.getUser_id());
				purchaseCar.setProduct_id(product_id);
				purchaseCar.setProduct_name(product.getName());
				purchaseCar.setProduct_amount(amount);
				purchaseCar.setProduct_price(product.getPrice());
				purchaseCar.setCreateTime(new Date());
				purchaseCar.setCreatebycid(user.getCid());
			}
			purchaseDao.savePurchaseCar(purchaseCar);
			return purchaseCar;
		}
		return null;
	}

	public int getCartCount(int user_id) {
		return purchaseDao.getCartCount(user_id);
	}

	/**
	 * 提交购物车，生成待审核的订货单，并清空购物车
	 * */
	public void submitCart(int community_id, MgrUser user, String[] product_id_array, String[] product_amount_array) {
		//进货单汇总信息,此处未设置总额，待后面保存明细之后在进行设置
		PurchaseHeader header = new PurchaseHeader();
		String purchase_header_no = CommonUtil.formatDate(new Date(), "yyyyMMdd") + RandomStringUtils.randomNumeric(10);	//进货单号
		header.setPurchase_header_no(purchase_header_no);
		header.setCreatebycid(community_id);
		header.setCreateby(user.getUser_id());
		header.setCreateTime(new Date());
		purchaseDao.savePurchaseHeader(header);

		double purchase_price = 0d;
		//生成进货单明细
		for(int i = 0; i < product_id_array.length; i++) {
			Integer product_id = CommonUtil.safeToInteger(product_id_array[i], null);
			Double product_amount = CommonUtil.safeToDouble(product_amount_array[i], null);

			if(product_id != null && product_amount != null) {
				TotalProduct product = purchaseDao.getProductById(product_id);
				if(product != null) {
					double product_price = MathUtil.mul(product.getPrice(), product_amount);
					PurchaseDetail detail = new PurchaseDetail();
					detail.setPurchase_header_id(header.getPurchase_header_id());
					detail.setProduct_id(product_id);
					detail.setProduct_name(product.getName());
					detail.setProduct_amount(product_amount);
					detail.setProduct_real_amount(product_amount);
					detail.setProduct_price(product.getPrice());
					detail.setProduct_real_price(product.getPrice());
					detail.setStatus(PurchaseStatus.INIT.getValue());	//初始状态
					purchaseDao.savePurchaseDetail(detail);

					purchase_price += product_price;
				}
			}
		}

		header.setPurchase_price(purchase_price);
		header.setPurchase_real_price(purchase_price);
		purchaseDao.savePurchaseHeader(header);

		//清空购物车
		purchaseDao.removePurchaseCart(user.getUser_id());
	}

	public List<SupplierInfo> findSupplierByProductId(int product_id) {
		return purchaseDao.findSupplierByProductId(product_id);
	}

	public List<TotalProduct> findProductByKeyword(String keyword, Integer header_id) {
		return purchaseDao.findProductByKeyword(keyword, header_id);
	}

	/**
	 * 根据总库商品生成小区的商品，如已经存在，则生成，不存在，则还要把不存在的分类递归生成
	 * */
	public Integer createCommunityProduct(int community_id, int total_product_id) {
		Product product = purchaseDao.getChildProByTotalProId(community_id, total_product_id);
		if(product == null) {
			TotalProduct totalProduct = purchaseDao.getProductById(total_product_id);
			//获取该总库商品的所有总库分类
			List<TotalService> serviceList = new ArrayList<TotalService>();
			findServiceLink(totalProduct.getService_id(), serviceList);
			//serviceList倒序
			Collections.reverse(serviceList);

			//遍历所有类别，将分库中不存在的类别也创建
			for(TotalService totalService : serviceList) {
				ServiceVo service = purchaseDao.getChildServiceByTotalServiceId(community_id, totalService.getService_id());
				if(service == null) {
					Map m = new HashMap();
					m.put("cid", community_id);
					m.put("service_id", totalService.getService_id());
					stockDao.insertService_base(m);
				}
			}

			//创建分库商品
			Map m = new HashMap();
			m.put("cid", community_id);
			m.put("service_id", totalProduct.getService_id());
			m.put("i_product_id", total_product_id);
			return stockDao.insertService_product(m);
        }
        return product.getProduct_id();
	}

	/**
	 * 获取total_service_id的所有父类别，包括本身
	 * */
	private void findServiceLink(int total_service_id, List<TotalService> list) {
		TotalService totalService = purchaseDao.getServiceById(total_service_id);
		if(totalService != null) {
			list.add(totalService);
			if(total_service_id != totalService.getParent_id().intValue()) {
				findServiceLink(totalService.getParent_id(), list);
			}
		}
	}

	public Map getPurchaseDetailById(int purchase_detail_id) {
		return purchaseDao.getPurchaseDetailById(purchase_detail_id);
	}

	public void updatePurchaseDetail(int purchase_detail_id, int status) {
		purchaseDao.updatePurchaseDetail(purchase_detail_id, status);
	}

	public void removePurchaseDetail(int purchase_detail_id) {
		purchaseDao.removePurchaseDetail(purchase_detail_id);
	}

	public PurchaseHeader getHeaderById(int header_id) {
		return purchaseDao.getHeaderById(header_id);
	}

    public PurchaseDetail savePurchaseDetail(PurchaseDetail detail) {
        return purchaseDao.savePurchaseDetail(detail);
    }

    public PurchaseDetail getPurchaseDetail(int purchase_detail_id) {
        return purchaseDao.getPurchaseDetail(purchase_detail_id);
    }

	/**
	 * 同步预计总价和实际总价
	 * */
	public void syncPurchaseHeader(PurchaseHeader header) {
		Map sum_map = purchaseDao.getSumProperties(header.getPurchase_header_id());
		Double product_price_sum = CommonUtil.safeToDouble(sum_map.get("product_price_sum"), null);
		Double product_real_price_sum = CommonUtil.safeToDouble(sum_map.get("product_real_price_sum"), null);
		header.setPurchase_price(product_price_sum);
		header.setPurchase_real_price(product_real_price_sum);
		purchaseDao.savePurchaseHeader(header);
	}

	/**
	 * 发货，如参数detail_id为空，则需要将该商品添加到订货单中并完成发货
	 * */
	public boolean sendGoods(Integer detail_id, int header_id, int product_id, double product_price, double product_amount, int supplier_id) {
		boolean result = false;
		PurchaseDetail detail = null;
		PurchaseHeader header = purchaseDao.getHeaderById(header_id);
		if(header != null) {
			if (detail_id == null) {
				TotalProduct product = purchaseDao.getProductById(product_id);
				if (product != null) {
					detail = new PurchaseDetail();
					detail.setPurchase_header_id(header.getPurchase_header_id());
					detail.setProduct_id(product_id);
					detail.setProduct_name(product.getName());
					detail.setProduct_amount(product_amount);
					detail.setProduct_real_amount(product_amount);
					detail.setStatus(PurchaseStatus.INIT.getValue());
					detail.setProduct_price(product.getPrice());
					detail.setProduct_real_price(product_price);
					detail.setSupplier_id(supplier_id);
					detail.setRemark("手动添加");
					purchaseDao.savePurchaseDetail(detail);
				}
			} else {
				detail = purchaseDao.getPurchaseDetail(detail_id);
				detail.setProduct_real_price(product_price);
				detail.setProduct_real_amount(product_amount);
				detail.setSupplier_id(supplier_id);
				purchaseDao.savePurchaseDetail(detail);
			}

			if(detail != null) {
				//开始进行发货操作，即改status以及更新header中的预计以及实际的价格数量
				detail.setStatus(PurchaseStatus.SENDED.getValue());
				purchaseDao.savePurchaseDetail(detail);

				syncPurchaseHeader(header);
				result = true;
			}
		}
		return result;
	}
}

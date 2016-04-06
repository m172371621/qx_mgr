package com.brilliantreform.sc.purchase.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.incomming.service.IncommingOrderService;
import com.brilliantreform.sc.purchase.enumerate.PurchaseStatus;
import com.brilliantreform.sc.purchase.po.PurchaseCar;
import com.brilliantreform.sc.purchase.po.PurchaseDetail;
import com.brilliantreform.sc.purchase.po.PurchaseHeader;
import com.brilliantreform.sc.purchase.po.SupplierInfo;
import com.brilliantreform.sc.purchase.service.PurchaseService;
import com.brilliantreform.sc.service.service.SevService;
import com.brilliantreform.sc.stock.po.TotalProduct;
import com.brilliantreform.sc.stock.po.TotalService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.JsonUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("purchase.do")
public class PurchaseCtrl {

	private static Logger logger = Logger.getLogger(PurchaseCtrl.class);

	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private IncommingOrderService incommingOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private SevService sevService;


	/**添加商品到购物车，如果已存在商品则修改购物车该商品的数量
	 * @param request product_id
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=addCartInfo")
	public void addCartInfo(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "addCartInfo");
		try {
			Integer product_id = CommonUtil.safeToInteger(request.getParameter("product_id"), null);
			if(product_id != null) {
				MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
				PurchaseCar purchaseCar = purchaseService.addCartInfo(product_id, 1d, user);
				if(purchaseCar != null) {
					//获取购物车数量
					int count = purchaseService.getCartCount(user.getUser_id());
					CommonUtil.outToWeb(response, count + "");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**删除购物车中商品
	 * @param request product_id
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=delCartInfo", method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String delCartInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");

		LogerUtil.logRequest(request, logger, "delCartInfo");
		Integer result_code = 0;
		String result_dec = "OK";
		String result_str = JsonUtil.result2Json(false, null);

		try {
			String ids = CommonUtil.safeToString(request.getParameter("ids"), null);
			if(StringUtils.isNotBlank(ids)) {
				String[] id = ids.split(",");
				for(String c_id : id) {
					Integer cart_id = CommonUtil.safeToInteger(c_id, null);
					if(cart_id != null) {
						purchaseService.delCartInfo(cart_id);
					}
				}
				result_str = JsonUtil.result2Json(result_code, result_dec, null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result_str;

	}
	
	/**查询进货汇总单 总部查看
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getPurchaseHeader")
	public void getPurchaseHeader(String dtGridPager,HttpServletRequest request,
			HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, logger, "getPurchaseHeader");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

			//处理结束时间
			Date endtime_d = CommonUtil.safeToDate(param.get("endtime"), "yyyy-MM-dd");
			if(endtime_d != null) {
				param.put("endtime", CommonUtil.formatDate(DateUtils.addDays(endtime_d, 1), "yyyy-MM-dd"));
			}

			List<Map> list= purchaseService.getPurchaseHeader(param);
			int count = purchaseService.getPurchaseHeaderCount(param);
			pager = GridUtil.setPagerResult(pager, list, count);
		} catch (Exception e) {
			logger.error("getPurchaseHeader error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}
	
	/**查询进货汇总单总数总部查看
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getPurchaseHeaderCount", method = {
			RequestMethod.POST, RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getPurchaseHeaderCount(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");

		LogerUtil.logRequest(request, logger, "getPurchaseHeaderCount");
		Integer result_code = 0;
		String result_dec = "OK";
		String result_str = JsonUtil.result2Json(false, null);

		try {
			List<Community> community_list =  (List<Community>)request.getSession().getAttribute("user_community");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("begintime", request.getParameter("begintime"));
			map.put("endtime", request.getParameter("endtime"));
			map.put("community_list", community_list);
			Integer count= purchaseService.getPurchaseHeaderCount(map);
			result_str = JsonUtil.result2Json(result_code, result_dec, count);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result_str;

	}
	
	@RequestMapping(params = "method=showPurchasePage")
	public String showPurchasePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "showPurchasePage");

			Integer service_id = CommonUtil.safeToInteger(request.getParameter("service_id"), 0);
			List<TotalService> serviceList = purchaseService.findService(service_id);

			if(service_id != null && service_id != 0) {
				TotalService service = purchaseService.getServiceById(service_id);
				request.setAttribute("service", service);
				//获取上级分类
				if(service.getParent_id() != null && service.getParent_id() != 0) {
					TotalService parent_service = purchaseService.getServiceById(service.getParent_id());
					request.setAttribute("parent_service", parent_service);
				}
			}

			//获取该分类下的商品数量
			Map param = new HashMap();
			if(service_id != null && service_id != 0) {
				param.put("service_id", service_id);
			}
			int product_count = purchaseService.searchProductCount(param);
			//获取购物车数量
			MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
			int cart_count = purchaseService.getCartCount(user.getUser_id());

			request.setAttribute("serviceList", serviceList);
			request.setAttribute("product_count", product_count);
			request.setAttribute("cart_count", cart_count);
			request.setAttribute("colnum", 3);	//每行显示商品数量
			request.setAttribute("rownum", 5);	//每页显示商品行数
		} catch (Exception e) {
			logger.error("showPurchasePage error", e);
		}
		return "/new/jsp/purchase/purchase";
	}

	@RequestMapping(params = "method=listProduct")
	public void listProduct(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, logger, "listProduct");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);

			List<TotalProduct> proList = purchaseService.searchProduct(param);
			int count = purchaseService.searchProductCount(param);

			//处理商品列表以供grid解析
			List<Map> list = new ArrayList<Map>();
			int colnum = CommonUtil.safeToInteger(param.get("colnum"), 0);
			List<List<TotalProduct>> _list = CommonUtil.splitList(proList, colnum);

			for(List<TotalProduct> plist : _list) {
				Map m = new HashMap();
				for(int i = 0; i < plist.size(); i++) {
					m.put("pro_" + i, plist.get(i));
				}
				if(m.size() > 0) {
					list.add(m);
				}
			}

			pager = GridUtil.setPagerResult(pager, list, count);

		} catch (Exception e) {
			logger.error("listProduct error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

	@RequestMapping(params = "method=listCart")
	public String listCart(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "listCart");

			MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
			//获取购物车信息
			List<Map> cartList = purchaseService.findcartInfo(user.getUser_id());

			request.setAttribute("cartList", cartList);
		} catch (Exception e) {
			logger.error("listCart error", e);
		}
		return "/new/jsp/purchase/cart";
	}

	/**
	 * 获取购物车中商品的总数量
	 * */
	@RequestMapping(params = "method=getCartCount")
	public void getCartCount(HttpServletRequest request, HttpServletResponse response) {
		int count = 0;
		try {
			LogerUtil.logRequest(request, logger, "getCartCount");

			MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
			count = purchaseService.getCartCount(user.getUser_id());
		} catch (Exception e) {
			logger.error("getCardCount error", e);
		}
		CommonUtil.outToWeb(response, count + "");
	}

    /**
     * 不显示任何按钮
     * */
    @RequestMapping(params = "method=showListPurchasePage")
    public String showListPurchasePage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/purchase/listPurchase";
    }

    /**
     * 显示所有按钮
     * */
	@RequestMapping(params = "method=showListPurchaseAdminPage")
	public String showListPurchaseAdminPage(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("who", "admin");
        return "/new/jsp/purchase/listPurchase";
	}

    /**
     * 供货商，只显示发货按钮
     * */
    @RequestMapping(params = "method=showListPurchaseSellPage")
    public String showListPurchaseSellPage(HttpServletRequest request,HttpServletResponse response) {
        request.setAttribute("who", "sell");
        return "/new/jsp/purchase/listPurchase";
    }

    /**
     * 采购商，只显示确认到货按钮
     * */
    @RequestMapping(params = "method=showListPurchaseBuyPage")
    public String showListPurchaseBuyPage(HttpServletRequest request,HttpServletResponse response) {
        request.setAttribute("who", "buy");
        return "/new/jsp/purchase/listPurchase";
    }

	@RequestMapping(params = "method=submitCart")
	public void submitCart(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "submitCart");

			Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
			String product_ids = CommonUtil.safeToString(request.getParameter("product_id"), null);
			String product_amounts = CommonUtil.safeToString(request.getParameter("product_amount"), null);

			if(StringUtils.isNotBlank(product_ids) && StringUtils.isNotBlank(product_amounts) && community_id != null) {
				String[] product_id_array = product_ids.split(",");
				String[] product_amount_array = product_amounts.split(",");

				if(ArrayUtils.isNotEmpty(product_id_array) && ArrayUtils.isNotEmpty(product_amount_array)) {
					MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
					purchaseService.submitCart(community_id, user, product_id_array, product_amount_array);
					CommonUtil.outToWeb(response, "success");
					return;
				}
			}

		} catch (Exception e) {
			logger.error("submitCart error", e);
		}
		CommonUtil.outToWeb(response, "fail");
	}

	@RequestMapping(params = "method=showEditPurchasePage")
	public String showEditPurchasePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "showEditPurchasePage");

			Integer header_id = CommonUtil.safeToInteger(request.getParameter("header_id"), null);

			if(header_id != null) {
				Map header = purchaseService.getPurchaseHeaderById(header_id);
				List<Map> detailList = purchaseService.findPurchaseDetailByHeaderId(header_id);

				request.setAttribute("header", header);
				request.setAttribute("detailList", detailList);
			}
		} catch (Exception e) {
			logger.error("showEditPurchasePage error", e);
		}
		return "/new/jsp/purchase/editPurchase";
	}

	@RequestMapping(params = "method=showConfirmPurchasePage")
	public String showConfirmPurchasePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "showEditPurchasePage");

			Integer header_id = CommonUtil.safeToInteger(request.getParameter("header_id"), null);

			if(header_id != null) {
				Map header = purchaseService.getPurchaseHeaderById(header_id);
				List<Map> detailList = purchaseService.findPurchaseDetailByHeaderId(header_id);

				Set<Integer> supplierIds = new HashSet<Integer>();	//供应商id集合
				List<Map> noSupplierDetailList = new ArrayList<Map>();	//没有供应商的商品
				for(Map detail : detailList) {
					Integer supplier_id = CommonUtil.safeToInteger(detail.get("supplier_id"), null);
					if(supplier_id != null) {
						supplierIds.add(supplier_id);
					} else {
						noSupplierDetailList.add(detail);
					}
				}

				List<List<Map>> list = new ArrayList<List<Map>>();
				for(Integer supplier_id : supplierIds) {
					List<Map> mlist = new ArrayList<Map>();
					for(Map detail : detailList) {
						Integer sid = CommonUtil.safeToInteger(detail.get("supplier_id"), null);
						if(sid != null && supplier_id == sid) {
							mlist.add(detail);
						}
					}
					list.add(mlist);
				}
				list.add(noSupplierDetailList);

				request.setAttribute("header", header);
				request.setAttribute("detailList", list);
			}
		} catch (Exception e) {
			logger.error("showConfirmPurchasePage error", e);
		}
		return "/new/jsp/purchase/confirmPurchase";
	}

	@RequestMapping(params = "method=findProductByKeyword")
	public void findProductByKeyword(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "findProductByKeyword");

			Integer header_id = CommonUtil.safeToInteger(request.getParameter("header_id"), null);
			String keyword = CommonUtil.safeToString(request.getParameter("keyword"), null);

			if(StringUtils.isNotBlank(keyword)) {
				keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
				List<TotalProduct> list = purchaseService.findProductByKeyword(keyword, header_id);

				Map map = new HashMap();
				map.put("value", list);
				CommonUtil.outToWeb(response, JSONObject.fromObject(map).toString());
			}

		} catch (Exception e) {
			logger.error("findProductByKeyword error", e);
		}
	}

	@RequestMapping(params = "method=getProductById")
	public void getProductById(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "getProductById");

			Integer product_id = CommonUtil.safeToInteger(request.getParameter("product_id"), null);

			if(product_id != null) {
				TotalProduct product = purchaseService.getProductById(product_id);
                List<SupplierInfo> supplierList = purchaseService.findSupplierByProductId(product_id);

                Map map = new HashMap();
                map.put("product", product);
                map.put("supplierList", supplierList);
				CommonUtil.outToWeb(response, JSONObject.fromObject(map).toString());
			}

		} catch (Exception e) {
			logger.error("getProductById error", e);
		}
	}

	@RequestMapping(params = "method=goodsArrive")
	public void goodsArrive(HttpServletRequest request, HttpServletResponse response) {
		String result = "fail";
		try {
			LogerUtil.logRequest(request, logger, "goodsArrive");

			Integer purchase_detail_id = CommonUtil.safeToInteger(request.getParameter("purchase_detail_id"), null);

			if(purchase_detail_id != null) {
				Map detail = purchaseService.getPurchaseDetailById(purchase_detail_id);

				Integer community_id = CommonUtil.safeToInteger(detail.get("createbycid"), null);
				Integer product_id = CommonUtil.safeToInteger(detail.get("product_id"), null);
				Double product_amount = CommonUtil.safeToDouble(detail.get("product_real_amount"), null);
				Double product_price = CommonUtil.safeToDouble(detail.get("product_real_price"), null);
				if(community_id != null && product_id != null && product_amount != null && product_price != null) {
					//首先判断该小区是否存在该商品，如不存在，则创建
					Integer pro_id = purchaseService.createCommunityProduct(CommonUtil.safeToInteger(detail.get("createbycid"), null), product_id);
                    if(pro_id != null) {
                        MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
                        Community community = (Community) request.getSession().getAttribute("selectCommunity");
                        String ip = CommonUtil.getIp(request);
                        //进货
                        incommingOrderService.insertIncommingDetail(1, community.getCommunity_id(), user, ip, pro_id, product_amount, product_price);

                        //更新状态
                        purchaseService.updatePurchaseDetail(purchase_detail_id, PurchaseStatus.CONFIRMED.getValue());
                        result = "success";
                    }
				}
			}
		} catch (Exception e) {
			logger.error("goodsArrive error", e);
		}
		CommonUtil.outToWeb(response, result);
	}

	@RequestMapping(params = "method=findSupplierByProduct")
	public void findSupplierByProduct(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "findSupplierByProduct");

			Integer product_id = CommonUtil.safeToInteger(request.getParameter("product_id"), null);

			if(product_id != null) {
				List<SupplierInfo> list = purchaseService.findSupplierByProductId(product_id);
				if(CollectionUtils.isNotEmpty(list)) {
					CommonUtil.outToWeb(response, JSONArray.fromObject(list).toString());
				}
			}
		} catch (Exception e) {
			logger.error("findSupplierByProduct error", e);
		}
	}

	@RequestMapping(params = "method=updateDetailStatus")
	public void updateDetailStatus(HttpServletRequest request, HttpServletResponse response) {
		String result = "fail";
		try {
			LogerUtil.logRequest(request, logger, "updateDetailStatus");

			Integer detail_id = CommonUtil.safeToInteger(request.getParameter("detail_id"), null);
			Integer status = CommonUtil.safeToInteger(request.getParameter("status"), null);

			if(detail_id != null && status != null) {
				purchaseService.updatePurchaseDetail(detail_id, status);
				result = "success";
			}
		} catch (Exception e) {
			logger.error("updateDetailStatus error", e);
		}
		CommonUtil.outToWeb(response, result);
	}

	/**
	 * 发货，如参数detail_id为空，则需要将该商品添加到订货单中并完成发货
	 * */
	@RequestMapping(params = "method=sendGoods")
	public void sendGoods(HttpServletRequest request, HttpServletResponse response) {
		String result = "fail";
		try {
			LogerUtil.logRequest(request, logger, "updateDetailStatus");

			Integer detail_id = CommonUtil.safeToInteger(request.getParameter("detail_id"), null);
			Integer header_id = CommonUtil.safeToInteger(request.getParameter("header_id"), null);
			Integer product_id = CommonUtil.safeToInteger(request.getParameter("product_id"), null);
			Double product_price = CommonUtil.safeToDouble(request.getParameter("product_price"), null);
			Double product_amount = CommonUtil.safeToDouble(request.getParameter("product_amount"), null);
			Integer supplier_id = CommonUtil.safeToInteger(request.getParameter("supplier_id"), null);

			if(header_id != null && product_id != null && product_price != null && product_amount != null && supplier_id != null) {
				if(purchaseService.sendGoods(detail_id, header_id, product_id, product_price, product_amount, supplier_id)) {
                    result = "success";
                }
			}
		} catch (Exception e) {
			logger.error("updateDetailStatus error", e);
		}
		CommonUtil.outToWeb(response, result);
	}

    @RequestMapping(params = "method=removeDetail")
    public void removeDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "removeDetail");

            Integer detail_id = CommonUtil.safeToInteger(request.getParameter("detail_id"), null);
            Integer header_id = CommonUtil.safeToInteger(request.getParameter("header_id"), null);
            if(detail_id != null && header_id != null) {
                purchaseService.removePurchaseDetail(detail_id);
                PurchaseHeader header = purchaseService.getHeaderById(header_id);
                purchaseService.syncPurchaseHeader(header);
            }
        } catch (Exception e) {
            logger.error("removeDetail error", e);
        }
    }

    @RequestMapping(params = "method=saveDetail")
    public void saveDetail(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            LogerUtil.logRequest(request, logger, "saveDetail");

            Integer header_id = CommonUtil.safeToInteger(request.getParameter("header_id"), null);
            String details = request.getParameter("details");
            Integer remove_id = CommonUtil.safeToInteger(request.getParameter("remove_id"), null);  //需要删除的detail_id

            if(header_id != null && StringUtils.isNotBlank(details)) {
                PurchaseHeader header = purchaseService.getHeaderById(header_id);
                if(header != null) {
                    String[] details_array = details.split("\\|");
                    for (String detail_str : details_array) {
                        String[] detail_str_array = detail_str.split(",");
                        if (ArrayUtils.isNotEmpty(detail_str_array)) {
                            Integer detail_id = CommonUtil.safeToInteger(detail_str_array[0], null);
                            Integer product_id = CommonUtil.safeToInteger(detail_str_array[1], null);
                            Double product_price = CommonUtil.safeToDouble(detail_str_array[2], null);
                            Double product_amount = CommonUtil.safeToDouble(detail_str_array[3], null);
                            Integer supplier_id = CommonUtil.safeToInteger(detail_str_array[4], null);

                            if (product_id != null && product_price != null && product_amount != null && supplier_id != null) {
                                PurchaseDetail detail = null;
                                if (detail_id == null) {
                                    TotalProduct product = purchaseService.getProductById(product_id);
                                    if (product != null) {
                                        detail = new PurchaseDetail();
                                        detail.setPurchase_header_id(header_id);
                                        detail.setProduct_id(product_id);
                                        detail.setProduct_name(product.getName());
                                        detail.setProduct_amount(product_amount);
                                        detail.setProduct_real_amount(product_amount);
                                        detail.setStatus(PurchaseStatus.INIT.getValue());
                                        detail.setProduct_price(product.getPrice());
                                        detail.setProduct_real_price(product_price);
                                        detail.setSupplier_id(supplier_id);
                                        detail.setRemark("手动添加");
                                        purchaseService.savePurchaseDetail(detail);
                                    }
                                } else {
                                    detail = purchaseService.getPurchaseDetail(detail_id);
                                    detail.setProduct_real_price(product_price);
                                    detail.setProduct_real_amount(product_amount);
                                    detail.setSupplier_id(supplier_id);
                                    purchaseService.savePurchaseDetail(detail);
                                }
                            }

                        }
                    }
                    //删除需要删除的数据
                    if(remove_id != null) {
                        purchaseService.removePurchaseDetail(remove_id);
                    }
                    //重新计算总价
                    purchaseService.syncPurchaseHeader(header);
                    result = "ok";
                }

            }

        } catch (Exception e) {
            logger.error("saveDetail error", e);
        }
        CommonUtil.outToWeb(response, result);
    }

}

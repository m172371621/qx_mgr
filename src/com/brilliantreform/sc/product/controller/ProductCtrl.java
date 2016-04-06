package com.brilliantreform.sc.product.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.po.ProductRule;
import com.brilliantreform.sc.product.po.ProductSearchBean;
import com.brilliantreform.sc.product.po.ProductTags;
import com.brilliantreform.sc.product.service.ProductService;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.service.service.SevService;
import com.brilliantreform.sc.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("product.do")
public class ProductCtrl {

	private static Logger logger = Logger.getLogger(ProductCtrl.class);

	private static int size = 20;

	//private static String pic_host = "http://test.qxit.com.cn/scframe";
	//private static String pic_path = "/upload/product/";

	@Autowired
	private ProductService productService;

	@Autowired
	private SevService sevService;

	@RequestMapping(params = "method=list", method = { RequestMethod.POST, RequestMethod.GET })
	public String searchProduct(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "searchProduct");

		try {

			HttpSession session = request.getSession();

			String name = CommonUtil.isNull(request.getParameter("name"));
			String status = CommonUtil.isNull(request.getParameter("status"));
			String cid = CommonUtil.isNull(request.getParameter("cid"));
			String sid = CommonUtil.isNull(request.getParameter("sid"));
			String product_type = CommonUtil.isNull(request.getParameter("product_type"));

			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));

			if (request.getAttribute("status") != null) {
				status = (String) request.getAttribute("status");
			}

			Integer i_status = status == null ? 0 : Integer.parseInt(status);
			Integer i_sid = sid == null ? 0 : Integer.parseInt(sid);
			Integer i_cid = cid == null ? 0 : Integer.parseInt(cid);
			Integer i_product_type = product_type == null ? 0 : Integer.parseInt(product_type);

			Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			ProductSearchBean searchBean = new ProductSearchBean();

			searchBean.setStatus(i_status == 0 ? null : i_status);
			searchBean.setName(name);
			searchBean.setCid(i_cid == 0 ? null : i_cid);
			searchBean.setSid(i_sid == 0 ? null : i_sid);
			searchBean.setProduct_type(i_product_type == 0 ? null : i_product_type);
			searchBean.setBegin(begin);
			searchBean.setSize(size);

			List<Product> list = new ArrayList<Product>();
			int count = 0;

			if (request.getParameter("pageIndex") != null) {
				list = this.productService.getProductList(searchBean);
				count = this.productService.countProductList(searchBean);
				if (0 == count)
					i_pageIndex = 0;
			}

			// List<ServiceVo> service_list =
			// this.sevService.getServiceList(1,null);

			request.setAttribute("list", list);
			// request.setAttribute("slist", service_list); //由session提供 TODO
			request.setAttribute("slist", request.getAttribute("user_service")); // 由session提供
			// TODO
			request.setAttribute("pageCount", count % size == 0 ? count / size : count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/product/product_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "editProduct");

		try {

			String product_id = request.getParameter("viewId");
			// String type = request.getParameter("type");

			if (product_id == null && request.getAttribute("product_id") != null) {
				product_id = (String) request.getAttribute("product_id");
			}

			if (product_id == null) {

				product_id = request.getParameter("product_id");

				String name = CommonUtil.isNull(request.getParameter("name"));
				String price = CommonUtil.isNull(request.getParameter("price"));
				String market_price = CommonUtil.isNull(request.getParameter("market_price"));
				String unit = CommonUtil.isNull(request.getParameter("unit"));
				String amount = CommonUtil.isNull(request.getParameter("amount"));
				String delivery_type = CommonUtil.isNull(request.getParameter("delivery_type"));
				String delivery_price = CommonUtil.isNull(request.getParameter("delivery_price"));
				String picture = CommonUtil.isNull(request.getParameter("picture"));
				String thumbnail = CommonUtil.isNull(request.getParameter("picture"));
				String description_url = CommonUtil.isNull(request.getParameter("description_url"));
				String description_pic = CommonUtil.isNull(request.getParameter("description_pic"));
				String product_type = CommonUtil.isNull(request.getParameter("product_type"));
				String status = CommonUtil.isNull(request.getParameter("status"));
				String rule_id = CommonUtil.isNull(request.getParameter("rule_id"));
				String service_id = CommonUtil.isNull(request.getParameter("sid"));
				String barcode = CommonUtil.isNull(request.getParameter("barcode"));
				String cid_1 = CommonUtil.isNull(request.getParameter("cid_1"));
				String dec_tag = CommonUtil.isNull(request.getParameter("dec_tag"));

				// 满
				String man = CommonUtil.isNull(request.getParameter("man"));
				// 减少
				String jian = CommonUtil.isNull(request.getParameter("jian"));

				Integer i_status = Integer.parseInt(status);
				Integer i_amount = amount == null ? 0 : Integer.parseInt(amount);
				Integer i_delivery_type = delivery_type == null ? 1 : Integer.parseInt(delivery_type);
				Integer i_rule_id = (rule_id == null || "0".equals(rule_id)) ? null : Integer.parseInt(rule_id);
				Integer i_product_type = (product_type == null || "0".equals(product_type)) ? null : Integer.parseInt(product_type);
				Integer i_service_id = (service_id == null || "0".equals(service_id)) ? null : Integer.parseInt(service_id);

				// 更新营销类型
				String[] tags = request.getParameterValues("tags");
				int old_tags2 = 0;
				if (null != tags) {
					for (String s : tags) {
						old_tags2 = ProductTags.setTags(old_tags2, Integer.parseInt(s), true);
					}
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("product_id", Integer.parseInt(product_id));
				map.put("old_tags2", old_tags2);
				productService.service_product_tagsUp(map);
				Product product = new Product();
				product.setProduct_id(Integer.parseInt(product_id));
				product.setName(name);
				product.setService_id(i_service_id);
				product.setPrice(CommonUtil.safeToDouble(price, null));
				product.setMarket_price(CommonUtil.safeToDouble(market_price, null));
				product.setUnit(unit);
				product.setAmount(i_amount);
				product.setDelivery_type(i_delivery_type);
				// product.setPicture(picture);
				// product.setThumbnail(thumbnail);
				product.setDescription_url(description_url);
				product.setStatus(i_status);
				product.setRule_id(i_rule_id);
				product.setBarcode(barcode);
				product.setDec_tag(dec_tag);
				// product.setDescription_pic(description_pic);
				product.setProduct_type(i_product_type);
				// 满减set
				if (man != null && jian != null)
					product.setWholesale_price(man + "|" + jian);

				if (i_delivery_type != null && i_delivery_type == 2) {
					Double f_delivery_price = delivery_price == null ? 0 : Double.parseDouble(delivery_price);
					product.setDelivery_price(f_delivery_price);
				}

				if (StringUtils.isNotBlank(picture)) {

					String new_path = "/upload/product/" + cid_1 + "/picture/" + product_id + picture.substring(picture.lastIndexOf("."));

					try {
						picture = URLEncoder.encode(picture, "utf-8");
						String url_new_path = URLEncoder.encode(new_path, "utf-8");
						HttpUtil.doGet(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + "/common/rename/", "password=qxitrename&old_path=" + picture + "&new_path=" + url_new_path);

						product.setPicture(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + new_path);
						product.setThumbnail(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + new_path);

					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						logger.error(e1.getMessage());
					}

				}

				if (StringUtils.isNotBlank(description_pic)) {

					String new_path = "/upload/product/" + cid_1 + "/payoff_pic/" + product_id + description_pic.substring(description_pic.lastIndexOf("."));

					try {
						description_pic = URLEncoder.encode(description_pic, "utf-8");
						String url_new_path = URLEncoder.encode(new_path, "utf-8");
						HttpUtil.doGet(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + "/common/rename/", "password=qxitrename&old_path=" + description_pic + "&new_path=" + url_new_path);

						product.setDescription_pic(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + new_path);
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						logger.error(e1.getMessage());
					}

				}

				this.productService.updateProduct(product);
				request.setAttribute("isEdit", 0);

			}

			Product product = productService.getProduct(Integer.parseInt(product_id));

			List<ProductRule> rules = productService.getRuleList(product.getCommunity_id());
			request.setAttribute("product", product);
			request.setAttribute("rules", rules);

			return "/jsp/product/product_edit";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	@RequestMapping(params = "method=add", method = { RequestMethod.POST, RequestMethod.GET })
	public String addProduct(HttpServletRequest request) {
		LogerUtil.logRequest(request, logger, "addProduct");
		try {
			String name = request.getParameter("name");

			if (name != null) {
				String price = CommonUtil.isNull(request.getParameter("price"));
				String market_price = CommonUtil.isNull(request.getParameter("market_price"));
				String unit = CommonUtil.isNull(request.getParameter("unit"));
				String amount = CommonUtil.isNull(request.getParameter("amount"));
				String delivery_type = CommonUtil.isNull(request.getParameter("delivery_type"));
				String delivery_price = CommonUtil.isNull(request.getParameter("delivery_price"));
				String picture = CommonUtil.isNull(request.getParameter("picture"));
				String thumbnail = CommonUtil.isNull(request.getParameter("picture"));
				String description_url = CommonUtil.isNull(request.getParameter("description_url"));
				String status = CommonUtil.isNull(request.getParameter("status"));
				String barcode = CommonUtil.isNull(request.getParameter("barcode"));
				String cid = request.getParameter("cid");
				String sid = request.getParameter("sid");
				String man = CommonUtil.isNull(request.getParameter("man"));
				String jian = CommonUtil.isNull(request.getParameter("jian"));

				String[] tags = request.getParameterValues("tags");
				int old_tags2 = 0;
				if (null != tags) {
					for (String s : tags) {
						old_tags2 = ProductTags.setTags(old_tags2, Integer.parseInt(s), true);
					}
				}
				
				Integer i_status = Integer.valueOf(Integer.parseInt(status));
				Integer i_amount = Integer.valueOf(amount == null ? 0 : Integer.parseInt(amount));
				Integer i_delivery_type = Integer.valueOf(delivery_type == null ? 1 : Integer.parseInt(delivery_type));
				Product product = new Product();
				product.setName(name);
				product.setPrice(CommonUtil.safeToDouble(price, null));
				product.setMarket_price(CommonUtil.safeToDouble(market_price, null));
				product.setUnit(unit);
				product.setAmount(i_amount);
				product.setDelivery_type(i_delivery_type);
				if (picture != null) {
					product.setPicture(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + picture);
				}
				product.setThumbnail(thumbnail);
				product.setDescription_url(description_url);
				product.setStatus(i_status);
				product.setTags(Integer.valueOf(0));
				product.setCommunity_id(Integer.valueOf(Integer.parseInt(cid)));
				product.setService_id(Integer.valueOf(Integer.parseInt(sid)));
				product.setBarcode(barcode == null ? " " : barcode);
				product.setTags(old_tags2);
				
				if ((man != null) && (jian != null)) {
					product.setWholesale_price(man + "|" + jian);
				}

				if ((i_delivery_type != null) && (i_delivery_type.intValue() == 2)) {
					Double f_delivery_price = Double.valueOf(delivery_price == null ? 0d : Double.parseDouble(delivery_price));
					product.setDelivery_price(f_delivery_price);
				}

				int id = this.productService.insertProduct(product).intValue();

				if (StringUtils.isNotBlank(picture)) {
					try {
						String new_path = "/upload/product/" + cid + "/picture/" + id + picture.substring(picture.lastIndexOf("."));

						picture = URLEncoder.encode(picture, "utf-8");
						String url_new_path = URLEncoder.encode(new_path, "utf-8");

						HttpUtil.doGet(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + "/common/rename/", "password=qxitrename&old_path=" + picture + "&new_path=" + url_new_path);

						product = new Product();
						product.setProduct_id(Integer.valueOf(id));

						product.setPicture(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + new_path);
						product.setThumbnail(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + new_path);
						this.productService.updateProduct(product);
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				}
				request.setAttribute("product_id", String.valueOf(id));
				request.setAttribute("num", Integer.valueOf(1));
				return "jsp/product/product_list";
			}
			System.out.println("--------");
			return "/jsp/product/product_add";
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/jsp/error";
	}

	@RequestMapping(params = "method=del", method = { RequestMethod.POST, RequestMethod.GET })
	public String delProduct(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "delProduct");

		try {
			String pId = CommonUtil.isNull(request.getParameter("pId"));
			this.productService.delProduct(Integer.parseInt(pId));
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}
		return this.searchProduct(request);
	}

	@RequestMapping(params = "method=listRule", method = { RequestMethod.POST, RequestMethod.GET })
	public String searchRule(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "listRule");

		try {

			HttpSession session = request.getSession();

			String name = CommonUtil.isNull(request.getParameter("name"));
			String cid = CommonUtil.isNull(request.getParameter("cid"));

			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));

			Integer i_cid = cid == null ? 0 : Integer.parseInt(cid);

			Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);

			int begin = (i_pageIndex - 1) * size;

			ProductSearchBean searchBean = new ProductSearchBean();

			searchBean.setName(name);
			searchBean.setCid(i_cid == 0 ? null : i_cid);

			searchBean.setBegin(begin);
			searchBean.setSize(size);

			List<ProductRule> list = new ArrayList<ProductRule>();
			int count = 0;

			if (request.getParameter("pageIndex") != null) {
				list = this.productService.getRuleListS(searchBean);
				count = this.productService.countRuleListS(searchBean);
				if (0 == count)
					i_pageIndex = 0;
			}

			// List<ServiceVo> service_list = this.sevService.getServiceList(1);

			request.setAttribute("list", list);
			// request.setAttribute("slist", service_list);
			request.setAttribute("pageCount", count % size == 0 ? count / size : count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/product/rule_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=editRule", method = { RequestMethod.POST, RequestMethod.GET })
	public String editRule(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "editRule");

		try {

			String rule_id = request.getParameter("viewId");
			// String type = request.getParameter("type");

			if (rule_id == null) {

				rule_id = request.getParameter("rule_id");

				if (request.getAttribute("rule_id") != null) {
					rule_id = (String) request.getAttribute("rule_id");
				}

				String rule_name = request.getParameter("rule_name");
				String user_limit = request.getParameter("user_limit");
				String amount_limit = request.getParameter("amount_limit");
				String rule_dec = request.getParameter("rule_dec");
				String rule_begin_time = request.getParameter("rule_begin_time");
				String rule_end_time = request.getParameter("rule_end_time");

				Integer i_user_limit = user_limit == null ? 0 : Integer.parseInt(user_limit);
				Integer i_amount_limit = amount_limit == null ? 0 : Integer.parseInt(amount_limit);

				ProductRule rule = new ProductRule();
				rule.setRule_id(Integer.parseInt(rule_id));
				rule.setRule_name(rule_name);
				rule.setRule_dec(rule_dec);
				rule.setUser_limit(i_user_limit);
				rule.setAmount_limit(i_amount_limit);
				rule.setRule_begin_time(CommonUtil.getTimestamp(rule_begin_time, 1));
				rule.setRule_end_time(CommonUtil.getTimestamp(rule_end_time, 1));

				this.productService.updateRule(rule);
				request.setAttribute("isEdit", 0);

			}

			ProductRule rule = productService.getRule(Integer.parseInt(rule_id));

			request.setAttribute("rule", rule);

			return "/jsp/product/rule_edit";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	@RequestMapping(params = "method=addRule", method = { RequestMethod.POST, RequestMethod.GET })
	public String addRule(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "addRule");

		try {

			String rule_name = request.getParameter("rule_name");
			if (rule_name != null) {
				String user_limit = request.getParameter("user_limit");
				String amount_limit = request.getParameter("amount_limit");
				String rule_dec = request.getParameter("rule_dec");
				String rule_begin_time = request.getParameter("rule_begin_time");
				String rule_end_time = request.getParameter("rule_end_time");
				String cid = request.getParameter("cid");
				Integer i_user_limit = user_limit == null ? 0 : Integer.parseInt(user_limit);
				Integer i_amount_limit = amount_limit == null ? 0 : Integer.parseInt(amount_limit);
				ProductRule rule = new ProductRule();
				rule.setRule_name(rule_name);
				rule.setRule_dec(rule_dec);
				rule.setUser_limit(i_user_limit);
				rule.setAmount_limit(i_amount_limit);
				rule.setRule_begin_time(CommonUtil.getTimestamp(rule_begin_time, 1));
				rule.setRule_end_time(CommonUtil.getTimestamp(rule_end_time, 1));
				rule.setCommunity_id(Integer.parseInt(cid));

				int id = this.productService.insertRule(rule);

				request.setAttribute("rule_id", String.valueOf(id));

				return this.editRule(request);

			} else {

				return "/jsp/product/rule_add";
			}

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	@RequestMapping(params = "method=listService", method = { RequestMethod.POST, RequestMethod.GET })
	public String listService(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "searchProduct");

		try {

			return "jsp/product/service_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=editService", method = { RequestMethod.POST, RequestMethod.GET })
	public String editService(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "editProduct");

		try {

			String vid = request.getParameter("viewId");
			String sid = request.getParameter("sid");
			String op_type = "";

			if (StringUtils.isBlank(vid) && sid == null) {

				return "jsp/product/service_edit";
			} else if (StringUtils.isNotBlank(vid)) {
				List<ServiceVo> list = (List<ServiceVo>) request.getSession().getAttribute("product_service");
				ServiceVo service = null;
				for (ServiceVo vo : list) {
					if ((int) vo.getService_id() == Integer.parseInt(vid)) {
						service = vo;
						break;
					}
				}
				request.setAttribute("service", service);
				return "jsp/product/service_edit";
			} else if (sid != null) {
				Community c = (Community) request.getSession().getAttribute("selectCommunity");
				int curr_cid = c.getCommunity_id();

				String sname = request.getParameter("sname");
				String cid = request.getParameter("cid");
				String pid = request.getParameter("pid");
				String sdec = request.getParameter("sdec");
				String sorder = request.getParameter("sorder");
				String status = request.getParameter("status");

				ServiceVo service = new ServiceVo();
				service.setService_name(sname);
				service.setCommunity_id(Integer.parseInt(cid));
				service.setParent_id(Integer.parseInt(pid));
				service.setService_dec(sdec);
				service.setService_type(1);
				service.setService_order(1);
				service.setStatus(Integer.parseInt(status));
				service.setService_order(Integer.parseInt(sorder));

				if (service.getCommunity_id() < 6) {
					service.setCommunity_id(1);
				}

				if (StringUtils.isNotBlank(sid)) {
					service.setService_id(Integer.parseInt(sid));
					this.sevService.updateService(service);

				} else {
					sid = this.sevService.insertService(service).toString();
					op_type = "add";

				}

				// all_service 当前小区全部服务列表
				request.getSession().setAttribute("all_service", this.sevService.getServiceList(null, curr_cid));
				// product_service 当前小区全部商品服务列表
				request.getSession().setAttribute("product_service", this.sevService.getServiceList(1, curr_cid));

				List<ServiceVo> list = (List<ServiceVo>) request.getSession().getAttribute("product_service");
				ServiceVo cservice = null;
				for (ServiceVo vo : list) {
					if ((int) vo.getService_id() == Integer.parseInt(sid)) {
						cservice = vo;
						break;
					}
				}
				request.setAttribute("service", cservice);
				request.setAttribute("isEdit", true);
			}

			if (op_type.length() > 0)
				return this.listService(request);

			return "jsp/product/service_edit";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=delService", method = { RequestMethod.POST, RequestMethod.GET })
	public String delService(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "editProduct");

		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");
			int curr_cid = c.getCommunity_id();

			String sid = request.getParameter("sid");

			this.sevService.delService(Integer.parseInt(sid));

			// all_service 当前小区全部服务列表
			request.getSession().setAttribute("all_service", this.sevService.getServiceList(null, curr_cid));
			// product_service 当前小区全部商品服务列表
			request.getSession().setAttribute("product_service", this.sevService.getServiceList(1, curr_cid));

			return this.listService(request);

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=bat", method = { RequestMethod.POST, RequestMethod.GET })
	public String bat(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "bat");

		try {

			String type = request.getParameter("bat_type");

			String sid_from = request.getParameter("sid_from");
			String sid_to = request.getParameter("sid_to");

			int status = 1;
			if ("2".equals(type) || "5".equals(type)) {
				status = 2;
			}

			if ("1".equals(type) || "2".equals(type) || "3".equals(type)) {

				String[] ids = request.getParameterValues("pids");
				for (String id : ids) {

					if ("3".equals(type)) {
						this.productService.updateProducts(Integer.parseInt(id), null, Integer.parseInt(sid_to), null);
					} else {
						this.productService.updateProducts(Integer.parseInt(id), null, null, status);
					}
				}

			}

			if ("4".equals(type) || "5".equals(type) || "6".equals(type)) {

				if ("6".equals(type)) {
					this.productService.updateProducts(null, Integer.parseInt(sid_from), Integer.parseInt(sid_to), null);
				} else {
					this.productService.updateProducts(null, Integer.parseInt(sid_from), null, status);
				}

			}

			return this.searchProduct(request);

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

    @RequestMapping(params = "method=showListProductPage")
    public String showListProductPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/product/listProduct";
    }

    @RequestMapping(params = "method=searchProduct")
    public void searchProduct(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchProduct");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            List<Product> list = productService.searchProduct(param);
            int count = productService.searchProductCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchProduct error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=removeProduct")
    public void removeProduct(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        LogerUtil.logRequest(request, logger, "removeProduct");
        try {
            String pId = CommonUtil.isNull(request.getParameter("product_id"));
            this.productService.delProduct(Integer.parseInt(pId));
            result = "success";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        CommonUtil.outToWeb(response, result);
    }

    @RequestMapping(params = "method=operateProduct")
    public void operateProduct(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        LogerUtil.logRequest(request, logger, "operateProduct");
        try {

            String ids = request.getParameter("ids");
            Integer sid_from = CommonUtil.safeToInteger(request.getParameter("sid_from"), null);
            Integer sid_to = CommonUtil.safeToInteger(request.getParameter("sid_to"), null);
            Integer status = CommonUtil.safeToInteger(request.getParameter("status"), null);

            if(StringUtils.isNotBlank(ids)) {
                String[] id_array = ids.split(",");
                for(String id : id_array) {
                    Integer product_id = CommonUtil.safeToInteger(id, null);
                    if(product_id != null) {
                        productService.updateProducts(product_id, sid_from, sid_to, status);
                    }
                }
                result = "success";
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        CommonUtil.outToWeb(response, result);
    }

    @RequestMapping(params = "method=viewProduct")
    public String viewProduct(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "viewProduct");
        try {
            Integer product_id = CommonUtil.safeToInteger(request.getParameter("product_id"), null);

            if(product_id != null) {
                Product product = productService.getProduct(product_id);
                request.setAttribute("product", product);
            }

        } catch (Exception e) {
            logger.error("viewProduct", e);
        }
        return "/new/jsp/product/editProduct";
    }

    @RequestMapping(params = "method=findProductRule")
    public void findProductRule(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "findProductRule");
        try {
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            if(community_id != null) {
                List<ProductRule> list = productService.getRuleList(community_id);
                if(list != null && list.size() > 0) {
                    CommonUtil.outToWeb(response, JSONArray.fromObject(list).toString());
                }
            }
        } catch (Exception e) {
            logger.error("findProductRule error", e);
        }
    }

    @RequestMapping(params = "method=saveProduct")
    public void saveProduct(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        String result = "fail";
        LogerUtil.logRequest(request, logger, "editProduct");
        try {
            Integer product_id = CommonUtil.safeToInteger(request.getParameter("product_id"), null);

            Product product = null;
            if(product_id != null) {
                //修改
                product = productService.getProduct(product_id);
            } else {
                //新增
                product = new Product();
                product.setAmount(0);
                product.setCreateTime(new Date());
            }

            if (product != null) {
                Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);    //门店
                Integer service_id = CommonUtil.safeToInteger(request.getParameter("service_id"), null);    //商品类别
                Integer product_type = CommonUtil.safeToInteger(request.getParameter("product_type"), null);
                String name = request.getParameter("name");  //名称
                String picture = request.getParameter("picture");   //商品图片
                String old_picture = request.getParameter("old_picture");   //商品图片(旧)
                String description_url = request.getParameter("description_url");   //描述url
                String description_pic = request.getParameter("description_pic");   //描述图片
                String old_description_pic = request.getParameter("old_description_pic");   //描述图片(旧)
                String description = request.getParameter("description");   //描述
                String dec_tag = request.getParameter("dec_tag");   //营销标题
                Double price = CommonUtil.safeToDouble(request.getParameter("price"), null);    //价格
                Double market_price = CommonUtil.safeToDouble(request.getParameter("market_price"), null);  //APP专享键
                String unit = request.getParameter("unit");     //单位
                Integer delivery_type = CommonUtil.safeToInteger(request.getParameter("delivery_type"), null);  //是否送货上门
                Double delivery_price = CommonUtil.safeToDouble(request.getParameter("delivery_price"), null);  //送货上门价格
                Double comment_level = CommonUtil.safeToDouble(request.getParameter("comment_level"), null);
                Integer status = CommonUtil.safeToInteger(request.getParameter("status"), null);    //状态
                Integer sold = CommonUtil.safeToInteger(request.getParameter("sold"), null);
                Integer rule_id = CommonUtil.safeToInteger(request.getParameter("rule_id"), null);      //营销规则
                String barcode = request.getParameter("barcode");   //条码

                Double man = CommonUtil.safeToDouble(request.getParameter("man"), null);    //满
                Double jian = CommonUtil.safeToDouble(request.getParameter("jian"), null);  //减

                String[] tags = request.getParameterValues("tags");     //tags
                int product_tags = 0;
                if(tags != null && tags.length > 0) {
                    for (String s : tags) {
                        product_tags = ProductTags.setTags(product_tags, Integer.parseInt(s), true);
                    }
                }

                //处理满减
                if(tags != null && tags.length > 0 && ArrayUtils.contains(tags, ProductTags.WHOLESALE + "")) {
                    product.setWholesale_price(man + "|" + jian);
                } else {
                    product.setWholesale_price(null);
                }

                //处理送货上门价格
                if(delivery_type != null && delivery_type == 2) {
                    product.setDelivery_price(delivery_price);
                } else {
                    product.setDelivery_price(null);
                }

                product.setCommunity_id(community_id);
                product.setService_id(service_id);
                product.setProduct_type(product_type);
                product.setName(name);
                product.setDescription_url(description_url);
                product.setDescription(description);
                product.setPrice(price);
                product.setMarket_price(market_price);
                product.setUnit(unit);
                product.setDelivery_type(delivery_type);
                product.setComment_level(comment_level);
                product.setStatus(status);
                product.setSold(sold);
                product.setRule_id(rule_id);
                product.setBarcode(barcode);
                product.setTags(product_tags);
                product.setUpdateTime(new Date());

                productService.saveProduct(product);
                //保存之后再处理
                //只有重新上传了图片之后才处理商品图片
                if (StringUtils.isNotBlank(picture) && !picture.equals(old_picture)) {
                    String new_path = "/upload/product/" + community_id + "/picture/" + System.currentTimeMillis() + picture.substring(picture.lastIndexOf("."));
                    picture = URLEncoder.encode(picture, "utf-8");
                    String url_new_path = URLEncoder.encode(new_path, "utf-8");
                    HttpUtil.doGet(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + "/common/rename/", "password=qxitrename&old_path=" + picture + "&new_path=" + url_new_path);

                    product.setPicture(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + new_path);
                    product.setThumbnail(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + new_path);
                }

                //处理促销信息
                if(product_type != null && product_type == 2) {
                    product.setDec_tag(dec_tag);
                    //处理促销图片
                    if (StringUtils.isNotBlank(description_pic) && !description_pic.equals(old_description_pic)) {
                        String new_path = "/upload/product/" + community_id + "/payoff_pic/" + System.currentTimeMillis() + description_pic.substring(description_pic.lastIndexOf("."));
                        description_pic = URLEncoder.encode(description_pic, "utf-8");
                        String url_new_path = URLEncoder.encode(new_path, "utf-8");
                        HttpUtil.doGet(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + "/common/rename/", "password=qxitrename&old_path=" + description_pic + "&new_path=" + url_new_path);

                        product.setDescription_pic(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + new_path);
                    }
                } else {
                    product.setDec_tag(null);
                    product.setDescription_pic(null);
                }
                productService.saveProduct(product);

                result = "success";
                map.put("product_id", product.getProduct_id());
            }
        } catch (Exception e) {
            logger.error("editProduct error", e);
        }
        map.put("result", result);
        CommonUtil.outToWeb(response, JSONObject.fromObject(map).toString());
    }

    @RequestMapping(params = "method=listProductServiceTree")
    public void listProductServiceTree(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "editProduct");
        try {
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            Integer pid = CommonUtil.safeToInteger(request.getParameter("pid"), 0);


        } catch (Exception e) {
            logger.error("listProductServiceTree error", e);
        }
    }

    /**
     * 返回门店对应的商品类别option
     * */
    @RequestMapping(params = "method=productServiceSelect")
    public void productServiceSelect(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "productServiceSelect");
        try {
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);

            if(community_id != null) {
                List<ServiceVo> serviceList = sevService.getServiceList(1, community_id);

                List<Map> list = new ArrayList<Map>();
                List<ServiceVo> fstService = new ArrayList<ServiceVo>();  //顶级类别
                for(ServiceVo service : serviceList) {
                    if(service.getParent_id() == null || service.getParent_id().intValue() == 0) {
                        fstService.add(service);
                    }
                }

                for(ServiceVo parent : fstService) {
                    List<ServiceVo> children = new ArrayList<ServiceVo>();
                    for (ServiceVo service : serviceList) {
                        if(service.getParent_id() != null && service.getParent_id().intValue() == parent.getService_id()) {
                            children.add(service);
                        }
                    }

                    Map map = new HashMap();
                    map.put("parent", parent);
                    map.put("children", children);
                    list.add(map);
                }

                StringBuffer html = new StringBuffer();
                for(Map m : list) {
                    ServiceVo parent = (ServiceVo)m.get("parent");
                    List<ServiceVo> children = (List)m.get("children");

                    html.append("<optgroup label='").append(parent.getService_name()).append("' data-sid='").append(parent.getService_id()).append("'>");
                    for (ServiceVo child : children) {
                        html.append("<option value='").append(child.getService_id()).append("'>").append(child.getService_name()).append("</option>");
                    }

                    html.append("</optgroup>");
                }
                CommonUtil.outToWeb(response, html.toString());
            }

        } catch (Exception e) {
            logger.error("productServiceSelect error", e);
        }
    }

    /**
     * 返回门店对应的商品类别option
     * */
    @RequestMapping(params = "method=simpleProductServiceOptions")
    public void simpleProductServiceOptions(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "simpleProductServiceOptions");
        try {
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);

            if(community_id != null) {
                List<ServiceVo> serviceList = sevService.findSecondProductService(community_id);

                StringBuffer html = new StringBuffer();
                for(ServiceVo service : serviceList) {
                    html.append("<option value='").append(service.getService_id()).append("'>").append(service.getService_name()).append("</option>");
                }
                CommonUtil.outToWeb(response, html.toString());
            }

        } catch (Exception e) {
            logger.error("simpleProductServiceOptions error", e);
        }
    }

    @RequestMapping(params = "method=showListRulePage")
    public String showListRulePage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/product/listRule";
    }

    @RequestMapping(params = "method=searchRule")
    public void searchRule(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchRule");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            List<Map> list = productService.searchRule(param);
            int count = productService.searchRuleCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchRule error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=getRuleById")
    public void getRuleById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getRuleById");

            Integer rule_id = CommonUtil.safeToInteger(request.getParameter("rule_id"), null);

            if(rule_id != null) {
                ProductRule rule = productService.getRule(rule_id);
                if(rule != null) {
                    CommonUtil.outToWeb(response, JSONObject.fromObject(rule).toString());
                }
            }
        } catch (Exception e) {
            logger.error("getRuleById error", e);
        }
    }

    @RequestMapping(params = "method=saveRule")
    public void saveRule(ProductRule rule, HttpServletRequest request, HttpServletResponse response) {
        //todo 待bug fix 手动设置参数
        String result = "fail";
        try {
            LogerUtil.logRequest(request, logger, "getRuleById");

            if(rule != null) {
                Date rule_begin_time = CommonUtil.safeToDate(request.getParameter("rule_begin_time_str"), "yyyy-MM-dd");
                Date rule_end_time = CommonUtil.safeToDate(request.getParameter("rule_end_time_str"), "yyyy-MM-dd");

                rule.setRule_begin_time(rule_begin_time);
                rule.setRule_end_time(rule_end_time);

                productService.saveRule(rule);
                result = "success";
            }
        } catch (Exception e) {
            logger.error("getRuleById error", e);
        }
        CommonUtil.outToWeb(response, result);
    }

    @RequestMapping(params = "method=showListProductServicePage")
    public String showListProductServicePage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/product/listProductService";
    }

    @RequestMapping(params = "method=searchService")
    public void searchService(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchService");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            List<Map> list = sevService.searchServiceBase(param);
            int count = sevService.searchServiceBaseCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchService error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=getServiceById")
    public void getServiceById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getServiceById");

            Integer service_id = CommonUtil.safeToInteger(request.getParameter("service_id"), null);

            if(service_id != null) {
                ServiceVo service = sevService.getServiceById(service_id);
                if(service != null) {
                    CommonUtil.outToWeb(response, JSONObject.fromObject(service).toString());
                }
            }
        } catch (Exception e) {
            logger.error("getServiceById error", e);
        }
    }

    @RequestMapping(params = "method=saveService")
    public void saveService(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            LogerUtil.logRequest(request, logger, "saveService");

            Integer service_id = CommonUtil.safeToInteger(request.getParameter("service_id"), null);
            String service_name = request.getParameter("service_name");
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            Integer parent_id = CommonUtil.safeToInteger(request.getParameter("parent_id"), null);
            Integer status = CommonUtil.safeToInteger(request.getParameter("status"), null);
            Integer service_order = CommonUtil.safeToInteger(request.getParameter("service_order"), null);
            String service_dec = request.getParameter("service_dec");

            ServiceVo service = null;
            if(service_id != null) {
                service = sevService.getServiceById(service_id);
            } else {
                service = new ServiceVo();
            }
            if(service != null) {
                service.setService_type(1);
                service.setService_name(service_name);
                service.setCommunity_id(community_id);
                service.setParent_id(parent_id);
                service.setStatus(status);
                service.setService_order(service_order);
                service.setService_dec(service_dec);

                if(service_id != null) {
                    sevService.updateService(service);
                } else {
                    sevService.insertService(service);
                }

                //todo 此处还应更新session、缓存等信息
                result = "success";
            }
        } catch (Exception e) {
            logger.error("saveService error", e);
        }
        CommonUtil.outToWeb(response, result);
    }
}

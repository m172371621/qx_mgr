package com.brilliantreform.sc.POS.soap;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.brilliantreform.sc.POS.service.PosTempService;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.order.po.Order;
import com.brilliantreform.sc.order.service.OrderService;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.po.ProductSearchBean;
import com.brilliantreform.sc.product.po.ProductTags;
import com.brilliantreform.sc.product.service.ProductService;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.JsonUtil;

@WebService(endpointInterface = "com.brilliantreform.sc.POS.soap.PosWS", serviceName = "posInt")
public class PosWSImpl implements PosWS {

	private static Logger logger = Logger.getLogger(PosWSImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PosTempService posTempService;

	@SuppressWarnings("unchecked")
	@Override
	public String getProduct(String authentication, String params) {
		logger.info("getProduct:" + params);

		int result_code = 0;
		String result_dec = "OK";
		Map<String, String> dataMap = null;

		String result = JsonUtil.result2Json(false, null);

		if (!this.check(authentication)) {
			return JsonUtil.result2Json(-1, "auth fail", null);
		}

		try {

			Map map = JsonUtil.json2Map(params);

			String code = (String) map.get("code");
			String user_id = (String) map.get("user_id");
			Integer cid = PosUserMap
					.getUserCommunity(Integer.parseInt(user_id));

			if (user_id == null || cid == null) {
				return JsonUtil.result2Json(-1, "none user", null);
			}

			Product p = productService.getProductByBarcode(code, cid);

			if (p != null) {
				dataMap = new HashMap();
				dataMap.put("product_id", p.getProduct_id() + "");
				dataMap.put("name", p.getName());
				dataMap.put("unit", p.getUnit());
				dataMap.put("price", p.getPrice() + "");
				dataMap.put("amount", p.getAmount() + "");
				if (ProductTags.checkTag(p.getTags(), ProductTags.WEIGHT)) {
					dataMap.put("weight", "1");
				}else
				{
					dataMap.put("weight", "0");
				}
				if (ProductTags.checkTag(p.getTags(), ProductTags.WHOLESALE)) {
					dataMap.put("wholesale", "1");
					dataMap.put("wholesale_price", p.getWholesale_price());
				}else
				{
					dataMap.put("wholesale", "0");
					dataMap.put("wholesale_price", "");
				}
			} else {
				result_code = -1;
				result_dec = "none";
			}

			result = JsonUtil.result2Json(result_code, result_dec, dataMap);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info("getProduct:" + result);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String posLogin(String authentication, String params) {

		logger.info("posLogin:" + params);

		int result_code = 0;
		String result_dec = "OK";
		Map<String, String> dataMap = null;

		String result = JsonUtil.result2Json(false, null);

		if (!this.check(authentication)) {
			return JsonUtil.result2Json(-1, "auth fail", null);
		}

		try {

			Map map = JsonUtil.json2Map(params);

			String username = (String) map.get("username");
			String password = (String) map.get("password");

			Integer user_id = 0;

			if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
				result_code = -1;
				result_dec = "用户名或密码错误";
			} else {

				user_id = userService.login(username, password);

				// 判断登陆结果
				if (user_id <= 0) {
					result_code = -1;
					result_dec = "用户名或密码错误";
				} else {
					List<Community> community_list = this.userService
							.getUserCommunity(user_id);
					if (community_list == null || community_list.size() == 0) {
						result_code = -2;
						result_dec = "用户名或密码错误";
					} else {
						PosUserMap.setUserCommunity(user_id, community_list
								.get(0).getCommunity_id());

						dataMap = new HashMap();
						dataMap.put("user_id", user_id + "");
						dataMap.put("user_name", username);
						dataMap.put("community_name", community_list.get(0)
								.getCommunity_name());
					}
				}
			}

			result = JsonUtil.result2Json(result_code, result_dec, dataMap);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info("posLogin:" + result);
		return result;
	}

	@Override
	public String posOrder(String authentication, String params) {

		logger.info("posOrder:" + params);

		int result_code = 0;
		String result_dec = "OK";
		Map<String, String> dataMap = null;

		String result = JsonUtil.result2Json(false, null);

		if (!this.check(authentication)) {
			return JsonUtil.result2Json(-1, "auth fail", null);
		}

		try {

			Map map = JsonUtil.json2Map(params);

			List<Map> products = (List<Map>) map.get("products");

			String user_id = (String) map.get("user_id");


			Integer cid = PosUserMap
					.getUserCommunity(Integer.parseInt(user_id));

			if (user_id == null || cid == null) {
				return JsonUtil.result2Json(-1, "none user", null);
			}
			// int set_user_id = 192;

			List<Order> list = new ArrayList<Order>();
			long now = System.currentTimeMillis();
			String order_serial = CommonUtil.createOrderSerial("" + 192);

			for (Map tmap : products) {
				Order order = new Order();
				order.setUser_id(192);
				order.setCommunity_id(cid);
				order.setPay_type(1);
				order.setChannel("4");
				order.setOrder_serial(order_serial);

				order.setDelivery_type(1);
				order.setOrder_time(new Timestamp(now));

				order.setOrder_status(3);
				order.setComment_status(1);

				order.setIp("");
				order.setSalesman("POS:" + user_id);
				order.setProduct_id(Integer.parseInt((String) tmap
						.get("product_id")));
				String amount = (String) tmap.get("amount");
//				amount = amount.split("\\.")[0];
//				order.setProduct_amount(Integer.parseInt(amount));
				
				order.setProduct_amount(Double.parseDouble(amount));

				

				try {
					Map custom_info = (Map) map.get("custom_info");
					String sex = (String) custom_info.get("sex");
					String age = (String) custom_info.get("age");
					String pay_type = (String) map.get("pay_type");
					String pay_off = (String) map.get("pay_off");
					
					if ("1".equals(pay_type)) {
						order.setPay_type_ext(11);
					} else if ("2".equals(pay_type)) {
						order.setPay_type_ext(12);
					} else {
						order.setPay_type_ext(11);
					}
					
					if ("M".equals(sex)) {
						order.setUser_sex(1);
					} else if ("F".equals(sex)) {
						order.setUser_sex(2);
					} else {
						order.setUser_sex(1);
					}

					if ("0-25".equals(age)) {
						order.setUser_age(1);
					} else if ("25-35".equals(age)) {
						order.setUser_age(2);
					} else if ("35-50".equals(age)) {
						order.setUser_age(3);
					} else if ("50+".equals(age)) {
						order.setUser_age(4);
					} else {
						order.setUser_age(1);
					}

					if(pay_off!=null)
						order.setPayoff_price(Double.parseDouble(pay_off));
					else
						order.setPayoff_price(0.0d);
				} catch (Exception e) {
					order.setPay_type_ext(11);
					order.setUser_sex(1);
					order.setUser_age(1);
					order.setPayoff_price(0.0d);
				}

				list.add(order);
			}

			Map order_result = this.orderService.createOrder3(list);

			if (order_result != null && order_result.get("result_code") != null) {
				result_code = (Integer) order_result.get("result_code");
				result_dec = (String) order_result.get("result_dec");
			} else {
				result_code = -1;
				result_dec = "下订单支付失败，请联系技术人员！";
			}

			result = JsonUtil.result2Json(result_code, result_dec, dataMap);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info("posOrder:" + result);
		return result;
	}

	private boolean check(String authentication) {
		if ("qxit_pos_2015".equals(authentication))
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String searchProduct(String authentication, String params) {
		logger.info("searchProduct:" + params);

		int result_code = 0;
		String result_dec = "OK";
		Map<String, String> dataMap = null;

		String result = JsonUtil.result2Json(false, null);

		if (!this.check(authentication)) {
			return JsonUtil.result2Json(-1, "auth fail", null);
		}

		try {

			Map map = JsonUtil.json2Map(params);

			String code = (String) map.get("code");
			String name = (String) map.get("name");
			String user_id = (String) map.get("user_id");
			Integer cid = PosUserMap
					.getUserCommunity(Integer.parseInt(user_id));

			if (user_id == null || cid == null) {
				return JsonUtil.result2Json(-1, "none user", null);
			}

			List<Product> pl = null;

			if (StringUtils.isNotBlank(code) || StringUtils.isNotBlank(name)) {
				ProductSearchBean pb = new ProductSearchBean();
				if (StringUtils.isNotBlank(code)) {
					pb.setBarcode(code.trim());
				}
				if (StringUtils.isNotBlank(name)) {
					pb.setName(name.trim());
				}
				pb.setCid(cid);
				pb.setBegin(0);
				pb.setStatus(1);
				pb.setSize(20);

				pl = productService.getProductList(pb);
			}

			if (pl != null && pl.size() > 0) {
				List<Map> list = new ArrayList<Map>();

				for (Product p : pl) {
					Map tmap = new HashMap();
					tmap.put("product_id", p.getProduct_id() + "");
					tmap.put("name", p.getName());
					tmap.put("unit", p.getUnit());
					tmap.put("price", p.getPrice() + "");
					tmap.put("amount", p.getAmount() + "");
					if (ProductTags.checkTag(p.getTags(), ProductTags.WEIGHT)) {
						tmap.put("weight", "1");
					}else
					{
						tmap.put("weight", "0");
					}
					if (ProductTags.checkTag(p.getTags(), ProductTags.WHOLESALE)) {
						tmap.put("wholesale", "1");
						tmap.put("wholesale_price", p.getWholesale_price());
					}else
					{
						tmap.put("wholesale", "0");
						tmap.put("wholesale_price", "");
					}
					list.add(tmap);
				}

				result = JsonUtil.result2Json(result_code, result_dec, list);
			} else {
				result_code = -1;
				result_dec = "none";

				result = JsonUtil.result2Json(result_code, result_dec, dataMap);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info("searchProduct:" + result);
		return result;
	}

	//未处理完 mark
	@Override
	public String addTempData(String authentication, String params) {
		logger.info("addTempData:" + params);

		int result_code = 0;
		String result_dec = "OK";

		String result = JsonUtil.result2Json(false, null);

		if (!this.check(authentication)) {
			return JsonUtil.result2Json(-1, "auth fail", null);
		}
		try {
			Map map = JsonUtil.json2Map(params);

			List<Map<String,Object>> products = (List<Map<String,Object>>) map.get("products");

			String user_id = (String) map.get("user_id");

			Integer cid = PosUserMap
					.getUserCommunity(Integer.parseInt(user_id));

			if (user_id == null || cid == null) {
				return JsonUtil.result2Json(-1, "none user", null);
			}
			Map<String,Object> paramMap=new HashMap<String, Object>();
			paramMap.put("user_id", user_id);
			paramMap.put("cid", cid);
			Map<String, Object> dataMap= posTempService.insertTempData(paramMap, products);
			result_code=Integer.parseInt(dataMap.get("result_code").toString()) ;
			result_dec=dataMap.get("result_dec").toString();
			result = JsonUtil.result2Json(result_code, result_dec);

		} catch (Exception e) {
			logger.error(e.getMessage());
			result = JsonUtil.result2Json(false, null);
		}
		return result;
	}
}

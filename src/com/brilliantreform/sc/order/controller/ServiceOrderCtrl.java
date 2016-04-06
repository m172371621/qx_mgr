package com.brilliantreform.sc.order.controller;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brilliantreform.sc.utils.SettingUtil;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.order.po.Order;
import com.brilliantreform.sc.order.service.OrderService;
import com.brilliantreform.sc.order.service.ServiceOrderService;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.service.ProductService;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.user.po.LoginInfo;
import com.brilliantreform.sc.user.po.UserInfo;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.JsonUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import com.brilliantreform.sc.weixin.dao.UserBindDao;
import com.brilliantreform.sc.weixin.util.WeixinUtil;

@Controller
@RequestMapping("serviceorder.do")
public class ServiceOrderCtrl {
	private static Logger logger = Logger.getLogger(ServiceOrderCtrl.class);

	public final static String EncodingAESKey = "si1Fq5e6x7JRiCUHXOqY3MYgUZTbomTm";
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

	@Autowired
	private UserBindDao userbindDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private ServiceOrderService serviceOrderService;

	@RequestMapping(params = "method=turnServiceOrder", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String turnServiceOrder(HttpServletRequest request) {

		Community c = (Community) request.getSession().getAttribute(
				"selectCommunity");
		int cid = c.getCommunity_id();
		LogerUtil.logRequest(request, logger, "turnServiceOrder");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "区享服务");
		map.put("community_id", cid);
		List<ServiceVo> slist = serviceOrderService.getService(map);
		request.setAttribute("qxit_service", slist);
		return "jsp/order/service_order_create";
	}

	@RequestMapping(params = "method=createServiceOrder", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String createServiceOrder(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "createServiceOrder");
			String product_id = request.getParameter("product_id");
			String phone = request.getParameter("phone");
			String product_price = request.getParameter("product_price");

			// Community c = (Community) request.getSession().getAttribute(
			// "selectCommunity");
			// int cid = c.getCommunity_id();
			LoginInfo info = userService.getUserByPhone(phone);
			UserInfo user = userService.getUserInfo(info.getUserId());
			String addr = (user.getFloor() == null ? "" : user.getFloor())
					+ "栋" + (user.getRoom() == null ? "" : user.getRoom())
					+ "室";

			Order order = new Order();
			order.setCommunity_id(user.getCommunityId());
			order.setOrder_type(2);
			order.setUser_id(info.getUserId());
			order.setProduct_id(Integer.parseInt(product_id));
			String order_serial = CommonUtil.createOrderSerial(String
					.valueOf(product_id));
			order.setOrder_serial(order_serial);
			order.setPay_type(2);
			order.setProduct_amount(1d);
			order.setProduct_price(Double.parseDouble(product_price));

			order.setDelivery_type(1);
			order.setDelivery_addr(addr);
			order.setOrder_type(2);

			order.setOrder_price(Double.parseDouble(product_price));

			order.setOrder_time(new Timestamp(System.currentTimeMillis()));
			order.setOrder_status(12);
			order.setComment_status(1);

			this.serviceOrderService.createOrder(order);
			Order myorder = orderService.getOrderBySerial(order_serial);
			// 此处做微信推送
			String retUrl = getNativeUrl(order_serial);
			JSONObject json = WeixinUtil.urlLong2Short(retUrl);
			retUrl = json.getString("short_url");

			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("phone", phone);

			Map<String, Object> msgmap = new HashMap<String, Object>();

			msgmap.put("URL",
					SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + "/weixinUserCenter/turn2OrderPay?order_serial="
							+ order_serial);
			msgmap.put("FIRST", "您有一个新的订单生成。");
			msgmap.put("KEYWORD1", myorder.getProduct_name());
			msgmap.put("KEYWORD2", product_price);
			msgmap.put("KEYWORD3", myorder.getProduct_amount());
			msgmap.put("KEYWORD4", myorder.getOrder_type() == 1 ? "商品类"
					: myorder.getOrder_type() == 2 ? "服务类" : myorder
							.getOrder_type() == 3 ? "模糊订单" : "");
			msgmap.put("KEYWORD5", "待支付");
			msgmap.put("REMARK", "感谢你的使用 , 祝您心情愉快。");

			WeixinUtil wutil = new WeixinUtil(userbindDao);
			wutil.sendMsgTemToUser(msgmap, pmap);
			// wutil.sendMsgToUser(retUrl, pmap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "jsp/order/service_order_create";
	}

	@RequestMapping(params = "method=getProductList", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String getProductList(HttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		LogerUtil.logRequest(request, logger, "getProductList");
		try {
			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();
			String serviceId = request.getParameter("serviceId");
			Map<String, Object> searchbean = new HashMap<String, Object>();
			searchbean.put("sid", Integer.parseInt(serviceId));
			searchbean.put("cid", cid);
//			searchbean.put("status", 1);
			List<Product> list = serviceOrderService.getProductList(searchbean);
			response.getWriter().print(JsonUtil.result2Json(0, "查询成功", list));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getNativeUrl(String productid) {
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		// 先设置基本信息
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String timestamp = String.valueOf(ts.getTime());
		String noncestr = CreateNoncestr();
		parameters.put("appid", WeixinUtil.appid);
		parameters.put("time_stamp", timestamp);
		parameters.put("nonce_str", noncestr);
		parameters.put("mch_id", "1235122102");
		parameters.put("product_id", productid);
		String sign = createSign("UTF-8", parameters);
		String retUrl = "weixin://wxpay/bizpayurl?appid=APPID&mch_id=MCHID&nonce_str=NONCESTR&product_id=PRODUCTID&time_stamp=TIMESTAMP&sign=SIGN";
		retUrl = retUrl.replace("SIGN", sign)
				.replace("APPID", WeixinUtil.appid).replace("MCHID",
						"1235122102").replace("PRODUCTID", productid).replace(
						"TIMESTAMP", timestamp).replace("NONCESTR", noncestr);
		return retUrl;
	}

	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public static String createSign(String characterEncoding,
			SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + EncodingAESKey);

		String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static void main(String args[]) {
		System.out.println(ServiceOrderCtrl
				.getNativeUrl("10025826056378823947"));
	}

}

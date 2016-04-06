package com.brilliantreform.sc.weixin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brilliantreform.sc.utils.SettingUtil;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.product.po.ProductDouble;
import com.brilliantreform.sc.utils.JsonUtil;
import com.brilliantreform.sc.weixin.po.WeixinQxx;
import com.brilliantreform.sc.weixin.service.WeixinQxxService;
import com.brilliantreform.sc.weixin.util.WeixinQxxUtil;

@Controller
public class WeixinQxxCtrl extends BaseExceptionController{

	private static Logger logger = Logger.getLogger(WeixinQxxCtrl.class);
	private final Integer limit_amount = 30;

	@Autowired
	private WeixinQxxService weixinQxxService;

	@RequestMapping(value="doWeixinInterative.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String doWeixinInterative(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = WeixinQxxUtil.getWeixinUserInfoByPost(request
				.getParameter("code"));
		String openid = map.get("openid").toString();
		String unionid = map.get("unionid").toString();
		request.getSession().setAttribute("openid", openid);
		request.getSession().setAttribute("unionid", unionid);
		WeixinQxx qxx = new WeixinQxx();
		qxx.setOpenid(openid);
		qxx.setRecommend_amount(0);
		qxx.setState("1");
		qxx.setNickname(map.get("nickname").toString());
		qxx.setImg_url(map.get("img_url").toString());
		qxx.setOther_recommend_code(request
				.getParameter("other_recommend_code")==null?"":request
						.getParameter("other_recommend_code"));
		WeixinQxx ret= weixinQxxService.insertPersonalInfo(qxx);
		request.getSession().setAttribute("other_recommend_code",
				ret.getOther_recommend_code());
		String uri_refer_to = "/weixinqxx.do?method=doWeixinQxxMain";
		logger.info(openid);
		logger.info(unionid);

		return "redirect:" + uri_refer_to;
	}

	@RequestMapping(value="weixinqxx.do", params = "method=doWeixinQxxMain", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String doWeixinQxxMain(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 查询当前openid的个人信息
//		request.getSession().setAttribute("openid",
//				"o-0EUwmT5XH80e46a-PK25CXOTFU");
		String openid = request.getSession().getAttribute("openid").toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid", openid);
		String page_url = "";
		WeixinQxx qxx = weixinQxxService.getPersonalInfo(map);
		page_url = "/new/jsp/weixinqxx/smallpage";
		if (qxx != null) {
			// 个人链接
			if (qxx.getRecommend_amount() >= limit_amount && qxx.getState().equals("2")) {
				page_url = "/new/jsp/weixinqxx/smallpage_buy";
			}
			request.setAttribute("recommend_amount", qxx.getRecommend_amount());
			request.setAttribute("my_recommend_code", qxx
					.getMy_recommend_code());
			request.setAttribute("other_recommend_code", qxx
					.getOther_recommend_code());
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		ProductDouble product = weixinQxxService.getProductInfo(paramMap);
		request.setAttribute("product_name", product.getName());
		request.setAttribute("product_price", product.getPrice());
		request.setAttribute("product_price_app", product.getMarket_price());
		request.setAttribute("product_pic", product.getPicture());
		request.setAttribute("product_dec_pic", product.getDescription_pic());
		request.setAttribute("product_amount", product.getReal_amount());
		request.setAttribute("my_recommend_code", qxx.getMy_recommend_code());
		request.setAttribute("other_recommend_code", request.getSession()
				.getAttribute("other_recommend_code") == null ? "" : request
				.getSession().getAttribute("other_recommend_code").toString());
		JSONObject json=JSONObject.fromObject(WeixinQxxUtil.getJsTickectJson(SettingUtil.getSettingValue("COMMON", "MGR_SERVER_HOST") + "/weixinqxx.do?method=doWeixinQxxMain"));
		logger.info(json.toString());
		request.setAttribute("appId", json.getString("appId"));
		request.setAttribute("timestamp", json.getString("timestamp"));
		request.setAttribute("nonceStr", json.getString("nonceStr"));
		request.setAttribute("signature", json.getString("signature"));
		return page_url;
	}

	@RequestMapping(value="weixinqxx.do",params = "method=doWeixinQxxNew", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String doWeixinQxxNew(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 查询当前openid的个人信息
		request.setCharacterEncoding("UTF-8");
		String name = new String(request.getParameter("name").getBytes("ISO8859_1"),"UTF-8");
		String phone = new String(request.getParameter("phone").getBytes("ISO8859_1"),"UTF-8");
		String addr = new String(request.getParameter("addr").getBytes("ISO8859_1"),"UTF-8");
		String my_recommend_code = generateShortUuid();
		String openid = request.getSession().getAttribute("openid").toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid", openid);
		WeixinQxx qxx = weixinQxxService.getPersonalInfo(map);
		if (qxx != null) {
			// 个人链接
			qxx.setName(name);
			qxx.setAddr(addr);
			qxx.setPhone(phone);
			if(qxx.getMy_recommend_code()==null){
				qxx.setMy_recommend_code(my_recommend_code);
			}
			weixinQxxService.updatePersonalInfo(qxx);
		}
		return "redirect:/weixinqxx.do?method=doWeixinQxxMain";
	}

	@RequestMapping(value="weixinqxx.do",params = "method=doWeixinQxxNewUrl", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String doWeixinQxxNewUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String openid = request.getSession().getAttribute("openid").toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("openid", openid);
		ProductDouble product = weixinQxxService.getProductInfo(paramMap);
		WeixinQxx qxx= weixinQxxService.getPersonalInfo(paramMap);
		request.setAttribute("product_name", product.getName());
		request.setAttribute("product_price", product.getPrice());
		request.setAttribute("product_price_app", product.getMarket_price());
		request.setAttribute("product_pic", product.getPicture());
		request.setAttribute("product_dec_pic", product.getDescription_pic());
		request.setAttribute("product_amount", product.getReal_amount());
		request.setAttribute("other_recommend_code", request.getSession()
				.getAttribute("other_recommend_code") == null ? "" : request
				.getSession().getAttribute("other_recommend_code").toString());
		request.setAttribute("name", qxx==null?"":qxx.getName());
		request.setAttribute("phone", qxx==null?"":qxx.getPhone());
		request.setAttribute("addr", qxx==null?"":qxx.getAddr());
		return "/new/jsp/weixinqxx/new_qxx";
	}

	@RequestMapping(value="weixinqxx.do",params = "method=doOrderSubmit", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String doOrderSubmit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		// 查询当前openid的个人信息
		String openid = request.getSession().getAttribute("openid").toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("openid", openid);
		paramMap.put("ip", request.getRemoteAddr());
		WeixinQxx qxx = weixinQxxService.getPersonalInfo(paramMap);
		if (qxx.getRecommend_amount() < limit_amount) {
			return "/new/jsp/weixinqxx/smallpage";
		}
		Map<String, Object> retMap = weixinQxxService.sumitOrder(paramMap);
		Integer result_code = (Integer) retMap.get("result_code");
		String result_dec = retMap.get("result_dec").toString();
		String pay_str = "";
		if (retMap.containsKey("pay_str")) {
			pay_str = retMap.get("pay_str").toString();
		}
		response.getWriter().print(
				JsonUtil.result2Json(result_code, result_dec, pay_str));
		return null;
	}
	
	@RequestMapping(value="weixinqxx.do",params = "method=doShare", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String doShare(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		// 查询当前openid的个人信息
		String openid = request.getSession().getAttribute("openid").toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("openid", openid);
		paramMap.put("ip", request.getRemoteAddr());
		WeixinQxx qxx = weixinQxxService.getPersonalInfo(paramMap);
		if (qxx.getRecommend_amount() < limit_amount) {
			return "/new/jsp/weixinqxx/smallpage";
		}
		Map<String, Object> retMap = weixinQxxService.sumitOrder(paramMap);
		Integer result_code = (Integer) retMap.get("result_code");
		String result_dec = retMap.get("result_dec").toString();
		String pay_str = "";
		if (retMap.containsKey("pay_str")) {
			pay_str = retMap.get("pay_str").toString();
		}
		response.getWriter().print(
				JsonUtil.result2Json(result_code, result_dec, pay_str));
		return null;
	}
	
	@RequestMapping(value="weixinqxx.do",params = "method=getRecommendList", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String getRecommendList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
//		request.getSession().setAttribute("openid",
//		"o-0EUwmT5XH80e46a-P");
		// 查询当前openid的个人信息
		String openid = request.getSession().getAttribute("openid").toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("openid", openid);
		ProductDouble product = weixinQxxService.getProductInfo(paramMap);
		String ret_url="/new/jsp/weixinqxx/recommendlist";
		WeixinQxx qxx=weixinQxxService.getPersonalInfo(paramMap);
		logger.info(qxx.toString());
		request.setAttribute("product_pic", product.getPicture());
		if(qxx==null){
			ret_url="redirect:/weixinqxx.do?method=doWeixinQxxNewUrl";
		}else if(qxx.getAddr()==null || qxx.getName()==null || qxx.getPhone()==null || !qxx.getState().equals("2")){
			ret_url="redirect:/weixinqxx.do?method=doWeixinQxxNewUrl";
		}
		List<WeixinQxx> list= weixinQxxService.getRecommendList(paramMap);
		request.setAttribute("list", list);
		return ret_url;
	}

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}
}

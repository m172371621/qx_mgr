package com.brilliantreform.sc.saleplugins.controller;

import com.brilliantreform.sc.order.po.MsgInfo;
import com.brilliantreform.sc.order.service.MsgInfoService;
import com.brilliantreform.sc.saleplugins.po.Pwmanager;
import com.brilliantreform.sc.saleplugins.service.PwsearchService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.JsonUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import com.brilliantreform.sc.utils.QxxUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/weighProduct")
public class PwsearchCtrl {
	private static Logger logger = Logger.getLogger(PwsearchCtrl.class);

	@Autowired
	private PwsearchService pwsearchService;
	@Autowired
	private MsgInfoService msgInfoService;

	/**
	 * 更新 商品要称重的状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Lm
	 */
	@RequestMapping(value = "pwsearchUp.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String pwsearchUp(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LogerUtil.logRequest(request, logger, "pwsearchUp");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			String num = request.getParameter("num");
			String user_id = request.getParameter("user_id");
			String produc_tid = request.getParameter("produc_tid");
			String unit = request.getParameter("unit");
			String zongPrice = request.getParameter("zongPrice");
			int result_code = 0;
			String result_dec = "提交成功";
			// 更新用户购物车状态
			Map<String, Object> user_cartMap = new HashMap<String, Object>();
			user_cartMap.put("f_amount", Integer.parseInt(unit));
			user_cartMap.put("user_id", Integer.parseInt(user_id));
			user_cartMap.put("product_id", Integer.parseInt(produc_tid));
			pwsearchService.user_cartUp(user_cartMap);
			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, null));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		return null;
	}

	/**
	 * 转到 称重商品管理 页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pwmanager.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String pwmanager(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LogerUtil.logRequest(request, logger, "pwmanager");
		try {
			if (QxxUtil.validateSession("distri_worker_bean", request, response)) {
				return "forward:/phoneorderctrl/loginweb.do?type=1";
			}
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			return "new/jsp/saleplugins/pwmanager";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}

	/**
	 * 称重商品管理 列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pwmanagerList.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String pwmanagerList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LogerUtil.logRequest(request, logger, "pwmanagerList");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int result_code = 0;
			String result_dec = "提交成功";
			MgrUser mgruser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
			int cid = mgruser.getCommunity_id();
			List<Pwmanager> pwmanager = pwsearchService.pwmanagerList(cid);
			result_code = pwmanager.size();
			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, pwmanager));
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}

	/**
	 * 称重商品管理 列表数量
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pwmanagerListcount.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String pwmanagerListcount(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//LogerUtil.logRequest(request, logger, "pwmanagerListcount");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int result_code = 0;
			String result_dec = "提交成功";
			MgrUser mgruser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
			int cid = mgruser.getCommunity_id();
			result_code = pwsearchService.pwmanagerListcount(cid);
			int size = pwsearchService.pwmanagerList(cid).size();
			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, size));
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}

	/**
	 * 更新 称重商品 状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pwmanagerUp.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String pwmanagerUp(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LogerUtil.logRequest(request, logger, "pwsearchUp");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			String size = request.getParameter("size");
			String user_id = request.getParameter("userid");
			String produc_tid = request.getParameter("proid");
			String unit = request.getParameter("unit");
			String zongPrice = request.getParameter("zongPrice");
			int result_code = 0;
			String result_dec = "提交成功";
			// 更新用户购物车状态
			Map<String, Object> user_cartMap = new HashMap<String, Object>();
			user_cartMap.put("f_amount", Float.parseFloat(size));
			user_cartMap.put("user_id", Integer.parseInt(user_id));
			user_cartMap.put("product_id", Integer.parseInt(produc_tid));
			pwsearchService.pwmanagerUp(user_cartMap);
			//推送
			MsgInfo msginfo = new MsgInfo();
			msginfo.setMsg_info_phone(user_id+"");
			msginfo.setMsg_template_code("0000000005");
			msginfo.setMsg_info_detail("");
			msgInfoService.addMsgInfo(msginfo);

			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, null));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		return null;
	}
	
	/**
	 * 取消 称重商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pwmanagerCancel.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String pwmanagerCancel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		LogerUtil.logRequest(request, logger, "pwmanagerCancel");
		
		try {
			String user_id = request.getParameter("userid");
			String produc_tid = request.getParameter("proid");
			int result_code = 0;
			String result_dec = "取消成功";
			// 取消 用户购物车状态
			Map<String, Object> user_cartMap = new HashMap<String, Object>();
			user_cartMap.put("user_id", Integer.parseInt(user_id));
			user_cartMap.put("product_id", Integer.parseInt(produc_tid));
			pwsearchService.pwmanagerCancel(user_cartMap);
			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, null));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		return null;
	}
}
package com.brilliantreform.sc.scancodeshpping.controller;

import com.brilliantreform.sc.scancodeshpping.po.Pos_order_temp_base;
import com.brilliantreform.sc.scancodeshpping.po.Pos_order_temp_product;
import com.brilliantreform.sc.scancodeshpping.service.PostSanService;
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
@RequestMapping(value = "/postSan")
public class PostSanCtrl {
	private static Logger logger = Logger.getLogger(PostSanCtrl.class);

	@Autowired
	private PostSanService postSanService;

	@RequestMapping(value = "seachPostScanBease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String seachPostScanBease(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LogerUtil.logRequest(request, logger, "seachPostScanBease");
		try {
			if (QxxUtil.validateSession("distri_worker_bean", request, response)) {
				return "forward:/phoneorderctrl/loginweb.do?type=1";
			}
			return "new/jsp/scancodeshpping/post";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}

	@RequestMapping(value = "seachPostScanBeaseList.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String seachPostScanBeaseList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LogerUtil.logRequest(request, logger, "seachPostScanBeaseList.do");
		try {
			int result_code = 0;
			String result_dec = "ok";
			MgrUser mgruser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
			int cid = mgruser.getCommunity_id();
			Pos_order_temp_base pb = postSanService.seachPostScanBeaseList(cid);
			response.getWriter().print(JsonUtil.result2Json(true, pb));
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}
	
	@RequestMapping(value = "seachPostScanproductList.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String seachPostScanproductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LogerUtil.logRequest(request, logger, "seachPostScanproductList.do");
		try {
			response.setCharacterEncoding("UTF-8");
			int result_code = 0;
			String result_dec = "ok";
			int pos_baseid = Integer.parseInt(request.getParameter("pos_baseid"));
			MgrUser mgruser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
			int cid = mgruser.getCommunity_id();
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("cid", cid);
			map.put("pos_baseid",pos_baseid);
			List<Pos_order_temp_product> pp = postSanService.seachPostScanproductList(map);
			response.getWriter().print(JsonUtil.result2Json(true, pp));
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}

}

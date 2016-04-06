package com.brilliantreform.sc.confgure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.confgure.po.Config_community;
import com.brilliantreform.sc.confgure.service.Config_communityService;
import com.brilliantreform.sc.product.controller.ProductCtrl;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.LogerUtil;

/**
 * 配置项管理ctrl
 * 
 * @author Lm
 *
 */
@Controller
@RequestMapping("config_communityctrl.do")
public class Config_communityCtrl {
	private static Logger logger = Logger.getLogger(ProductCtrl.class);

	private static int size = 10;

	@Autowired
	private Config_communityService communityService;

	/**
	 * 配置项列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getConfig_communityList", method = { RequestMethod.POST, RequestMethod.GET })
	public String getConfig_communityList(HttpServletRequest request) {
		LogerUtil.logRequest(request, logger, "getConfig_communityList");
		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int cid = c.getCommunity_id();
			Map param = CommonUtil.getParameterMap(request);
			Integer pageIndex = CommonUtil.safeToInteger(param.get("pageIndex"), 1);
			int begin = (pageIndex - 1) * size;
			Map map = new HashMap();
			map.put("community_id", cid);
			map.put("begin", begin);
			map.put("size", size);
			map.put("config_id", param.get("config_id"));
			List<Config_community> config_communityList = communityService.getConfig_communityList(map);
			int count = communityService.getConfig_communityCount(cid);

			if (count == 0) {
				pageIndex = 0;
			}
			request.setAttribute("config_communityList", config_communityList);
			request.setAttribute("config_id", param.get("config_id"));
			request.setAttribute("pageCount", CommonUtil.getPageCount(count, size));
			request.setAttribute("pageIndex", pageIndex);
			return "jsp/confgure/confgure";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
	}

	/**
	 * 配置项编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=config_communityEdit", method = { RequestMethod.POST, RequestMethod.GET })
	public String config_communityEdit(HttpServletRequest request) {
		LogerUtil.logRequest(request, logger, "config_communityEdit");
		try {
			String config_id = null;
			if (null != request.getParameter("config_id"))
				config_id = new String(request.getParameter("config_id").getBytes("iso-8859-1"), "UTF-8");
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int community_id = c.getCommunity_id();
			// int community_id =
			// CommonUtil.safeToInteger(request.getParameter("community_id"),
			// null);
			Map map = new HashMap();
			if (config_id != null) {
				map.put("config_id", config_id);
				map.put("community_id", community_id);
				Config_community list = communityService.selConfig_community(map);
				if (list != null)
					request.setAttribute("list", list);
			} else {
				return "/jsp/confgure/confgure_edit";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
		return "/jsp/confgure/confgure_edit";
	}

	/**
	 * 更新 插入配置
	 * 
	 * @param request
	 * @param response
	 * @param community
	 * @return
	 */
	@RequestMapping(params = "method=config_CommunityUpdata", method = { RequestMethod.POST, RequestMethod.GET })
	public String config_CommunityUpdata(HttpServletRequest request, HttpServletResponse response, Config_community community) {
		LogerUtil.logRequest(request, logger, "config_CommunityUpdata");
		try {
			String config_id2 = request.getParameter("config_id2");
			if (community != null) {
				if (config_id2 == "") {
					communityService.config_CommunityInsert(community);
				}
				communityService.config_CommunityUpdata(community);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
		return "redirect:/config_communityctrl.do?method=getConfig_communityList";
	}

	/**
	 * 删除配置项
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=config_CommunityDel", method = { RequestMethod.POST, RequestMethod.GET })
	public String config_CommunityDel(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "config_CommunityDel");
		try {
			String config_id = CommonUtil.safeToString(request.getParameter("config_id"), null);
			int community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
			Map map = new HashMap();
			map.put("config_id", config_id);
			map.put("community_id", community_id);
			int num = communityService.config_CommunityDel(map);
			response.getWriter().print(num);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
		return null;
	}
}
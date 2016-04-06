package com.brilliantreform.sc.community.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.po.TreeNode;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.JsonUtil;

@Controller
@RequestMapping("communityCtrl.do")
public class communityCtrl {

	private static Logger logger = Logger.getLogger(communityCtrl.class);
	@Autowired
	private CommunityService communityService;

	/**
	 * 获取当前登录用户的组织机构树
	 * 
	 * @param request
	 * @param response
	 * @return JSON数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getOrgList", method = { RequestMethod.POST })
	public void getOrgList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("method=getOrgList");
		int result_code = 0;
		String result_dec = "OK";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 从session中获取用户数据
			MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
			map.put("user_id", user.getUser_id());
			// 获取组织架构
            Boolean user_isAdmin = (Boolean)request.getSession().getAttribute("user_isAdmin");
            List<TreeNode> list_node = user_isAdmin ? communityService.getAdminOrgList() : communityService.getOrgList(map);

			response.getWriter().print(
					JsonUtil.result2Json(result_code, result_dec, list_node));
		} catch (Exception e) {
			logger.error("method=getOrgList" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
	}
	
	/**
	 * 获取经营组织详情
	 * 
	 * @param request
	 * @param response
	 * @return JSON数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=getOrgDetail", method = { RequestMethod.POST })
	public void getOrgDetail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("method=getOrgDetail");
		int result_code = 0;
		String result_dec = "OK";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 从session中获取用户数据
			map.put("community_id", Integer.parseInt(request.getParameter("community_id")) );
			// 获取组织架构
			Community  com = communityService.getOrgDetail(map);
			response.getWriter().print(
					JsonUtil.result2Json(result_code, result_dec, com));
		} catch (Exception e) {
			logger.error("method=getOrgDetail" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
	}
	
	@RequestMapping(params = "method=orgConfig", method = { RequestMethod.POST,RequestMethod.GET })
	public String orgConfig(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("method=getOrgList");
		return "new/jsp/community/org_config";
	}
	
	/**
	 * 添加经营组织
	 * 
	 * @param request
	 * @param response
	 * @return JSON数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=insertCommunity", method = { RequestMethod.POST })
	public void insertCommunity(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("method=insertCommunity");
		int result_code = 0;
		String result_dec = "OK";
		try {
			JSONObject json=JSONObject.fromObject(request.getParameter("retObj"));
			Community com=(Community) JSONObject.toBean(json, Community.class);
//			com.setCommunity_name(request.getParameter("community_name"));
//			com.setCommunity_addr(request.getParameter("community_addr"));
//			com.setOrg_info_phone(request.getParameter("org_info_phone"));
//			com.setOrg_info_person(request.getParameter("org_info_person"));
//			com.setOrg_info_pid(Integer.parseInt(request.getParameter("org_info_pid")) );
//			com.setOrg_info_type(Integer.parseInt(request.getParameter("org_info_type")));
			communityService.insertCommunity(com);
			// 获取组织架构
			response.getWriter().print(
					JsonUtil.result2Json(result_code, result_dec, com));
		} catch (Exception e) {
			logger.error("method=getOrgDetail" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
	}
	
	/**
	 * 添加经营组织
	 * 
	 * @param request
	 * @param response
	 * @return JSON数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=updateCommunity", method = { RequestMethod.POST })
	public void updateCommunity(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("method=updateCommunity");
		int result_code = 0;
		String result_dec = "OK";
		try {
			JSONObject json=JSONObject.fromObject(request.getParameter("retObj"));
			Community com=(Community) JSONObject.toBean(json, Community.class);
//			com.setCommunity_name(request.getParameter("community_name"));
//			com.setCommunity_addr(request.getParameter("community_addr"));
//			com.setOrg_info_phone(request.getParameter("org_info_phone"));
//			com.setOrg_info_person(request.getParameter("org_info_person"));
//			com.setOrg_info_pid(Integer.parseInt(request.getParameter("org_info_pid")) );
//			com.setOrg_info_type(Integer.parseInt(request.getParameter("org_info_type")));
			communityService.updateCommunity(com);
			// 获取组织架构
			response.getWriter().print(
					JsonUtil.result2Json(result_code, result_dec, com));
		} catch (Exception e) {
			logger.error("method=getOrgDetail" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
	}
}

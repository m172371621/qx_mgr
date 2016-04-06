package com.brilliantreform.sc.community.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community_register;
import com.brilliantreform.sc.community.service.ComunityRegisterService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.JsonUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("communityRegisterCtrl.do")
public class CommunityRegisterCtrl {
	
	private static Logger logger = Logger.getLogger(CommunityRegisterCtrl.class);
	
	@Autowired
	private ComunityRegisterService comunityRegisterService;

	@RequestMapping(params = "method=orgRegister", method = { RequestMethod.POST,RequestMethod.GET })
	public String orgConfig(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("method=orgRegister");
		return "new/jsp/community/orgRegister";
	}
	
	@RequestMapping(params = "method=registerList", method = { RequestMethod.POST,RequestMethod.GET })
	public String registerList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("method=registerList");
		return "new/jsp/community/registerList";
	}
	
	/**
	 * 加盟申请
	 * 
	 * @param request
	 * @param response
	 * @return JSON数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=register", method = { RequestMethod.POST })
	public String insertCommunity(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("method=register");
        String msg = "申请失败！";
		try {
			String community_person_name=request.getParameter("community_person_name");
			String community_person_phone=request.getParameter("community_person_phone");
			String community_person_addr=request.getParameter("community_person_addr");
			Community_register register=new Community_register();
			register.setCommunity_person_name(community_person_name);
			register.setCommunity_person_phone(community_person_phone);
			register.setCommunity_person_addr(community_person_addr);
			register.setCommunity_addr(community_person_addr);
			comunityRegisterService.register(register);
			// 获取组织架构
			//response.getWriter().print(
			//		JsonUtil.result2Json(result_code, result_dec, register));
            msg = "申请成功！";
		} catch (Exception e) {
			logger.error("method=getOrgDetail" + e.getMessage());
			//response.getWriter().print(JsonUtil.result2Json(false, null));
		}
        request.setAttribute("msg", msg);
		return "/new/jsp/msg";
	}
	
	
	/**
	 * 查询申请列表
	 * 
	 * @param request
	 * @param response
	 * @return JSON数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=queryRegisterList", method = { RequestMethod.POST })
	public void queryRegisterList(String dtGridPager, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("method=queryRegisterList");
		int result_code = 0;
		String result_dec = "OK";
		try {
			Pager pager = new Pager();
			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));
			Map param = GridUtil.parseGridPager(pager);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("start", param.get("begin"));
            map.put("size", param.get("size"));
            if(param.containsKey("community_register_status")){
            	map.put("community_register_status", param.get("community_register_status"));
            }
            Integer count=comunityRegisterService.queryRegisterCount(map);
			List list= comunityRegisterService.queryRegisterList(map);
			pager = GridUtil.setPagerResult(pager, list, count);
			// 获取组织架构
			CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
		} catch (Exception e) {
			logger.error("method=queryRegisterList" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
	}
	
	/**
	 * 查询申请总数
	 * 
	 * @param request
	 * @param response
	 * @return JSON数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=queryRegisterCount", method = { RequestMethod.POST })
	public void queryRegisterCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("method=queryRegisterCount");
		int result_code = 0;
		String result_dec = "OK";
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			Integer count= comunityRegisterService.queryRegisterCount(map);
			
			// 获取组织架构
			response.getWriter().print(
					JsonUtil.result2Json(result_code, result_dec, count));
		} catch (Exception e) {
			logger.error("method=queryRegisterCount" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
	}
	
	
	/**
	 * 审核
	 * 
	 * @param request
	 * @param response
	 * @return JSON数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=auditRegister", method = { RequestMethod.POST })
	public void auditRegisterCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("method=auditRegisterCount");
		int result_code = 0;
		String result_dec = "审核成功！";
		try {
			JSONObject json=JSONObject.fromObject(request.getParameter("retObj"));
			Community_register register=(Community_register) JSONObject.toBean(json, Community_register.class);
			Integer count= comunityRegisterService.auditRegister(register);
			// 获取组织架构
			response.getWriter().print(
					JsonUtil.result2Json(result_code, result_dec, count));
		} catch (Exception e) {
			logger.error("method=auditRegisterCount" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
	}
	
	/**
	 * 查询申请明细
	 * 
	 * @param request
	 * @param response
	 * @return JSON数据
	 * @throws IOException
	 */
	@RequestMapping(params = "method=queryRegisterDetail", method = { RequestMethod.POST })
	public void queryRegisterDetail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("method=queryRegisterDetail");
		int result_code = 0;
		String result_dec = "OK";
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			Community_register register= comunityRegisterService.queryRegisterDetail(map);
			
			// 获取组织架构
			response.getWriter().print(
					JsonUtil.result2Json(result_code, result_dec, register));
		} catch (Exception e) {
			logger.error("method=queryRegisterDetail" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
	}
}

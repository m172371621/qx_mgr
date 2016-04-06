package com.brilliantreform.sc.active.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.active.po.SignPrize;
import com.brilliantreform.sc.active.service.ActiveService;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.LogerUtil;

@Controller
@RequestMapping("active.do")
public class ActiveCtrl {

	private static Logger logger = Logger.getLogger(ActiveCtrl.class);

	@Autowired
	private ActiveService activeService;
	
	private static int size = 20;


	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=list", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list");

		try {

			String isSet = CommonUtil.isNull(request
					.getParameter("isSet"));

			if (isSet != null && "1".equals(isSet)) {
				String[] ids = request.getParameterValues("ids");

				SignPrize sp = new SignPrize();
				sp.setPrize_url("images/get.png");
				sp.setStatus(2);
				for (String id : ids) {
					sp.setSign_id(Integer.parseInt(id));
					this.activeService.updateSignPrize(sp);
				}

				request.setAttribute("SUCCESS", 1);
			}

			String name = CommonUtil.isNull(request
					.getParameter("name"));
			String phone = CommonUtil.isNull(request
					.getParameter("phone"));
			String stat = CommonUtil.isNull(request
					.getParameter("stat"));
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));

			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			Integer i_stat = null;
			if (stat != null && !"0".equals(stat)) {
				i_stat = Integer.parseInt(stat);
			}

			Map searchBean = new HashMap();
			searchBean.put("name", name);
			searchBean.put("phone", phone);
			searchBean.put("stat", i_stat);

			Community c =(Community)request.getSession().getAttribute("selectCommunity");
			int cid = c.getCommunity_id();
			
			int count = activeService.countSignPrize(name, phone, i_stat,cid);
			List list = activeService.getSignPrize(name, phone, i_stat, cid, begin, size);

			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);			
			
			return "jsp/other/active_sign";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

}

package com.brilliantreform.sc.statistics.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.statistics.service.StatisticsService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.LogerUtil;

@Controller
@RequestMapping("statistics.do")
public class StatisticsCtrl {

	private static Logger logger = Logger.getLogger(StatisticsCtrl.class);
	
	@Autowired
	private StatisticsService statisticsService;

	@SuppressWarnings({"unchecked" })
	@RequestMapping(params = "method=countRegist", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String searchProduct(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "countRegist");

		try {

			String start_time = CommonUtil.isNull(request.getParameter("start_time"));			
			String end_time = CommonUtil.isNull(request.getParameter("end_time"));
			String cid = CommonUtil.isNull(request.getParameter("cid"));

			int i_cid = cid==null ?  0 : Integer.parseInt(cid);
	
			Map smap = new HashMap();		
			smap.put("cid", i_cid);
			smap.put("end_time", end_time);
			smap.put("start_time", start_time);

			if(end_time!=null) end_time = end_time + " 23:59:59";
			if(start_time!=null) start_time = start_time + " 00:00:00";
			
			List<Map> list = this.statisticsService.countRegist(start_time, end_time, i_cid == 0 ? null : i_cid);
			
			request.setAttribute("list", list);
			request.setAttribute("smap", smap);

			return "jsp/statistics/regist_count";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}
}


package com.brilliantreform.sc.alipay.controller;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.alipay.po.AliPayLogBean;
import com.brilliantreform.sc.alipay.service.AliPayLogService;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.LogerUtil;

@Controller
@RequestMapping(value = "alipayLogCtrl.do")
public class AliPayLogCtrl {

	private static Logger logger = Logger.getLogger(AliPayLogCtrl.class);
	private static int size = 20;

	@Autowired
	private AliPayLogService alipayLogService;

	/**
	 * 支付宝支付记录
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=alipaylog_list", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String alipaylist(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "alipaylist");

		try {

			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();
			String order_serial = CommonUtil.isNull(request
					.getParameter("order_serial"));
			String user = CommonUtil.isNull(request.getParameter("user"));

			Date d = new Date();
			java.text.DateFormat format1 = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			String time_from = CommonUtil.isNull(request
					.getParameter("time_from")) == null ? format1.format(d)
					: CommonUtil.isNull(request.getParameter("time_from"));
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);
			gc.add(5, 1);
			String time_to = CommonUtil.isNull(request.getParameter("time_to")) == null ? format1
					.format(gc.getTime())
					: CommonUtil.isNull(request.getParameter("time_to"));
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));

			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			Map searchBean = new HashMap();

			searchBean.put("cid", cid);
			searchBean.put("order_serial", order_serial);
			searchBean.put("user", user);
			searchBean.put("start", begin);
			searchBean.put("size", size);

			searchBean.put("time_from", time_from);
			searchBean.put("time_to", time_to);

			int count = alipayLogService.getAliPayLogCount(searchBean);
			List<AliPayLogBean> list = alipayLogService
					.getAliPayLogList(searchBean);
			for (AliPayLogBean log : list) {
				JSONObject json = JSONObject.fromObject(log.getRetdata());
				if (json.containsKey("total_fee"))
					log.setPay_price(json.get("total_fee").toString());
			}
			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/order/alipay_paylog_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}
	}
}
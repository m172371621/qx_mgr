package com.brilliantreform.sc.weixin.controller;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brilliantreform.sc.alipay.po.AliPayLogBean;
import com.brilliantreform.sc.alipay.service.AliPayLogService;
import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.MathUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import com.brilliantreform.sc.weixin.po.WeixinPayLogBean;
import com.brilliantreform.sc.weixin.service.PayLogService;
import com.brilliantreform.sc.weixin.util.XmlUtil;

@Controller
@RequestMapping(value = "payLogCtrl.do")
public class PayLogCtrl {

	private static Logger logger = Logger.getLogger(PayLogCtrl.class);
	private static int size = 20;

	@Autowired
	private PayLogService payLogService;
	@Autowired
	private AliPayLogService alipayLogService;

	/**
	 * 获取微信支付记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=list_weixinpaylog", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list_weixinpaylog");

		try {

			 Community c = (Community) request.getSession().getAttribute(
			 "selectCommunity");
			 int cid = c.getCommunity_id();
			String order_serial = CommonUtil.isNull(request
					.getParameter("order_serial"));
			String user = CommonUtil.isNull(request.getParameter("user"));

			Date d = new Date();
			java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String time_from = CommonUtil.isNull(request
					.getParameter("time_from"))==null?format1.format(d):CommonUtil.isNull(request
							.getParameter("time_from"));
		    GregorianCalendar gc=new GregorianCalendar();
		    gc.setTime(d); 
		    gc.add(5, 1);
			String time_to = CommonUtil.isNull(request
					.getParameter("time_to"))==null?format1.format(gc.getTime()):CommonUtil.isNull(request
							.getParameter("time_to"));
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

			int count = payLogService.getWeixinPayLogCount(searchBean);
			List<WeixinPayLogBean> list = payLogService
					.getWeixinPayLogList(searchBean);
			for(WeixinPayLogBean log:list){
				Map<String,Object> map=XmlUtil.parseXml(log.getRetdata());
				if(map.containsKey("total_fee")) log.setPay_price(map.get("total_fee").toString());
			}
			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/order/weixin_paylog_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}
	
	/**
	 * 支付宝支付记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=alipaylist", method = {
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
			java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String time_from = CommonUtil.isNull(request
					.getParameter("time_from"))==null?format1.format(d):CommonUtil.isNull(request
							.getParameter("time_from"));
		    GregorianCalendar gc=new GregorianCalendar();
		    gc.setTime(d); 
		    gc.add(5, 1);
			String time_to = CommonUtil.isNull(request
					.getParameter("time_to"))==null?format1.format(gc.getTime()):CommonUtil.isNull(request
							.getParameter("time_to"));
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

			int count = payLogService.getAliPayLogCount(searchBean);
			List<WeixinPayLogBean> list = payLogService
					.getAliPayLogList(searchBean);
			for(WeixinPayLogBean log:list){
				JSONObject json=JSONObject.fromObject(log.getRetdata());
				if(json.containsKey("total_fee")) log.setPay_price(json.get("total_fee").toString());
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

	@RequestMapping(params = "method=showListAlipayLogPage")
	public String showListAlipayLogPage(HttpServletRequest request) {
		return "/new/jsp/order/listAlipayLog";
	}


	@RequestMapping(params = "method=listAlipayLog")
	public void listAlipayLog(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, logger, "listAlipayLog");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
			CommonUtil.parseParamCommunityId(request, param);

			Date time_to = CommonUtil.safeToDate(param.get("time_to"), "yyyy-MM-dd");
			if(time_to != null) {
				param.put("time_to", CommonUtil.formatDate(DateUtils.addDays(time_to, 1), "yyyy-MM-dd"));
			}

            List<Map> list = alipayLogService.searchAlipayLog(param);
            int count = alipayLogService.searchAlipayLogCount(param);

			pager = GridUtil.setPagerResult(pager, list, count);

		} catch (Exception e) {
			logger.error("listAlipayLog error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());

	}

	@RequestMapping(params = "method=showListWxpayLogPage")
	public String showListWxpayLogPage(HttpServletRequest request) {
		return "/new/jsp/order/listWxpayLog";
	}


	@RequestMapping(params = "method=listWxpayLog")
	public void listWxpayLog(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, logger, "listWxpayLog");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

			Date time_to = CommonUtil.safeToDate(param.get("time_to"), "yyyy-MM-dd");
			if(time_to != null) {
				param.put("time_to", CommonUtil.formatDate(DateUtils.addDays(time_to, 1), "yyyy-MM-dd"));
			}

            List<Map> list = payLogService.searchWxPayLog(param);
            int count = payLogService.searchWxPayLogCount(param);

			for(Map log : list){
				Double total_fee = CommonUtil.safeToDouble(log.get("total_fee"), null);
				if(total_fee != null) {
                    log.put("total_fee", MathUtil.div(total_fee, 100));
				}
			}

			pager = GridUtil.setPagerResult(pager, list, count);

		} catch (Exception e) {
			logger.error("listWxpayLog error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());

	}
}

package com.brilliantreform.sc.order.controller;

import com.alibaba.fastjson.JSON;
import com.brilliantreform.sc.activity.po.UserCoupon;
import com.brilliantreform.sc.activity.service.CouponService;
import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.order.enumerate.DeliveryStatusEnum;
import com.brilliantreform.sc.order.po.*;
import com.brilliantreform.sc.order.service.MsgInfoService;
import com.brilliantreform.sc.order.service.OrderService;
import com.brilliantreform.sc.order.service.OrderStatusLogService;
import com.brilliantreform.sc.phoneOrder.service.PhoneOrderService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("order.do")
public class OrderCtrl {

	private static Logger logger = Logger.getLogger(OrderCtrl.class);

	private static int size = 20;

	@Autowired
	private OrderService orderService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private OrderStatusLogService orderStatusLogService;
	@Autowired
	private MsgInfoService msgInfoService;
    @Autowired
    private PhoneOrderService phoneOrderService;
    @Autowired
    private CouponService couponService;
	@Autowired
	private UserService userService;

	@RequestMapping(params = "method=search", method = { RequestMethod.POST, RequestMethod.GET })
	public String searchOrder(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "searchOrder");

		try {

			String order_serial = CommonUtil.isNull(request.getParameter("order_serial"));
			String user_name = CommonUtil.isNull(request.getParameter("user_name"));
			String user_phone = CommonUtil.isNull(request.getParameter("user_phone"));
			String product_name = CommonUtil.isNull(request.getParameter("product_name"));
			String order_status = CommonUtil.isNull(request.getParameter("order_status"));
			String pay_type = CommonUtil.isNull(request.getParameter("pay_type"));
			String cid = CommonUtil.isNull(request.getParameter("cid"));
			String sid = CommonUtil.isNull(request.getParameter("sid"));
			String start_time = CommonUtil.isNull(request.getParameter("start_time"));
			String end_time = CommonUtil.isNull(request.getParameter("end_time"));
			String pickup_start_time = CommonUtil.isNull(request.getParameter("pickup_start_time"));
			String pickup_end_time = CommonUtil.isNull(request.getParameter("pickup_end_time"));

			String del_id = CommonUtil.isNull(request.getParameter("del_id"));

			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));

			if (del_id != null) {
				this.orderService.deleteOrder(Integer.parseInt(del_id));
			}

			if (request.getAttribute("order_status") != null) {
				order_status = (String) request.getAttribute("order_status");
			}

			Integer i_order_status = order_status == null ? 0 : Integer.parseInt(order_status);
			Integer i_pay_type = pay_type == null ? 0 : Integer.parseInt(pay_type);
			Integer i_cid = cid == null ? 0 : Integer.parseInt(cid);
			Integer i_sid = sid == null ? 0 : Integer.parseInt(sid);
			Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			OrderSearch searchBean = new OrderSearch();

			searchBean.setOrder_serial(order_serial);
			searchBean.setProduct_name(product_name);
			searchBean.setUser_name(user_name);
			searchBean.setUser_phone(user_phone);
			searchBean.setOrder_status(i_order_status == 0 ? null : i_order_status);
			searchBean.setPay_type(i_pay_type == 0 ? null : i_pay_type);
			searchBean.setCid(i_cid == 0 ? null : i_cid);
			searchBean.setSid(i_sid == 0 ? null : i_sid);
			searchBean.setStart_time(start_time);
			searchBean.setEnd_time(end_time);

			searchBean.setPickup_start_time(pickup_start_time);
			searchBean.setPickup_end_time(pickup_end_time);

			searchBean.setBegin(begin);
			searchBean.setSize(size);

			List<Order> list = new ArrayList<Order>();
			int count = 0;

			if (request.getParameter("pageIndex") != null) {
				list = this.orderService.searchOrderList(searchBean);
				count = this.orderService.countOrderList(searchBean);
				if (0 == count)
					i_pageIndex = 0;
			}

			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size : count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/order/order_search";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String editOrder(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "editOrder");
		// :product_price:100.0;type:3;order_price:100;isStatChg:36;order_status:12;editId:36;method:edit;
		try {

			String order_id = request.getParameter("viewId");
			String type = request.getParameter("type");
			request.setAttribute("msg", "");

			if (order_id == null) {

				order_id = request.getParameter("editId");

				String product_amount = request.getParameter("product_amount");
				// String product_price = request.getParameter("product_price");

				String order_status = request.getParameter("order_status");
				//String order_type = request.getParameter("order_type");
				// String delivery_type = request.getParameter("delivery_type");
				// String delivery_price =
				// request.getParameter("delivery_price");
				String pay_type = request.getParameter("pay_type");
				String isStatChg = request.getParameter("isStatChg");

				Integer i_orderStatus = Integer.parseInt(order_status);

				Double i_product_amount = product_amount == null ? null : Double.parseDouble(product_amount);
				// Float f_product_price = product_price == null ? null : Float
				// .parseFloat(product_price);
				//
				// Integer i_delivery_type = delivery_type == null ? null
				// : Integer.parseInt(delivery_type);
				Integer i_payType = pay_type == null ? null : Integer.parseInt(pay_type);

				Order order = new Order();
				order.setOrder_id(Integer.parseInt(order_id));

				order.setProduct_amount(i_product_amount);
				order.setPay_type(i_payType);
				// order.setProduct_price(f_product_price);
				//
				// order.setDelivery_type(i_delivery_type);
				//
				// if (i_delivery_type != null && i_delivery_type == 2) {
				// Float f_delivery_price = Float.parseFloat(delivery_price);
				// order.setDelivery_price(f_delivery_price);
				// order.setOrder_price(f_product_price
				// + f_delivery_price);
				// } else if (i_delivery_type != null && i_delivery_type == 1) {
				// order.setDelivery_price(0f);
				// order.setOrder_price(f_product_price);
				// }
				// //订单定价
				// else if (i_delivery_type == null)
				// {
				// order.setDelivery_price(0f);
				// order.setOrder_price(f_product_price);
				// }
				if ("3".equals(type)) {
					String order_price = request.getParameter("order_price");
					Double f_order_price = order_price == null ? null : Double.parseDouble(order_price);

					order.setOrder_price(f_order_price);

				}

				if ("1".equals(isStatChg)) {
					Order tmporder = this.orderService.getOrderById(Integer.parseInt(order_id));
					if (tmporder.getDelivery_type() == 2 && tmporder.getDistri_staus() != 1) {
						request.setAttribute("msg", "未配送完成的送货上门订单不允许修改状态！");
					} else {
						order.setOrder_status(i_orderStatus);
						orderService.updateOrder(order);
						request.setAttribute("orderEdit", 0);
					}
				}

			}

			Order order = this.orderService.getOrderById(Integer.parseInt(order_id));

			request.setAttribute("order", order);

			if ("2".equals(type))
				return "/jsp/order/order_edit2";
			if ("3".equals(type)) {
				return "/jsp/order/order_edit3";
			}
			return "/jsp/order/order_edit";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=orderDelivery", method = { RequestMethod.POST, RequestMethod.GET })
	public String orderDelivery(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "orderDelivery");

		try {

			String service_id = CommonUtil.isNull(request.getParameter("service_id"));
			String product_name = CommonUtil.isNull(request.getParameter("product_name"));
			String ispickup = CommonUtil.isNull(request.getParameter("ispickup"));
			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));
			String cid = CommonUtil.isNull(request.getParameter("cid"));

			Integer i_service_id = null;
			if (service_id != null && !"0".equals(service_id)) {
				i_service_id = Integer.parseInt(service_id);
			}

			Integer i_cid = null;
			if (cid != null && !"0".equals(cid)) {
				i_cid = Integer.parseInt(cid);
			}

			Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			Map searchBean = new HashMap();
			searchBean.put("service_id", service_id);
			searchBean.put("product_name", product_name);
			searchBean.put("cid", i_cid);

			if (ispickup != null || "1".equals(ispickup)) {
				String[] ids = request.getParameterValues("ids");
				for (String id : ids) {
					Integer pId = Integer.parseInt(id);
					this.orderService.updateOrderByProduct(pId);
				}

				request.setAttribute("pickUp", 1);
			}

			List<Map> list = new ArrayList<Map>();
			int count = 0;

			if (request.getParameter("pageIndex") != null) {
				list = this.orderService.searchProduct(begin, size, null, i_service_id, product_name, null, i_cid);
				count = this.orderService.countProduct(null, i_service_id, product_name, null, i_cid);
				if (0 == count)
					i_pageIndex = 0;
			}

			for (Map map : list) {
				Integer productId = (Integer) map.get("product_id");
				Integer orderCount = this.orderService.countOrderByProduct(productId);
				map.put("order_count", orderCount);
			}

			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size : count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/order/order_delivery";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=orderPickup", method = { RequestMethod.POST, RequestMethod.GET })
	public String orderPickup(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "orderPickup");

		try {

			String ispickup = CommonUtil.isNull(request.getParameter("ispickup"));
			if (ispickup != null || "1".equals(ispickup)) {

				String[] ids = request.getParameterValues("ids");
				for (String id : ids) {
					Integer pId = Integer.parseInt(id);
					Order tmporder = this.orderService.getOrderById(Integer.parseInt(id));
					if (tmporder.getDelivery_type() == 2 && tmporder.getDistri_staus() != 1) {
						request.setAttribute("msg", "未配送完成的送货上门订单不允许修改状态！");
					} else {
						Order order = new Order();
						order.setOrder_id(pId);
						order.setOrder_status(3);
						this.orderService.updateOrder(order);
						request.setAttribute("pickUp", 1);
					}
				}

			}

			if (request.getParameter("order_status") == null)
				request.setAttribute("order_status", "122");

			this.searchOrder(request);

			return "jsp/order/order_pickup";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=orderPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String orderPrice(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "orderPrice");

		try {

			if (request.getParameter("order_status") == null)
				request.setAttribute("order_status", "11");

			this.searchOrder(request);

			return "jsp/order/order_price";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	/**
	 * 用户更改下拉框时更换session
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=selectOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public void selectOrder(HttpServletRequest request, HttpServletResponse response) {
		// TODO 没有问题干掉
		int cid = Integer.valueOf(request.getParameter("cid"));
		request.getSession().setAttribute("selectCid", cid);
		Community community = communityService.getCommunity(cid);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(cid);
		request.getSession().setAttribute("comm_service", community);
	}

	/**
	 * 统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=countOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public String countOrder(HttpServletRequest request, HttpServletResponse response) {
		// TODO 没有问题干掉
		System.out.println("成功");
		System.out.println("成功");
		System.out.println("成功");
		System.out.println("成功");
		System.out.println("成功");
		System.out.println("成功");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String goodtype = request.getParameter("goodtype");
		String cid = request.getParameter("cid");
		System.out.println("开始时间" + start);
		System.out.println("结束时间" + end);
		System.out.println("小区" + cid);
		System.out.println("商品类别" + goodtype);

		Map<String, String> map = new HashMap<String, String>(4);
		// 判断goodtype
		if ("4".equals(goodtype)) {
			goodtype = null;
		}
		// map.put("start", "2014-3-1");
		// map.put("end", "2015-1-1");
		// map.put("pty", "1");
		// map.put("cid", "2");

		map.put("start", start);
		map.put("end", end);
		map.put("pty", goodtype);
		map.put("cid", cid);
		// map.put("cid", "2");

		List<CountOrder> countOrder = orderService.countOrder(map);
		request.getSession().setAttribute("countSumOrders", countOrder);
		// return "jsp/order/order_price";
		return "jsp/order/countSumOrders";
	}

	/**
	 * 退货订单处理 搜索显示
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(params = "method=orderReturn", method = { RequestMethod.POST, RequestMethod.GET })
	public String orderReturnList(HttpServletRequest request, HttpServletResponse response) {

		LogerUtil.logRequest(request, logger, "searchOrder");

		try {
			String order_serial = CommonUtil.isNull(request.getParameter("order_serial"));
			String community_id = CommonUtil.isNull(request.getParameter("cid"));

			//String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));

			//Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
			//int begin = (i_pageIndex - 1) * size;

			Integer cid = community_id == null ? null : Integer.parseInt(community_id);

			Order order = new Order();
			order.setOrder_serial(order_serial);

			order.setCommunity_id(cid);

			List<Order> orderList = orderService.getOrderBySerialList2(order);
			request.setAttribute("orderList", orderList);
			request.setAttribute("order_serial", order_serial);
			return "jsp/order/order_Cancel";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	// //////////////////
	/**
	 * 取消订单
	 * 
	 * @param
	 * @param
	 * @return 0 成功
	 * @throws IOException
	 */
	@RequestMapping(params = "method=orderReturnTrue", method = { RequestMethod.POST, RequestMethod.GET })
	public String cancelOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {

		/*
		 * LogerUtil.logRequest(request, logger, "OrderCtrl cancelOrder");
		 * response.setCharacterEncoding("UTF-8");
		 * 
		 * String order_serial = request.getParameter("order_serial"); // String
		 * note = new String(request.getParameter("note").getBytes( //
		 * "ISO-8859-1"), "UTF-8"); // // Map<String, Object> map =
		 * JsonUtil.getJson(request); // String order_serial = (String)
		 * map.get("order_serial");
		 * 
		 * int result_code = 0; String result_dec = "OK"; Map dataMap = null;
		 * 
		 * // 判断access_token // int userId =
		 * AccessToken.getUserId(order_serial); // if (userId == 0) { //
		 * response.getWriter().print( // JsonUtil.result2Json(999,
		 * "token not valid", null)); // return null; // } try { List<Order> old
		 * = this.orderService .getOrderBySerialList(order_serial); int userId =
		 * old.get(0).getUser_id(); if (old.get(0).getOrder_status() == 23) {
		 * result_code = -1; result_dec = "订单已取消"; response.getWriter().print(
		 * JsonUtil.result2Json(999, "token not valid", null)); return null; }
		 * 
		 * Order order = new Order(); order.setOrder_serial(order_serial);
		 * order.setOrder_status(23);
		 * this.orderService.updateOrderBySerial(order);
		 * 
		 * Map<String, Object> paramMap = new HashMap<String, Object>();
		 * paramMap.put("order_serial", order_serial); // 此处处理删除卡牌
		 * qxCardService.deleteCard(paramMap);
		 * 
		 * this.orderService.channel_order(order_serial, CommonUtil
		 * .getIp(request));
		 * 
		 * List<QxCardPayLog> list = qxCardService
		 * .getQxCardByOrder(order_serial); if (list != null && list.size() > 0)
		 * { for (QxCardPayLog qxCardPayLog : list) { if
		 * (qxCardPayLog.getStatus() == 1) { UserQxCard userQxCard = new
		 * UserQxCard(); userQxCard.setUser_id(userId);
		 * userQxCard.setQxcard_no(qxCardPayLog.getQxcard_no());
		 * userQxCard.setQxcard_balance(0 - qxCardPayLog .getPay_price());
		 * userQxCard.setQxcard_status(3);
		 * this.qxCardService.updateUserQxCard(userQxCard);
		 * 
		 * QxCardLog qxCardLog = new QxCardLog();
		 * qxCardLog.setQxcard_no(qxCardPayLog.getQxcard_no());
		 * qxCardLog.setOrder_serial(order_serial); qxCardLog.setOp_price(0 -
		 * qxCardPayLog.getPay_price()); qxCardLog.setQxcard_balance(0f);
		 * qxCardLog.setOp_type(3001); qxCardLog.setUser_id(userId);
		 * qxCardLog.setUser_ip(CommonUtil.getIp(request));
		 * qxCardLog.setUser_type(2); qxCardLog.setOp_result(0);
		 * qxCardLog.setOp_result_dec(""); qxCardLog.setOp_dec("用户取消订单");
		 * this.qxCardService.insertQxCardLog(qxCardLog);
		 * 
		 * qxCardPayLog.setStatus(2);
		 * this.qxCardService.updateQxCardPayLog(qxCardPayLog); }
		 * 
		 * } }
		 * 
		 * response.getWriter().print( JsonUtil.result2Json(result_code,
		 * result_dec, dataMap));
		 * 
		 * } catch (Exception e) { logger.error("OrderCtrl cancelOrder:" +
		 * e.getMessage());
		 * response.getWriter().print(JsonUtil.result2Json(false, null)); }
		 */

		return null;

	}

	// //////////////////

	/**
	 * 转到录入信息页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=addOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public String addOrder(HttpServletRequest request) {
		LogerUtil.logRequest(request, logger, "addOrderInfo");
		try {
			return "jsp/order/order_add";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
	}

	/**
	 * 插入录入
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = "method=addOrderInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String addOrderInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

		LogerUtil.logRequest(request, logger, "addOrderInfo");

		response.setCharacterEncoding("UTF-8");
		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int cid = c.getCommunity_id();
			// 此处从前台获取干洗等金额及备注信息
			String gPrice = CommonUtil.isNull(request.getParameter("gPrice"));
			String gRemarks = CommonUtil.isNull(request.getParameter("gRemarks"));
			String kPrice = CommonUtil.isNull(request.getParameter("kPrice"));
			String kRemarks = CommonUtil.isNull(request.getParameter("kRemarks"));
			String pPrice = CommonUtil.isNull(request.getParameter("pPrice"));
			String pRemarks = CommonUtil.isNull(request.getParameter("pRemarks"));
			String yPrice = CommonUtil.isNull(request.getParameter("yPrice"));
			String yRemarks = CommonUtil.isNull(request.getParameter("yRemarks"));

			// 设置返回标志与提示
			int result_code = 0;
			String result_dec = "提交成功！";

			// 组合数据进入bean中，插入数据
			AddInfo addInfo = new AddInfo();
			addInfo.setCleaning_price(gPrice == null ? null : Float.parseFloat(gPrice));
			addInfo.setCleaning_remarks(gRemarks == null ? null : gRemarks);
			addInfo.setOrder_price(kPrice == null ? null : Float.parseFloat(kPrice));
			addInfo.setOrder_remarks(kRemarks == null ? null : kRemarks);
			addInfo.setLeather_price(pPrice == null ? null : Float.parseFloat(pPrice));
			addInfo.setLeather_remarks(pRemarks == null ? null : pRemarks);
			addInfo.setRocking_car_price(yPrice == null ? null : Float.parseFloat(yPrice));
			addInfo.setRocking_car_remarks(yRemarks == null ? null : yRemarks);
			addInfo.setCommunity_id(cid);
			// 插入数据
			orderService.addOrder(addInfo);
			// 成功后返回json数据
			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, null));
		} catch (Exception e) {
			// 产生异常时候也返回数据，并作日志打出
			logger.error("OrderCtrl addOrderInfo:" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
		return null;
	}

	/**
	 * 跳转到 批次查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = "method=queryBatch", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryBatch(HttpServletRequest request, HttpServletResponse response) throws IOException {

		LogerUtil.logRequest(request, logger, "addOrderInfo");

		response.setCharacterEncoding("UTF-8");
		try {
			return "jsp/order/queryBatch";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
	}

	/**
	 * 批次查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = "method=queryBatchList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryBatchList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LogerUtil.logRequest(request, logger, "addOrderInfo");
		response.setCharacterEncoding("UTF-8");

		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int cid = c.getCommunity_id();
			String service_name = CommonUtil.isNull(request.getParameter("service_name"));
			if (null == service_name)
				service_name = "";
			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));
			Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;
			int queryBatchCount = 0;
			QueryBatch queryBatch = new QueryBatch();
			queryBatch.setService_name(service_name);
			queryBatch.setBegin(begin);
			queryBatch.setSize(size);
			queryBatch.setCommunity_id(cid);
			List<QueryBatch> queryBatchList = new ArrayList<QueryBatch>();
			if (request.getParameter("pageIndex") != null) {
				queryBatchList = orderService.queryBatchList(queryBatch);
				queryBatchCount = this.orderService.queryBatchCount(queryBatch);
				if (0 == queryBatchCount)
					i_pageIndex = 0;
			}
			request.setAttribute("pageCount", queryBatchCount % size == 0 ? queryBatchCount / size : queryBatchCount / size + 1);

			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("service_name", service_name);
			request.setAttribute("queryBatch", queryBatchList);
			return "jsp/order/queryBatch";
		} catch (Exception e) {
			// 产生异常时候也返回数据，并作日志打出
			logger.error("OrderCtrl addOrderInfo:" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}

		return null;
	}

	/**
	 * 中奖记录查询
	 */
	@RequestMapping(params = "method=winning", method = { RequestMethod.POST, RequestMethod.GET })
	public String getWinning(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LogerUtil.logRequest(request, logger, "getWinning");

		response.setCharacterEncoding("UTF-8");

		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int cid = c.getCommunity_id();
			String phone = CommonUtil.isNull(request.getParameter("phone"));
			String card_name = CommonUtil.isNull(request.getParameter("card_name"));
			String start_time = CommonUtil.isNull(request.getParameter("start_time"));
			String end_time = CommonUtil.isNull(request.getParameter("end_time"));
			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));
			Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;
			int count = 0;
			// 获取当天日期时间
			if (null == end_time) {
				end_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
			}
			// 获得当天7天的时间
			if (null == start_time) {
				start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("card_name", card_name);
			map.put("phone", phone);
			map.put("start_time", start_time);
			map.put("end_time", end_time);
			map.put("community_id", cid);
			map.put("begin", begin);
			map.put("size", size);
			List<WinningInfo> winningInfo = new ArrayList<WinningInfo>();
			if (request.getParameter("pageIndex") != null) {
				winningInfo = (List<WinningInfo>) orderService.getWinning(map);
				count = orderService.getWinningCount(map);
				if (0 == count)
					i_pageIndex = 0;
			}
			request.setAttribute("pageCount", count % size == 0 ? count / size : count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("winningInfo", winningInfo);
			request.setAttribute("card_name", card_name);
			request.setAttribute("phone", phone);
			request.setAttribute("start_time", start_time);
			request.setAttribute("end_time", end_time);
			return "jsp/order/winning";
		} catch (Exception e) {
			// 产生异常时候也返回数据，并作日志打出
			logger.error("OrderCtrl getWinning:" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
		return null;
	}

	@RequestMapping(params = "method=awardUpdate", method = { RequestMethod.POST, RequestMethod.GET })
	public String awardUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LogerUtil.logRequest(request, logger, "awardUpdate");
		response.setCharacterEncoding("UTF-8");

		// 设置返回标志与提示
		int result_code = 0;
		String result_dec = "提交成功！";
		Map<String, Object> orderID = new HashMap<String, Object>();
		try {
			String order_id = CommonUtil.isNull(request.getParameter("orderID"));
			String loginName = CommonUtil.isNull(request.getParameter("loginName"));
			String userid = CommonUtil.isNull(request.getParameter("userid"));
			orderID.put("awardStatus", 2);
			orderID.put("awardTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			orderID.put("operatorName", loginName);
			orderID.put("order_id", order_id);
			orderService.awardUpdate(orderID);
			//预售商品到货 卡牌
			MsgInfo msginfo = new MsgInfo();
			msginfo.setMsg_info_phone(userid+"");
			msginfo.setMsg_template_code("0000000008");
			msginfo.setMsg_info_detail("");
			msgInfoService.addMsgInfo(msginfo);
			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, null));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
		return null;
	}

	@RequestMapping(params = "method=listOrder")
	public String listOrder(HttpServletRequest request) {
		try {
			LogerUtil.logRequest(request, logger, "listOrder");

			List<Map> list = new ArrayList<Map>();
			int count = 0;

			String page = CommonUtil.safeToString(request.getParameter("page"), null);
			String order_status_array = CommonUtil.safeToString(request.getParameter("order_status_array"), null);
			//Integer pay_type_ext = CommonUtil.safeToInteger(request.getParameter("pay_type_ext"), null);
			//Integer delivery_type = CommonUtil.safeToInteger(request.getParameter("delivery_type"), null);
			Integer pageIndex = CommonUtil.safeToInteger(request.getParameter("pageIndex"), 1);
			int begin = (pageIndex - 1) * size;

			// 首先查询order_product
			Map param = CommonUtil.getParameterMap(request);
			param.put("community_id", ((Community) request.getSession().getAttribute("selectCommunity")).getCommunity_id());
			Date end_time_d = CommonUtil.safeToDate(request.getParameter("end_time"), "yyyy-MM-dd");
			if (end_time_d != null) {
				param.put("end_time", CommonUtil.formatDate(DateUtils.addDays(end_time_d, 1), "yyyy-MM-dd"));
			}
			Date pickup_end_time_d = CommonUtil.safeToDate(request.getParameter("pickup_end_time"), "yyyy-MM-dd");
			if (pickup_end_time_d != null) {
				param.put("pick_end_time", CommonUtil.formatDate(DateUtils.addDays(pickup_end_time_d, 1), "yyyy-MM-dd"));
			}

			String product_name = CommonUtil.safeToString(param.get("product_name"), null);
			Integer sid = CommonUtil.safeToInteger(param.get("sid"), null);
			List<String> orderSerialList = new ArrayList<String>();
			if (StringUtils.isNotBlank(product_name) || sid != null) {
				orderSerialList = orderService.findOrderSerial(param);
				if (orderSerialList == null || orderSerialList.size() == 0) {
					orderSerialList.add("0"); // 表示没有订单
				}
			}

			param.put("order_serial_array", orderSerialList.toArray());
			if (StringUtils.isNotBlank(order_status_array)) {
				param.put("order_status_array", order_status_array.split(","));
			}
			param.put("begin", begin);
			param.put("size", size);
			list = orderService.searchOrderBase(param);
			count = orderService.searchOrderBaseCount(param);

			Map<String, List<Map>> orderProductMap = new HashMap<String, List<Map>>();
			for (Map orderBase : list) {
				String order_serial = CommonUtil.safeToString(orderBase.get("order_serial"), null);
				if (StringUtils.isNotBlank(order_serial)) {
					List<Map> orderProductList = orderService.findOrderProductTemp(order_serial);
					if (orderProductList != null && orderProductList.size() > 0) {
						orderProductMap.put(order_serial, orderProductList);
					}
				}
			}

			request.setAttribute("orderBaseList", list);
			request.setAttribute("orderProductMap", orderProductMap);
			request.setAttribute("pageCount", CommonUtil.getPageCount(count, size));
			request.setAttribute("pageIndex", count == 0 ? 0 : pageIndex);
			request.setAttribute("queryParam", param);
			request.setAttribute("order_status_array", order_status_array);

			if (StringUtils.isNotBlank(page)) {
				return "jsp/order/" + page;
			} else {
				return "jsp/order/listOrder";
			}
		} catch (Exception e) {
			logger.error("listOrder error", e);
			return "/jsp/error";
		}
	}

	@RequestMapping(params = "method=showOrder")
	public String showOrder(HttpServletRequest request) {
		try {
			LogerUtil.logRequest(request, logger, "listOrder");

			String order_serial = CommonUtil.safeToString(request.getParameter("order_serial"), null);
			if (StringUtils.isNotBlank(order_serial)) {
				Map order = orderService.getOrderBaseById(order_serial);

				List<Map> productList = orderService.findOrderProductTemp(order_serial);

				request.setAttribute("order", order);
				request.setAttribute("list", productList);
			}
		} catch (Exception e) {
			logger.error("showOrder error", e);
			return "/jsp/error";
		}
		return "jsp/order/editOrder";
	}

	@RequestMapping(params = "method=pickUpOrder")
	public void pickUpOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "error";
		try {
			LogerUtil.logRequest(request, logger, "pickUpOrder");

			String ids = CommonUtil.safeToString(request.getParameter("ids"), null);

			if (StringUtils.isNotBlank(ids)) {
				String[] order_serial_array = ids.split(",");
				for (String order_serial : order_serial_array) {
					orderService.pickUpOrder(order_serial);
				}
				result = "success";
			}
		} catch (Exception e) {
			logger.error("pickUpOrder error", e);
		}
		response.getWriter().print(result);
	}

	@RequestMapping(params = "method=updatePreSaleOrder")
	public void updatePreSaleOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "error";
		try {
			LogerUtil.logRequest(request, logger, "updatePreSaleOrder");

			String ids = CommonUtil.safeToString(request.getParameter("ids"), null);

			MsgInfo msginfo = new MsgInfo();
			if (StringUtils.isNotBlank(ids)) {
				String[] order_serial_array = ids.split(",");
				for (String order_serial : order_serial_array) {
					orderService.updatePreSaleOrder(order_serial);
					// 预算商品到货
					int user_id = orderService.selOrderProductUserid(order_serial);
					msginfo.setMsg_info_phone(user_id + "");
					msginfo.setMsg_template_code("0000000006");
					msginfo.setMsg_info_detail("");
					msgInfoService.addMsgInfo(msginfo);
				}
				result = "success";
			}
		} catch (Exception e) {
			logger.error("updatePreSaleOrder error", e);
		}
		response.getWriter().print(result);
	}

	@RequestMapping(params = "method=updateOrderStatus")
	public void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "error";
		try {
			String order_serial = CommonUtil.safeToString(request.getParameter("order_serial"), null);
			Integer new_order_status = CommonUtil.safeToInteger(request.getParameter("new_order_status"), null);

			if (StringUtils.isNotBlank(order_serial) && new_order_status != null) {
				Map order = orderService.getOrderBaseById(order_serial);
				if (order != null) {
					Integer order_status = CommonUtil.safeToInteger(order.get("order_status"), null);
                    if(order_status != null) {
                        if(order_status.intValue() == 3) {
                            //提货操作
                            orderService.pickUpOrder(order_serial);
                        } else {
                            orderService.updateOrderBaseStatus(new_order_status, order_serial, null);

                            Order orderProduct = new Order();
                            orderProduct.setOrder_serial(order_serial);
                            orderProduct.setOrder_status(new_order_status);
                            orderService.updateOrderBySerial(orderProduct);
                        }
                        OrderStatusLog log = new OrderStatusLog();
                        log.setUser_id(((MgrUser) request.getSession().getAttribute("user_info")).getUser_id());
                        log.setCreate_time(new Date());
                        log.setNew_status(new_order_status);
                        log.setOld_status(order_status);
                        log.setOrder_serial(order_serial);
                        orderStatusLogService.saveOrderStatusLog(log);

                        result = "success";
                    }
				}
			}
		} catch (Exception e) {
			logger.error("updateOrderStatus error", e);
		}
		response.getWriter().print(result);
	}

	@RequestMapping(params = "method=saveBatchStock")
	public String saveBatchStock(HttpServletRequest request) {
		try {
			LogerUtil.logRequest(request, logger, "saveBatchStock");

			String[] batch_serial_array = request.getParameterValues("batch_serial");
			String[] product_id_array = request.getParameterValues("product_id");
			String[] old_stock_num_array = request.getParameterValues("old_stock_num");
			String[] stock_num_array = request.getParameterValues("stock_num");

			if (batch_serial_array != null && batch_serial_array.length > 0 && old_stock_num_array != null && old_stock_num_array.length > 0 && stock_num_array != null && stock_num_array.length > 0
					&& product_id_array != null && product_id_array.length > 0) {
				for (int i = 0; i < batch_serial_array.length; i++) {
					String batch_serial = batch_serial_array[i];
					Integer product_id = CommonUtil.safeToInteger(product_id_array[i], null);
					Integer old_stock_num = CommonUtil.safeToInteger(old_stock_num_array[i], null);
					Integer stock_num = CommonUtil.safeToInteger(stock_num_array[i], null);

					if (StringUtils.isNotBlank(batch_serial) && old_stock_num != null && stock_num != null && product_id != null) {
						if (old_stock_num.intValue() != stock_num.intValue()) {
							Map param = new HashMap();
							param.put("batch_serial", batch_serial);
							param.put("product_id", product_id);
							param.put("order_current_sum", stock_num - old_stock_num);
							param.put("real_stock_sum", stock_num - old_stock_num);
							// 更新批次表
							orderService.update_product_batch_stock(param);
							// 更新商品实时库存表
							orderService.update_product_real_stock(param);
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("saveBatchStock error", e);
			return "/jsp/error";
		}
		return "redirect:/order.do?method=queryBatchList";
	}

	@RequestMapping(params = "method=showListOrderPage")
	public String showListOrderPage(HttpServletRequest request) {
        MgrUser mgrUser = (MgrUser)request.getSession().getAttribute("user_info");
        Boolean user_isAdmin = (Boolean)request.getSession().getAttribute("user_isAdmin");
        List<Map> communityList = new ArrayList<Map>();
        if(user_isAdmin != null && user_isAdmin) {
            communityList = communityService.findSecondAdminCommunity();
        } else {
            communityList = communityService.findSecondUserCommunity(mgrUser.getUser_id());
        }
        request.setAttribute("communityList", communityList);
		return "/new/jsp/order/listOrder";
	}

	@RequestMapping(params = "method=showListPickUpOrderPage")
	public String showListPickUpOrderPage(HttpServletRequest request) {
        MgrUser mgrUser = (MgrUser)request.getSession().getAttribute("user_info");
        Boolean user_isAdmin = (Boolean)request.getSession().getAttribute("user_isAdmin");
        List<Map> communityList = new ArrayList<Map>();
        if(user_isAdmin != null && user_isAdmin) {
            communityList = communityService.findSecondAdminCommunity();
        } else {
            communityList = communityService.findSecondUserCommunity(mgrUser.getUser_id());
        }
        request.setAttribute("communityList", communityList);
		return "/new/jsp/order/listPickUpOrder";
	}

	@RequestMapping(params = "method=showListPreSaleOrderPage")
	public String showListPreSaleOrderPage(HttpServletRequest request) {
        MgrUser mgrUser = (MgrUser)request.getSession().getAttribute("user_info");
        Boolean user_isAdmin = (Boolean)request.getSession().getAttribute("user_isAdmin");
        List<Map> communityList = new ArrayList<Map>();
        if(user_isAdmin != null && user_isAdmin) {
            communityList = communityService.findSecondAdminCommunity();
        } else {
            communityList = communityService.findSecondUserCommunity(mgrUser.getUser_id());
        }
        request.setAttribute("communityList", communityList);
		return "/new/jsp/order/listPreSaleOrder";
	}

	@RequestMapping(params = "method=listOrderV4")
	public void listOrderV4(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		List<Map> list = new ArrayList<Map>();
		try {
			LogerUtil.logRequest(request, logger, "listOrderV4");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
			CommonUtil.parseParamCommunityId(request, param);
			//处理开始结束时间
			Date end_time_d = CommonUtil.safeToDate(param.get("end_time"), "yyyy-MM-dd");
			if(end_time_d != null) {
				param.put("end_time", CommonUtil.formatDate(DateUtils.addDays(end_time_d, 1), "yyyy-MM-dd"));
			}
			Date pickup_end_time_d = CommonUtil.safeToDate(param.get("pickup_end_time"), "yyyy-MM-dd");
			if(pickup_end_time_d != null) {
				param.put("pick_end_time", CommonUtil.formatDate(DateUtils.addDays(pickup_end_time_d, 1), "yyyy-MM-dd"));
			}
			String order_status_array = CommonUtil.safeToString(param.get("order_status_array"), null);
			if(StringUtils.isNotBlank(order_status_array)) {
				param.put("order_status_array", order_status_array.split(","));
			}

			String keyword = CommonUtil.safeToString(param.get("keyword"), null);
			String product_name = CommonUtil.safeToString(param.get("product_name"), null);
			Integer sid = CommonUtil.safeToInteger(param.get("sid"), null);
			List<String> orderSerialList = new ArrayList<String>();
			if(StringUtils.isNotBlank(keyword) || StringUtils.isNotBlank(product_name) || sid != null) {
				orderSerialList = orderService.findOrderSerial(param);
				if(orderSerialList == null || orderSerialList.size() == 0) {
					orderSerialList.add("0");	//表示没有订单
				}
			}
			param.put("order_serial_array", orderSerialList.toArray());

			List<Map> orderList = orderService.searchOrderBase(param);
			int count = orderService.searchOrderBaseCount(param);

			for (Map orderBase : orderList) {
				String order_serial = CommonUtil.safeToString(orderBase.get("order_serial"), null);
				if (StringUtils.isNotBlank(order_serial)) {
					List<Map> orderProductList = orderService.findOrderProductTemp(order_serial);
					if (orderProductList != null && orderProductList.size() > 0) {
						Integer order_status = CommonUtil.safeToInteger(orderBase.get("order_status"), null);
						String status_name = CommonUtil.parseOrderStatus(order_status);
						orderBase.put("status_name", status_name);

						list.add(new HashMap());
						list.add(orderBase);
						list.addAll(orderProductList);
					}
				}
			}

			pager = GridUtil.setPagerResult(pager, list, count, true);

		} catch (Exception e) {
			logger.error("listSysJob error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());

	}

	@RequestMapping(params = "method=showOrderV4")
	public String showOrderV4(HttpServletRequest request) {
		try {
			LogerUtil.logRequest(request, logger, "listOrderV4");

			String order_serial = CommonUtil.safeToString(request.getParameter("order_serial"), null);
			if (StringUtils.isNotBlank(order_serial)) {
				Map order = orderService.getOrderBaseById(order_serial);

				List<Map> productList = orderService.findOrderProductTemp(order_serial);

				if(order != null) {
					String status_name = CommonUtil.parseOrderStatus(CommonUtil.safeToInteger(order.get("order_status"), null));
					order.put("status_name", status_name);

                    //解析订单优惠明细 pay_off_all  满减|首单减|随机减
                    String pay_off_text = "";
                    String pay_off_all = CommonUtil.safeToString(order.get("pay_off_all"), null);
                    if(StringUtils.isNotBlank(pay_off_all)) {
                        Integer coupon_id = CommonUtil.safeToInteger(order.get("coupon_id"), null);
                        String[] pay_off_all_array = pay_off_all.split("\\|");

                        double man_jian = CommonUtil.safeToDouble(pay_off_all_array[0], 0d);
                        double shou_dan = CommonUtil.safeToDouble(pay_off_all_array[1], 0d);
                        double sui_ji = CommonUtil.safeToDouble(pay_off_all_array[2], 0d);

                        String pay_off_text_1 = man_jian > 0 ? ("满减：" + man_jian + "|") : "";
                        String pay_off_text_2 = shou_dan > 0 ? ("首单减：" + shou_dan + "|") : "";
                        String pay_off_text_3 = sui_ji > 0 ? ("随机减：" + sui_ji + "|") : "";
                        String pay_off_text_4 = "";

                        if(coupon_id != null) {
                            UserCoupon userCoupon = couponService.getUserCouponById(coupon_id);
                            if(userCoupon != null) {
                                double hong_bao = userCoupon.getOff_price();
                                pay_off_text_4 = "红包减：" + hong_bao + "|";
                            }
                        }

                        pay_off_text = pay_off_text_1 + pay_off_text_2 + pay_off_text_3 + pay_off_text_4;
                        if(pay_off_text.endsWith("|")) {
                            pay_off_text = pay_off_text.substring(0, pay_off_text.length() - 1);
                        }

                        request.setAttribute("pay_off_text", pay_off_text);
                    }
				}
				request.setAttribute("order", order);
				request.setAttribute("list", productList);
			}
		} catch (Exception e) {
			logger.error("showOrderV4 error", e);
			return "/jsp/error";
		}
		return "/new/jsp/order/editOrder";
	}
	
	/**
	 * 获取新订单数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "method=getNewOrderCount", method = { RequestMethod.POST, RequestMethod.GET })
	public String getNewOrderCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("UTF-8");
		try {
			//Integer community_id=((Community) request.getSession().getAttribute("selectCommunity")).getCommunity_id();
			Map<String, Object> map = new HashMap<String, Object>();
            MgrUser mgrUser = (MgrUser)request.getSession().getAttribute("user_info");
            Boolean user_isAdmin = (Boolean)request.getSession().getAttribute("user_isAdmin");
            List<Map> communityList = new ArrayList<Map>();
            if(user_isAdmin != null && user_isAdmin) {
                communityList = communityService.findSecondAdminCommunity();
            } else {
                communityList = communityService.findSecondUserCommunity(mgrUser.getUser_id());
            }
            map.put("community_list", communityList);

			Integer count = orderService.getNewOrderCount(map);
			response.getWriter().print(JsonUtil.result2Json(0, "查询成功", count));
		} catch (Exception e) {
			response.getWriter().print(JsonUtil.result2Json(false, null));
			logger.error(e.getMessage());
		}
		return null;

	}

    @RequestMapping(params = "method=showListProductBatchPage")
    public String showListProductBatchPage(HttpServletRequest request) {
        return "/new/jsp/order/listProductBatch";
    }

    @RequestMapping(params = "method=searchProductBatch")
    public void searchProductBatch(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchBatchList");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            List<Map> list = orderService.searchProductBatch(param);
            int count = orderService.searchProductBatchCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            logger.error("searchBatchList error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=acceptOrder")
    public void acceptOrder(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            String order_serial = request.getParameter("order_serial");
			Integer distri_worker_id = CommonUtil.safeToInteger(request.getParameter("distri_worker_id"), null);

            if(StringUtils.isNotBlank(order_serial) && distri_worker_id != null) {
                Map order = orderService.getOrderBySerial3(order_serial);
				Integer community_id = CommonUtil.safeToInteger(order.get("community_id"), null);
                Map map = phoneOrderService.acceptOrder(distri_worker_id, community_id, order_serial);
                if(map != null) {
                    Integer p_result_code = CommonUtil.safeToInteger(map.get("p_result_code"), null);
                    if(p_result_code != null && p_result_code.intValue() == 0) {
						try {
							//发送邮件提醒给配送员
							MgrUser mgrUser = userService.getMgrUserInfo(distri_worker_id);
							if(mgrUser != null && StringUtils.isNotBlank(mgrUser.getEmail())) {
								String mail_host = SettingUtil.getSettingValue("EMAIL", "HOST");
								Integer mail_port = CommonUtil.safeToInteger(SettingUtil.getSettingValue("EMAIL", "PORT"), null);
								String mail_username = SettingUtil.getSettingValue("EMAIL", "USERNAME");
								String mail_password = SettingUtil.getSettingValue("EMAIL", "PASSWORD");

								String delivery_mail_subject = SettingUtil.getSettingValue("ORDER", "DELIVERY_MAIL_SUBJECT");
								String delivery_mail_content = SettingUtil.getSettingValue("ORDER", "DELIVERY_MAIL_CONTENT");

								if(StringUtils.isNotBlank(mail_host) && mail_port != null &&
										StringUtils.isNotBlank(mail_username) && StringUtils.isNotBlank(mail_password)) {
									HtmlEmail email = new HtmlEmail();
									email.setHostName(mail_host);
									email.setSmtpPort(mail_port);
									email.setAuthentication(mail_username, mail_password);
									email.setSSLOnConnect(true);
									email.setCharset("GBK");

									email.setFrom(mail_username);
									email.addTo(mgrUser.getEmail());

									email.setSubject(delivery_mail_subject);
									email.setHtmlMsg(delivery_mail_content);
									email.send();
								}
							}

							//接单信息推送给客户
							MsgInfo msginfo = new MsgInfo();
							msginfo.setMsg_info_phone(order.get("user_id") + "");
							msginfo.setMsg_template_code("0000000012");
							msginfo.setMsg_info_detail("");
							msgInfoService.addMsgInfo(msginfo);
						} catch (Exception e) {
							logger.error("send mail error", e);
						}

                        result.setResult(true);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("acceptOrder error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    /**
     * 显示订单配送小票
     * */
    @RequestMapping(params = "method=showOrderNotePage")
    public String showOrderNotePage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String order_serial = request.getParameter("order_serial");
            if(StringUtils.isNotBlank(order_serial)) {
                Map orderMap = orderService.getOrderBaseById(order_serial);
                if(orderMap != null && orderMap.size() > 0) {
                    List<Order> productList = orderService.findOrderProduct(order_serial);
                    if(productList != null && productList.size() > 0) {
                        request.setAttribute("order", orderMap);
                        request.setAttribute("productList", productList);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("showOrderNotePage error", e);
        }
        return "/new/jsp/order/orderNote";
    }

	@RequestMapping(params = "method=getDeliveryInfo")
	public void getDeliveryInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			String order_serial = request.getParameter("order_serial");

			if(StringUtils.isNotBlank(order_serial)) {
				Map order = orderService.getOrderBySerial3(order_serial);
				if(order != null) {
					Integer community_id = CommonUtil.safeToInteger(order.get("community_id"), null);
					String delivery_addr = CommonUtil.safeToString(order.get("delivery_addr"), "");		//收货地址
					String delivery_user = CommonUtil.safeToString(order.get("delivery_user"), "");		//收货人
					String delivery_phone = CommonUtil.safeToString(order.get("delivery_phone"), "");	//收货人电话

					List<Order> orderProductList = orderService.findOrderProduct(order_serial);

					//该门店所有的配送员
					List<Map> deliveryers = orderService.findDeliveryer(community_id);
					//获取这些配送员的待配送的订单数量
					for(Map deliveryer : deliveryers) {
						Integer distri_worker_id = CommonUtil.safeToInteger(deliveryer.get("user_id"), -1);
						deliveryer.put("count", orderService.getDeliveryCount(distri_worker_id, DeliveryStatusEnum.FINISH.getValue()));

						//获取配送员最近一次地址
						String addr = "";
						String addr_time = "";
						DeliveryAddress deliveryAddress = orderService.getDeliveryAddress(distri_worker_id);
						if(deliveryAddress != null) {
							addr = deliveryAddress.getAddress();
							addr_time = CommonUtil.formatDate(deliveryAddress.getCreateTime(), "yyyy-MM-dd HH:mm");
						}
						deliveryer.put("addr", addr);
						deliveryer.put("addr_time", addr_time);
					}

					Map map = new HashMap();
					map.put("delivery_addr", delivery_addr);
					map.put("delivery_user", delivery_user);
					map.put("delivery_phone", delivery_phone);
					map.put("orderProductList", orderProductList);
					map.put("deliveryers", deliveryers);

					CommonUtil.outToWeb(response, JSON.toJSONString(map));
				}
			}
		} catch (Exception e) {
			logger.error("getDeliveryInfo error", e);
		}
	}

	@RequestMapping(params = "method=changeDeliveryStatus")
	public void changeDeliveryStatus(HttpServletRequest request, HttpServletResponse response) {
		RtnResult result = new RtnResult(false);
		try {
			String order_serial = request.getParameter("order_serial");

			if(StringUtils.isNotBlank(order_serial)) {
				orderService.updateDeliveryStatus(order_serial, DeliveryStatusEnum.ING.getValue());
				result.setResult(true);
			}
		} catch (Exception e) {
			logger.error("changeDeliveryStatus error", e);
		}
		CommonUtil.outToWeb(response, JSON.toJSONString(result));
	}
}

package com.brilliantreform.sc.order.cxy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.order.cxy.po.CountOrder;
import com.brilliantreform.sc.order.cxy.service.OrderServiceCxy;
import com.brilliantreform.sc.service.service.SevService;

@Controller
@RequestMapping("ordercxy.do")
public class OrderCtrlCxy {

//	private static Logger logger = Logger.getLogger(OrderCtrl.class);
//
//	private static int size = 20;

	@Autowired
	private OrderServiceCxy orderService;
	@Autowired
	private SevService sevService;
	@Autowired
	private CommunityService communityService;
	
/*	
	@RequestMapping(params = "method=search", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String searchOrder(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "searchOrder");

		try {

			HttpSession session = request.getSession();

			String order_serial = CommonUtil.isNull(request
					.getParameter("order_serial"));
			String user_name = CommonUtil.isNull(request
					.getParameter("user_name"));
			String user_phone = CommonUtil.isNull(request
					.getParameter("user_phone"));
			String product_name = CommonUtil.isNull(request
					.getParameter("product_name"));
			String order_status = CommonUtil.isNull(request
					.getParameter("order_status"));
			String cid = CommonUtil.isNull(request
					.getParameter("cid"));
			String sid = CommonUtil.isNull(request
					.getParameter("sid"));
			String start_time = CommonUtil.isNull(request
					.getParameter("start_time"));
			String end_time = CommonUtil.isNull(request
					.getParameter("end_time"));
			
			String del_id = CommonUtil.isNull(request
					.getParameter("del_id"));
			
			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));

			if (del_id != null) {
				this.orderService.deleteOrder(Integer.parseInt(del_id));
			}
			
			if (request.getAttribute("order_status") != null) {
				order_status = (String) request.getAttribute("order_status");
			}

			Integer i_order_status = order_status == null ? 0 : Integer
					.parseInt(order_status);
			Integer i_cid = cid == null ? 0 : Integer
					.parseInt(cid);
			Integer i_sid = sid == null ? 0 : Integer
					.parseInt(sid);
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			OrderSearch searchBean = new OrderSearch();

			searchBean.setOrder_serial(order_serial);
			searchBean.setProduct_name(product_name);
			searchBean.setUser_name(user_name);
			searchBean.setUser_phone(user_phone);
			searchBean.setOrder_status(i_order_status == 0 ? null
					: i_order_status);
			searchBean.setCid(i_cid == 0 ? null
					: i_cid);
			searchBean.setSid(i_sid == 0 ? null
					: i_sid);
			searchBean.setStart_time(start_time);
			searchBean.setEnd_time(end_time);
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
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/order/order_search";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=edit", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String editOrder(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "editOrder");
//:product_price:100.0;type:3;order_price:100;isStatChg:36;order_status:12;editId:36;method:edit;
		try {

			String order_id = request.getParameter("viewId");
			String type = request.getParameter("type");

			if (order_id == null) {

				order_id = request.getParameter("editId");

				String product_amount = request.getParameter("product_amount");
				String product_price = request.getParameter("product_price");

				String order_status = request.getParameter("order_status");
				String delivery_type = request.getParameter("delivery_type");
				String delivery_price = request.getParameter("delivery_price");
				String pay_type = request.getParameter("pay_type");
				String isStatChg = request.getParameter("isStatChg");

				Integer i_orderStatus = Integer.parseInt(order_status);

				Integer i_product_amount = product_amount == null ? null
						: Integer.parseInt(product_amount);
				Float f_product_price = product_price == null ? null : Float
						.parseFloat(product_price);

				Integer i_delivery_type = delivery_type == null ? null
						: Integer.parseInt(delivery_type);
				Integer i_payType = pay_type == null ? null : Integer
						.parseInt(pay_type);

				Order order = new Order();
				order.setOrder_id(Integer.parseInt(order_id));

				order.setProduct_amount(i_product_amount);
				order.setProduct_price(f_product_price);
				order.setPay_type(i_payType);
				order.setDelivery_type(i_delivery_type);

				if (i_delivery_type != null && i_delivery_type == 2) {
					Float f_delivery_price = Float.parseFloat(delivery_price);
					order.setDelivery_price(f_delivery_price);
					order.setOrder_price(f_product_price
							+ f_delivery_price);
				} else if (i_delivery_type != null && i_delivery_type == 1) {
					order.setDelivery_price(0f);
					order.setOrder_price(f_product_price);
				}
				//订单定价
				else if (i_delivery_type == null)
				{
					order.setDelivery_price(0f);
					order.setOrder_price(f_product_price);
				}

				if ("1".equals(isStatChg))
					order.setOrder_status(i_orderStatus);

				orderService.updateOrder(order);
				request.setAttribute("orderEdit", 0);

			}

			Order order = this.orderService.getOrderById(Integer
					.parseInt(order_id));
			

			request.setAttribute("order", order);
			
			

			if ("2".equals(type)) {
				return "/jsp/order/order_edit2";
			} else if ("3".equals(type)) {
				return "/jsp/order/order_edit3";
			} else {
				return "/jsp/order/order_edit";
			}

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=orderDelivery", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String orderDelivery(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "orderDelivery");

		try {

			HttpSession session = request.getSession();

			String service_id = CommonUtil.isNull(request
					.getParameter("service_id"));
			String product_name = CommonUtil.isNull(request
					.getParameter("product_name"));
			String ispickup = CommonUtil.isNull(request
					.getParameter("ispickup"));
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			String cid = CommonUtil.isNull(request
					.getParameter("cid"));

			Integer i_service_id = null;
			if (service_id != null && !"0".equals(service_id)) {
				i_service_id = Integer.parseInt(service_id);
			}
			
			Integer i_cid = null;
			if (cid != null && !"0".equals(cid)) {
				i_cid = Integer.parseInt(cid);
			}
			
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
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
				 list = this.orderService.searchProduct(begin, size, null,
						i_service_id, product_name, null,i_cid);
				 count = this.orderService.countProduct(null, i_service_id,
						product_name, null,i_cid);
				if (0 == count)
					i_pageIndex = 0;
			}


			for (Map map : list) {
				Integer productId = (Integer) map.get("product_id");
				Integer orderCount = this.orderService
						.countOrderByProduct(productId);
				map.put("order_count", orderCount);
			}

			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/order/order_delivery";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=orderPickup", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String orderPickup(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "orderPickup");

		try {

			String ispickup = CommonUtil.isNull(request
					.getParameter("ispickup"));
			if (ispickup != null || "1".equals(ispickup)) {
				String[] ids = request.getParameterValues("ids");
				for (String id : ids) {
					Integer pId = Integer.parseInt(id);
					Order order = new Order();
					order.setOrder_id(pId);
					order.setOrder_status(3);

					this.orderService.updateOrder(order);

				}

				request.setAttribute("pickUp", 1);
			}

			if (request.getParameter("order_status") == null)
				request.setAttribute("order_status", "1");

			this.searchOrder(request);

			
			
			return "jsp/order/order_pickup";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=orderPrice", method = {
			RequestMethod.POST, RequestMethod.GET })
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

	*//**
	 * 用户更改下拉框时更换session
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(params = "method=selectOrder", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void  selectOrder(HttpServletRequest request,
			HttpServletResponse response){
		// TODO 没有问题干掉
		System.out.println("成功");
		System.out.println("成功");
		System.out.println("成功");
		System.out.println("成功");
		System.out.println("成功");
		System.out.println("成功");
		int cid=Integer.valueOf(request.getParameter("cid"));
		 request.getSession().setAttribute("selectCid", cid);
		 Community community = communityService.getCommunity(cid);
		 try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(cid);
		 request.getSession().setAttribute("comm_service", community);
	}*/
	
	
	/**
	 *  统计
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=countOrder", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String  countOrder(HttpServletRequest request,
			HttpServletResponse response){
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
	System.out.println("开始时间"+start);
	System.out.println("结束时间"+end);
	System.out.println("小区"+cid);
	System.out.println("商品类别"+goodtype);
	
	Map<String,String> map =new HashMap<String, String>(4);
	//判断goodtype
	if("4".equals(goodtype)){
		goodtype=null;
	}
//	map.put("start", "2014-3-1");
//	map.put("end", "2015-1-1");
//	map.put("pty", "1");
//	map.put("cid", "2");
	
	map.put("start", start);
	map.put("end", end);
	map.put("pty", goodtype);
	map.put("cid", cid);
//	map.put("cid", "2");
	
	List<CountOrder> countOrder = orderService.countOrder(map);
	request.getSession().setAttribute("countSumOrders", countOrder);
//		return "jsp/order/order_price";
	return "jsp/order/countSumOrders";
	}
	
	
}

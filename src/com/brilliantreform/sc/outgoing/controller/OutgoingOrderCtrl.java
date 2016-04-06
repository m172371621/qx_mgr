package com.brilliantreform.sc.outgoing.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.distri.controller.DistriCtrl;
import com.brilliantreform.sc.incomming.po.IncommingDetailBean;
import com.brilliantreform.sc.incomming.po.IncommingHeaderBean;
import com.brilliantreform.sc.outgoing.service.OutgoingOrderService;
import com.brilliantreform.sc.product.po.ProductDouble;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.JsonUtil;
import com.brilliantreform.sc.utils.LogerUtil;

/**
 * 管理后台进货，调拨入，退货，损耗，调拨出单据的处理
 * 
 * @author lc
 * 
 */
@Controller
@RequestMapping("outgoingOrder.do")
public class OutgoingOrderCtrl {

	private static Logger logger = Logger.getLogger(DistriCtrl.class);

	@Autowired
	private OutgoingOrderService outgoingOrderService;

	private static int size = 20;

	@RequestMapping(params = "method=list_incomming_head", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list_incomming_head(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list_incomming_head");

		try {
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			String time_from = request.getParameter("time_from");
			String time_to = request.getParameter("time_to");
			Integer state = Integer
					.parseInt(request.getParameter("state") == null ? "0"
							: request.getParameter("state"));
			if (time_to == null) {
				time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
						(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
			}
			if (time_from == null) {
				time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
						(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));

			}
			int start = (i_pageIndex - 1) * size;
			Map<String, Object> parammap = new HashMap<String, Object>();
			parammap.put("community_id", cid);
			parammap.put("state", state);

			parammap.put("time_from", time_from);
			parammap.put("time_to", time_to);
			parammap.put("start", start);
			parammap.put("size", 20);

			// 单据类型
			Integer stock_type = 0;
			if (null == request.getParameter("stock_type")) {
				stock_type = (Integer) request.getSession().getAttribute(
						"stock_type");
			} else {
				stock_type = Integer.parseInt(request
						.getParameter("stock_type"));
				request.getSession().setAttribute("stock_type", stock_type);
			}
			parammap.put("stock_type", stock_type);

			int count = outgoingOrderService.getIncommingHeaderCount(parammap);
			List<IncommingHeaderBean> list = outgoingOrderService
					.getIncommingHeader(parammap);
			request.setAttribute("list", list);
			request.setAttribute("time_from", time_from);
			request.setAttribute("time_to", time_to);
			request.setAttribute("parammap", parammap);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			return "jsp/outgoing/outgoing_head_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	/**
	 * 修改汇总信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=update_incomming_head", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String update_incomming_head(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "update_incomming_head");

		try {
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			String createTime = request.getParameter("time_from1");
			Integer state = Integer
					.parseInt(request.getParameter("state") == null ? "0"
							: request.getParameter("state"));
			if (createTime == null) {
				createTime = new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date());
			}
			int start = (i_pageIndex - 1) * size;
			Map<String, Object> parammap = new HashMap<String, Object>();
			parammap.put("community_id", cid);
			parammap.put("state", state);
			parammap.put("create_time", createTime);
			parammap.put("start", start);
			parammap.put("size", 20);

			// 此处修改汇总信息
			IncommingHeaderBean headerBean = new IncommingHeaderBean();
			Integer stockchangeHeaderId = Integer.parseInt(request
					.getParameter("stockchangeHeaderId"));
			headerBean.setStockchange_header_id(stockchangeHeaderId);
			headerBean.setState(2);
			outgoingOrderService.updateHeaderInfo(headerBean);

			int count = outgoingOrderService.getIncommingHeaderCount(parammap);
			List<IncommingHeaderBean> list = outgoingOrderService
					.getIncommingHeader(parammap);
			request.setAttribute("list", list);
			request
					.setAttribute("time_from", request
							.getParameter("time_from"));
			request.setAttribute("time_to", request.getParameter("time_to"));
			request.setAttribute("parammap", parammap);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			return "jsp/outgoing/outgoing_head_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=list_incomming_detail", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list_incomming_detail(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list_distri_detail");

		try {
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);

			int start = (i_pageIndex - 1) * size;
			int stockchangeHeaderId = request
					.getParameter("stockchangeHeaderId") == null ? 0 : Integer
					.parseInt(request.getParameter("stockchangeHeaderId"));
			IncommingDetailBean parammap = new IncommingDetailBean();
			parammap.setStart(start);
			parammap.setSize(size);
			parammap.setStockchange_header_id(stockchangeHeaderId);

			int count = outgoingOrderService.getIncommintDetailCount(parammap);
			List<IncommingDetailBean> list = outgoingOrderService
					.getIncommintDetail(parammap);
			request.setAttribute("list", list);
			request.setAttribute("parammap", parammap);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			return "jsp/outgoing/outgoing_detail_view";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	/**
	 * 跳转到修改明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=modify_detail_url", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String modify_detail_url(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "modify_detail_url");

		try {
			int stockchangeHeaderId = request
					.getParameter("stockchangeHeaderId") == null ? 0 : Integer
					.parseInt(request.getParameter("stockchangeHeaderId"));
			IncommingDetailBean parammap = new IncommingDetailBean();
			parammap.setStockchange_header_id(stockchangeHeaderId);

			List<IncommingDetailBean> list = outgoingOrderService
					.getIncommintDetail(parammap);
			String batch_serial = "0";
			if (list.size() > 0) {
				batch_serial = list.get(0).getBatch_serial();
			}
			request.setAttribute("list", list);
			request.setAttribute("batch_serial", batch_serial);
			request.setAttribute("parammap", parammap);
			request.setAttribute("stockchangeHeaderId", stockchangeHeaderId);
			return "jsp/outgoing/outgoing_detail_modify";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	// 查询商品
	@RequestMapping(value = "method=queryGoods")
	public String queryGoods(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();
			Map<String, Object> map = new HashMap<String, Object>();
			String product_name = request.getParameter("q");
			String temp = new String(product_name.getBytes("ISO-8859-1"),
					"utf-8");
			product_name = URLDecoder.decode(temp, "utf-8");
			map.put("community_id", cid);
			map.put("product_name", product_name);
			int result_code = 0;
			String result_dec = "OK";
			try {
				List<ProductDouble> list = outgoingOrderService.queryGoods(map);
				// JSONArray jsonarry=JSONArray.fromObject(list);
				// System.out.println(jsonarry.toString());
				response.getWriter().print(
						JsonUtil.result2Json(result_code, result_dec, list));

			} catch (Exception e) {
				logger.error("IncommingOrderCtrl queryGoods" + e.getMessage());
				response.getWriter().print(JsonUtil.result2Json(false, null));
			}
		} catch (Exception e) {
		}
		return null;
	}

	@RequestMapping(params = "method=list_goods_url", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list_goods_url(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list_goods_url");
		return "jsp/outgoing/outgoing_goods_list";
	}

	@RequestMapping(params = "method=add_detail_url", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String add_detail_url(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "add_detail_url");
		return "jsp/outgoing/outgoing_detail_add";
	}

	/**
	 * 删除指定单据并查询列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=del_incomming_head", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String del_incomming_head(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "del_incomming_head");

		try {
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			String createTime = request.getParameter("time_from1");
			Integer state = Integer
					.parseInt(request.getParameter("state") == null ? "0"
							: request.getParameter("state"));
			if (createTime == null) {
				createTime = new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date());
			}
			int start = (i_pageIndex - 1) * size;
			Map<String, Object> parammap = new HashMap<String, Object>();
			parammap.put("community_id", cid);
			parammap.put("state", state);
			parammap.put("create_time", createTime);
			parammap.put("start", start);
			parammap.put("size", 20);

			// 删除指定单据的参数
			IncommingHeaderBean headerbean = new IncommingHeaderBean();
			int stockchangeHeaderId = Integer.parseInt(request
					.getParameter("stockchangeHeaderId"));
			headerbean.setStockchange_header_id(stockchangeHeaderId);
			outgoingOrderService.delIncommintHeader(headerbean);

			int count = outgoingOrderService.getIncommingHeaderCount(parammap);
			List<IncommingHeaderBean> list = outgoingOrderService
					.getIncommingHeader(parammap);
			request.setAttribute("list", list);
			request.setAttribute("parammap", parammap);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			return "jsp/outgoing/outgoing_head_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=del_incomming_detail", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String del_incomming_detail(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "del_incomming_detail");

		try {
			int stockchangeHeaderId = Integer.parseInt(request
					.getParameter("stockchangeHeaderId"));
			IncommingDetailBean parammap = new IncommingDetailBean();
			parammap.setStockchange_header_id(stockchangeHeaderId);

			//
			int stockchangeDetailId = Integer.parseInt(request
					.getParameter("stockchangeDetailId"));
			IncommingDetailBean detailBean = new IncommingDetailBean();
			detailBean.setStockchange_detail_id(stockchangeDetailId);
			outgoingOrderService.delIncommintDetail(detailBean);

			List<IncommingDetailBean> list = outgoingOrderService
					.getIncommintDetail(parammap);
			String batch_serial = "0";
			if (list.size() > 0) {
				batch_serial = list.get(0).getBatch_serial();
			}
			request.setAttribute("list", list);
			request.setAttribute("batch_serial", batch_serial);
			request.setAttribute("parammap", parammap);
			request.setAttribute("stockchangeHeaderId", stockchangeHeaderId);
			return "jsp/outgoing/outgoing_detail_modify";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	/**
	 * 修改明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	@RequestMapping(params = "method=modify_incomming_detail", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String modify_incomming_detail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		LogerUtil.logRequest(request, logger, "add_incomming_detail");

		try {
			//
			// 增加进货单汇总
			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			MgrUser user = (MgrUser) request.getSession().getAttribute(
					"user_info");
			String loginName = user.getLoginname();

			// Map<String, Object> map = new HashMap<String, Object>();
			// map.put("community_id", cid);
			// String maxNo = outgoingOrderService.getMaxNo(map);
			// maxNo = maxNo == null ? "000000" : maxNo;
			// IncommingHeaderBean headerBean = new IncommingHeaderBean();
			// headerBean.setCommunity_id(cid);
			// String str_cid = cid < 10 ? ("00000" + cid)
			// : cid < 100 ? ("0000" + cid) : cid < 1000 ? ("000" + cid)
			// : cid < 10000 ? ("00" + cid)
			// : cid < 10000 ? ("0" + cid) : String
			// .valueOf(cid);
			// int int_no = (Integer.parseInt(maxNo) + 1);
			// String str_no = int_no < 10 ? ("00000" + int_no)
			// : int_no < 100 ? ("0000" + int_no)
			// : int_no < 1000 ? ("000" + int_no)
			// : int_no < 10000 ? ("00" + int_no)
			// : cid < 10000 ? ("0" + int_no)
			// : String.valueOf(int_no);
			// String stockchange_header_no = "JH" + str_cid + str_no;
			// headerBean.setStockchange_header_no(stockchange_header_no);
			// headerBean.setStock_type(1);// 1=进货
			// headerBean.setState(1);// 1=未确认
			// headerBean.setCreate_by(loginName);
			//
			// // 添加汇总数据后获取汇总ID，使用此ID和明细关联
			// Integer stockchange_header_id = outgoingOrderService
			// .insertIncommingHeader(headerBean);

			Integer stockchange_header_id = Integer.parseInt(request
					.getParameter("stockchangeHeaderId"));

			// 增加进货单明细
			// 获取前台传参，参数分为商品ID，数量，
			String products = request.getParameter("products");
			String batch_serial = request.getParameter("batch_serial");
			batch_serial = batch_serial.equals("0") ? String
					.valueOf((new Date()).getTime()) : batch_serial;
			if (products != null) {
				JSONArray jsonarry = JSONArray.fromObject(products);
				List<IncommingDetailBean> detailList = JSONArray.toList(
						jsonarry, IncommingDetailBean.class);
				for (IncommingDetailBean detailBean : detailList) {
					detailBean.setStockchange_header_id(stockchange_header_id);
					detailBean.setBatch_serial(batch_serial);
					detailBean.setStock_type(1);// 进货=1
					detailBean.setCreate_by(loginName);
					detailBean.setCommunity_id(cid);
					detailBean.setCreate_by(user.getLoginname());
					detailBean.setIp(request.getRemoteAddr());
				}
				outgoingOrderService.insertIncommingDetail(detailList);
			}
			//
			int result_code = 0;
			String result_dec = "OK";
			response.getWriter().print(
					JsonUtil.result2Json(result_code, result_dec, null));
			return null;

		} catch (Exception e) {

			response.getWriter().print(JsonUtil.result2Json(false, null));
			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@SuppressWarnings( { "unchecked", "deprecation" })
	@RequestMapping(params = "method=add_incomming_detail", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String add_incomming_detail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		LogerUtil.logRequest(request, logger, "add_incomming_detail");

		try {
			//
			// 增加进货单汇总
			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			MgrUser user = (MgrUser) request.getSession().getAttribute(
					"user_info");
			String loginName = user.getLoginname();

			Integer stock_type = Integer.parseInt(request
					.getParameter("stock_type"));

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("community_id", cid);
			String maxNo = outgoingOrderService.getMaxNo(map);
			maxNo = maxNo == null ? "000000" : maxNo;
			IncommingHeaderBean headerBean = new IncommingHeaderBean();
			headerBean.setCommunity_id(cid);
			String str_cid = cid < 10 ? ("00000" + cid)
					: cid < 100 ? ("0000" + cid) : cid < 1000 ? ("000" + cid)
							: cid < 10000 ? ("00" + cid)
									: cid < 10000 ? ("0" + cid) : String
											.valueOf(cid);
			int int_no = (Integer.parseInt(maxNo) + 1);
			String str_no = int_no < 10 ? ("00000" + int_no)
					: int_no < 100 ? ("0000" + int_no)
							: int_no < 1000 ? ("000" + int_no)
									: int_no < 10000 ? ("00" + int_no)
											: cid < 10000 ? ("0" + int_no)
													: String.valueOf(int_no);
			String preHead = stock_type == 3 ? "TH" : stock_type == 4 ? "SH"
					: stock_type == 5 ? "DC" : "";
			String stockchange_header_no = preHead + str_cid + str_no;
			headerBean.setStockchange_header_no(stockchange_header_no);
			headerBean.setStock_type(stock_type);// 3=退货，4=损耗，5=调拨出
			headerBean.setState(1);// 1=未确认
			headerBean.setCreate_by(loginName);

			// 添加汇总数据后获取汇总ID，使用此ID和明细关联
			Integer stockchange_header_id = outgoingOrderService
					.insertIncommingHeader(headerBean);

			// 增加进货单明细
			// 获取前台传参，参数分为商品ID，数量，
			String products = request.getParameter("products");
			// String batch_serial = String.valueOf((new Date()).getTime());
			if (products != null) {
				JSONArray jsonarry = JSONArray.fromObject(products);
				List<IncommingDetailBean> detailList = JSONArray.toList(
						jsonarry, IncommingDetailBean.class);
				for (IncommingDetailBean detailBean : detailList) {
					detailBean.setStockchange_header_id(stockchange_header_id);
					// detailBean.setBatch_serial(batch_serial);
					detailBean.setStock_type(stock_type);// 进货=1
					detailBean.setCreate_by(loginName);
					detailBean.setCommunity_id(cid);
					detailBean.setCreate_by(user.getLoginname());
					detailBean.setIp(request.getRemoteAddr());
				}
				outgoingOrderService.insertIncommingDetail(detailList);
			}
			//
			int result_code = 0;
			String result_dec = "OK";
			response.getWriter().print(
					JsonUtil.result2Json(result_code, result_dec, null));
			return null;

		} catch (Exception e) {

			response.getWriter().print(JsonUtil.result2Json(false, null));
			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

}

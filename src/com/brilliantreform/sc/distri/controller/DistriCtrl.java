package com.brilliantreform.sc.distri.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.brilliantreform.sc.distri.po.DistriOrderBean;
import com.brilliantreform.sc.distri.service.DistriService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.ExcelUtil;
import com.brilliantreform.sc.utils.JsonUtil;
import com.brilliantreform.sc.utils.LogerUtil;

@Controller
@RequestMapping("distri.do")
public class DistriCtrl {
	private static Logger logger = Logger.getLogger(DistriCtrl.class);
	@Autowired
	private DistriService distriService;

	private static int size = 20;

	@RequestMapping(params = "method=list_distri_head", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list_distri_head(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list_distri_head");

		try {
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			String createTime=request.getParameter("time_from1");
			if(createTime==null){
				createTime=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			}
			int start = (i_pageIndex - 1) * size;
			Map<String, Object> parammap = new HashMap<String, Object>();
			String distri_worker_id=request.getParameter("distri_worker_id")==null?"0":request.getParameter("distri_worker_id");
			request.setAttribute("distri_worker_id", distri_worker_id);
			Integer i_worker_id=Integer.parseInt(distri_worker_id);
			if(i_worker_id>0)
				   parammap.put("distri_worker_id", i_worker_id);
			parammap.put("distri_community_id", cid);
			parammap.put("create_time", createTime);
			parammap.put("start", start);
			parammap.put("size", 20);

			int count = distriService.getDistriCount(parammap);
			List<DistriOrderBean> list = distriService.getDistriList(parammap);

			request.setAttribute("list", list);
			request.setAttribute("createTime", createTime);
			request.setAttribute("distri_worker", request.getParameter("distri_worker"));
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			return "jsp/distri/distri_head_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=list_distri_detail", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list_distri_detail(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list_distri_detail");
		String distri_num = "";
		try {

			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();
			String type = request.getParameter("type");
			distri_num = request.getParameter("distri_num");
			request.setAttribute("distri_num", distri_num);
			String distri_worker_id=request.getParameter("distri_worker_id");
			request.setAttribute("distri_worker_id", distri_worker_id);
			Integer i_worker_id=Integer.parseInt(distri_worker_id);

			String createTime=request.getParameter("time_from1");
			if(createTime==null){
				createTime=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			}
			if ("2".equals(type)) {
				// 根据配送单号生成新的配送单
				Date nowTime = new Date();
				SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
				String timestr = time.format(nowTime);
				String str_cid=cid<9?("000"+cid):cid<99?("00"+cid):("0"+cid);
				String str_worker_id=i_worker_id<9?("000"+i_worker_id):i_worker_id<99?("00"+i_worker_id):("0"+i_worker_id);
				distri_num = str_cid + str_worker_id+timestr;

				request.setAttribute("distri_num", distri_num);
				DistriOrderBean distri = new DistriOrderBean();
				distri.setDistri_num(distri_num);
				distri.setDistri_worker_id(i_worker_id);
				distri.setDistri_head_status(1);
				distri.setDistri_community_id(cid);
				distri.setDistri_worker_id(i_worker_id);

				Map<String, Object> mmap = new HashMap<String, Object>();
				String size=request.getParameter("input_ordertop");
				mmap.put("distri_num", distri_num);
				mmap.put("communityId", cid);
				mmap.put("start", 0);
				mmap.put("size", Integer.parseInt(size));
				mmap.put("order_status", 30);
				
				mmap.put("order_time_begin", createTime);
				mmap.put("order_time_end", createTime);
				mmap.put("distri_staus", 30);
				mmap.put("distri_worker_id", i_worker_id);
				distriService.insertDistriDetail(mmap,distri);
//				int count=distriService.getDetailCountByNum(mmap);
//				if(count>0){
//					distriService.insertDistri(distri);
////					distriService.insertDistriProduct(mmap);
////					distriService.updateOrderStatus(mmap);
//				}
			}
			int start = (i_pageIndex - 1) * size;

			Map<String, Object> parammap = new HashMap<String, Object>();
			parammap.put("distri_num", distri_num);
			parammap.put("communityId", cid);
			parammap.put("start", start);
			parammap.put("size", size);
			parammap.put("create_time", createTime);
			if(i_worker_id>0)
			   parammap.put("distri_worker_id", i_worker_id);
			int count = distriService.getDistriDetailCount(parammap);
			List<DistriOrderBean> list = distriService
					.getDistriDetail(parammap);
			for(DistriOrderBean distriBean:list){
				List<DistriOrderBean> detailList=distriBean.getDetailList();
				for(DistriOrderBean detailBean:detailList){
					List<DistriOrderBean> productList=detailBean.getSubList();
					distriBean.setCount((distriBean.getCount()==null?0:distriBean.getCount())+productList.size());
				}
				
			}

			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			String view_status=request.getParameter("view_status");
			if(view_status==null) view_status="1";
			request.setAttribute("view_status", view_status);
			return "jsp/distri/distri_detail_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=add_distri", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String add_distri(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "add_distri");
		String distri_num = "";
		try {

			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();
//			Date nowTime = new Date();
//			SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
//			String timestr = time.format(nowTime);
//			distri_num = "000" + cid + "0001" + timestr;
//
//			request.setAttribute("distri_num", distri_num);
//			DistriOrderBean distri = new DistriOrderBean();
//			distri.setDistri_num(distri_num);
//			distri.setDistri_worker_id(1);
//			distri.setDistri_head_status(1);
//			distri.setDistri_community_id(cid);
//			distriService.insertDistri(distri);
//
//			Map<String, Object> parammap = new HashMap<String, Object>();
//			parammap.put("distri_num", distri_num);
//			parammap.put("communityId", cid);
//			parammap.put("start", 0);
//			parammap.put("size", size);
//			int count = distriService.getDistriDetailCount(parammap);
//			List<DistriOrderBean> list = distriService
//					.getDistriDetail(parammap);
//
//			request.setAttribute("list", list);
//			request.setAttribute("pageCount", count);
//			request.setAttribute("pageIndex", 1);
			request.setAttribute("distri_worker_id", request.getParameter("distri_worker_id"));
			String view_status=request.getParameter("view_status");
			if(view_status==null) view_status="1";
			request.setAttribute("view_status", view_status);
			return "jsp/distri/distri_detail_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=add_distri_detail", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String add_distri_detail(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list_distri_head");

		try {

			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			String distri_num = request.getParameter("distri_num");
			Map<String, Object> parammap = new HashMap<String, Object>();
			parammap.put("distri_num", distri_num);
			parammap.put("communityId", cid);
			parammap.put("start", 0);
			parammap.put("size", size);
			// 根据配送单号生成新的配送单
			parammap.put("order_status", 30);
			String createTime=request.getParameter("time_from1");
			parammap.put("order_time_begin", createTime);
			parammap.put("order_time_end", createTime);
			distriService.insertDistriDetail(parammap,null);
//			distriService.insertDistriProduct(parammap);
//			distriService.updateOrderStatus(parammap);
			int count = distriService.getDistriDetailCount(parammap);
			List<DistriOrderBean> list = distriService
					.getDistriDetail(parammap);

			request.setAttribute("list", list);
			request.setAttribute("pageCount", count);
			request.setAttribute("pageIndex", 1);
			
			String view_status=request.getParameter("view_status");
			if(view_status==null) view_status="1";
			request.setAttribute("view_status", view_status);
			return "jsp/distri/distri_detail_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=export_excel", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void export_excel(HttpServletRequest request,
			HttpServletResponse response) {

		LogerUtil.logRequest(request, logger, "export_excel");

		try {

			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			Map<String, Object> parammap = new HashMap<String, Object>();
			String distri_num = request.getParameter("distri_num");
			parammap.put("distri_num", distri_num);
			parammap.put("start", 0);
			parammap.put("distri_staus", 1);
			int count = distriService.getDistriDetailCount(parammap);
			parammap.put("size", count);
			List<DistriOrderBean> list = distriService
					.getDistriDetailExecel(parammap);

			request.setAttribute("list", list);
			OutputStream outputStream = response.getOutputStream();
			String fileName = "distri.xls";
			// 下载格式设置
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			outputStream = ExcelUtil.exportExcel(outputStream, list);
			outputStream.close();

		} catch (Exception e) {

			logger.error(e.getMessage());
		}

	}

	@RequestMapping(params = "method=opt_status", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void opt_status(HttpServletRequest request,
			HttpServletResponse response) {

		LogerUtil.logRequest(request, logger, "list_distri_detail");
		String distri_num = "";
		try {

			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();
			String status = request.getParameter("status");
			String phone = request.getParameter("phone");
			String[] phone_attr = phone.split(",");
			DistriOrderBean distri = new DistriOrderBean();
			distri_num=request.getParameter("distri_num");
			for (int i=0;i<phone_attr.length;i++) {
				distri.setPhone(phone_attr[i]);
				distri.setDistri_num(distri_num);
				distri.setDistri_staus(Integer.parseInt(status));
				distriService.optDistriDetail(distri);
				if("2".equals(status)){
					Map<String, Object> mmap = new HashMap<String, Object>();
					mmap.put("order_status", 3);
					mmap.put("distri_num", distri_num);
					mmap.put("phone", phone_attr[i]);
					List<DistriOrderBean> list = distriService.getSerialByPhone(mmap);
					for(DistriOrderBean tmp:list){
						mmap.put("order_serial", tmp.getOrder_serial());
						distriService.updateOrderStatus(mmap);
					}
				}
			}
			response.getWriter().print(JsonUtil.result2Json(0, "更新成功", null));

		} catch (Exception e) {

			logger.error(e.getMessage());
		}

	}
	
	@RequestMapping(params = "method=opt_head_status", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void opt_head_status(HttpServletRequest request,
			HttpServletResponse response) {

		LogerUtil.logRequest(request, logger, "list_distri_detail");
		String distri_num = "";
		try {

			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();
			String status = request.getParameter("status");
			String distri_product_id = request.getParameter("distri_product_id");
			String[] distri_product_id_attr = distri_product_id.split(",");
			DistriOrderBean distri = new DistriOrderBean();
			for (String obj : distri_product_id_attr) {
				distri.setDistri_product_id(Integer.parseInt(obj));
				distri.setDistri_staus(Integer.parseInt(status));
				distriService.optDistriDetail(distri);
			}
			response.getWriter().print(JsonUtil.result2Json(0, "更新成功", null));

		} catch (Exception e) {

			logger.error(e.getMessage());
		}

	}
	
	@RequestMapping(params = "method=getDistriWorker", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void getDistriWorker(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		LogerUtil.logRequest(request, logger, "getDistriWorker");
		try {

			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();
			Map<String, Object> distri=new HashMap<String, Object>();
			distri.put("distri_community_id", cid);
			List<DistriOrderBean> list=distriService.getDistriWorker(distri);
			response.getWriter().print(JsonUtil.result2Json(0, "查询成功", list));

		} catch (Exception e) {

			logger.error(e.getMessage());
		}

	}
	
	@RequestMapping(params = "method=distr_head_new", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String distr_head_new(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "distr_head_new");
		try {

			Date nowTime = new Date();
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
			String timestr = time.format(nowTime);
			request.setAttribute("createTime", timestr);
			return "jsp/distri/distri_head_new";
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}
	}
	
	@RequestMapping(params = "method=distr_work_list", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String distr_work_list(HttpServletRequest request) {
		try {
			LogerUtil.logRequest(request, logger, "distr_work_list");
			Map param = CommonUtil.getParameterMap(request);
			Integer pageIndex = CommonUtil.safeToInteger(param.get("pageIndex"), 1);
			int begin = (pageIndex - 1) * size;
			param.put("begin", begin);
			param.put("size", size);
			param.put("worker_name",param.get("worker_name"));
			param.put("Community_id", ((Community)request.getSession().getAttribute("selectCommunity")).getCommunity_id());
			
			List<Map> list = distriService.distr_work_list(param);
			int count = distriService.distr_work_count(param);
			request.setAttribute("list", list);
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("pageCount", CommonUtil.getPageCount(count,size));
			request.setAttribute("queryParam", param);
			return  "jsp/distri/distri_worker_list";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}
	

	@RequestMapping(params = "method=distr_work_edit", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String distr_work_edit(HttpServletRequest request) {
		try {
			LogerUtil.logRequest(request, logger, "distr_work_edit");
			Integer worker_id = CommonUtil.safeToInteger(request.getParameter("worker_id"),null);
			if(null != worker_id) {
					Map<String,Object> map = distriService.get_id(worker_id);
					if(null != map)
							request.setAttribute("map", map);
			}
			return  "jsp/distri/distri_worker_edit";
		} catch (Exception e) {
			 logger.error(e.getMessage());
			return "jsp/error";
		}
	}
	
	@RequestMapping(params = "method=save_worker", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String save_worker(HttpServletRequest request , HttpServletResponse response, DistriOrderBean bean) {
		try {
			LogerUtil.logRequest(request, logger, "save_worker");
			distriService.save_Worker(bean);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		return "redirect:/distri.do?method=distr_work_list";
	}
	
	@RequestMapping(params = "method=delete", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String delete(HttpServletRequest request , HttpServletResponse response) throws IOException {
		String result = "error";
		try {
			LogerUtil.logRequest(request, logger, "delete");
			Integer worker_id = CommonUtil.safeToInteger(request.getParameter("worker_id"),null);
			if(null != worker_id) {
				distriService.delete(worker_id);
			    result = "ok";
			}
		} catch (Exception e) {
			 logger.error(e.getMessage());
			return "jsp/error";		
		}
		response.getWriter().print(result);
		return null;
	}
}
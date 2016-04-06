package com.brilliantreform.sc.task.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.order.po.MsgInfo;
import com.brilliantreform.sc.order.service.MsgInfoService;
import com.brilliantreform.sc.order.service.OrderService;
import com.brilliantreform.sc.task.po.*;
import com.brilliantreform.sc.task.service.TaskService;
import com.brilliantreform.sc.utils.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("task.do")
public class TaskCtrl {
	
	private static Logger logger = Logger.getLogger(TaskCtrl.class);
	
	//private static String pic_path = "/upload/product/";
	
	private static int size = 20;

	@Autowired
	private OrderService orderService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private MsgInfoService msgInfoService;

	@RequestMapping(params = "method=search", method = { RequestMethod.POST, RequestMethod.GET })
	public String searchTask(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "searchTask");

		try {

			HttpSession session = request.getSession();

			String title = CommonUtil.isNull(request.getParameter("title"));
			String send_user = CommonUtil.isNull(request.getParameter("send_user"));
			String recv_user = CommonUtil.isNull(request.getParameter("recv_user"));
			String phone = CommonUtil.isNull(request.getParameter("phone"));
			String task_status = CommonUtil.isNull(request.getParameter("task_status"));
			String start_time = CommonUtil.isNull(request.getParameter("start_time"));
			String end_time = CommonUtil.isNull(request.getParameter("end_time"));
			String task_cid = CommonUtil.isNull(request.getParameter("task_cid"));
			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));

			if (request.getAttribute("task_status") != null) {
				task_status = (String) request.getAttribute("task_status");
			}

			Integer i_task_status = task_status == null ? 0 : Integer.parseInt(task_status);
			Integer i_task_cid = task_cid == null ? 0 : Integer.parseInt(task_cid);

			Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			TaskQueryBean searchBean = new TaskQueryBean();

			searchBean.setTitle(title);
			searchBean.setSend_user(send_user);
			searchBean.setRecv_user(recv_user);
			searchBean.setPhone(phone);
			searchBean.setTask_status(i_task_status);
			searchBean.setTask_cid(i_task_cid == 0 ? null : i_task_cid);
			searchBean.setStart_time(start_time);
			searchBean.setEnd_time(end_time);
			searchBean.setBegin(begin);
			searchBean.setSize(size);

			List<Task> list = new ArrayList<Task>();
			int count = 0;

			if (request.getParameter("pageIndex") != null) {
				list = this.taskService.searchTask(searchBean);
				count = taskService.countSearchTask(searchBean);

				if (0 == count)
					i_pageIndex = 0;
			}

			request.setAttribute("list", list);

			request.setAttribute("pageCount", count % size == 0 ? count / size : count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);

			searchBean.setTask_status(i_task_status);
			searchBean.setTask_cid(i_task_cid);
			request.setAttribute("searchBean", searchBean);

			return "jsp/task/task_search";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String editTask(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "editTask");

		try {

			String task_id = request.getParameter("viewId");
			String opType = request.getParameter("opType");

			if (task_id == null) {

				task_id = request.getParameter("editId");

				Task task = new Task();
				task.setTask_id(Integer.parseInt(task_id));

				if ("1".equals(opType)) {
					task.setTask_status(2);
					task.setReceive_user_id(1);
				}

				if ("2".equals(opType)) {
					task.setTask_status(9);
				}

				if ("3".equals(opType)) {
					task.setTask_status(5);
				}

				this.taskService.updateTask(task);
				request.setAttribute("isEdit", 0);

			}

			Task task = this.taskService.getTaskById(Integer.parseInt(task_id));

			request.setAttribute("task", task);

			// if ("2".equals(type)) {
			// return "/jsp/order/order_edit2";
			// } else if ("3".equals(type)) {
			// return "/jsp/order/order_edit3";
			// } else {
			// return "/jsp/order/order_edit";
			// }
			return "/jsp/task/task_edit";
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	@RequestMapping(params = "method=searchPropertyTask", method = { RequestMethod.POST, RequestMethod.GET })
	public String searchPropertyTask(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "searchPropertyTask");

		try {

			HttpSession session = request.getSession();

			String title = CommonUtil.isNull(request.getParameter("title"));
			String send_user = CommonUtil.isNull(request.getParameter("send_user"));
			String phone = CommonUtil.isNull(request.getParameter("phone"));
			String task_status = CommonUtil.isNull(request.getParameter("task_status"));
			String start_time = CommonUtil.isNull(request.getParameter("start_time"));
			String end_time = CommonUtil.isNull(request.getParameter("end_time"));
			String task_cid = CommonUtil.isNull(request.getParameter("task_cid"));
			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));

			if (request.getAttribute("task_status") != null) {
				task_status = (String) request.getAttribute("task_status");
			}

			Integer i_task_status = task_status == null ? 0 : Integer.parseInt(task_status);
			Integer i_task_cid = task_cid == null ? 0 : Integer.parseInt(task_cid);

			Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			TaskQueryBean searchBean = new TaskQueryBean();

			searchBean.setTitle(title);
			searchBean.setSend_user(send_user);
			searchBean.setPhone(phone);
			searchBean.setTask_status(i_task_status == 0 ? null : i_task_status);
			searchBean.setTask_cid(i_task_cid == 0 ? null : i_task_cid);
			searchBean.setStart_time(start_time);
			searchBean.setEnd_time(end_time);
			searchBean.setBegin(begin);
			searchBean.setSize(size);

			List<TaskProperty> list = new ArrayList<TaskProperty>();
			int count = 0;

			if (request.getParameter("pageIndex") != null) {
				list = this.taskService.searchTaskProperty(searchBean);
				count = taskService.countTaskProperty(searchBean);

				if (0 == count)
					i_pageIndex = 0;
			}

			request.setAttribute("list", list);
			;
			request.setAttribute("pageCount", count % size == 0 ? count / size : count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);

			searchBean.setTask_status(i_task_status);
			searchBean.setTask_cid(i_task_cid);
			request.setAttribute("searchBean", searchBean);

			return "jsp/task/property_task_search";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=editPropertyTask", method = { RequestMethod.POST, RequestMethod.GET })
	public String editPropertyTask(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "editPropertyTask");

		try {

			String task_id = request.getParameter("viewId");
			String opType = request.getParameter("opType");

			if (task_id == null) {

				task_id = request.getParameter("editId");

				TaskProperty task = new TaskProperty();
				task.setTask_id(Integer.parseInt(task_id));

				if ("1".equals(opType)) {
					task.setTask_status(2);

				}

				if ("2".equals(opType)) {
					task.setTask_status(3);
					task.setComplete_time(new Timestamp(System.currentTimeMillis()));
				}

				this.taskService.updateTaskProperty(task);
				request.setAttribute("isEdit", 0);

			}

			TaskProperty task = this.taskService.getTaskProperty(Integer.parseInt(task_id));

			request.setAttribute("task", task);

			// if ("2".equals(type)) {
			// return "/jsp/order/order_edit2";
			// } else if ("3".equals(type)) {
			// return "/jsp/order/order_edit3";
			// } else {
			// return "/jsp/order/order_edit";
			// }
			return "/jsp/task/property_task_edit";
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}
	}

	/**
	 * 物业通告信息
	 */
	@RequestMapping(params = "method=propertyInfoList", method = { RequestMethod.POST, RequestMethod.GET })
	public String propertyInfoList(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "propertyInfo");
		List<PropertyInfo> propertyInfoList = null;
		try {

			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int community_id = c.getCommunity_id();
			String start_time = CommonUtil.isNull(request.getParameter("start_time"));
			String end_time = CommonUtil.isNull(request.getParameter("end_time"));

			String pageIndex = CommonUtil.isNull(request.getParameter("pageIndex"));
			Integer i_pageIndex = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;
			int count = 0;
			// 获取当天日期时间
			if (null == end_time) {
				end_time = new SimpleDateFormat("yyyy-MM-dd").format(new Date((new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
			}
			// 获得当天7天的时间
			if (null == start_time) {
				start_time = new SimpleDateFormat("yyyy-MM-dd").format(new Date((new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start_time", start_time);
			map.put("end_time", end_time);
			map.put("community_id", community_id);
			map.put("begin", begin);
			map.put("size", size);

			// 结果
			propertyInfoList = taskService.propertyInfoList(map);
			// 总记录数
			count = taskService.propertyInfoListCount(map);
			if (0 == count)
				i_pageIndex = 0;

			request.setAttribute("pageCount", count % size == 0 ? count / size : count / size + 1);
			request.setAttribute("start_time", start_time);
			request.setAttribute("end_time", end_time);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("propertyInfoList", propertyInfoList);
			return "/jsp/task/wylist";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}

	/**
	 * 跳转物业通告录入页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(params = "method=propertyInfo_Jump", method = { RequestMethod.POST, RequestMethod.GET })
	public String propertyInfo_Jump(HttpServletRequest request, HttpServletResponse response) {

		LogerUtil.logRequest(request, logger, "addOrderInfo");

		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("addProPerty", 1);
			request.setAttribute("pageIndex", 1);
			return "jsp/task/wy";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
	}

	/**
	 * 物业通告录入
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(params = "method=propertyInfo_ADD", method = { RequestMethod.POST, RequestMethod.GET })
	public String propertyInfo_ADD(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "propertyInfo_ADD");
		response.setCharacterEncoding("UTF-8");
		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int communityId = c.getCommunity_id();
			// 设置返回标志与提示
			int result_code = 0;
			String result_dec = "提交成功！";

			String title = CommonUtil.isNull(request.getParameter("title"));
			String content = CommonUtil.isNull(request.getParameter("content"));
			String phone = CommonUtil.isNull(request.getParameter("phone"));
			String landline = CommonUtil.isNull(request.getParameter("landline"));
			String address = CommonUtil.isNull(request.getParameter("address"));
			String createTime = CommonUtil.isNull(request.getParameter("createTime"));
			String remarks = CommonUtil.isNull(request.getParameter("remarks"));

			String addPropertyInfo = CommonUtil.isNull(request.getParameter("addPropertyInfo"));
			request.setAttribute("addPropertyInfo", addPropertyInfo);

			PropertyInfo info = new PropertyInfo();
			info.setTitle(title == null ? null : title);
			info.setContent(content == null ? null : content);
			info.setPhone(phone == null ? null : phone);
			info.setLandline(landline == null ? null : landline);
			info.setAddress(address == null ? null : address);
			info.setCreateTime(createTime == null ? null : createTime);
			info.setCommunity_id(communityId);
			info.setRemarks(remarks == null ? null : remarks);

			taskService.propertyInfo_ADD(info);

			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, null));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		return null;
	}

	/**
	 * 跳转 编辑物业公告页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=editPropertyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String editPropertyInfo(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "editPropertyInfo");
		response.setCharacterEncoding("UTF-8");
		PropertyInfo info = null;
		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int communityId = c.getCommunity_id();
			// 设置返回标志与提示
			int result_code = 0;
			String result_dec = "提交成功！";
			String property_information_id = CommonUtil.isNull(request.getParameter("property_information_id"));
			info = taskService.propertyInfoOne(Integer.parseInt(property_information_id));
			String createTime = info.getCreateTime();
			request.setAttribute("property_information_id", property_information_id);
			request.setAttribute("createTime", createTime);
			request.setAttribute("addProPerty", 0);
			request.setAttribute("info", info);
			return "jsp/task/wy";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
	}

	/**
	 * 编辑物业公告 信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=editProperty", method = { RequestMethod.POST, RequestMethod.GET })
	public String editProperty(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "editProperty");
		response.setCharacterEncoding("utf-8");
		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int communityId = c.getCommunity_id();
			// 设置返回标志与提示
			int result_code = 0;
			String result_dec = "更新成功！";
			String property_information_id = CommonUtil.isNull(request.getParameter("property_information_id"));
			String title = CommonUtil.isNull(request.getParameter("title"));
			String content = CommonUtil.isNull(request.getParameter("content"));
			String phone = CommonUtil.isNull(request.getParameter("phone"));
			String landline = CommonUtil.isNull(request.getParameter("landline"));
			String address = CommonUtil.isNull(request.getParameter("address"));
			String createTime = CommonUtil.isNull(request.getParameter("createTime"));
			String remarks = CommonUtil.isNull(request.getParameter("remarks"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title);
			map.put("content", content);
			map.put("phone", phone);
			map.put("landline", landline);
			map.put("address", address);
			map.put("createTime", createTime);
			map.put("remarks", remarks);
			map.put("property_information_id", property_information_id);
			taskService.editPropertyInfo(map);
			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, null));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
		return null;
	}

	/**
	 * 删除物业公告信息 根据 property_information_id 删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=delPropertyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String delPropertyInfo(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "delPropertyInfo");

		try {
			String property_information_id = CommonUtil.isNull(request.getParameter("property_information_id"));
			taskService.delPropertyInfo(Integer.parseInt(property_information_id));
			return this.propertyInfoList(request, null);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
	}

	/**
	 * 预约管理
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping(params = "method=getService_order", method = { RequestMethod.POST, RequestMethod.GET })
	public String getService_order(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "getService_order");
		
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int communityId = c.getCommunity_id();
			
			String user_name = CommonUtil.isNull(request.getParameter("user_name"));
			String user_phone =  CommonUtil.isNull(request.getParameter("user_phone"));
			String service_order_name = CommonUtil.isNull(request.getParameter("service_order_name"));
			String beginTime = request.getParameter("start_create_time");
			String endTime = request.getParameter("end_create_time");
			
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("community_id", communityId);
			map.put("begin", begin);
			map.put("size", size);
			map.put("user_name", user_name);
			map.put("user_phone", user_phone);
			map.put("service_name", service_order_name);
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			
			List<Service_order> service_order_Map = taskService.getService_order(map);
			int  count  = taskService.getService_order_count(map);
			request.setAttribute("service_order_Map", service_order_Map);
			request.setAttribute("user_name", user_name);
			request.setAttribute("user_phone", user_phone);
			request.setAttribute("service_order_name", service_order_name);
			Calendar cal  = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			request.setAttribute("beginTime", beginTime ==null?new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()):beginTime);
			request.setAttribute("endTime", endTime == null?new SimpleDateFormat("yyyy-MM-dd").format(new Date()):endTime);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			return "jsp/task/service_order";
			//response.getWriter().print(JSONArray.fromObject(map).toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
	}
	
	/**
	 * 删除预约
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=delService_order", method = { RequestMethod.POST, RequestMethod.GET })
	public String delService_order(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "delService_order");
		try {
			String order_id = request.getParameter("oid");
			String user_id = request.getParameter("uid");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("order_id", Integer.valueOf(order_id));
			map.put("user_id", Integer.valueOf(user_id));
			int num = taskService.delService_order(map);//执行删除
			response.getWriter().print(num);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
		return null;
	}
	
	/**
	 * 跳转预约编辑页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=service_order_edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String service_order_edit(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "service_order_edit");
		
		try {
			String order_id = request.getParameter("order_id");
			Service_order service_order = taskService.getService_order_edit(Integer.valueOf(order_id));
			request.setAttribute("service_order", service_order);
			return "jsp/task/service_order_edit";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
	}
	
	/**
	 * 提交更新 预约管理
	 */
	@RequestMapping(params = "method=updataService_order", method = { RequestMethod.POST, RequestMethod.GET })
	public String updataService_order(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "updataService_order");
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			
			String order_id = request.getParameter("order_id");
			String user_id = request.getParameter("user_id_value");
			String service_name = request.getParameter("service_name_value");
			String order_dec = request.getParameter("order_dec");
			String order_status = request.getParameter("order_status");
			String picture = CommonUtil.isNull(request
					.getParameter("picture"));
			String product_id = request.getParameter("viewId");
			
			String cid_1 = CommonUtil.isNull(request.getParameter("cid_1"));
			
			if (product_id == null
					&& request.getAttribute("product_id") != null) {
				product_id = (String) request.getAttribute("product_id");
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("order_id", Integer.valueOf(order_id));
			map.put("user_id", Integer.valueOf(user_id));
			map.put("service_name", service_name);
			map.put("order_dec", order_dec);
			map.put("order_status", Integer.valueOf(order_status));
			if (StringUtils.isNotBlank(picture)) {
				String new_path = "/upload/product/" + cid_1 + "/picture/"
						+ product_id
						+ picture.substring(picture.lastIndexOf("."));
				try {
					picture = URLEncoder.encode(picture, "utf-8");
					String url_new_path = URLEncoder.encode(new_path,
							"utf-8");
					HttpUtil.doGet(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST")
							+ "/common/rename/",
							"password=qxitrename&old_path=" + picture
									+ "&new_path=" + url_new_path);
					map.put("service_img", new_path);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					logger.error(e1.getMessage());
				}
			}
			int num = taskService.updataService_order(map);
			//服务接单发送
			if("2".equals(order_status)){
				MsgInfo msginfo = new MsgInfo();
				msginfo.setMsg_info_phone(user_id+"");
				msginfo.setMsg_template_code("0000000007");
				msginfo.setMsg_info_detail("");
				msgInfoService.addMsgInfo(msginfo);
			}
			request.setAttribute("updataService_order_num", num);
			return getService_order(request, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "/jsp/error";
		}
		
	}

    @RequestMapping(params = "method=showListServiceOrderPage")
    public String showListServiceOrderPage(HttpServletRequest request) {
        return "/new/jsp/order/listServiceOrder";
    }

    @RequestMapping(params = "method=searchServiceOrder")
    public void searchServiceOrder(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchServiceOrder");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);
            //处理结束时间
            Date endTime = CommonUtil.safeToDate(param.get("endTime"), "yyyy-MM-dd");
            if(endTime != null) {
                param.put("endTime", CommonUtil.formatDate(DateUtils.addDays(endTime, 1), "yyyy-MM-dd"));
            }

            List<Map> list = taskService.searchServiceOrder(param);
            int count = taskService.searchServiceOrderCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchServiceOrder error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());

    }

    @RequestMapping(params = "method=getServiceOrderById")
    public void getServiceOrderById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getServiceOrderById");

            Integer order_id = CommonUtil.safeToInteger(request.getParameter("order_id"), null);

            if(order_id != null) {
                Map serviceOrder = taskService.getServiceOrderById(order_id);
                CommonUtil.outToWeb(response, JSONObject.fromObject(serviceOrder).toString());
            }
        } catch (Exception e) {
            logger.error("getServiceOrderById error", e);
        }
    }

    @RequestMapping(params = "method=editServiceOrder")
    public void editServiceOrder(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            LogerUtil.logRequest(request, logger, "saveServiceOrder");

            Integer order_id = CommonUtil.safeToInteger(request.getParameter("order_id"), null);
            String order_dec = request.getParameter("order_dec");
            Integer order_status = CommonUtil.safeToInteger(request.getParameter("order_status"), null);

            if(order_id != null) {
                Service_order serviceOrder = taskService.getService_order_edit(order_id);
                if(serviceOrder != null) {
                    taskService.editServiceOrder(order_id, order_status, order_dec);
                    //接受订单的时候推送通知
                    if (order_status != null && order_status.intValue() == 2) {
                        MsgInfo msginfo = new MsgInfo();
                        msginfo.setMsg_info_phone(serviceOrder.getUser_id() + "");
                        msginfo.setMsg_template_code("0000000007");
                        msginfo.setMsg_info_detail("");
                        msgInfoService.addMsgInfo(msginfo);
                    }
                    result = "success";
                }
            }
        } catch (Exception e) {
            logger.error("saveServiceOrder error", e);
        }
        CommonUtil.outToWeb(response, result);
    }
}

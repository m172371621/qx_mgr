package com.brilliantreform.sc.stock.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.stock.service.StockService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.LogerUtil;

/**
 * 库存Ctrl
 * 
 * @author Lm
 * 
 */
@Controller
@RequestMapping("stock.do")
public class StockCtrl {

	@Autowired
	private StockService stockService;

	private static Logger logger = Logger.getLogger(StockCtrl.class);

	@RequestMapping(params = "method=stockPage", method = { RequestMethod.POST, RequestMethod.GET })
	public String stockPage(HttpServletRequest request) {
		LogerUtil.logRequest(request, logger, "stockPage");
		try {
			return "jsp/stock/stock";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}

	@RequestMapping(params = "method=newStockPage", method = { RequestMethod.POST, RequestMethod.GET })
	public String newStockPage(HttpServletRequest request) {
		LogerUtil.logRequest(request, logger, "stockPage");
		try {
			return "new/jsp/stock/stock";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
	}

	// 第一个树
	@RequestMapping(params = "method=list", method = { RequestMethod.POST, RequestMethod.GET })
	public String list(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "list");
		response.setCharacterEncoding("utf-8");
		List list = new ArrayList();
		try {
			List<Map> service_base = stockService.inventory_classification();
			for (Map maps : service_base) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", maps.get("service_id"));
				m.put("pId", maps.get("parent_id"));
				m.put("name", maps.get("service_name"));
				m.put("isParent", "true");
				m.put("level", 0);
				m.put("open", false);
				list.add(m);
				for (Map maps2 : stockService.getService_product_sum((Integer) m.get("id"))) {
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("id", "x" + maps2.get("product_id"));
					m2.put("pId", maps2.get("service_id"));
					m2.put("name", maps2.get("name"));
					m2.put("isParent", "false");
					m2.put("level", 1);
					m2.put("sId", maps2.get("product_id"));
					m2.put("title", "条码: "+maps2.get("barcode"));
					list.add(m2);
				}
			}
			response.getWriter().print(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		return null;
	}

	// 第二个树
	@RequestMapping(params = "method=list2", method = { RequestMethod.POST, RequestMethod.GET })
	public String list2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LogerUtil.logRequest(request, logger, "list2");
		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int cid = c.getCommunity_id();
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("UTF_8");
			List<Map> list = new ArrayList<Map>();
			List<Map> service_base_list = stockService.getService_base(cid);
			for (Map maps : service_base_list) {
				Map<String, Object> m = new HashMap<String, Object>(); // 父对象
				m.put("id", maps.get("service_id"));
				m.put("pId", maps.get("parent_id"));
				m.put("name", maps.get("service_name"));
				m.put("isParent", true);
				m.put("level", 0);
				list.add(m);
				Map map = new HashMap();
				map.put("service_id", (Integer) maps.get("service_id"));
				map.put("cid", cid);
				List<Map> tempMap = stockService.getService_Product_iservice_id(map);
				for (Map maps2 : tempMap) {
					Map<String, Object> m2 = new HashMap<String, Object>(); // 子对象
					m2.put("id", "x" + maps2.get("product_id"));
					m2.put("pId", (Integer) maps.get("service_id"));
					m2.put("level", 1);
					m2.put("sId", maps2.get("product_id"));
					m2.put("name", (String) maps2.get("name"));
					m2.put("isParent", false);
					m2.put("title", "条码: "+maps2.get("barcode"));
					list.add(m2);
				}
			}
			String s = JSONArray.fromObject(list).toString();
			System.out.println(s);
			response.getWriter().print(s);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		return null;
	}

	/**
	 * 添加 小区商品分类 和 小区商品 信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=adds", method = { RequestMethod.POST, RequestMethod.GET })
	public String adds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LogerUtil.logRequest(request, logger, "adds");
		JSONArray array = null;
		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int cid = c.getCommunity_id();
			String temp = request.getParameter("nodes");
			array = JSONArray.fromObject(temp);
			int len = array.size();
			Map<String, Object> map = new HashMap<String, Object>();
			Integer i_service_id = null;
			int service_id = 0;
			for (int i = 0; i < len; i++) {
				// 父节点
				// service_base表中的 -> i_serivce_id
				if ((Boolean) array.getJSONObject(i).get("isParent")) {
					// 根据父节点ID(分类)判断父节点_数据是否存在，有就不插入数据，没有就插入
					map.put("i_service_id", array.getJSONObject(i).get("id"));
					map.put("cid", cid);
					map.put("service_name", array.getJSONObject(i).get("name"));
					// 检查有没有该小区的分类 ; null 没有
					i_service_id = stockService.checkService_base(map);
					if (i_service_id == null) {
						// 插入该小区分类
						map.put("service_id", array.getJSONObject(i).get("id")); // 父节点ID
																					// (就是分类ID，找service_product分类是否有。)
						service_id = stockService.insertService_base(map);
					}else{
						//更新分类的i_service_id为本service_id
						Map<String,Object> paramMap=new HashMap<String, Object>();
						paramMap.put("service_id", i_service_id);
						paramMap.put("i_service_id", array.getJSONObject(i).get("id"));
						stockService.updateIServiceId(paramMap);
						 
					}
				} else {
					// 子节点
					// 根据子节点节点ID，(分类)判断子节点_数据是否存在，有就不插入数据，没有就插入
					if (service_id == 0)
						service_id = i_service_id;
					Map<String,Object> tmp_paramMap=new HashMap<String, Object>();
					tmp_paramMap.put("i_product_id", array.getJSONObject(i).get("sId"));// sId是从(tree树一中)自定义一个分类id(这个id就是分类)
					tmp_paramMap.put("service_id", array.getJSONObject(i).get("pId"));
					tmp_paramMap.put("cid", cid);
					tmp_paramMap.put("name", array.getJSONObject(i).get("name"));
					Integer num = stockService.checkService_product(tmp_paramMap);// 找商品没有就添加
					if (num ==null) {
						//不存在此商品
						stockService.insertService_product(tmp_paramMap);
					}else{
						//存在此商品，则更新i_product_id
						tmp_paramMap.put("product_id", num);
						stockService.updateIProductId(tmp_paramMap);
					}

				}

			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		response.getWriter().print("ok");
		return null;
	}

	// 删除
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=del", method = { RequestMethod.POST, RequestMethod.GET })
	public String del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LogerUtil.logRequest(request, logger, "del");
		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int cid = c.getCommunity_id();
			Integer parentId = Integer.parseInt(request.getParameter("parentId"));
			Boolean isParent = Boolean.parseBoolean(request.getParameter("isParent"));
			Integer i_service_id = null;
			Map map = new HashMap();
			//判断是否是删除分类
			if (isParent) {
				//删除分类
				map.put("service_id", parentId);
				map.put("community_id", cid);
				int i_product_id = stockService.getService_base_id(map);
				int num = stockService.delService_base(map); // 删除service_base
				int all = stockService.delService_product_all(map); // 删除当前下service_base的service_product
			}else{
				//删除指定商品
				map.put("product_id", parentId);
				map.put("community_id", cid);
				int num2 = stockService.delService_product(map);
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		return null;
	}
	
	/*
	 * 跟新分类
	 */
	@RequestMapping(params = "method=updata", method = { RequestMethod.POST, RequestMethod.GET })
	public String updata(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LogerUtil.logRequest(request, logger, "updata");
		try {
			Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
			int cid = c.getCommunity_id();
			int service_id = Integer.valueOf(request.getParameter("childrenId"));
			String[] i_service_id = request.getParameter("parentId").substring(0, request.getParameter("parentId").lastIndexOf(",")).split(",");
			Map map = new HashMap();
			for(String str : i_service_id) {
				map.put("service_id", service_id);
				map.put("cid", cid);
				map.put("product_id", Integer.parseInt(str));
				int num = stockService.update(map);
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return "jsp/error";
		}
		return null;
	}

}

package com.brilliantreform.sc.service.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.service.service.SevService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("service.do")
public class ServiceCtrl {

	private static Logger LOGGER = Logger.getLogger(ServiceCtrl.class);

	private static int size = 20;

	@Autowired
	private SevService sevService;

	@RequestMapping(params = "method=listService")
	public String listService(HttpServletRequest request) {
		try {
			LogerUtil.logRequest(request, LOGGER, "listService");

			Map param = CommonUtil.getParameterMap(request);
			Integer pageIndex = CommonUtil.safeToInteger(param.get("pageIndex"), 1);
			int begin = (pageIndex - 1) * size;
			param.put("begin", begin);
			param.put("size", size);
			param.put("community_id", ((Community)request.getSession().getAttribute("selectCommunity")).getCommunity_id());

			List<ServiceVo> list = sevService.searchServiceInfo(param);
			int count = sevService.searchServiceInfoCount(param);

			request.setAttribute("list", list);
			request.setAttribute("pageCount", CommonUtil.getPageCount(count, size));
			request.setAttribute("pageIndex", count == 0 ? 0 : pageIndex);
			request.setAttribute("queryParam", param);
		} catch (Exception e) {
			LOGGER.error("listService error", e);
			return "/jsp/error";
		}
		return "/jsp/service/listService";
	}

	@RequestMapping(params = "method=loadEditPage")
	public String loadEditPage(HttpServletRequest request) {
		try {
			LogerUtil.logRequest(request, LOGGER, "loadEditPage");

			Integer service_id = CommonUtil.safeToInteger(request.getParameter("service_id"), null);
			if(service_id != null) {
				ServiceVo service = sevService.getServiceInfoById(service_id);
				if(service != null) {
					request.setAttribute("service", service);
				}
			}
		} catch (Exception e) {
			LOGGER.error("loadEditPage error", e);
			return "/jsp/error";
		}
		return "/jsp/service/editService";
	}

	@RequestMapping(params = "method=saveService")
	public String saveService(HttpServletRequest request, ServiceVo service) {
		try {
			LogerUtil.logRequest(request, LOGGER, "saveService");

			if(service != null) {
				sevService.saveServiceInfo(service);
			}
		} catch (Exception e) {
			LOGGER.error("saveService error", e);
			return "/jsp/error";
		}
		return "redirect:/service.do?method=listService";
	}

	@RequestMapping(params = "method=removeService")
	public String removeService(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "fail";
		try {
			LogerUtil.logRequest(request, LOGGER, "removeService");

			Integer service_id = CommonUtil.safeToInteger(request.getParameter("service_id"), null);
			if(service_id != null) {
				sevService.removeService(service_id);
				result = "ok";
			}
		} catch (Exception e) {
			LOGGER.error("removeService error", e);
			return "/jsp/error";
		}
		response.getWriter().print(result);
		return null;
	}

    @RequestMapping(params = "method=showListServicePage")
    public String showListServicePage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/service/listService";
    }

    @RequestMapping(params = "method=searchService")
    public void searchService(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, LOGGER, "searchService");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            List<ServiceVo> list = sevService.searchServiceInfo(param);
            int count = sevService.searchServiceInfoCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            LOGGER.error("searchService error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=loadEditPageV4")
    public String loadEditPageV4(HttpServletRequest request) {
        try {
            LogerUtil.logRequest(request, LOGGER, "loadEditPageV4");

            Integer service_id = CommonUtil.safeToInteger(request.getParameter("service_id"), null);
            if(service_id != null) {
                ServiceVo service = sevService.getServiceInfoById(service_id);
                if(service != null) {
                    request.setAttribute("service", service);
                }
            }
        } catch (Exception e) {
            LOGGER.error("loadEditPageV4 error", e);
            return "/jsp/error";
        }
        return "/new/jsp/service/editService";
    }

    @RequestMapping(params = "method=saveServiceV4")
    public void saveServiceV4(HttpServletRequest request, HttpServletResponse response, ServiceVo service) {
        String result = "fail";
        try {
            LogerUtil.logRequest(request, LOGGER, "saveServiceV4");

            if(service != null) {
                sevService.saveServiceInfo(service);
                result = "success";
            }
        } catch (Exception e) {
            LOGGER.error("saveService error", e);
        }
        CommonUtil.outToWeb(response, result);
    }

}

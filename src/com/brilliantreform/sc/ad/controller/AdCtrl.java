package com.brilliantreform.sc.ad.controller;

import com.brilliantreform.sc.ad.service.AdService;
import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.service.po.MainAD;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.service.service.SevService;
import com.brilliantreform.sc.sys.po.SysJob;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("ad.do")
public class AdCtrl {

	private static Logger LOGGER = Logger.getLogger(AdCtrl.class);

	private static int size = 20;

	@Autowired
	private AdService adService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private SevService sevService;

	@RequestMapping(params = "method=listAd")
	public String listAd(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, LOGGER, "listAd");

			Map param = CommonUtil.getParameterMap(request);
			Integer pageIndex = CommonUtil.safeToInteger(param.get("pageIndex"), 1);
			int begin = (pageIndex - 1) * size;
			param.put("begin", begin);
			param.put("size", size);
			param.put("community_id", ((Community)request.getSession().getAttribute("selectCommunity")).getCommunity_id());

			List<Map> list = adService.searchAd(param);
			int count = adService.searchAdCount(param);

			if(count == 0) {
				pageIndex = 0;
			}

			request.setAttribute("list", list);
			request.setAttribute("pageCount", CommonUtil.getPageCount(count, size));
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("queryParam", param);

			request.setAttribute("community_list", communityService.getCommunityList());
		} catch (Exception e) {
			LOGGER.error("listAd error", e);
			return "/jsp/error";
		}
		return "jsp/ad/listAd";
	}

	@RequestMapping(params = "method=showAd")
	public String showAd(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, LOGGER, "showAd");

			Integer ad_id = CommonUtil.safeToInteger(request.getParameter("ad_id"), null);
			if(ad_id != null) {
				MainAD ad = adService.getAdById(ad_id);
				if(ad != null) {
					request.setAttribute("ad", ad);
				}
			}

			//request.setAttribute("community_list", communityService.getCommunityList());
			//获取服务信息
			Map param = new HashMap();
			param.put("community_id", ((Community) (request.getSession().getAttribute("selectCommunity"))).getCommunity_id());
			param.put("begin", 0);
			param.put("size", Integer.MAX_VALUE);
			request.setAttribute("service_list", sevService.searchServiceInfo(param));
		} catch (Exception e) {
			LOGGER.error("showAd error", e);
			return "/jsp/error";
		}
		return "jsp/ad/showAd";
	}

	@RequestMapping(params = "method=saveAd")
	public String saveAd(HttpServletRequest request, HttpServletResponse response, MainAD ad) {
		try {
			LogerUtil.logRequest(request, LOGGER, "saveAd");

			if(ad != null) {
				adService.saveAd(ad);
			}
		} catch (Exception e) {
			LOGGER.error("saveAd error", e);
			return "/jsp/error";
		}
		return "redirect:/ad.do?method=listAd";
	}

	@RequestMapping(params = "method=removeAd")
	public String removeAd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "fail";
		try {
			LogerUtil.logRequest(request, LOGGER, "removeAd");

			Integer ad_id = CommonUtil.safeToInteger(request.getParameter("ad_id"), null);
			if(ad_id != null) {
				adService.deleteAd(ad_id);
				result = "ok";
			}
		} catch (Exception e) {
			LOGGER.error("removeAd error", e);
			return "/jsp/error";
		}
		response.getWriter().print(result);
		return null;
	}

	@RequestMapping(params = "method=removeAdBatch")
	public void removeAdBatch(HttpServletRequest request, HttpServletResponse response){
		String result = "fail";
		try {
			LogerUtil.logRequest(request, LOGGER, "removeAdBatch");

			String ids = CommonUtil.safeToString(request.getParameter("ids"), null);
			if(StringUtils.isNotBlank(ids)) {
				String[] id_array = ids.split(",");
				for(String ad_id : id_array) {
					Integer id = CommonUtil.safeToInteger(ad_id, null);
					if(id != null) {
						adService.deleteAd(id);
					}
				}
				result = "ok";
			}
		} catch (Exception e) {
			LOGGER.error("removeAdBatch error", e);
		}
		CommonUtil.outToWeb(response, result);
	}

	@RequestMapping(params = "method=showListAdPage")
	public String showListAdPage(HttpServletRequest request, HttpServletResponse response) {
		return "/new/jsp/ad/listAd";
	}

	@RequestMapping(params = "method=listAdV4")
	public void listAdV4(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, LOGGER, "listAdV4");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
			CommonUtil.parseParamCommunityId(request, param);

			List<Map> list = adService.searchAd(param);
			int count = adService.searchAdCount(param);

			pager = GridUtil.setPagerResult(pager, list, count);

		} catch (Exception e) {
			LOGGER.error("listAdV4 error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

	@RequestMapping(params = "method=showAdV4")
	public String showAdV4(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, LOGGER, "showAdV4");

			Integer ad_id = CommonUtil.safeToInteger(request.getParameter("ad_id"), null);
			if(ad_id != null) {
				MainAD ad = adService.getAdById(ad_id);
				if(ad != null) {
					request.setAttribute("ad", ad);
				}
			}

			//request.setAttribute("community_list", communityService.getCommunityList());
			//获取服务信息
			//Map param = new HashMap();
			//param.put("community_id", ((Community) (request.getSession().getAttribute("selectCommunity"))).getCommunity_id());
			//param.put("begin", 0);
			//param.put("size", Integer.MAX_VALUE);
			//request.setAttribute("service_list", sevService.searchServiceInfo(param));
		} catch (Exception e) {
			LOGGER.error("showAdV4 error", e);
			return "/new/jsp/500";
		}
		return "/new/jsp/ad/showAd";
	}

	@RequestMapping(params = "method=saveAdV4")
	public void saveAdV4(HttpServletRequest request, HttpServletResponse response, MainAD ad) {
		Integer ad_id = 0;
		try {
			LogerUtil.logRequest(request, LOGGER, "saveAd");

			if(ad != null) {
				adService.saveAd(ad);
				ad_id = ad.getAd_id();
			}
		} catch (Exception e) {
			LOGGER.error("saveAd error", e);
		}
		CommonUtil.outToWeb(response, ad_id + "");
	}

    /**
     * 获取该门店的服务信息
     * */
    @RequestMapping(params = "method=findServiceInfo")
    public void findServiceInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, LOGGER, "findServiceInfo");

            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);

            if(community_id != null) {
                Map param = new HashMap();
                param.put("community_id", community_id);
                param.put("begin", 0);
                param.put("size", Integer.MAX_VALUE);
                List<ServiceVo> list = sevService.searchServiceInfo(param);
                if(CollectionUtils.isNotEmpty(list)) {
                    CommonUtil.outToWeb(response, JSONArray.fromObject(list).toString());
                }
            }
        } catch (Exception e) {
            LOGGER.error("findServiceInfo error", e);
        }
    }
}

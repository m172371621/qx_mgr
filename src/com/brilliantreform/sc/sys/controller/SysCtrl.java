package com.brilliantreform.sc.sys.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.confgure.po.Config_community;
import com.brilliantreform.sc.sys.init.InitCacheBean;
import com.brilliantreform.sc.sys.po.SysMenu;
import com.brilliantreform.sc.sys.po.SysSetting;
import com.brilliantreform.sc.sys.service.SysService;
import com.brilliantreform.sc.sys.utils.CacheUtil;
import com.brilliantreform.sc.utils.*;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sys.do")
public class SysCtrl {

	private static Logger LOGGER = Logger.getLogger(SysCtrl.class);

	@Autowired
	private SysService sysService;
    @Autowired
    private InitCacheBean initCacheBean;

    @RequestMapping(params = "method=showListCommunityConfigPage")
    public String showListCommunityConfigPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/sys/listCommunityConfig";
    }

	@RequestMapping(params = "method=searchCommunityConfig")
	public void searchCommunityConfig(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, LOGGER, "searchCommunityConfig");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
			CommonUtil.parseParamCommunityId(request, param);

			List<Map> list = sysService.searchCommunityConfig(param);
			int count = sysService.searchCommunityConfigCount(param);

			pager = GridUtil.setPagerResult(pager, list, count);

		} catch (Exception e) {
			LOGGER.error("searchCommunityConfig error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

    @RequestMapping(params = "method=getCommunityConfigById")
    public void getCommunityConfigById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, LOGGER, "getCommunityConfigById");

            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            String config_id = request.getParameter("config_id");

            if(community_id != null && StringUtils.isNotBlank(config_id)) {
                Config_community config = sysService.getCommunityConfigById(community_id, config_id);
                if(config != null) {
                    CommonUtil.outToWeb(response, JSONObject.fromObject(config).toString());
                }
            }

        } catch (Exception e) {
            LOGGER.error("getCommunityConfigById error", e);
        }
    }

    @RequestMapping(params = "method=saveCommunityConfig")
    public void saveCommunityConfig(Config_community config, HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "saveCommunityConfig");

            Integer community_id_old = CommonUtil.safeToInteger(request.getParameter("community_id_old"), null);
            String config_id_old = request.getParameter("config_id_old");

            if(config != null) {
                sysService.saveCommunityConfig(config, community_id_old, config_id_old);
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("saveCommunityConfig error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=removeCommunityConfig")
    public void removeCommunityConfig(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "removeCommunityConfig");

            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            String config_id = request.getParameter("config_id");

            if(community_id != null && StringUtils.isNotBlank(config_id)) {
                sysService.removeCommunityConfig(community_id, config_id);
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("removeCommunityConfig error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=showListSettingPage")
    public String showListSettingPage(HttpServletRequest request) {
        try {
            List<String> moduleList = sysService.findSettingModules();
            request.setAttribute("moduleList", moduleList);
        } catch (Exception e) {
            LOGGER.error("showListSettingPage error", e);
        }
        return "/new/jsp/sys/listSetting";
    }

    @RequestMapping(params = "method=showReloadCachePage")
    public String showReloadCachePage(HttpServletRequest request) {
        return "/new/jsp/sys/reloadCache";
    }

    @RequestMapping(params = "method=searchSetting")
    public void searchSetting(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, LOGGER, "searchSetting");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            //CommonUtil.parseParamCommunityId(request, param);

            List<Map> list = sysService.searchSetting(param);
            int count = sysService.searchSettingCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            LOGGER.error("searchSetting error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=reloadCache")
    public void reloadCache(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "reloadCache");

            String[] types = request.getParameterValues("type");
            if(types != null && types.length > 0) {
                for(String type : types) {
                    CacheManager cacheManager = CacheManager.getInstance();
                    Cache commonCache = cacheManager.getCache(CacheUtil.commonCacheName);
                    Cache dictCache = cacheManager.getCache(DictUtil.cacheName);
                    Cache settingCache = cacheManager.getCache(SettingUtil.cacheName);

                    if("setting".equals(type)) {
                        initCacheBean.reloadSettingCache(settingCache);
                    } else if("dict".equals(type)) {
                        initCacheBean.reloadDictCache(dictCache);
                    } else if("community".equals(type)) {
                        initCacheBean.reloadCommunity(commonCache);
                    }
                }
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("reloadCache error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=showListMenuPage")
    public String showListMenuPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, LOGGER, "showListMenuPage");

        } catch (Exception e) {
            LOGGER.error("showListMenuPage error", e);
        }
        return "/new/jsp/sys/listMenu";
    }

    @RequestMapping(params = "method=getMenuTree")
    public void getMenuTree(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, LOGGER, "getMenuTree");

            List<Map> menuList = sysService.findMenuTree();
            CommonUtil.outToWeb(response, JSONArray.fromObject(menuList).toString());

        } catch (Exception e) {
            LOGGER.error("getMenuTree error", e);
        }
    }

    @RequestMapping(params = "method=searchMenu")
    public void searchMenu(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, LOGGER, "searchMenu");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            //CommonUtil.parseParamCommunityId(request, param);

            List<Map> list = sysService.searchMenu(param);
            int count = sysService.searchMenuCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            LOGGER.error("searchMenu error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=getMenuById")
    public void getMenuById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, LOGGER, "getMenuById");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            if(objid != null) {
                SysMenu menu = sysService.getMenuById(objid);
                if(menu != null) {
                    CommonUtil.outToWeb(response, JSONObject.fromObject(menu).toString());
                }
            }

        } catch (Exception e) {
            LOGGER.error("getMenuById error", e);
        }
    }

    @RequestMapping(params = "method=saveMenu")
    public void saveMenu(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "saveMenu");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            String name = request.getParameter("name");
            String logo = request.getParameter("logo");
            Integer parentid = CommonUtil.safeToInteger(request.getParameter("parentid"), 0);
            Integer type = CommonUtil.safeToInteger(request.getParameter("type"), 1);
            String url = request.getParameter("url");
            String remark = request.getParameter("remark");

            if(StringUtils.isNotBlank(name) && parentid != null && type != null) {
                SysMenu menu = null;
                if (objid != null) {
                    menu = sysService.getMenuById(objid);
                } else {
                    menu = new SysMenu();
                    menu.setSort(sysService.getMaxMenuSort(parentid) + 1);
                }

                if(menu != null) {
                    menu.setName(name);
                    menu.setLogo(logo);
                    menu.setParentid(parentid);
                    menu.setType(type);
                    menu.setUrl(url);
                    menu.setRemark(remark);
                    menu.setRemovetag(0);
                    sysService.saveMenu(menu);

                    result.setResult(true);
                }
            }

        } catch (Exception e) {
            LOGGER.error("saveMenu error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=removeMenu")
    public void removeMenu(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "removeMenu");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);

            if(objid != null) {
                sysService.removeMenu(objid);
                result.setResult(true);
            }

        } catch (Exception e) {
            LOGGER.error("removeMenu error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=getSettingById")
    public void getSettingById(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);

            if(objid != null) {
                SysSetting setting = sysService.getSettingById(objid);
                if(setting != null) {
                    CommonUtil.outToWeb(response, JSONObject.fromObject(setting).toString());
                }
            }
        } catch (Exception e) {
            LOGGER.error("getSettingById error", e);
        }
    }

    @RequestMapping(params = "method=saveSetting")
    public void saveSetting(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            String module = request.getParameter("module");
            String name = request.getParameter("name");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");

            if(StringUtils.isNotBlank(module) && StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
                SysSetting setting = null;
                if(objid != null) {
                    setting = sysService.getSettingById(objid);
                } else {
                    setting = new SysSetting();
                }

                if(setting != null) {
                    setting.setModule(module);
                    setting.setName(name);
                    setting.setValue(value);
                    setting.setRemark(remark);
                    sysService.saveSetting(setting);

                    //刷新缓存
                    CacheManager cacheManager = CacheManager.getInstance();
                    Cache settingCache = cacheManager.getCache(SettingUtil.cacheName);
                    initCacheBean.reloadSettingCache(settingCache);
                    result.setResult(true);
                }

            }

        } catch (Exception e) {
            LOGGER.error("saveSetting error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=removeSetting")
    public void removeSetting(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            if(objid != null) {
                sysService.deleteSetting(objid);
                //刷新缓存
                CacheManager cacheManager = CacheManager.getInstance();
                Cache settingCache = cacheManager.getCache(SettingUtil.cacheName);
                initCacheBean.reloadSettingCache(settingCache);
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("removeSetting error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }
}

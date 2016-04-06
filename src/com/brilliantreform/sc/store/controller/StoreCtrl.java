package com.brilliantreform.sc.store.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.store.po.Store;
import com.brilliantreform.sc.store.service.StoreService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("store.do")
public class StoreCtrl {
    @Autowired
    private StoreService storeService;

    private Logger logger = Logger.getLogger(StoreCtrl.class);

    @RequestMapping(params = "method=storePage", method = {RequestMethod.POST, RequestMethod.GET})
    public String storePage(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "storePage");
        try {
            List<Community> list = (List<Community>) request.getSession().getAttribute("user_community");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("user_info");
            Integer type = CommonUtil.safeToInteger(request.getParameter("type"), null);
            Integer obj_id = CommonUtil.safeToInteger(request.getParameter("obj_id"), null);
            Store store = null;
            if (obj_id != null) {
                if (type == null && null != obj_id) {
                    Map map = new HashMap();
                    map.put("obj_id", obj_id);
                    map.put("cache_id", CommonUtil.safeToInteger(request.getParameter("cache_id"), null));
                    store = storeService.selStore(map);
                    String[] temp = store.getAddress().split("_");
                    request.setAttribute("store", store);
                    request.setAttribute("province", temp[0]);
                    request.setAttribute("city", temp[1]);
                    request.setAttribute("area", temp[2]);
                    request.setAttribute("address", temp[3]);
                    request.setAttribute("obj", store.getObj_id());
                    request.setAttribute("objid", CommonUtil.safeToInteger(request.getParameter("cache_id"), null));
                    request.setAttribute("cache_id", CommonUtil.safeToInteger(request.getParameter("cache_id"), null));
                } else {
                    store = storeService.seachStore(obj_id);
                    String[] temp = store.getAddress().split("_");
                    request.setAttribute("store", store);
                    request.setAttribute("province", temp[0]);
                    request.setAttribute("city", temp[1]);
                    request.setAttribute("area", temp[2]);
                    request.setAttribute("address", temp[3]);
                    request.setAttribute("obj", store.getObj_id());
                    request.setAttribute("objid", 4);
                    request.setAttribute("cache_id", store.getCache_id());
                }
            }
            request.setAttribute("list", list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "new/jsp/500";
        }
        return "new/jsp/store/store";
    }

    @RequestMapping(params = "method=saveCache_Store", method = {RequestMethod.POST, RequestMethod.GET})
    public String saveCache_Store(HttpServletRequest request, Store store) {
        LogerUtil.logRequest(request, logger, "saveCache_Store");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            MgrUser mgruser = (MgrUser) request.getSession().getAttribute("user_info");
            store.setCache_id(CommonUtil.safeToInteger(request.getParameter("cache_id"), null));
            store.setAddress(store.getProvince() + "_" + store.getCity() + "_" + store.getArea() + "_" + store.getAddress());
            store.setUser_id(mgruser.getUser_id());
            store.setUpdoortime(store.getUpdoortime().replace(",", "_"));
            store.setWorktime(store.getWorktime().replace(",", "_"));
            storeService.addStore(store);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "new/jsp/500";
        }
        return "new/jsp/store/store";
    }

    @RequestMapping(params = "method=storePageList")
    public String storePageList(HttpServletRequest request) {
        LogerUtil.logRequest(request, logger, "storePageList");
        try {
            MgrUser mgruser = (MgrUser) request.getSession().getAttribute("user_info");
            Integer type = CommonUtil.safeToInteger(request.getParameter("type"), null);
            if (type == 1) {
                return "new/jsp/store/storeList_main";
            } else if (type == 2) {

                return "new/jsp/store/storeList_sub";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "new/jsp/500";
        }
        return "new/jsp/404";
    }

    @RequestMapping(params = "method=storeList", method = {RequestMethod.GET, RequestMethod.POST})
    public void storeList(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "storeList");
        Pager pager = new Pager();
        try {
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));
            Map param = GridUtil.parseGridPager(pager);
            Community c = (Community) request.getSession().getAttribute("selectCommunity");// 获取小区编号
            int cid = c.getCommunity_id();
            MgrUser mgruser = (MgrUser) request.getSession().getAttribute("user_info");
            Map map = new HashMap();
            map.put("start", param.get("begin"));
            map.put("size", param.get("size"));
            map.put("user_id", mgruser.getUser_id());
            map.put("cache_id", param.get("cache_id"));
            if (mgruser.getUser_id() == 1) {
                map.put("user_id", null);
            }
            List<Store> list = storeService.storeList(map);
            int count = storeService.storeListCount(map);
            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=updateStore", method = {RequestMethod.POST, RequestMethod.GET})
    public void updateStore(HttpServletRequest request, HttpServletResponse response, Store store) {
        LogerUtil.logRequest(request, logger, "updateStore");
        try {
            if (request.getParameter("value") != null) {
                store.setQuestion(request.getParameter("value"));
            }
            Integer obj_id = CommonUtil.safeToInteger(request.getParameter("obj_id"), null);
            store.setObj_id(obj_id);
            store.setCache_id(CommonUtil.safeToInteger(request.getParameter("cache_id"), null));
            store.setAddress(store.getProvince() + "_" + store.getCity() + "_" + store.getArea() + "_" + store.getAddress());
            store.setUpdoortime(store.getUpdoortime().replace(",", "_"));
            store.setWorktime(store.getWorktime().replace(",", "_"));
            storeService.updateStore(store);
            CommonUtil.outToWeb(response, "ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @RequestMapping(params = "method=seachStore", method = {RequestMethod.POST, RequestMethod.GET})
    public void seachStore(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "seachStore");
        try {
            Integer obj_id = CommonUtil.safeToInteger(request.getParameter("obj_id"), null);
            Store store = storeService.seachStore(obj_id);
            CommonUtil.outToWeb(response, store.getQuestion());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @RequestMapping(params = "method=updateStoreQuestion", method = {RequestMethod.POST, RequestMethod.GET})
    public void updateStoreQuestion(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "updateStoreQuestion");
        try {
            String questionVaue = request.getParameter("value");
            Integer obj_id = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            if (questionVaue != null && !"".equals(questionVaue) && obj_id != null) {
                storeService.updateStoreQuestion(questionVaue, obj_id);
                CommonUtil.outToWeb(response, "ok");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    //addCommunity_base
    @RequestMapping(params = "method=addCommunity_base", method = {RequestMethod.POST, RequestMethod.GET})
    public void addCommunity_base(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "addCommunity_base");
        try {
            Integer obj_id = CommonUtil.safeToInteger(request.getParameter("obj_id"), null);
            if (obj_id != null) {
                /**
                 *判断是否有上层组织  null最上层 否则 把上层组织Id 插入到`org_info_pid中
                 */
                Community community = storeService.queryChildrenId(obj_id);
                /**
                 * 物业（property_base）
                 */
                Store store = storeService.seachStore(obj_id);
                if (null != store) {
                    community.setCommunity_addr(store.getAddress()); //地址
                    community.setCommunity_Dec(store.getStorename());//备注 和小区名一样
                    community.setCommunity_name(store.getStorename());//小区名
                    community.setProperty_tel(store.getPersonincharge_phone()); //联系电话
                    community.setQx_tel(store.getPhone()); //服务电话
                    community.setCity(store.getCity()); //市
                    community.setStatus(1); // 默认为1
                    community.setOrg_info_phone(store.getPhone()); //联系人电话
                    community.setOrg_info_area(store.getAddress());//全部地址
                    community.setOrg_info_person(store.getPersonincharge());//负责人电话
                    community.setOrg_info_location(store.getCoordinate());//坐标
                    community.setOrg_info_type(4); //经营组织类型 1=总部 2=个体户 3=公司 4=门店
                    if (null != community) {
                        community.setOrg_info_pid(community.getCommunity_id()); //上层组织ID
                    }
                    /**
                     * 插入(community_base)
                     */
                    storeService.addCommunity(community);
                    /**
                     * 更改store为审核通过 状态为2
                     */
                    Map map = new HashMap();
                    map.put("obj_id", obj_id);
                    map.put("cache_id", 2);
                    storeService.updateCache_id(map);
                    CommonUtil.outToWeb(response, "ok");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    //tijiao
    @RequestMapping(params = "method=tijiao", method = {RequestMethod.POST, RequestMethod.GET})
    public void tijiao(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "tijiao");
        try {
            Integer obj_id = CommonUtil.safeToInteger(request.getParameter("obj_id"), null);
            if (null != obj_id) {
                Map map = new HashMap();
                map.put("obj_id", obj_id);
                map.put("cache_id", 1);
                storeService.updateCache_id(map);
                CommonUtil.outToWeb(response, "ok");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

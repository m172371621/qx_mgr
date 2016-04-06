package com.brilliantreform.sc.weixin.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.weixin.po.WeixinTuiJian;
import com.brilliantreform.sc.weixin.service.WeixinTuiJianService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.brilliantreform.sc.utils.LogerUtil.logRequest;

/**
 * Created by Lm on 2015/12/14 0014.
 * 微信推荐
 */
@Controller
@RequestMapping("weixintuijianctrl.do")
public class WeixinTuiJianCtrl {

    private static Logger logger = Logger.getLogger(WeixinTuiJianCtrl.class);
    @Autowired
    private WeixinTuiJianService weixintuijianservice;


    @RequestMapping(params = "method=weixintuijianListPage")
    public String weixinqxxListPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            logRequest(request, logger, "weixintuijianListPage");
            return "new/jsp/weixin/weixintuijian";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 查询 Weixinqxx
     *
     * @return List<Weixinqxx>
     */
    @RequestMapping(params = "method=selweixintuijianList")
    public void selWeixinqxxList(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            logRequest(request, logger, "selweixintuijianList");
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));
            Community c = (Community) request.getSession().getAttribute("selectCommunity");
            int cid = c.getCommunity_id();
            Map param = GridUtil.parseGridPager(pager);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("state", param.get("state"));
            map.put("phone", param.get("phone"));
            map.put("my_recommend_code", param.get("my_recommend_code"));
            map.put("name", param.get("name"));
            map.put("begin", param.get("begin"));
            map.put("size", param.get("size"));
            List<WeixinTuiJian> list = weixintuijianservice.selWeixinqxxList(map);
            int count = weixintuijianservice.selWeixinqxxListcount(map);
            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=updateWeixintuijian")
    public void updateWeixintuijian(HttpServletRequest request, HttpServletResponse response) {
        try {
            logRequest(request, logger, "updateWeixintuijian");
            String openid = request.getParameter("openid");
            Integer state = CommonUtil.safeToInteger(request.getParameter("state"), null);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("openid", openid);
            WeixinTuiJian weixinqxx = weixintuijianservice.selWeixinqxx(map); //根据openid 查找信息
            if(weixinqxx.getOther_recommend_code() != null && state == 2) {
                map.put("openid", null);
                map.put("my_recommend_code", weixinqxx.getOther_recommend_code());
                weixintuijianservice.updateWeixinqxxTuiJian(map);
            }
            map.put("openid", openid);
            map.put("my_recommend_code", null);
            map.put("state", state); //状态
            map.put("name", weixinqxx.getName()); //姓名
            map.put("phone", weixinqxx.getPhone());//手机
            weixintuijianservice.updateWeixinqxxTuiJian(map);
            response.getWriter().print("OK");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

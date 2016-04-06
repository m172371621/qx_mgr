package com.brilliantreform.sc.activity.controller;

import com.alibaba.fastjson.JSON;
import com.brilliantreform.sc.activity.po.CouponGroup;
import com.brilliantreform.sc.activity.po.CouponInfo;
import com.brilliantreform.sc.activity.po.CouponUnion;
import com.brilliantreform.sc.activity.service.CouponService;
import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("coupon.do")
public class CouponCtrl {
    private static Logger LOGGER = Logger.getLogger(CouponCtrl.class);

    @Autowired
    private CouponService couponService;

    @RequestMapping(params = "method=showListCouponInfoPage")
    public String showListCouponInfoPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/activity/coupon/listCouponInfo";
    }

    @RequestMapping(params = "method=searchCouponInfo")
    public void searchCouponInfo(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);

            List<Map> list = couponService.searchCouponInfo(param);
            int count = couponService.searchCouponInfoCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            LOGGER.error("searchCouponInfo error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=saveCouponInfo")
    public void saveCouponInfo(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            Integer coupon_id = CommonUtil.safeToInteger(request.getParameter("coupon_id"), null);
            Double off_price = CommonUtil.safeToDouble(request.getParameter("off_price"), null);
            Double use_price = CommonUtil.safeToDouble(request.getParameter("use_price"), null);
            Integer cid = CommonUtil.safeToInteger(request.getParameter("cid"), 0);
            Date expire_date = CommonUtil.safeToDate(request.getParameter("expire_date"), "yyyy-MM-dd");

            if(off_price != null && use_price != null) {
                CouponInfo couponInfo = null;
                if(coupon_id != null) {
                    couponInfo = couponService.getCouponInfoById(coupon_id);
                } else {
                    couponInfo = new CouponInfo();
                }

                if(couponInfo != null) {
                    couponInfo.setOff_price(off_price);
                    couponInfo.setUse_price(use_price);
                    couponInfo.setCid(cid);
                    couponInfo.setExpire_date(expire_date != null ? expire_date : DateUtils.addMonths(new Date(), 1));
                    couponService.saveCouponInfo(couponInfo);
                    result.setResult(true);
                }
            }
        } catch (Exception e) {
            LOGGER.error("saveCouponInfo error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=removeCoupon")
    public void removeCoupon(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            Integer coupon_id = CommonUtil.safeToInteger(request.getParameter("coupon_id"), null);

            if(coupon_id != null) {
                couponService.removeCouponInfo(coupon_id);
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("removeCoupon error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=getCouponInfoById")
    public void getCouponInfoById(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer coupon_id = CommonUtil.safeToInteger(request.getParameter("coupon_id"), null);

            if(coupon_id != null) {
                CouponInfo couponInfo = couponService.getCouponInfoById(coupon_id);
                if(couponInfo != null) {
                    CommonUtil.outToWeb(response, JSONObject.fromObject(couponInfo).toString());
                }
            }
        } catch (Exception e) {
            LOGGER.error("getCouponInfoById error", e);
        }
    }

    @RequestMapping(params = "method=showListUserCouponPage")
    public String showListUserCouponPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/activity/coupon/listUserCoupon";
    }

    @RequestMapping(params = "method=searchUserCoupon")
    public void searchUserCoupon(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);

            List<Map> list = couponService.searchUserCoupon(param);
            int count = couponService.searchUserCouponCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            LOGGER.error("searchUserCoupon error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=showListCouponGroupPage")
    public String showListCouponGroupPage(HttpServletRequest request, HttpServletResponse response) {
        //获取所有的有效的红包
        Map map = new HashMap();
        map.put("begin", 0);
        map.put("size", Integer.MAX_VALUE);
        List<Map> couponInfoList = couponService.searchCouponInfo(map);
        request.setAttribute("couponInfoList", couponInfoList);
        return "/new/jsp/activity/coupon/listCouponGroup";
    }

    @RequestMapping(params = "method=searchCouponGroup")
    public void searchCouponGroup(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);

            List<Map> list = couponService.searchCouponGroup(param);
            int count = couponService.searchCouponGroupCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            LOGGER.error("searchCouponGroup error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=getCouponGroupInfo")
    public void getCouponGroupInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            String group_id = request.getParameter("group_id");

            if(StringUtils.isNotBlank(group_id)) {
                CouponGroup couponGroup = couponService.getCouponGroupById(group_id);
                List<Map> couponInfoList = couponService.findCouponUnion(group_id);

                if(couponGroup != null && couponInfoList != null && couponInfoList.size() > 0) {
                    Map map = new HashMap();
                    map.put("group", couponGroup);
                    map.put("couponList", couponInfoList);
                    CommonUtil.outToWeb(response, JSON.toJSONString(map));
                }
            }
        } catch (Exception e) {
            LOGGER.error("getCouponGroupInfo error", e);
        }
    }

    @RequestMapping(params = "method=saveCouponGroup")
    public void saveCouponGroup(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            String group_id = request.getParameter("group_id");
            String name = request.getParameter("name");
            Date expiretime = CommonUtil.safeToDate(request.getParameter("expiretime"), "yyyy-MM-dd HH:mm");
            String[] coupon_id_array = request.getParameterValues("coupon_id");
            String[] amount_array = request.getParameterValues("amount");
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);

            if(StringUtils.isNotBlank(name) && expiretime != null && coupon_id_array != null && coupon_id_array.length > 0 && amount_array != null && amount_array.length > 0) {
                CouponGroup couponGroup = null;
                if(StringUtils.isNotBlank(group_id)) {
                    couponGroup = couponService.getCouponGroupById(group_id);
                } else {
                    couponGroup = new CouponGroup();
                }

                if(couponGroup != null) {
                    boolean insert = false;
                    if(StringUtils.isBlank(group_id)) {
                        group_id = "G" + RandomStringUtils.randomAlphanumeric(7);
                        insert = true;
                    }
                    couponGroup.setGroup_id(group_id);
                    couponGroup.setName(name);
                    couponGroup.setExpiretime(expiretime);
                    couponGroup.setStatus(0);
                    couponGroup.setCid(community_id);
                    if(insert) {
                        couponService.insertCouponGroup(couponGroup);
                    } else {
                        couponService.updateCouponGroup(couponGroup);
                    }

                    //先清空该红包组内所有的红包
                    couponService.removeCouponUnionByGroup(couponGroup.getGroup_id());

                    for(int i = 0; i < coupon_id_array.length; i++) {
                        Integer coupon_id = CommonUtil.safeToInteger(coupon_id_array[i], null);
                        Integer amount = CommonUtil.safeToInteger(amount_array[i], null);
                        if(coupon_id != null && amount != null) {
                            CouponUnion groupUnion = new CouponUnion();
                            groupUnion.setGroup_id(couponGroup.getGroup_id());
                            groupUnion.setCoupon_id(coupon_id);
                            groupUnion.setAmount(amount);
                            couponService.insertCouponUnion(groupUnion);
                        }
                    }
                    result.setResult(true);
                }
            }
        } catch (Exception e) {
            LOGGER.error("saveCouponGroup error", e);
        }
        CommonUtil.outToWeb(response, JSON.toJSONString(result));
    }

    @RequestMapping(params = "method=removeCouponGroup")
    public void removeCouponGroup(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            String group_id = request.getParameter("group_id");

            if(StringUtils.isNotBlank(group_id)) {
                CouponGroup couponGroup = couponService.getCouponGroupById(group_id);
                if(couponGroup != null) {
                    couponGroup.setStatus(1);   //无效
                    couponService.updateCouponGroup(couponGroup);
                    result.setResult(true);
                }
            }
        } catch (Exception e) {
            LOGGER.error("removeCouponGroup error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }
}

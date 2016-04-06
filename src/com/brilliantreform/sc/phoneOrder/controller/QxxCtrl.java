package com.brilliantreform.sc.phoneOrder.controller;

import com.brilliantreform.sc.activity.po.UserCoupon;
import com.brilliantreform.sc.activity.service.CouponService;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.order.enumerate.DeliveryStatusEnum;
import com.brilliantreform.sc.order.po.MsgInfo;
import com.brilliantreform.sc.order.service.MsgInfoService;
import com.brilliantreform.sc.order.service.OrderService;
import com.brilliantreform.sc.phoneOrder.po.DistriOrderBean;
import com.brilliantreform.sc.phoneOrder.service.QxxService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.mgrpo.Role;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.JsonUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import com.brilliantreform.sc.weixin.util.WeixinQxxUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Lm on 2016/1/18.
 */
@Controller
@RequestMapping("qxxCtrl.do")
public class QxxCtrl {
    private static Logger logger = Logger.getLogger(QxxCtrl.class);

    @Autowired
    private QxxService qxxService;
    @Autowired
    private MsgInfoService msgInfoService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserService userService;


    /**
     * 转到新区享侠登陆页面 login.jsp
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=loginNewWeb", method = {RequestMethod.POST,
            RequestMethod.GET})
    public String loginNewWeb(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "loginNewWeb");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            //获取code
            String code = request.getParameter("code");
            logger.info("code=" + code);
            if (request.getSession().getAttribute("weixin_openid") == null) {
                if (null != code) {
                    String weixin_openid = WeixinQxxUtil.getCode_Openid(code);
                    request.getSession().setAttribute("weixin_openid", weixin_openid);
                }
            }
            request.setAttribute("code", code);
            return "new/jsp/newQxx/index";
        } catch (Exception e) {
            logger.error("error", e);
            return "jsp/error";
        }
    }

    /**
     * 验证 用户名 和 密码  支持微信自动登陆
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=login_validate", method = {RequestMethod.POST,
            RequestMethod.GET})
    public String login_validate(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "login_validate");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String code = request.getParameter("code");
            if (request.getSession().getAttribute("weixin_openid") == null) {
                //获取code
                logger.info("code=" + code);
                if (null != code && code != "") {
                    String weixin_openid = WeixinQxxUtil.getCode_Openid(code);
                    request.getSession().setAttribute("weixin_openid", weixin_openid);
                }
            }
            String msg = ""; //提示信息
            String username = request.getParameter("username"); //登陆名
            String password = request.getParameter("password"); //登陆密码
            MgrUser mgrUser = null;
            String openid = (String) request.getSession().getAttribute("weixin_openid"); //从Session得到openid
            logger.info("openid=" + openid);
            Map<String, Object> mapMgr = null;
            if (StringUtils.isNotBlank(openid)) {
                mapMgr = qxxService.selOpenidMgrUser(openid);
            }
            if (null != mapMgr) {
                Integer user_id = CommonUtil.safeToInteger(mapMgr.get("user_id"), null);
                if (null != user_id) {
                    mgrUser = qxxService.selMgrUserByid(user_id);
                    username = mgrUser.getLoginname();
                    password = mgrUser.getPassword();
                    MgrUser mgrUser2 = qxxService.getMgrUser(username);
                    if (username.equals(mgrUser2.getLoginname()) && password.equals(mgrUser2.getPassword())) {
                        if (user_id > 0) {
                            boolean isAdmin = false;
                            List<Role> list_role = this.qxxService.getUserRole(user_id);
                            for (Role role : list_role) {
                                if (role.getRole_id() == 1) {
                                    isAdmin = true;
                                    break;
                                }
                            }
                            List<Community> community_list = this.qxxService.findUserCommunity(isAdmin ? null : user_id);
                            request.getSession().setAttribute("community_list", community_list);
                        }
                        msg = "auto";
                        request.getSession().setAttribute("qxx_mgr", mgrUser2);
                        response.getWriter().print(JsonUtil.result2Json(true, msg));
                        return null;
                    }
                }
            }
            if (StringUtils.isNotBlank(username) || StringUtils.isNotBlank(password)) {
                mgrUser = qxxService.getMgrUser(username);
                if (null != mgrUser && password.equals(mgrUser.getPassword()) && username.equals(mgrUser.getLoginname())) {
                    if (mgrUser.getActivation() == 0) {
                        msg = "账户禁止登陆";
                    } else {
                        if (StringUtils.isNotBlank(openid)) {
                            mgrUser.setOpenid(openid);
                            mgrUser.setUser_id(mgrUser.getUser_id());
                            qxxService.insertMgrUserOpenid(mgrUser);
                        }
                        if (mgrUser.getUser_id() > 0) {
                            boolean isAdmin = false;
                            List<Role> list_role = this.qxxService.getUserRole(mgrUser.getUser_id());
                            for (Role role : list_role) {
                                if (role.getRole_id() == 1) {
                                    isAdmin = true;
                                    break;
                                }
                            }
                            List<Community> community_list = this.qxxService.findUserCommunity(isAdmin ? null : mgrUser.getUser_id());
                            request.getSession().setAttribute("community_list", community_list);
                        }
                        request.getSession().setAttribute("qxx_mgr", mgrUser);
                        response.getWriter().print(JsonUtil.result2Json(true, msg));
                        return null;
                    }
                } else {
                    msg = "用户名或密码错误";
                }
            }
            response.getWriter().print(JsonUtil.result2Json(false, msg));
            return null;
        } catch (Exception e) {
            logger.error("error", e);
            return null;
        }
    }

    /**
     * 验证 用户名 和 密码 2 普通登陸
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=login_validate2", method = {RequestMethod.POST,
            RequestMethod.GET})
    public String login_validate2(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String code = request.getParameter("code");
            String msg = ""; //提示信息
            String username = request.getParameter("username"); //登陆名
            String password = request.getParameter("password"); //登陆密码
            MgrUser mgrUser = null;
            List<Role> list_role = new ArrayList<Role>();
            if (StringUtils.isNotBlank(username) || StringUtils.isNotBlank(password)) {
                mgrUser = qxxService.getMgrUser(username);
                if (null != mgrUser && password.equals(mgrUser.getPassword()) && username.equals(mgrUser.getLoginname())) {
                    if (mgrUser.getActivation() == 0) {
                        msg = "账户禁止登陆";
                    } else {
                        list_role = this.qxxService.getUserRole(mgrUser.getUser_id());
                        Integer temp = CommonUtil.safeToInteger(SettingUtil.getSettingValue("COMMON", "DELIVERY_ROLE"), null);
                        if (null != temp) {
                            if (list_role != null && list_role.size() == 1) {
                                for (Role list : list_role) {
                                    if (list.getRole_id() == 5) {
                                        request.setAttribute("DELIVERY_ROLE", temp);
                                        break;
                                    } else {
                                        request.setAttribute("DELIVERY_ROLE", 0);
                                    }
                                }
                            } else {
                                request.setAttribute("DELIVERY_ROLE", 0);
                            }
                        }
                        if (mgrUser.getUser_id() > 0) {
                            boolean isAdmin = false;
                            for (Role role : list_role) {
                                if (role.getRole_id() == 1) {
                                    isAdmin = true;
                                    break;
                                }
                            }
                            List<Community> community_list = this.qxxService.findUserCommunity(isAdmin ? null : mgrUser.getUser_id());
                            request.getSession().setAttribute("community_list", community_list);
                        }
                        request.getSession().setAttribute("qxx_mgr", mgrUser);
                        response.getWriter().print(JsonUtil.result2Json(true, msg));
                        return null;
                    }
                } else {
                    msg = "用户名或密码错误";
                }
            }
            response.getWriter().print(JsonUtil.result2Json(false, msg));
            return null;
        } catch (Exception e) {
            logger.error("error", e);
            return null;
        }
    }

    @RequestMapping(params = {"method=login_out"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String login_out(HttpServletRequest request) {
        try {
            request.getSession().removeAttribute("qxx_mgr");
        } catch (Exception e) {
            logger.error(e);
        }
        return "new/jsp/newQxx/index";
    }

    /**
     * 工作台
     *
     * @return
     */
    @RequestMapping(params = "method=getWorkbenchCount", method = {RequestMethod.GET, RequestMethod.POST})
    public String getWorkbench(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getWorkbenchCount");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            request.getSession().getAttribute("community_list");
            if (null != mgrUser) {
                Map mapParams = new HashMap();
                mapParams.put("community_list", (List<Community>) request.getSession().getAttribute("community_list"));
                mapParams.put("user_id", mgrUser.getUser_id() == 1 ? null : mgrUser.getUser_id());
                mapParams.put("request", request);
                Map map = qxxService.getWorkbench(mapParams);
                response.getWriter().print(JsonUtil.result2Json(true, map));
            }
        } catch (Exception e) {
            logger.error("error: " + e);
        }
        return null;
    }

    /**
     * 订单 未接单数据列表
     *
     * @return
     */
    @RequestMapping(params = "method=getNotOrderList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getNotOrderList(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getNotOrderList");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            if (null != mgrUser) {
                int size = 10;
                Integer leftIndex = CommonUtil.safeToInteger(request.getParameter("leftIndex"), null);
                Map mapParams = new HashMap();
                mapParams.put("community_list", (List<Community>) request.getSession().getAttribute("community_list"));
                mapParams.put("user_id", mgrUser.getUser_id());
                mapParams.put("size", size);
                mapParams.put("begin", leftIndex == null ? 0 : leftIndex);
                List<Map> orderList = qxxService.getNotOrderList(mapParams);
                Map map = new IdentityHashMap();
                List list = new ArrayList();
                int num = leftIndex == null ? 1 : leftIndex + 1;
                for (Map orders : orderList) {
                    orders.put("pay_type_ext", payType(((Integer) orders.get("pay_type_ext"))));
                    orders.put("num", num);
                    list.add(orders);
                    num++;
                }
                map.put("model", list);
                response.getWriter().print(JsonUtil.result2Json(true, map));
            }
        } catch (Exception e) {
            logger.error("error: " + e);
        }
        return null;
    }

    /**
     * 订单 数据详情
     *
     * @return
     */
    @RequestMapping(params = "method=orderXq", method = {RequestMethod.GET, RequestMethod.POST})
    public String orderXq(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "orderXq");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            String order_serial = request.getParameter("order_serial");
            if (null != mgrUser) {
                Map mapParams = new HashMap();
                mapParams.put("order_serial", order_serial);
                List<Map> orderQds = qxxService.orderXq(mapParams);
                Map order_base = qxxService.selorder_base(mapParams);
                if (null != orderQds && orderQds.size() > 0) {
                    Integer coupon_id = CommonUtil.safeToInteger(order_base.get("coupon_id"), null);
                    Map map = new HashMap();
                    List list = new ArrayList();
                    map.put("order_serial", order_serial);
                    for (Map orderQd : orderQds) {
                        map.put("order_price", orderQd.get("order_price2"));
                        map.put("delivery_phone", orderQd.get("delivery_phone"));
                        map.put("delivery_addr", orderQd.get("delivery_addr"));
                        if (orderQd.get("delivery_time") != null)
                            map.put("delivery_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderQd.get("delivery_time")));
                        map.put("delivery_type", orderQd.get("dtype"));
                        String pay_off_all = (String) orderQd.get("pay_off_all");
                        map.put("refund_money", orderQd.get("refund_money"));
                        map.put("youhui", youhui(pay_off_all, coupon_id));
                        list.add(orderQd);
                    }
                    map.put("model", list);
                    response.getWriter().print(JsonUtil.result2Json(true, map));
                }
            }
        } catch (Exception e) {
            logger.error("error: " + e);
        }
        return null;
    }

    /**
     * 订单 配送中列表
     *
     * @return
     */
    @RequestMapping(params = "method=getDeliveryOrderList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getOrderDeliveryList(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getDeliveryOrderList");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            String order_serial = request.getParameter("order_serial");
            if (null != mgrUser) {
                int size = 10;
                Integer leftIndex = CommonUtil.safeToInteger(request.getParameter("leftIndex"), null);
                Map mapParams = new HashMap();
                mapParams.put("community_list", (List<Community>) request.getSession().getAttribute("community_list"));
                mapParams.put("size", size);
                mapParams.put("user_id", mgrUser.getUser_id() == 1 ? null : mgrUser.getUser_id());
                mapParams.put("distri_staus", DeliveryStatusEnum.ING.getValue());
                mapParams.put("begin", leftIndex == null ? 0 : leftIndex);
                List<Map> deliveryList = qxxService.getDeliveryOrderList(mapParams);
                Map map = new IdentityHashMap();
                List list = new ArrayList();
                int num = leftIndex == null ? 1 : leftIndex + 1;
                for (Map delivery : deliveryList) {
                    delivery.put("refund_money",delivery.get("refund_money"));
                    delivery.put("pay_type_ext", payType(((Integer) delivery.get("pay_type_ext"))));
                    delivery.put("num", num++);
                    list.add(delivery);
                }
                map.put("model", list);
                response.getWriter().print(JsonUtil.result2Json(true, map));
            }
        } catch (Exception e) {
            logger.error("error: " + e);
        }
        return null;
    }

    /**
     * 配送完成
     */
    @RequestMapping(params = "method=finishOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public String finishOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        LogerUtil.logRequest(request, logger, "finishOrder");
        try {
            Integer distriDetailId = Integer.parseInt(request.getParameter("distriDetailId"));
            Integer userid = Integer.parseInt(request.getParameter("user_id"));
            DistriOrderBean distriOrder = new DistriOrderBean();
            distriOrder.setDistri_detail_id(distriDetailId);
            qxxService.finishOrder(distriOrder);
            //卡牌
            MsgInfo msginfo = new MsgInfo();
            msginfo.setMsg_info_phone(userid + "");
            msginfo.setMsg_template_code("0000000008");
            msginfo.setMsg_info_detail("");
            msgInfoService.addMsgInfo(msginfo);
            response.getWriter().print(JsonUtil.result2Json(0, "完成订单", null));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 订单 完成列表
     *
     * @return
     */
    @RequestMapping(params = "method=getFinishOrderList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getOrderFinishList(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getOrderFinishList");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            if (null != mgrUser) {
                int size = 10;
                Integer leftIndex = CommonUtil.safeToInteger(request.getParameter("leftIndex"), null);
                Map mapParams = new HashMap();
                mapParams.put("community_list", (List<Community>) request.getSession().getAttribute("community_list"));
                mapParams.put("size", size);
                mapParams.put("begin", leftIndex == null ? 0 : leftIndex);
                List<Map> finishList = qxxService.getOrderFinishList(mapParams);
                Map map = new IdentityHashMap();
                List list = new ArrayList();
                int num = leftIndex == null ? 1 : leftIndex + 1;
                for (Map orders : finishList) {
                    orders.put("pay_type_ext", payType(((Integer) orders.get("pay_type_ext"))));
                    orders.put("num", num++);
                    list.add(orders);
                }
                map.put("model", list);
                response.getWriter().print(JsonUtil.result2Json(true, map));
            }
        } catch (Exception e) {
            logger.error("error: " + e);
        }
        return null;
    }

    /**
     * 未称重 列表
     *
     * @return
     */
    @RequestMapping(params = "method=getNotWeighList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getNotWeighList(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getWeighingList");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            if (null != mgrUser) {
                int size = 10;
                Integer leftIndex = CommonUtil.safeToInteger(request.getParameter("leftIndex"), null);
                Map mapParams = new HashMap();
                mapParams.put("community_list", (List<Community>) request.getSession().getAttribute("community_list"));
                mapParams.put("size", size);
                mapParams.put("begin", leftIndex == null ? 0 : leftIndex);
                List<Map> notWeighList = qxxService.getNotWeighList(mapParams);
                Map map = new IdentityHashMap();
                List list = new ArrayList();
                int num = leftIndex == null ? 1 : leftIndex + 1;
                for (Map orders : notWeighList) {
                    orders.put("num", num++);
                    list.add(orders);
                }
                map.put("model", list);
                response.getWriter().print(JsonUtil.result2Json(true, map));
            }
        } catch (Exception e) {
            logger.error("error: " + e);
        }
        return null;
    }

    /**
     * 称重中 列表
     *
     * @return
     */
    @RequestMapping(params = "method=getWeighingList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getWeighingList(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getWeighingList");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            if (null != mgrUser) {
                int size = 10;
                Integer leftIndex = CommonUtil.safeToInteger(request.getParameter("leftIndex"), null);
                Map mapParams = new HashMap();
                mapParams.put("community_list", (List<Community>) request.getSession().getAttribute("community_list"));
                mapParams.put("size", size);
                mapParams.put("begin", leftIndex == null ? 0 : leftIndex);
                List<Map> notWeighList = qxxService.getWeighingList(mapParams);
                Map map = new IdentityHashMap();
                List list = new ArrayList();
                int num = leftIndex == null ? 1 : leftIndex + 1;
                for (Map orders : notWeighList) {
                    orders.put("num", num++);
                    list.add(orders);
                }
                map.put("model", list);
                response.getWriter().print(JsonUtil.result2Json(true, map));
            }
        } catch (Exception e) {
            logger.error("error: " + e);
        }
        return null;
    }

    /**
     * 称重完成 列表
     *
     * @return
     */
    @RequestMapping(params = "method=getFinishWeighList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getFinishWeighList(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getFinishWeighList");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            if (null != mgrUser) {
                int size = 10;
                Integer leftIndex = CommonUtil.safeToInteger(request.getParameter("leftIndex"), null);
                Map mapParams = new HashMap();
                mapParams.put("community_list", (List<Community>) request.getSession().getAttribute("community_list"));
                mapParams.put("size", size);
                mapParams.put("begin", leftIndex == null ? 0 : leftIndex);
                List<Map> notWeighList = qxxService.getFinishWeighList(mapParams);
                Map map = new IdentityHashMap();
                List list = new ArrayList();
                int num = leftIndex == null ? 1 : leftIndex + 1;
                for (Map orders : notWeighList) {
                    orders.put("num", num++);
                    list.add(orders);
                }
                map.put("model", list);
                response.getWriter().print(JsonUtil.result2Json(true, map));
            }
        } catch (Exception e) {
            logger.error("error: " + e);
        }
        return null;
    }

    /**
     * 更新 称重商品 状态
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "method=weighOk", method = {RequestMethod.GET, RequestMethod.POST})
    public String weighOk(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LogerUtil.logRequest(request, logger, "weighOk");
        response.setCharacterEncoding("utf-8");
        try {
            String size = request.getParameter("size");
            String params = request.getParameter("params");
            Integer user_id = CommonUtil.safeToInteger(params.split(",")[0], null);
            Integer produc_tid = CommonUtil.safeToInteger(params.split(",")[1], null);
            // 更新用户购物车状态
            Map<String, Object> user_cartMap = new HashMap<String, Object>();
            user_cartMap.put("f_amount", Float.parseFloat(size));
            user_cartMap.put("user_id", user_id);
            user_cartMap.put("product_id", produc_tid);
            qxxService.weighOk(user_cartMap);
            //推送
            MsgInfo msginfo = new MsgInfo();
            msginfo.setMsg_info_phone(user_id + "");
            msginfo.setMsg_template_code("0000000005");
            msginfo.setMsg_info_detail("");
            msgInfoService.addMsgInfo(msginfo);
            response.getWriter().print(JsonUtil.result2Json(true, null));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "jsp/error";
        }
        return null;
    }

    /**
     * 取消 称重商品
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "method=weighCancel", method = {RequestMethod.GET, RequestMethod.POST})
    public String weighCancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LogerUtil.logRequest(request, logger, "pwmanagerCancel");
        response.setCharacterEncoding("UTF-8");
        try {
            String params = request.getParameter("params");
            Integer user_id = CommonUtil.safeToInteger(params.split(",")[0], null);
            Integer produc_tid = CommonUtil.safeToInteger(params.split(",")[1], null);
            int result_code = 0;
            String msg = "";
            // 取消 用户购物车状态
            Map<String, Object> mapParams = new HashMap<String, Object>();
            mapParams.put("user_id", user_id);
            mapParams.put("product_id", produc_tid);
            qxxService.weighCancel(mapParams);
            msg = "取消成功";
            response.getWriter().print(JsonUtil.result2Json(true, msg));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "jsp/error";
        }
        return null;
    }

    /**
     * 生成配送单
     */
    @RequestMapping(params = "method=acceptOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public String acceptOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        try {
            String order_serial = request.getParameter("params");
            if (StringUtils.isNotBlank(order_serial)) {
                Map order = orderService.getOrderBySerial3(order_serial);
                Integer community_id = CommonUtil.safeToInteger(order.get("community_id"), null);
                Map<String, Object> retmap = new HashMap<String, Object>();
                MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
                retmap = qxxService.acceptOrder(mgrUser.getUser_id(), community_id, order_serial);
                if (retmap != null) {
                    Integer p_result_code = (Integer) retmap.get("p_result_code");
                    if (p_result_code == 0)
                        response.getWriter().print(JsonUtil.result2Json(true, null));
                } else {
                    response.getWriter().print(JsonUtil.result2Json(false, null));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 订单设置提货
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=updateOrderStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateOrderStatus(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "updateOrderStatus");
        try {
            String order_serial = request.getParameter("params");
            boolean temp = false;
            if (StringUtils.isNotBlank(order_serial)) {
                orderService.pickUpOrder(order_serial);
                temp = true;
            }
            response.getWriter().print(JsonUtil.result2Json(temp, null));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    // 11 现金 12 刷卡 13混合 21 区享卡 22 支付宝 23 微信
    public String payType(Integer type) {
        if (type == null) return "";
        if (type == 11) return "现金";
        else if (type == 12) return "刷卡";
        else if (type == 13) return "混合";
        else if (type == 21) return "区享卡";
        else if (type == 22) return "支付宝";
        else if (type == 23) return "微信";
        else return "";
    }

    public String youhui(String pay_off_all, Integer coupon_id) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(pay_off_all)) {
            String[] pay_off_all_array = pay_off_all.split("\\|");
            double man_jian = CommonUtil.safeToDouble(pay_off_all_array[0], 0d);
            double shou_dan = CommonUtil.safeToDouble(pay_off_all_array[1], 0d);
            double sui_ji = CommonUtil.safeToDouble(pay_off_all_array[2], 0d);
            if (man_jian > 0 || shou_dan > 0 || sui_ji > 0) {
                builder.append("<div class='card'>");
                builder.append("<div class='card-content'>");
                builder.append(" <div class='card-content-inner'>");
                if (man_jian > 0) {
                    builder.append("<span>满减：</span><span style='float:right;'>" + man_jian + "</span><br/>");
                }
                if (shou_dan > 0) {
                    builder.append("<span>首单减：</span><span style='float:right;'>" + shou_dan + "</span><br/>");
                }
                if (sui_ji > 0) {
                    builder.append("<span>随机减：</span><span style='float:right;'>" + sui_ji + "</span><br/>");
                }
                if (coupon_id != null) {
                    UserCoupon userCoupon = couponService.getUserCouponById(coupon_id);
                    if (userCoupon != null) {
                        double hong_bao = userCoupon.getOff_price();
                        builder.append("<span>红包减：</span><span style='float:right;'>" + hong_bao + "</span><br/>");
                    }
                }
                builder.append("</div>");
                builder.append("</div>");
                builder.append("</div>");
            }
            return builder.toString();
        }
        return "";
    }

    @RequestMapping(params = "method=insertMgruserdistri_worker_addr", method = {RequestMethod.POST, RequestMethod.GET})
    public String insertMgruserdistri_worker_addr(HttpServletRequest request, HttpServletResponse response) {
        try {
            String lng = request.getParameter("lng");
            String lat = request.getParameter("lat");
            String address = request.getParameter("address");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            if (null != mgrUser) {
                qxxService.insertDelivery_address(lng, lat, address, mgrUser.getUser_id());
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    // 取货 列表
    @RequestMapping(params = {"method=getQuHuoOrderList"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String getQuHuoOrderList(HttpServletResponse response, HttpServletRequest request) {
        try {
            LogerUtil.logRequest(request, logger, "getQuHuoOrderList");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            if (null != mgrUser) {
                int size = 10;
                Integer leftIndex = CommonUtil.safeToInteger(request.getParameter("leftIndex"), null);
                Map mapParams = new HashMap();
                mapParams.put("community_list", (List<Community>) request.getSession().getAttribute("community_list"));
                mapParams.put("user_id", mgrUser.getUser_id() == 1 ? null : mgrUser.getUser_id());
                mapParams.put("size", size);
                mapParams.put("distri_staus", DeliveryStatusEnum.WAIT.getValue());
                mapParams.put("begin", leftIndex == null ? 0 : leftIndex);
                List<Map> notWeighList = qxxService.getQuHuoOrderList(mapParams);
                Map map = new IdentityHashMap();
                List list = new ArrayList();
                int num = leftIndex == null ? 1 : leftIndex + 1;
                for (Map orders : notWeighList) {
                    orders.put("num", num++);
                    orders.put("pay_type_ext", payType(((Integer) orders.get("pay_type_ext"))));
                    list.add(orders);
                }
                map.put("model", list);
                response.getWriter().print(JsonUtil.result2Json(true, map));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    // 确定 取货
    @RequestMapping(params = {"method=quHuoOK"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String quHuoOK(HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("UTF-8");
        try {
            String order_serial = request.getParameter("params");
            if (StringUtils.isNotBlank(order_serial)) {
                qxxService.quHuoOK(order_serial);
                response.getWriter().print(JsonUtil.result2Json(true, null));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(params = {"method=selAMap"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String selAMap(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getQuHuoOrderList");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("qxx_mgr");
            Map map = new HashMap();
            Integer distri_staus = CommonUtil.safeToInteger(request.getParameter("distri_staus"), null);
            map.put("community_list", (List<Community>) request.getSession().getAttribute("community_list"));
            map.put("distri_staus", distri_staus == null ? -1 : distri_staus);
            response.getWriter().print(JsonUtil.result2Json(true, qxxService.selAMap(map)));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(params = {"method=searchPage"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String searchPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            String val = request.getParameter("val");
            List list = null;
            if (StringUtils.isNotBlank(val)) {
                list = new ArrayList();
                list.add("测试1" + new Random().nextInt());
                list.add("测试2" + new Random().nextInt());
                list.add("测试3" + new Random().nextInt());
                list.add("测试4" + new Random().nextInt());
                list.add("测试5" + new Random().nextInt());
            }

            response.getWriter().print(JsonUtil.result2Json(true, list));
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
}
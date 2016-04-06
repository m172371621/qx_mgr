package com.brilliantreform.sc.phoneOrder.controller;

import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.order.po.MsgInfo;
import com.brilliantreform.sc.order.service.MsgInfoService;
import com.brilliantreform.sc.order.service.OrderService;
import com.brilliantreform.sc.phoneOrder.po.DistriOrderBean;
import com.brilliantreform.sc.phoneOrder.po.PhoneOrder;
import com.brilliantreform.sc.phoneOrder.service.PhoneOrderService;
import com.brilliantreform.sc.service.service.SevService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.*;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2015/11/30 0030.
 */

@Controller
@RequestMapping(value = "/phoneorderctrl")
public class PhoneOrderCtrl {
    private static Logger logger = Logger.getLogger(PhoneOrderCtrl.class);

    @Autowired
    private PhoneOrderService phoneorderservice;
    @Autowired
    private MsgInfoService msgInfoService;
    @Autowired
    private SevService sevService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private OrderService orderService;


    /**
     * 转到登陆页面 login.jsp
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "loginweb.do", method = {RequestMethod.POST,
            RequestMethod.GET})
    public String loginweb(HttpServletRequest request, HttpServletResponse response) {

        LogerUtil.logRequest(request, logger, "loginweb");
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
                    return loginMgrUser_validate(request, response);
                }
            }
            request.setAttribute("type", request.getParameter("type"));
            return "forward:/phoneorderctrl/loginMgrUser_validate.do";
        } catch (Exception e) {
            logger.error("error", e);
            return "jsp/error";
        }
    }

    /**
     * 验证用户和密码	 mgrUser
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "loginMgrUser_validate.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String loginMgrUser_validate(HttpServletRequest request, HttpServletResponse response) {

        LogerUtil.logRequest(request, logger, "loginMgrUser_validate");
        try {

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String msg = ""; //提示信息
            String mgr_login_name = request.getParameter("distri_worker_login_name"); //登陆名
            String mgr_login_pwd = request.getParameter("distri_worker_login_pwd"); //登陆密码
            MgrUser mgrUser = null;
            String openid = (String) request.getSession().getAttribute("weixin_openid"); //从Session得到openid
            logger.info("openid=" + openid);
            Map<String, Object> mapMgr = null;
            if (StringUtils.isNotBlank(openid)) {
                mapMgr = phoneorderservice.selOpenidMgrUser(openid);
            }

            if (null != mapMgr) {
                Integer user_id = CommonUtil.safeToInteger(mapMgr.get("user_id"), null);
                if (null != user_id) {
                    mgrUser = phoneorderservice.selMgrUserByid(user_id);
                    mgr_login_name = mgrUser.getLoginname();
                    mgr_login_pwd = mgrUser.getPassword();
                    MgrUser mgrUser2 = phoneorderservice.getMgrUser(mgr_login_name);
                    if (mgr_login_name.equals(mgrUser2.getLoginname()) && mgr_login_pwd.equals(mgrUser2.getPassword())) {
                        request.getSession().setAttribute("distri_worker_bean", mgrUser2);
                        request.setAttribute("error", "");
                        return "new/jsp/saleplugins/index";
                    }
                }
            }
            if (StringUtils.isNotBlank(mgr_login_name) || StringUtils.isNotBlank(mgr_login_pwd)) {
                mgrUser = phoneorderservice.getMgrUser(mgr_login_name);
                if (null != mgrUser && mgr_login_pwd.equals(mgrUser.getPassword()) && mgr_login_name.equals(mgrUser.getLoginname())) {
                    if (StringUtils.isNotBlank(openid)) {
                        mgrUser.setOpenid(openid);
                        mgrUser.setUser_id(mgrUser.getUser_id());
                        phoneorderservice.insertMgrUserOpenid(mgrUser);
                    }
                    request.getSession().setAttribute("distri_worker_bean", mgrUser);
                    request.setAttribute("error", "");
                    return "new/jsp/saleplugins/index";
                }
                msg = "用户名或密码错误";
            }
            request.setAttribute("error", msg);
            return "new/jsp/phoneOrder/login";
        } catch (Exception e) {
            logger.error("error", e);
            return "jsp/error";
        }
    }

    /**
     * 注销用户
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "loginOut.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "loginOut");
        try {
            // request.getSession().invalidate();
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            if (null != mgrUser) {
                phoneorderservice.delMgrUserOpenid(mgrUser);
                request.getSession().removeAttribute("distri_worker_bean");
            }
            return "new/jsp/phoneOrder/login";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "jsp/error";
        }
    }

    @RequestMapping(value = "phonePage.do", method = {RequestMethod.POST,
            RequestMethod.GET})
    public String phonePage(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "phonePage");
        try {
            if (QxxUtil.validateSession("distri_worker_bean", request, response)) {
                return "forward:/phoneorderctrl/loginweb.do?type=1";
            }
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            return "new/jsp/phoneOrder/phoneOrder";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "jsp/error";
        }
    }

    @RequestMapping(value = "transf_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String transf_list(HttpServletRequest request, HttpServletResponse response) {

        LogerUtil.logRequest(request, logger, "transf_list");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            return "new/jsp/distri_worker_web/transf_list";

        } catch (Exception e) {

            logger.error(e.getMessage());
            return "jsp/error";
        }

    }

    @RequestMapping(value = "getTransf_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String getTransf_list(HttpServletRequest request, HttpServletResponse response) throws IOException {

        LogerUtil.logRequest(request, logger, "transf_list");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("distri_worker_id", mgrUser.getUser_id());
            map.put("create_time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            List<DistriOrderBean> list = phoneorderservice.getDistriHead(map);
            response.getWriter().print(JsonUtil.result2Json(0, "查询成功", list, list.size()));
        } catch (Exception e) {
            response.getWriter().print(JsonUtil.result2Json(false, null));
            logger.error(e.getMessage());
        }
        return null;

    }

    @RequestMapping(value = "transf_detail.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String transf_detail(HttpServletRequest request, HttpServletResponse response) {

        LogerUtil.logRequest(request, logger, "transf_detail");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            request.setAttribute("phone", request.getParameter("phone"));
            return "new/jsp/distri_worker_web/transf_detail";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "jsp/error";
        }

    }

    @RequestMapping(value = "getTransf_detail.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String getTransf_detail(HttpServletRequest request, HttpServletResponse response) throws IOException {

        LogerUtil.logRequest(request, logger, "getTransf_detail");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("distri_worker_id", mgrUser.getUser_id());
            map.put("phone", request.getParameter("phone"));
            map.put("distri_staus", 1);
            map.put("create_time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            DistriOrderBean list = phoneorderservice.getDistriDetail(map);
            response.getWriter().print(JsonUtil.result2Json(0, "查询成功", list));
        } catch (Exception e) {
            response.getWriter().print(JsonUtil.result2Json(false, null));
            logger.error(e.getMessage());
        }
        return null;

    }

    @RequestMapping(value = "getTransf_product.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String getTransf_product(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");
        LogerUtil.logRequest(request, logger, "getTransf_product");
        try {
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("distri_worker_id", mgrUser.getUser_id());
            map.put("phone", request.getParameter("phone"));
            map.put("distri_staus", 1);
            map.put("create_time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            List<DistriOrderBean> list = phoneorderservice.getDistriProduct(map);
            response.getWriter().print(JsonUtil.result2Json(0, "查询成功", list, list.size()));
        } catch (Exception e) {
            response.getWriter().print(JsonUtil.result2Json(false, null));
            logger.error(e.getMessage());
        }
        return null;

    }

    @RequestMapping(value = "optDetail.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String optDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        LogerUtil.logRequest(request, logger, "optDetail");
        try {
            String phone = request.getParameter("phone");
            String status = request.getParameter("distri_staus");
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("distri_worker_id", mgrUser.getUser_id());
            map.put("phone", phone);
            map.put("distri_staus", Integer.parseInt(status));
            phoneorderservice.optDetail(map);
            if ("2".equals(status)) {
                Map<String, Object> mmap = new HashMap<String, Object>();
                mmap.put("order_status", 3);
                mmap.put("phone", phone);
                mmap.put("distri_worker_id", mgrUser.getUser_id());
                List<DistriOrderBean> list = phoneorderservice.getSerialByPhone(mmap);
                for (DistriOrderBean tmp : list) {
                    mmap.put("order_serial", tmp.getOrder_serial());
                    phoneorderservice.updateOrderStatus(mmap);
                }
            }
            response.getWriter().print(JsonUtil.result2Json(0, "修改成功", null));
        } catch (Exception e) {
            response.getWriter().print(JsonUtil.result2Json(false, null));
            logger.error(e.getMessage());
        }
        return null;

    }

    @RequestMapping(value = "optProduct.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String optProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");
        LogerUtil.logRequest(request, logger, "getTransf_product");
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            map.put("distri_worker_id", mgrUser.getUser_id());
            map.put("order_serial", request.getParameter("order_serial"));
            map.put("distri_staus", Integer.parseInt(request.getParameter("distri_staus")));
            map.put("remark", request.getParameter("remark"));
            phoneorderservice.optDetail(map);
            phoneorderservice.optProduct(map);
            response.getWriter().print(JsonUtil.result2Json(0, "修改成功", null));
        } catch (Exception e) {
            response.getWriter().print(JsonUtil.result2Json(false, null));
            logger.error(e.getMessage());
        }
        return null;

    }

    /**
     * 跳转到待接单页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "transf_order_detail", method = {RequestMethod.POST, RequestMethod.GET})
    public String transf_o_detail(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "transf_detail");
        try {
            if (QxxUtil.validateSession("distri_worker_bean", request, response)) {
                return "forward:/phoneorderctrl/loginweb.do?type=1";
            }
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            return "new/jsp/phoneOrder/phoneOrder";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "jsp/error";
        }

    }

    /**
     * 跳转到待接单页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "transf_order_detail.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String transf_order_detail(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "transf_detail");
        try {
            if (QxxUtil.validateSession("distri_worker_bean", request, response)) {
                return "forward:/phoneorderctrl/loginweb.do?type=1";
            }
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            return "new/jsp/distri_worker_web/deliverlist";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "jsp/error";
        }

    }

    /**
     * 跳转到 未到货页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "listOrderPage.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String listOrderPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (QxxUtil.validateSession("distri_worker_bean", request, response)) {
                return "forward:/phoneorderctrl/loginweb.do?type=1";
            }
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            return "new/jsp/phoneOrder/listOrder";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "jsp/error";
        }

    }

    /**
     * 返回链接地址
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "getValidate.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String getValidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LogerUtil.logRequest(request, logger, "transf_detail");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            return "new/jsp/saleplugins/index";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "jsp/error";
        }
    }

    /**
     * 获取待接单的订单列表
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "getOrderList.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String getOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");
        LogerUtil.logRequest(request, logger, "getOrderList");
        try {
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("community_id", mgrUser.getCommunity_id());
            map.put("create_time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            List<PhoneOrder> list = phoneorderservice.getOrderList(map);
            response.getWriter().print(JsonUtil.result2Json(0, "查询成功", list, list.size()));
        } catch (Exception e) {
            response.getWriter().print(JsonUtil.result2Json(false, null));
            logger.error(e.getMessage());
        }
        return null;

    }

    /**
     * 获取已接单配送列表
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "getDeliveryList.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String getDeliveryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        LogerUtil.logRequest(request, logger, "getDeliveryList");
        try {
            MgrUser mgruser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("community_id", mgruser.getCommunity_id());
            map.put("distri_worker_id", mgruser.getUser_id());
            List<DistriOrderBean> list = phoneorderservice.getDeliveryDetail(map);
            response.getWriter().print(JsonUtil.result2Json(0, "查询成功", list, list.size()));
        } catch (Exception e) {
            response.getWriter().print(JsonUtil.result2Json(false, null));
            logger.error(e.getMessage());
        }
        return null;

    }

    /**
     * 生成配送单
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "acceptOrder.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String acceptOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        try {
            String order_serial = request.getParameter("order_serial");

            if(StringUtils.isNotBlank(order_serial)) {
                Map order = orderService.getOrderBySerial3(order_serial);
                Integer community_id = CommonUtil.safeToInteger(order.get("community_id"), null);

                Map<String, Object> retmap = new HashMap<String, Object>();
                MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
                retmap = phoneorderservice.acceptOrder(mgrUser.getUser_id(), community_id, order_serial);
                if (retmap != null) {
                    Integer p_result_code = (Integer) retmap.get("p_result_code");
                    if (p_result_code == 0)
                        response.getWriter().print(JsonUtil.result2Json(0, "接单成功", null));
                } else {
                    response.getWriter().print(JsonUtil.result2Json(false, null));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.getWriter().print(JsonUtil.result2Json(false, null));
        }
        return null;

    }

    /**
     * 配送完成
     */
    @RequestMapping(value = "finishOrder.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String finishOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");
        LogerUtil.logRequest(request, logger, "finishOrder");
        try {
            Integer distriDetailId = Integer.parseInt(request.getParameter("distriDetailId"));
            Integer userid = Integer.parseInt(request.getParameter("user_id"));
            DistriOrderBean distriOrder = new DistriOrderBean();
            distriOrder.setDistri_detail_id(distriDetailId);
            phoneorderservice.finishOrder(distriOrder);
            //卡牌
            MsgInfo msginfo = new MsgInfo();
            msginfo.setMsg_info_phone(userid + "");
            msginfo.setMsg_template_code("0000000008");
            msginfo.setMsg_info_detail("");
            msgInfoService.addMsgInfo(msginfo);
            response.getWriter().print(JsonUtil.result2Json(0, "完成订单", null));
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.getWriter().print(JsonUtil.result2Json(false, null));
        }
        return null;
    }

    /**
     * 获取新订单数
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "getNewOrderCount.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String getNewOrderCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        LogerUtil.logRequest(request, logger, "getNewOrderCount");
        try {
            int result_code = 0;
            String result_dec = "查询成功";
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("community_id", mgrUser.getCommunity_id());
            map.put("distri_worker_id", mgrUser.getUser_id());
            result_code = phoneorderservice.getNewOrderCount(map);
            List<PhoneOrder> phoneOrders = phoneorderservice.getOrderList(map);
            int size = phoneOrders.size();
            response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, size));
        } catch (Exception e) {
            response.getWriter().print(JsonUtil.result2Json(false, null));
            logger.error(e.getMessage());
        }
        return null;

    }

    /**
     * 获取提货订单
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "getBaseProduct.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String getBaseProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        LogerUtil.logRequest(request, logger, "getBaseProduct");
        try {
            MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("distri_worker_bean");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("community_id", mgrUser.getCommunity_id());
            List<PhoneOrder> list = phoneorderservice.getBaseProduct(map);
            response.getWriter().print(JsonUtil.result2Json(0, "查询成功", list, list.size()));
        } catch (Exception e) {
            response.getWriter().print(JsonUtil.result2Json(false, null));
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
    @RequestMapping(value = "updateOrderStatus.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String updateOrderStatus(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "updateOrderStatus");
        try {
            String order_serial = request.getParameter("order_serial");
            Integer type = CommonUtil.safeToInteger(request.getParameter("type"), null);
            phoneorderservice.updatePreSaleOrder(order_serial, type);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "test.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String weiXinTest(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "weiXinTest");
        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                System.out.print(echostr);
                response.getWriter().print(echostr);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
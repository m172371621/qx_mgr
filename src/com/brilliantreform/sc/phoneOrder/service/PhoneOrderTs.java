package com.brilliantreform.sc.phoneOrder.service;

import com.brilliantreform.sc.phoneOrder.po.PhoneOrder;
import com.brilliantreform.sc.saleplugins.po.Pwmanager;
import com.brilliantreform.sc.saleplugins.service.PwsearchService;
import com.brilliantreform.sc.utils.SettingUtil;
import com.brilliantreform.sc.weixin.util.WeixinQxxUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2015/12/31.
 * 推送
 */
@Component
public class PhoneOrderTs {

    private static final Logger LOGGER = Logger.getLogger(PhoneOrderTs.class);

    @Autowired
    private PhoneOrderService phoneOrderService;
    @Autowired
    private PwsearchService pwsearchService;

    @Scheduled(cron = "0/10 * *  * * ? ")
    public void tuiSong() {
        JSONObject jsonObject = order();
        LOGGER.info(jsonObject);
        JSONObject jsonObject2 = chengZhong();
        LOGGER.info(jsonObject2);
    }

    /**
     * Created by Lm on 2015/12/31.
     * 区享侠_订单推送
     */
    public JSONObject order() {
        try {
            List<PhoneOrder> phoneOrders = phoneOrderService.weiXinTs();
            if (null != phoneOrders && phoneOrders.size() > 0) {
                for (PhoneOrder phoneOrder : phoneOrders) {
                    Integer userid = phoneOrder.getUser_id();
                    if (null != userid) {
                        Map param = new HashMap();
                        param.put("userid", userid);
                        List<Map> temps = phoneOrderService.getUserId(param);
                        if (null != temps && temps.size() > 0) {
                            //消息组装
                            for (Map temp : temps) {
                                List<Map> tempUser_id =  phoneOrderService.getPhoneOpenid((Integer) temp.get("user_id"));
                                for(Map user_id : tempUser_id) {
                                    Map msgmap = new HashMap();
                                    msgmap.put("FIRST", "您收到一条新的订单");
                                    msgmap.put("TEMPLATEID", "s-ZYX9t_Apsrth21mzbXv_3MctRXIrFd6hju2Zk-WNU");
                                    msgmap.put("URL", SettingUtil.getSettingValue("COMMON", "MGR_SERVER_HOST") + "/phoneorderctrl/phonePage.do");
                                    msgmap.put("KEYWORD1", phoneOrder.getOrder_serial());
                                    msgmap.put("KEYWORD2", phoneOrder.getDelivery_type() == 1 ? "自提" : "送货上门");
                                    msgmap.put("KEYWORD3", phoneOrder.getOrder_price());
                                    msgmap.put("KEYWORD4", phoneOrder.getOrder_time());
                                    msgmap.put("KEYWORD5", phoneOrder.getNick() + "   " + phoneOrder.getPhone());
                                    msgmap.put("REMARK", "商品信息：" + phoneOrder.getProduct_name() + "...");
                                    WeixinQxxUtil.sendMsgOrder(msgmap, user_id);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("order_error:" + e.getMessage());
        }
        return null;
    }

    /**
     * Created by Lm on 2015/12/31.
     * 区享侠订_称重推送
     */
    public JSONObject chengZhong() {
        try {
            List<Pwmanager> pwmanagers = pwsearchService.pwmanagerTS();
            if (null != pwmanagers && pwmanagers.size() > 0) {
                for (Pwmanager pwmanager : pwmanagers) {
                    Integer userid = pwmanager.getUser_id();
                    if (null != userid) {
                        Map param = new HashMap();
                        param.put("userid", userid);
                        List<Map> temps = phoneOrderService.getUserId(param);
                        if (null != temps && temps.size() > 0) {
                            //消息组装
                            for (Map temp : temps) {
                                List<Map> tempUser_id =  phoneOrderService.getPhoneOpenid((Integer) temp.get("user_id"));
                                for(Map user_id : tempUser_id) {
                                    //消息组装
                                    Map msgmap = new HashMap();
                                    msgmap.put("FIRST", "您收到一条新的称重订单");
                                    msgmap.put("TEMPLATEID", "Mvr5ta8u457Dxd2Ldk3tf6_e3JA2wEG9c2yrrEF4HQw");
                                    msgmap.put("URL", SettingUtil.getSettingValue("COMMON", "MGR_SERVER_HOST") + "/weighProduct/pwmanager.do");
                                    msgmap.put("tradeDateTime", pwmanager.getCreateTime());
                                    msgmap.put("orderType", "称重订单");
                                    msgmap.put("customerInfo", pwmanager.getUsername() + "  " + pwmanager.getPhone());
                                    msgmap.put("REMARK", "商品信息：" + pwmanager.getProduct_name() + "...");
                                    WeixinQxxUtil.sendMsgChengZhong(msgmap, user_id);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("chengZhong_error:" + e.getMessage());
        }
        return null;
    }
}




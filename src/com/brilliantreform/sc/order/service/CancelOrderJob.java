package com.brilliantreform.sc.order.service;

import com.brilliantreform.sc.ad.dao.AdDao;
import com.brilliantreform.sc.order.po.OrderRefund;
import com.brilliantreform.sc.order.po.OrderRefundBase;
import com.brilliantreform.sc.order.po.OrderRefundProduct;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 取消超过20分钟未付款的订单
 * Created by shangwq on 2015/9/21.
 */
@Component
public class CancelOrderJob {

    private static final Logger LOGGER = Logger.getLogger(CancelOrderJob.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private AdDao adDao;

    //@Scheduled(cron = "* 0/20 * * * ?")
    public void cancelOrder() {
        try {
            Integer time = CommonUtil.safeToInteger(SettingUtil.getSettingValue("ORDER", "CANCEL_UNPAY_ORDER_TIME"), 20);
            if(time != null) {
                List<Map> orderList = orderService.findUnPayOrder(time);
                for (Map map : orderList) {
                    try {
                        orderRefundService.cancelUnPayOrder(CommonUtil.safeToString(map.get("order_serial"), ""));
                    } catch (Exception e) {
                        LOGGER.error("this order：" + CommonUtil.safeToString(map.get("order_serial"), "") + " system auto cancel order error", e);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("cancel order job error", e);
        }
    }

    public void transferOrderRefund() {
        //所有的退款原数据
        List<OrderRefund> orderRefundList = adDao.findOrderRefund();
        for(OrderRefund orderRefund : orderRefundList) {
            OrderRefundBase refundBase = new OrderRefundBase();
            refundBase.setOrder_serial(orderRefund.getOrder_serial());
            refundBase.setRefund_serial(orderRefund.getRefund_serial());
            refundBase.setUserid(orderRefund.getUserid());
            refundBase.setWorkerid(orderRefund.getWorkerid());
            refundBase.setCommunity_id(orderRefund.getCommunity_id());
            refundBase.setReturn_status(orderRefund.getStatus());
            refundBase.setRefund_money(orderRefund.getRefund_price());
            refundBase.setRefund_type(orderRefund.getRefund_type());
            refundBase.setRefund_status(orderRefund.getRefund_status());
            refundBase.setRefund_reason(orderRefund.getRefund_reason());
            refundBase.setReturn_time(orderRefund.getRefund_time());
            refundBase.setRefund_time(orderRefund.getRefund_time());
            refundBase.setRemark(orderRefund.getRemark());
            refundBase.setCreate_time(orderRefund.getCreate_time());
            refundBase.setRemovetag(0);
            orderRefundService.saveOrderRefundBase(refundBase);

            OrderRefundProduct refundProduct = new OrderRefundProduct();
            refundProduct.setRefund_id(refundBase.getObjid());
            refundProduct.setOrder_serial(orderRefund.getOrder_serial());
            refundProduct.setOrder_id(orderRefund.getOrder_id());
            refundProduct.setProduct_id(orderRefund.getProduct_id());
            refundProduct.setProduct_price(orderRefund.getProduct_price());
            refundProduct.setRefund_amount(orderRefund.getRefund_amount());
            orderRefundService.saveOrderRefundProduct(refundProduct);
        }
    }
}

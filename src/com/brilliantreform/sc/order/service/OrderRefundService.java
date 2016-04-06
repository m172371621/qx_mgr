package com.brilliantreform.sc.order.service;

import com.brilliantreform.sc.activity.dao.CouponDao;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.incomming.dao.IncommingOrderDao;
import com.brilliantreform.sc.incomming.po.ProductRealStockBean;
import com.brilliantreform.sc.order.dao.OrderDao;
import com.brilliantreform.sc.order.dao.OrderRefundDao;
import com.brilliantreform.sc.order.enumerate.OrderPayTypeExtEnum;
import com.brilliantreform.sc.order.enumerate.RefundTypeEnum;
import com.brilliantreform.sc.order.enumerate.ReturnGoodsStatus;
import com.brilliantreform.sc.order.enumerate.ReturnMoneyStatus;
import com.brilliantreform.sc.order.po.*;
import com.brilliantreform.sc.qxcard.dao.QxCardDao;
import com.brilliantreform.sc.qxcard.po.QxCardLog;
import com.brilliantreform.sc.qxcard.po.UserQxCard;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.MathUtil;
import com.brilliantreform.sc.weixin.dao.PayLogDao;
import com.brilliantreform.sc.weixin.util.WXPay;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderRefundService {
    private static Logger logger = Logger.getLogger(OrderRefundService.class);

    @Autowired
    private OrderRefundDao orderRefundDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private IncommingOrderDao incommingOrderDao;
    @Autowired
    private PayLogDao payLogDao;
    @Autowired
    private QxCardDao qxCardDao;
    @Autowired
    private CouponDao couponDao;

    public Integer saveOrderRefund(OrderRefund orderRefund) {
        logger.info("into OrderRefundService saveOrderRefund:" + orderRefund + ";");
        return orderRefundDao.saveOrderRefund(orderRefund);
    }

    public List<OrderRefund> findOrderRefundByOrderId(int order_id) {
        return orderRefundDao.findOrderRefundByOrderId(order_id);
    }

    public double getRefundAmount(int order_id, int[] status, Integer objid) {
        return orderRefundDao.getRefundAmount(order_id, status, objid);
    }

    public boolean isOrderAward(int order_id) {
        return orderRefundDao.isOrderAward(order_id);
    }

    public OrderRefund getOrderRefundById(Integer objid) {
        return orderRefundDao.getOrderRefundById(objid);
    }

    public List<Map> searchRefund(Map param) {
        return orderRefundDao.searchRefund(param);
    }

    public int searchRefundCount(Map param) {
        return orderRefundDao.searchRefundCount(param);
    }

    public Map getSellChangLogByProduct(String order_serial, int product_id) {
        return orderRefundDao.getSellChangLogByProduct(order_serial, product_id);
    }

    public List<OrderRefund> findRefundByRefundSerial(String refund_serial, Integer refund_status) {
        return orderRefundDao.findRefundByRefundSerial(refund_serial, refund_status);
    }

    public double getReGoodsAmount(String order_serial) {
        return orderRefundDao.getReGoodsAmount(order_serial);
    }

    public void saveOrderRefundBase(OrderRefundBase orderRefundBase) {
        if(orderRefundBase != null) {
            if(orderRefundBase.getObjid() == null) {
                orderRefundDao.insertOrderRefundBase(orderRefundBase);
            } else {
                orderRefundDao.updateOrderRefundBase(orderRefundBase);
            }
        }
    }

    public void saveOrderRefundProduct(OrderRefundProduct orderRefundProduct) {
        if(orderRefundProduct != null) {
            if(orderRefundProduct.getObjid() == null) {
                orderRefundDao.insertOrderRefundProduct(orderRefundProduct);
            } else {
                orderRefundDao.updateOrderRefundProduct(orderRefundProduct);
            }
        }
    }

    /**
     * 获取某个小订单还能退货的商品数量：该小订单总数-待处理或已完成的数量
     * */
    public double getCanRefundProductAmount(int order_id, int[] return_status) {
        return orderRefundDao.getCanRefundProductAmount(order_id, return_status);
    }

    /**
     * 获取某个大订单所有退货的商品数量
     * */
    public double sumRefundAmountBySerial(String order_serial, int[] return_status) {
        return orderRefundDao.sumRefundAmountBySerial(order_serial, return_status);
    }

    /**
     * 获取某个大订单的可退款金额
     * */
    public double getCanRefundMoney(String order_serial) {
        return orderRefundDao.getCanRefundMoney(order_serial);
    }

    public List<Map> searchOrderRefundBase(Map param) {
        return orderRefundDao.searchOrderRefundBase(param);
    }

    public int searchOrderRefundBaseCount(Map param) {
        return orderRefundDao.searchOrderRefundBaseCount(param);
    }

    public List<Map> searchOrderRefundProduct(Map param) {
        return orderRefundDao.searchOrderRefundProduct(param);
    }

    public OrderRefundBase getOrderRefundBaseById(int objid) {
        return orderRefundDao.getOrderRefundBaseById(objid);
    }

    public OrderRefundBase getOrderRefundBaseByRefundSerial(String refund_serial, Integer refund_status) {
        return orderRefundDao.getOrderRefundBaseByRefundSerial(refund_serial, refund_status);
    }

    /**
     * 退库存
     */
    public void returnStock(double amount, String order_serial, int product_id, String ip, Integer userid) {
        //获取商品的批次号
        Map log = getSellChangLogByProduct(order_serial, product_id);
        if (log != null) {
            Map param = new HashMap();
            param.put("batch_serial", log.get("batch_serial"));
            param.put("product_id", product_id);
            param.put("order_current_sum", amount);
            param.put("real_stock_sum", amount);
            //更新批次表
            orderDao.update_product_batch_stock(param);
            //更新商品实时库存表
            orderDao.update_product_real_stock(param);
            ProductRealStockBean realStockBean = new ProductRealStockBean();
            realStockBean.setCommunity_id(CommonUtil.safeToInteger(log.get("community_id"), -1));
            realStockBean.setProduct_id(product_id);
            ProductRealStockBean stockBean = incommingOrderDao.getProductStockById(realStockBean);
            if (stockBean != null) {
                //记录库存变动记录
                Map changLog = new HashMap();
                changLog.put("product_id", product_id);
                changLog.put("community_id", log.get("community_id"));
                changLog.put("batch_serial", log.get("batch_serial"));
                changLog.put("order_serial", order_serial);
                changLog.put("log_type", 8);    //用户退货
                changLog.put("order_current_sum", amount);
                changLog.put("stock_sum", stockBean.getReal_stock_sum());
                changLog.put("IP", ip);
                changLog.put("create_by", userid);
                changLog.put("create_type", 2);
                orderDao.insert_stock_change_log(changLog);
            }
        }

    }

    /**
     * 订单退货
     *
     * @param order_id     小订单id
     * @param refundAmount 退货数量
     * @param refundMoney  退货金额
     * @param refundReason 退货原因
     * @param rejectReason 驳回原因
     * @param workerid     操作员id
     * @param ip           操作员IP
     */
    public void returnGoods(int order_id, double refundAmount, double refundMoney, String refundReason, Integer workerid, String ip) throws Exception {
        Order order = orderDao.getOrderById(order_id);
        if (order != null) {
            OrderRefund refund = new OrderRefund();
            refund.setCreate_time(new Date());
            refund.setOrder_id(order_id);
            refund.setOrder_serial(order.getOrder_serial());
            refund.setRefund_serial(CommonUtil.buildRefundNo(order.getOrder_id()));
            refund.setUserid(order.getUser_id());
            refund.setStatus(1);    //退货状态：0.待处理(初始) 1.退货完成 -1.退货驳回
            refund.setRefund_time(new Date());
            refund.setRefund_reason(refundReason);
            refund.setWorkerid(workerid);
            refund.setProduct_id(order.getProduct_id());
            refund.setRefund_amount(refundAmount);
            refund.setProduct_price(order.getProduct_price());
            refund.setRefund_price(refundMoney);
            refund.setCommunity_id(order.getCommunity_id());

            //退货完成的时候需要设置退款状态和退款方式
            int refund_type = 1;    //现金
            if (order.getPay_type_ext() != null) {
                if (order.getPay_type_ext() == 21) {
                    refund_type = 2;    //区享卡
                } else if (order.getPay_type_ext() == 22) {
                    refund_type = 3;    //支付宝
                } else if (order.getPay_type_ext() == 23) {
                    refund_type = 4;    //微信
                }
            }
            refund.setRefund_type(refund_type);        //退款方式

            //只有已提货和已付款状态下才需要退款
            if (order.getOrder_base_status() == 3 || order.getOrder_base_status() == 22) {
                //退款状态：如果是现金、区享卡支付的话就直接是退款完成，支付宝支付是处理中，等待财务进行二次处理
                if (refund_type == 1 || refund_type == 2) {
                    refund.setRefund_status(2);    //退款完成
                } else {
                    refund.setRefund_status(0);    //待处理(微信支付在此也设置成待处理，后面退款的时候会改状态)
                }
            } else {
                refund.setRefund_status(-2);    //无需退款
            }

            saveOrderRefund(refund);

            //如果该订单的商品全都退完了的话，则将该订单设置成已取消
            double refunded_amount = getRefundAmount(order_id, new int[]{1}, null);
            if (refunded_amount == order.getProduct_amount()) {
                orderDao.updateOrderStatusById(order.getOrder_id(), 23);

                //判断大订单的商品是否全退完，如全退完则删除卡牌并将大订单状态改成已取消23
                double all_product_amount = orderDao.getOrderAmount(order.getOrder_serial());
                double all_refund_amount = getReGoodsAmount(order.getOrder_serial());
                if (all_refund_amount == all_product_amount) {
                    qxCardDao.deleteCard(order.getOrder_serial());
                    orderDao.updateOrderBaseStatus(order.getOrder_serial(), 23);

                    //返还红包
                    if(order.getCoupon_id() != null) {
                        couponDao.updateUserCouponStatus(order.getCoupon_id(), 1);
                    }
                }
            }

            //退货完成的情况下退库存
            returnStock(refundAmount, order.getOrder_serial(), order.getProduct_id(), ip, workerid);

            if (order.getOrder_base_status() == 3 || order.getOrder_base_status() == 22) {
                //如果是退款方式是区享卡，则进行退款
                if (refund_type == 2) {
                    List<Map> paylogs = qxCardDao.findQxcardRefund(order.getOrder_serial());
                    double refund_money = Double.parseDouble(refund.getRefund_price() + "");    //需要退款的金额
                    for (Map paylog : paylogs) {
                        if (refund_money > 0) {
                            double pay_price = Double.parseDouble(paylog.get("pay_price") + "");    //该区享卡的支付金额
                            double need_refund_money = pay_price >= refund_money ? refund_money : pay_price;    //本次退款金额
                            refund_money = refund_money - need_refund_money;    //重新计算需要退款的金额

                            UserQxCard userQxCard = new UserQxCard();
                            userQxCard.setUser_id(order.getUser_id());
                            userQxCard.setQxcard_no(paylog.get("qxcard_no") + "");
                            userQxCard.setQxcard_balance(-need_refund_money);
                            userQxCard.setQxcard_status(3);
                            qxCardDao.updateUserQxCard(userQxCard);

                            UserQxCard uq = qxCardDao.getUserQxcardByNo(paylog.get("qxcard_no") + "");
                            if (uq != null) {
                                QxCardLog qxCardLog = new QxCardLog();
                                qxCardLog.setQxcard_no(paylog.get("qxcard_no") + "");
                                qxCardLog.setOrder_serial(order.getOrder_serial());
                                qxCardLog.setOp_price(-need_refund_money);
                                qxCardLog.setQxcard_balance(uq.getQxcard_balance());
                                qxCardLog.setOp_type(2002);    //退款
                                qxCardLog.setUser_id(order.getUser_id());
                                qxCardLog.setUser_ip(ip);
                                qxCardLog.setUser_type(2);
                                qxCardLog.setOp_result(0);
                                qxCardLog.setOp_result_dec("成功");
                                qxCardLog.setOp_dec("用户退货退款");
                                qxCardDao.insertQxCardLog(qxCardLog);
                            }

                        }
                    }
                } else if (refund_type == 4) {
                    //微信支付退款
                    boolean result = false;
                    Map wxpaylog = payLogDao.getWxpaylogByNo(order.getOrder_serial());
                    if (wxpaylog != null && wxpaylog.size() > 0) {
                        String retdata = CommonUtil.safeToString(wxpaylog.get("retdata"), null);
                        if (StringUtils.isNotBlank(retdata)) {
                            Document doc = DocumentHelper.parseText(retdata);
                            Element root = doc.getRootElement();
                            String transaction_id = root.elementTextTrim("transaction_id");
                            Integer total_fee = CommonUtil.safeToInteger(root.elementTextTrim("total_fee"), null);    //单位：分
                            String mch_id = root.elementTextTrim("mch_id"); //商户号
                            if (StringUtils.isNotBlank(transaction_id) && total_fee != null && StringUtils.isNotBlank(mch_id)) {
                                Map refund_map = WXPay.refund(mch_id, refund.getRefund_serial(), transaction_id, total_fee, (int) (refund.getRefund_price() * 100));
                                result = (Boolean) refund_map.get("result");
                                if (result) {
                                    refund.setRefund_status(2);
                                    saveOrderRefund(refund);
                                    //退款成功，将退款记录到weixinpay_log表中
                                    payLogDao.saveWXpayLog(refund.getOrder_serial(), CommonUtil.safeToString(refund_map.get("data"), null), new Date(), -(int) (refund.getRefund_price() * 100));
                                } else {
                                    refund.setRefund_status(-1);
                                    saveOrderRefund(refund);
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * 区享卡退款
     * @param order_serial 订单号
     * @param refund_money 需要退款的金额
     * @param user_id 客户id
     * */
    public boolean refundByQxCard(String order_serial, double refund_money, int user_id) {
        boolean result = false;
        try {
            List<Map> paylogs = qxCardDao.findQxcardRefund(order_serial);
            for (Map paylog : paylogs) {
                if (refund_money > 0) {
                    double pay_price = Double.parseDouble(paylog.get("pay_price") + "");    //该区享卡的支付金额
                    double need_refund_money = pay_price >= refund_money ? refund_money : pay_price;    //本次退款金额
                    refund_money = refund_money - need_refund_money;    //重新计算需要退款的金额

                    UserQxCard userQxCard = new UserQxCard();
                    userQxCard.setUser_id(user_id);
                    userQxCard.setQxcard_no(paylog.get("qxcard_no") + "");
                    userQxCard.setQxcard_balance(-need_refund_money);
                    userQxCard.setQxcard_status(3);
                    qxCardDao.updateUserQxCard(userQxCard);

                    UserQxCard uq = qxCardDao.getUserQxcardByNo(paylog.get("qxcard_no") + "");
                    if (uq != null) {
                        QxCardLog qxCardLog = new QxCardLog();
                        qxCardLog.setQxcard_no(paylog.get("qxcard_no") + "");
                        qxCardLog.setOrder_serial(order_serial);
                        qxCardLog.setOp_price(-need_refund_money);
                        qxCardLog.setQxcard_balance(uq.getQxcard_balance());
                        qxCardLog.setOp_type(2002);    //退款
                        qxCardLog.setUser_id(user_id);
                        qxCardLog.setUser_type(3);  //系统自动
                        qxCardLog.setOp_result(0);
                        qxCardLog.setOp_result_dec("成功");
                        qxCardLog.setOp_dec("用户退货退款");
                        qxCardDao.insertQxCardLog(qxCardLog);
                    }
                }
            }
            result = true;
        } catch (java.lang.Exception e) {
            logger.error("refundByQxcard error", e);
        }
        return result;
    }

    /**
     * 微信退款
     * */
    public boolean refundByWxPay(String order_serial, String refund_serial, double refund_money) {
        boolean result = false;
        try {
            Map wxpaylog = payLogDao.getWxpaylogByNo(order_serial);
            if (wxpaylog != null && wxpaylog.size() > 0) {
                String retdata = CommonUtil.safeToString(wxpaylog.get("retdata"), null);
                if (StringUtils.isNotBlank(retdata)) {
                    Document doc = DocumentHelper.parseText(retdata);
                    Element root = doc.getRootElement();
                    String transaction_id = root.elementTextTrim("transaction_id");
                    Integer total_fee = CommonUtil.safeToInteger(root.elementTextTrim("total_fee"), null);    //单位：分
                    String mch_id = root.elementTextTrim("mch_id"); //商户号
                    if (StringUtils.isNotBlank(transaction_id) && total_fee != null && StringUtils.isNotBlank(mch_id)) {
                        Map refund_map = WXPay.refund(mch_id, refund_serial, transaction_id, total_fee, (int) (refund_money * 100));
                        result = (Boolean) refund_map.get("result");
                        if (result) {
                            //退款成功，将退款记录到weixinpay_log表中
                            payLogDao.saveWXpayLog(order_serial, CommonUtil.safeToString(refund_map.get("data"), null), new Date(), -(int) (refund_money * 100));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("refundByWxPay error", e);
        }
        return result;
    }

    /**
     * 校验订单是否能退款<br>
     * 1.未取消的订单<br>
     * 2.使用过红包的订单只能全部取消，不能部分退
     * */
    public RtnResult checkRefund(String order_serial, List<RefundData> refundDataList) {
        RtnResult result = new RtnResult(false);
        result.setMsg("校验订单信息失败");
        try {
            if (StringUtils.isNotBlank(order_serial) && refundDataList != null && refundDataList.size() > 0) {
                Map order = orderDao.getOrderBySerial3(order_serial);
                if(order != null) {
                    Integer order_status = CommonUtil.safeToInteger(order.get("order_status"), null);
                    if(order_status == 23) {
                        result.setMsg("已取消的订单不能退货");
                    } else {
                        Integer coupon_id = CommonUtil.safeToInteger(order.get("coupon_id"), null);
                        if(coupon_id != null) {
                            //使用过红包，校验本次退货是否是全退，直接判断退货总数是否等于商品总数
                            double total_refund_amount = 0d;
                            for(RefundData refundData : refundDataList) {
                                Double refund_amount = refundData.getRefund_amount();
                                if(refund_amount != null) {
                                    total_refund_amount = MathUtil.add(refund_amount, total_refund_amount);
                                }
                            }
                            double total_order_product_amount = orderDao.getOrderAmount(order_serial);
                            if(total_order_product_amount != total_refund_amount) {
                                result.setMsg("该订单使用了红包，只能取消订单，不能部分退货");
                            } else {
                                result.setResult(true);
                            }
                        } else {
                            result.setResult(true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("checkRefund error", e);
        }
        return result;
    }

    /**
     * 计算退款金额
     * */
    public double calcRefundMoney(String order_serial, List<RefundData> refundDataList) {
        double can_refund_money = getCanRefundMoney(order_serial);	//可退款总额
        double product_money = 0d;	//退货数量*商品价格
        for(RefundData refundData : refundDataList) {
            Map orderProduct = orderDao.getOrderProductMapById(refundData.getOrder_id());
            if(orderProduct != null) {
                Double product_price = CommonUtil.safeToDouble(orderProduct.get("product_price"), null);
                if(product_price != null) {
                    product_money = MathUtil.add(product_money, MathUtil.mul(product_price, refundData.getRefund_amount()));
                }
            }
        }
        return product_money > can_refund_money ? can_refund_money : product_money;
    }

    /**
     * 订单退货
     *
     * @param refundDataList     退款明细数据集
     * @param order_serial 订单号
     * @param refundReason 退货原因
     * @param workerid     操作员id
     * @param ip           操作员IP
     * @param remark       备注
     */
    public RtnResult refund(List<RefundData> refundDataList, String order_serial, double refund_money,
                            String refundReason, Integer workerid, String ip, String remark) {
        RtnResult result = new RtnResult(false);
        result.setMsg("退款失败");
        try {
            Map order = orderDao.getOrderBySerial3(order_serial);
            if (order != null) {
                boolean flag = false;
                RtnResult checkResult = checkRefund(order_serial, refundDataList);
                if (!checkResult.getResult()) {
                    result.setMsg(checkResult.getMsg());
                } else {
                    flag = true;
                }
                if (flag) {
                    double sys_refund_money = calcRefundMoney(order_serial, refundDataList);    //系统计算出来的退款金额
                    //若系统计算出来的退款金额和参数中的不一致，则也无法继续下去
                    if (sys_refund_money != refund_money) {
                        result.setMsg("退款金额有误");
                    } else {
                        flag = true;
                    }
                }
                if (flag) {
                    String refund_serial = CommonUtil.buildRefundNo(order_serial);
                    OrderRefundBase orderRefundBase = new OrderRefundBase();
                    orderRefundBase.setCreate_time(new Date());
                    orderRefundBase.setOrder_serial(order_serial);
                    orderRefundBase.setRefund_serial(refund_serial);
                    orderRefundBase.setUserid(CommonUtil.safeToInteger(order.get("user_id"), null));
                    orderRefundBase.setWorkerid(workerid);
                    orderRefundBase.setCommunity_id(CommonUtil.safeToInteger(order.get("community_id"), null));
                    orderRefundBase.setReturn_status(ReturnGoodsStatus.WAIT.getValue());  //初始化退货状态：待处理，后面退货完成的时候会更改成完成
                    orderRefundBase.setRefund_money(refund_money);

                    int refund_type = RefundTypeEnum.CASH.getValue();    //现金
                    Integer pay_type_ext = CommonUtil.safeToInteger(order.get("pay_type_ext"), null);
                    if (pay_type_ext != null) {
                        if (pay_type_ext == OrderPayTypeExtEnum.QXCARD.getValue()) {
                            refund_type = RefundTypeEnum.QXCARD.getValue();    //区享卡
                        } else if (pay_type_ext == OrderPayTypeExtEnum.ALIPAY.getValue()) {
                            refund_type = RefundTypeEnum.ALIPAY.getValue();    //支付宝
                        } else if (pay_type_ext == OrderPayTypeExtEnum.WXPAY.getValue()) {
                            refund_type = RefundTypeEnum.WXPAY.getValue();    //微信
                        }
                    }
                    orderRefundBase.setRefund_type(refund_type);    //退款方式
                    orderRefundBase.setRefund_status(ReturnMoneyStatus.WAIT.getValue());    //退款状态：待处理
                    orderRefundBase.setRefund_reason(refundReason);
                    orderRefundBase.setRemark(remark);
                    orderRefundBase.setRemovetag(0);

                    saveOrderRefundBase(orderRefundBase);

                    for (RefundData refundData : refundDataList) {
                        OrderRefundProduct orderRefundProduct = new OrderRefundProduct();
                        orderRefundProduct.setRefund_id(orderRefundBase.getObjid());
                        orderRefundProduct.setOrder_serial(order_serial);
                        orderRefundProduct.setOrder_id(refundData.getOrder_id());
                        orderRefundProduct.setProduct_id(refundData.getProduct_id());
                        orderRefundProduct.setProduct_price(refundData.getProduct_price());
                        orderRefundProduct.setRefund_amount(refundData.getRefund_amount());
                        orderRefundProduct.setRefund_money(refundData.getRefund_money());
                        saveOrderRefundProduct(orderRefundProduct);
                    }

                    //退货退库存
                    try {
                        for (RefundData refundData : refundDataList) {
                            returnStock(refundData.getRefund_amount(), order_serial, refundData.getProduct_id(), ip, workerid);
                        }
                        //退货完成的时候更改退货状态
                        orderRefundBase.setReturn_status(ReturnGoodsStatus.FINISH.getValue());
                        orderRefundBase.setReturn_time(new Date());
                    } catch (Exception e) {
                        logger.error("return goods error", e);
                        orderRefundBase.setReturn_status(ReturnGoodsStatus.FAIL.getValue());
                    }
                    saveOrderRefundBase(orderRefundBase);

                    //小订单全部退完的时候状态设成已取消
                    for (RefundData refundData : refundDataList) {
                        double canRefundAmount = orderRefundDao.getCanRefundProductAmount(refundData.getOrder_id(), new int[]{ReturnGoodsStatus.FINISH.getValue()});
                        if (canRefundAmount == 0) {
                            //小订单的商品已经全部退完
                            orderDao.updateOrderStatusById(refundData.getOrder_id(), 23);
                        }
                    }

                    //大订单全部退完的时候状态设成已取消
                    double total_refund_amount = sumRefundAmountBySerial(order_serial, new int[]{ReturnGoodsStatus.FINISH.getValue()});
                    double total_product_amount = orderDao.getOrderAmount(order_serial);
                    if(total_product_amount == total_refund_amount) {
                        orderDao.updateOrderBaseStatus(order_serial, 23);
                        //返还红包
                        Integer coupon_id = CommonUtil.safeToInteger(order.get("coupon_id"), null);
                        if (coupon_id != null) {
                            couponDao.updateUserCouponStatus(coupon_id, 1);
                        }
                    }

                    //退货完成的情况下才进行退款
                    if(orderRefundBase.getReturn_status() == ReturnGoodsStatus.FINISH.getValue()) {
                        //退款，支付宝不在此处退款
                        int refund_status = ReturnMoneyStatus.DONOT.getValue();  //退款状态，默认无需退款
                        Integer order_status = CommonUtil.safeToInteger(order.get("order_status"), null);
                        if (order_status != null) {
                            //只有已提货和已付款状态下才需要退款
                            if (order_status == 3 || order_status == 22) {
                                //退款状态：如果是现金支付的话就直接是退款完成
                                if (refund_type == RefundTypeEnum.CASH.getValue()) {
                                    refund_status = ReturnMoneyStatus.FINISH.getValue();    //退款完成
                                    orderRefundBase.setRefund_time(new Date());
                                } else {
                                    refund_status = ReturnMoneyStatus.WAIT.getValue();  //待处理(区享卡、支付宝、微信退款)
                                }
                            }
                        }
                        orderRefundBase.setRefund_status(refund_status);
                        saveOrderRefundBase(orderRefundBase);

                        if (refund_status == ReturnMoneyStatus.WAIT.getValue()) {
                            if (refund_type == RefundTypeEnum.QXCARD.getValue() || refund_type == RefundTypeEnum.WXPAY.getValue()) {
                                boolean refundResult = false;
                                if (refund_type == RefundTypeEnum.QXCARD.getValue()) {
                                    refundResult = refundByQxCard(order_serial, refund_money, CommonUtil.safeToInteger(order.get("user_id"), null));   //区享卡退款
                                } else {
                                    refundResult = refundByWxPay(order_serial, refund_serial, refund_money);  //微信退款
                                }
                                if (refundResult) {
                                    orderRefundBase.setRefund_status(ReturnMoneyStatus.FINISH.getValue());  //退款完成
                                    orderRefundBase.setRefund_time(new Date());
                                } else {
                                    orderRefundBase.setRefund_status(ReturnMoneyStatus.FAIL.getValue());  //退款失败
                                }
                                saveOrderRefundBase(orderRefundBase);
                            }
                        }
                        result.setResult(true);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("refund error", e);
        }
        return result;
    }

    public void cancelUnPayOrder(String order_serial) {
        Map order = orderDao.getOrderBySerial3(order_serial);
        if(order != null) {
            Integer order_status = CommonUtil.safeToInteger(order.get("order_status"), null);
            //只能取消未支付的订单
            if(order_status != null && order_status.intValue() == 21) {
                //如果该订单存在待处理的退款请求，则跳过该订单
                double wait_refund_amount = orderRefundDao.sumRefundAmountBySerial(order_serial, new int[]{ReturnGoodsStatus.WAIT.getValue()});
                if(wait_refund_amount > 0) {
                    return;
                }
                //构造退货退款基础数据
                List<RefundData> refundDataList = new ArrayList<RefundData>();
                List<Map> orderProductList = orderDao.findOrderProductTemp(order_serial);
                for(Map orderProduct : orderProductList) {
                    Integer order_id = CommonUtil.safeToInteger(orderProduct.get("order_id"), null);
                    Integer product_id = CommonUtil.safeToInteger(orderProduct.get("product_id"), null);
                    Double product_price = CommonUtil.safeToDouble(orderProduct.get("product_price"), null);
                    Double product_amount = CommonUtil.safeToDouble(orderProduct.get("product_amount"), null);
                    Double refunded_amount = CommonUtil.safeToDouble(orderProduct.get("refund_amount"), null);  //已经退货成功的数量

                    if(order_id != null && product_id != null && product_price != null && product_amount != null && refunded_amount != null) {
                        //计算本次可退的数量
                        double refund_amount = product_amount - refunded_amount;
                        if(refund_amount > 0) {
                            RefundData refundData = new RefundData();
                            refundData.setOrder_id(order_id);
                            refundData.setProduct_id(product_id);
                            refundData.setProduct_price(product_price);
                            refundData.setRefund_amount(refund_amount);
                            refundDataList.add(refundData);
                        }
                    }
                }

                if(refundDataList != null && refundDataList.size() > 0) {
                    //计算退款金额
                    double refund_money = calcRefundMoney(order_serial, refundDataList);
                    if(refund_money > 0) {
                        //开始进行退货退款操作
                        refund(refundDataList, order_serial, refund_money, "系统自动取消", null, null, null);
                    }
                }
            }
        }
    }

}

package com.brilliantreform.sc.order.controller;

import com.alibaba.fastjson.JSON;
import com.brilliantreform.sc.activity.po.UserCoupon;
import com.brilliantreform.sc.activity.service.CouponService;
import com.brilliantreform.sc.alipay.po.AliPayLogBean;
import com.brilliantreform.sc.alipay.po.AlipayRefundDetailData;
import com.brilliantreform.sc.alipay.service.AliPayLogService;
import com.brilliantreform.sc.alipay.util.AlipayUtils;
import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.order.enumerate.RefundTypeEnum;
import com.brilliantreform.sc.order.enumerate.ReturnGoodsStatus;
import com.brilliantreform.sc.order.enumerate.ReturnMoneyStatus;
import com.brilliantreform.sc.order.po.Order;
import com.brilliantreform.sc.order.po.OrderRefund;
import com.brilliantreform.sc.order.po.OrderRefundBase;
import com.brilliantreform.sc.order.po.RefundData;
import com.brilliantreform.sc.order.service.OrderRefundService;
import com.brilliantreform.sc.order.service.OrderService;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.po.ProductTags;
import com.brilliantreform.sc.product.service.ProductService;
import com.brilliantreform.sc.qxcard.service.QxCardService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import com.brilliantreform.sc.utils.MathUtil;
import com.brilliantreform.sc.weixin.service.PayLogService;
import com.brilliantreform.sc.weixin.util.WXPay;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("orderRefund.do")
public class OrderRefundCtrl {

	private static Logger logger = Logger.getLogger(OrderRefundCtrl.class);

	private static int size = 20;
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRefundService orderRefundService;
	@Autowired
	private AliPayLogService aliPayLogService;
	@Autowired
	private QxCardService qxCardService;
	@Autowired
	private PayLogService payLogService;
    @Autowired
    private ProductService productService;
	@Autowired
	private CouponService couponService;

	@RequestMapping(params = "method=listReturnMoney")
	public String listReturnMoney(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "listRefund");

			Integer pageIndex = CommonUtil.safeToInteger(request.getParameter("pageIndex"), 1);
			int begin = (pageIndex - 1) * size;
			String username = request.getParameter("username");
			String order_serial = request.getParameter("order_serial");
			String product_name = request.getParameter("product_name");
			String start_refund_time = request.getParameter("start_refund_time");
			String end_refund_time = request.getParameter("end_refund_time");
			String start_create_time = request.getParameter("start_create_time");
			String end_create_time = request.getParameter("end_create_time");
			Integer status = CommonUtil.safeToInteger(request.getParameter("status"), null);
			Integer refund_status = CommonUtil.safeToInteger(request.getParameter("refund_status"), null);

			Map param = new HashMap();
			param.put("begin", begin);
			param.put("size", size);
			param.put("username", username);
			param.put("order_serial", order_serial);
			param.put("product_name", product_name);
			param.put("start_refund_time", start_refund_time);
			Date end_refund_time_d = CommonUtil.safeToDate(end_refund_time, "yyyy-MM-dd");
			if(end_refund_time_d != null) {
				param.put("end_refund_time", DateUtils.addDays(end_refund_time_d, 1));
			}
			param.put("start_create_time", start_create_time);
			Date end_create_time_d = CommonUtil.safeToDate(end_create_time, "yyyy-MM-dd");
			if(end_create_time_d != null) {
				param.put("end_create_time", CommonUtil.formatDate(DateUtils.addDays(end_create_time_d, 1), "yyyy-MM-dd"));
			}
			param.put("status", status);
			param.put("refund_status", refund_status);
			Community community =(Community)request.getSession().getAttribute("selectCommunity");
			param.put("community_id", community.getCommunity_id());

			List<Map> list = orderRefundService.searchRefund(param);
			int count = orderRefundService.searchRefundCount(param);
			if (count == 0) {
				pageIndex = 0;
			}

			request.setAttribute("list", list);
			request.setAttribute("pageCount", CommonUtil.getPageCount(count, size));
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("queryParam", param);

			return "jsp/order/listReturnMoney";
		} catch (Exception e) {
			logger.error("查询退款列表 异常", e);
			return "/jsp/error";
		}
	}

	@RequestMapping(params = "method=listReturnGoods")
	public String listReturnGoods(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "listReturnGoods");

			Integer pageIndex = CommonUtil.safeToInteger(request.getParameter("pageIndex"), 1);
			int begin = (pageIndex - 1) * size;
			String username = request.getParameter("username");
			String order_serial = request.getParameter("order_serial");
			String product_name = request.getParameter("product_name");
			String start_refund_time = request.getParameter("start_refund_time");
			String end_refund_time = request.getParameter("end_refund_time");
			String start_create_time = request.getParameter("start_create_time");
			String end_create_time = request.getParameter("end_create_time");
			Integer status = CommonUtil.safeToInteger(request.getParameter("status"), null);
			Integer refund_status = CommonUtil.safeToInteger(request.getParameter("refund_status"), null);

			Map param = new HashMap();
			param.put("begin", begin);
			param.put("size", size);
			param.put("username", username);
			param.put("order_serial", order_serial);
			param.put("product_name", product_name);
			param.put("start_refund_time", start_refund_time);
			Date end_refund_time_d = CommonUtil.safeToDate(end_refund_time, "yyyy-MM-dd");
			if(end_refund_time_d != null) {
				param.put("end_refund_time", DateUtils.addDays(end_refund_time_d, 1));
			}
			param.put("start_create_time", start_create_time);
			Date end_create_time_d = CommonUtil.safeToDate(end_create_time, "yyyy-MM-dd");
			if(end_create_time_d != null) {
				param.put("end_create_time", CommonUtil.formatDate(DateUtils.addDays(end_create_time_d, 1), "yyyy-MM-dd"));
			}
			param.put("status", status);
			param.put("refund_status", refund_status);
			Community community =(Community)request.getSession().getAttribute("selectCommunity");
			param.put("community_id", community.getCommunity_id());

			List<Map> list = orderRefundService.searchRefund(param);
			int count = orderRefundService.searchRefundCount(param);
			if (count == 0) {
				pageIndex = 0;
			}

			request.setAttribute("list", list);
			request.setAttribute("pageCount", CommonUtil.getPageCount(count, size));
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("queryParam", param);

			return "jsp/order/listReturnGoods";
		} catch (Exception e) {
			logger.error("查询退货列表 异常", e);
			return "/jsp/error";
		}
	}

	@RequestMapping(params = "method=showRefund")
	public String showRefund(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "showRefund");

			Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
			Integer orderid = CommonUtil.safeToInteger(request.getParameter("orderid"), null);

			if(orderid != null) {
				Order order = orderService.getOrderById(orderid);
				if(order != null) {
					request.setAttribute("order", order);
					if(objid != null) {
						OrderRefund refund = orderRefundService.getOrderRefundById(objid);
						if(refund != null) {
							request.setAttribute("refund", refund);
						}
					}

					//获取该订单的退货信息
					List<OrderRefund> refundList = orderRefundService.findOrderRefundByOrderId(order.getOrder_id());
					if(refundList != null && refundList.size() > 0) {
						request.setAttribute("refunds", refundList);
					}
					//可进行退货数量，订单商品总数-处理中的和完成状态的数量
					double can_refund_amount = MathUtil.sub(order.getProduct_amount(), orderRefundService.getRefundAmount(orderid, new int[]{0,1}, objid));
					request.setAttribute("can_refund_amount", can_refund_amount);
				}
				request.setAttribute("objid", objid);
			}
		} catch (Exception e) {
			logger.error("展示退货页面 异常", e);
			return "/jsp/error";
		}
		return "/jsp/order/order_refund";
	}

	/**
	 * 判断该订单卡牌是否中奖，如果中奖且已领取则提示
	 * */
	@RequestMapping(params = "method=checkAward")
	public String checkAward(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean award = false;
		try {
			Integer order_id = CommonUtil.safeToInteger(request.getParameter("order_id"), null);
			if(order_id != null) {
				if(orderRefundService.isOrderAward(order_id)) {
					award = true;
				}
			}
		} catch (Exception e) {
			logger.error("checkCard error", e);
		}
		response.getWriter().print(award);
		return null;
	}

	/**
	 * 退货
	 * */
	@RequestMapping(params = "method=returnGoods")
	public String returnGoods(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "returnGoods");

			Integer order_id = CommonUtil.safeToInteger(request.getParameter("order_id"), null);
			Integer type = CommonUtil.safeToInteger(request.getParameter("type"), null);
			Double refundAmount = CommonUtil.safeToDouble(request.getParameter("refund_amount"), null);
			Double refundMoney = CommonUtil.safeToDouble(request.getParameter("refund_money"), null);
			String refundReason = request.getParameter("refund_reason");
			String rejectReason = request.getParameter("reject_reason");

			if(order_id != null && type != null && refundAmount != null && refundMoney != null) {
				MgrUser worker = (MgrUser) request.getSession().getAttribute("user_info");
				if(type.intValue() == 1) {
					//同意退货请求，进行退货操作
					orderRefundService.returnGoods(order_id, refundAmount, refundMoney, refundReason, worker.getUser_id(), CommonUtil.getIp(request));
				} else {
					//驳回退货请求
					Order order = orderService.getOrderById(order_id);
					if(order != null) {
						OrderRefund refund = new OrderRefund();
						refund.setOrder_id(order_id);
						refund.setOrder_serial(order.getOrder_serial());
						refund.setRefund_serial(CommonUtil.buildRefundNo(order.getOrder_id()));
						refund.setUserid(order.getUser_id());
						refund.setStatus(type);    //退货状态：0.待处理(初始) 1.退货完成 -1.退货驳回
						refund.setRefund_time(new Date());
						refund.setRefund_reason(refundReason);
						refund.setWorkerid(worker.getUser_id());
						refund.setReject_reason(rejectReason);
						refund.setProduct_id(order.getProduct_id());
						refund.setRefund_amount(refundAmount);
						refund.setProduct_price(order.getProduct_price());
						refund.setRefund_price(refundMoney);
						refund.setCommunity_id(order.getCommunity_id());
						refund.setCreate_time(new Date());
						orderRefundService.saveOrderRefund(refund);
					}
				}
			}
		} catch (Exception e) {
			logger.error("退货 异常", e);
			return "/jsp/error";
		}
		return "redirect:/orderRefund.do?method=showRefund&orderid=" + request.getParameter("order_id");
	}

	/**
	 * 退款
	 * */
	@RequestMapping(params = "method=returnMoney")
	public String returnMoney(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "returnMoney");

			Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);

			if(objid != null) {
				OrderRefund refund = orderRefundService.getOrderRefundById(objid);
				if(refund != null) {
					Order order = orderService.getOrderById(refund.getOrder_id());
					//根据退款方式进行退款
					Integer refund_type = refund.getRefund_type();
					if(refund_type != null) {
						if(refund_type.intValue() == 3) {
							//支付宝退款
							AliPayLogBean aliPayLog = aliPayLogService.getAliPayLogByOrderSerial(order.getOrder_serial());
							if (aliPayLog != null) {
								//获取原付款交易号
								String retdata = aliPayLog.getRetdata();
								if (StringUtils.isNotBlank(retdata)) {
									String tradeNo = CommonUtil.safeToString(JSONObject.fromObject(retdata).get("trade_no"), null);
									if (StringUtils.isNotBlank(tradeNo)) {
										String ymd = refund.getRefund_serial().substring(0, 9);
										if(!CommonUtil.formatDate(new Date(), "yyyyMMdd").equals(ymd)) {
											//支付宝退款需要重新生成一下退款批次号，防止日期不正确
											refund.setRefund_serial(CommonUtil.buildRefundNo(refund.getOrder_id()));
											orderRefundService.saveOrderRefund(refund);
										}

										AlipayRefundDetailData data = new AlipayRefundDetailData();
										data.setTradeNo(tradeNo);    //原付款订单
										data.setRefundMoney(refund.getRefund_price());    //退款金额
										data.setRefundReason(refund.getRefund_reason());    //退款原因
										String content = AlipayUtils.buildRefundRequest(refund.getRefund_serial(), data, 2);
										request.setAttribute("content", content);
										logger.info("refund html:" + content);
										return "/jsp/order/alipay_refund";
									}
								}
							}
						} else if(refund_type.intValue() == 4) {
							//微信支付退款
							boolean result = false;
							Map wxpaylog = payLogService.getWxpaylogByNo(order.getOrder_serial());
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
										result = (Boolean)refund_map.get("result");
										if (result) {
											refund.setRefund_status(2);
											orderRefundService.saveOrderRefund(refund);
											//退款成功，将退款记录到weixinpay_log表中
											payLogService.saveWXpayLog(refund.getOrder_serial(), CommonUtil.safeToString(refund_map.get("data"), null), new Date(), -(int) (refund.getRefund_price() * 100));
										} else {
											refund.setRefund_status(-1);
											orderRefundService.saveOrderRefund(refund);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("退款 异常", e);
			return "/jsp/error";
		}
		return "redirect:/orderRefund.do?method=showListReturnMoneyPage";
	}

	@RequestMapping(params = "method=showListReturnGoodsPage")
	public String showListReturnGoodsPage(HttpServletRequest request, HttpServletResponse response) {
		return "/new/jsp/order/listReturnGoods";
	}

	@RequestMapping(params = "method=showListReturnMoneyPage")
	public String showListReturnMoneyPage(HttpServletRequest request, HttpServletResponse response) {
		return "/new/jsp/order/listReturnMoney";
	}

	@RequestMapping(params = "method=listReturnGoodsV4")
	public void listReturnGoodsV4(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, logger, "listReturnGoodsV4");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
			CommonUtil.parseParamCommunityId(request, param);

			Date end_refund_time_d = CommonUtil.safeToDate(param.get("end_refund_time"), "yyyy-MM-dd");
			Date end_create_time_d = CommonUtil.safeToDate(param.get("end_create_time"), "yyyy-MM-dd");
            Date end_order_time_d = CommonUtil.safeToDate(param.get("end_order_time"), "yyyy-MM-dd");

			if(end_refund_time_d != null) {
				param.put("end_refund_time", DateUtils.addDays(end_refund_time_d, 1));
			}
			if(end_create_time_d != null) {
				param.put("end_create_time", CommonUtil.formatDate(DateUtils.addDays(end_create_time_d, 1), "yyyy-MM-dd"));
			}
            if(end_order_time_d != null) {
                param.put("end_order_time", CommonUtil.formatDate(DateUtils.addDays(end_order_time_d, 1), "yyyy-MM-dd"));
            }

			List<Map> list = orderRefundService.searchRefund(param);
			int count = orderRefundService.searchRefundCount(param);

			pager = GridUtil.setPagerResult(pager, list, count, true);

		} catch (Exception e) {
			logger.error("listReturnGoodsV4 error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

	@RequestMapping(params = "method=showRefundV4")
	public String showRefundV4(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "showRefundV4");

			Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
			Integer orderid = CommonUtil.safeToInteger(request.getParameter("orderid"), null);

			if(orderid != null) {
				Order order = orderService.getOrderById(orderid);
				if(order != null) {
					request.setAttribute("order", order);
					String status_name = CommonUtil.parseOrderStatus(order.getOrder_base_status());
					request.setAttribute("status_name", status_name);

                    //判断是否是称重商品
                    Product product = productService.getProduct(order.getProduct_id());
                    boolean isPrePro = false;
                    if(product != null) {
                        isPrePro = ProductTags.checkTag(product.getTags(), ProductTags.WEIGHT);
                    }
                    request.setAttribute("isPrePro", isPrePro);

					if(objid != null) {
						OrderRefund refund = orderRefundService.getOrderRefundById(objid);
						if(refund != null) {
							request.setAttribute("refund", refund);
						}
					}

					//获取该订单的退货信息
					List<OrderRefund> refundList = orderRefundService.findOrderRefundByOrderId(order.getOrder_id());
					if(refundList != null && refundList.size() > 0) {
						request.setAttribute("refunds", refundList);
					}
					//可进行退货数量，订单商品总数-处理中的和完成状态的数量
					double can_refund_amount = MathUtil.sub(order.getProduct_amount(), orderRefundService.getRefundAmount(orderid, new int[]{0,1}, objid));
					request.setAttribute("can_refund_amount", can_refund_amount);
				}
				request.setAttribute("objid", objid);
			}
		} catch (Exception e) {
			logger.error("展示退货页面 异常", e);
			return "/jsp/error";
		}
		return "/new/jsp/order/order_refund";
	}

	@RequestMapping(params = "method=returnGoodsV4")
	public void returnGoodsV4(HttpServletRequest request, HttpServletResponse response) {
		String result = "fail";
		try {
			LogerUtil.logRequest(request, logger, "returnGoodsV4");

			Integer order_id = CommonUtil.safeToInteger(request.getParameter("order_id"), null);
			Integer type = CommonUtil.safeToInteger(request.getParameter("type"), null);
			Double refundAmount = CommonUtil.safeToDouble(request.getParameter("refund_amount"), null);
			Double refundMoney = CommonUtil.safeToDouble(request.getParameter("refund_money"), null);
			String refundReason = request.getParameter("refund_reason");
			String rejectReason = request.getParameter("reject_reason");

			if(order_id != null && type != null && refundAmount != null && refundMoney != null) {
				MgrUser worker = (MgrUser) request.getSession().getAttribute("user_info");
				if(type.intValue() == 1) {
					//同意退货请求，进行退货操作
					orderRefundService.returnGoods(order_id, refundAmount, refundMoney, refundReason, worker.getUser_id(), CommonUtil.getIp(request));
				} else {
					//驳回退货请求
					Order order = orderService.getOrderById(order_id);
					if(order != null) {
						OrderRefund refund = new OrderRefund();
						refund.setOrder_id(order_id);
						refund.setOrder_serial(order.getOrder_serial());
						refund.setRefund_serial(CommonUtil.buildRefundNo(order.getOrder_id()));
						refund.setUserid(order.getUser_id());
						refund.setStatus(type);    //退货状态：0.待处理(初始) 1.退货完成 -1.退货驳回
						refund.setRefund_time(new Date());
						refund.setRefund_reason(refundReason);
						refund.setWorkerid(worker.getUser_id());
						refund.setReject_reason(rejectReason);
						refund.setProduct_id(order.getProduct_id());
						refund.setRefund_amount(refundAmount);
						refund.setProduct_price(order.getProduct_price());
						refund.setRefund_price(refundMoney);
						refund.setCommunity_id(order.getCommunity_id());
						refund.setCreate_time(new Date());
						orderRefundService.saveOrderRefund(refund);
					}
				}
				result = "success";
			}
		} catch (Exception e) {
			logger.error("退货 异常", e);
		}
		CommonUtil.outToWeb(response, result);
	}

	@RequestMapping(params = "method=showRefundOrderPage")
	public String showRefundOrderPage(HttpServletRequest request) throws Exception {
		try {
			String order_serial = CommonUtil.safeToString(request.getParameter("order_serial"), null);

			if (StringUtils.isNotBlank(order_serial)) {
				Map order = orderService.getOrderBaseById(order_serial);
				if(order != null) {
					String status_name = CommonUtil.parseOrderStatus(CommonUtil.safeToInteger(order.get("order_status"), null));
					order.put("status_name", status_name);

					List<Map> orderProductList = orderService.findOrderProductTemp(order_serial);
					for(Map orderProduct : orderProductList) {
						Integer product_id = CommonUtil.safeToInteger(orderProduct.get("product_id"), null);
						//判断是否是称重商品，非称重商品只能退整数
						boolean isPrePro = false;
						Product product = productService.getProduct(product_id);
						if(product != null) {
							isPrePro = ProductTags.checkTag(product.getTags(), ProductTags.WEIGHT);
						}
						orderProduct.put("isPrePro", isPrePro);

						Integer order_id = CommonUtil.safeToInteger(orderProduct.get("order_id"), null);
						//获取商品的可退货数量，总数-待处理和已退货完成的数量
						double canRefundAmount = orderRefundService.getCanRefundProductAmount(order_id, new int[] {ReturnGoodsStatus.WAIT.getValue(), ReturnGoodsStatus.FINISH.getValue()});
						orderProduct.put("canRefundAmount", canRefundAmount);	//可退货数量
					}

					//解析订单优惠明细 pay_off_all  满减|首单减|随机减
					String pay_off_text = "";
					String pay_off_all = CommonUtil.safeToString(order.get("pay_off_all"), null);
					if(StringUtils.isNotBlank(pay_off_all)) {
						Integer coupon_id = CommonUtil.safeToInteger(order.get("coupon_id"), null);
						String[] pay_off_all_array = pay_off_all.split("\\|");

						double man_jian = CommonUtil.safeToDouble(pay_off_all_array[0], 0d);
						double shou_dan = CommonUtil.safeToDouble(pay_off_all_array[1], 0d);
						double sui_ji = CommonUtil.safeToDouble(pay_off_all_array[2], 0d);

						String pay_off_text_1 = man_jian > 0 ? ("满减：" + man_jian + "|") : "";
						String pay_off_text_2 = shou_dan > 0 ? ("首单减：" + shou_dan + "|") : "";
						String pay_off_text_3 = sui_ji > 0 ? ("随机减：" + sui_ji + "|") : "";
						String pay_off_text_4 = "";

						if(coupon_id != null) {
							UserCoupon userCoupon = couponService.getUserCouponById(coupon_id);
							if(userCoupon != null) {
								double hong_bao = userCoupon.getOff_price();
								pay_off_text_4 = "红包减：" + hong_bao + "|";
							}
						}

						pay_off_text = pay_off_text_1 + pay_off_text_2 + pay_off_text_3 + pay_off_text_4;
						if(pay_off_text.endsWith("|")) {
							pay_off_text = pay_off_text.substring(0, pay_off_text.length() - 1);
						}
						request.setAttribute("pay_off_text", pay_off_text);
					}

					request.setAttribute("order", order);
					request.setAttribute("orderProductList", orderProductList);
				}
			}

		} catch (Exception e) {
			logger.error("展示退货页面 异常", e);
			throw e;
		}
		return "/new/jsp/order/refundOrder";
	}

	/**
	 * 校验订单是否能退款
	 * */
	@RequestMapping(params = "method=checkRefund")
	public void checkRefund(HttpServletRequest request, HttpServletResponse response) {
		RtnResult result = new RtnResult(false);
		try {
			String order_serial = request.getParameter("order_serial");
			String[] order_id_array = request.getParameterValues("order_id");
			String[] refund_amount_array = request.getParameterValues("refund_amount");

			if(StringUtils.isNotBlank(order_serial) && ArrayUtils.isNotEmpty(order_id_array) && ArrayUtils.isNotEmpty(refund_amount_array)) {
				List<RefundData> refundDataList = new ArrayList<RefundData>();
				for(int i = 0; i < order_id_array.length; i++) {
					Integer order_id = CommonUtil.safeToInteger(order_id_array[i], null);
					Double refund_amount = CommonUtil.safeToDouble(refund_amount_array[i], null);

					if(order_id != null && refund_amount != null) {
						RefundData refundData = new RefundData();
						refundData.setOrder_id(order_id);
						refundData.setRefund_amount(refund_amount);
						refundDataList.add(refundData);
					}
					result = orderRefundService.checkRefund(order_serial, refundDataList);
				}
			}
		} catch (Exception e) {
			logger.error("checkRefund error", e);
		}
		CommonUtil.outToWeb(response, JSON.toJSONString(result));
	}

	/**
	 * 计算退款金额
	 * */
	@RequestMapping(params = "method=calcRefundMoney")
	public void calcRefundMoney(HttpServletRequest request, HttpServletResponse response) {
		double refund_money = 0d;
		try {
			String order_serial = request.getParameter("order_serial");
			String[] order_id_array = request.getParameterValues("order_id");
			String[] refund_amount_array = request.getParameterValues("refund_amount");

			if(StringUtils.isNotBlank(order_serial) && ArrayUtils.isNotEmpty(order_id_array) && ArrayUtils.isNotEmpty(refund_amount_array)) {
				List<RefundData> refundDataList = new ArrayList<RefundData>();
				for(int i = 0; i < order_id_array.length; i++) {
					Integer order_id = CommonUtil.safeToInteger(order_id_array[i], null);
					Double refund_amount = CommonUtil.safeToDouble(refund_amount_array[i], null);

					if(order_id != null && refund_amount != null) {
						RefundData refundData = new RefundData();
						refundData.setOrder_id(order_id);
						refundData.setRefund_amount(refund_amount);
						refundDataList.add(refundData);
					}
					refund_money = orderRefundService.calcRefundMoney(order_serial, refundDataList);
				}
			}
		} catch (Exception e) {
			logger.error("calcRefundMoney error", e);
		}
		CommonUtil.outToWeb(response, refund_money + "");
	}

	@RequestMapping(params = "method=refund")
	public void refund(HttpServletRequest request, HttpServletResponse response) {
		RtnResult result = new RtnResult(false);
		try {
			String order_serial = request.getParameter("order_serial");
			Double refund_money = CommonUtil.safeToDouble(request.getParameter("refund_money"), null);
			String[] order_id_array = request.getParameterValues("order_id");
			String[] product_id_array = request.getParameterValues("product_id");
			String[] product_price_array = request.getParameterValues("product_price");
			String[] refund_amount_array = request.getParameterValues("refund_amount");

			if(StringUtils.isNotBlank(order_serial) && refund_money != null && ArrayUtils.isNotEmpty(order_id_array)
					&& ArrayUtils.isNotEmpty(product_id_array) && ArrayUtils.isNotEmpty(product_price_array) && ArrayUtils.isNotEmpty(refund_amount_array)) {
				List<RefundData> refundDataList = new ArrayList<RefundData>();
				for(int i = 0; i < order_id_array.length; i++) {
					Integer order_id = CommonUtil.safeToInteger(order_id_array[i], null);
					Integer product_id = CommonUtil.safeToInteger(product_id_array[i], null);
					Double product_price = CommonUtil.safeToDouble(product_price_array[i], null);
					Double refund_amount = CommonUtil.safeToDouble(refund_amount_array[i], null);

					if(order_id != null && product_id != null && product_price != null && refund_amount != null) {
						RefundData refundData = new RefundData();
						refundData.setOrder_id(order_id);
						refundData.setProduct_id(product_id);
						refundData.setProduct_price(product_price);
						refundData.setRefund_amount(refund_amount);
						refundDataList.add(refundData);
					}
				}
				if(refundDataList != null && refundDataList.size() > 0) {
					MgrUser worker = (MgrUser) request.getSession().getAttribute("user_info");
					String ip = CommonUtil.getIp(request);
					RtnResult refundResult = orderRefundService.refund(refundDataList, order_serial, refund_money, null, worker.getUser_id(), ip, null);
					if(refundResult.getResult()) {
						result.setResult(true);
					} else {
						result.setMsg(refundResult.getMsg());
					}
				}
			}
		} catch (Exception e) {
			logger.error("refund error", e);
		}
		CommonUtil.outToWeb(response, JSON.toJSONString(result));
	}

	/**
	 * 只进行退款操作
	 * */
	@RequestMapping(params = "method=refundOnly")
	public void refundOnly(HttpServletRequest request, HttpServletResponse response) {
		RtnResult result = new RtnResult(false);
		try {
			Integer refund_id = CommonUtil.safeToInteger(request.getParameter("refund_id"), null);

			if(refund_id != null) {
				OrderRefundBase orderRefundBase = orderRefundService.getOrderRefundBaseById(refund_id);
				if(orderRefundBase != null) {
					//根据退款方式进行退款
					Integer refund_type = orderRefundBase.getRefund_type();
					if(refund_type != null) {
						if(refund_type.intValue() == RefundTypeEnum.ALIPAY.getValue()) {
							String content = "";	//支付宝退款form
							try {
								//支付宝退款
								AliPayLogBean aliPayLog = aliPayLogService.getAliPayLogByOrderSerial(orderRefundBase.getOrder_serial());
								if (aliPayLog != null) {
									//获取原付款交易号
									String retdata = aliPayLog.getRetdata();
									if (StringUtils.isNotBlank(retdata)) {
										String tradeNo = CommonUtil.safeToString(JSONObject.fromObject(retdata).get("trade_no"), null);
										if (StringUtils.isNotBlank(tradeNo)) {
											String ymd = orderRefundBase.getRefund_serial().substring(0, 9);
											if (!CommonUtil.formatDate(new Date(), "yyyyMMdd").equals(ymd)) {
												//支付宝退款需要重新生成一下退款批次号，防止日期不正确
												orderRefundBase.setRefund_serial(CommonUtil.buildRefundNo(orderRefundBase.getOrder_serial()));
												orderRefundService.saveOrderRefundBase(orderRefundBase);
											}

											AlipayRefundDetailData data = new AlipayRefundDetailData();
											data.setTradeNo(tradeNo);    //原付款订单
											data.setRefundMoney(orderRefundBase.getRefund_money());    //退款金额
											data.setRefundReason(orderRefundBase.getRefund_reason() == null ? "" : orderRefundBase.getRefund_reason());    //退款原因
											content = AlipayUtils.buildRefundRequest(orderRefundBase.getRefund_serial(), data, 1);
										}
									}
								}
							} catch (Exception e) {
								logger.error("alipay refund error", e);
							} finally {
								if(StringUtils.isNotBlank(content)) {
									result.setResult(true);
									result.setData(content);
								}
							}

						} else if(refund_type.intValue() == RefundTypeEnum.WXPAY.getValue()) {
							//微信支付退款
							boolean refund_result = orderRefundService.refundByWxPay(orderRefundBase.getOrder_serial(), orderRefundBase.getRefund_serial(), orderRefundBase.getRefund_money());
							if(refund_result) {
								orderRefundBase.setRefund_status(ReturnMoneyStatus.FINISH.getValue());
								orderRefundBase.setRefund_time(new Date());

								result.setResult(true);
							} else {
								orderRefundBase.setRefund_status(ReturnMoneyStatus.FAIL.getValue());
							}
							orderRefundService.saveOrderRefundBase(orderRefundBase);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("refundOnly error", e);
		}
		CommonUtil.outToWeb(response, JSON.toJSONString(result));
	}

	@RequestMapping(params = "method=searchOrderRefundBase")
	public void searchOrderRefundBase(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
			CommonUtil.parseParamCommunityId(request, param);

			Date end_refund_time_d = CommonUtil.safeToDate(param.get("end_refund_time"), "yyyy-MM-dd");
			Date end_create_time_d = CommonUtil.safeToDate(param.get("end_create_time"), "yyyy-MM-dd");
			Date end_order_time_d = CommonUtil.safeToDate(param.get("end_order_time"), "yyyy-MM-dd");

			if(end_refund_time_d != null) {
				param.put("end_refund_time", DateUtils.addDays(end_refund_time_d, 1));
			}
			if(end_create_time_d != null) {
				param.put("end_create_time", CommonUtil.formatDate(DateUtils.addDays(end_create_time_d, 1), "yyyy-MM-dd"));
			}
			if(end_order_time_d != null) {
				param.put("end_order_time", CommonUtil.formatDate(DateUtils.addDays(end_order_time_d, 1), "yyyy-MM-dd"));
			}

			List<Map> list = orderRefundService.searchOrderRefundBase(param);
			int count = orderRefundService.searchOrderRefundBaseCount(param);

			pager = GridUtil.setPagerResult(pager, list, count, true);

		} catch (Exception e) {
			logger.error("searchOrderRefundBase error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

	@RequestMapping(params = "method=searchOrderRefundProduct")
	public void searchOrderRefundProduct(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);

			List<Map> list = orderRefundService.searchOrderRefundProduct(param);
			int count = list.size();

			pager = GridUtil.setPagerResult(pager, list, count, true);

		} catch (Exception e) {
			logger.error("searchOrderRefundProduct error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}
}

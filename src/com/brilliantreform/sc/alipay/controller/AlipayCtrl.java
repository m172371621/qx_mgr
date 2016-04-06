package com.brilliantreform.sc.alipay.controller;

import com.brilliantreform.sc.alipay.service.AliPayLogService;
import com.brilliantreform.sc.alipay.util.AlipayUtils;
import com.brilliantreform.sc.order.enumerate.ReturnMoneyStatus;
import com.brilliantreform.sc.order.po.OrderRefund;
import com.brilliantreform.sc.order.po.OrderRefundBase;
import com.brilliantreform.sc.order.service.OrderRefundService;
import com.brilliantreform.sc.utils.CommonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("alipay.do")
public class AlipayCtrl {

    private static Logger logger = Logger.getLogger(AlipayCtrl.class);

    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private AliPayLogService aliPayLogService;
    /**
     * 获取支付宝退款返回过来的结果
     * */
    @RequestMapping(params = "method=refundCallback")
    public String callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("into alipay refund callback");
        try {
            Map<String,String> params = new HashMap<String,String>();
            Map param = request.getParameterMap();
            for (Iterator iter = param.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) param.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }
            logger.info("refund callback param：" + JSONObject.fromObject(params).toString());
            logger.info("refund callback param：" + params);
            System.out.println("1. refund callback param:" + JSONObject.fromObject(params).toString());
            //批次号
            String batch_no = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"),"UTF-8");
            //批量退款数据中转账成功的笔数
            Integer success_num = CommonUtil.safeToInteger(new String(request.getParameter("success_num").getBytes("ISO-8859-1"),"UTF-8"), null);
            //批量退款数据中的详细信息
            String result_details = new String(request.getParameter("result_details").getBytes("ISO-8859-1"),"UTF-8");

            System.out.println("2. param : batch_no = " + batch_no + ";success_num=" + success_num + ";result_details=" + result_details);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            if(AlipayUtils.verify(params)){//验证成功
                System.out.println("3. verify success");
                if(StringUtils.isNotBlank(batch_no) && success_num != null) {
                    System.out.println("4. into db operater");
                    //根据退款订单号获取退款数据
                    OrderRefundBase orderRefundBase = orderRefundService.getOrderRefundBaseByRefundSerial(batch_no, ReturnMoneyStatus.WAIT.getValue());
                    if(orderRefundBase != null) {
                        //同步退款状态
                        orderRefundBase.setRefund_status(success_num > 0 ? ReturnMoneyStatus.FINISH.getValue() : ReturnMoneyStatus.FAIL.getValue());
                        orderRefundBase.setRefund_time(new Date());
                        orderRefundService.saveOrderRefundBase(orderRefundBase);

                        //退款成功的时候记录日志
                        if(success_num > 0) {
                            Enumeration paramNames = request.getParameterNames();
                            StringBuffer sb = new StringBuffer("{");
                            Map<String, String> rmap = new HashMap<String, String>();
                            while (paramNames.hasMoreElements()) {
                                String paramName = (String) paramNames.nextElement();
                                String[] paramValues = request.getParameterValues(paramName);
                                if (paramValues.length == 1) {
                                    String paramValue = paramValues[0];
                                    rmap.put(paramName, paramValue);
                                    sb.append("\"");
                                    sb.append(paramName);
                                    sb.append("\"");
                                    sb.append(":");
                                    sb.append("\"");
                                    sb.append(paramValue);
                                    sb.append("\"");
                                    sb.append(",");
                                }
                            }
                            String queryString = sb.toString();
                            queryString = queryString.substring(0, queryString.length() - 1);
                            queryString = queryString + "}";

                            aliPayLogService.saveAlipayLog(orderRefundBase.getOrder_serial(), queryString, new Date(), -orderRefundBase.getRefund_money());
                        }
                        response.getWriter().print("success");
                    }
                }
            } else {
                response.getWriter().print("fail");
            }
        } catch (Exception e) {
            logger.error("alipay refund callback error", e);
        }
        return null;
    }

}

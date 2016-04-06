<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.brilliantreform.sc.alipay.util.AlipayUtils" %>
<%@ page import="com.brilliantreform.sc.system.SpringContextHolder" %>
<%@ page import="com.brilliantreform.sc.qxcard.service.QxCardService" %>
<%@ page import="com.brilliantreform.sc.qxcard.po.QxCardCzLog" %>
<%@ page import="com.brilliantreform.sc.qxcard.enumerate.*" %>
<%@ page import="com.brilliantreform.sc.qxcard.po.QxCardCzPayLog" %>
<%@ page import="com.brilliantreform.sc.utils.CommonUtil" %>
<%@ page import="net.sf.json.JSONObject" %>
<%
    try {
        Map<String, String> params = new HashMap<String, String>();
        Map param = request.getParameterMap();
        for (Iterator iter = param.keySet().iterator(); iter.hasNext(); ) {
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
        //充值单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        //交易金额
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

        Map<String, String> map = CommonUtil.getParameterMap(request);
        String retdata = JSONObject.fromObject(map).toString();
        System.out.println(retdata);

        if (AlipayUtils.verify(params)) {
            if (trade_status.equals("TRADE_SUCCESS")) {
                QxCardService qxCardService = SpringContextHolder.getBean("qxCardService");
                QxCardCzLog czLog = qxCardService.getCzLogByCzSerial(out_trade_no);
                if (czLog != null && CommonUtil.safeToDouble(total_fee, 0d) == czLog.getPay_price().doubleValue()
                        && czLog.getStatus() == QxCardCzStatusEnum.ING.getValue() && czLog.getPay_type() == QxCardCzPayTypeEnum.ALIPAY.getValue()) {
                    czLog.setPay_status(QxCardCzPayStatusEnum.YES.getValue());
                    czLog.setStatus(QxCardCzStatusEnum.FINISH.getValue());
                    czLog.setPay_time(new Date());
                    czLog.setCz_time(new Date());
                    qxCardService.saveQxCardCzLog(czLog);

                    qxCardService.rechargeQxcard(czLog);

                    QxCardCzPayLog czPayLog = new QxCardCzPayLog();
                    czPayLog.setCzlog_id(czLog.getObjid());
                    czPayLog.setCz_serial(out_trade_no);
                    czPayLog.setPay_time(new Date());
                    czPayLog.setPay_price(czLog.getPay_price());
                    czPayLog.setRetdata(retdata);
                    qxCardService.insertQxcardCzPayLog(czPayLog);

                    out.print("success");
                    return;
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    out.print("fail");
%>

package com.brilliantreform.sc.weixin.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.order.po.Order;
import com.brilliantreform.sc.order.service.OrderService;
import com.brilliantreform.sc.utils.JsonUtil;
import com.brilliantreform.sc.weixin.po.WeixinPayBean;
import com.brilliantreform.sc.weixin.po.WeixinQxx;
import com.brilliantreform.sc.weixin.service.WeixinPayService;
import com.brilliantreform.sc.weixin.service.WeixinQxxService;
import com.brilliantreform.sc.weixin.util.XmlUtil;

@Controller
public class WeixinPayCtrl {
	private static Logger logger = Logger.getLogger(WeixinPayCtrl.class);

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private WeixinPayService weixinPayService;
	
	@Autowired
	private WeixinQxxService weixinQxxService;
	

	@RequestMapping(value = "weixinpay.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String callback(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		logger.info("into weixinpay callback");
		try {

			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
			outSteam.close();
			inStream.close();
			String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
			Map<String, Object> rmap = XmlUtil.parseXml(result);
			for(Object obj:rmap.keySet().toArray())
			{
				logger.info("weixinpay params" + obj.toString()+",value:"+rmap.get(obj).toString());
			}
//			

			// alipayDao.insertLog(params);
            WeixinPayBean weixinpayBean=new WeixinPayBean();
            weixinpayBean.setRetdata(result);
			if (rmap.get("out_trade_no") != null && rmap.get("return_code").toString().equals("SUCCESS")) {
				Order order = new Order();
				order.setOrder_serial(rmap.get("out_trade_no").toString().substring(0,20));
				order.setOrder_status(22);
				order.setPay_time(new Timestamp(System.currentTimeMillis()));
				orderService.updateOrderBaseStatus(rmap.get("out_trade_no").toString().substring(0,20), 22);
				weixinpayBean.setOut_trade_no(rmap.get("out_trade_no").toString().substring(0,20));
				weixinpayBean.setTotal_fee(Integer.parseInt(rmap.get("total_fee").toString()));
				logger.info("weixinpay updateOrderBySerial");
				weixinPayService.insertLog(weixinpayBean);
				WeixinQxx qxx=new WeixinQxx();
				qxx.setOpenid(rmap.get("openid").toString());
				qxx.setBuy_count(qxx.getBuy_count()+1);
				weixinQxxService.updatePersonalInfo(qxx);
			}else{
				weixinpayBean.setOut_trade_no("0");
				
			}
			response.getWriter().print(setXML("SUCCESS", ""));

		} catch (Exception e) {
			logger.error("weixinpay callback:" + e.getMessage());
			response.getWriter().print(JsonUtil.result2Json(false, null));
		}
		return null;
	}

	public String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";

	}
}

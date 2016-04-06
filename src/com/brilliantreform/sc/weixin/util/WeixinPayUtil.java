package com.brilliantreform.sc.weixin.util;

import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeixinPayUtil {
	private static Logger logger = Logger.getLogger(WeixinPayUtil.class);
	//private static String back_url = "http://www.qxit.com.cn/qx_mgr/weixinpay.do";
	private static String unifind_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//private static String mch_id = "1295309101";
	//private static String appid = "wx400adb553bf0425e";

	public static String getWeixinpayUrl(String openid, String ip,
			String order_serial, String subject, String body, String price) {

		String retStr = getNewAlipayRequest(openid, ip, order_serial, body,
				price);
		return retStr;
	}

	private static String getNewAlipayRequest(String openid, String ip,
			String order_serial, String body, String price) {
		SortedMap<Object, Object> parammap = new TreeMap<Object, Object>();
		try {
			Map<String, Object> map = getPrepayId(openid, ip, order_serial,
					body, price);

			if (map != null) {
				parammap.put("appId", SettingUtil.getSettingValue("WEIXIN", "QXX_APPID"));
				Timestamp ts = new Timestamp(System.currentTimeMillis());
				parammap.put("timeStamp", ts.getTime());
				parammap.put("nonceStr", CommonUtil.CreateNoncestr());
				parammap.put("package", "prepay_id="
						+ map.get("prepay_id").toString());
				parammap.put("signType", "MD5");
				parammap.put("paySign", WeixinQxxUtil.createSign("UTF-8",
						parammap));
			}
		} catch (Exception e) {
		}

		JSONObject json = JSONObject.fromObject(parammap);
		return json.toString();

	}

	private static Map<String, Object> getPrepayId(String openid, String ip,
			String order_serial, String body, String price) {

		return getPrepayId("JSAPI", openid, ip, order_serial, body, price);
	}

	private static Map<String, Object> getPrepayId(String trade_type,
			String openid, String ip, String order_serial, String body,
			String price) {
		Map<String, Object> map = null;
		try {
			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			String serial_six = String
					.valueOf(((new Random().nextInt(899999)) + 100000));
			order_serial += serial_six;

			// 先设置基本信息
			parameters.put("appid", SettingUtil.getSettingValue("WEIXIN", "QXX_APPID"));
			parameters.put("mch_id", SettingUtil.getSettingValue("WEIXIN", "QXX_MCHID"));
			parameters.put("nonce_str", CommonUtil.CreateNoncestr());
			parameters.put("spbill_create_ip", ip);
			parameters.put("out_trade_no", order_serial);
			parameters.put("body", body);// 商品描述
			// 自定义订单号，此处仅作举例
			parameters.put("total_fee", price);// 总金额
			parameters.put("notify_url", SettingUtil.getSettingValue("WEIXIN", "QXX_NOTIFY_URL"));// 通知地址
			parameters.put("trade_type", trade_type);// 交易类型

			if (trade_type.equals("JSAPI")) {
				parameters.put("openid", openid);// 商品描述
				parameters.put("sign", WeixinQxxUtil.createSign("UTF-8",
						parameters));
			}

			logger.info("getPrepayid,parameters:" + parameters.toString());
			String requestData = XmlUtil.getRequestXml(parameters);
			map = XmlUtil.httpRequestXML(unifind_url, "POST", requestData);
			logger.info("getPrepayid:" + map.toString());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return map;
	}

	public static int stringToInt(String string) {
		int j = 0;
		String str = string.substring(0, string.indexOf("."))
				+ string.substring(string.indexOf(".") + 1);
		int intgeo = Integer.parseInt(str);
		return intgeo;
	}

	public static String setXML(String return_code, String result_code,
			String return_msg, String prepay_id, String appid, String mch_id,
			String nonce_str, String sign) {
		return "<xml>"
				+ "<return_code><![CDATA["
				+ return_code
				+ "]]></return_code>"
				+ "<result_code><![CDATA["
				+ result_code
				+ "]]></result_code>"
				+ "<return_msg><![CDATA["
				+ return_msg
				+ "]]></return_msg>"
				+ (result_code.equals("SUCCESS") ? ("<prepay_id><![CDATA["
						+ prepay_id + "]]></prepay_id>") : "")
				+ "<appid><![CDATA[" + appid + "]]></appid>"
				+ "<nonce_str><![CDATA[" + nonce_str + "]]></nonce_str>"
				+ "<sign><![CDATA[" + sign + "]]></sign>" + "</xml>";

	}

}

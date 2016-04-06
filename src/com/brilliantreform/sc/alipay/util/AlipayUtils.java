package com.brilliantreform.sc.alipay.util;

import com.brilliantreform.sc.alipay.config.AlipayConfig;
import com.brilliantreform.sc.alipay.po.AlipayRefundDetailData;
import com.brilliantreform.sc.alipay.sign.MD5;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.HttpUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 支付宝相关接口
 * Created by shangwq on 2015/8/19.
 */
public class AlipayUtils {

    /**
     * 构建支付宝接口请求form、url
     * @param type 1.url   2.form 默认url
     * */
    public static String buildRequest(Map param, Integer type) {
        String content = "";
        if(param != null && param.size() > 0) {
            //把请求参数打包成数组
            String sign = buildRefundSign(param);
            param.put("sign_type", AlipayConfig.sign_type);
            param.put("sign", sign);
            if(type != null && type == 2) {
                content = buildRequestForm(param, "get");
            } else {
                content = buildRequestUrl(param);
            }
        }
        return content;
    }

    public static String buildRefundRequest(String batch_no, AlipayRefundDetailData data, Integer type) {
        List<AlipayRefundDetailData> list = new ArrayList<AlipayRefundDetailData>();
        if(data != null) {
            list.add(data);
        }
        return buildBatchRefundRequest(batch_no, list, type);
    }

    /**
     * 构建支付宝批量退款请求url或form 1.url 2.form
     * */
    public static String buildBatchRefundRequest(String batch_no, List<AlipayRefundDetailData> data, Integer type) {
        String content = "";
        if(data != null && data.size() > 0) {
            String service = "refund_fastpay_by_platform_pwd"; //接口名称
            String _input_charset = "utf-8";    //参数编码字符集
            String refund_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());    //退款请求时间
            int batch_num = data.size();    //总笔数
            //构造单笔数据集
            StringBuffer detail_data = new StringBuffer();
            for (AlipayRefundDetailData detail : data) {
                String trade_no = detail.getTradeNo();  //交易号
                Double refund_money = detail.getRefundMoney();  //退款金额
                String refund_reason = detail.getRefundReason();    //退款理由
                if (StringUtils.isNotBlank(trade_no) && refund_money != null) {
                    detail_data.append(trade_no).append("^").append(refund_money).append("^").append(refund_reason).append("#");
                }
            }
            if (detail_data.toString().endsWith("#")) {
                detail_data = detail_data.deleteCharAt(detail_data.length() - 1);
            }

            //把请求参数打包成数组
            Map<String, String> param = new HashMap<String, String>();
            param.put("service", service);		//接口名称
            param.put("partner", AlipayConfig.DEFAULT_PARTNER);		//合作者身份ID
            param.put("_input_charset", _input_charset);	//字符编码
            param.put("notify_url", SettingUtil.getSettingValue("ALIPAY", "REFUND_NOTIFY_URL"));     //异步通知url
            param.put("batch_no", batch_no);
            param.put("batch_num", batch_num + "");
            param.put("seller_email", AlipayConfig.DEFAULT_SELLER);
            param.put("detail_data", detail_data.toString());
            param.put("refund_date", refund_date);
            String sign = buildRefundSign(param);
            param.put("sign_type", AlipayConfig.sign_type);
            param.put("sign", sign);
            if(type != null && type.intValue() == 2) {
                content = buildRequestForm(param, "get");
            } else {
                content = buildRequestUrl(param);
            }
        }
        return content;
    }

    /**
     * 验证消息是否是支付宝发出的合法消息
     * @param param 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> param) {
        String responseTxt = "true";
        boolean isSign = false;
        if(param.get("notify_id") != null) {
            String notify_id = param.get("notify_id");
            String veryfy_url = AlipayConfig.HTTPS_VERIFY_URL + "partner=" + AlipayConfig.DEFAULT_PARTNER + "&notify_id=" + notify_id;
            responseTxt = HttpUtil.doGet(veryfy_url);
        }
        if(param.get("sign") != null) {
            String sign = param.get("sign");
            String mysign = buildRefundSign(param);
            isSign = mysign.equals(sign);
        }
        return ("true".equals(responseTxt) && isSign);
    }

    private static String buildRefundSign(Map<String, String> param) {
        String unsignstr = sortParam(paramFilter(param));   //待签名字符
        return MD5.sign(unsignstr, AlipayConfig.key, AlipayConfig.input_charset);
    }

    private static String buildRequestForm(Map<String, String> param, String method) {
        List<String> keys = new ArrayList<String>(param.keySet());
        StringBuffer html = new StringBuffer();
        html.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + AlipayConfig.ALIPAY_GATEWAY_NEW
                + "\" method=\"" + method
                + "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) param.get(name);
            html.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        html.append("<input type=\"submit\" value=确定\" style=\"display:none;\"></form>");
        html.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return html.toString();
    }

    private static String buildRequestUrl(Map<String, String> param) {
        StringBuffer url = new StringBuffer(AlipayConfig.ALIPAY_GATEWAY_NEW).append("?");
        List<String> keys = new ArrayList<String>(param.keySet());
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) param.get(name);
            url.append(name).append("=").append(value).append("&");
        }
        return url.toString();
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param param 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String sortParam(Map<String, String> param) {
        List<String> keys = new ArrayList<String>(param.keySet());
        Collections.sort(keys);
        String unsignstr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = param.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                unsignstr = unsignstr + key + "=" + value;
            } else {
                unsignstr = unsignstr + key + "=" + value + "&";
            }
        }
        return unsignstr;
    }

    /**
     * 除去数组中的空值和签名参数
     * @param param 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paramFilter(Map<String, String> param) {
        Map<String, String> result = new HashMap<String, String>();
        if (param == null || param.size() <= 0) {
            return result;
        }
        for (String key : param.keySet()) {
            String value = param.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type") || key.equalsIgnoreCase("method")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

}

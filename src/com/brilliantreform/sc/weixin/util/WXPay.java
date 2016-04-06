package com.brilliantreform.sc.weixin.util;

import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付相关工具类
 * Created by shangwq on 2015/8/25.
 */
public class WXPay {

    private static final Logger LOGGER = Logger.getLogger(WXPay.class);

    //公众号中的微信支付
    //public static final String WX_APPID = "wx699aa43650cb214d";
    //public static final String WX_MCHID = "1235122102";
    //public static final String WX_CERT_LOCATION = "wx_apiclient_cert.p12";
    //public static final String WX_CERT_PASSWORD = "1235122102";
    //public final static String WX_EncodingAESKey = "si1Fq5e6x7JRiCUHXOqY3MYgUZTbomTm";

    private static boolean wx_hasInit = false;	//标志http请求器是否已经初始化
    private static CloseableHttpClient wx_httpClient;
    private static RequestConfig wx_requestConfig;

    //app中的微信支付
    //public static final String APP_APPID = "wx8a05653dc2086f7c";
    //public static final String APP_MCHID = "1263387501";
    //public static final String APP_CERT_LOCATION = "app_apiclient_cert.p12";
    //public static final String APP_CERT_PASSWORD = "1263387501";
    //public final static String APP_EncodingAESKey = "B96BD583E6F080D50DA80A2F90FFF16A";

    private static boolean app_hasInit = false;	//标志http请求器是否已经初始化
    private static CloseableHttpClient app_httpClient;
    private static RequestConfig app_requestConfig;

    /**
     * 退款
     * @param order_id
     * @param order_serial
     * @param transactionID 是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。建议优先使用
     * @param totalFee  订单总金额，单位为分，整数
     * @param refundFee 退款总金额，单位为分,可以做部分退款，整数
     * */
    public static Map refund(String mch_id, String out_refund_no, String transactionID, int totalFee, int refundFee) {
        Map map = new HashMap();
        boolean result = false;
        try {
            String app_id = "";
            if(StringUtils.isNotBlank(mch_id)) {
                if(SettingUtil.getSettingValue("WEIXIN", "WX_MCHID").equals(mch_id)) {
                    app_id = SettingUtil.getSettingValue("WEIXIN", "WX_APPID");
                } else if(SettingUtil.getSettingValue("WEIXIN", "APP_MCHID").equals(mch_id)) {
                    app_id = SettingUtil.getSettingValue("WEIXIN", "APP_APPID");
                }
            }

            if(StringUtils.isNotBlank(app_id)) {
                String nonceStr = CommonUtil.getRandomStringByLength(32);   //32为随机字符串
                Map param = new HashMap();
                param.put("appid", app_id);
                param.put("mch_id", mch_id);
                param.put("nonce_str", nonceStr);
                param.put("op_user_id", mch_id);
                param.put("out_refund_no", out_refund_no);
                param.put("transaction_id", transactionID);
                param.put("refund_fee", refundFee);
                param.put("total_fee", totalFee);
                param.put("sign", createSign(mch_id, "UTF-8", param));


                String postDataXML = WeixinUtil.genXML(param);
                String data = requestData(mch_id, WeixinUtil.refund_url, postDataXML);
                LOGGER.info("weixin pay refund result:" + data);
                map.put("data", data);
                if (StringUtils.isNotBlank(data)) {
                    Document doc = DocumentHelper.parseText(data);
                    Element root = doc.getRootElement();
                    String return_code = root.elementTextTrim("return_code");
                    if (StringUtils.isNotBlank(return_code) && "SUCCESS".equals(return_code)) {
                        String result_code = root.elementTextTrim("result_code");
                        if (StringUtils.isNotBlank(result_code) && "SUCCESS".equals(result_code)) {
                            result = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("weixin pay refund error", e);
        }
        map.put("result", result);
        return map;
    }

    private static void initRefundClient(String mch_id) throws Exception {
        if(StringUtils.isNotBlank(mch_id)) {
            if(SettingUtil.getSettingValue("WEIXIN", "WX_MCHID").equals(mch_id)) {
                if(!wx_hasInit) {
                    KeyStore keyStore = KeyStore.getInstance("PKCS12");
                    InputStream instream = Thread.currentThread().getContextClassLoader().getResourceAsStream(SettingUtil.getSettingValue("WEIXIN", "WX_CERT_LOCATION"));//加载本地的证书进行https加密传输
                    try {
                        keyStore.load(instream, SettingUtil.getSettingValue("WEIXIN", "WX_CERT_PASSWORD").toCharArray());//设置证书密码
                    } catch (Exception e) {
                        LOGGER.error("load weixin cert error", e);
                    } finally {
                        instream.close();
                    }
                    SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, SettingUtil.getSettingValue("WEIXIN", "WX_CERT_PASSWORD").toCharArray()).build();
                    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

                    wx_httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
                    //根据默认超时限制初始化requestConfig
                    wx_requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();

                    wx_hasInit = true;
                }
            } else if(SettingUtil.getSettingValue("WEIXIN", "APP_MCHID").equals(mch_id)) {
                if(!app_hasInit) {
                    KeyStore keyStore = KeyStore.getInstance("PKCS12");
                    InputStream instream = Thread.currentThread().getContextClassLoader().getResourceAsStream(SettingUtil.getSettingValue("WEIXIN", "APP_CERT_LOCATION"));//加载本地的证书进行https加密传输
                    try {
                        keyStore.load(instream, SettingUtil.getSettingValue("WEIXIN", "APP_CERT_PASSWORD").toCharArray());//设置证书密码
                    } catch (Exception e) {
                        LOGGER.error("load weixin cert error", e);
                    } finally {
                        instream.close();
                    }
                    SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, SettingUtil.getSettingValue("WEIXIN", "APP_CERT_PASSWORD").toCharArray()).build();
                    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

                    app_httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
                    //根据默认超时限制初始化requestConfig
                    app_requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();

                    app_hasInit = true;
                }
            }
        }
    }

    public static String requestData(String mch_id, String url, String data) {
        if(StringUtils.isNotBlank(mch_id)) {
            if(SettingUtil.getSettingValue("WEIXIN", "WX_MCHID").equals(mch_id)) {
                String result = null;
                HttpPost post = null;
                try {
                    initRefundClient(mch_id);
                    post = new HttpPost(url);
                    //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
                    StringEntity postEntity = new StringEntity(data, "UTF-8");
                    post.addHeader("Content-Type", "text/xml");
                    post.setEntity(postEntity);
                    //设置请求器的配置
                    post.setConfig(wx_requestConfig);
                    HttpResponse response = wx_httpClient.execute(post);
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity, "UTF-8");
                } catch (Exception e) {
                    LOGGER.error("request weixin data error", e);
                } finally {
                    if(post != null) {
                        post.abort();
                    }
                }
                return result;
            } else if(SettingUtil.getSettingValue("WEIXIN", "APP_MCHID").equals(mch_id)) {
                String result = null;
                HttpPost post = null;
                try {
                    initRefundClient(mch_id);
                    post = new HttpPost(url);
                    //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
                    StringEntity postEntity = new StringEntity(data, "UTF-8");
                    post.addHeader("Content-Type", "text/xml");
                    post.setEntity(postEntity);
                    //设置请求器的配置
                    post.setConfig(app_requestConfig);
                    HttpResponse response = app_httpClient.execute(post);
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity, "UTF-8");
                } catch (Exception e) {
                    LOGGER.error("request weixin data error", e);
                } finally {
                    if(post != null) {
                        post.abort();
                    }
                }
                return result;
            }
        }
        return null;
    }

    public static String createSign(String mch_id, String characterEncoding, Map<String,Object> param) {
        String key = "";
        if(StringUtils.isNotBlank(mch_id)) {
            if (SettingUtil.getSettingValue("WEIXIN", "WX_MCHID").equals(mch_id)) {
                key = SettingUtil.getSettingValue("WEIXIN", "WX_EncodingAESKey");
            } else if (SettingUtil.getSettingValue("WEIXIN", "APP_MCHID").equals(mch_id)) {
                key = SettingUtil.getSettingValue("WEIXIN", "APP_EncodingAESKey");
            }
        }
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry : param.entrySet()) {
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        sb.append("key=").append(key);
        return WeixinUtil.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
    }
}

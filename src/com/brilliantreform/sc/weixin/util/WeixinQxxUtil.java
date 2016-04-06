package com.brilliantreform.sc.weixin.util;

import com.brilliantreform.sc.weixin.po.AccessToken;
import com.brilliantreform.sc.weixin.po.JsTickect;
import com.brilliantreform.sc.weixin.po.WeixinUser;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WeixinQxxUtil {

    private static Logger logger = Logger.getLogger(WeixinQxxUtil.class);
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String CODE_ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public static final String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    public final static String appid = "wx2d1ceefba62d640d";
    public final static String appsecret = "d4624c36b6795d1d99dcf0547af5443d";

//    public final static String appid = "wx400adb553bf0425e";
//    public final static String appsecret = "e33cc0ccc0ee49a05e520c509337e749";

    public final static String mch_id = "1235122102";
    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    public final static String EncodingAESKey = "9E0809FBBCC84C059CE5D7D0B608B483";
    public static final String cert_location = "apiclient_cert.p12";
    public static final String cert_password = "1235122102";
    private static AccessToken accessToken;

    private static boolean hasInit = false; // 标志http请求器是否已经初始化
    private static CloseableHttpClient httpClient;
    private static RequestConfig requestConfig;

    private static JsTickect ticket;

    private static void initRefundClient() throws Exception {
        if (!hasInit) {


            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream instream = Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(cert_location);// 加载本地的证书进行https加密传输
            try {
                keyStore.load(instream, cert_password.toCharArray());// 设置证书密码
            } catch (Exception e) {
                logger.error("load weixin cert error", e);
            } finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(
                    keyStore, cert_password.toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                    .build();
            // 根据默认超时限制初始化requestConfig
            requestConfig = RequestConfig.custom().setSocketTimeout(10000)
                    .setConnectTimeout(30000).build();

            hasInit = true;
        }
    }

    public static String requestData(String url, String data) {
        String result = null;
        HttpPost post = null;
        try {
            initRefundClient();
            post = new HttpPost(url);
            // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
            StringEntity postEntity = new StringEntity(data, "UTF-8");
            post.addHeader("Content-Type", "text/xml");
            post.setEntity(postEntity);
            // 设置请求器的配置
            post.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            logger.error("request weixin data error", e);
        } finally {
            if (post != null) {
                post.abort();
            }
        }
        return result;
    }

    /**
     * 获取access_token
     *
     * @return
     */
    public static AccessToken getAccessToken() {
        Date getnow = new Date();
        logger.info("weixin now:" + getnow);
        logger.info("weixin accessToken:" + accessToken);
        if (accessToken != null
                && getnow.before(DateUtils.addSeconds(accessToken
                .getUpdateDate(), accessToken.getExpiresIn()))) {
            return accessToken;
        }
        String requestUrl = access_token_url.replace("APPID", appid).replace(
                "APPSECRET", appsecret);
        JSONObject jsonObject = XmlUtil.httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                accessToken.setUpdateDate(getnow);
                logger.info("weixin accessToken1:" + accessToken.getToken()
                        + "----updatetime:" + accessToken.getUpdateDate());
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                logger.error("获取token失败 errcode:{} errmsg:{}"
                        + jsonObject.getInt("errcode")
                        + jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    /**
     * 获取access_token
     *
     * @return
     */
    public static JsTickect getTickect() {
        Date getnow = new Date();
        if (ticket != null
                && getnow.before(DateUtils.addSeconds(ticket.getUpdateDate(),
                ticket.getExpiresIn()))) {
            return ticket;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        url = url.replace("ACCESS_TOKEN", getAccessToken().getToken());
        JSONObject jsonObject = XmlUtil.httpRequest(url, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                ticket = new JsTickect();
                ticket.setTicket(jsonObject.getString("ticket"));
                ticket.setExpiresIn(jsonObject.getInt("expires_in"));
                ticket.setUpdateDate(getnow);
            } catch (JSONException e) {
                ticket = null;
                // 获取token失败
                logger.error("获取token失败 errcode:{} errmsg:{}"
                        + jsonObject.getInt("errcode")
                        + jsonObject.getString("errmsg"));
            }
        }
        return ticket;
    }

    public static String createSign(String characterEncoding,
                                    Map<String, Object> param) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        sb.append("key=").append(EncodingAESKey);
        return MD5Encode(sb.toString(), characterEncoding).toUpperCase();
    }

    public static String createTickectSign(String characterEncoding,
                                           SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        String sign = MD5Encode(sb.toString(), characterEncoding);
        return sign;
    }

    public static String getJsTickectJson(String url) {
        Map<String, String> ret = sign(url);
        return JSONObject.fromObject(ret).toString();
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String genXML(Map param) {
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        for (Object key : param.keySet()) {
            Object value = param.get(key);
            xml.append("<").append(key).append("><![CDATA[").append(value)
                    .append("]]></").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }

    public static WeixinUser getWeixinUserInfo(String openid) {
        WeixinUser user = null;
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
            String post_url = url.replace("ACCESS_TOKEN",
                    getAccessToken().getToken()).replace("OPENID", openid);
            JSONObject json = XmlUtil.httpRequest(post_url, "GET", "");
            logger.info(json.toString());
            user = (WeixinUser) JSONObject
                    .toBean(json, WeixinUser.class);

        } catch (Exception e) {
            logger.info(e.toString());
        }
        return user;
    }

    public static Map<String, Object> getWeixinUserInfoByPost(String code) {
        String open_requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
        String requestUrl = open_requestUrl.replace("APPID", appid).replace(
                "APPSECRET", appsecret).replace("CODE", code);
        logger.info("requestUrl:" + requestUrl);
        JSONObject jsonObject = XmlUtil.httpRequest(requestUrl, "GET", null);
        logger.info("getopenid");
        for (Object obj : jsonObject.keySet().toArray()) {
            logger.info(obj.toString() + ":"
                    + jsonObject.getString(obj.toString()));
        }
        String nickname = "";
        String img_url = "";
//		if(jsonObject!=null){
//			String ref_url="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
//			ref_url=ref_url.replace("REFRESH_TOKEN", jsonObject.getString("refresh_token"));
//			JSONObject retjson = XmlUtil.httpRequest(ref_url, "GET", "");
//			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
//			String post_url = url.replace("ACCESS_TOKEN",
//					retjson.getString("access_token")).replace("OPENID", jsonObject
//							.getString("openid"));
//			logger.info(post_url);
//			JSONObject json = XmlUtil.httpRequest(post_url, "GET", "");
//			for (Object obj : json.keySet().toArray()) {
//				logger.info(obj.toString() + ":"
//						+ json.getString(obj.toString()));
//			}
//			nickname=EmojiFilter.filterEmoji(json.getString("nickname"));
//			img_url=json.getString("headimgurl");
//			WeixinUser weixinuser=getWeixinUserInfo(jsonObject.getString("openid"));
//			nickname=weixinuser.getNickname();
//			img_url=weixinuser.getHeadimgurl();
//		}
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openid", jsonObject.containsKey("openid") ? jsonObject
                .getString("openid") : "");
        map.put("nickname", nickname);
        map.put("img_url", img_url);
        map.put("unionid", jsonObject.containsKey("unionid") ? jsonObject
                .getString("unionid") : "");
        logger.info("map" + map.toString());
        // 如果请求成功
        return map;
    }

    public static String createSign(String characterEncoding,
                                    SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + EncodingAESKey);

        String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    public static Map<String, String> sign(String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + getTickect().getTicket() +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("appId", appid);
        ret.put("url", url);
        ret.put("jsapi_ticket", getTickect().getTicket());
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static void main(String args[]) {

        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        String post_url = url.replace("ACCESS_TOKEN",
                getAccessToken().getToken()).replace("OPENID", "o-0EUwoykGd2JvKxEtWJoaKDGc7E");
        logger.info(post_url);
        JSONObject json = XmlUtil.httpRequest(post_url, "GET", "");
        for (Object obj : json.keySet().toArray()) {
            logger.info(obj.toString() + ":"
                    + json.getString(obj.toString()));
        }
    }

    /**
     * @param code
     * @return
     */
    public static String getOpenid(String code) {
        String open_requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
        String requestUrl = open_requestUrl.replace("APPID",
                appid).replace("APPSECRET",
                appsecret).replace("CODE", code);
        logger.info("requestUrl:" + requestUrl);
        JSONObject jsonObject = XmlUtil.httpRequest(requestUrl, "GET", null);
        logger.info("getopenid");
        for (Object obj : jsonObject.keySet().toArray()) {
            logger.info(obj.toString() + ":"
                    + jsonObject.getString(obj.toString()));
        }
        // 如果请求成功
        return jsonObject.getString("openid");
    }

    /**
     * 通过code换取网页授权openid
     *
     * @return openid
     */
    public static String getCode_Openid(String code) {
        String openid = "";
        String url = CODE_ACCESS_TOKEN_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
        JSONObject jsonObject = doGetStr(url);
        if (null != jsonObject) {
            openid = jsonObject.getString("openid");
        }
        return openid;
    }

    /**
     * GET 获取
     *
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity) {
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * POST 请求
     *
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url, String outStr) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpPost.setEntity(new StringEntity(outStr, "utf-8"));
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            String temp = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            jsonObject = JSONObject.fromObject(temp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 发送消息给区享侠公众号
     * 订单
     *
     * @param msgmap
     * @param map
     * @return
     */
    public static JSONObject sendMsgOrder(Map<String, Object> msgmap, Map<String, Object> map) {
        //UserBindDao dao = SpringContextHolder.getBean("userBindDao");
        String openid = map.get("openid").toString();
        String url = msgmap.get("URL").toString(); // + "&openid=" + openid;
        String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        String access_token = getAccessToken().getToken();
        sendMsgUrl = sendMsgUrl.replace("ACCESS_TOKEN", access_token);
        String str = "{"
                + "'touser':'OPENID',"
                + "'template_id':'TEMPLATEID',"
                + "'url':'URL',"
                + "'topcolor':'#FF0000',"
                + "'data':{"
                + "        'first': {"
                + "            'value':'FIRST',"
                + "            'color':'#173177'"
                + "        },"
                + "        'keyword1': {"
                + "            'value':'KEYWORD1',"
                + "            'color':'#173177'"
                + "        },"
                + "        'keyword2':{"
                + "            'value':'KEYWORD2',"
                + "            'color':'#173177'"
                + "        },"
                + "        'keyword3': {"
                + "            'value':'KEYWORD3',"
                + "           'color':'#173177'"
                + "        },"
                + "        'keyword4': {"
                + "            'value':'KEYWORD4',"
                + "            'color':'#173177'"
                + "        },"
                + "        'keyword5':{"
                + "            'value':'KEYWORD5',"
                + "            'color':'#173177'"
                + "        },"
                + "        'remark':{"
                + "            'value':'REMARK',"
                + "            'color':'#173177'"
                + "        }"
                + "}}";
        str = str.replace("OPENID", openid).replace("TEMPLATEID", msgmap.get("TEMPLATEID").toString())
                .replace("URL", url).replace("KEYWORD5", msgmap.get("KEYWORD5").toString()).replace("FIRST", msgmap.get("FIRST").toString())
                .replace("KEYWORD1", msgmap.get("KEYWORD1").toString()).replace("KEYWORD2", msgmap.get("KEYWORD2").toString())
                .replace("KEYWORD3", msgmap.get("KEYWORD3").toString()).replace("KEYWORD4", msgmap.get("KEYWORD4").toString())
                .replace("REMARK", msgmap.get("REMARK").toString()).replace("'", "\"");
        logger.info("msg:" + str);
        JSONObject jsonObject2 = doPostStr(sendMsgUrl, str);
        logger.info("json:" + jsonObject2);
        return jsonObject2;
    }

    /**
     * 发送消息给区享侠公众号
     * 订单
     *
     * @param msgmap
     * @param map
     * @return 提交时间：{{tradeDateTime.DATA}}
     * 订单类型：{{orderType.DATA}}
     * 客户信息：{{customerInfo.DATA}}
     * {{orderItemName.DATA}}：{{orderItemData.DATA}}
     * {{remark.DATA}}
     */
    public static JSONObject sendMsgChengZhong(Map<String, Object> msgmap, Map<String, Object> map) {
        String openid = map.get("openid").toString();
        String url = msgmap.get("URL").toString(); // + "&openid=" + openid;
        String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        String access_token = getAccessToken().getToken();
        sendMsgUrl = sendMsgUrl.replace("ACCESS_TOKEN", access_token);
        String str = "{"
                + "'touser':'OPENID',"
                + "'template_id':'TEMPLATEID',"
                + "'url':'URL',"
                + "'topcolor':'#FF0000',"
                + "'data':{"
                + "        'first': {"
                + "            'value':'FIRST',"
                + "            'color':'#173177'"
                + "        },"
                + "        'tradeDateTime': {"
                + "            'value':'TRADEDATETIME',"
                + "            'color':'#173177'"
                + "        },"
                + "        'orderType': {"
                + "            'value':'ORDERTYPE',"
                + "            'color':'#173177'"
                + "        },"
                + "        'customerInfo':{"
                + "            'value':'CUSTOMERINFO',"
                + "            'color':'#173177'"
                + "        },"
                + "        'remark':{"
                + "            'value':'REMARK',"
                + "            'color':'#173177'"
                + "        }"
                + "}}";
        str = str.replace("OPENID", openid).replace("TEMPLATEID", msgmap.get("TEMPLATEID").toString())
                .replace("URL", url)
                .replace("FIRST", msgmap.get("FIRST").toString())
                .replace("TRADEDATETIME", msgmap.get("tradeDateTime").toString())
                .replace("ORDERTYPE", msgmap.get("orderType").toString()).replace("CUSTOMERINFO", msgmap.get("customerInfo").toString())
                .replace("REMARK", msgmap.get("REMARK").toString()).replace("'", "\"");
        logger.info("msg:" + str);
        JSONObject jsonObject2 = doPostStr(sendMsgUrl, str);
        logger.info("json:" + jsonObject2);
        return jsonObject2;
    }
}

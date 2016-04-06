package com.brilliantreform.sc.weixin.util;

import com.brilliantreform.sc.weixin.dao.UserBindDao;
import com.brilliantreform.sc.weixin.po.AccessToken;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.util.*;

public class WeixinUtil {

	@Autowired
    private UserBindDao userbindDao;
	private static Logger logger = Logger.getLogger(WeixinUtil.class);
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static final String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	public final static String appid="wx699aa43650cb214d";
	public final static String mch_id="1235122102";
	public final static String appsecret="16aebee192f261eded368797facd6ef9";
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	private static AccessToken accessToken;

	 /**
     * 获取access_token 
     *  
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @return 
     */  
	public static AccessToken getAccessToken() {
		Date getnow = new Date();
		logger.info("weixin now:"+getnow);
		logger.info("weixin accessToken:"+accessToken);
		if(accessToken!=null && getnow.before(DateUtils.addSeconds(accessToken.getUpdateDate(),accessToken.getExpiresIn()))){
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
				logger.info("weixin accessToken1:"+accessToken.getToken()+"----updatetime:"+accessToken.getUpdateDate());
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
    public static JSONObject sendMsgToUser(String msgContent){
    	String sendMsgUrl="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    	String access_token=getAccessToken().getToken();
    	sendMsgUrl=sendMsgUrl.replace("ACCESS_TOKEN", access_token);
    	JSONObject jsonObject = XmlUtil.httpRequest(sendMsgUrl, "POST", msgContent);  
    	return jsonObject;
    }
    public JSONObject sendMsgToUser(String msgContent,Map<String,Object> map){
    	Map<String,Object> openmap=userbindDao.getOpenid(map);
    	if(openmap==null){
    		return null;
    	}
    	String openid=openmap.get("openid").toString();
    	String sendMsgUrl="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    	String access_token=getAccessToken().getToken();
    	sendMsgUrl=sendMsgUrl.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobj=new JSONObject();
		jsonobj.put("touser", openid);
		jsonobj.put("msgtype", "text");
		JSONObject contentObj=new JSONObject();
		contentObj.put("content", msgContent);
		jsonobj.put("text", contentObj);
		WeixinUtil.sendMsgToUser(jsonobj.toString());
    	JSONObject jsonObject = XmlUtil.httpRequest(sendMsgUrl, "POST", msgContent);  
    	return jsonObject;
    }
    public static JSONObject urlLong2Short(String url){
    	String access_token=getAccessToken().getToken();
    	String longurl="https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
    	longurl=longurl.replace("ACCESS_TOKEN", access_token);
    	JSONObject paramObj=new JSONObject();
    	paramObj.put("action", "long2short");
    	paramObj.put("long_url", url);
    	JSONObject jsonObject = XmlUtil.httpRequest(longurl, "POST", paramObj.toString()); 
    	return jsonObject;
    }
    public JSONObject sendMsgTemToUser(Map<String,Object> msgmap,Map<String,Object> map){
    	
    	Map<String,Object> openmap=userbindDao.getOpenid(map);
    	if(openmap==null){
    		return null;
    	}
    	String openid=openmap.get("openid").toString();
    	String url=msgmap.get("URL").toString()+"&openid="+openid;
    	String sendMsgUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    	String access_token=getAccessToken().getToken();
    	sendMsgUrl=sendMsgUrl.replace("ACCESS_TOKEN", access_token);
		String str="{"
           +"'touser':'OPENID',"
           +"'template_id':'TEMPLATEID',"
           +"'url':'URL',"
           +"'topcolor':'#FF0000',"
           +"'data':{"
           +"        'first': {"
           +"            'value':'FIRST',"
           +"            'color':'#173177'"
           +"        },"
           +"        'keyword1': {"
           +"            'value':'KEYWORD1',"
           +"            'color':'#173177'"
           +"        },"
           +"        'keyword2':{"
           +"            'value':'KEYWORD2',"
           +"            'color':'#173177'"
           +"        },"
           +"        'keyword3': {"
           +"            'value':'KEYWORD3',"
           +"           'color':'#173177'"
           +"        },"
           +"        'keyword4': {"
           +"            'value':'KEYWORD4',"
           +"            'color':'#173177'"
           +"        },"
           +"        'keyword5':{"
           +"            'value':'KEYWORD5',"
           +"            'color':'#173177'"
           +"        },"
           +"        'remark':{"
           +"            'value':'REMARK',"
           +"            'color':'#173177'"
           +"        }"
           +"}}";
		str=str.replace("OPENID", openid).replace("TEMPLATEID", "SCyKqxlH8PwkbzuvRPMJ5UISoN2w_CnDC3EUZdWBZm4")
		        .replace("URL", url).replace("KEYWORD5", msgmap.get("KEYWORD5").toString()).replace("FIRST", msgmap.get("FIRST").toString())
		        .replace("KEYWORD1", msgmap.get("KEYWORD1").toString()).replace("KEYWORD2", msgmap.get("KEYWORD2").toString())
		       .replace("KEYWORD3", msgmap.get("KEYWORD3").toString()).replace("KEYWORD4", msgmap.get("KEYWORD4").toString())
		       .replace("REMARK", msgmap.get("REMARK").toString()).replace("'", "\"");
		logger.info("msg:"+str);
		WeixinUtil.sendMsgToUser(str);
    	JSONObject jsonObject = XmlUtil.httpRequest(sendMsgUrl, "POST", str);  
    	return jsonObject;
    }
    
    public JSONObject sendMsgExpressToUser(Map<String,Object> msgmap,Map<String,Object> map){
    	
    	Map<String,Object> openmap=userbindDao.getOpenid(map);
    	if(openmap==null){
    		return null;
    	}
    	String openid=openmap.get("openid").toString();
//    	String openid="odQOkt1AwEYGodms_48qmhXkazf8";
    	String url=msgmap.get("URL").toString()+"?openid="+openid;
    	String sendMsgUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    	String access_token=getAccessToken().getToken();
    	sendMsgUrl=sendMsgUrl.replace("ACCESS_TOKEN", access_token);
		String str="{"
           +"'touser':'OPENID',"
           +"'template_id':'TEMPLATEID',"
           +"'url':'URL',"
           +"'topcolor':'#FF0000',"
           +"'data':{"
           +"        'first': {"
           +"            'value':'FIRST',"
           +"            'color':'#173177'"
           +"        },"
           +"        'keyword1': {"
           +"            'value':'KEYWORD1',"
           +"            'color':'#173177'"
           +"        },"
           +"        'keyword2':{"
           +"            'value':'KEYWORD2',"
           +"            'color':'#173177'"
           +"        },"
           +"        'remark':{"
           +"            'value':'REMARK',"
           +"            'color':'#173177'"
           +"        }"
           +"}}";
		str=str.replace("OPENID", openid).replace("TEMPLATEID", "Tt5dw6QSbyVpRzLdd6ui0OaTPHnBKuQW3crQcBYcwaE")
		        .replace("URL", url).replace("FIRST", msgmap.get("FIRST").toString())
		        .replace("KEYWORD1", msgmap.get("KEYWORD1").toString()).replace("KEYWORD2", msgmap.get("KEYWORD2").toString())
		       .replace("REMARK", msgmap.get("REMARK").toString()).replace("'", "\"");
		logger.info("msg:"+str);
		WeixinUtil.sendMsgToUser(str);
    	JSONObject jsonObject = XmlUtil.httpRequest(sendMsgUrl, "POST", str);  
    	return jsonObject;
    }
    
    public static JSONObject sendMsgToUserTest(String msgContent,String openid){
    	String sendMsgUrl="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    	String access_token=getAccessToken().getToken();
    	sendMsgUrl=sendMsgUrl.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobj=new JSONObject();
		jsonobj.put("touser", openid);
		jsonobj.put("msgtype", "text");
		JSONObject contentObj=new JSONObject();
		contentObj.put("content", msgContent);
		jsonobj.put("text", contentObj);
		JSONObject customserviceObj=new JSONObject();
		customserviceObj.put("kf_account", "000001@quxiang2014");
		jsonobj.put("customservice", customserviceObj);
		WeixinUtil.sendMsgToUser(jsonobj.toString());
    	JSONObject jsonObject = XmlUtil.httpRequest(sendMsgUrl, "POST", msgContent);  
    	return jsonObject;
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
		for(Object key : param.keySet()) {
			Object value = param.get(key);
			xml.append("<").append(key).append("><![CDATA[").append(value).append("]]></").append(key).append(">");
		}
		xml.append("</xml>");
		return xml.toString();
	}

	public WeixinUtil(UserBindDao userBindDao)
    {
    	this.userbindDao = userBindDao;
    }
    
    public WeixinUtil()
    {
    	
    }
    
    public static void main(String args[]) {
//		System.out.println(ServiceOrderCtrl.getNativeUrl("10025826056378823947"));
    	Map<String,Object> pmap = new HashMap<String,Object>();
		pmap.put("phone","13675170252");
    	Map<String, Object> msgmap = new HashMap<String, Object>();
    	msgmap.put("URL",
		"http://www.qxit.com.cn/scframe/deliver/msgNotSign");
        msgmap.put("FIRST", "尊敬的用户您好，您有快递包裹已送达区享便民服务中心，请尽快至服务站领取！");
        msgmap.put("KEYWORD1", "顺通");
        msgmap.put("KEYWORD2", "123333");
        msgmap.put("REMARK", "感谢使用区享平台。");
		WeixinUtil weixin=new WeixinUtil();
		weixin.sendMsgExpressToUser(msgmap, null);
//    	JSONObject json=WeixinUtil.urlLong2Short("weixin://wxpay/bizpayurl?appid=wx699aa43650cb214d&mch_id=1235122102&nonce_str=2SKENKJ56dRsKSH7&product_id=10025826273981424487&time_stamp=1432627398167&sign=66DC9B6D0E4F5D769976A826FCD4CAFC");
//    	System.out.println(json.toString());

    } 

}

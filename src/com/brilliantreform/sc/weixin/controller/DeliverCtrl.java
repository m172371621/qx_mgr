package com.brilliantreform.sc.weixin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brilliantreform.sc.utils.JsonUtil;
import com.brilliantreform.sc.weixin.po.AccessToken;
import com.brilliantreform.sc.weixin.po.WeixinExpress;
import com.brilliantreform.sc.weixin.po.WeixinUser;
import com.brilliantreform.sc.weixin.service.DeliverService;
import com.brilliantreform.sc.weixin.service.UserBindService;
import com.brilliantreform.sc.weixin.util.XmlUtil;


/**
 * @author ch
 * 微信端快递
 */
@Controller
@RequestMapping(value="/deliver")
public class DeliverCtrl {

	private static Logger logger = Logger.getLogger(DeliverCtrl.class);
	
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public final static String appid="wx699aa43650cb214d";
	public final static String appsecret="16aebee192f261eded368797facd6ef9";
	
	@Autowired
	private DeliverService deliverService;
	
	@Autowired
	private UserBindService userBindService;
	
	/**链接到微信绑定手机号页面
	 * @return 绑定手机号页面
	 */
	@RequestMapping(value="/recWeixinAction")
	public String recWeixinAction(HttpServletRequest request,
			HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		Set set=request.getParameterMap().keySet();
//		String sopenid=request.getSession().getAttribute("openid").toString();
		Enumeration em= request.getSession().getAttributeNames();
		Map<String,Object> session_map=new HashMap<String, Object>();
		while (em.hasMoreElements()) {
			String attr = (String) em.nextElement();
			session_map.put(attr, attr);
		}
		String openid="";
		if(!session_map.containsKey("openid")){
			Object obj[]=set.toArray();
			logger.info(obj.length);
			for(Object o:obj){
				logger.info(o.toString());
				if("code".equals(o.toString())){
					String code=request.getParameter("code");
					logger.info("code:"+request.getParameter("code"));
					openid=getOpenid(code);
					logger.info(openid);
					request.getSession().setAttribute("openid", openid);
				}
			}
//			request.getSession().setAttribute("openid", "serewrwrsdf");
		}
		openid=request.getSession().getAttribute("openid").toString();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("openid", openid);
//		request.getSession().setAttribute("openid", "serewrwrsdf");
		try {
			//判断是否已经绑定平台手机号
			if(userBindService.isExistsUser(map)<1){
				request.getSession().setAttribute("refer", "/deliver/recWeixinAction");
				return "wap/weixin/userbindphone";
			}else{
				if(!isBinded(openid)){
					WeixinUser user=getWeixinUserInfo(openid);
					user.setOpenid(openid);
					user.setPhone(request.getParameter("phone").toString());
					deliverService.bindPhone(user);
				}
			}
			
//			//查询是否存在绑定的手机号,如果不存在，则链接到绑定手机号页面，存在则查询快递列表
//			if(!isBinded(openid)){
//				return "wap/weixin/bindPage";
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "wap/weixin/deliverNotSign";
	}
	
	@RequestMapping(value="/notSign")
	public String trun2DeliverNotSign() {
		logger.info("转到快递页面");
		return "wap/weixin/deliverNotSign";
	}
	@RequestMapping(value="/sign")
	public String trun2DeliverSign() {
		logger.info("转到快递页面");
		return "wap/weixin/deliverSign";
	}
	/**链接到微信绑定手机号页面
	 * @return 绑定手机号页面
	 */
	@RequestMapping(value="/bindPage")
	public String turn2BindPage(){
		return "wap/weixin/bindPage";
	}
	
	@RequestMapping(value="/operExpress")
	public String operExpress(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		try {
//			//查询是否存在绑定的手机号,如果不存在，则链接到绑定手机号页面，存在则查询快递列表
			String openid=request.getSession().getAttribute("openid").toString();
			if(!isBinded(openid)){
				return "wap/weixin/bindPage";
			}
		else{
			//查询快递列表
			int result_code = 0;
			String result_dec = "OK";
			try {
				
				List<WeixinExpress> expressList=getExpressList(request,response);
				String tmp=JsonUtil.result2Json(result_code, result_dec, expressList);
				System.out.println(tmp);
				response.getWriter().print(
						JsonUtil.result2Json(result_code, result_dec, expressList));
			} catch (Exception e) {
				logger.error("DeliverCtrl operExpress" + e.getMessage());
				response.getWriter().print(JsonUtil.result2Json(false, null));
			}
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "wap/weixin/deliverNotSign";
	}
	/**根据微信号查询是否有绑定的手机号
	 * @param openid 微信用户唯一号
	 * @return true=存在绑定的手机号 false=无绑定手机号
	 */
	public boolean isBinded(String openid){
		return deliverService.isBinded(openid);
	}
	/**
	 * 查询绑定的手机号列表
	 * @param openid 微信用户唯一号
	 * @return List<String> 绑定手机列表
	 */
	public List<Map<String,Object>> getPhoneListById(String openid){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("openid", openid);
		return deliverService.getPhoneListById(map);
	}
	//根据微信号绑定的手机号查询快递相关信息
	@RequestMapping(value="/getPhoneListByIdAction")
	public String getPhoneListByIdAction(HttpServletRequest request,
			HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("openid", request.getSession().getAttribute("openid"));
//			map.put("openid", "serewrwrsdf");
			int result_code = 0;
			String result_dec = "OK";
			try {
				List<Map<String,Object>> phoneList=deliverService.getPhoneListById(map);
				response.getWriter().print(
						JsonUtil.result2Json(result_code, result_dec, phoneList));
				
			} catch (Exception e) {
				logger.error("DeliverCtrl operExpress" + e.getMessage());
				response.getWriter().print(JsonUtil.result2Json(false, null));
			}
		} catch (Exception e) {
		}
		return null;
	}
	//根据微信号绑定的手机号查询快递相关信息
	@RequestMapping(value="/getExpressListAction")
	public String getExpressListAction(HttpServletRequest request,
			HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("openid", request.getSession().getAttribute("openid"));
//			map.put("openid", "serewrwrsdf");
			int result_code = 0;
			String result_dec = "OK";
			try {
				List<WeixinExpress> expressList=deliverService.getExpressList(map);
				response.getWriter().print(
						JsonUtil.result2Json(result_code, result_dec, expressList));
				
			} catch (Exception e) {
				logger.error("DeliverCtrl operExpress" + e.getMessage());
				response.getWriter().print(JsonUtil.result2Json(false, null));
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	@RequestMapping(value="/getSighExpressListAction")
	public String getSighExpressListAction(HttpServletRequest request,
			HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("openid", request.getSession().getAttribute("openid"));
//			map.put("openid", "serewrwrsdf");
			int result_code = 0;
			String result_dec = "OK";
			try {
				List<WeixinExpress> expressList=deliverService.getSignExpressList(map);
				response.getWriter().print(
						JsonUtil.result2Json(result_code, result_dec, expressList));
				
			} catch (Exception e) {
				logger.error("DeliverCtrl operExpress" + e.getMessage());
				response.getWriter().print(JsonUtil.result2Json(false, null));
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	//根据微信号绑定的手机号查询快递相关信息
	public List<WeixinExpress> getExpressList(HttpServletRequest request,
			HttpServletResponse response){
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("openid", request.getSession().getAttribute("openid"));
			int result_code = 0;
			String result_dec = "OK";
			try {
				List<WeixinExpress> expressList=deliverService.getExpressList(map);
				JSONArray jsonarray=JSONArray.fromObject(expressList);
				
				response.getWriter().print(
						JsonUtil.result2Json(result_code, result_dec, expressList));
			} catch (Exception e) {
				logger.error("DeliverCtrl operExpress" + e.getMessage());
				response.getWriter().print(JsonUtil.result2Json(false, null));
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	//根据微信号绑定的手机号查询快递相关信息
	
	public List<WeixinExpress> getSignExpressList(HttpServletRequest request,
			HttpServletResponse response){
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("openid", request.getSession().getAttribute("openid"));
			int result_code = 0;
			String result_dec = "OK";
			try {
				List<WeixinExpress> expressList=deliverService.getSignExpressList(map);
				JSONArray jsonarray=JSONArray.fromObject(expressList);
				
				response.getWriter().print(
						JsonUtil.result2Json(result_code, result_dec, expressList));
			} catch (Exception e) {
				logger.error("DeliverCtrl operExpress" + e.getMessage());
				response.getWriter().print(JsonUtil.result2Json(false, null));
			}
		} catch (Exception e) {
		}
		return null;
	}
	//给微信绑定手机号
	@RequestMapping(value="/bindPhone")
	public String bindPhone(HttpServletRequest request,
			HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		try {
			String openid=request.getSession().getAttribute("openid").toString();
//			String openid="serewrwrsdf";
			String phone=request.getParameter("phone");
			//查询用户基本信息
			WeixinUser user=getWeixinUserInfo(openid);
			user.setOpenid(openid);
			user.setPhone(phone);
			deliverService.bindPhone(user);
			int result_code = 0;
			String result_dec = "OK";
			try {
				response.getWriter().print(
						JsonUtil.result2Json(result_code, result_dec, phone));
			} catch (Exception e) {
				logger.error("DeliverCtrl bindPhone" + e.getMessage());
				response.getWriter().print(JsonUtil.result2Json(false, null));
			}
			return "wap/weixin/deliverNotSign";
		} catch (Exception e) {
		}
		return null;
	}
	
	public WeixinUser getWeixinUserInfo(String openid){
		String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		String post_url= url.replace("ACCESS_TOKEN", getAccessToken(appid, appsecret).getToken()).replace("OPENID",openid);
		JSONObject json=XmlUtil.httpRequest(post_url, "GET", "");
		WeixinUser user=(WeixinUser) JSONObject.toBean(json, WeixinUser.class);
		return user;
	}
	//删除绑定手机号
	@RequestMapping(value="/delPhone")
	public String delPhone(String phone,HttpServletRequest request,
			HttpServletResponse response){
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("openid", request.getSession().getAttribute("openid"));
//			String openid="serewrwrsdf";
			map.put("phone", phone);
			int result_code = 0;
			String result_dec = "OK";
			try {
				deliverService.delPhone(map);
				response.getWriter().print(
						JsonUtil.result2Json(result_code, result_dec, phone));
			} catch (Exception e) {
				logger.error("DeliverCtrl bindPhone" + e.getMessage());
				response.getWriter().print(JsonUtil.result2Json(false, null));
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /** 
     * 获取access_token 
     *  
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @return 
     */  
    public static AccessToken getAccessToken(String appid, String appsecret) {  
        AccessToken accessToken = null;  
      
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);  
        JSONObject jsonObject = XmlUtil.httpRequest(requestUrl, "GET", null);  
        // 如果请求成功  
        if (null != jsonObject) {  
            try {  
                accessToken = new AccessToken();  
                accessToken.setToken(jsonObject.getString("access_token"));  
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
            } catch (JSONException e) {  
                accessToken = null;  
                // 获取token失败  
                logger.error("获取token失败 errcode:{} errmsg:{}"+jsonObject.getInt("errcode")+jsonObject.getString("errmsg"));  
            }  
        }  
        return accessToken;  
    }
    
    public static String getOpenid(String code) {  
        String open_requestUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
        String requestUrl = open_requestUrl.replace("APPID", appid).replace("APPSECRET", appsecret).replace("CODE", code);  
        JSONObject jsonObject = XmlUtil.httpRequest(requestUrl, "GET", null); 
        logger.info("getopenid");
        for(Object obj:jsonObject.keySet().toArray()){
        	logger.info(obj.toString()+":"+jsonObject.getString(obj.toString()));
        }
        // 如果请求成功  
        return jsonObject.getString("openid");
    }
    
    public String getRequestString(HttpServletRequest request,
			HttpServletResponse response){
    	 PrintWriter printWriter = null;  
         ServletInputStream sis = null;  
           
         String xmlData = null;  
           
         try {  
             printWriter = response.getWriter();  
             // 取HTTP请求流  
             sis = request.getInputStream();  
             // 取HTTP请求流长度  
             int size = request.getContentLength();  
             logger.info(size);
             // 用于缓存每次读取的数据  
             byte[] buffer = new byte[size];  
             // 用于存放结果的数组  
             byte[] xmldataByte = new byte[size];  
             int count = 0;  
             int rbyte = 0;  
             // 循环读取  
             while (count < size) {   
                 // 每次实际读取长度存于rbyte中  
                 rbyte = sis.read(buffer);   
                 for(int i=0;i<rbyte;i++) {  
                     xmldataByte[count + i] = buffer[i];  
                 }  
                 count += rbyte;  
             }  
               
             xmlData = new String(xmldataByte, "UTF-8");  
             logger.info(xmlData);
          }catch(Exception e){
        	  logger.info(e.getMessage());
          }
          return xmlData;
    }
    public static void main(String[] args) {
//    	AccessToken as=new AccessToken();
//    	as=DeliverCtrl.getAccessToken("wx699aa43650cb214d","16aebee192f261eded368797facd6ef9");
//    	System.out.println(as.getToken());
    	DeliverCtrl de=new DeliverCtrl();
    	WeixinUser user= de.getWeixinUserInfo("odQOkt1AwEYGodms_48qmhXkazf8");
    	System.out.println(user.getNickname());
	}
}

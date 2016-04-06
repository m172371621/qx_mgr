package com.brilliantreform.sc.weixin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brilliantreform.sc.user.po.UserInfo;
import com.brilliantreform.sc.weixin.service.UserBindService;
import com.brilliantreform.sc.weixin.util.WeixinUtil;

@Controller
@RequestMapping(value="/userbindctrl")
public class UserBindCtrl {

	private static Logger logger = Logger.getLogger(UserBindCtrl.class);
	private static String content="谢谢您关注区享公众号，同时你可通过登录区享app客户端来获取更多小区服务，用户名为NAME，密码为PWD，服务区域为MUNITY";
	@Autowired
	private UserBindService userBindService;
	
	@RequestMapping(value="/trun2Bind")
	public String trun2Bind() {
		logger.info("转到快递页面");
		return "wap/weixin/userbindphone";
	}
	
	/**链接到微信绑定手机号页面
	 * @return 绑定手机号页面
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/bindphone")
	public String recWeixinAction(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String openid=request.getSession().getAttribute("openid").toString();
		String phone=request.getParameter("phone");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("openid", openid);
		map.put("phone", phone);
		request.getSession().setAttribute("phone", phone);
		String refer=request.getSession().getAttribute("refer").toString();
		try {
			userBindService.bindPhone(map);
			Map<String,Object> usermap=new HashMap<String, Object>();
			usermap.put("phone", phone);
			usermap.put("loginname", phone);
			String pwd=String.valueOf(((new Random().nextInt(899999))+100000));
			usermap.put("password", pwd);
			usermap.put("username", phone);
			userBindService.insertuserinfo(usermap);
			content=content.replace("NAME", phone).replace("PWD", pwd);
			if(userBindService.isBindCommunity(usermap)<1){
				return "wap/weixin/bindcommunity";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "forward:"+refer+"?phone="+phone;
	}
	
	/**微信绑定小区
	 * @return 
	 */
	@RequestMapping(value="/bindcommunity")
	public String bindcommunity(HttpServletRequest request,
			HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String openid=request.getSession().getAttribute("openid").toString();
		String phone=request.getSession().getAttribute("phone").toString();
		String refer=request.getSession().getAttribute("refer").toString();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("openid", openid);
		map.put("phone", phone);
//		Enumeration en=request.getParameterNames();
//		while (en.hasMoreElements()) {
//			String str = (String) en.nextElement();
//			logger.info(str);
//			
//		}
		try {
			UserInfo user=userBindService.getUserInfo(map);
			map.put("userid", user.getUserId());
			map.put("communityId", request.getParameter("communityid").toString());
			String name = request.getParameter("community");
			String temp = new String(name.getBytes("ISO-8859-1"),"utf-8");  
			name = URLDecoder.decode(temp, "utf-8"); 
			content=content.replace("MUNITY", name);
			userBindService.bindCommunity(map);
			JSONObject jsonobj=new JSONObject();
			jsonobj.put("touser", openid);
			jsonobj.put("msgtype", "text");
			JSONObject contentObj=new JSONObject();
			contentObj.put("content", content);
			jsonobj.put("text", contentObj);
			WeixinUtil.sendMsgToUser(jsonobj.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "forward:"+refer+"?phone="+phone;
	}
}

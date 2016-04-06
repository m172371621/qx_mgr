package com.brilliantreform.sc.system.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.brilliantreform.sc.utils.HttpUtil;

public class SMSutils {
	
	private static Logger logger = Logger.getLogger(SMSutils.class);
	
	//验证码过期时间
	public static long CODE_EXP_TIME = 1*60*1000l;

	//验证码缓存
	public static Map<String,String> MOBILE_SMSCODE = new HashMap<String,String>();
	
	/**
	 * 
	 * @param code
	 * @param mobile
	 * @return 1 验证码错误 2验证码超时
	 */
	public static int validateSMSCode(String code,String mobile)
	{
		
		logger.info("into SMSutils validateSMSCode :"+ mobile + " " + code);
		
		int result = 0;
		
		try{
			
			long now = System.currentTimeMillis();
			String smsCode = MOBILE_SMSCODE.get(mobile);
			
			//验证码错误
			if(smsCode == null)
				result = 1;
			else
			{
				String rcode = smsCode.split("\\|")[0];
				long time = Long.parseLong(smsCode.split("\\|")[1]);
			
				//验证码错误
				if(!rcode.equalsIgnoreCase(code))
					result = 1;
				
				//验证码超时
				else if(now - time > CODE_EXP_TIME)
					result = 2;
			}
			
		}catch(Exception e)
		{
			logger.error("SMSutils validateSMSCode:"+e.getMessage());
			result = 1;
		}
		
		logger.info("end SMSutils validateSMSCode :"+ result);
		//验证码错误
		
		return result;
		
		
	}
	
	public static boolean createSMSCode(String mobile)
	{
		boolean flag = false;
		long now = System.currentTimeMillis();
		String code = RandomStringUtils.randomNumeric(6);
		
		if(sendSMSCode(code,mobile))
		//if(true)
		{
			MOBILE_SMSCODE.put(mobile, code + "|" + now);
			flag = true;
		}
		
		logger.info("end SMSutils validateSMSCode :" + flag + " " + mobile + " " + code);
		return flag;
		
	}
	
	public static boolean sendSMSCode(String code,String phone)
	{
		logger.info("int SMSutils sendSMSCode :"+ code + ";" + phone);
		
		String url = "http://www.mssms.cn:8000/msm/sdk/http/sendsms.jsp";
		
		StringBuilder sb = new StringBuilder("username=NFTB700193&scode=899514&tempid=MB-2013102300");
		sb.append("&content=");
		sb.append("@1@=");
		sb.append(code);
		sb.append("&mobile=");
		sb.append(phone);
			
		String result = HttpUtil.doGet(url, sb.toString());
		
		logger.info("SMSutils sendSMSCode :"+ result);
		
		if(result.startsWith("0"))
		{
			return true;
		}
			
		return false;
	}
	
}

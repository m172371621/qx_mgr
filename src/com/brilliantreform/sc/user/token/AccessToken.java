package com.brilliantreform.sc.user.token;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.system.config.OauthConstant;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.HASH;

@Service("accessToken")
public class AccessToken {

	private static Logger logger = Logger.getLogger(AccessToken.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 生成 access_Token
	 * @param customerKey 客户标识
	 * @param userId
	 * @return access_Token
	 */
	public String createToken(String customerKey,String userId)
	{
		
		StringBuffer sb = new StringBuffer();
		long now = System.currentTimeMillis();
		
		sb.append(customerKey);
		sb.append(now);
		sb.append(UUID.randomUUID().toString());
		sb.append(OauthConstant.OAUTH_VERSION);
		
		String token = HASH.sha1(sb.toString());;
		
		token = token + "-" + userId; 
		
		TokenMap.map.put(token, now);
		this.userService.insertToken(token, now);
		
		logger.info("end AccessToken createToken :" + userId + " " + token);
		return token;
	}
	
	/**
	 * 注销 access_Token
	 * @param customerKey 客户标识
	 * @param userId
	 * @return access_Token
	 */
	public void destroyToken(String token)
	{
		logger.info("into AccessToken destroyToken :" + token);
		if(StringUtils.isNotBlank(token) && TokenMap.map.containsKey(token))
		{
			logger.info("end AccessToken destroyToken");
			TokenMap.map.remove(token);
			this.userService.delToken(token);
		}else			
		{
			logger.info("end AccessToken destroyToken not exist");
		}
	}
	
	/**
	 * 验证 access_Token
	 * 
	 * @param token
	 * @return 0 验证成功 token过期
	 */
	@SuppressWarnings("unchecked")
	public int checkToken(String token)
	{	
		logger.info("into AccessToken checkToken :" + token);
		int resultCode = 0;
		
		//token不存在
		if(StringUtils.isBlank(token))
		{
			resultCode = -1;
			logger.info("end AccessToken checkToken :" + token + " -1");
			return resultCode;
		}
		
		if(!TokenMap.map.containsKey(token))
		{
			Map map = this.userService.getToken(token);
			
			if(map != null)
			{
				TokenMap.map.put(token, (Long)map.get("time"));
			}
			
		}	
		
		if(!TokenMap.map.containsKey(token))
		{
			
			resultCode = -1;
			logger.info("end AccessToken checkToken :" + token + " -3");
			return resultCode;
		}	
		
		long time = TokenMap.map.get(token);
		//token超时
		if(System.currentTimeMillis() - time > OauthConstant.EXPIRES_TIME)
		{
			resultCode = -2;
			logger.info("end AccessToken checkToken :" + token + " -2");
			
		}
		
		//从token中获取用户ID
		resultCode = AccessToken.getUserId(token);
			
		logger.info("end AccessToken checkToken :" + token + " " + resultCode);
		return resultCode;
	}
	
	/**
	 * 获取用户ID
	 * 
	 * @param token
	 * @return 0 获取失败 
	 */
	public static int getUserId(String token)
	{	
		
		int userId = 0;
		if(StringUtils.isNotBlank(token) )
		{
		  String[] info = token.split("-");
		  if(info.length == 2 && StringUtils.isNumeric(info[1]))
		  {
			  logger.info("end AccessToken getUserId :" + info[1]);
			  return Integer.parseInt(info[1]);
		  }
		}
		
		logger.info("end AccessToken getUserId :" + userId);
		return userId;
	}
	
	
}


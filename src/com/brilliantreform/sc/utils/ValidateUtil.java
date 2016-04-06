package com.brilliantreform.sc.utils;

import java.util.regex.Pattern;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.brilliantreform.sc.system.sms.SMSutils;
import com.brilliantreform.sc.system.xmpp.XmppUtil;

public class ValidateUtil {

	public static boolean checkMobile(String mobile)
	{
		Pattern pattern = Pattern.compile("^1\\d{10}$");
		
		if(mobile!=null && pattern.matcher(mobile).matches())
			return true;
		
		return false;
	}
	
	public static boolean checkSMSCode(String code)
	{
		Pattern pattern = Pattern.compile("^\\d{6}$");
		
		if(code!=null && pattern.matcher(code).matches())
			return true;
		
		return false;
	}
	
	public static boolean checkUserType(String type)
	{
		Pattern pattern = Pattern.compile("^[1|2]$");
		
		if(type!=null && pattern.matcher(type).matches())
			return true;
		
		return false;
	}
	
	public static void main(String[] args)
	{
		//XmppUtil.createXmppAccount("ccc","111111");
	}
	
}

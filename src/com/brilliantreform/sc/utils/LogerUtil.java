package com.brilliantreform.sc.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class LogerUtil {
		
	@SuppressWarnings("unchecked")
	public  static void logRequest(HttpServletRequest request,Logger logger,String name)
	{
        
		Enumeration paramNames = request.getParameterNames();  
		StringBuffer sb = new StringBuffer();
	    while (paramNames.hasMoreElements()) {  
	      String paramName = (String) paramNames.nextElement();  
	  
	      String[] paramValues = request.getParameterValues(paramName);  
	      if (paramValues.length == 1) {  
	        String paramValue = paramValues[0];  
	        if (paramValue.length() != 0) {  
	         
	        	sb.append(paramName);
	        	sb.append(":");
	        	sb.append(paramValue);
	        	sb.append(";");
	        	
	        }  
	      }  
	    } 
	    
	    logger.info(name + ":" + sb.toString());
	}
	
}

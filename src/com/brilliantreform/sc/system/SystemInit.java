package com.brilliantreform.sc.system;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Service;

import com.brilliantreform.sc.system.dicti.Dicti;
import com.brilliantreform.sc.system.dicti.DictiInit;
import com.brilliantreform.sc.system.dicti.service.DictiService;
import com.brilliantreform.sc.user.service.UserService;


public class SystemInit implements ServletContextListener   {



	/** *//**  
     * 这个方法在Web应用服务做好接受请求的时候被调用。  
     *   
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)  
     */  
    public void contextInitialized(ServletContextEvent event)    
    {   
      
    	DictiService dictiService = (DictiService)SpringContextHolder.getBean("dictiService");
    	DictiInit dinit = new DictiInit();
    	dinit.InitDicti(dictiService);
    	
    	/*Map map = Dicti.get(Dicti.get("快递公司"));
    	for (Object key : map.keySet()) {
    		   System.out.println("key= "+ key + " and value= " + map.get(key));
    	}*/
    }   
       
       
    /** *//**  
     * 这个方法在Web应用服务被移除，没有能力再接受请求的时候被调用。  
     *   
    * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)  
     */  
    public void contextDestroyed(ServletContextEvent event)   
    {   
        
    }   
    
}

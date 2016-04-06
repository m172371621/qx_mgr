<%@page import="com.brilliantreform.sc.order.service.OrderService"%>
<jsp:directive.page import="org.springframework.web.context.WebApplicationContext" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
Community c =(Community)request.getSession().getAttribute("selectCommunity");
WebApplicationContext context = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

OrderService os = (OrderService) context.getBean("orderService");
int count = os.countNewOrder(c.getCommunity_id());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>alarm</title>
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script type="text/javascript" >

    	  
    	 var count= <%=os.countNewOrder(c.getCommunity_id())%>;

    	 setTimeout(function(){
			 location.reload(); 
	  	 },60000);
	  	

      </script>
</head>
<body>
<!--  embed id="txMp3" src="zxdl.mp3" hidden="true" autostart="true" loop="true">-->
<% if(count!=0){ %>
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0"  
WIDTH="550" HEIGHT="400" >  
<PARAM VALUE="http://sucai.flashline.cn/flash2/yinyue/deaa1c5d0b8736c7efb8e423a69ef91c.swf">  
<PARAM VALUE=high>  
<PARAM VALUE=#FFFFFF>  
<EMBED src="http://sucai.flashline.cn/flash2/yinyue/deaa1c5d0b8736c7efb8e423a69ef91c.swf" quality=high bgcolor=#FFFFFF WIDTH="550" HEIGHT="400"  
NAME="myMovieName" ALIGN="" TYPE="application/x-shockwave-flash"  
PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">  
<%} %>
</EMBED>  
</OBJECT> 
</body>
</html>
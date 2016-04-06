<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.express.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
	


  </head>
  
  <body>
  <div>
  	区享科技-快递签收单
  </div>
  <br>
  <% Express ex = (Express)request.getAttribute("express"); 
  	 Map commap =  Dicti.get(Dicti.get("快递公司")); 
  %>
<div style="font-size:12px">
*************************
<br>
快递公司：<%= commap.get(Integer.parseInt(ex.getExpress_com())) %><br>
快递单号：<%= ex.getExpress_no() %><br>
手机号：<% if(ex.getUser_phone() != null && !"12345678900".equals(ex.getUser_phone())) out.println(ex.getUser_phone()); %><br>
地址：<% if(ex.getExpress_info() != null) out.println(ex.getExpress_info()); %><br>	           
签收日期：<br>
&nbsp;&nbsp;&nbsp;<%= CommonUtil.formatTime(System.currentTimeMillis(),1) %><br>
签收人：<br><br><br><br><br>
*************************
</div>
 <br>
 <div>
 </div>
      <input id="but" type="button" value="打印" onclick="me_print()"> 
  </body>
  <script type="text/javascript">
  function me_print()
  {		
  				document.getElementById('but').style="display:none";
					window.print();	
					
  }				
</script>
</html>

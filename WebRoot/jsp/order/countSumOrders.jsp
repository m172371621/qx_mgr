<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.cxy.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link type="text/css" href="http://code.jquery.com/ui/1.9.1/themes/smoothness/jquery-ui.css" rel="stylesheet" />

    <link href="http://passport.smartjs.cn:8139/jsp/appointment/jQuery-Timepicker-Addon/jquery-ui-timepicker-addon.css" type="text/css" />

    <script src="http://code.jquery.com/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://code.jquery.com/ui/1.9.1/jquery-ui.min.js"></script>
    <script src="http://passport.smartjs.cn:8139/jsp/appointment/jQuery-Timepicker-Addon/jquery-ui-timepicker-addon.js" type="text/javascript"></script>

    <!--中文-->
   <script type="text/javascript" src="http://passport.smartjs.cn:8139/jsp/appointment/jquerytimepickerzh.js"></script>
<script type="text/javascript">
//读取url
function getUrlParam(name) {
 var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
 //构造一个含有目标参数的正则表达式对象
 var r = window.location.search.substr(1).match(reg);
 //匹配目标参数
 if (r != null)
  return unescape(r[2]);
 return null;
 //返回参数值
}

$(function (){
	 jQuery('#start').datetimepicker({
         timeFormat: "HH:mm:ss",
         dateFormat: "yy-mm-dd"});
	 jQuery('#end').datetimepicker({
         timeFormat: "HH:mm:ss",
         dateFormat: "yy-mm-dd"});

	
	$("#start").val(getUrlParam("start"));
	$("#end").val(getUrlParam("end"));
	//回显 选择框
	var cid=getUrlParam("cid");
	$("#comm").val(cid);
    /* $("#comm option").each(function(){ //遍历全部option
        var txt = $(this).val(); //获取option的内容
        //如果是
        if(cid==txt){
        	$("#comm").val(cid);
        }
    }); */
	
});

	
</script>
</head>
<body>
<form method="get" action="${pageContext.request.contextPath }/ordercxy.do">
<input type="hidden" value="countOrder" name="method"/>
当前小区：<select  id="comm" class="i_text"  name="cid">  
								 <!-- <span>当前小区：<select name="cid1"  id="comm" class="i_text" > -->
									<%-- <%if(session.getAttribute("user_isAdmin")!= null){ %><option value="0"  >全部</option><%} %> --%>
									<%
									   int cid =((Community) session.getAttribute("selectCommunity")).getCommunity_id();
									   List<Community> clist = (List<Community>)session.getAttribute("user_community");
									   for(Community c : clist){
									%>
									<option value="<%= c.getCommunity_id()%>" <% if(c.getCommunity_id() == cid) {out.print("selected");  }%> ><%= c.getCommunity_name() %></option>
									<% } %>
								</select>
开始<input name="start" id="start" type="text"/> 
结束<input name="end" id="end"  type="text"/> 
 类别<select  name="goodtype" id ="goodtype">
		<option value="4">全部</option>
		<option value="1">商品类</option>
		<option value="2">服务类</option>
		<option value="3">模糊订单</option>
</select>  
<input type="submit" value="提交"/>
</form>

	<table width="100%" border="0" cellpadding="2" cellspacing="0">
		<tr>
			<td width="100%">
				<table id="cityCount"  border="0" cellpadding="3" cellspacing="1" width="100%"
					align="center" style="background-color: #b9d8f3;">
					<tr
						style="text-align: center; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold">
						<td><font size="2">类型</font></td>
						<td><font size="2">商品名</font></td>
						<td><font size="2">总销量</font></td>
						<td><font size="2">总价格</font></td></tr>
							<%
							List<CountOrder> slist = (List<CountOrder>)request.getSession().getAttribute("countSumOrders");
							double money=0;
							   double count=0;
							   String status = "商品类";   
									   for(CountOrder s : slist){
							%>
							<% 
							    if(s.getOrder_type()== 2){
								   status = "服务类";
							   }else if(s.getOrder_type() == 3){
								   status = "模糊订单";
							   }  
							   money+=Double.valueOf(s.getPrice());
							 count+=Double.valueOf(s.getAmount()); 
							   %>
										<tr
											style="text-align: center; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold">
											<td><font size="2"><%= status%></font></td>
											<td><font size="2"><%= s.getProduct_name()%></font></td>
											<td><font size="2"><%= s.getAmount()%></font></td>
											<td><font size="2"><%= s.getPrice()%></font></td>
										</tr>
							<% } %>
					<tr><td ></td><td ></td><td >总价<%=money %></td><td>总销量<%=count %></td><tr/>
				</table>
			</td>
		</tr>
	</table>
	
	

</body>
</html>


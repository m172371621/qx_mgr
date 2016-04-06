<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
 
<script type="text/javascript">
$(function() {
	$("input[name='start_time']").datepicker({

        dateFormat: "yy-mm-dd"
    });
	$("input[name='end_time']").datepicker({

        dateFormat: "yy-mm-dd"
    });
});
</script>

</head>
<body <% if(request.getAttribute("pickUp")!=null) out.print("onload=\"alert('设置成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="statistics_1"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">注册数量查询</div>
					<div class="sform_wrap c">
					<% Map smap =  (Map)request.getAttribute("smap");
					   if(smap == null)
						   smap = new HashMap();
					   int cid =  smap.get("cid") == null ? 0 : (Integer)smap.get("cid");
					%>
						<form action="${pageContext.request.contextPath }/statistics.do?method=countRegist" method="post" id="searchForm">
							<ul>
								<li>选择小区
								<select name="cid"  id="" class="i_text_1">
									<option value="0" >全部</option>	
									<%
									   List<Community> clist = (List<Community>)session.getAttribute("user_community");
									   for(Community c : clist){
									%>
									<option value="<%= c.getCommunity_id()%>" <%if(cid == c.getCommunity_id()) out.print("selected"); %>><%= c.getCommunity_name() %></option>								
									<% } %>
								</select></li>
								<li>时间从（包含）：<input type="text"  class="i_text" name="start_time" value="<%= JsonUtil.obj2Str(smap.get("start_time")) %>"/></li>
								<li>到（包含）：<input type="text"  class="i_text" name="end_time" value="<%= JsonUtil.obj2Str(smap.get("end_time")) %>" /></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
							</form>
						</div>
						
							
						
						<script type="text/javascript">
							function submitForm()
							{
								
								$('#searchForm').submit();
							}						
						</script>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col width="180px"/><col width="80px"/></colgroup>
							<tr>
								<th>小区 </th>
								<th>统计时间</th>
								<th>注册数量 </th>							
								
							</tr>
							<% List<Map> list = (List<Map>)request.getAttribute("list");
							   String times = smap.get("start_time") == null ? "开始" : (String)smap.get("start_time");
							   times += "<-->";
							   times += smap.get("end_time") == null ? "至今" : (String)smap.get("end_time");
							   for(Map map : list){
							%>
							<tr>							
								<td><%= map.get("cid") %> </td>
								<td><%= times %></td>
								<td><%= map.get("count") %> </td>
								
							</tr>
							<% }%>
							
						</table>
					</div>
					

					 
	</div>
</div>
<!-- content end -->

</div>
</body>
</html>

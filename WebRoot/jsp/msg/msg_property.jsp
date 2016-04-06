<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.task.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="task_0"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">物业消息</div>
				
							
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col width="80px"/><col width="80px"/><col width="120px"/><col width="80px"/><col width="120px"/><col width="160px"/><col width="160px"/><col width="80px"/></colgroup>
							<tr>
								<th>标题 </th>
								<th>发布时间</th>
								<th class="icf">&#xf013e;</th>
							</tr>
							<% List<Task> list = (List<Task>)request.getAttribute("list");
							   int pageIndex = 0;
							   int pageCount = 0;
							   if(list!=null){
							   for(Task task: list) {
							   String ptime = CommonUtil.formatTimestamp(task.getPublish_time(),0);
							   String etime = CommonUtil.formatTimestamp(task.getExpired_time(),0);
							   String status = "任务审核中";
							   if(task.getTask_status() == 1)
							   {
								   status = "任务发布中";
							   }else if(task.getTask_status() == 2)
							   {
								   status = "任务被接受";
							   }else if(task.getTask_status() == 3)
							   {
								   status = "任务已完成";
							   }else if(task.getTask_status() == 4)
							   {
								   status = "任务发布者已评价";
							   }else if(task.getTask_status() == 6)
							   {
								   status = "任务接受者已评价";
							   }else if(task.getTask_status() == 7)
							   {
								   status = "双方已评 ";
							   }else if(task.getTask_status() == 5)
							   {
								   status = "任务过期";
							   }else if(task.getTask_status() == 9)
							   {
								   status = "任务审核中";
							   }
							
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(task.getTask_title()) %></td>							
								<td><%= JsonUtil.obj2Str(task.getSend_user_name()) %> </td>
								<td><%= JsonUtil.obj2Str(task.getReceive_user_name()) %></td>
								<td><%= status%> </td>
								<td><%= JsonUtil.obj2Str(task.getPhone()) %></td>
								<td><%= JsonUtil.obj2Str(task.getCommunity()) %></td>
								<td><%= ptime %></td>
								<td><%= etime %></td>
								<td><a target="_blank" href="${pageContext.request.contextPath }/task.do?method=edit&type=1&viewId=<%= task.getTask_id() %>" class="btn btn_r">操作</a></td>
							</tr>
							<% }} %>
						</table>
					</div>
					
<jsp:include  page="../common/page.jsp"  flush="true">   
     <jsp:param  name="pageIndex"  value="<%= pageIndex %>"/>   
     <jsp:param  name="pageCount"  value="<%= pageCount %>"/>  
</jsp:include>	
					 
	</div>
</div>
<!-- content end -->

</div>
</body>
</html>

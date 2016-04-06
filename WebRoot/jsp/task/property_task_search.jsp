<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.task.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
 
<script type="text/javascript">
$(function() {
	$("input[name='start_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
	$("input[name='end_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
});
</script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="task_1"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">物业任务查询</div>
					<div class="sform_wrap c">
							<ul>
							<% 
							   TaskQueryBean searchBean = request.getAttribute("searchBean") == null? new TaskQueryBean(): (TaskQueryBean)request.getAttribute("searchBean");
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   
							   int task_status = searchBean.getTask_status()== null ? 0 : searchBean.getTask_status();	
							   int task_cid = searchBean.getTask_cid()== null ? 0 : searchBean.getTask_cid();	
							%>

								<li>任务标题：<input type="text" class="i_text" name="title1" value="<%= JsonUtil.obj2Str(searchBean.getTitle()) %>"/></li>
								<li>发布人：<input type="text" class="i_text" name="send_user1" value="<%= JsonUtil.obj2Str(searchBean.getSend_user()) %>"/></li>
								<li>任务联系号码：<input type="text" class="i_text" name="phone1" value="<%= JsonUtil.obj2Str(searchBean.getPhone()) %>"/></li>
								
								<li>任务状态：<select name="task_status1"  id="" class="i_text">
									<option value="0"  >全部</option>
									<option value="1" <% if(task_status==1) out.print("selected");%> >任务发布中</option>
									<option value="2" <% if(task_status==2) out.print("selected");%> >任务已接收</option>
									<option value="3" <% if(task_status==3) out.print("selected");%> >任务完成</option>
								</select>
								</li>
								<li>任务所属小区：<select name="task_cid1"  id="" class="i_text">
									<%if(session.getAttribute("user_isAdmin")!= null){ %><option value="0"  >全部</option><%} %>
									<%
									   List<Community> clist = (List<Community>)session.getAttribute("user_community");
									   for(Community c : clist){
									%>
									<option value="<%= c.getCommunity_id()%>" <% if(c.getCommunity_id() == task_cid) {out.print("selected");  }%> ><%= c.getCommunity_name() %></option>
								
									<% } %>
								</select>
								</li>
								<li>任务发布时间从：<input readonly type="text" class="i_text" name="start_time1" value="<%= JsonUtil.obj2Str(searchBean.getStart_time()) %>"/></li>
								<li>到：<input readonly type="text" class="i_text" name="end_time1" value="<%= JsonUtil.obj2Str(searchBean.getEnd_time()) %>" /></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>								
							</ul>
						</div>
						<form action="${pageContext.request.contextPath }/task.do?method=searchPropertyTask" method="post" id="searchForm">
							<input type="hidden" name="title" value="<%= JsonUtil.obj2Str(searchBean.getTitle()) %>"/>
							<input type="hidden" name="send_user" value="<%= JsonUtil.obj2Str(searchBean.getSend_user()) %>"/>
							<input type="hidden" name="phone" value="<%= JsonUtil.obj2Str(searchBean.getPhone()) %>"/>
							<input type="hidden" name="task_status" value="<%= task_status %>"/>
							<input type="hidden" name="start_time" value="<%= JsonUtil.obj2Str(searchBean.getStart_time()) %>"/>
							<input type="hidden" name="end_time" value="<%= JsonUtil.obj2Str(searchBean.getEnd_time()) %>"/>
							<input type="hidden" name="task_cid" value="<%= JsonUtil.obj2Str(searchBean.getEnd_time()) %>"/>
							<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
						</form>
						<script type="text/javascript">
							function submitForm()
							{
								$("input[name='title']").val($("input[name='title1']").val());
								$("input[name='send_user']").val($("input[name='send_user1']").val());
								$("input[name='phone']").val($("input[name='phone1']").val());
								$("input[name='task_status']").val($("select[name='task_status1']").val());
								$("input[name='task_cid']").val($("select[name='task_cid1']").val());
								$("input[name='start_time']").val($("input[name='start_time1']").val());
								$("input[name='end_time']").val($("input[name='end_time1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
						</script>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col width="80px"/><col width="120px"/><col width="80px"/><col width="120px"/><col width="160px"/><col width="80px"/></colgroup>
							<tr>
								<th>任务标题 </th>
								<th>发布人</th>						
								<th>任务状态</th>
								<th>手机号</th>
								<th>小区</th>
								<th>发布时间</th>
								<th class="icf">&#xf013e;</th>
							</tr>
							<% List<TaskProperty> list = (List<TaskProperty>)request.getAttribute("list");
							   if(list!=null){
							   for(TaskProperty task: list) {
							   String ptime = CommonUtil.formatTimestamp(task.getPublish_time(),0);
							   String status = "任务发布中";
							   if(task.getTask_status() == 1)
							   {
								   status = "任务发布中";
							   }else if(task.getTask_status() == 2)
							   {
								   status = "任务已接收";
							   }else if(task.getTask_status() == 3)
							   {
								   status = "任务已完成";
							   }
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(task.getTask_title()) %></td>							
								<td><%= JsonUtil.obj2Str(task.getSend_user_name()) %> </td>
								<td><%= status%> </td>
								<td><%= JsonUtil.obj2Str(task.getPhone()) %></td>
								<td><%= JsonUtil.obj2Str(task.getCommunity()) %></td>
								<td><%= ptime %></td>
								<td><a target="_blank" href="${pageContext.request.contextPath }/task.do?method=editPropertyTask&type=1&viewId=<%= task.getTask_id() %>" class="btn btn_r">操作</a></td>
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

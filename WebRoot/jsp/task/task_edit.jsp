<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.task.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body <% if(request.getAttribute("isEdit")!=null) out.print("onload=\"alert('修改成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<%
Task task = (Task)request.getAttribute("task");
if(task == null)
	task = new Task();
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
String ptime = CommonUtil.formatTimestamp(task.getPublish_time(),0);
String etime = CommonUtil.formatTimestamp(task.getExpired_time(),0);
String ctime = CommonUtil.formatTimestamp(task.getComplete_time(),0);
String recv_type = "";
if(task.getTarget_user_type().contains("1"))
{
	recv_type = "小区用户";
}
if(task.getTarget_user_type().contains("2"))
{
	if(recv_type.length()>0)
		recv_type += "和";
		   
	recv_type += "服务专员";
}
%>
<!-- content -->
<input type="hidden" id="curr_li" value="task_0"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">任务详情</div>
					<form action="${pageContext.request.contextPath }/task.do?method=edit" method="post" id="form">
					<div class="p20 m20">
					<input type="hidden" value="<%= task.getTask_id() %>" name="editId"/>
					<input type="hidden" value="0" name="opType" id="opType"/>
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>任务标题</th>
								<td><span ><%= JsonUtil.obj2Str(task.getTask_title()) %></span></td>
							</tr>
							<tr>
								<th>任务描述</th>
								<td><span ><%= JsonUtil.obj2Str(task.getTask_dec()) %></span></td>
							</tr>
							<tr>
								<th>任务图片</th>
								<td>						
								<span>								
								<%  List<String> pList = task.getPhoto();
									if(pList!=null && pList.size()>0){
								    for(String url : pList)
								    {
								 %>
                                 <img src="<%= url %>"/><br/>
								<%
								    }}else
								    {
								    	out.print("&nbsp;");
								    }
								%>
								</span>								
								</td>
							</tr>							
							<tr>
								<th>任务音频</th>
								<td><span >
								<%  List<String> aList = task.getAudio();
									if(aList!=null && aList.size()>0){
								    for(String url : aList)
								    {
								 %>
                                 <a href="<%= url %>"><%= url %></a><br/>
								<%
								    }}else
								    {
								    	out.print("&nbsp;");
								    }
								%>
								</span></td>
							</tr>
							<tr>
								<th>任务发布人</th>
								<td><span ><%= JsonUtil.obj2Str(task.getSend_user_name()) %></span></td>
							</tr>
							<tr>
								<th>发布人手机</th>
								<td><span ><%= JsonUtil.obj2Str(task.getPhone()) %></span></td>
							</tr>
							<tr>
								<th>完成任务地址</th>
								<td><span ><%= JsonUtil.obj2Str(task.getReceive_addr()) %></span></td>
							</tr>
							<tr>
								<th>任务接收类型</th>
								<td><span ><%= recv_type %></span></td>
							</tr>
							<tr>
								<th>任务状态</th>
								<td><span ><%= status %></span></td>
							</tr>
							
							<tr>
								<th>任务接收人</th>
								<td><span ><%= JsonUtil.obj2Str(task.getReceive_user_name()) %></span></td>
							</tr>
							
							<tr>
								<th>任务发布时间</th>
								<td><span ><%= ptime %></span></td>
							</tr>
							<tr>
								<th>任务过期时间</th>
								<td><span ><%= etime %></span></td>
							</tr>
							<tr>
								<th>任务完成时间</th>
								<td><span ><%= ctime %></span></td>
							</tr>	
						</table>
					</div>
					
					<div class="mrw_btn_wrap">
					<%
						if(task.getTarget_user_type().contains("2") && task.getTask_status() == 1){
					%>
						<a href="javascript:$('#opType').val('1');$('#form').submit();" class="btn btn_big btn_r">接受任务</a>
					<%
						}
					%>
						<a href="javascript:$('#opType').val('2');$('#form').submit();" class="btn btn_big btn_r">待审核</a>
						<a href="javascript:$('#opType').val('3');$('#form').submit();" class="btn btn_big btn_r">设置为过期</a>
					</div>
					</form>
				</div>
			</div>
<!-- content end -->

</div>
</body>
</html>

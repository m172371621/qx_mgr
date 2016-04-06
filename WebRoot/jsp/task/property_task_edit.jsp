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
TaskProperty task = (TaskProperty)request.getAttribute("task");
if(task == null)
	task = new TaskProperty();
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
String ptime = CommonUtil.formatTimestamp(task.getPublish_time(),0);
String ctime = CommonUtil.formatTimestamp(task.getComplete_time(),0);

%>
<!-- content -->
<input type="hidden" id="curr_li" value="task_1"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">任务详情</div>
					<form action="${pageContext.request.contextPath }/task.do?method=editPropertyTask" method="post" id="form">
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
								<th>任务地址</th>
								<td><span ><%= JsonUtil.obj2Str(task.getAddress()) %></span></td>
							</tr>
							<tr>
								<th>任务状态</th>
								<td><span ><%= status %></span></td>
							</tr>
							
							<tr>
								<th>任务发布时间</th>
								<td><span ><%= ptime %></span></td>
							</tr>
							<tr>
								<th>任务完成时间</th>
								<td><span ><%= ctime %></span></td>
							</tr>	
						</table>
					</div>
					
					<div class="mrw_btn_wrap">
						<a href="javascript:$('#opType').val('1');$('#form').submit();" class="btn btn_big btn_r">接收任务</a>
						<a href="javascript:$('#opType').val('2');$('#form').submit();" class="btn btn_big btn_r">完成任务</a>
					</div>
					</form>
				</div>
			</div>
<!-- content end -->

</div>
</body>
</html>

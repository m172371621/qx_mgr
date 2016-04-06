<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.user.mgrpo.*" %>


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
<input type="hidden" id="curr_li" value="user_0"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">区享用户查询</div>
					<div class="sform_wrap c">
							<ul>
							<% 
							   UserSearchBean searchBean = request.getAttribute("searchBean") == null? new UserSearchBean(): (UserSearchBean)request.getAttribute("searchBean");
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   int cid = searchBean.getCid() == null ? 	0:  searchBean.getCid();
							%>

								<li>手机号：<input type="text" class="i_text" name="phone1" value="<%= JsonUtil.obj2Str(searchBean.getPhone()) %>"/></li>
								<li>用户名：<input type="text" class="i_text" name="loginname1" value="<%= JsonUtil.obj2Str(searchBean.getLoginname()) %>"/></li>
								<li>所属小区：<select name="cid1"  id="" class="i_text">
									<option value="0"  >全部</option>
									<%
									   List<Community> clist = (List<Community>)session.getAttribute("user_community");
									   for(Community c : clist){
									%>
									<option value="<%= c.getCommunity_id()%>" <% if(c.getCommunity_id() == cid) {out.print("selected");  }%> ><%= c.getCommunity_name() %></option>
								
									<% } %>
								</select>
								</li>
								
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
						</div>
						<form action="${pageContext.request.contextPath }/qxuser.do?method=list" method="post" id="searchForm">
							<input type="hidden" name="phone" value="<%= JsonUtil.obj2Str(searchBean.getPhone()) %>"/>
							<input type="hidden" name="cid" value="<%= JsonUtil.obj2Str(searchBean.getCid()) %>"/>
							<input type="hidden" name="loginname" value="<%= JsonUtil.obj2Str(searchBean.getLoginname()) %>"/>
							<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
						</form>
						<script type="text/javascript">
							function submitForm()
							{
								$("input[name='phone']").val($("input[name='phone1']").val());
								$("input[name='loginname']").val($("input[name='loginname1']").val());
								$("input[name='cid']").val($("select[name='cid1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
						</script>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="60px"/><col width="120px"/><col width="120px"/><col width="150px"/><col width="150px"/><col width="180px"/><col width="80px"/></colgroup>
							<tr>
								<th>用户ID </th>
								<th>用户名</th>
								<%--<th>密码 </th>--%>
								<th>手机号</th>
								<th>住址</th>
								<th>所属小区</th>
								<th>创建时间</th>
								<th class="icf">&#xf013e;</th>
							</tr>
							<% List<MgrUser> list = (List<MgrUser>)request.getAttribute("list");
							   if(list!=null){
							   for(MgrUser user: list) {
							   String time = CommonUtil.formatDate(user.getCreateTime(), "yyyy-MM-dd HH:mm");
							  
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(user.getUser_id()) %></td>							
								<td><%= JsonUtil.obj2Str(user.getLoginname()) %> </td>
								<%--<td><%= JsonUtil.obj2Str(user.getPassword()) %></td>--%>
								<td><%= JsonUtil.obj2Str(user.getPhone()) %> </td>
								<td><%= JsonUtil.obj2Str(user.getAddr()).replace("null","-").replace("NULL","-") %></td>
								<td><%= JsonUtil.obj2Str(user.getCname()) %></td>
								<td><%= time %></td>
								<td><a target="_blank" href="${pageContext.request.contextPath }/qxuser.do?method=updateUser&userId=<%= user.getUser_id() %>" class="btn btn_r">修改</a></td>
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

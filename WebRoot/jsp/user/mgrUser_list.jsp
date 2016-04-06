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
					<div class="mrw_title">系统用户查询</div>
					<div class="sform_wrap c">
							<ul>
							<% 
							   UserSearchBean searchBean = request.getAttribute("searchBean") == null? new UserSearchBean(): (UserSearchBean)request.getAttribute("searchBean");
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   				   
							%>

								<li>用户ID：<input type="text" class="i_text" name="user_id1" value="<%= JsonUtil.obj2Str(searchBean.getUser_id()) %>"/></li>
								<li>用户名：<input type="text" class="i_text" name="loginname1" value="<%= JsonUtil.obj2Str(searchBean.getLoginname()) %>"/></li>
		
								
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
						</div>
						<form action="${pageContext.request.contextPath }/user.do?method=list" method="post" id="searchForm">
							<input type="hidden" name="user_id" value="<%= JsonUtil.obj2Str(searchBean.getUser_id()) %>"/>
							<input type="hidden" name="loginname" value="<%= JsonUtil.obj2Str(searchBean.getLoginname()) %>"/>
							<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
						</form>
						<script type="text/javascript">
							function submitForm()
							{
								$("input[name='user_id']").val($("input[name='user_id1']").val());
								$("input[name='loginname']").val($("input[name='loginname1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
						</script>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="60px"/><col width="60px"/><col width="80px"/><col width="100px"/><col width="100px"/><col width="100px"/><col width="80px"/></colgroup>
							<tr>
								<th>用户ID </th>
								<th>用户名</th>
								<%--<th>密码 </th>--%>
								<th>姓名 </th>
								<th>手机号</th>
								<th>e-mail</th>
								<th>创建时间</th>
								<th class="icf">&#xf013e;</th>
							</tr>
							<% List<MgrUser> list = (List<MgrUser>)request.getAttribute("list");
							   if(list!=null){
							   for(MgrUser user: list) {
							   String time = CommonUtil.formatDate(user.getCreateTime(), "yyyy-MM-dd HH:mm");
							  
							%>
							<tr>
								<td id="user_id"><%= JsonUtil.obj2Str(user.getUser_id()) %></td>							
								<td><%= JsonUtil.obj2Str(user.getLoginname()) %> </td>
								<%--<td><%= JsonUtil.obj2Str(user.getPassword()) %></td>--%>
								<td><%= JsonUtil.obj2Str(user.getPersonName()) %></td>
								<td><%= JsonUtil.obj2Str(user.getPhone()) %> </td>
								<td><%= JsonUtil.obj2Str(user.getEmail()) %></td>
								<td><%= time %></td>
								<td>
									<a href="${pageContext.request.contextPath }/user.do?method=edit&type=1&user_id=<%= user.getUser_id() %>" class="btn btn_r">修改</a>
									<%if(user.getActivation()== 1) {%>
										<a href="javascript:accountDisable(<%= user.getUser_id() %>)" class="btn btn_r">冻结</a>
									<%}else{ %>
									    <a href="javascript:accountEnable(<%= user.getUser_id() %>)" class="btn btn_r" style="background:green">解冻</a>
									<%} %>
								</td>
							</tr>
							<% }} %>
							<tr>								
								<td colspan='7'><a href="${pageContext.request.contextPath }/user.do?method=add&type=1" class="btn btn_r">新增用户</a></td>
							</tr>
						</table>
					</div>
					<script type="text/javascript">
					function accountDisable(user_id){
						if(confirm('确定冻结!')){
							$.ajax({
								type:'post',
								url : "${pageContext.request.contextPath }/user.do?method=accountDisable",
								dataType: 'json',
								data:{user_id:user_id},
								success:function(data){
									if(data.result_code ==0){
										alert(data.result_dec);
										location.href='${pageContext.request.contextPath}/user.do?method=list';
										}
								}
							})
						}
					}

					function accountEnable(user_id){
						if(confirm('确定冻结!')){
							$.ajax({
								type:'post',
								url : "${pageContext.request.contextPath }/user.do?method=accountEnable",
								dataType: 'json',
								data:{user_id:user_id},
								success:function(data){
									if(data.result_code ==0){
										alert(data.result_dec);
										location.href='${pageContext.request.contextPath}/user.do?method=list';
										}
								}
							})
						}
					}
					</script>
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

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.user.mgrpo.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body <% if(request.getAttribute("isEdit")!=null) out.print("onload=\"alert('修改成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<%%>

<!-- content -->
<input type="hidden" id="curr_li" value="user_0"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">新增用户</div>
					<form action="${pageContext.request.contextPath }/user.do?method=add" method="post" id="form">
					<div class="p20 m20">
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>登陆名</th>
								<td><input type="text" width="180px"  class="i_text" name="loginname" value=""/></td>
							</tr>
							<tr>
								<th>密码</th>
								<td><input type="text" width="180px"  class="i_text" name="password" value=""/></td>
							</tr>
							<tr>
								<th>姓名</th>
								<td><input type="text" width="180px"  class="i_text" name="personName" value=""/></td>
							</tr>
							<tr>
								<th>手机号</th>
								<td><input type="text" width="180px"  class="i_text" name="phone" value=""/></td>
							</tr>
							<tr>
								<th>邮箱</th>
								<td><input type="text" width="180px"  class="i_text" name="email" value=""/></td>
							</tr>
							<tr>
								<th>所属小区</th>
								<td>
									<%
									   List<Community> clist = (List<Community>)request.getAttribute("clist");
									   for(Community c : clist){
									%>				
										<input type="radio" name="cids" value="<%=c.getCommunity_id() %>" /><%=c.getCommunity_name() %>
									<% } %>
									
								</td>
							</tr>
							<tr>
								<th>角色</th>
								<td>
									<%
									   List<Role> rlist = (List<Role>)request.getAttribute("rlist");
									   if(session.getAttribute("user_isAdmin") != null){
									   	  for(Role r : rlist){%>				
											<input type="radio" name="role_id" value="<%= r.getRole_id() %>" onclick='getServcie(this)' /><%= r.getRole_name() %>
									<%   }   
									   }else{
										   for(Role r : rlist){
										   		if( r.getRole_id()!=1){%>				
											<input type="radio" name="role_id" value="<%= r.getRole_id() %>"  onclick='getServcie(this)'/><%= r.getRole_name() %>
									<%   		}
											}
									   }
									%>							
								</td>
							</tr>
								
						</table>
					</div>
					
					<div class="mrw_btn_wrap">
					
						<a href="javascript:$('#form').submit();" class="btn btn_big btn_r">新增</a>
						
					</div>
					</form>
				</div>
			</div>
<!-- content end -->
<script type="text/javascript">
function getServcie(obj)
{
	if(obj.value=='4')
	{
		$("input[name='cids']").attr('disabled',false);
		$("input[name='sids']").attr('disabled',false);
	}
	else if(obj.value=='1')
	{
		$("input[name='cids']").attr('disabled',true);
		$("input[name='sids']").attr('disabled',true);
	}else
	{
		$("input[name='cids']").attr('disabled',false);
		$("input[name='sids']").attr('disabled',true);
	}
}
</script>
</div>
</body>
</html>

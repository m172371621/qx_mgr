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

<%MgrUser user =  (MgrUser)request.getAttribute("user");%>

<!-- content -->
<input type="hidden" id="curr_li" value="user_0"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">新增用户</div>
					<form action="${pageContext.request.contextPath }/user.do?method=edit" method="post" id="form">
					<input type="hidden" width="180px"  class="i_text" name="user_id" value="<%= user.getUser_id() %>"/>
					<div class="p20 m20">
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>登陆名</th>
								<td><input type="text" width="180px"  readonly class="i_text" name="loginname" value="<%= JsonUtil.obj2Str(user.getLoginname()) %>"/></td>
							</tr>
							<tr>
								<th>密码</th>
								<td><input type="text" width="180px"  class="i_text" name="password" value="<%= JsonUtil.obj2Str(user.getPassword()) %>"/></td>
							</tr>
							<tr>
								<th>姓名</th>
								<td><input type="text" width="180px"  class="i_text" name="personName" value="<%= JsonUtil.obj2Str(user.getPassword()) %>"/></td>
							</tr>
							<tr>
								<th>手机号</th>
								<td><input type="text" width="180px"  class="i_text" name="phone" value="<%= JsonUtil.obj2Str(user.getPhone()) %>"/></td>
							</tr>
							<tr>
								<th>邮箱</th>
								<td><input type="text" width="180px"  class="i_text" name="email" value="<%= JsonUtil.obj2Str(user.getEmail()) %>"/></td>
							</tr>
							
							<tr>
								<th>角色</th>
								<td>
									<%
									   List<Role> rlist = (List<Role>)request.getAttribute("rlist");
									   int user_rid = ((List<Role>)request.getAttribute("user_list_role")).get(0).getRole_id();
									   	  for(Role r : rlist){%>				
											<input type="radio" name="role_id" value="<%= r.getRole_id() %>" onclick='getServcie(this)' <% if(r.getRole_id() == user_rid) {out.print("checked");  }%>/><%= r.getRole_name() %>
									<%   
										  }   									   
									%>							
								</td>
							</tr>
							<tr>
								<th>所属小区</th>
								<td>
									<%
									   List<Community> clist = (List<Community>)request.getAttribute("clist");
									 int user_cid = 0;
									 if(request.getAttribute("user_community_list")!=null)
									 {
										 user_cid = ((List<Community>)request.getAttribute("user_community_list")).get(0).getCommunity_id();
									 }
									   for(Community c : clist){
										   
										   
									%>				
										<input type="radio" name="cids" value="<%=c.getCommunity_id() %>" <% if(c.getCommunity_id() == user_cid) {out.print("checked");  }%>/><%=c.getCommunity_name() %>
									<% } %>
									
								</td>
							</tr>
							<!-- tr >
								<th>所属服务</th>
								<td>
									
										<input type="checkbox" name="sids" disab
									
									
								</td>
							</tr>	 -->
						</table>
					</div>
					
					<div class="mrw_btn_wrap">
					
						<a href="javascript:$('#form').submit();" class="btn btn_big btn_r">确定</a>
						
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

if(<%= user_rid %>==1)
	$("input[name='cids']").attr('disabled',true);

if(<%= user_rid %>==4)
	$("input[name='sids']").attr('disabled',false);

</script>
</div>
</body>
</html>

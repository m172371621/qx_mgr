<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.product.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
 
<script type="text/javascript">
$(function() {
	$("input[name='rule_begin_time']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
	$("input[name='rule_end_time']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
});
</script>

</head>
<body <% if(request.getAttribute("is_new") == null) out.print("onload=\"alert('"+ request.getAttribute("result_dec") +"')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />


<!-- content -->
<input type="hidden" id="curr_li" value="product_1"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">新增用户</div>
					<form action="${pageContext.request.contextPath }/qxuser.do?method=createUser" method="post" id="form">
					<div class="p20 m20">
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>用户名</th>
								<td><input type="text" width="180px"  class="i_text_1" name="username" value=""/></td>
							</tr>
							<tr>
								<th>所属小区</th>
								<td>
								<select name="cid"  id="" class="i_text_1">
									<%
									   Community uc =(Community)request.getSession().getAttribute("selectCommunity");
									   List<Community> clist = (List<Community>)session.getAttribute("user_community");
									   for(Community c : clist){
									%>
									<option value="<%= c.getCommunity_id()%>"  <% if(c.getCommunity_id() == uc.getCommunity_id()+0 ) {out.print("selected");}%>><%= c.getCommunity_name() %></option>								
									<% } %>
								</select>
								</td>
							</tr>
									
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
</script>
</div>
</body>
</html>

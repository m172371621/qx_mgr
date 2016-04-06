<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.express.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />



</head>
<body <% if(request.getAttribute("SUCCESS") != null) out.print("onload=\"alert('修改成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />


<!-- content -->
<input type="hidden" id="curr_li" value="express_0"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">快递录入</div>
					<form action="${pageContext.request.contextPath }/express.do?method=edit" method="post" id="form">
					<div class="p20 m20">
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>快递公司</th>
								<td>		
								<%  
								Express express = (Express)request.getAttribute("express");		
								Map commap =  Dicti.get(Dicti.get("快递公司")); 
									int i = 0;
									for (Object key : commap.keySet()) {
								%>	
									<input type="radio" <%if((key+"").equals(express.getExpress_com()))out.print("checked"); %> id="radio<%= key %>" name="express_com" value="<%= key %>"/><label for="radio<%=key %>"><%= commap.get(key) %></label>									
								<% 
									i++;
									if(i%4 == 0)
										out.println("<br/>");
									
									} 
									
								%>								
								</td>
							</tr>
							<tr>
								<th>快递单号</th>
								<td><input type="text" width="180px"  class="i_text_1" name="express_no" value="<%= JsonUtil.obj2Str(express.getExpress_no())  %>"/></td>
							</tr>
							<tr>
								<th>手机号</th>
								<td><input type="text" width="180px"  class="i_text_1" name="user_phone" value="<%= JsonUtil.obj2Str(express.getUser_phone())  %>"/></td>
							</tr>
							<tr>
								<th>地址</th>
								<td><input type="text" width="180px"  class="i_text_1" name="express_info" value="<%= JsonUtil.obj2Str(express.getExpress_info())  %>"/>
								<input type="hidden" width="180px"  class="i_text_1" name="express_id" value="<%= express.getExpress_id()  %>"/></td>
							</tr>
							<tr>
								<th>签收</th>
								<td><select name="status"  id="" class="i_text">
												
									<option value="1" <% if(1==express.getStatus() ) {out.print("selected");  }%> >否</option>
									<option value="2" <% if(2==express.getStatus() ) {out.print("selected");  }%> >是</option>
									
								</select>
								</td>
							</tr>	
						</table>
					</div>
					
					<div class="mrw_btn_wrap">
						
						<a href="javascript:submit();" class="btn btn_big btn_r">确定</a>
						<a href="${pageContext.request.contextPath }/express.do?method=list" class="btn btn_big btn_r">返回列表</a>
					</div>
					</form>
				</div>
			</div>
<!-- content end -->
<script type="text/javascript">

function submit()
{

	if(!$('input[name=express_com]:checked').val())
	{
		alert("请选择快递公司");
		return;
	}
	
	$('#form').submit();
}

</script>
</div>
</body>
</html>

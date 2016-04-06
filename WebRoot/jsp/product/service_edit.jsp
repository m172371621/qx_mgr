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
 

</head>
<body <% if(request.getAttribute("isEdit")!=null) out.print("onload=\"alert('修改成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<%
ServiceVo svo =  (ServiceVo)request.getAttribute("service");
if(svo == null)
	svo = new ServiceVo();
 
int pid = svo.getParent_id() == null ? 0 : svo.getParent_id();
int sorder = svo.getService_order() == null ? 0 : svo.getService_order();
int status = svo.getStatus() == null ? 0 : svo.getStatus();
%>

<!-- content -->
<input type="hidden" id="curr_li" value="product_2"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">编辑商品类别</div>
					<form action="${pageContext.request.contextPath }/product.do?method=editService" method="post" id="form">
					<input type="hidden" width="180px"  class="i_text_1" name="sid" value="<%= JsonUtil.obj2Str(svo.getService_id()) %>"/>
					<div class="p20 m20">
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>名称</th>
								<td><input type="text" width="180px"  class="i_text_1" name="sname" value="<%= JsonUtil.obj2Str(svo.getService_name()) %>"/></td>
							</tr>
							<tr>
								<th>所属小区</th>
								<td><select name="cid"  id="" class="i_text">
									<% Community c =(Community)request.getSession().getAttribute("selectCommunity");%>
									<option value="<%= c.getCommunity_id()%>" ><%= c.getCommunity_name() %></option>								
								</select>
								</td>
							</tr>
							<tr>
								<th>父类别</th>
								<td><select name="pid"  id="" class="i_text">
									<option value="0"  >无</option>
									<%
									List<ServiceVo> slist = (List<ServiceVo>)request.getSession().getAttribute("product_service");
									   for(ServiceVo s : slist){
									%>
									<option value="<%= s.getService_id()%>" <% if(s.getService_id() == pid) {out.print("selected");  }%> ><%= s.getService_name() %></option>
								
									<% } %>
								</select>
								</td>
							</tr>
							<tr>
								<th>状态</th>
								<td><select name="status"  id="status" class="i_text">
									<option value="1" <% if(status == 1) out.print("selected"); %>>上架</option>
									<option value="2" <% if(status == 2) out.print("selected"); %>>下架</option>
								</select>
								</td>
							</tr>
							<tr>
								<th>排序等级</th>
								<td><input type="text" width="180px"  class="i_text_1" name="sorder" value="<%= sorder %>"/></td>
							</tr>
							<tr>
								<th>描述</th>
								<td><input type="text" width="180px"  class="i_text_1" name="sdec" value="<%= JsonUtil.obj2Str(svo.getService_dec()) %>"/></td>
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

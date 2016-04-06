<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.product.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>


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
<input type="hidden" id="curr_li" value="product_2"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">商品类别管理</div>
					<div class="sform_wrap c">
							<ul>


								
							</ul>
						</div>
						
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col width="120px"/><col width="160px"/><col width="60px"/><col width="60px"/><col width="180px"/><col width="180px"/></colgroup>
							<tr>
								<th>名称 </th>					
								<th>所属小区</th>
								<th>父类别</th>
								<th>状态</th>
								<th>排序等级</th>
								<th>创建时间</th>
								<th class="icf">&#xf013e;</th>
							</tr>
							<% List<ServiceVo> list = (List<ServiceVo>)request.getSession().getAttribute("product_service");
								Community c = (Community)request.getSession().getAttribute("selectCommunity");
								String curr_cname = c.getCommunity_name();
								
							   if(list!=null){
							   for(ServiceVo service: list) {
								  
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(service.getService_name()) %></td>							
								<td><%= curr_cname %> </td>
								<td><% if(service.getParent_id()==0){out.print("无");}else{out.print("小区商城");} %></td>
								<td><% if(service.getStatus()==1){out.print("上架");}else{out.print("下架");}%></td>
								<td><%= service.getService_order() %></td>
								<td><%= CommonUtil.formatTimestamp(service.getCreateTime(),1) %></td>
								<td><a target="_blank" href="${pageContext.request.contextPath }/product.do?method=editService&type=1&viewId=<%= service.getService_id() %>" class="btn btn_r">修改</a>
									&nbsp;&nbsp;<a href="javascript:del('${pageContext.request.contextPath }/product.do?method=delService&sid=<%= service.getService_id() %>')" class="btn btn_r">删除</a>
								</td>
							</tr>
							<% }} %>
							<tr>								
								<td colspan='6'><a href="${pageContext.request.contextPath }/product.do?method=editService" class="btn btn_r">新增</a></td>
							</tr>
						</table>
					</div>

					 
	</div>
</div>
<!-- content end -->
<script type="text/javascript">
function del(url)
{

	if(confirm('确定删除吗？'))
	{
		location.href=url;
	}
}
</script>
</div>
</body>
</html>

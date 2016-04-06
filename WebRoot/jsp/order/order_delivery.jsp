<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body <% if(request.getAttribute("pickUp")!=null) out.print("onload=\"alert('设置成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="order_1"/>
<div id="mright">
	<div id="mr_cnt">
		
					<div class="mrw_title">商品到货</div>
					<div class="sform_wrap c">
				
							<ul>
							<% 
							   Map searchBean = (Map)request.getAttribute("searchBean");
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   
							   String service_id = searchBean.get("service_id") == null ? "0" : (String)searchBean.get("service_id");
							   String product_name = searchBean == null ? "" : (String)searchBean.get("product_name");
							   int cid = searchBean.get("cid") == null ? 0:(Integer)searchBean.get("cid");
							   
							%>
								<li>所属服务：<select name="sid1"  id="" class="i_text">
									<%if(session.getAttribute("user_isAdmin")!= null){ %><option value="0"  >全部</option><%} %>
									<%
									List<ServiceVo> slist = (List<ServiceVo>)request.getSession().getAttribute("product_service");
									   for(ServiceVo s : slist){
									%>
									<option value="<%= s.getService_id()%>" <% if(s.getService_id() ==Integer.valueOf( service_id)) {out.print("selected");  }%> ><%= s.getService_name() %></option>
								
									<% } %>
								</select>
								</li>
								<li>商品名：<input type="text" class="i_text" name="product_name1" value="<%= JsonUtil.obj2Str(product_name) %>"/></li>
								<li>商品所属小区：<select name="cid1"  id="" class="i_text">
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
					<script type="text/javascript">
							function submitForm()
							{
								$("input[name='product_name']").val($("input[name='product_name1']").val());
								$("input[name='service_id']").val($("select[name='service_id1']").val());
								$("input[name='cid']").val($("select[name='cid1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/order.do?method=orderDelivery" method="post" id="searchForm">
					
					<input type="hidden" name="product_name" value="<%= JsonUtil.obj2Str(product_name) %>"/>
					<input type="hidden" name="service_id" value="<%= JsonUtil.obj2Str(service_id) %>"/>
					<input type="hidden" name="cid" value="<%= JsonUtil.obj2Str(cid) %>"/>
					<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
								<th>服务 </th>
								<th>商品名</th>						
								<th>订单数量</th>					
								<th >到货</th>
							</tr>
							<% List<Map> list = (List<Map>)request.getAttribute("list");
							   if(list!=null){
							   for(Map map: list) {
							   
							%>
							<tr>
								<td><%= map.get("service_name") %></td>							
								<td><%=  map.get("product_name") %> </td>
								<td><%=  map.get("order_count") %></td>
								<td><input type="checkbox" name="ids" value="<%=  map.get("product_id") %>"/></td>
							</tr>
							<% }} %>
							<tr>
								<td colspan="3">&nbsp;</td>
								<td><input type="hidden" name="ispickup" id="ispickup"/><a href="javascript:$('#ispickup').val('1');$('#searchForm').submit();" class="btn btn_r">设置到货</a></td>
							</tr>
						</table>
					</div>
					</form>
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

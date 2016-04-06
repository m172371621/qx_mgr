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
<input type="hidden" id="curr_li" value="product_1"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">营销规则管理</div>
					<div class="sform_wrap c">
							<ul>
							<% 
							   ProductSearchBean searchBean = request.getAttribute("searchBean") == null? new ProductSearchBean(): (ProductSearchBean)request.getAttribute("searchBean");
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   
							   int cid = searchBean.getCid()== null ? 0 : searchBean.getCid();	
							%>

								<li>名称：<input type="text" class="i_text" name="rule_name1" value="<%= JsonUtil.obj2Str(searchBean.getName()) %>"/></li>
								
							
								<li>所属小区：<select name="cid1"  id="" class="i_text">
									<%if(session.getAttribute("user_isAdmin")!= null){ %><option value="0"  >全部</option><%} %>
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
						<form action="${pageContext.request.contextPath }/product.do?method=listRule" method="post" id="searchForm">
							<input type="hidden" name="rule_name" value="<%= JsonUtil.obj2Str(searchBean.getName()) %>"/>
							<input type="hidden" name="cid" value="<%= cid %>"/>
							<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
						</form>
						<script type="text/javascript">
							function submitForm()
							{
								$("input[name='rule_name']").val($("input[name='rule_name1']").val());
								$("input[name='cid']").val($("select[name='cid1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
						</script>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col width="160px"/><col width="80px"/><col width="180px"/><col width="180px"/><col width="80px"/></colgroup>
							<tr>
								<th>规则名称 </th>					
								<th>所属小区</th>
								<th>规则类型</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th class="icf">&#xf013e;</th>
							</tr>
							<% List<ProductRule> list = (List<ProductRule>)request.getAttribute("list");
							   if(list!=null){
							   for(ProductRule rule: list) {
								  
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(rule.getRule_name()) %></td>							
								<td><%= JsonUtil.obj2Str(rule.getCommunityName()) %> </td>
								<td><%= "限量商品" %> </td>
								<td><%= CommonUtil.formatTimestamp(rule.getRule_begin_time(),1) %></td>
								<td><%= CommonUtil.formatTimestamp(rule.getRule_end_time(),1) %></td>
								<td><a target="_blank" href="${pageContext.request.contextPath }/product.do?method=editRule&type=1&viewId=<%= rule.getRule_id() %>" class="btn btn_r">操作</a></td>
							</tr>
							<% }} %>
							<tr>								
								<td colspan='6'><a href="${pageContext.request.contextPath }/product.do?method=addRule" class="btn btn_r">新增规则</a></td>
							</tr>
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

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.incomming.po.IncommingDetailBean" %>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.*" %>


<%
String goods_path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+goods_path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<script type="text/javascript" src="<%=basePath%>js/autocomplete/lib/jquery.js"></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/lib/jquery.bgiframe.min.js'></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/lib/jquery.ajaxQueue.js'></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/lib/thickbox-compressed.js'></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/jquery.autocomplete.js'></script>
<script type='text/javascript' src='<%=basePath%>js/autocomplete/localdata.js'></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/autocomplete/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/autocomplete/lib/thickbox.css" />

</head>
<body <% if(request.getAttribute("SUCCESS")!=null) out.print("onload=\"alert('设置成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

 

<!-- content -->
<input type="hidden" id="curr_li" value="qxcard_0"/>
<div id="mright">
	<div id="mr_cnt">
		
					<%
		  Integer stock_type=(Integer)request.getSession().getAttribute("stock_type");
		if(stock_type==1){ 
		%>
					<div class="mrw_title">查看入库单明细</div>
		<%}else if(stock_type==2){ %>
		            <div class="mrw_title">查看调入单明细</div>
		<%} %>
					<div class="sform_wrap c">
						</div>
						<%
						IncommingDetailBean parammap = request.getAttribute("parammap") == null? new IncommingDetailBean(): (IncommingDetailBean)request.getAttribute("parammap");
						int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
						 %>
					<script type="text/javascript">
					
							function submitForm()
							{  
							    $('#pageIndex').val(1);
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/incommmingOrder.do?method=list_incomming_detail" method="post" id="searchForm">
					<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
					<input type="hidden" name="stockchangeHeaderId" value="<%= JsonUtil.obj2Str(parammap.getStockchange_header_id()) %>"/>
					<div id="order_wrap" class="p20">
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
								<th>商品名称</th>
								<th>条码</th>
								<th>参考售价</th>
								<th>数量</th>
								<th>进货单价</th>
							</tr>
							<% List<IncommingDetailBean> list = (List<IncommingDetailBean>)request.getAttribute("list");
							   if(list!=null){
							   for(IncommingDetailBean detailbean: list) {
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(detailbean.getProduct_name()) %></td>							
								<td><%= JsonUtil.obj2Str(detailbean.getBarcode()) %></td>	
								<td><%= JsonUtil.obj2Str(detailbean.getPrice()) %></td>	
								<td><%= JsonUtil.obj2Str(detailbean.getChange_count()) %></td>
								<td><%= JsonUtil.obj2Str(detailbean.getUnit_price()) %></td>		
							</tr>
							<% }} %>
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
<script type="text/javascript">
												
</script>	

</div>
</body>
</html>

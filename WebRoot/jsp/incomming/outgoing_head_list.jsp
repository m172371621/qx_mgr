<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="com.brilliantreform.sc.incomming.po.IncommingHeaderBean" %>
<%@ page language="java" import="com.brilliantreform.sc.system.dicti.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body <% if(request.getAttribute("SUCCESS")!=null) out.print("onload=\"alert('设置成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<jsp:include page="../common/datetimepicker.jsp" />
 
<script type="text/javascript">
$(function() {
	$("input[name='time_from1']").datepicker({
        dateFormat: "yy-mm-dd"
    });
    $("input[name='time_to1']").datepicker({
        dateFormat: "yy-mm-dd"
    });
});
</script>

<!-- content -->
<input type="hidden" id="curr_li" value="qxcard_0"/>
<div id="mright">
	<div id="mr_cnt">
		<%
		  Integer stock_type=Integer.parseInt(request.getParameter("stock_type"));
		if(stock_type==3){ 
		%>
					<div class="mrw_title">退货单列表</div>
		<%}else if(stock_type==4){ %>
		            <div class="mrw_title">损耗单列表</div>
		
		<%}else if(stock_type==5){ %>
		            <div class="mrw_title">调出单列表</div>
		<%} %>
					<div class="sform_wrap c">
				
							<ul>
								<li>生成日期：<input readonly type="text" class="i_text" style="width:180px" name="time_from1" id="time_from1" value="<%=request.getAttribute("time_from") %>"/></li>
								<li>到：<input readonly type="text" class="i_text" style="width:180px" name="time_to1" id="time_to1" value="<%=request.getAttribute("time_to") %>"/></li>
								<!-- 
								<li>状态：<select name="state" size="1" id="state">
								    <option value="1">未确认</option>
								    <option value="2">已确认</option>
                                </select></li>
                                 -->
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
								<%if(stock_type==3){%>
					<li><a href="${pageContext.request.contextPath }/outgoingOrder.do?method=add_detail_url&stock_type=<%=request.getParameter("stock_type") %>" class="btn btn_r">发起退货单</a></li>
		<%}else if(stock_type==4){ %>
		            <li><a href="${pageContext.request.contextPath }/outgoingOrder.do?method=add_detail_url&stock_type=<%=request.getParameter("stock_type") %>" class="btn btn_r">发起损耗单</a></li>
		
		<%}else if(stock_type==5){ %>
		           <li><a href="${pageContext.request.contextPath }/outgoingOrder.do?method=add_detail_url&stock_type=<%=request.getParameter("stock_type") %>" class="btn btn_r">发起调出单</a></li>
		<%} %>
							</ul>
							
						
						</div>
						<%
						int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
						 %>
					<script type="text/javascript">
					
							function submitForm()
							{  
							    $("#time_from").val($("#time_from1").val());
							    $("#time_to").val($("#time_to1").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
					</script>	
					<form action="${pageContext.request.contextPath }/outgoingOrder.do?method=list_incomming_head&stock_type=<%=request.getParameter("stock_type") %>" method="post" id="searchForm">
					<div id="order_wrap" class="p20">
					   <input type="hidden" class="i_text" style="width:180px" name="time_from" id="time_from" value="<%=request.getAttribute("time_from") %>"/>
					    <input type="hidden" class="i_text" style="width:180px" name="time_to" id="time_to" value="<%=request.getAttribute("time_to") %>"/>
					    <input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
						<table class="table_t_t">
						<colgroup><col width="160px"/><col/><col/><col/></colgroup>
							<tr>
								<th>单据编号</th>
								<th>发起人</th>
								<th>发起时间</th>
								<th>小区</th>
								<!-- 
								<th>状态</th>
								 -->
								<th></th>
							</tr>
							<% List<IncommingHeaderBean> list = (List<IncommingHeaderBean>)request.getAttribute("list");
							   if(list!=null){
							   for(IncommingHeaderBean headerbean: list) {
							%>
							<tr>
								<td><%= JsonUtil.obj2Str(headerbean.getStockchange_header_no()) %></td>							
								<td><%= JsonUtil.obj2Str(headerbean.getCreate_by()) %></td>	
								<td><%= JsonUtil.obj2Str(headerbean.getCreate_time()) %></td>	
								<td><%= JsonUtil.obj2Str(headerbean.getCommunity_name()) %></td>
								<!--  	
								<td><%= headerbean.getState()==1?"未确认":"已确认" %></td>	
								-->
								<td>									
									<a href="${pageContext.request.contextPath }/outgoingOrder.do?method=list_incomming_detail&stockchangeHeaderId=<%= headerbean.getStockchange_header_id() %>" class="btn btn_r">详情</a>									
								</td>
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
        $(function(){
					
			  var mydate = new Date();
              var str = "" + mydate.getFullYear() + "-";
              str += (mydate.getMonth()+1>9?(mydate.getMonth()+1).toString():'0' + (mydate.getMonth()+1))+"-";
              str += mydate.getDate();
		});
		function view(id)
		{  
			
			location.href="${pageContext.request.contextPath }/outgoingOrder.do?method=list_incomming_detail&stockchangeHeaderId="+id;
			
		}	
												
</script>	

</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="order_0"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">退货管理</div>
					<div class="sform_wrap c">
							<ul>
							<% 
							   OrderSearch searchBean = request.getAttribute("searchBean") == null? new OrderSearch(): (OrderSearch)request.getAttribute("searchBean");
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   
							   int order_status = searchBean.getOrder_status() == null ? 0 : searchBean.getOrder_status();	
							   int pay_type = searchBean.getPay_type() == null ? 0 : searchBean.getPay_type();	
							   int cid = searchBean.getCid() == null ? 0 : searchBean.getCid();	
							   int sid = searchBean.getSid() == null ? 0 : searchBean.getSid();	 
							%>	
								<% 
								List<Order> orderList = (List<Order>)request.getAttribute("orderList") == null ? new ArrayList<Order>():(List<Order>)request.getAttribute("orderList");
								%>
								<li>订单号:<input type="text" class="i_text" name="order_serial1" id="order_serial1" value="<%= JsonUtil.obj2Str(request.getAttribute("order_serial")==null?null:request.getAttribute("order_serial")) %>"/></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
								<li><a href="javascript:submitFormExit()" class="btn btn_big btn_y" id="refund">退货</a></li>
							</ul>
						</div>
						
					
							<form action="${pageContext.request.contextPath }/order.do?method=orderReturn" method="post" id="searchForm">
							<input type="hidden" name="order_serial" value="<%= JsonUtil.obj2Str(request.getAttribute("order_serial")) %>"/>
							<input  type="hidden" name="cid" value="<%=((Community) session.getAttribute("selectCommunity")).getCommunity_id()  %>"/> 
							</form> 
						<script type="text/javascript">
						function submitForm()
						{
							$("input[name='order_serial']").val($("input[name='order_serial1']").val());
							$('#searchForm').submit();
						}	
						function submitFormExit(){
							 if(<%=orderList.size()==0 %>){
									return 
								}
							if(confirm("确认取消该订单？"))  
							{  
								    var order_serial1 = $("#order_serial1").val();
								   $.ajax({
								     type: 'POST',
								     url: "${pageContext.request.contextPath }/order.do?method=orderReturnTrue&order_serial="+order_serial1,
								    success: function(data){
								       debugger;
								       var dataObj = eval("("+data+")");
								       var str = dataObj.result_code;
								       if(str == "0"){
								       	alert("订单取消成功！");
								       }else{
								    	alert("订单已取消过！");
								       }
								   }
								});
							}else  { 
								 return;
							}
						}
						</script>
						
						
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col width="180px"/><col width="130px"/><col width="160px"/><col width="160px"/></colgroup>
							<tr>
								<th>订单号 </th>
								<th>手机号</th>
								<th>商品名</th>
								<th>商品数量</th>
								<th>金额 </th>
								<%--
								<th class="icf">&#xf013e;</th>
								--%>
							</tr>
							
							<%--
								<%
									   if(od !=null){
								%>
								<tr>
									<td><%= od.getOrder_serial()==null?"":od.getOrder_serial() %> </td>
									<td><%= od.getOrder_phone()==null?"":od.getOrder_phone() %></td>
									<td><%= od.getProduct_name()==null?"":od.getProduct_name()  %></td>
									<td><%= od.getProduct_amount()==null?"":od.getProduct_amount()  %></td>
									<td><%= od.getProduct_price()==null?"":od.getProduct_price()  %></td>
								</tr>
								<% 
								 } %>
							--%>
							
							
							<c:forEach var="orderList" items="${orderList}" >
								<tr>
								<td>${orderList.order_serial}</td>
								<td>${orderList.order_phone}</td>
								<td>${orderList.product_name}</td>
								<td>${orderList.product_amount}</td>
								<td>${orderList.product_price}</td>
							</tr>
							</c:forEach>
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

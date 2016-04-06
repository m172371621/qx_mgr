<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.service.po.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
<jsp:include page="../common/datetimepicker.jsp" />
 
<script type="text/javascript">
$(function() {
	$("input[name='start_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
	$("input[name='end_time1']").datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
});
</script>

</head>
<body <% if(request.getAttribute("pickUp")!=null) out.print("onload=\"alert('设置成功')\"");
    if(request.getAttribute("msg")!=null) out.print("onload=\"alert('"+request.getAttribute("msg")+"')\"");
%> 
   
>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<!-- content -->
<input type="hidden" id="curr_li" value="order_2"/>
<div id="mright">
	<div id="mr_cnt">
					<div class="mrw_title">订单查询</div>
					<div class="sform_wrap c">
							<ul>
							<% 
							   OrderSearch searchBean = request.getAttribute("searchBean") == null? new OrderSearch(): (OrderSearch)request.getAttribute("searchBean");
							   int pageIndex = request.getAttribute("pageIndex")==null ? 0 : (Integer)request.getAttribute("pageIndex");
							   int pageCount = request.getAttribute("pageCount")==null ? 0 : (Integer)request.getAttribute("pageCount");
							   int order_status = searchBean.getOrder_status() == null ? 0 : searchBean.getOrder_status();	 
							   int cid = searchBean.getCid() == null ? 0 : searchBean.getCid(); 
							   int sid = searchBean.getSid() == null ? 0 : searchBean.getSid();	 
							%>

								<li>订单号：<input type="text" class="i_text" name="order_serial1" value="<%= JsonUtil.obj2Str(searchBean.getOrder_serial()) %>"/></li>
								<li>商品名：<input type="text" class="i_text" name="product_name1" value="<%= JsonUtil.obj2Str(searchBean.getProduct_name()) %>"/></li>
								<li>用户名：<input type="text" class="i_text" name="user_name1"  value="<%= JsonUtil.obj2Str(searchBean.getUser_name()) %>"/></li>
								<li>手机号码：<input type="text" class="i_text" name="user_phone1" value="<%= JsonUtil.obj2Str(searchBean.getUser_phone()) %>"/></li>
								
								<li>订单状态：<select name="order_status1"  id="" class="i_text">
									<option value="122" <% if(order_status == 122) out.print("selected");%> >未提货</option>
									<option value="3" <% if(order_status==3) out.print("selected");%> >已提货</option>
								</select>
								</li>
								<li>								
								</li>
								<li>所属服务：<select name="sid1"  id="" class="i_text">
									<option value="0"  >全部</option>
									<%
									List<ServiceVo> slist = (List<ServiceVo>)request.getSession().getAttribute("product_service");
									   for(ServiceVo s : slist){
									%>
									<option value="<%= s.getService_id()%>" <% if(s.getService_id() == sid) {out.print("selected");  }%> ><%= s.getService_name() %></option>
								
									<% } %>
								</select>
								</li>
								<li>订单时间从：<input type="text" readonly class="i_text" name="start_time1" value="<%= JsonUtil.obj2Str(searchBean.getStart_time()) %>"/></li>
								<li>到：<input type="text" readonly class="i_text" name="end_time1" value="<%= JsonUtil.obj2Str(searchBean.getEnd_time()) %>" /></li>
								<li><a href="javascript:submitForm()" class="btn btn_big btn_y">搜 索</a></li>
							</ul>
						</div>
						<form action="${pageContext.request.contextPath }/order.do?method=orderPickup" method="post" id="searchForm">
							<input type="hidden" name="order_serial" value="<%= JsonUtil.obj2Str(searchBean.getOrder_serial()) %>"/>
							<input type="hidden" name="product_name" value="<%= JsonUtil.obj2Str(searchBean.getProduct_name()) %>"/>
							<input type="hidden" name="user_name" value="<%= JsonUtil.obj2Str(searchBean.getUser_name()) %>"/>
							<input type="hidden" name="user_phone" value="<%= JsonUtil.obj2Str(searchBean.getUser_phone()) %>"/>
							<input type="hidden" name="order_status" value="<%= order_status %>"/>
							<input  type="hidden" name="cid" value="<%=((Community) session.getAttribute("selectCommunity")).getCommunity_id()  %>"/> 
							<input type="hidden" name="sid" value="<%= sid %>"/>
							<input type="hidden" name="start_time" value="<%= JsonUtil.obj2Str(searchBean.getStart_time()) %>"/>
							<input type="hidden" name="end_time" value="<%= JsonUtil.obj2Str(searchBean.getEnd_time()) %>"/>
							<input type="hidden" name="pageIndex" id="pageIndex" value="<%= pageIndex %>"/>
						
						<script type="text/javascript">
							function submitForm()
							{
								$("input[name='order_serial']").val($("input[name='order_serial1']").val());
								$("input[name='product_name']").val($("input[name='product_name1']").val());
								$("input[name='user_name']").val($("input[name='user_name1']").val());
								$("input[name='user_phone']").val($("input[name='user_phone1']").val());
								$("input[name='order_status']").val($("select[name='order_status1']").val());
								
								$("input[name='sid']").val($("select[name='sid1']").val());
								$("input[name='start_time']").val($("input[name='start_time1']").val());
								$("input[name='end_time']").val($("input[name='end_time1']").val());
								$('#pageIndex').val(1);
								$('#searchForm').submit();
							}						
						</script>
					<div id="order_wrap" class="p20">
						
						<table class="table_t_t">
						<colgroup><col width="160px"/><col width="180px"/><col width="80px"/><col width="80px"/><col width="80px"/><col width="80px"/><col width="80px"/><col width="160px"/><col width="80px"/></colgroup>
							<tr>
								<th>订单号 </th>
								<th>商品名</th>
								<th>用户名 </th>							
								<th>订单状态</th>
								<th>手机号</th>
								<th>商品数量</th>
								<th>订单总价</th>
								<th>订单时间</th>
								<th>选择提货</th>
							</tr>
							<% List<Order> list = (List<Order>)request.getAttribute("list");
							   if(list!=null){
							   for(Order order: list) {
							   String time = CommonUtil.formatTime(order.getOrder_time().getTime(),0);
							   String status = "订单提交";
							   if(order.getOrder_status() == 1)
							   {
								   status = "订单提交";
							   }else if(order.getOrder_status() == 2)
							   {
								   status = "已到货";
							   }else if(order.getOrder_status() == 3)
							   {
								   status = "已提货";
							   }else if(order.getOrder_status() == 11)
							   {
								   status = "待定价";
							   }else if(order.getOrder_status() == 12)
							   {
								   status = "已定价";
							   }else if(order.getOrder_status() == 21)
							   {
								   status = "未付款";
							   }else if(order.getOrder_status() == 22)
							   {
								   status = "已付款";
							   }else if(order.getOrder_status() == 23)
							   {
								   status = "已取消";
							   }
							%>
							<tr>
								<td><a target="_blank" href="${pageContext.request.contextPath }/order.do?method=edit&type=2&viewId=<%= order.getOrder_id() %>" ><%= order.getOrder_serial() %></a></td>							
								<td><%= order.getProduct_name() %> </td>
								<td><%= order.getUser_name() %></td>
								<td><%= status%> </td>
								<td><%= order.getUser_phone() %></td>
								<td><%= order.getProduct_amount() %></td>
								<td><%= order.getOrder_price() %></td>
								<td><%= time %></td>
								<td><input type="checkbox" name="ids" value="<%= order.getOrder_id() %>"  <%if(order.getOrder_status() == 3) out.print("disabled"); %>/></td>
							</tr>
							<% }} %>
							<tr>
								<td colspan="8">&nbsp;</td>
								<td><input type="hidden" name="ispickup" id="ispickup"/><a href="javascript:$('#ispickup').val('1');$('#searchForm').submit();" class="btn btn_r">设置提货</a></td>
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

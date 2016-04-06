<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>
<%@ page language="java" import="com.brilliantreform.sc.utils.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../common/resource.jsp" />
</head>
<body <% if(request.getAttribute("orderEdit")!=null) out.print("onload=\"alert('修改成功')\"");%>>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
<jsp:include page="../common/menu.jsp" />

<%
Order order = (Order)request.getAttribute("order");
int order_type = order.getOrder_status() == 11 || order.getOrder_status() == 12 ? 1:0;

%>
<!-- content -->
<input type="hidden" id="curr_li" value="order_3"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">修改订单</div>
					<form action="${pageContext.request.contextPath }/order.do?method=edit&type=3" method="post" id="form">
					<div class="p20 m20"><input type="hidden" value="<%= order.getOrder_id() %>" name="editId"/>
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>订单编号</th>
								<td><input type="text" width="180px"  disabled class="i_text_1" name="order_serial" value="<%= JsonUtil.obj2Str(order.getOrder_serial()) %>"/></td>
							</tr>
							<tr>
								<th>商品名称</th>
								<td><input type="text" width="180px"  disabled class="i_text_1" name="product_name" value="<%= JsonUtil.obj2Str(order.getProduct_name()) %>"/></td>
							</tr>
							<tr>
								<th>用户名</th>
								<td><input type="text" disabled class="i_text_1" name="user_name" value="<%= JsonUtil.obj2Str(order.getUser_name()) %>"/></td>
							</tr>							
							<tr>
								<th>订购数量</th>
								<td><input type="text" disabled class="i_text_1" id="product_amount" name="product_amount" value="<%= JsonUtil.obj2Str(order.getProduct_amount()) %>"/></td>
							</tr>
							<tr>
								<th>商品总价</th>
								<td><input type="text" class="i_text_1" id="product_price" name="product_price" value="<%= JsonUtil.obj2Str(order.getProduct_price()) %>" onkeyup="count()"/></td>
							</tr>
							<tr>
								<th>订单状态</th>
								<td><select name="order_status"  id="" class="i_text_1" >
									<option value="11" <% if(order.getOrder_status()==11) out.print("selected");%> >待定价</option>
									<option id="option" value="12" <% if(order.getOrder_status()==12) out.print("selected");%> >已定价</option>
								</select>
								<input type="hidden"  name="isStatChg" id="isStatChg" value="0"/>
								</td>
							</tr>
							<tr>
								<th>用户手机</th>
								<td><input type="text" disabled class="i_text_1" name="user_phone" value="<%= JsonUtil.obj2Str(order.getUser_phone()) %>"/></td>
							</tr>
							<tr>
								<th>用户小区</th>
								<td><input type="text" disabled class="i_text_1" disabled name="c_name" value="<%= JsonUtil.obj2Str(order.getC_name()) %>"/></td>
							</tr>
							<tr>
								<th>送货地址</th>
								<td><input type="text" disabled class="i_text_1" disabled name="delivery_addr" value="<%= JsonUtil.obj2Str(order.getDelivery_addr()) %>"/></td>
							</tr>
							<tr>
								<th>订单总价</th>
								<td><input type="text" readonly class="i_text_1" id="order_price" name="order_price" value="<%= JsonUtil.obj2Str(order.getOrder_price()) %>"/></td>
							</tr>
							
							<tr>
								<th>下单时间</th>
								<td><input type="text" disabled class="i_text_1" name="order_time" value="<%= CommonUtil.formatTimestamp(order.getOrder_time(),1) %>"/></td>
							</tr>
							<tr>
								<th>定价时间</th>
								<td><input type="text" disabled class="i_text_1" name="delivery_time" value="<%= CommonUtil.formatTimestamp(order.getDelivery_time(),1) %>"/></td>
							</tr>
									
						</table>
					</div>
					<script type="text/javascript">
						function count()
						{
							var price =  parseFloat($('#product_price').val());


							if(!isNaN(price))
							{
								$('#order_price').val(price);	
								$('#option').attr("selected",true);
								
							}
							else
								$('#order_price').val("0");	
						
						}
						function submit()
						{
							if('<%=order.getOrder_status()%>' != $("select[name='order_status']").val())
							{
								$('#isStatChg').val(1);
							}
							
							$('#form').submit();
						
						}
						</script>	
					<div class="mrw_btn_wrap">
						<a href="javascript:submit()" class="btn btn_big btn_r">确定</a>
					</div>
					</form>
				</div>
			</div>
<!-- content end -->

</div>
</body>
</html>

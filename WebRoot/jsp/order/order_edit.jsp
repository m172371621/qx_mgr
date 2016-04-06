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
<input type="hidden" id="curr_li" value="order_0"/>
<div id="mright">
				<div id="mr_cnt">
					<div class="mrw_title">修改订单</div>
					<form action="${pageContext.request.contextPath }/order.do?method=edit" method="post" id="form">
					<div class="p20 m20"><input type="hidden" value="<%= order.getOrder_id() %>" name="editId"/>
						<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
							<tr>
								<th>订单编号</th>
								<td><input type="text" width="180px" disabled class="i_text_1" name="order_serial" value="<%= JsonUtil.obj2Str(order.getOrder_serial()) %>"/></td>
							</tr>
							<tr>
								<th>商品名称</th>
								<td><input type="text" width="180px" disabled class="i_text_1" name="product_name" value="<%= JsonUtil.obj2Str(order.getProduct_name()) %>"/></td>
							</tr>
							<tr>
								<th>用户名</th>
								<td><input type="text" disabled class="i_text_1" name="user_name" value="<%= JsonUtil.obj2Str(order.getUser_name()) %>"/></td>
							</tr>							
							<tr>
								<th>订购数量</th>
								<td><input type="text" disabled class="i_text_1" id="product_amount" name="product_amount" value="<%= JsonUtil.obj2Str(order.getProduct_amount()) %>" onkeyup="count()"/></td>
							</tr>
							<tr>
								<th>商品单价</th>
								<td><input type="text" disabled class="i_text_1" id="product_price" name="product_price" value="<%= JsonUtil.obj2Str(order.getProduct_price()) %>" onkeyup="count()"/></td>
							</tr>
							<tr>
								<th>订单状态</th>
								<td><select name="order_status"  id="" class="i_text_1" onchange="$('#isStatChg').val(1)">
								<%if(order_type == 0) {%>
									<option value="1" <% if(order.getOrder_status()==1) out.print("selected");%> >订单提交</option>
									<option value="2" <% if(order.getOrder_status()==2) out.print("selected");%> >已到货</option>
									<option value="3" <% if(order.getOrder_status()==3) out.print("selected");%> >已提货</option>
									<option value="21" <% if(order.getOrder_status()==21) out.print("selected");%> >未付款</option>
									<option value="22" <% if(order.getOrder_status()==22) out.print("selected");%> >已付款</option>
									<option value="23" <% if(order.getOrder_status()==23) out.print("selected");%> >已取消</option>
								<%}else{%>
									<option value="11" <% if(order.getOrder_status()==11) out.print("selected");%> >待定价</option>
									<option value="12" <% if(order.getOrder_status()==12) out.print("selected");%> >已定价</option>
								<%}%>
								</select><input type="hidden" value="<%= order.getOrder_id() %>" name="isStatChg" id="isStatChg" value="0"/>
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
							<%if(order_type == 0) {%>
							<tr>
								<th>送货类型</th>
								<td>
								    <input type="hidden" id="hidd_delivery_type" value="<%=order.getDelivery_type() %>"/>
								    <input type="hidden" id="hidd_distri_status" value="<%=order.getDistri_staus() %>"/>
									<input disabled type="radio" name="delivery_type" value="1" <% if(order.getDelivery_type()==1) out.print("checked");%> onclick="$('#delivery_price').val(0);$('#delivery_price').attr('disabled',true); count();"/>自取 
									<input disabled type="radio" name="delivery_type" value="2" <% if(order.getDelivery_type()==2) out.print("checked");%> onclick="$('#delivery_price').attr('disabled',false); "/>送货上门								
								</td>
							</tr>
							<tr>
								<th>上门价格</th>
								<td><input type="text" disabled class="i_text_1" id ="delivery_price" name="delivery_price" value="<%= JsonUtil.obj2Str(order.getDelivery_price()) %>" <% if(order.getDelivery_type()==1) out.print("disabled");%> onkeyup="count()"/></td>
							</tr>
							<tr>
								<th>付款类型</th>
								<td><input type="radio" name="pay_type" value="1" <% if(order.getPay_type()==1) out.print("checked");%> />货到付款
									<input type="radio" name="pay_type" value="2" <% if(order.getPay_type()==2) out.print("checked");%>/>在线支付 
								</td>
							</tr>
							<%}%>
							<tr>
								<th>订单总价</th>
								<td><input type="text" disabled class="i_text_1" id="order_price" name="order_price" value="<%= JsonUtil.obj2Str(order.getOrder_price()) %>"/></td>
							</tr>
							<%if(order_type == 0) {%>
							<tr>
								<th>下单时间</th>
								<td><input type="text" disabled class="i_text_1" name="order_time" value="<%= CommonUtil.formatTimestamp(order.getOrder_time(),1) %>"/></td>
							</tr>
							<tr>
								<th>到货时间</th>
								<td><input type="text" disabled class="i_text_1" name="delivery_time" value="<%= CommonUtil.formatTimestamp(order.getDelivery_time(),1) %>"/></td>
							</tr>
							<tr>
								<th>提货时间</th>
								<td><input type="text" disabled class="i_text_1" name="pickup_time" value="<%= CommonUtil.formatTimestamp(order.getPickup_time(),1) %>"/></td>
							</tr>
							<%}else{%>
							<tr>
								<th>下单时间</th>
								<td><input type="text" disabled class="i_text_1" name="order_time" value="<%= CommonUtil.formatTimestamp(order.getOrder_time(),1) %>"/></td>
							</tr>
							<tr>
								<th>定价时间</th>
								<td><input type="text" disabled class="i_text_1" name="delivery_time" value="<%= CommonUtil.formatTimestamp(order.getDelivery_time(),1) %>"/></td>
							</tr>
							<%}%>
									
						</table>
					</div>
						
					<div class="mrw_btn_wrap">
						<a href="javascript:frmsumit();" class="btn btn_big btn_r">提 交</a><a href="${pageContext.request.contextPath }/jsp/order/order_search.jsp" class="btn btn_big btn_r">返回</a>
					</div>
					</form>
				</div>
			</div>
<!-- content end -->

            <script type="text/javascript">
						function count()
						{
							var amount = parseFloat($('#product_amount').val());
							var price =  parseFloat($('#product_price').val());

							var delivery_price = 0.0;
							
							if($('#delivery_price').val())
							   delivery_price = parseFloat($('#delivery_price').val());	

							if(!isNaN(amount)&& !isNaN(amount)&& !isNaN(amount))
								$('#order_price').val(price+delivery_price);	
							else
								$('#order_price').val("0");	
						}
						function frmsumit(){
                            var delivery_type=$("#hidd_delivery_type").val();
                            if("2"==delivery_type){
                                var distri_status=$("#hidd_distri_status").val();
                                if("1"!=distri_status){
                                    alert("未配送完成的送货上门订单不允许修改为已提货！");
                                    return ;
                                }
                                $('#form').submit();
                                
                            }
                        }
						</script>
</div>
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../common/resource.jsp" />
	<script type="text/javascript">
		function updateStatus() {
			var order_serial = $('#order_serial').val();
			var order_status = $('#order_status').val();
			//var select_status = $('#select_status').val();

			var new_order_status = calcNextStatus(order_status);
			if(new_order_status == -1) {
				alert("该订单不能变更状态！");
				return;
			} else {
				if('${order.delivery_type}' == '2' && new_order_status == 3) {
					alert("送货上门的订单不允许变更成已提货");
					return;
				}
				if(confirm("确定" + $('#updateBtn').text() + "吗？")) {
					$.post('${pageContext.request.contextPath }/order.do?method=updateOrderStatus',
							{order_serial : order_serial, new_order_status : new_order_status},
							function(data) {
								if(data != null && data == 'success') {
									alert('状态变更成功！');
									location.reload();
								} else {
									alert("变更失败！");
								}
					});
				}
			}



		}

		//根据现在的订单状态计算之后的订单状态
		function calcNextStatus(order_status) {
			var new_order_status = -1;
			if(order_status == 21) {
				new_order_status = 22;
			} else if(order_status == 22) {
				new_order_status = 3;
			} else if(order_status == 1) {
				new_order_status = 3;
			} else if(order_status == 2) {
				new_order_status = 3;
			} else if(order_status == 5) {
				new_order_status = 2;
			} else if(order_status == 2) {
				new_order_status = 3;
			} else if(order_status == 22) {
				new_order_status = 3;
			}
			return new_order_status;
		}

		$(function() {
			var order_status = $('#order_status').val();
			var new_order_status = calcNextStatus(order_status);
			if(new_order_status == -1) {
				$('#updateBtn').attr('disabled', 'disabled');
			} else {
				if([5].indexOf(new_order_status) >= 0) {
					$('#updateBtn').text($('#updateBtn').text() + "：未到货")
				} else if ([21].indexOf(new_order_status) >= 0) {
					$('#updateBtn').text($('#updateBtn').text() + "：待付款")
				} else if ([1, 2, 22].indexOf(new_order_status) >= 0) {
					$('#updateBtn').text($('#updateBtn').text() + "：待收货")
				} else if ([3].indexOf(new_order_status) >= 0) {
					$('#updateBtn').text($('#updateBtn').text() + "：已提货")
				} else if ([4, 23].indexOf(new_order_status) >= 0) {
					$('#updateBtn').text($('#updateBtn').text() + "：已取消")
				}
			}
		});
	</script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
	<jsp:include page="../common/menu.jsp" />
	<input type="hidden" id="curr_li" value="order_21"/>
	<div id="mright">
		<div id="mr_cnt">
			<div class="mrw_title">修改订单</div>
			<form action="${pageContext.request.contextPath }/order.do?method=updateOrderStatus" method="post" id="orderForm">
				<input type="hidden" value="${order.order_serial}" name="order_serial" id="order_serial"/>
				<input type="hidden" value="${order.order_status}" name="order_status" id="order_status"/>
				<div class="p20 m20">
					<table class="table_t_r">
						<colgroup><col width="30%"/><col/></colgroup>
						<tr>
							<th>订单编号</th>
							<td>${order.order_serial}</td>
						</tr>
						<tr>
							<th>用户名</th>
							<td>${order.user_name}</td>
						</tr>
						<tr>
							<th>用户手机</th>
							<td>${order.delivery_phone}</td>
						</tr>
						<tr>
							<th>用户小区</th>
							<td>${order.community_name}</td>
						</tr>
						<tr>
							<th>送货类型</th>
							<td>
								<c:if test="${order.delivery_type == 1}">自取</c:if>
								<c:if test="${order.delivery_type == 2}">送货上门</c:if>
							</td>
						</tr>
						<tr>
							<th>上门价格</th>
							<td>${order.delivery_price}</td>
						</tr>
						<tr>
							<th>送货地址</th>
							<td>${order.delivery_addr}</td>
						</tr>
						<tr>
							<th>订单状态</th>
							<td>
								<c:set var="select_status" value="${','}${order.order_status}${','}"/>
								<font color="red">
									<c:if test="${fn:contains(',5,', select_status)}">未到货</c:if>
									<c:if test="${fn:contains(',21,', select_status)}">待支付</c:if>
									<c:if test="${fn:contains(',1,2,22,', select_status)}">待收货</c:if>
									<c:if test="${fn:contains(',3,', select_status)}">已提货</c:if>
									<c:if test="${fn:contains(',4,23,', select_status)}">已取消</c:if>
								</font>
								<%--<c:set var="select_status" value="${','}${order.order_status}${','}"/>
								<select id="select_status" class="i_text" disabled="disabled">
									<option value="1" <c:if test="${fn:contains(',5,', select_status)}">selected</c:if> >未到货</option>
									<option value="2" <c:if test="${fn:contains(',21,', select_status)}">selected</c:if> >待支付</option>
									<option value="3" <c:if test="${fn:contains(',1,2,22,', select_status)}">selected</c:if> >待收货</option>
									<option value="4" <c:if test="${fn:contains(',3,', select_status)}">selected</c:if> >已提货</option>
									<option value="5" <c:if test="${fn:contains(',4,23,', select_status)}">selected</c:if> >已取消</option>
								</select>--%>
							</td>
						</tr>
						<tr>
							<th>订单总价</th>
							<td>${order.order_price}</td>
						</tr>
						<tr>
							<th>付款类型</th>
							<td>
								<c:if test="${order.pay_type == 1}">货到付款</c:if>
								<c:if test="${order.pay_type == 2}">线上</c:if>
							</td>
						</tr>
						<tr>
							<th>付款方式</th>
							<td>
								<c:if test="${order.pay_type_ext == 11}">现金</c:if>
								<c:if test="${order.pay_type_ext == 12}">刷卡</c:if>
								<c:if test="${order.pay_type_ext == 13}">混合</c:if>
								<c:if test="${order.pay_type_ext == 21}">区享卡</c:if>
								<c:if test="${order.pay_type_ext == 22}">支付宝</c:if>
								<c:if test="${order.pay_type_ext == 23}">微信</c:if>
							</td>
						</tr>
						<tr>
							<th>付款时间</th>
							<td><fmt:formatDate value="${order.pay_time}" pattern="yyyy-MM-dd HH:mm"/></td>
						</tr>
						<tr>
							<th>下单时间</th>
							<td><fmt:formatDate value="${order.order_time}" pattern="yyyy-MM-dd HH:mm"/></td>
						</tr>
						<tr>
							<th>配送时间</th>
							<td>${order.delivery_time}</td>
						</tr>
						<tr>
							<th>提货时间</th>
							<td><fmt:formatDate value="${order.pickup_time}" pattern="yyyy-MM-dd HH:mm"/></td>
						</tr>
						<tr>
							<th>备注</th>
							<td>${order.delivery_dec}</td>
						</tr>

						<table class="table_t_t">
							<tr><th>商品名称</th><th>商品单价</th><th>订购数量</th><th>总价</th></tr>
							<c:forEach items="${list}" var="product">
								<tr <c:if test="${product.order_status == 23}">style="color: red;" </c:if> >
									<td>${product.product_name}</td>
									<td>${product.product_price}</td>
									<td>
										<fmt:formatNumber type="number" value="${product.product_amount}" maxFractionDigits="2"/>
									</td>
									<td>
										<fmt:formatNumber type="number" value="${product.product_amount * product.product_price}" maxFractionDigits="2"/>
									</td>
								</tr>
							</c:forEach>
						</table>

					</table>

				</div>

				<div class="mrw_btn_wrap">
					<a href="javascript:updateStatus();" class="btn btn_big btn_r" id="updateBtn">状态变更</a>
					<a href="${pageContext.request.contextPath }/order.do?method=listOrder" class="btn btn_big btn_r">返回</a>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>

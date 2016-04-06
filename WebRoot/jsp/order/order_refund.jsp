<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../common/resource.jsp" />
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/common-utils.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/nice-validator/jquery.validator.js?local=zh-CN"></script>
	<script type="text/javascript">
		//计算退货金额
		function genRefundMoney() {
			if($('#refund_amount').isValid()) {
				var refund_amount = $('#refund_amount').val();
				var product_price = '${order.product_price}';
				var refund_money = mul(refund_amount, product_price);
				if(Number(refund_money) > Number('${order.order_price}')) {
					$('#refund_money').val('${order.order_price}');
				} else {
					$('#refund_money').val(refund_money);
				}
				$('#refundForm').data('validator').hideMsg('#refund_money');
			}
		}

		function refund(type) {
			if($('#refundForm').isValid()) {
				if(type == -1 && $('#reject_reason').val() == '') {
					alert("驳回请输入驳回理由");
					return;
				}
				if(confirm("确认要" + (type == 1 ? "退货" : "驳回") + "吗？")) {
					var flag = true;
					if(type == 1) {
						$.ajax({
							type : 'POST',
							url : "${pageContext.request.contextPath }/orderRefund.do?method=checkAward",
							data : {order_id : '${order.order_id}'},
							async : false,
							success: function(data){
								if(data != null && data == 'true') {
									if(!confirm("该用户已经中奖并已领取奖品，确定进行退货吗？")) {
										flag = false;
									}
								}
							}
						});
					}
					if(flag) {
						$('#type').val(type);
						$('#refundForm').submit();
					}
				}
			}
		}

		function checkAward() {
			$.ajax({
				type: 'POST',
				url: "${pageContext.request.contextPath }/orderRefund.do?method=checkAward",
				data:{order_id : '${order.order_id}'},
				success: function(data){
					if(data != null && data == 'true') {
						if(confirm("该用户已经中奖并已领取奖品，确定进行退货吗？")) {

						}
					}
				}
			});
		}
	</script>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="main" class="c">
	<jsp:include page="../common/menu.jsp" />
	<input type="hidden" id="curr_li" value="order_0"/>
	<div id="mright">
		<div id="mr_cnt">
			<div class="mrw_title">订单退货</div>
			<form action="${pageContext.request.contextPath }/orderRefund.do?method=returnGoods" method="post" id="refundForm">
				<input type="hidden" value="${order.order_id}" name="order_id"/>
                <!-- type：1.同意退货 0.驳回请求 -->
				<input type="hidden" name="type" id="type"/>
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
							<td>${order.user_phone}</td>
						</tr>
						<tr>
							<th>商品名称</th>
							<td>${order.product_name}</td>
						</tr>
						<tr>
							<th>商品单价</th>
							<td>${order.product_price}</td>
						</tr>
						<tr>
							<th>订购数量</th>
							<td>${order.product_amount}</td>
						</tr>
						<tr>
							<th>订单总价</th>
							<td>${order.order_price}</td>
						</tr>
						<tr>
							<th>订单状态</th>
							<td>
								<c:if test="${order.order_base_status == 5}">未到货</c:if>
								<c:if test="${order.order_base_status == 21}">待支付</c:if>
								<c:if test="${order.order_base_status == 1 || order.order_base_status == 2 || order.order_base_status == 22}">待收货</c:if>
								<c:if test="${order.order_base_status == 3}">已提货</c:if>
								<c:if test="${order.order_base_status == 4 || order.order_base_status == 23}">已取消</c:if>
							</td>
						</tr>
						<c:if test="${order.order_base_status != 11 && order.order_base_status != 12}">
							<tr>
								<th>送货类型</th>
								<td>
									<c:if test="${order.delivery_type == 1}">自取</c:if>
									<c:if test="${order.delivery_type != 1}">送货上门</c:if>
								</td>
							</tr>
							<tr>
								<th>付款类型</th>
								<td>
									<c:if test="${order.pay_type == 1}">货到付款</c:if>
									<c:if test="${order.pay_type == 2}">在线支付</c:if>
								</td>
							</tr>
						</c:if>
						<tr>
							<th>退货数量</th>
							<td>
                                <input type="text" class="i_text_1" name="refund_amount" id="refund_amount" onblur="genRefundMoney()" data-rule="required;range[0.01~${can_refund_amount}]"
                                       value="${refund.refund_amount}" <c:if test="${!empty refund && refund.status != 0}">disabled</c:if> />
                            </td>
						</tr>
						<tr>
							<th>退款金额</th>
							<td>
                                <input type="text" class="i_text_1" name="refund_money" id="refund_money" value="${refund.refund_price}" data-rule="required;range[0~${order.order_price}]"
                                       <c:if test="${!empty refund && refund.status != 0}">disabled</c:if> />
                            </td>
						</tr>
						<tr>
							<th>退货理由</th>
							<td>
                                <textarea class="i_text_1" name="refund_reason" id="refund_reason" style="height: 100px;"
                                          <c:if test="${!empty refund && refund.status != 0}">disabled</c:if>>${refund.refund_reason}</textarea>
                            </td>
						</tr>
                        <c:if test="${refund.status == 0 || refund.status == -1}">
                            <tr>
                                <th>驳回理由</th>
                                <td>
                                    <textarea class="i_text_1" name="reject_reason" id="reject_reason" style="height: 100px;"
                                              <c:if test="${!empty refund && refund.status != 0}">disabled</c:if>>${refund.reject_reason}</textarea>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${!empty refund}">
                            <tr>
                                <th>退货状态</th>
                                <td>
                                    <c:if test="${refund.status == 0}">提交申请</c:if>
                                    <c:if test="${refund.status == 1}">完成</c:if>
                                    <c:if test="${refund.status == -1}">驳回</c:if>
                                </td>
                            </tr>
                            <tr>
                                <th>退款状态</th>
                                <td>
                                    <c:if test="${refund.refund_status == 0}">待处理</c:if>
									<c:if test="${refund.refund_status == 1}">处理中</c:if>
                                    <c:if test="${refund.refund_status == 2}">完成</c:if>
                                    <c:if test="${refund.refund_status == -1}">失败</c:if>
                                    <c:if test="${refund.refund_status == -2}">无需退款</c:if>
                                </td>
                            </tr>
                        </c:if>

					</table>

					<c:if test="${!empty refunds}">
						<table class="table_t_t">
							<tr><th>退货数量</th><th>退货金额</th><th>退货时间</th><th>退货状态</th><th>退款方式</th><th>退款状态</th></tr>
							<c:forEach items="${refunds}" var="r">
								<tr <c:if test="${r.objid == objid}">style="color:red;"</c:if> >
									<td>${r.refund_amount}</td>
									<td>${r.refund_price}</td>
									<td><fmt:formatDate value="${r.refund_time}" pattern="yyyy-MM-dd HH:mm"/> </td>
									<td>
										<c:if test="${r.status == 0}">待处理</c:if>
										<c:if test="${r.status == 1}">完成</c:if>
										<c:if test="${r.status == -1}">驳回</c:if>
									</td>
									<td>
										<c:if test="${r.refund_type == 1}">现金</c:if>
										<c:if test="${r.refund_type == 2}">区享卡</c:if>
										<c:if test="${r.refund_type == 3}">支付宝</c:if>
										<c:if test="${r.refund_type == 4}">微信</c:if>
									</td>
									<td>
										<c:if test="${r.refund_status == 0}">待处理</c:if>
										<c:if test="${r.refund_status == 1}">处理中</c:if>
										<c:if test="${r.refund_status == 2}">完成</c:if>
										<c:if test="${r.refund_status == -1}">失败</c:if>
										<c:if test="${r.refund_status == -2}">无需退款</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>

				</div>

				<div class="mrw_btn_wrap">
					<c:if test="${empty refund || refund.status == 0}">
						<a href="javascript:refund(1);" class="btn btn_big btn_r">退货</a>
					</c:if>
                    <c:if test="${refund.status == 0}">
                        <a href="javascript:refund(-1);" class="btn btn_big btn_r">驳回</a>
                    </c:if>
					<a href="${pageContext.request.contextPath }/jsp/order/order_search.jsp" class="btn btn_big btn_r">返回</a>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>

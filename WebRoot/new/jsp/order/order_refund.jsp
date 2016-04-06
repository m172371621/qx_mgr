<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>订单退货</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal" id="refundForm">
                        <input type="hidden" value="${order.order_id}" name="order_id"/>
                        <!-- type：1.同意退货 0.驳回请求 -->
                        <input type="hidden" name="type" id="type"/>

                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="alert alert-info">
                                    订单号：<b class="text-navy">${order.order_serial}</b>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    门店：<b class="text-success">${order.c_name}</b>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    状态：<b class="text-danger">${status_name}</b>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">订单信息</div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-offset-1 control-label text-right">商品名称</label>

                                    <div class="col-sm-3">
                                        <p class="form-control-static">${order.product_name}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-offset-1 control-label text-right">用户名</label>

                                    <div class="col-sm-3">
                                        <p class="form-control-static">${order.user_name}</p>
                                    </div>
                                    <label class="col-sm-1 control-label text-right">用户手机</label>

                                    <div class="col-sm-3">
                                        <p class="form-control-static">${order.user_phone}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-offset-1 control-label text-right">商品单价</label>

                                    <div class="col-sm-3">
                                        <p class="form-control-static">${order.product_price}</p>
                                    </div>
                                    <label class="col-sm-1 control-label text-right">订购数量</label>

                                    <div class="col-sm-3">
                                        <p class="form-control-static"><fmt:formatNumber type="number"
                                                                                         value="${order.product_amount}"/></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-offset-1 control-label text-right">订单总价</label>

                                    <div class="col-sm-3">
                                        <p class="form-control-static">${order.order_price}</p>
                                    </div>
                                    <label class="col-sm-1 control-label text-right">付款方式</label>

                                    <div class="col-sm-3">
                                        <qx:getEnumName
                                                clazz="com.brilliantreform.sc.order.enumerate.OrderPayTypeExtEnum"
                                                value="${order.pay_type_ext}" name="pay_type_ext"/>
                                        <p class="form-control-static">${pay_type_ext}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-offset-1 control-label text-right">送货类型</label>

                                    <div class="col-sm-3">
                                        <p class="form-control-static">
                                            <c:if test="${order.delivery_type == 1}">自取</c:if>
                                            <c:if test="${order.delivery_type != 1}">送货上门</c:if>
                                        </p>
                                    </div>
                                </div>

                            </div>
                        </div>

                        <div class="panel panel-default">
                            <div class="panel-heading">退货信息</div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-offset-1 control-label text-right">退货数量</label>

                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="refund_amount" id="refund_amount"
                                               placeholder="本次最多可退数量：<fmt:formatNumber type="number" value="${can_refund_amount}"/>"
                                               onblur="genRefundMoney()"
                                               data-rule="required;<c:if test="${!isPrePro}">integer;</c:if>range[0.01~<fmt:formatNumber type="number" value="${can_refund_amount}"/>]"
                                               value="${refund.refund_amount}"
                                               <c:if test="${!empty refund && refund.status != 0}">disabled</c:if> />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-offset-1 control-label text-right">退款金额</label>

                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="refund_money" id="refund_money"
                                               value="${refund.refund_price}"
                                               data-rule="required;range[0~${order.order_price}]"
                                               <c:if test="${!empty refund && refund.status != 0}">disabled</c:if> />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-offset-1 control-label text-right">退货理由</label>

                                    <div class="col-sm-3">
                                        <textarea class="form-control" name="refund_reason" id="refund_reason"
                                                  <c:if test="${!empty refund && refund.status != 0}">disabled</c:if>>${refund.refund_reason}</textarea>
                                    </div>
                                </div>
                                <c:if test="${refund.status == 0 || refund.status == -1}">
                                    <div class="form-group">
                                        <label class="col-sm-1 col-sm-offset-1 control-label text-right">驳回理由</label>

                                        <div class="col-sm-3">
                                            <textarea class="form-control" name="reject_reason" id="reject_reason"
                                                      <c:if test="${!empty refund && refund.status != 0}">disabled</c:if>>${refund.reject_reason}</textarea>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${!empty refund}">
                                    <div class="form-group">
                                        <label class="col-sm-1 col-sm-offset-1 control-label text-right">退货状态</label>

                                        <div class="col-sm-3">
                                            <qx:getEnumName
                                                    clazz="com.brilliantreform.sc.order.enumerate.ReturnGoodsStatus"
                                                    value="${refund.status}" name="returnGoosStatusName"/>
                                            <p class="form-control-static">${returnGoosStatusName}</p>
                                        </div>
                                        <label class="col-sm-1 control-label text-right">退款状态</label>

                                        <div class="col-sm-3">
                                            <qx:getEnumName
                                                    clazz="com.brilliantreform.sc.order.enumerate.ReturnMoneyStatus"
                                                    value="${refund.refund_status}" name="returnMoneyStatusName"/>
                                            <p class="form-control-static">${returnMoneyStatusName}</p>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </form>

                    <c:if test="${!empty refunds}">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                <tr class="text-center">
                                    <th>退货数量</th>
                                    <th>退货金额</th>
                                    <th>退货时间</th>
                                    <th>退货状态</th>
                                    <th>退款方式</th>
                                    <th>退款状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${refunds}" var="r">
                                    <tr
                                            <c:if test="${r.objid == objid}">class="info"</c:if> >
                                        <td>${r.refund_amount}</td>
                                        <td>${r.refund_price}</td>
                                        <td><fmt:formatDate value="${r.refund_time}" pattern="yyyy-MM-dd HH:mm"/></td>
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
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <div class="text-center">
                        <c:if test="${(empty refund || refund.status == 0) && can_refund_amount > 0}">
                            <button class="btn btn-danger" onclick="refund(1)">退货</button>
                        </c:if>
                        <c:if test="${refund.status == 0}">
                            <button class="btn btn-primary" onclick="refund(-1)">驳回</button>
                        </c:if>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script type="text/javascript">
    //计算退货金额
    function genRefundMoney() {
        if ($('#refund_amount').isValid()) {
            var refund_amount = $('#refund_amount').val();
            var product_price = '${order.product_price}';
            var refund_money = mul(refund_amount, product_price);
            if (Number(refund_money) > Number('${order.order_price}')) {
                $('#refund_money').val('${order.order_price}');
            } else {
                $('#refund_money').val(refund_money);
            }
            $('#refundForm').data('validator').hideMsg('#refund_money');
        }
    }

    function refund(type) {
        if ($('#refundForm').isValid()) {
            if (type == -1 && $('#reject_reason').val() == '') {
                layer.alert("驳回请输入驳回理由");
                return;
            }

            layer.confirm("确认要" + (type == 1 ? "退货" : "驳回") + "吗？", function (index) {
                var flag = true;
                if (type == 1) {
                    var o = layer.load();
                    $.ajax({
                        type: 'POST',
                        url: "${ctx}/orderRefund.do?method=checkAward",
                        data: {order_id: '${order.order_id}'},
                        async: false,
                        success: function (data) {
                            if (data != null && data == 'true') {
                                if (!confirm("该用户已经中奖并已领取奖品，确定进行退货吗？")) {
                                    flag = false;
                                }
                            }
                            layer.close(o);
                        }
                    });
                }
                if (flag) {
                    $('#type').val(type);
                    var m = layer.load();
                    $.post('${ctx}/orderRefund.do?method=returnGoodsV4', $('#refundForm').serialize(), function(data) {
                        if(data != null && data == 'success') {
                            layer.alert("操作成功！", function() {
                                location.reload();
                            });
                        } else {
                            layer.alert("操作失败！");
                        }
                        layer.close(m);
                    });
                }
            });

        }
    }

    function checkAward() {
        $.ajax({
            type: 'POST',
            url: "${pageContext.request.contextPath }/orderRefund.do?method=checkAward",
            data: {order_id: '${order.order_id}'},
            success: function (data) {
                if (data != null && data == 'true') {
                    if (confirm("该用户已经中奖并已领取奖品，确定进行退货吗？")) {

                    }
                }
            }
        });
    }
</script>

</body>

</html>
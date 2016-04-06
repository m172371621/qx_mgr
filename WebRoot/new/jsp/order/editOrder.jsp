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
                    <h5>修改订单</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal" id="orderForm">
                        <input type="hidden" value="${order.order_serial}" id="order_serial"/>
                        <input type="hidden" value="${order.order_status}" id="order_status"/>

                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="alert alert-info">
                                    订单号：<b class="text-navy">${order.order_serial}</b>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    门店：<b class="text-success">${order.community_name}</b>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    状态：<b class="text-danger">${order.status_name}</b>
                                </div>
                            </div>
                        </div>

                        <c:if test="${order.order_status != 5 && order.order_status != 4 && order.order_status != 23}">
                            <div class="form-group">
                                <div class="col-sm-6 col-sm-offset-3">
                                    <div class="progress">
                                        <div class="progress-bar" id="statusBar"/>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group" id="statusBarTitle">
                                <div>
                                    提交订单<br>
                                    <fmt:formatDate value="${order.order_time}" pattern="yyyy-MM-dd"/><br>
                                    <fmt:formatDate value="${order.order_time}" pattern="HH:mm"/>
                                </div>
                                <c:if test="${order.status_name == '待收货' || order.status_name == '已提货'}">
                                    <div>
                                        付款成功<br>
                                        <fmt:formatDate value="${order.pay_time}" pattern="yyyy-MM-dd"/><br>
                                        <fmt:formatDate value="${order.pay_time}" pattern="HH:mm"/>
                                    </div>
                                </c:if>
                                <c:if test="${order.delivery_type == 2}">
                                    <div>等待收货</div>
                                </c:if>
                                <div>
                                    已提货<br>
                                    <fmt:formatDate value="${order.pickup_time}" pattern="yyyy-MM-dd"/><br>
                                    <fmt:formatDate value="${order.pickup_time}" pattern="HH:mm"/>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                        </c:if>

                        <div class="panel panel-default">
                            <div class="panel-heading">订单信息</div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label text-right">用户名</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static">${order.user_name}</p>
                                    </div>
                                    <label class="col-sm-1 control-label text-right">用户手机</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static">${order.delivery_phone}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label text-right">付款类型</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static">
                                            <c:if test="${order.pay_type == 1}">货到付款</c:if>
                                            <c:if test="${order.pay_type == 2}">线上</c:if>
                                        </p>
                                    </div>
                                    <label class="col-sm-1 control-label text-right">付款方式</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static">
                                            <qx:getEnumName
                                                    clazz="com.brilliantreform.sc.order.enumerate.OrderPayTypeExtEnum"
                                                    value="${order.pay_type_ext}" name="pay_type_ext"/>
                                            ${pay_type_ext}
                                        </p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label text-right">付款时间</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static"><fmt:formatDate value="${order.pay_time}"
                                                                                       pattern="yyyy-MM-dd HH:mm"/></p>
                                    </div>
                                    <label class="col-sm-1 control-label text-right">送货类型</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static">
                                            <c:if test="${order.delivery_type == 1}">自取</c:if>
                                            <c:if test="${order.delivery_type == 2}">送货上门</c:if>
                                        </p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label text-right">优惠明细</label>
                                    <div class="col-sm-7">
                                        <p class="form-control-static">${pay_off_text}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label text-right">送货地址</label>
                                    <div class="col-sm-7">
                                        <p class="form-control-static">${order.delivery_addr}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label text-right">备注</label>
                                    <div class="col-sm-7">
                                        <p class="form-control-static">${order.delivery_dec}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                    <div class="hr-line-dashed"></div>
                    <div class="table-responsive m-t">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>商品</th>
                                <th>数量</th>
                                <th>单价</th>
                                <th>总价</th>
                                <th>可退货数量</th>
                                <th style="width: 100px;">退货数量</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="product">
                                <tr>
                                    <td>
                                        <div><strong>${product.product_name}</strong></div>
                                    </td>
                                    <td><fmt:formatNumber type="number" value="${product.product_amount}"
                                                          maxFractionDigits="2"/></td>
                                    <td>&yen;${product.product_price}</td>
                                    <td>&yen;<fmt:formatNumber type="number"
                                                               value="${product.product_amount * product.product_price}"
                                                               maxFractionDigits="2"/></td>
                                    <td>1</td>
                                    <td>
                                        <div class="input-group">
                                            <input type="number" class="form-control text-center" style="width: 100px;">
                                            <span class="input-group-btn">
                                              <button type="button" class="btn btn-primary">全</button>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <table class="table invoice-total">
                        <tbody>
                        <tr>
                            <td><strong>总计</strong></td>
                            <td><h3 class="text-danger">&yen;${order.order_price}</h3></td>
                        </tr>
                        </tbody>
                    </table>

                    <div class="text-right">
                        <input type="button" value="状态变更" class="btn btn-primary" id="updateBtn" onclick="updateStatus()"/>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function initStatusBar() {
        //初始化订单状态进度条
        var bar = $('#statusBarTitle div');
        var num = bar.length;
        if (num == 2) {
            bar.eq(0).addClass('col-sm-2 col-sm-offset-3');
            bar.eq(1).addClass('col-sm-4 text-right');
        } else if (num == 3) {
            bar.eq(0).addClass('col-sm-2 col-sm-offset-3');
            bar.eq(1).addClass('col-sm-2 text-center');
            bar.eq(2).addClass('col-sm-2 text-right');
        } else if (num == 4) {
            bar.eq(0).addClass('col-sm-1 col-sm-offset-3');
            bar.eq(1).addClass('col-sm-2 text-center');
            bar.eq(2).addClass('col-sm-2 text-center');
            bar.eq(3).addClass('col-sm-2 text-left');
        }

        if ('${order.status_name}' == '待收货') {
            if (num == 3) {
                $('#statusBar').css('width', '50%');
            } else if (num == 4) {
                $('#statusBar').css('width', '75%');
            }
        }
        if ('${order.status_name}' == '已提货') {
            $('#statusBar').css('width', '100%');
        }
    }

    function updateStatus() {
        var order_serial = $('#order_serial').val();
        var order_status = $('#order_status').val();

        var new_order_status = calcNextStatus(order_status);
        if (new_order_status == -1) {
            layer.alert("该订单不能变更状态！");
            return;
        } else {
            /*if ('${order.delivery_type}' == '2' && new_order_status == 3) {
                layer.alert("送货上门的订单不允许变更成已提货");
                return;
            }*/
            layer.confirm("确定" + $('#updateBtn').val() + "吗？", function (index) {
                $.post('${ctx}/order.do?method=updateOrderStatus',
                        {order_serial: order_serial, new_order_status: new_order_status},
                        function (data) {
                            if (data != null && data == 'success') {
                                layer.alert('状态变更成功！', function() {
                                    location.reload();
                                });
                            } else {
                                layer.alert("变更失败！");
                            }
                        });
            });
        }
    }

    //根据现在的订单状态计算之后的订单状态
    function calcNextStatus(order_status) {
        var new_order_status = -1;
        if (order_status == 21) {
            new_order_status = 22;
        } else if (order_status == 22) {
            new_order_status = 3;
        } else if (order_status == 1) {
            new_order_status = 3;
        } else if (order_status == 2) {
            new_order_status = 3;
        } else if (order_status == 5) {
            new_order_status = 2;
        } else if (order_status == 2) {
            new_order_status = 3;
        } else if (order_status == 22) {
            new_order_status = 3;
        }
        return new_order_status;
    }

    $(function () {
        initStatusBar();

        var order_status = $('#order_status').val();
        var new_order_status = calcNextStatus(order_status);
        if (new_order_status == -1) {
            $('#updateBtn').attr('disabled', 'disabled');
        } else {
            if ([5].indexOf(new_order_status) >= 0) {
                $('#updateBtn').val($('#updateBtn').val() + "：未到货");
            } else if ([21].indexOf(new_order_status) >= 0) {
                $('#updateBtn').val($('#updateBtn').val() + "：待付款");
            } else if ([1, 2, 22].indexOf(new_order_status) >= 0) {
                $('#updateBtn').val($('#updateBtn').val() + "：待收货");
            } else if ([3].indexOf(new_order_status) >= 0) {
                $('#updateBtn').val($('#updateBtn').val() + "：已提货");
            } else if ([4, 23].indexOf(new_order_status) >= 0) {
                $('#updateBtn').val($('#updateBtn').val() + "：已取消");
            }
        }
    });
</script>
</body>

</html>
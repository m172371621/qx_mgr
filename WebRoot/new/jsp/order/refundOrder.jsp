<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <style type="text/css">
        .n-default .msg-wrap {
            position: relative;
        }
        .n-invalid {
            border: 1px solid #c00;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>订单明细</h5>
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
                                    <label class="col-sm-1 control-label text-right">订单总计</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static">
                                            <fmt:formatNumber type="number" value="${order.order_price}" maxFractionDigits="2" groupingUsed="false"/>
                                        </p>
                                    </div>
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
                    <form id="refundForm">
                        <div class="table-responsive m-t">
                            <table class="table table-striped" id="refundTable">
                                <thead>
                                    <tr>
                                        <th>商品</th>
                                        <th>数量</th>
                                        <th>单价</th>
                                        <th>总价</th>
                                        <th class="text-center" style="width: 150px;">可退货数量</th>
                                        <th class="text-center" style="width: 200px;">退货数量</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${orderProductList}" var="product">
                                        <tr>
                                            <td>
                                                <div><strong>${product.product_name}</strong></div>
                                            </td>
                                            <td>
                                                <fmt:formatNumber type="number" value="${product.product_amount}" maxFractionDigits="2" groupingUsed="false"/>
                                            </td>
                                            <td>
                                                <fmt:formatNumber type="number" value="${product.product_price}" maxFractionDigits="2" groupingUsed="false"/>
                                            </td>
                                            <td>
                                                <fmt:formatNumber type="number" value="${product.product_amount * product.product_price}" maxFractionDigits="2" groupingUsed="false"/>
                                            </td>
                                            <td class="text-center">
                                                <fmt:formatNumber type="number" value="${product.canRefundAmount}" maxFractionDigits="2" groupingUsed="false"/>
                                            </td>
                                            <td class="text-center">
                                                <div class="input-group">
                                                    <input type="text" class="form-control text-center" name="refund${product.order_id}" onblur="showRefundMoney()" data-order_id="${product.order_id}" data-product_id="${product.product_id}"
                                                           data-rule="<c:if test="${!product.isPrePro}">integer;</c:if>range[<c:if test="${product.isPrePro}">0.01</c:if><c:if test="${!product.isPrePro}">1</c:if>~<fmt:formatNumber type="number" value="${product.canRefundAmount}" groupingUsed="false"/>]" data-target="#msg_holder${product.order_id}"
                                                        <c:if test="${product.canRefundAmount == 0}">disabled</c:if>
                                                    />
                                                    <span class="input-group-btn">
                                                      <button type="button" class="btn btn-primary" onclick="fullRefund(this)">全</button>
                                                    </span>
                                                </div>
                                                <span id="msg_holder${product.order_id}"></span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </form>

                    <table class="table invoice-total" id="refund_money_table" style="display:none;">
                        <tbody>
                            <tr>
                                <td><strong>退款总计</strong></td>
                                <td>
                                    <h3 class="text-danger" id="refund_money">
                                        <fmt:formatNumber type="number" value="${order.order_price}" maxFractionDigits="2" groupingUsed="false"/>
                                    </h3>
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="text-right">
                        <input type="button" value="退货退款" class="btn btn-primary" id="refundBtn" onclick="refund()" disabled/>
                    </div>

                    <br><br>
                    <div class="panel panel-default">
                        <div class="panel-heading">退货退款信息</div>
                        <div class="panel-body">
                            <div id="table" class="dt-grid-container"></div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="refundTemplate">
    <b>退货明细</b>
    <ul>
        [: for (var i = 0; i < list.length; i++) { :]
            <li>[:=list[i].product_name:]&nbsp;&nbsp;&nbsp;&nbsp;[:=list[i].refund_amount:]</li>
        [:}:]
    </ul>
    <b>退款金额：</b>[:=refund_money:]
    <br>
    <b>退款方式：</b>[:=refund_type:]
</script>

<script type="text/javascript">
    function refund() {
        showRefundMoney();
        if($('#refund_money_table').is(":visible")) {
            var list = new Array();
            $('#refundTable tr:gt(0)').each(function(i, v) {
                var refund_amount = $(v).find('input').val();
                if(refund_amount > 0) {
                    var product_name = $(v).find('td').eq(0).find('strong').html();
                    list.push({product_name : product_name, refund_amount : refund_amount});
                }
            });
            var data = new Object();
            data.list = list;
            data.refund_money = $('#refund_money').html();
            data.refund_type = '${pay_type_ext}';
            layer.confirm(tppl($('#refundTemplate').html(), data), function() {
                var i = layer.load();
                $.post('${ctx}/orderRefund.do?method=refund', buildRequestData(), function(result) {
                    if(result) {
                        var json = eval('(' + result + ')');
                        if(json && json.result) {
                            layer.alert('退货退款成功', function() {location.reload();});
                        } else {
                            layer.alert(json.msg, function() {location.reload();});
                        }
                    } else {
                        layer.alert('退货退款失败', function() {location.reload();});
                    }
                    layer.close(i);
                });
            });
        }
    }

    function buildRequestData() {
        var data = 'order_serial=${order.order_serial}&refund_money=' + $('#refund_money').html();
        $('#refundTable tr:gt(0)').each(function(i, v) {
            var obj = $(v).find('input');
            var refund_amount = obj.val();
            if(refund_amount > 0) {
                var order_id = obj.data('order_id');
                var product_id = obj.data('product_id');
                var product_price = Number($(v).find('td').eq(2).html());
                data += '&order_id=' + order_id + '&product_id=' + product_id + '&product_price=' + product_price + '&refund_amount=' + refund_amount;
            }
        });
        return data;
    }

    function showRefundMoney() {
        var refund_money = calcRefundMoney();
        if(refund_money > 0) {
            $('#refund_money_table').show();
            $('#refund_money').html(refund_money);
            $('#refundBtn').attr('disabled', false);
        } else {
            $('#refund_money_table').hide();
            $('#refund_money').html('');
            $('#refundBtn').attr('disabled', true);
        }
    }

    //计算退款总金额
    function calcRefundMoney() {
        var refund_money = 0;
        var i = layer.load();
        $.ajax({
            url : '${ctx}/orderRefund.do?method=calcRefundMoney',
            type : 'post',
            async : false,
            data : buildRequestData(),
            success : function(data) {
                if(data && Number(data) > 0) {
                    refund_money = Number(data);
                }
            },
            complete : function() {
                layer.close(i);
            }
        });
        return refund_money;
        /*var refund_money = 0;
        if($('#refundForm').isValid()) {
            $('#refundTable tr:gt(0)').each(function(i, v) {
                var refund_amount = $(v).find('input').val();
                if(refund_amount > 0) {
                    var product_price = Number($(v).find('td').eq(2).html());
                    refund_money += mul(refund_amount, product_price);
                }
            });
            if(refund_money > ${order.order_price}) {
                refund_money = ${order.order_price};
            }
        }
        return refund_money;*/
    }

    function fullRefund(obj) {
        var amount = Number($(obj).parents('tr').find('td').eq(4).html());
        if(amount > 0) {
            $(obj).parents('td').find('input').val(amount);
            showRefundMoney();
        }
    }

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

    var return_status = {0 : '待处理', 1 : '完成', '-1' : '驳回'};
    var refund_type = {1 : '现金', 2 : '区享卡', 3 : '支付宝', 4 : '微信'};
    var refund_status = {0 : '待处理', 1 : '处理中', 2 : '完成', '-1' : '失败', '-2' : '无需退款'};

    var gridColumn = [
        {id:'create_time', title:'申请时间', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center'},
        {id:'return_status', title:'退货状态', type:'string', columnClass:'text-center', codeTable : return_status},
        {id:'return_time', title:'退货时间', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center'},
        {id:'refund_money', title:'退款金额', type:'string', columnClass:'text-center'},
        {id:'refund_type', title:'退款方式', type:'string', columnClass:'text-center', codeTable : refund_type},
        {id:'refund_status', title:'退款状态', type:'string', columnClass:'text-center', codeTable : refund_status},
        {id:'refund_time', title:'退款时间', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center'}
    ];

    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/orderRefund.do?method=searchOrderRefundBase',
        columns : gridColumn,
        gridContainer : 'table',
        tools : ''
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function () {
        initStatusBar();

        grid.parameters = new Object();
        grid.parameters['order_serial'] = '${order.order_serial}';
        grid.load();
    });
</script>
</body>

</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <style type="text/css">
        .nav-tabs>li>a {
            padding: 10px 10px 10px 10px;
        }
        .checkbox input[type=checkbox], .checkbox-inline input[type=checkbox], .radio input[type=radio], .radio-inline input[type=radio] {
            margin-top: 4px;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>订单管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-6">
                            <ul class="nav nav-tabs">
                                <li class="active" data-status=""><a data-toggle="tab" aria-expanded="true">全部订单</a></li>
                                <li class="" data-status="5"><a data-toggle="tab" aria-expanded="false">未到货</a></li>
                                <li class="" data-status="21"><a data-toggle="tab" aria-expanded="false">待支付</a></li>
                                <li class="" data-status="1,2,22"><a data-toggle="tab" aria-expanded="false">待收货</a></li>
                                <li class="" data-status="3"><a data-toggle="tab" aria-expanded="false">已提货</a></li>
                                <li class="" data-status="4,23"><a data-toggle="tab" aria-expanded="false">已取消</a></li>
                            </ul>
                        </div>
                        <div class="col-sm-5 col-sm-offset-1">
                            <div class="input-group">
                                <%--<ui:communitySelect id="community_id" event="search()"/>--%>
                                <div class="input-group-btn">
                                    <button data-toggle="dropdown" class="btn btn-sm btn-white dropdown-toggle" type="button" id="cbtn">全部<span class="caret"></span></button>
                                    <ul class="dropdown-menu" style="height: 350px;overflow:scroll;">
                                        <li><a href="javascript:void(0)" onclick="changeCommunity('', '全部')">全部</a></li>
                                        <li class="divider"></li>
                                        <c:forEach items="${communityList}" var="c">
                                            <li><a href="javascript:void(0)" onclick="changeCommunity('${c.community_id}', '${c.community_name}')">${c.community_name}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <input type="text" placeholder="订单号/手机号/用户名/商品名" class="input-sm form-control" id="keyword">
                                <span class="input-group-btn">
                                  <button type="button" class="btn btn-sm btn-primary" onclick="search()"><i
                                          class="fa fa-search"></i></button>
                                  <button type="button" class="btn btn-sm btn-outline btn-default" onclick="showForm(this)">高级
                                      <i class="fa fa-chevron-down"></i></button>
                                </span>
                            </div>
                        </div>
                    </div>
                    <br>
                    <!-- 高级查询条件 -->
                    <form role="form" class="form-horizontal" id="queryForm" style="display: none;">
                        <input type="hidden" id="community_id">
                        <input type="hidden" id="order_status_array"/>
                        <div class="form-group">
                            <qx:getEnumList clazz="com.brilliantreform.sc.order.enumerate.OrderPayTypeExtEnum" enumList="payTypeExtList"/>
                            <label for="pay_type_ext" class="col-sm-1 control-label text-right">付款方式</label>
                            <div class="col-sm-2">
                                <select id="pay_type_ext" class="form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${payTypeExtList}" var="payTypeExt">
                                        <option value="${payTypeExt.value}">${payTypeExt.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <label for="sid" class="col-sm-1 control-label text-right">商品类别</label>
                            <div class="col-sm-2">
                                <ui:simplePsTag id="sid" header="全部" />
                            </div>

                            <label class="col-sm-1 control-label text-right">订单时间</label>
                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="start_time"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="end_time"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label text-right">提货时间</label>

                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="pickup_start_time"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="pickup_end_time"/>
                                </div>
                            </div>
                            <div class="col-sm-7">
                                <button class="btn btn-primary" type="button" id="queryBtn" onclick="search()">查询</button>
                            </div>
                        </div>
                    </form>

                    <div id="orderTable" class="dt-grid-container"></div>
                    <div id="orderToolBar" class="dt-grid-toolbar-container"></div>

                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="deliveryWin" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title_edit">分配配送员</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="deliveryForm">
                    <input type="hidden" id="order_serial_d" name="order_serial">
                    <div class="form-group" style="margin-top: -15px;margin-bottom: -10px;">
                        <label class="col-md-3 control-label text-left">收货人</label>
                        <div class="col-md-9">
                            <p class="form-control-static" id="delivery_user_d"></p>
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: -15px;margin-bottom: -10px;">
                        <label class="col-md-3 control-label text-left">收货人电话</label>
                        <div class="col-md-9">
                            <p class="form-control-static" id="delivery_phone_d"></p>
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: -15px;margin-bottom: 0px;">
                        <label class="col-md-3 control-label text-left">收货地址</label>
                        <div class="col-md-9">
                            <p class="form-control-static" id="delivery_addr_d"></p>
                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">货物清单</div>
                        <div class="panel-body" id="productList_d">
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">配送员</div>
                        <div class="panel-body" id="deliveryer_d">
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="acceptOrder()">接单</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<div style="display:none;position:fixed; right:0px; bottom:10px; width:15px; height:75px; background-color:#CCC; cursor:pointer"
     id="toTop" onclick="toTop()">
    返回顶部
</div>
<%--<div style="position:fixed; right:0px; bottom:10px; width:15px; height:38px; background-color:#CCC; cursor:pointer" onclick="fullScreen()">
    全屏
</div>--%>

<script type="text/html" id="detailTemplate_1">
    <div class="row">
        <div class="col-md-4">[:= order_time :]</div>
        <div class="col-md-5">[:= order_serial :]</div>
        <div class="col-md-3 text-center">[:= community_name :]</div>
    </div>
</script>
<script type="text/html" id="detailTemplate_2">
    <div class="row">
        <div class="col-md-4">[:= order_time :]</div>
        <div class="col-md-5"><a href="javascript:void(0)" onclick="showOrderNote('[:= order_serial :]')">[:= order_serial :]</a></div>
        <div class="col-md-3 text-center">[:= community_name :]</div>
    </div>
</script>
<!-- 分配配送员窗口中货物清单模板 -->
<script type="text/html" id="productListTemplate">
    <ul>
        [: for (var i = 0; i < orderProductList.length; i++) { :]
        <li>[:=orderProductList[i].product_name:] * [:=orderProductList[i].product_amount:]</li>
        [:}:]
    </ul>
</script>
<!-- 分配配送员窗口中配送员列表模板 -->
<script type="text/html" id="deliveryerTemplate">
    <dl>
        [: for (var i = 0; i < deliveryers.length; i++) { :]
        <dt>
            <label class="radio-inline">
                <input type="radio" name="distri_worker_id" value="[:=deliveryers[i].user_id:]" data-rule="checked;">
                [:=deliveryers[i].distri_worker_name:]&nbsp;&nbsp;&nbsp;待配送数量：[:=deliveryers[i].count:]
            </label>
        </dt>
        <dd>定位地址：[:=deliveryers[i].addr:]&nbsp;&nbsp;&nbsp;[:=deliveryers[i].addr_time:]</dd>
        [:}:]
    </dl>
</script>

<script type="text/javascript">
    var pay_type_ext = {11: '现金', 12: '刷卡', 13: '混合', 21: '区享卡', 22: '支付宝', 23: '微信'};
    var channel = {1: 'android', 2: 'ios', 3: 'weixin', 4: 'pos'};
    var ps_status = {'-1': '未接单', 0: '待取货', 2 : '配送中', 1: '完成'};    //配送状态
    var delivery_type = {1 : '自', 2 : '送'};

    function toTop() {
        $('body,html').animate({scrollTop: 0}, 100);
    }

    function showForm(obj) {
        $('#queryForm').slideToggle(200);
        $(obj).find("i").toggleClass("fa-chevron-up").toggleClass("fa-chevron-down"),
                setTimeout(function () {
                    $('.ibox-content').resize();
                }, 50);
    }

    /**
     * 解析订单详情
     * */
    function parseOrderDetail(record) {
        var order_serial = record.order_serial;
        var order_id = record.order_id;
        var order_time = record.order_time;
        var communityname = record.communityname;
        var product_name = record.product_name;
        var product_amount = record.product_amount;
        if (order_serial && !order_id) {
            var data = {order_time : $.fn.DtGrid.tools.dateFormat(order_time, 'yyyy-MM-dd hh:mm'), order_serial : order_serial, community_name : communityname};
            var html = tppl($('#detailTemplate_1').html(), data);
            //只有待收货且送货上门的才可打印小票
            if(record.order_status == 1 || record.order_status == 2 || record.order_status == 22) {
                if(record.delivery_type == 2) {
                    html = tppl($('#detailTemplate_2').html(), data)
                }
            }
            return html;
        } else if (!order_serial) {
            return '';
        } else if (order_id) {
            //处理退货数量
            //var refund_amount = record.refund_amount ? "  (<span class='text-danger'>" + record.refund_amount + "</span>)" : "";
            return "<div class='row'><div class='col-md-9'>" + product_name +
                    "</div><div class='col-md-3 text-center'>" + product_amount + "</div></div>";
        }
    }

    /**
     * 解析按钮
     * */
    function parseBtn(record) {
        var order_serial = record.order_serial;
        var order_id = record.order_id;
        if (order_serial && !order_id) {
            var viewBtn = "<button class='btn btn-primary' onclick='editOrder(\"" + record.order_serial + "\")'>查看</button>";
            //接单，分配配送员，状态变成待取货
            var acceptOrderBtn = "<button class='btn btn-info' onclick='showDeliveryWin(\"" + record.order_serial + "\")'>接单</button>";
            //已取货，状态为配送中
            var deliveryBtn = "<button class='btn btn-info' onclick='changeDeliveryStatus(\"" + record.order_serial + "\")'>配送</button>";
            var finishOrderBtn = "<button class='btn btn-info' onclick='finishOrder(\"" + record.order_serial + "\")'>提货</button>";

            var btn = viewBtn;
            if(record.order_status == 1 || record.order_status == 2 || record.order_status == 22) {
                //只有未接单的送货上门订单才显示接单按钮
                if(record.distri_staus == -1 && record.delivery_type == 2) {
                    btn += " " + acceptOrderBtn;
                }
                //只有待取货状态下的订单才显示配送按钮
                if(record.distri_staus == 0 && record.delivery_type == 2) {
                    btn += " " + deliveryBtn;
                }
                //自取的或者送货上门配送中的订单才能提货
                if(record.delivery_type == 1 || (record.delivery_type == 2 && record.distri_staus == 2)) {
                    btn += " " + finishOrderBtn;
                }
            }
            return btn;
        } else if (!order_serial) {
            return '';
        } else if (order_id) {
            //可以退货按钮为红色，不可以则为黄色
            /*if(record.product_amount > record.refund_amount) {
                return "<button class='btn btn-danger btn-sm' onclick='refund(\"" + record.order_id + "\")'>退货</button>";
            } else {
                return "<button class='btn btn-warning btn-sm' onclick='refund(\"" + record.order_id + "\")'>查看</button>";
            }*/
            return "<div class='btn btn-sm' disabled style='cursor: default;visibility: hidden;'>明细</div>";
        }
    }

    /**
     * 解析价格
     * */
    function parseOrderPrice(record) {
        var order_serial = record.order_serial;
        var product_name = record.product_name;
        if (order_serial && !product_name) {
            return "<b>" + $.fn.DtGrid.tools.numberFormat(record.order_price, '#.##') + "</b>";
        } else if (product_name) {
            var price = mul(record.product_price, record.product_amount);
            return $.fn.DtGrid.tools.numberFormat(price, '#.##');
        } else {
            return "";
        }
    }

    /**
     * 解析渠道
     * */
    function parseChannel(record) {
        var order_serial = record.order_serial;
        var product_name = record.product_name;
        if (order_serial && !product_name) {
            return channel[record.channel];
        } else {
            return "";
        }
    }

    /**
     * 解析配送状态
     * */
    function parsePs_status(record) {
        var order_serial = record.order_serial;
        var product_name = record.product_name;
        if (order_serial && !product_name && record.order_status != 23) {
            return ps_status[record.distri_staus] + "（" + delivery_type[record.delivery_type] + "）";
        } else {
            return "";
        }
    }

    var gridColumn = [
        {
            id: 'order_detail', title: '订单详情', type: 'string', columnClass: 'text-left',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                return parseOrderDetail(record);
            }
        },
        {id: 'username', title: '收货人', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {
            id: 'order_price', title: '总价', type: 'string', columnClass: 'text-center', hideType: 'xs',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                return parseOrderPrice(record);
            }
        },
        {
            id: 'pay_type_ext',
            title: '付款方式',
            type: 'string',
            columnClass: 'text-center',
            hideType: 'xs',
            codeTable: pay_type_ext
        },
        {
            id: 'channel', title: '渠道', type: 'string', columnClass: 'text-center', hideType: 'xs', codeTable: channel,
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                return parseChannel(record);
            }
        },
        {id: 'status_name', title: '订单状态', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {
            id: 'distri_staus',
            title: '配送状态',
            type: 'string',
            columnClass: 'text-center',
            hideType: 'xs',
            resolution : function (value, record) {
                return parsePs_status(record);
            }
        },
        {id: 'refund_amount', title: '退货数量', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {
            id: 'operation', title: '操作', type: 'string', columnClass: 'text-center', hideType: 'xs',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                return parseBtn(record);
            }
        }
    ];
    var gridOption = {
        ajaxLoad: true,
        loadURL: '${ctx}/order.do?method=listOrderV4',
        exportFileName: '订单列表',
        columns: gridColumn,
        gridContainer: 'orderTable',
        toolbarContainer: 'orderToolBar',
        tools: '',
        tableClass: 'table table-responsive',
        onGridComplete: function (grid) {
            $('table .dt-grid-row button').each(function (i, v) {
                if ($(v).attr('onclick').indexOf('editOrder') == 0) {
                    $(v).parent().parent().attr('data-type', 'order');
                    if($.trim($(v).parent().prev().prev().prev().text()) == '待收货') {
                        $(v).parent().parent().addClass('danger');
                    } else {
                        $(v).parent().parent().addClass('active');
                    }
                }
            });
        }
    };

    var grid = $.fn.DtGrid.init(gridOption);
    $(function () {
        grid.load();
        $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});

        $(window).scroll(function () {
            if ($(this).scrollTop() != 0) {
                $('#toTop').fadeIn();
            } else {
                $('#toTop').fadeOut();
            }
        });

        $('.nav-tabs li').on('click', function(){
            $('#order_status_array').val($(this).data('status'));
            search();
        });
    });

    //自定义查询
    function search() {
        var community_id = $('#community_id').val();
        var keyword = $('#keyword').val();
        var order_status_array = $('#order_status_array').val();
        var pay_type_ext = $('#pay_type_ext').val();
        var sid = $('#sid').val();
        var start_time = $('#start_time').val();
        var end_time = $('#end_time').val();
        var pickup_start_time = $('#pickup_start_time').val();
        var pickup_end_time = $('#pickup_end_time').val();

        grid.parameters = new Object();
        grid.parameters['community_id'] = community_id;
        grid.parameters['keyword'] = keyword;
        grid.parameters['order_status_array'] = order_status_array;
        if($('#queryForm').is(':visible')) {
            grid.parameters['pay_type_ext'] = pay_type_ext;
            grid.parameters['sid'] = sid;
            grid.parameters['start_time'] = start_time;
            grid.parameters['end_time'] = end_time;
            grid.parameters['pickup_start_time'] = pickup_start_time;
            grid.parameters['pickup_end_time'] = pickup_end_time;
        }
        grid.loadToFirst();
    }

    function editOrder(order_serial) {
        window.parent.openTab('${ctx}/orderRefund.do?method=showRefundOrderPage&order_serial=' + order_serial, '订单明细');
    }

    function refund(order_id) {
        window.parent.openTab('${ctx}/orderRefund.do?method=showRefundV4&orderid=' + order_id, '订单退货');
    }

    function changeCommunity(community_id, community_name) {
        $('#community_id').val(community_id);
        $('#cbtn').html(community_name + "<span class='caret'></span>");
        loadSimplePs_sid(community_id);
        search();
    }

    //接单
    function acceptOrder(order_serial) {
        if($('#deliveryForm').isValid()) {
            layer.confirm("确定要接单吗？", function() {
                var i = layer.load();
                $.post('${ctx}/order.do?method=acceptOrder', $('#deliveryForm').serialize(), function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json && json.result) {
                            layer.alert("操作成功！");
                            $('#deliveryWin').modal('hide');
                            showOrderNote($('#order_serial_d').val());
                            search();
                        } else {
                            layer.alert("操作失败！");
                        }
                    } else {
                        layer.alert("操作失败！");
                    }
                    layer.close(i);
                });
            });
        }
    }

    //提货
    function finishOrder(order_serial) {
        layer.confirm("确定要提货吗？", function() {
            var i = layer.load();
            $.post('${ctx}/order.do?method=pickUpOrder', {ids : order_serial}, function(data) {
                if(data && data == 'success') {
                    layer.alert("操作成功！");
                    search();
                } else {
                    layer.alert("操作失败！");
                }
                layer.close(i);
            });
        });
    }

    function showOrderNote(order_serial) {
        var newWin = window.open ('${ctx}/order.do?method=showOrderNotePage&order_serial=' + order_serial,
                'newwindow',
                'height=500, width=300,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
    }

    function showDeliveryWin(order_serial) {
        var i = layer.load();
        $.post('${ctx}/order.do?method=getDeliveryInfo', {order_serial : order_serial}, function(data) {
            if(data) {
                var json = eval('(' + data + ')');
                $('#order_serial_d').val(order_serial);
                $('#delivery_addr_d').text(json.delivery_addr);
                $('#delivery_user_d').text(json.delivery_user);
                $('#delivery_phone_d').text(json.delivery_phone);
                $('#productList_d').html(tppl($('#productListTemplate').html(), json));
                $('#deliveryer_d').html(tppl($('#deliveryerTemplate').html(), json));
                $('#deliveryWin').modal({backdrop: 'static'});

                //如果只有一个配送员，则默认选中该配送员
                if($('#deliveryer_d input[type=radio]').length == 1) {
                    $('#deliveryer_d input[type=radio]').eq(0).prop('checked', true);
                }
            }
            layer.close(i);
        });
    }

    //配送，配送状态改成配送中
    function changeDeliveryStatus(order_serial) {
        layer.confirm("配送员已取货，即将配送吗？", function() {
            var i = layer.load();
            $.post('${ctx}/order.do?method=changeDeliveryStatus', {order_serial : order_serial}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json && json.result) {
                        layer.alert("操作成功！");
                        search();
                    } else {
                        layer.alert("操作失败！");
                    }
                } else {
                    layer.alert("操作失败！");
                }
                layer.close(i);
            });
        });
    }
</script>

</body>

</html>
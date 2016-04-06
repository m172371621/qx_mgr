<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <style type="text/css">
        .nav-tabs>li>a {
            padding: 10px 10px 10px 10px;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>订单到货</h5>
                </div>
                <div class="ibox-content">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline btn-primary" onclick="changeOrder()">
                            设置到货
                        </button>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <ul class="nav nav-tabs disabled">
                                <li class="active" data-status="5"><a data-toggle="tab" aria-expanded="false">未到货</a></li>
                            </ul>
                        </div>
                        <div class="col-sm-5 col-sm-offset-1">
                            <div class="input-group">
                                <div class="input-group-btn">
                                    <button data-toggle="dropdown" class="btn btn-sm btn-white dropdown-toggle" type="button" id="cbtn">全部<span class="caret"></span></button>
                                    <ul class="dropdown-menu">
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
                                  <button type="button" class="btn btn-sm btn-outline btn-default" onclick="showForm(this)">高级<i
                                          class="fa fa-chevron-down"></i></button>
                                </span>
                            </div>
                        </div>
                    </div>
                    <br>
                    <!-- 高级查询条件 -->
                    <form role="form" class="form-horizontal" id="queryForm" style="display: none;">
                        <input type="hidden" id="community_id">
                        <input type="hidden" id="order_status_array" value="5"/>
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

<div style="display:none;position:fixed; right:0px; bottom:10px; width:15px; height:75px; background-color:#CCC; cursor:pointer"
     id="toTop" onclick="toTop()">
    返回顶部
</div>
<%--<div style="position:fixed; right:0px; bottom:10px; width:15px; height:38px; background-color:#CCC; cursor:pointer" onclick="fullScreen()">
    全屏
</div>--%>

<script type="text/javascript">
    function changeOrder() {
        var records = grid.getCheckedRecords();
        if(records.length == 0) {
            layer.alert("请先选中需要设置到货的订单");
        } else {
            layer.confirm("确定要设置到货吗？", function(index) {
                var ids = '';
                $.each(records, function(i, v) {
                    ids += v.order_serial + ',';
                });
                if(ids != '') {
                    ids = ids.substring(0, ids.length - 1);
                }
                var n = layer.load();
                $.post('${ctx}/order.do?method=updatePreSaleOrder', {ids : ids}, function(data) {
                    if(data != null && data == 'success') {
                        layer.alert("操作成功！");
                        search();
                    } else {
                        layer.alert("操作失败！");
                    }
                    layer.close(n);
                });
            });
        }
    }

    var pay_type_ext = {11: '现金', 12: '刷卡', 13: '混合', 21: '区享卡', 22: '支付宝', 23: '微信'};
    var channel = {1: 'android', 2: 'ios', 3: 'weixin', 4: 'pos'};
    var ps_status = {'-1': '未接单', 0: '配送中', 1: '完成'};    //配送状态

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
            return "<div class='row'><div class='col-md-4'>" + $.fn.DtGrid.tools.dateFormat(order_time, 'yyyy-MM-dd hh:mm') +
                    "</div><div class='col-md-5'>" + order_serial +
                    "</div><div class='col-md-3 text-center'>" + communityname + "</div></div>";
        } else if (!order_serial) {
            return '';
        } else if (order_id) {
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
            return "<button class='btn btn-primary' onclick='editOrder(\"" + record.order_serial + "\")'>明细</button>";
        } else if (!order_serial) {
            return '';
        } else if (order_id) {
            return "";
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
            return mul(record.product_price, record.product_amount);
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

    var gridColumn = [
        {
            id: 'order_detail', title: '订单详情', type: 'string', columnClass: 'text-left',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                return parseOrderDetail(record);
            }
        },
        {id: 'username', title: '收货人', type: 'string', columnClass: 'text-center'},
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
            codeTable: ps_status
        },
        {
            id: 'operation', title: '操作', type: 'string', columnClass: 'text-center', hideType: 'xs',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                return parseBtn(record);
            }
        }
    ];
    var gridOption = {
        check : true,
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
                    $(v).parent().parent().addClass('active');
                }
            });

            //处理checkbox
            $('.dt-grid-row input[type=checkbox]').each(function(i, v) {
               var tr = $(v).parents('tr');
                if(tr.data('type') != 'order') {
                    $(v).remove();
                }
            });
        }
    };

    var grid = $.fn.DtGrid.init(gridOption);
    $(function () {
        search();

        $(".input-daterange").datepicker({});

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

        grid.parameters = new Object();
        grid.parameters['community_id'] = community_id;
        grid.parameters['keyword'] = keyword;
        grid.parameters['order_status_array'] = order_status_array;
        if($('#queryForm').is(':visible')) {
            grid.parameters['pay_type_ext'] = pay_type_ext;
            grid.parameters['sid'] = sid;
            grid.parameters['start_time'] = start_time;
            grid.parameters['end_time'] = end_time;
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
</script>

</body>

</html>
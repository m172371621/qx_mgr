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
                    <h5>退款管理</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="order_serial" class="col-sm-1 control-label text-right">订单号</label>
                            <div class="col-sm-2">
                                <input type="text" id="order_serial" class="form-control">
                            </div>
                            <label for="loginname" class="col-sm-1 control-label text-right">用户名</label>
                            <div class="col-sm-2">
                                <input type="text" id="loginname" class="form-control">
                            </div>
                            <%--<label for="product_name" class="col-sm-1 control-label text-right">商品名</label>
                            <div class="col-sm-2">
                                <input type="text" id="product_name" class="form-control">
                            </div>--%>
                            <label for="status" class="col-sm-1 control-label text-right">退货状态</label>
                            <div class="col-sm-2">
                                <qx:getEnumList clazz="com.brilliantreform.sc.order.enumerate.ReturnGoodsStatus" enumList="reGoods"/>
                                <select id="return_status" class="form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${reGoods}" var="status">
                                        <option value="${status.value}">${status.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="refund_status" class="col-sm-1 control-label text-right">退款状态</label>
                            <div class="col-sm-2">
                                <qx:getEnumList clazz="com.brilliantreform.sc.order.enumerate.ReturnMoneyStatus" enumList="reMoney"/>
                                <select id="refund_status" class="form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${reMoney}" var="status">
                                        <option value="${status.value}">${status.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label text-right">申请时间</label>
                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="start_create_time"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="end_create_time"/>
                                </div>
                            </div>
                            <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id" header="全部"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label text-right">订单时间</label>
                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="start_order_time"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="end_order_time"/>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" id="queryBtn">查询</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div id="table" class="dt-grid-container"></div>
                    <div id="toolBar" class="dt-grid-toolbar-container"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="productWin">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title">退货商品明细</h4>
            </div>
            <div class="modal-body">
                <div id="productTable" class="dt-grid-container"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var refund_status = {0 : '待处理', 1 : '处理中', 2 : '完成', '-1' : '失败', '-2' : '无需退款'};
    var return_status = {0 : '待处理', 1 : '完成', '-1' : '驳回'};
    var refund_type = {1 : '现金', 2 : '区享卡', 3 : '支付宝', 4 : '微信'};

    var gridColumn = [
        {id:'order_serial', title:'订单号', type:'string', columnClass:'text-center'},
        {id:'loginname', title:'用户名', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'create_time', title:'申请时间', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center'},
        {id:'return_status', title:'退货状态', type:'string', columnClass:'text-center', codeTable : return_status},
        {id:'return_time', title:'退货时间', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center'},
        {id:'refund_money', title:'退款金额', type:'string', columnClass:'text-center'},
        {id:'refund_type', title:'退款方式', type:'string', columnClass:'text-center', codeTable : refund_type},
        {id:'refund_status', title:'退款状态', type:'string', columnClass:'text-center', codeTable : refund_status},
        {id:'refund_time', title:'退款时间', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs',
            resolution:function(value, record) {
                //return "<button class='btn btn-primary' onclick='detail(\"" + record.objid + "\")'>明细</button>";
                if(record.refund_status == 0) {
                    return "<button class='btn btn-primary' onclick='refundOnly(\"" + record.objid + "\")'>退款</button>";
                } else {
                    return "";
                }
            }}
    ];

    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/orderRefund.do?method=searchOrderRefundBase',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : '',
    };

    var grid = $.fn.DtGrid.init(gridOption);

    $(function() {
        $(".input-daterange").datepicker({});
        grid.load();
        $('#queryBtn').click(search);
    });

    //自定义查询
    function search(){
        var community_id = $('#community_id').val();
        var order_serial = $('#order_serial').val();
        var loginname = $('#loginname').val();
        var product_name = $('#product_name').val();
        var return_status = $('#return_status').val();
        var refund_status = $('#refund_status').val();
        var start_create_time = $('#start_create_time').val();
        var end_create_time = $('#end_create_time').val();
        var start_order_time = $('#start_order_time').val();
        var end_order_time = $('#end_order_time').val();

        grid.parameters = new Object();
        grid.parameters['community_id'] = community_id;
        grid.parameters['order_serial'] = order_serial;
        grid.parameters['loginname'] = loginname;
        grid.parameters['product_name'] = product_name;
        grid.parameters['return_status'] = return_status;
        grid.parameters['refund_status'] = refund_status;
        grid.parameters['start_create_time'] = start_create_time;
        grid.parameters['end_create_time'] = end_create_time;
        grid.parameters['start_order_time'] = start_order_time;
        grid.parameters['end_order_time'] = end_order_time;
        grid.loadToFirst();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////

    var productGridColumn = [
        {id:'product_name', title:'商品名称', type:'string', columnClass:'text-center'},
        {id:'product_price', title:'价格', type:'string', columnClass:'text-center'},
        {id:'refund_amount', title:'退货数量', type:'string', columnClass:'text-center'},
    ];

    var productGridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/orderRefund.do?method=searchOrderRefundProduct',
        columns : productGridColumn,
        gridContainer : 'productTable',
        tools : '',
    };

    var productGrid = $.fn.DtGrid.init(productGridOption);

    function detail(refund_id) {
        productGrid.parameters = new Object();
        productGrid.parameters['refund_id'] = refund_id;
        productGrid.load();

        $('#productWin').modal();
    }

    function refundOnly(refund_id) {
        layer.confirm("确定要退款吗？", function(index) {
            var i = layer.load();
            $.post('${ctx}/orderRefund.do?method=refundOnly', {refund_id : refund_id}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json && json.result) {
                        //只有result返回的话，则代表直接退款成功了，若还有data返回，则为支付宝退款，data为退款请求form数据，需跳转到支付宝退款页面处理
                        if(json.data) {
                            layer.close(index);
                            parent.openTab(json.data, '支付宝退款');
                        } else {
                            layer.alert("退款成功");
                            grid.load();
                        }
                    } else {
                        layer.alert("退款失败！");
                    }
                } else {
                    layer.alert("退款失败！");
                }
                layer.close(i);
            });
        });
    }
</script>
</body>

</html>
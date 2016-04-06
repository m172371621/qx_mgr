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
                    <h5>区享卡操作查询</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="qxcard_no" class="col-sm-1 control-label text-right">区享卡号</label>
                            <div class="col-sm-2">
                                <input type="text" id="qxcard_no" class="form-control">
                            </div>
                            <label for="order_serial" class="col-sm-1 control-label text-right">订单号</label>
                            <div class="col-sm-2">
                                <input type="text" id="order_serial" class="form-control">
                            </div>
                            <label for="phone" class="col-sm-1 control-label text-right">手机号</label>
                            <div class="col-sm-2">
                                <input type="text" id="phone" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label text-right">操作时间</label>
                            <div class="col-sm-4">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="time_from"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="time_to"/>
                                </div>
                            </div>
                            <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id" header="全部"/>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" onclick="search()">查询</button>
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

<script type="text/javascript">
    var op_type = { '1001' : '开卡', '1002' : '激活', '1003' : '冻结', '1004' : '解冻', '2001' : '消费', '2002' : '退款', '3001' : '过期', '3002' : '删除', '3003' : '作废'};

    var gridColumn = [
        {id:'order_serial', title:'订单编号', type:'string', columnClass:'text-center'},
        {id:'qxcard_no', title:'区享卡号', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'qxcard_balance', title:'余额', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'phone', title:'手机号码', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'op_price', title:'操作金额', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'op_type', title:'操作类型', type:'string', columnClass:'text-center', hideType:'xs',codeTable:op_type},
        {id:'op_dec', title:'操作描述', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'op_result_dec', title:'操作结果描述', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'createtime', title:'操作日期', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'}
    ];
    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/qxcard.do?method=searchOptLog',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : ''
    };
    var grid = $.fn.DtGrid.init(gridOption);
    $(function(){
        grid.load();
        $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});
    });

    //自定义查询
    function search(){
        var qxcard_no = $('#qxcard_no').val();
        var order_serial = $('#order_serial').val();
        var phone = $('#phone').val();
        var time_from = $('#time_from').val();
        var time_to = $('#time_to').val();
        var community_id = $('#community_id').val();
        grid.parameters = new Object();
        grid.parameters['qxcard_no'] = qxcard_no;
        grid.parameters['order_serial'] = order_serial;
        grid.parameters['phone'] = phone;
        grid.parameters['time_from'] = time_from;
        grid.parameters['time_to'] = time_to;
        grid.parameters['community_id'] = community_id;
        grid.loadToFirst();
    }
</script>

</body>

</html>
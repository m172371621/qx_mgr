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
                        <h5>微信付款记录查询</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label for="order_serial" class="col-sm-1 control-label text-right">订单号</label>
                                <div class="col-sm-2">
                                    <input type="text" id="order_serial" class="form-control">
                                </div>
                                <label for="user" class="col-sm-1 control-label text-right">手机号</label>
                                <div class="col-sm-2">
                                    <input type="text" id="user" class="form-control">
                                </div>
                                <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                                <div class="col-sm-2">
                                    <ui:simpleCommunitySelect id="community_id" header="全部"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label text-right">付款日期</label>
                                <div class="col-sm-3">
                                    <div class="input-daterange input-group">
                                        <input type="text" class="form-control" id="time_from"/>
                                        <span class="input-group-addon">到</span>
                                        <input type="text" class="form-control" id="time_to"/>
                                    </div>
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
                        <div id="wxpayTable" class="dt-grid-container"></div>
                        <div id="wxpayToolBar" class="dt-grid-toolbar-container"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var gridColumn = [
            {id:'out_trade_no', title:'订单号', type:'string', columnClass:'text-center'},
            {id:'phone', title:'手机号码', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'order_price', title:'订单总额', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'total_fee', title:'微信支付金额', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'qxcard_pay_price', title:'区享卡支付金额', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'bind_time', title:'支付日期', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
            {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'}
        ];
        var gridOption = {
            ajaxLoad : true,
            loadURL : '${ctx}/payLogCtrl.do?method=listWxpayLog',
            columns : gridColumn,
            gridContainer : 'wxpayTable',
            toolbarContainer : 'wxpayToolBar',
            tools : ''
        };
        var grid = $.fn.DtGrid.init(gridOption);
        $(function(){
            $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});
            grid.load();
        });

        //自定义查询
        function search(){
            var community_id = $('#community_id').val();
            var order_serial = $('#order_serial').val();
            var user = $('#user').val();
            var time_from = $('#time_from').val();
            var time_to = $('#time_to').val();
            grid.parameters = new Object();
            grid.parameters['community_id'] = community_id;
            grid.parameters['order_serial'] = order_serial;
            grid.parameters['user'] = user;
            grid.parameters['time_from'] = time_from;
            grid.parameters['time_to'] = time_to;
            grid.loadToFirst();
        }
    </script>

</body>

</html>
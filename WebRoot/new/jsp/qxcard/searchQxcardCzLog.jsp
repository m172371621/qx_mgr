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
                    <h5>区享卡充值查询</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="phone" class="col-sm-1 control-label text-right">手机号</label>
                            <div class="col-sm-2">
                                <input type="text" id="phone" class="form-control"/>
                            </div>
                            <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id" header="全部"/>
                            </div>
                            <label for="ad_type" class="col-sm-1 control-label text-right">充值时间</label>
                            <div class="col-sm-3">
                                <div class="input-daterange input-group">
                                    <input type="text" class="form-control" id="start_cz_time"/>
                                    <span class="input-group-addon">到</span>
                                    <input type="text" class="form-control" id="end_cz_time"/>
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
                    <%--<div class="btn-group">
                        <button type="button" class="btn btn-outline btn-default">
                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                        </button>
                        <button type="button" class="btn btn-outline btn-default">
                            <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                        </button>
                    </div>--%>
                    <div id="table" class="dt-grid-container"></div>
                    <div id="toolBar" class="dt-grid-toolbar-container"></div>

                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var objid;
    var cz_status = {0 : '未完成', 1 : '完成', '-1' : '取消'};    //充值状态
    var pay_status = {0 : '未支付', 1 : '已支付'};

    function parseBtn(record) {
        var content = "";
        if(record.channel == 5 && record.pay_status == 0 && record.status == 0) {
            content = "<button class='btn btn-primary' onclick='alipay(\"" + record.objid + "\")'>支付</button>"
            + " "
            + "<button class='btn btn-danger' onclick='cancel(\"" + record.objid + "\")'>取消</button>";
        }
        return content;
    }

    function parsePrice(record) {
        var content = "<b class='text-danger'>" + record.cz_price + "</b> (" + record.card_value + "*" + record.amount + ")";
        return content;
    }

    var gridColumn = [
        {id:'phone', title:'手机号', type:'string', columnClass:'text-center'},
        {id:'cz_price', title:'充值金额', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo) {
            return parsePrice(record);
        }},
        {id:'pay_price', title:'付款金额', type:'string', columnClass:'text-center', hideType:'xs'},
        {id : 'pay_status', title : '付款状态', type: 'string', codeTable : pay_status, columnClass : 'text-center', hideType : 'xs'},
        {id : 'status', title : '充值状态', type: 'string', codeTable : cz_status, columnClass : 'text-center', hideType : 'xs'},
        {id:'create_time', title:'提交时间', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
        {id:'cz_time', title:'完成时间', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
        {id:'communityName', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo) {
            return parseBtn(record);
        }}
    ];
    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/qxcard.do?method=searchQxcardCzLog',
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
        var phone = $('#phone').val();
        var community_id = $('#community_id').val();
        var start_cz_time = $('#start_cz_time').val();
        var end_cz_time = $('#end_cz_time').val();
        grid.parameters = new Object();
        grid.parameters['phone'] = phone;
        grid.parameters['community_id'] = community_id;
        grid.parameters['start_cz_time'] = start_cz_time;
        grid.parameters['end_cz_time'] = end_cz_time;
        grid.loadToFirst();
    }

    function alipay(objid) {
        var i = layer.load();
        $.post('${ctx}/qxcard.do?method=buildAlipayUrl', {objid : objid}, function(url) {
            if(url != null && url != '') {
                this.objid = objid;
                layer.alert("支付宝页面已在新窗口打开，请在新窗口中进行支付！", {btn: ['已支付','关闭']}, function(index) {
                    var o = layer.load();
                    $.post('${ctx}/qxcard.do?method=getQxcardCzLogById', {objid : objid}, function(data) {
                        if(data) {
                            var json = eval('(' + data + ')');
                            if(json && json.status == 1) {
                                layer.open({
                                    title: "充值成功",
                                    content: "<table class='table'><thead><tr><th>手机</th><td>" + json.phone + "</td></tr></thead><tbody><tr><th>卡号</th><td>" + json.card_no + "</td></tr><tr><th>余额</th><td>" + json.card_value + "</td></tr></tbody></table>"
                                });
                            } else {
                                layer.alert("充值失败！");
                            }
                        }
                        layer.close(o);
                    });
                });
                window.open(url);
            }
            layer.close(i);
        });
    }

    function cancel(objid) {
        layer.confirm("确定要取消吗？", function() {
            var i = layer.load();
            $.post('${ctx}/qxcard.do?method=cancelQxcardCz', {objid : objid}, function(data) {
                if(data != null && data == 'success') {
                    layer.alert("操作成功！");
                    grid.load();
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
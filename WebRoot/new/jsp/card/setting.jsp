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
                    <h5>卡牌设置</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="community_id" class="col-sm-1 control-label text-right">门店</label>

                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id" event="search()"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="tabs-container">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a data-toggle="tab" href="#awardTab" aria-expanded="true">中奖概率&奖品</a>
                            </li>
                            <li class="">
                                <a data-toggle="tab" href="#amountTab" aria-expanded="false">数量</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div id="awardTab" class="tab-pane active">
                                <div class="panel-body">

                                    <div id="awardTable" class="dt-grid-container"></div>
                                    <%--<div id="awardToolBar" class="dt-grid-toolbar-container"></div>--%>

                                </div>
                            </div>


                            <div id="amountTab" class="tab-pane">
                                <div class="panel-body">

                                    <div class="btn-group">
                                        <button type="button" class="btn btn-outline btn-default" onclick="showAddNumWin()">
                                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                    <div id="numTable" class="dt-grid-container"></div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editAwardWin" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑中奖信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editAwardForm">
                    <input type="hidden" id="community_id_edit" name="community_id">
                    <input type="hidden" id="level_id_edit" name="level_id">
                    <div class="form-group">
                        <label for="community_name_edit" class="col-md-4 control-label text-right">门店</label>
                        <div class="col-md-5">
                            <p class="form-control-static" id="community_name_edit"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="card_name_edit" class="col-md-4 control-label text-right">奖项</label>
                        <div class="col-md-5">
                            <p class="form-control-static" id="card_name_edit"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="award_edit" class="col-md-4 control-label text-right">奖品</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="award_edit" name="award">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rate_edit" class="col-md-4 control-label text-right">概率</label>
                        <div class="col-md-5">
                            <div class="input-group">
                                <input type="text" class="form-control" id="rate_edit" name="rate">
                                <span class="input-group-addon">%</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="amount_edit" class="col-md-4 control-label text-right">剩余数量</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="amount_edit" name="amount">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="updateCardSetting()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editNumWin" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑订单卡牌数量</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editNumForm">
                    <input type="hidden" id="objid_edit" name="objid">
                    <input type="hidden" id="cid_edit" name="community_id">
                    <div class="form-group">
                        <label for="cname_edit" class="col-md-4 control-label text-right">门店</label>
                        <div class="col-md-5">
                            <p class="form-control-static" id="cname_edit"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="order_price_edit" class="col-md-4 control-label text-right">订单总价</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="order_price_edit" name="order_price">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="card_num_edit" class="col-md-4 control-label text-right">卡牌数量</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="card_num_edit" name="card_num">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="updateCardNum()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var awardGridColumn = [
        {id:'card_name', title:'奖项', type:'string', columnClass:'text-center'},
        {id:'award', title:'奖品', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'rate', title:'概率', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo) {
            return value + "  %";
        }},
        {id:'amount', title:'剩余数量', type:'string', columnClass:'text-center', hideType:'xs',  resolution:function(value, record, column, grid, dataNo, columnNo) {
            return "<input type='hidden' name='amount' value='" + value + "' />" + value;
        }},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'probability', title:'probability', type:'string', columnClass:'text-center', hide:true},
        {id:'level_id', title:'level_id', type:'string', columnClass:'text-center', hide:true},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
            var content = '<button class="btn btn-primary" type="button">编辑</button>';
            return content;
        }}
    ];

    var numGridColumn = [
        {id:'order_price', title:'订单总价', type:'string', columnClass:'text-center'},
        {id:'card_num', title:'卡牌数量', type:'string', columnClass:'text-center'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
            var content = '<button class="btn btn-primary" type="button" id="editBtn">编辑</button>'
            + ' ' + '<button class="btn btn-primary" type="button" id="removeBtn">删除</button>';
            return content;
        }}
    ];

    var awardGridOption = {
        check : false,
        ajaxLoad : true,
        loadURL : '${ctx}/card.do?method=searchAwardInfo',
        columns : awardGridColumn,
        gridContainer : 'awardTable',
        //toolbarContainer : 'awardToolBar',
        tools : '',
        onCellClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
            var target = e.target || e.srcElement;
            if(target.type == 'button') {
                showEditAwardWin(record);
            }
        }
    };

    var numGridOption = {
        check : false,
        ajaxLoad : true,
        loadURL : '${ctx}/card.do?method=searchCardNumConfig',
        columns : numGridColumn,
        gridContainer : 'numTable',
        //toolbarContainer : 'numToolBar',
        tools : '',
        onCellClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
            var target = e.target || e.srcElement;
            if(target.type == 'button') {
                if(target.id == 'editBtn') {
                    showEditNumWin(record);
                } else if(target.id == 'removeBtn') {
                    removeCardNum(record.objid);
                }
            }
        }
    };

    var awardGrid = $.fn.DtGrid.init(awardGridOption);
    var numGrid = $.fn.DtGrid.init(numGridOption);

    $(function(){
        search();
    });

    function search() {
        awardSearch();
        numSearch();
    }

    function awardSearch(){
        var community_id = $('#community_id').val();
        awardGrid.parameters = new Object();
        awardGrid.parameters['community_id'] = community_id;
        awardGrid.loadToFirst();
    }

    function numSearch(){
        var community_id = $('#community_id').val();
        numGrid.parameters = new Object();
        numGrid.parameters['community_id'] = community_id;
        numGrid.loadToFirst();
    }

    function showEditAwardWin(record) {
        $('#community_id_edit').val(record.community_id);
        $('#level_id_edit').val(record.level_id);
        $('#community_name_edit').text(record.community_name);
        $('#card_name_edit').text(record.card_name);
        $('#award_edit').val(record.award);
        $('#rate_edit').val(record.rate);
        $('#amount_edit').val(record.amount);

        $('#editAwardWin').modal({
            backdrop: 'static'  //点击窗体外不会关闭窗口
        });
    }

    function showEditNumWin(record) {
        $('#objid_edit').val(record.objid);
        $('#cid_edit').val(record.community_id);
        $('#cname_edit').text(record.community_name);
        $('#order_price_edit').val(record.order_price);
        $('#card_num_edit').val(record.card_num);

        $('#editNumWin').modal({
            backdrop: 'static'  //点击窗体外不会关闭窗口
        });
    }

    function showAddNumWin() {
        $('#objid_edit').val('');
        $('#cid_edit').val($('#community_id').val());
        $('#cname_edit').text($("#community_id").find("option:selected").text());
        $('#order_price_edit').val('');
        $('#card_num_edit').val('');

        $('#editNumWin').modal({
            backdrop: 'static'  //点击窗体外不会关闭窗口
        });
    }

    function updateCardSetting() {
        layer.confirm("确定要保存吗？", function() {
            var i = layer.load();
            $.post('${ctx}/card.do?method=updateCardSetting', $('#editAwardForm').serialize(), function(data) {
                if(data != null && data == 'success') {
                    layer.alert("操作成功！");
                    awardGrid.load();
                    $('#editAwardWin').modal('hide');
                } else {
                    layer.alert("操作失败！");
                }
                layer.close(i);
            });
        });
    }

    function updateCardNum() {
        layer.confirm("确定要保存吗？", function() {
            var i = layer.load();
            $.post('${ctx}/card.do?method=saveCardNumConfig', $('#editNumForm').serialize(), function(data) {
                if(data != null && data == 'success') {
                    layer.alert("操作成功！");
                    numGrid.load();
                    $('#editNumWin').modal('hide');
                } else {
                    layer.alert("操作失败！");
                }
                layer.close(i);
            });
        });
    }

    function removeCardNum(objid) {
        layer.confirm("确定要删除吗？", function() {
            var i = layer.load();
            $.post('${ctx}/card.do?method=delCardNum', {objid : objid}, function(data) {
                if(data != null && data == 'ok') {
                    layer.alert("删除成功！");
                    numGrid.load();
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
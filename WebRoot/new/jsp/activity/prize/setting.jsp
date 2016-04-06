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
                    <h5>抽奖中奖设置</h5>
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
                                <a data-toggle="tab" href="#prizeTab" aria-expanded="true">中奖概率&奖品</a>
                            </li>
                            <li class="">
                                <a data-toggle="tab" href="#amountTab" aria-expanded="false">数量</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div id="prizeTab" class="tab-pane active">
                                <div class="panel-body">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-outline btn-default" onclick="showEditPrizeWin()">
                                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                    <div id="prizeTable" class="dt-grid-container"></div>
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

<div class="modal fade" id="editPrizeWin" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="prize_title">编辑中奖信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editPrizeForm">
                    <input type="hidden" id="prize_id_edit" name="prize_id">
                    <input type="hidden" id="community_id_edit" name="community_id">
                    <div class="form-group">
                        <label for="community_name_edit" class="col-md-4 control-label text-right">门店</label>
                        <div class="col-md-5">
                            <p class="form-control-static" id="community_name_edit"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="prize_level_edit" class="col-md-4 control-label text-right">奖项</label>
                        <div class="col-md-5">
                            <select class="form-control" id="prize_level_edit" name="prize_level" data-rule="required;">
                                <option value="一等奖">一等奖</option>
                                <option value="二等奖">二等奖</option>
                                <option value="三等奖">三等奖</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="prize_name_edit" class="col-md-4 control-label text-right">奖品</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="prize_name_edit" name="prize_name" data-rule="required;">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rate_edit" class="col-md-4 control-label text-right">概率</label>
                        <div class="col-md-5">
                            <div class="input-group">
                                <input type="text" class="form-control" id="rate_edit" name="rate" data-rule="required;range[0~100]" data-target="#msg_holder">
                                <span class="input-group-addon">%</span>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <span class="msg-box" id="msg_holder"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="count_edit" class="col-md-4 control-label text-right">剩余数量</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="count_edit" name="count" data-rule="required;integer[+0]">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="prize_img_edit" class="col-md-4 control-label text-right">奖品图片</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="prize_img_edit" name="prize_img">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="prize_dec_edit" class="col-md-4 control-label text-right">奖品说明</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="prize_dec_edit" name="prize_dec">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="savePrize()">保存</button>
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
                <h4 class="modal-title" id="num_title">编辑订单抽奖数量</h4>
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
                        <label for="card_num_edit" class="col-md-4 control-label text-right">抽奖数量</label>
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
    var prizeGridColumn = [
        {id:'prize_level', title:'奖项', type:'string', columnClass:'text-center'},
        {id:'prize_name', title:'奖品', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'rate', title:'概率', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo) {
            return value + "  %";
        }},
        {id:'count', title:'剩余数量', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
            var content = '<button class="btn btn-primary" type="button" id="editPrizeBtn">编辑</button>'
                     + ' ' + '<button class="btn btn-danger" type="button" id="removePrizeBtn">删除</button>';
            return content;
        }}
    ];

    var numGridColumn = [
        {id:'order_price', title:'订单总价', type:'string', columnClass:'text-center'},
        {id:'card_num', title:'抽奖次数', type:'string', columnClass:'text-center'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
            var content = '<button class="btn btn-primary" type="button" id="editBtn">编辑</button>'
            + ' ' + '<button class="btn btn-danger" type="button" id="removeBtn">删除</button>';
            return content;
        }}
    ];

    var prizeGridOption = {
        check : false,
        ajaxLoad : true,
        loadURL : '${ctx}/prize.do?method=searchPrizeInfo',
        columns : prizeGridColumn,
        gridContainer : 'prizeTable',
        tools : '',
        onCellClick : function(value, record, column, grid, dataNo, columnNo, cell, row, extraCell, e) {
            var target = e.target || e.srcElement;
            if(target.id == 'editPrizeBtn') {
                showEditPrizeWin(record);
            } else if(target.id == 'removePrizeBtn') {
                removePrize(record.prize_id);
            }
        }
    };

    var numGridOption = {
        check : false,
        ajaxLoad : true,
        loadURL : '${ctx}/card.do?method=searchCardNumConfig',
        columns : numGridColumn,
        gridContainer : 'numTable',
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

    var prizeGrid = $.fn.DtGrid.init(prizeGridOption);
    var numGrid = $.fn.DtGrid.init(numGridOption);

    $(function(){
        search();
    });

    function search() {
        prizeSearch();
        numSearch();
    }

    function prizeSearch(){
        var community_id = $('#community_id').val();
        prizeGrid.parameters = new Object();
        prizeGrid.parameters['community_id'] = community_id;
        prizeGrid.loadToFirst();
    }

    function numSearch(){
        var community_id = $('#community_id').val();
        numGrid.parameters = new Object();
        numGrid.parameters['community_id'] = community_id;
        numGrid.loadToFirst();
    }

    function clearPrizeForm() {
        $('#prize_id_edit').val('');
        $('#community_id_edit').val($("#community_id").val());
        $('#community_name_edit').text($("#community_id").find("option:selected").text());
        $('#prize_level_edit').val('一等奖');
        $('#prize_name_edit').val('');
        $('#rate_edit').val('');
        $('#count_edit').val('');
        $('#prize_img_edit').val('');
        $('#prize_dec_edit').val('');
        $('#editPrizeForm').validator('cleanUp');
    }

    function showEditPrizeWin(record) {
        clearPrizeForm();
        if(record) {
            $('#prize_title').text("编辑中奖设置");

            $('#prize_id_edit').val(record.prize_id);
            $('#community_id_edit').val(record.cid);
            $('#community_name_edit').text(record.community_name);
            $('#prize_level_edit').val(record.prize_level);
            $('#prize_name_edit').val(record.prize_name);
            $('#rate_edit').val(record.rate);
            $('#count_edit').val(record.count);
            $('#prize_img_edit').val(record.prize_img);
            $('#prize_dec_edit').val(record.prize_dec);
        } else {
            $('#prize_title').text("添加中奖设置");
        }

        $('#editPrizeWin').modal({backdrop: 'static'});
    }

    function showEditNumWin(record) {
        $('#objid_edit').val(record.objid);
        $('#cid_edit').val(record.community_id);
        $('#cname_edit').text(record.community_name);
        $('#order_price_edit').val(record.order_price);
        $('#card_num_edit').val(record.card_num);

        $('#editNumWin').modal({backdrop: 'static'});
    }

    function showAddNumWin() {
        $('#objid_edit').val('');
        $('#cid_edit').val($('#community_id').val());
        $('#cname_edit').text($("#community_id").find("option:selected").text());
        $('#order_price_edit').val('');
        $('#card_num_edit').val('');

        $('#editNumWin').modal({backdrop: 'static'});
    }

    function savePrize() {
        layer.confirm("确定要保存吗？", function() {
            var i = layer.load();
            $.post('${ctx}/prize.do?method=savePrize', $('#editPrizeForm').serialize(), function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json && json.result) {
                        layer.alert("操作成功！");
                        prizeGrid.load();
                        $('#editPrizeWin').modal('hide');
                    } else {
                        if(json.msg) {
                            layer.alert(json.msg);
                        } else {
                            layer.alert("操作失败！");
                        }
                    }
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

    function removePrize(prize_id) {
        layer.confirm("确定要删除吗？", function() {
            var i = layer.load();
            $.post('${ctx}/prize.do?method=removePrize', {prize_id : prize_id}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json && json.result) {
                        layer.alert("删除成功！");
                        prizeGrid.load();
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
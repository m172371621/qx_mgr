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
                        <h5>区享卡操作列表</h5>
                    </div>
                    <div class="ibox-content">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label for="qxcard_no" class="col-sm-1 control-label text-right">卡号</label>
                                <div class="col-sm-2">
                                    <input type="text" id="qxcard_no" class="form-control">
                                </div>
                                <label for="seller_name" class="col-sm-1 control-label text-right">销售员</label>
                                <div class="col-sm-2">
                                    <input type="text" id="seller_name" class="form-control">
                                </div>
                                <label for="op_type" class="col-sm-1 control-label text-right">操作类型</label>
                                <div class="col-sm-2">
                                    <select class="form-control" id="op_type">
                                        <option value="">全部</option>
                                        <option value="1">开卡</option>
                                        <option value="2">冻结</option>
                                        <option value="3">解冻</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label text-right">到货日期</label>
                                <div class="col-sm-4">
                                    <div class="input-daterange input-group">
                                        <input type="text" class="form-control" id="time_from"/>
                                        <span class="input-group-addon">到</span>
                                        <input type="text" class="form-control" id="time_to"/>
                                    </div>
                                </div>
                                <label class="col-sm-1 control-label text-right">门店</label>
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
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline btn-default" onclick="showAddWin()">
                                开卡/冻结卡
                            </button>
                        </div>
                        <div id="table" class="dt-grid-container"></div>
                        <div id="toolBar" class="dt-grid-toolbar-container"></div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="addWin" tabindex="-1" role="dialog" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">区享卡激活/冻结</h4>
                </div>
                <div class="modal-body">
                    <form role="form" class="form-horizontal" id="addForm">
                        <div class="form-group">
                            <label for="community_id_add" class="col-md-4 control-label text-right">门店</label>
                            <div class="col-md-5">
                                <ui:simpleCommunitySelect id="community_id_add" name="community_id"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="qxcard_no_add" class="col-md-4 control-label text-right">区享卡号</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="qxcard_no_add" name="qxcard_no" data-rule="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="custm_name_add" class="col-md-4 control-label text-right">客户名</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="custm_name_add" name="custm_name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="custm_phone_add" class="col-md-4 control-label text-right">客户手机号</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="custm_phone_add" name="custm_phone">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="custm_dec_add" class="col-md-4 control-label text-right">客户备注</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="custm_dec_add" name="custm_dec">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="seller_dec_add" class="col-md-4 control-label text-right">操作备注</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="seller_dec_add" name="seller_dec">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="loginname_add" class="col-md-4 control-label text-right">操作类型</label>
                            <div class="col-md-5">
                                <select id="op_type_add" class="form-control" name="op_type">
                                    <option value="1">开卡</option>
                                    <option value="2">冻结</option>
                                    <option value="3">解冻</option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveLog()">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var op_type = { 1 : '开卡', 2 : '冻结', 3 : '解冻'};

        var gridColumn = [
            {id:'qxcard_no', title:'区享卡号', type:'string', columnClass:'text-center'},
            {id:'value', title:'面值', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'qxcard_balance', title:'余额', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'op_type', title:'操作类型', type:'string', columnClass:'text-center', hideType:'xs', codeTable : op_type},
            {id:'custm_name', title:'客户名', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'custm_phone', title:'客户号码', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'seller_name', title:'销售员', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'seller_dec', title:'备注', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'createtime', title:'操作时间', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
            {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'}
        ];

        var gridOption = {
            ajaxLoad : true,
            loadURL : '${ctx}/qxcard.do?method=searchRegLog',
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
            var seller_name = $('#seller_name').val();
            var op_type = $('#op_type').val();
            var time_from = $('#time_from').val();
            var time_to = $('#time_to').val();
            var community_id = $('#community_id').val();
            grid.parameters = new Object();
            grid.parameters['qxcard_no'] = qxcard_no;
            grid.parameters['seller_name'] = seller_name;
            grid.parameters['op_type'] = op_type;
            grid.parameters['time_from'] = time_from;
            grid.parameters['time_to'] = time_to;
            grid.parameters['community_id'] = community_id;
            grid.loadToFirst();
        }

        function clearAddForm() {
            $('#qxcard_no_add').val('');
            $('#custm_name_add').val('');
            $('#custm_phone_add').val('');
            $('#custm_dec_add').val('');
            $('#seller_dec_add').val('');
            $('#op_type').val(1);
        }

        function showAddWin() {
            clearAddForm();
            $('#addWin').modal({
                backdrop: 'static'  //点击窗体外不会关闭窗口
            });
        }

        function saveLog() {
            if($('#addForm').isValid()) {
                layer.confirm("确定要保存吗？", function() {
                    var i = layer.load();
                    $.post('${ctx}/qxcard.do?method=saveRegLog', $('#addForm').serialize(), function(data) {
                        if(data) {
                            var json = eval('(' + data + ')');
                            if(json) {
                                if(json.result) {
                                    layer.alert("操作成功！");
                                    $('#addWin').modal('hide');
                                    grid.load();
                                } else {
                                    if(json.msg) {
                                        layer.alert(json.msg);
                                    } else {
                                        layer.alert("操作失败！");
                                    }
                                }
                            }
                        }
                        layer.close(i);
                    });
                });
            }
        }

    </script>

</body>

</html>
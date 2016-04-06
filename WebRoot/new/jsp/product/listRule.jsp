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
                    <h5>营销规则管理</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="name" class="col-sm-1 control-label text-right">名称</label>
                            <div class="col-sm-2">
                                <input type="text" id="name" class="form-control">
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
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline btn-default" onclick="showEditRuleWin()">
                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                        </button>
                        <%--<button type="button" class="btn btn-outline btn-default">
                            <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                        </button>--%>
                    </div>
                    <div id="table" class="dt-grid-container"></div>
                    <div id="toolBar" class="dt-grid-toolbar-container"></div>

                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editRuleWin" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑规则</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editRuleForm">
                    <input type="hidden" id="rule_id_edit" name="rule_id">
                    <div class="form-group">
                        <label for="rule_name_edit" class="col-md-4 control-label text-right">名称</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="rule_name_edit" name="rule_name" data-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="community_id_edit" class="col-md-4 control-label text-right">门店</label>
                        <div class="col-md-5">
                            <ui:simpleCommunitySelect id="community_id_edit" name="community_id"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rule_type_edit" class="col-md-4 control-label text-right">类型</label>
                        <div class="col-md-5">
                            <select class="form-control" id="rule_type_edit" name="rule_type">
                                <option value="1">限量商品</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_limit_edit" class="col-md-4 control-label text-right">用户限量</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="user_limit_edit" name="user_limit" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="amount_limit_edit" class="col-md-4 control-label text-right">总量</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="amount_limit_edit" name="amount_limit" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rule_dec_edit" class="col-md-4 control-label text-right">描述</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="rule_dec_edit" name="rule_dec"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label text-right">持续时间</label>
                        <div class="col-md-6">
                            <div class="input-daterange input-group">
                                <input type="text" class="form-control" id="rule_begin_time_edit" name="rule_begin_time_str"/>
                                <span class="input-group-addon">到</span>
                                <input type="text" class="form-control" id="rule_end_time_edit" name="rule_end_time_str"/>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveRule()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var rule_type = {1 : '限量商品'};

    var gridColumn = [
        {id:'rule_name', title:'名称', type:'string', columnClass:'text-center'},
        {id:'rule_type', title:'类型', type:'string', columnClass:'text-center', hideType:'xs', codeTable : rule_type},
        {id:'rule_begin_time', title:'开始时间', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
        {id:'rule_end_time', title:'结束时间', type:'date', format : 'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
        {id:'community_name', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
            return '<button class="btn btn-primary" onclick="showEditRuleWin(' + record.rule_id + ')">修改</button>';
        }}

    ];
    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/product.do?method=searchRule',
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
        var name = $('#name').val();
        var community_id = $('#community_id').val();
        grid.parameters = new Object();
        grid.parameters['name'] = name;
        grid.parameters['community_id'] = community_id;
        grid.loadToFirst();
    }

    function showEditRuleWin(rule_id) {
        clearEditRuleForm();
        if(rule_id) {
            var i = layer.load();
            $.post('${ctx}/product.do?method=getRuleById', {rule_id : rule_id}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#rule_id_edit').val(json.rule_id);
                        $('#community_id_edit').val(json.community_id);
                        $('#rule_name_edit').val(json.rule_name);
                        $('#user_limit_edit').val(json.user_limit);
                        $('#amount_limit_edit').val(json.amount_limit);
                        $('#rule_dec_edit').val(json.rule_dec);
                        if(json.rule_begin_time) {
                            $('#rule_begin_time_edit').val($.fn.DtGrid.tools.dateFormat(json.rule_begin_time, 'yyyy-MM-dd'));
                        }
                        if(json.rule_end_time) {
                            $('#rule_end_time_edit').val($.fn.DtGrid.tools.dateFormat(json.rule_end_time, 'yyyy-MM-dd'));
                        }
                    }
                }
                layer.close(i);
            });
        }
        $('#editRuleWin').modal({backdrop: 'static'});
    }

    function clearEditRuleForm() {
        $('#rule_id_edit').val('');
        $('#rule_name_edit').val('');
        $('#user_limit_edit').val('');
        $('#amount_limit_edit').val('');
        $('#rule_dec_edit').val('');
        $('#rule_begin_time_edit').val('');
        $('#rule_end_time_edit').val('');
        $('#editRuleForm').validator('cleanUp');
    }

    function saveRule() {
        if($('#editRuleForm').isValid()) {
            layer.confirm("确定要保存吗？", function() {
                var i = layer.load();
                $.post('${ctx}/product.do?method=saveRule', $('#editRuleForm').serialize(), function(data) {
                    if(data != null && data == 'success') {
                        layer.alert("操作成功！");
                        $('#editRuleWin').modal('hide');
                        grid.load();
                    } else {
                        layer.alert("操作失败！");
                    }
                    layer.close(i);
                });
            });
        }
    }
</script>

</body>

</html>
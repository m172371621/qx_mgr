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
                    <h5>参数配置<b class="text-danger">（系统核心配置，非专业人员严禁改动）</b></h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="module" class="col-sm-1 control-label text-right">模块</label>
                            <div class="col-sm-2">
                                <select id="module" class="form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${moduleList}" var="module">
                                        <option value="${module}">${module}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label for="name" class="col-sm-1 control-label text-right">名称</label>
                            <div class="col-sm-2">
                                <input id="name" type="text" class="form-control">
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
                        <button type="button" class="btn btn-outline btn-default" onclick="showEditWin()">
                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                        </button>
                    </div>
                    <div id="table" class="dt-grid-container"></div>
                    <div id="toolBar" class="dt-grid-toolbar-container"></div>

                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editWin" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title_edit">编辑参数</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm">
                    <input type="hidden" id="objid_edit" name="objid">
                    <div class="form-group">
                        <label for="module_edit" class="col-md-3 control-label text-right">模块</label>
                        <div class="col-md-7">
                            <input class="form-control" id="module_edit" name="module" data-rule="required;"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name_edit" class="col-md-3 control-label text-right">名称</label>
                        <div class="col-md-7">
                            <input class="form-control" id="name_edit" name="name" data-rule="required;"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="value_edit" class="col-md-3 control-label text-right">值</label>
                        <div class="col-md-7">
                            <input class="form-control" id="value_edit" name="value" data-rule="required;"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remark_edit" class="col-md-3 control-label text-right">备注</label>
                        <div class="col-md-7">
                            <input class="form-control" id="remark_edit" name="remark"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveSetting()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var gridColumn = [
        {id:'module', title:'模块', type:'string', columnClass:'text-center'},
        {id:'name', title:'名称', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'value', title:'值', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'remark', title:'备注', type:'string', columnClass:'text-center', hideType:'xs'},
        {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
            var content = '';
            content += '<button class="btn btn-primary" onclick="showEditWin(\'' + record.objid + '\')">修改</button>';
            content += '  ';
            content += '<button class="btn btn-danger" onclick="removeSetting(\'' + record.objid + '\')">删除</button>';
            return content;
        }}

    ];
    var gridOption = {
        ajaxLoad : true,
        loadURL : '${ctx}/sys.do?method=searchSetting',
        columns : gridColumn,
        gridContainer : 'table',
        toolbarContainer : 'toolBar',
        tools : ''
    };
    var grid = $.fn.DtGrid.init(gridOption);
    $(function(){
        grid.load();
    });

    //自定义查询
    function search(){
        var module = $('#module').val();
        var name = $('#name').val();
        grid.parameters = new Object();
        grid.parameters['module'] = module;
        grid.parameters['name'] = name;
        grid.loadToFirst();
    }

    function clearEditForm() {
        $('#objid_edit').val('');
        $('#module_edit').val('');
        $('#name_edit').val('');
        $('#value_edit').val('');
        $('remark_edit').val('');
        $('#editForm').validator('cleanUp');
    }

    function showEditWin(objid) {
        clearEditForm();
        if(objid) {
            $('#title_edit').text("修改参数");
            var i = layer.load();
            $.post('${ctx}/sys.do?method=getSettingById', {objid : objid}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json) {
                        $('#objid_edit').val(json.objid);
                        $('#module_edit').val(json.module);
                        $('#name_edit').val(json.name);
                        $('#value_edit').val(json.value);
                        $('#remark_edit').val(json.remark);
                    }
                }
                layer.close(i);
            });
        } else {
            $('#title_edit').text("添加参数");
        }

        $('#editWin').modal({ backdrop: 'static' });
    }

    function saveSetting() {
        layer.confirm("确定要保存吗？", function() {
            var i =layer.load();
            $.post('${ctx}/sys.do?method=saveSetting', $('#editForm').serialize(), function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json && json.result) {
                        layer.alert("操作成功！");
                        $('#editWin').modal('hide');
                        grid.load();
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

    function removeSetting(objid) {
        layer.confirm("确定要删除吗？", function() {
            var i =layer.load();
            $.post('${ctx}/sys.do?method=removeSetting', {objid : objid}, function(data) {
                if(data) {
                    var json = eval('(' + data + ')');
                    if(json && json.result) {
                        layer.alert("操作成功！");
                        grid.load();
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
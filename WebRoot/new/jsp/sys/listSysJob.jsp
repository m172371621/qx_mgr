<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <style type="text/css">
        .checkbox input[type=checkbox], .checkbox-inline input[type=checkbox], .radio input[type=radio], .radio-inline input[type=radio] {
            margin-top: 4px;
        }
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>定时任务管理</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline btn-default" onclick="showEditWin()">
                                新增
                            </button>
                            <button type="button" class="btn btn-outline btn-default" onclick="removeJob()">
                                删除
                            </button>
                            <button type="button" class="btn btn-outline btn-default" onclick="startJob()">
                                启动
                            </button>
                            <button type="button" class="btn btn-outline btn-default" onclick="stopJob()">
                                停止
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
                    <h4 class="modal-title" id="title_edit">编辑定时任务</h4>
                </div>
                <div class="modal-body">
                    <form role="form" class="form-horizontal" id="editForm">
                        <input type="hidden" id="objid_edit" name="objid">
                        <div class="form-group">
                            <label for="name_edit" class="col-md-4 control-label text-right">名称</label>
                            <div class="col-md-5">
                                <input class="form-control" id="name_edit" name="name" data-rule="required;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="cronExpression_edit" class="col-md-4 control-label text-right">调度计划</label>
                            <div class="col-md-5">
                                <input class="form-control" id="cronExpression_edit" name="cronExpression" data-rule="required;"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="beanId_edit" class="col-md-4 control-label text-right">beanId</label>
                            <div class="col-md-5">
                                <input class="form-control" id="beanId_edit" name="beanId" data-rule="required;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="clazz_edit" class="col-md-4 control-label text-right">类路径</label>
                            <div class="col-md-5">
                                <input class="form-control" id="clazz_edit" name="clazz"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="job_method_edit" class="col-md-4 control-label text-right">执行方法</label>
                            <div class="col-md-5">
                                <input class="form-control" id="job_method_edit" name="job_method" data-rule="required;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label text-right">类型</label>
                            <div class="col-md-5">
                                <label class="radio-inline">
                                    <input type="radio" value="1" name="isSync" data-rule="checked">同步
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" value="0" name="isSync" data-rule="checked">异步
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="remark_edit" class="col-md-4 control-label text-right">说明</label>
                            <div class="col-md-5">
                                <input class="form-control" id="remark_edit" name="remark"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label text-right">状态</label>
                            <div class="col-md-5">
                                <label class="radio-inline">
                                    <input type="radio" value="1" name="status" data-rule="checked">开启
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" value="0" name="status" data-rule="checked">关闭
                                </label>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveJob()">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var isSync = { 0 : '异步', 1 : '同步'};
        var jobStatus = { 0 : '关闭', 1 : "<font class='text-danger'>开启</font>"};
        var status_name = {'NONE' : '关闭', 'NORMAL' : '正常', 'PAUSED' : '暂停', 'COMPLETE' : '完成', 'ERROR' : '错误', 'BLOCKED' : '正在执行'};

        var gridColumn = [
            {id:'name', title:'名称', type:'string', columnClass:'text-center'},
            {id:'cronExpression', title:'调度计划', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'beanId', title:'beanId', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'clazz', title:'类', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'method', title:'方法', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'isSync', title:'类型', type:'string', columnClass:'text-center', hideType:'xs',codeTable:isSync},
            {id:'status', title:'状态', type:'string', columnClass:'text-center', hideType:'xs',codeTable:jobStatus},
            //{id:'runningStatus', title:'运行状态', type:'string', columnClass:'text-center', hideType:'xs',codeTable:status_name},
            {id:'remark', title:'说明', type:'string', columnClass:'text-center', hideType:'xs'},
            {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs', resolution:function(value, record, column, grid, dataNo, columnNo){
                var content = '';
                content += '<button class="btn btn-primary" onclick="showEditWin(' + record.objid + ')">修改</button>';
                content += '  ';
                content += '<button class="btn btn-danger" onclick="runOnce(' + record.objid + ')">手动执行</button>';
                return content;
            }}
        ];

        var gridOption = {
            check : true,
            ajaxLoad : true,
            loadURL : '${ctx}/sysJob.do?method=searchSysJob',
            columns : gridColumn,
            gridContainer : 'table',
            toolbarContainer : 'toolBar',
            tools : ''
        };

        var grid = $.fn.DtGrid.init(gridOption);

        $(function(){
            grid.load();
        });

        function clearEditForm() {
            $('#objid_edit').val('');
            $('#name_edit').val('');
            $('#cronExpression_edit').val('');
            $('#beanId_edit').val('');
            $('#clazz_edit').val('');
            $('#job_method_edit').val('');
            $('input[name=isSync]').eq(0).prop('checked', true);
            $('input[name=status]').eq(0).prop('checked', true);
            $('#remark_edit').val('');

            $('#editForm').validator('cleanUp');
        }

        function showEditWin(objid) {
            clearEditForm();
            if(objid) {
                $('#title_edit').text("编辑定时任务");
                var i = layer.load();
                $.post('${ctx}/sysJob.do?method=getJobById', {objid : objid}, function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json) {
                            $('#objid_edit').val(json.objid);
                            $('#name_edit').val(json.name);
                            $('#cronExpression_edit').val(json.cronExpression);
                            $('#beanId_edit').val(json.beanId);
                            $('#clazz_edit').val(json.clazz);
                            $('#job_method_edit').val(json.method);
                            $("input[name=isSync][value='" + json.isSync + "']").prop('checked', true);
                            $("input[name=status][value='" + json.status + "']").prop('checked', true);
                            $('#remark_edit').val(json.remark);
                        }
                    }
                    layer.close(i);
                });
            } else {
                $('#title_edit').text("添加定时任务");
            }

            $('#editWin').modal({
                backdrop: 'static'  //点击窗体外不会关闭窗口
            });
        }

        function saveJob() {
            if($('#editForm').isValid()) {
                layer.confirm("确定要保存吗？", function() {
                    var i = layer.load();
                    $.post('${ctx}/sysJob.do?method=saveJob', $('#editForm').serialize(), function(data) {
                        if(data) {
                            var json = eval('(' + data + ')');
                            if(json && json.result) {
                                layer.alert("操作成功!");
                                $('#editWin').modal('hide');
                                grid.load();
                            } else {
                                layer.alert("操作失败!");
                            }
                        }
                        layer.close(i);
                    });
                });
            }
        }

        function removeJob() {
            var records = grid.getCheckedRecords();
            if(records.length == 0) {
                layer.alert("请先选择需要删除的定时任务！");
            } else {
                layer.confirm("确定要删除吗？", function(index) {
                    var ids = '';
                    $.each(records, function (i, v) {
                        ids += v.objid + ',';
                    });
                    if (ids != '') {
                        ids = ids.substring(0, ids.length - 1);
                    }

                    var i = layer.load();
                    $.post('${ctx}/sysJob.do?method=removeJob', {ids : ids}, function(data) {
                        if(data) {
                            var json = eval('(' + data + ')');
                            if(json && json.result) {
                                layer.alert("操作成功!");
                                grid.load();
                            } else {
                                layer.alert("操作失败!");
                            }
                        }
                        layer.close(i);
                    });
                });
            }
        }

        function runOnce(objid) {
            layer.confirm("确定要立即执行吗？", function() {
                var i = layer.load();
                $.post('${ctx}/sysJob.do?method=runOnce', {objid : objid}, function(data) {
                    if(data) {
                        var json = eval('(' + data + ')');
                        if(json && json.result) {
                            layer.alert("操作成功!");
                        } else {
                            layer.alert("操作失败!");
                        }
                    }
                    layer.close(i);
                });
            });
        }

        function startJob() {
            var records = grid.getCheckedRecords();
            if(records.length == 0) {
                layer.alert("请先选择需要启动的定时任务！");
            } else {
                layer.confirm("确定要启动吗？", function(index) {
                    var ids = '';
                    $.each(records, function (i, v) {
                        ids += v.objid + ',';
                    });
                    if (ids != '') {
                        ids = ids.substring(0, ids.length - 1);
                    }

                    var i = layer.load();
                    $.post('${ctx}/sysJob.do?method=startJob', {ids : ids}, function(data) {
                        if(data) {
                            var json = eval('(' + data + ')');
                            if(json && json.result) {
                                layer.alert("操作成功!");
                                grid.load();
                            } else {
                                layer.alert("操作失败!");
                            }
                        }
                        layer.close(i);
                    });
                });
            }
        }

        function stopJob() {
            var records = grid.getCheckedRecords();
            if(records.length == 0) {
                layer.alert("请先选择需要停止的定时任务！");
            } else {
                layer.confirm("确定要停止吗？", function(index) {
                    var ids = '';
                    $.each(records, function (i, v) {
                        ids += v.objid + ',';
                    });
                    if (ids != '') {
                        ids = ids.substring(0, ids.length - 1);
                    }

                    var i = layer.load();
                    $.post('${ctx}/sysJob.do?method=stopJob', {ids : ids}, function(data) {
                        if(data) {
                            var json = eval('(' + data + ')');
                            if(json && json.result) {
                                layer.alert("操作成功!");
                                grid.load();
                            } else {
                                layer.alert("操作失败!");
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